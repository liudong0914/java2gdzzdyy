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
<div class="box3" panelTitle="用户交易统计折线图" style="margin-right:0px;padding-right:0px;">
	<tr>
		<td>交易总额：</td>
		<td><span id="a" name="a" value=""></span>  </td>&nbsp;&nbsp;&nbsp;
		<td>支出总额：</td>
		<td><span id="b" name="b" value=""></span>  </td>&nbsp;&nbsp;&nbsp;
	</tr>
	<tr>
		<td>收入总额：</td>
		<td><span id="c" name="c" value=""/></span>  </td>&nbsp;&nbsp;&nbsp;
		<td>交易总数：</td>
		<td><span id="d" name="d" value=""/></span>  </td>&nbsp;&nbsp;&nbsp;
		<td>交易总人数：</td>
		<td><span id="e" name="e" value=""/></span>  </td>
	</tr>
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
			        url:"/sysUmsUserInfoAction.do?method=getAjaxUserMoney",
			        dataType:'json',
			        success:function(result){
			       //请求成功时执行该函数内容，result即为服务器返回的json对象
             if (result) {
             var arraya =result.a;
             var arrayb =result.b;
             var arrayc =result.c;
             
              var arraya5 =result.a5;
             var arrayb5 =result.b5;
             
             var arraya2 =result.a2;//时间
             var arrayb2 =result.b2;//总额
             var arrayb3 =result.b3;//支出
             var arrayb4 =result.b4;//收入
             var arrayc2 =result.c2;//最大值
             
             var countMoney =result.countMoney;//总金额
             var countNum =result.countNum;//总数
             var countNumProper =result.countNumProper;//总人数
              var countMoneyIn =result.countMoneyIn;//收入总金额
              var countMoneyOut =result.countMoneyOut;//支出总金额
             
             
			document.getElementById("a").innerHTML  = countMoney+"元";
			document.getElementById("b").innerHTML  = countMoneyOut+"元";
			document.getElementById("c").innerHTML  = countMoneyIn+"元";
			document.getElementById("d").innerHTML  = countNum+"人";
			document.getElementById("e").innerHTML  = countNumProper+"人";
			
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
    xAxis : [
        {
            type : 'category',
            axisLine: {onZero: false},
            data : arraya2
        },
         {
            gridIndex: 1,
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: true},
            data: arraya
        }
    ],
     yAxis: [
        {
            name: '总额',
            type: 'value',
            max: arrayc2
        },
        {
            gridIndex: 1,
            name : '交易数',
            type : 'value',
            max : arrayc
        }
    ],
    series: [
        
        {
            name:'支出总额',
            type:'bar',
            stack: '总额',
             label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:arrayb3
        },
        {
            name:'收入总额',
            type:'bar',
            stack: '总额',
             label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:arrayb4
        },
        {
            name:'交易总额',
            type:'line',
             label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:arrayb2
        },
        {
            name:'交易人数',
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
            data: arrayb5
        },
        {
            name:'交易总数',
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
            data: arrayb
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
        text: '用户交易统计图',
        subtext: '数据来自进步学堂',
        x: 'center',
        align: 'right'
    },
     tooltip : {
        trigger: 'axis',
        axisPointer: {
            animation: false
        }
    },
    legend: {
        data:['支出总额','收入总额','交易总额','交易人数','交易总数'],
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
            start: 65,
            end: 85,
            xAxisIndex: [0, 1]
        },
        {
            type: 'inside',
            realtime: true,
            start: 65,
            end: 85,
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
            axisLine: {onZero: false},
            data : []
        },
         {
            gridIndex: 1,
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: true},
            data: []
        }
    ],
    yAxis: [
        {
            name: '交易总额',
            type: 'value',
            max: 1500
        },
        {
            gridIndex: 1,
            name : '交易数',
            type : 'value',
            max : 500
        }
    ],
    series: [
        {
            name:'支出总额',
            type:'bar',
            stack: '总额',
             label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:[]
        },
        {
            name:'收入总额',
            type:'bar',
            stack: '总额',
             label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:[]
        },
         {
            name:'交易总额',
            type:'line',
             label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:[]
        },
    	{
            name:'交易人数',
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
        },
        {
            name:'交易总数',
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
