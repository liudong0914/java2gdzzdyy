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
<div class="box3" panelTitle="听力播放统计折线图" style="margin-right:0px;padding-right:0px;">
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
			        url:"/tkBookContentWatchAction.do?method=getAjaxContentWatch",
			        dataType:'json',
			        success:function(result){
			       //请求成功时执行该函数内容，result即为服务器返回的json对象
             if (result) {
             var arraya =result.a;
             var arrayb =result.b;
             var arrayc =result.c;
             var starttime =result.starttime;
             var endtime =result.endtime;
                    myChart.hideLoading();    //隐藏加载动画
                    //加载数据图表
                     myChart.setOption({
 dataZoom: [
            {
                show: true,
                realtime: true,
                startValue : starttime,
                endValue : endtime
            },
            {
                type: 'inside',
                realtime: true,
                startValue : starttime,
                endValue : endtime
            }
        ],
	xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: false},
            data : arraya
        }
    ],
    yAxis: [
        {
            name: '播放次数',
            type: 'value',
            max: arrayc
        }
    ],
    series: [
        {
            name:'播放次数',
            type:'line',
            animation: false,
            label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:arrayb
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

 option = {
    title : {
        text: '听力播放统计图',
        subtext: '数据来自进步学堂',
        x: 'center',
        align: 'right'
    },
    grid: {
        bottom: 80
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
    tooltip : {
        trigger: 'axis',
        axisPointer: {
            animation: false
        }
    },
    legend: {
        data:['播放次数'],
        x: 'left'
    },
    dataZoom: [
        {
            show: true,
            realtime: true,
            start: 65,
            end: 85
        },
        {
            type: 'inside',
            realtime: true,
            start: 65,
            end: 85
        }
    ],
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: false},
            data : []
        }
    ],
    yAxis: [
        {
            name: '播放次数',
            type: 'value',
            max: 1500
        }
    ],
    series: [
        {
            name:'播放次数',
            type:'line',
            animation: false,
             label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:[]
        }
    ]
};


        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
</script>
  </body>
</html>
