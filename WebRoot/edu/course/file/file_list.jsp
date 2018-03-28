<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="java.util.Map"%>
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
    document.pageForm.action = "/eduCourseFileAction.do?method=list";
  	document.pageForm.submit();
}
function search(){
	document.getElementById('pageNo').value = 1;
	postData();
}
function edit(objid){
	document.pageForm.action = "/eduCourseFileAction.do?method=beforeUpdate&objid=" + objid;
  	document.pageForm.submit();
}

function dowmFile(fileid){
	document.pageForm.action = "/eduCourseFileAction.do?method=downFile&fileid=" + fileid;
  	document.pageForm.submit();
}
function convertFile(fileid){
	document.pageForm.action = "/eduCourseFileAction.do?method=convertFile&fileid=" + fileid;
  	document.pageForm.submit();
}
</script>
</head>
<body style="background:#fcfcfc;">
<form name="pageForm" method="post">
<div class="tab">
	<div class="acts" style="left:0px;">
		资源名称：<input type="text" class="ipt-text" name="filename" value="${filename }" style="width:200px;">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<%-- 
		文件类型：
		<select name="filetype" class="ipt-text" style="width:100px;height:34px;">
			<option value="">全部</option>
			<option value="1" <logic:equal value="1" name="filetype">selected="selected"</logic:equal>>文档</option>
			<option value="2" <logic:equal value="2" name="filetype">selected="selected"</logic:equal>>图片</option>
			<option value="3" <logic:equal value="3" name="filetype">selected="selected"</logic:equal>>音频</option>
			<option value="4" <logic:equal value="4" name="filetype">selected="selected"</logic:equal>>视频</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		
		资源类型：
		<select name="coursefiletype" class="ipt-text" style="width:100px;height:34px;" onchange="search()">
			<option value="">全部</option>
			<option value="1" <logic:equal value="1" name="coursefiletype">selected="selected"</logic:equal>>教案</option>
			<option value="2" <logic:equal value="2" name="coursefiletype">selected="selected"</logic:equal>>课件</option>
			<option value="3" <logic:equal value="3" name="coursefiletype">selected="selected"</logic:equal>>习题</option>
			<option value="4" <logic:equal value="4" name="coursefiletype">selected="selected"</logic:equal>>素材</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;--%>
		<input type="button" class="btn btn-pop ml20" value="查询" onclick="search()">
	</div>
	<%
	String isAuhtor = (String)session.getAttribute("isAuhtor");
	Map moduleidMap = (Map)session.getAttribute("moduleidMap");
	String moduleidType = (String)moduleidMap.get("4");
	if("1".equals(isAuhtor) || "2".equals(moduleidType)){
	%>
	<div class="acts">
		<input class="btn btn-pop ml20" value="新增" onclick="addRecord('/eduCourseFileAction.do?method=beforeAdd')" style="display:inline-block;" type="button">
		<input class="btn btn-pop ml20" value="删除" onclick="delRecord('/eduCourseFileAction.do?method=delBatchRecord')" type="button">
	</div>
	<%} %>
</div>

<table width="100%" class="table">
	<thead>
	<tr>
		<th width="45">
		<input type="checkbox" onclick="setState(this.checked)">
		</th>
		<th>资源名称</th>
		<th width="80">大小</th>
		<th width="80">资源类型</th>
		<th width="100">上传用户</th>
		<th width="80">转码状态</th>
		<th width="100">操作</th>
	</tr>
	</thead>
	<tbody>
	<logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
	<tr>
		<td colspan="7">
		<div class="tableBox box02">
			<table width="100%" style="border-collapse:separate;">
				<thead>
				<tr>
					<th width="45">
						<input class="checkShow msgCheck" type="checkbox" name="checkid" value="<bean:write property="fileid" name="model"/>" <bean:write property="flags" name="model"/>/>
					</th>
					<th style="text-align:left;"><bean:write property="filename" name="model"/></th>
					<th width="80"><bean:write property="flago" name="model"/></th>
					<th width="80"><bean:write property="coursefiletype" name="model"/></th>
					<th width="100"><bean:write name="model" property="flagl"/></th>
					<th width="80">
						<c:if test="${model.convertstatus == '0' }">
						待转换
						</c:if>
						<c:if test="${model.convertstatus == '1' }">
						正常
						</c:if>
						<c:if test="${model.convertstatus == '2' }">
						失败
						</c:if>
					</th>
					<th width="100">
						<a class="alink" href="javascript:edit('<bean:write name="model" property="fileid"/>')">编辑</a>
						<a class="alink" href="javascript:dowmFile('<bean:write name="model" property="fileid"/>')">下载</a>
						<c:if test="${model.convertstatus == '0' }">
							<a class="alink" href="javascript:;" onclick="convertFile('<bean:write name="model" property="fileid"/>')">优先转码</a>
						</c:if>
						<c:if test="${model.convertstatus == '2' }">
							<a class="alink" href="javascript:;" onclick="convertFile('<bean:write name="model" property="fileid"/>')">重新转码</a>
						</c:if>
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
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
</form>
</body>
</html>