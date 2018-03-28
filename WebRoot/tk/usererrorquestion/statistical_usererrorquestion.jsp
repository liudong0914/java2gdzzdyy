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
<div class="box3" panelTitle="用户错题统计柱状图" style="margin-right:0px;padding-right:0px;">
</div>
<!-- 
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="backUp()"><span class="icon_back">返回</span></a>
		</div>
	</div>		
	</div>	
	</div>
	<div class="clear"></div>
</div>
 -->
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="scrollContent" >
    <div id="main" style="width:100%;height:600px;"></div>
</div>
<script type="text/javascript">
$(document).ready(function(){
 		myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
$.ajax({
			        type:"post",
			        url:"/tkUserErrorQuestionAction.do?method=getAjaxUserErrorQuestion",
			        dataType:'json',
			        success:function(result){
			       //请求成功时执行该函数内容，result即为服务器返回的json对象
             if (result) {
             var arraya1 =result.a1;
             var arrayb1 =result.b1;
             var arrayb2 =result.b2;
              var flag =result.flag;
              document.getElementById("flag").value=flag;
              
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
     xAxis: [
        {
            type: 'category',
            data: arraya1
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '数量',
            axisLabel: {
                formatter: '{value} 个'
            }
        }
    ],
    series: [
         {
            name:'错题数量',
            type:'bar',
             label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:arrayb2
        },
        {
            name:'总题数',
            type:'bar',
             label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:arrayb1
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
 		//app.title = '折柱混合';

option = {
    tooltip: {
        trigger: 'axis'
    },
    toolbox: {
        feature: {
        	 dataZoom: {
                 yAxisIndex: 'none'
             },
            dataView: {show: true, readOnly: false},
            magicType: {show: true, type: ['line', 'bar']},
            saveAsImage: {show: true}
        }
    },
    legend: {
        data:['错题数量','总题数']
    },
    xAxis: [
        {
            type: 'category',
            data: []
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '数量',
            axisLabel: {
                formatter: '{value} 个'
            }
        }
    ],
      dataZoom: [
        {
            show: true,
            realtime: true,
            start: 0,
            end: 50
        },
        {
            type: 'inside',
            realtime: true,
            start: 0,
            end: 50
        }
    ],
    series: [
        {
            name:'错题数量',
            type:'bar',
             label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:[]
        },
        {
            name:'总题数',
            type:'bar',
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
        // 处理点击事件并且跳转到相应的百度搜索页面
		myChart.on('click', function (params) {
    	var flag = document.getElementById("flag").value;
    	if(flag != "2"){
    	myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
		$.ajax({
					        type:"post",
					        url:"/tkUserErrorQuestionAction.do?method=getAjaxUserErrorQuestion&obj="+params.name+"&flag="+flag,
					        dataType:'json',
					        success:function(result){
					       //请求成功时执行该函数内容，result即为服务器返回的json对象
		             if (result) {
		             var arraya1 =result.a1;
		             var arrayb1 =result.b1;
		             var arrayb2 =result.b2;
		             var flag =result.flag;
              		document.getElementById("flag").value=flag;
              		
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
		     xAxis: [
		        {
		            type: 'category',
		            data: arraya1
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value',
		            name: '数量',
		            axisLabel: {
		                formatter: '{value} 个'
		            }
		        }
		    ],
		    series: [
		         {
		            name:'错题数量',
		            type:'bar',
		            data:arrayb2
		        },
		        {
		            name:'总题数',
		            type:'bar',
		            data:arrayb1
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
	}	
    	
});

function backUp(){
 		window.location.href="/tkUserErrorQuestionAction.do?method=statisticalUserErrorQuestion";
}	
</script>
<input type="hidden"  id="flag" name="flag" value=""/>
  </body>
</html>
