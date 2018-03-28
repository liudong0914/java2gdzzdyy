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
<div class="box3" panelTitle="试卷下载统计折线图" style="margin-right:0px;padding-right:0px;">
	<tr>
		<c:forEach items="${subjectcount}" var="sc" varStatus="">
			<td>${sc[0]}：</td>
			<td><span>${sc[1] }</span></td>&nbsp;&nbsp;&nbsp;
		</c:forEach>
	</tr>
</div>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="scrollContent" >
    <div id="main" style="width:97%;height:500px;"></div>
</div>
<script type="text/javascript">
$(document).ready(function(){
        //加载数据图表
        myChart.setOption({
	   dataZoom: [
	             {
	                 show: true,
	                 realtime: true,
	                 startValue : '${begindate}',
                   	 endValue : '${enddate}',
	                 xAxisIndex: [0, 1]
	             }
	         ],
    xAxis : [
        {
            type : 'category',
            axisLine: {onZero: false},
            data : ${datearray}
        }
    ],
     yAxis: [
        {
            name: '总数',
            type: 'value',
            max: ${maxnum}
        }
    ],
    series: [
       <c:forEach items="${subjectdatalist}" var="sub" varStatus="s">    
        {
            name:'${sub.subjectname}',
            type:'bar',
            stack: '总数',
             label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:${sub.subjectdata}
        },
        </c:forEach>
        {
            name:'下载总数',
            type:'line',
             label: {
                normal: {
                    show: true,
                    position: 'top'
                }
            },
            data:${downloadarray}
        }
       ]
    });    
});
			        	



        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));


 option = {
    title : {
        text: '试卷下载统计图',
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
        data:[<c:forEach items="${subjectlist}" var="sl"> 
    	'${sl[1]}',
    	</c:forEach>'下载总数'],
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
            start: 35,
            end: 50,
            xAxisIndex: [0, 1]
        }
    ],
    grid: [ {
        top:'20%',
        right:80,
        left: 80,
        height: '60%'
    }],
    xAxis : [
        {
            type : 'category',
            axisLine: {onZero: false},
            data : []
        },
         {
            type : 'category',
            boundaryGap : false,
            axisLine: {onZero: true},
            data: []
        }
    ],
    yAxis: [
        {
            name: '下载总数',
            type: 'value',
            max: 1500
        }
    ],
    series: [
		<c:forEach items="${subjectlist}" var="sl">  
        {
            name:'${sl[1]}',
            type:'bar',
            stack: '总数',
             label: {
                normal: {
                    show: true,
                    position: 'inside'
                }
            },
            data:[]
        },
        </c:forEach>
         {
            name:'下载总数',
            type:'line',
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
