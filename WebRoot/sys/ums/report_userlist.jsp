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
<style>

</style>
<script>
function report(){
	var list = document.getElementsByName("checkbox");
	var obj="";
	for(var i=0;i<list.length;i++)
	{
	if(list[i].checked){
		obj+=list[i].value+",";
	}
	}
	if(obj == "")
	{
	  alert("请先选择查询条件");
	  return false;
	}
	document.pageForm.action = '/sysUmsUserInfoAction.do?method=reportListUsertype&obj='+obj;
	document.pageForm.submit();
}
function toExcel(){
	var list = document.getElementsByName("checkbox");
	var obj="";
	for(var i=0;i<list.length;i++)
	{
	if(list[i].checked){
		obj+=list[i].value+",";
	}
	}
	if(obj == "")
	{
	  alert("请至少选择一个查询条件！");
	  return false;
	}
	document.pageForm.action = '/sysUmsUserInfoAction.do?method=uploadExcel&obj='+obj;
	document.pageForm.submit();
}
function reportAjax(){
	var list = document.getElementsByName("checkbox");
	var obj="";
	for(var i=0;i<list.length;i++)
	{
	if(list[i].checked){
		obj+=list[i].value+",";
	}
	}
	if(obj == "")
	{
	  alert("请至少选择一个查询条件！");
	  return false;
	}
	$.ajax({
        type: "get",
        async: false,
        url: "sysUmsUserInfoAction.do?method=getReportAjax&obj=" + obj,
        dataType: "text",
        success: function(data){
        	$("#imgId").attr('src',data);
        }
	});
}
</script>

</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="用户信息统计饼状图" style="margin-right:0px;padding-right:0px;">
<table>
	<td><input  type="checkbox" name="checkbox" value="usertype" onchange="reportAjax()">用户类型</td>
	<td><input  type="checkbox" name="checkbox" value="sex" onchange="reportAjax()">性别</td>
	<td><input  type="checkbox" name="checkbox" value="xueduan" onchange="reportAjax()">学段</td>
	<!--  <td><button type="button" onclick="report()"><span class="icon_find">查询</span></button></td>-->
	<td><button type="button" onclick="toExcel()"><span class="icon_export">导出Excel</span></button></td>
</table>		
</div>

<div id="scrollContent" >
<img id="imgId" src='<bean:write name="pieurl"/>' style="display: block;margin: 0 auto 0;"></img>
</div>

</form>
</body>
</html>