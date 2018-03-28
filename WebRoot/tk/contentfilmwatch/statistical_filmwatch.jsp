<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
<%@page import="com.wkmk.sys.bo.SysUnitInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->   

<!-- 引入 ECharts 文件 -->  
<script type="text/javascript" src="../../libs/js/echarts.js"></script>
</head>
<body>
<div class="box3" panelTitle="微课播放统计折线图" style="margin-right:0px;padding-right:0px;">
</div>
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="scrollContent" >
    <div id="main" style="width:100%;height:600px;"></div>
    </div>
<script type="text/javascript">
$(document).ready(function(){
 		myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
$.ajax({
			        type:"post",
			        url:"/tkBookContentFilmWatchAction.do?method=getAjaxFilmWatch",
			        dataType:'json',
			        success:function(result){
			       //请求成功时执行该函数内容，result即为服务器返回的json对象
             if (result) {
             var arraya =result.a;//微课播放 日期
             var arrayb =result.b;//总数 
             var arraymax1 =result.max1;//总数最大值
             var arrayc =result.c;//试听微课播放 日期
             var arrayd =result.d;//总数
             var arraymax2 =result.max2;// 最大值
             var starttime =result.starttime;
             var endtime =result.endtime;
                    myChart.hideLoading();    //隐藏加载动画
                    //加载数据图表
                    var timeData = arraya;
timeData = timeData.map(function (str) {
    return str.replace('2009/', '');
});
                     myChart.setOption({
 	 dataZoom: [
   	            {
   	                show: true,
   	                realtime: true,
   	                startValue : starttime,
   	                endValue : endtime,
   	                xAxisIndex: [0, 1]
   	            },
   	            {
   	                type: 'inside',
   	                realtime: true,
   	                startValue : starttime,
   	                endValue : endtime,
   	                xAxisIndex: [0, 1]
   	            }
   	        ],
      xAxis:[
        {
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: true},
            data: timeData
        },
        {
            gridIndex: 1,
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: true},
            data: timeData,
        }
    ],
    yAxis : [
        {
            name : '微课播放总数',
            type : 'value',
            max : arraymax1
        },
        {
            gridIndex: 1,
            name : '试听微课播放总数',
            type : 'value',
            max : arraymax2
        }
    ],  
    series : [
        {
            name:'微课播放总数',
            type:'line',
            symbolSize: 8,
            hoverAnimation: false,
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:arrayb
        },
        {
            name:'试听微课播放总数',
            type:'line',
            xAxisIndex: 1,
            yAxisIndex: 1,
            symbolSize: 8,
            hoverAnimation: false,
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data: arrayd
        }
    ]
    });
			        	
}
},
 error : function(errorMsg) {
             //请求失败时执行该函数
         alert("图表请求数据失败!");
         myChart.hideLoading();
         }
});

});

        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        //app.title = '嵌套环形图';

 
var timeData = [];

timeData = timeData.map(function (str) {
    return str.replace('2009/', '');
});

option = {
    title: {
        text: '微课播放统计图',
        subtext: '数据来自进步学堂',
        x: 'center'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            animation: false
        }
    },
    legend: {
        data:['微课播放总数','试听微课播放总数'],
        x: 'left'
    },
    toolbox: {
        feature: {
            dataZoom: {
                yAxisIndex: 'none'
            },
              dataView: {readOnly: false},
            magicType: {type: ['line', 'bar']},
            saveAsImage: {}
        }
    },
    dataZoom: [
        {
            show: true,
            realtime: true,
            start: 30,
            end: 70,
            xAxisIndex: [0, 1]
        },
        {
            type: 'inside',
            realtime: true,
            start: 30,
            end: 70,
            xAxisIndex: [0, 1]
        }
    ],
    grid: [{
        left: 50,
        right: 50,
        height: '25%'
    }, {
        left: 50,
        right: 50,
        top: '50%',
        height: '35%'
    }],
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: true},
            data: []
        },
        {
            gridIndex: 1,
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: true},
            data: []
        }
    ],
    yAxis : [
        {
            name : '微课播放总数',
            type : 'value',
            max : 500
        },
        {
            gridIndex: 1,
            name : '试听微课播放总数',
            type : 'value',
            max : 500
        }
    ],
    series : [
        {
            name:'微课播放总数',
            type:'line',
            symbolSize: 8,
            hoverAnimation: false,
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:[]
        },
        {
            name:'试听微课播放总数',
            type:'line',
            xAxisIndex: 1,
            yAxisIndex: 1,
            symbolSize: 8,
            hoverAnimation: false,
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data: []
        }
    ]
};


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
</script>
  </body>
</html>
