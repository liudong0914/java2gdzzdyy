<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/libs/js/sk/comm.js"></script>
<link type="text/css" href="/skin/course/css/style.css" rel="stylesheet">
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount" />

function postData(){
    document.pageForm.action = "/courseManager.do?method=columnList";
  	document.pageForm.submit();
}

function edit(objid){
	document.pageForm.action = "/courseManager.do?method=beforeUpdateColumn&objid=" + objid;
  	document.pageForm.submit();
}

$(function(){
  	<logic:present name="reloadtree">
    parent.rfrmleft.location ='/courseManager.do?method=columnTree';
  	</logic:present>  
})
</script>
</head>
<body style="background:#fcfcfc;">
<form name="pageForm" method="post">
<%
String isAuhtor = (String)session.getAttribute("isAuhtor");
Map moduleidMap = (Map)session.getAttribute("moduleidMap");
String moduleidType = (String)moduleidMap.get("2");
if("1".equals(isAuhtor) || "2".equals(moduleidType)){
%>
<logic:notEqual value="1" name="eduCourseInfo" property="status">
<div class="tab">
	<div class="acts" style="left:0px;">
		<input class="btn btn-pop ml20" value="新增" onclick="addRecord('/courseManager.do?method=beforeAddColumn')" style="display:inline-block;" type="button">
		<input class="btn btn-pop ml20" value="删除" onclick="delRecord('/courseManager.do?method=delBatchColumn')" type="button">
	</div>
</div>
</logic:notEqual>
<%} %>

<table width="100%" class="table">
	<thead>
	<tr>
		<th width="45">
		<input type="checkbox" onclick="setState(this.checked)">
		</th>
		<th width="160">编号</th>
		<th>目录名称</th>
		<th width="100">目录别名</th>
		<th width="60">状态</th>
		<th width="60">操作</th>
	</tr>
	</thead>
	<tbody>
	<logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
	<tr>
		<td colspan="6">
		<div class="tableBox box02">
			<table width="100%" style="border-collapse:separate;">
				<thead>
				<tr>
					<th width="45">
						<input class="checkShow msgCheck" type="checkbox" name="checkid" value="<bean:write property="columnid" name="model"/>" <bean:write property="flags" name="model"/>/>
					</th>
					<th width="160"><bean:write property="columnno" name="model"/></th>
					<th style="text-align:left;"><bean:write property="title" name="model"/></th>
					<th width="100" style="text-align:left;"><bean:write property="secondTitle" name="model"/></th>
					<th width="60"><java2code:value codetype="status4" property="status" color="red" colorvalue="2"></java2code:value></th>
					<th width="60">
						<%if("1".equals(isAuhtor) || "2".equals(moduleidType)){ %>
						<a class="alink" href="javascript:edit('<bean:write name="model" property="columnid"/>')">编辑</a>
						<%}else{ %>
						<a class="alink" href="javascript:edit('<bean:write name="model" property="columnid"/>')">查看</a>
						<%} %>
					</th>
				</tr>
				</thead>
			</table>
		</div>
		</td>
	</tr>
	</logic:iterate>
	</tbody>
</table>
<%@ include file="/skin/course/jsp/page.jsp"%>

<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
<input type="hidden" value="${parentno }" name="parentno"/>
</form>
</body>
</html>