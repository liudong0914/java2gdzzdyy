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
<script>
function report(){
	var opinionCategory = document.getElementsByName('optionName');
    var opinionCategoryValue = 1;
    for(var i=0;i<opinionCategory.length;i++){
           if(opinionCategory[i].checked){
                         opinionCategoryValue = opinionCategory[i].value;
           }
    }
	var startTime = document.getElementById("startTime").value;
	var endTime = document.getElementById("endTime").value;
	if(startTime == "")
	{
	  alert("请先选择开始日期！");
	  return false;
	}
	if(endTime == "")
	{
	  alert("请先选择结束日期！");
	  return false;
	}
	var checkresult=compareDay(startTime,endTime);
    if(checkresult==-1)
    {
     alert("开始日期大于结束日期,请重新选择！");
     return false;
    }else if(checkresult==0)
    {
        alert("开始日期等于结束日期,请重新选择！");
        return false;
       }
	document.pageForm.action = '/sysUmsUserInfoAction.do?method=reportUserPayList&startTime='+startTime+'&endTime='+endTime+'&option='+opinionCategoryValue;
	document.pageForm.submit();
}

//**********判断两个日期大小************/
function compareDay(a,b){
 //a,b格式为yyyy-MM-dd 
 var a1=a.split("-");
 var b1=b.split("-");
 var a2=new Date(a1[0],a1[1],a1[2]);
 var b2=new Date(b1[0],b1[1],b1[2]);
 if(Date.parse(a2)-Date.parse(b2)>0)
 {
  //a>b  
  return -1;
 }
 if(Date.parse(a2)-Date.parse(b2)==0)
 {
  //a=b  
  return 0;
 }
 if(Date.parse(a2)-Date.parse(b2)<0)
 {
  //a<b  
  return 1;
 }
}
function downpicture(){
	var lineurl = document.getElementById("urlId").value;
	var name = document.getElementById("name").value;
	document.pageForm.action = '/sysUmsUserInfoAction.do?method=downpicture&lineurl='+lineurl+'&name='+name;
	document.pageForm.submit();
}
</script>

</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="用户充值统计散点折线图" style="margin-right:0px;padding-right:0px;">
<table>
			<tr>
           <td class="ali03">开始日期：</td>
			<td class="ali01">
				<input type="text" id="startTime" readonly="readonly" value="<bean:write name="startTime"/>" class="date validate[required,custom[date]]"/>(日期格式：2008-08-08)
			</td>
			<td> <input type="radio"    name="optionName" value="1"  <%if("1".equals(request.getAttribute("option") + ""))out.print(" checked ");%>/>充值人数</td>
			<td><button type="button" onclick="report()"><span class="icon_find">查询</span></button></td>
			</tr>
			<tr>
			<td class="ali03">结束日期：</td>
			<td class="ali01">
				<input type="text" id="endTime" readonly="readonly" value="<bean:write name="endTime"/>" class="date validate[required,custom[date]]"/>(日期格式：2008-08-08)
			</td>
			<td> <input type="radio"    name="optionName" value="2" <%if("2".equals(request.getAttribute("option") + ""))out.print(" checked ");%>/>充值总额</td>
			<td><button type="button" onclick="downpicture()"><span class="icon_jpg">导出</span></button></td>
			</tr>
		
</table>		
</div>
<div id="scrollContent" >
<img id="imgId" src='<bean:write name="lineurl"/>' style="display: block;margin: 0 auto 0;"></img>
<input type="hidden" id="urlId" value="<bean:write name="lineurl"/>"/>
<input type="hidden" id="name" value="3"/>
</div>

</form>
</body>
</html>