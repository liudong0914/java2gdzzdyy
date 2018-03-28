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
<div class="box3" panelTitle="班级分类统计饼状图" style="margin-right:0px;padding-right:0px;">
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
			        url:"/tkClassInfoAction.do?method=getAjaxClassType",
			        dataType:'json',
			        success:function(result){
			       //请求成功时执行该函数内容，result即为服务器返回的json对象
             if (result) {
             var arraya =result.a;
             var arrayb =result.b;
              var arrayc =result.c;
                    myChart.hideLoading();    //隐藏加载动画
                    //加载数据图表
                     myChart.setOption({
       legend: {
        	orient: 'vertical',
        	x: 'left',
        	data:arrayc
    	},
        series: [
        {
            name:'班级分类',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '35%'],

            label: {
                normal: {
                    position: 'inner',
                    formatter: '{b}: {c}({d}%)'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:arraya
        },
        {
            name:'班级分类',
            type:'pie',
            radius: ['40%', '55%'],
            label: {
                normal: {
                   formatter: '{b}: {c}({d}%)'
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

 var option = {
  title : {
        text: '班级分类统计图',
        x:'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b}: {c} ({d}%)"
    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:[]
    },
        toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView: {readOnly: false},
          	magicType: {type: ['pie', 'funnel']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    series: [
        {
            name:'班级分类',
            type:'pie',
            selectedMode: 'single',
            radius: [0, '35%'],

            label: {
                normal: {
                    position: 'inner',
                    formatter: '{b}: {c}({d}%)'
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            data:[]
        },
        {
            name:'班级分类',
            type:'pie',
            radius: ['40%', '55%'],
            label: {
                normal: {
                   formatter: '{b}: {c}({d}%)'
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
