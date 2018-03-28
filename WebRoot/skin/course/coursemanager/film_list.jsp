<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.edu.bo.EduCourseFilm" %>
<%@page import="com.wkmk.vwh.bo.VwhFilmInfo" %>
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
    document.pageForm.action = "/eduCourseFilmAction.do?method=filmList";
  	document.pageForm.submit();
}

function edit(objid){
	document.pageForm.action = "/eduCourseFilmAction.do?method=beforeUpdate&objid=" + objid;
  	document.pageForm.submit();
}
function addFilmPix(filmid){
	var diag = new top.Dialog();
	diag.Title = "微课视频";
	diag.URL = '/eduCourseFilmAction.do?method=videoList&flag=1&filmid='+filmid;
	diag.Width = 550;
	diag.Height = 300;
	//diag.ShowMaxButton=true;
	//diag.ShowMinButton=true;
	diag.CancelEvent = function(){
		postData();
		diag.close();
	};
	diag.show();
	//diag.max();
}
</script>
</head>
<body style="background:#fcfcfc;">
<form name="pageForm" method="post">
<div class="tab">
	<div class="acts" style="left:0px;">
		微课名称：<input type="text" class="ipt-text" name="title" value="${title }" style="width:200px;">
		&nbsp;&nbsp;&nbsp;&nbsp;
		状态：
		<select name="status" class="ipt-text" style="width:100px;height:34px;" onchange="postData()">
			<option value="">全部</option>
			<option value="1" <logic:equal value="1" name="status">selected="selected"</logic:equal>>正常</option>
			<option value="2" <logic:equal value="2" name="status">selected="selected"</logic:equal>>禁用</option>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="btn btn-pop ml20" value="查询" onclick="postData()">
	</div>
	<%
	String isAuhtor = (String)session.getAttribute("isAuhtor");
	Map moduleidMap = (Map)session.getAttribute("moduleidMap");
	String moduleidType = (String)moduleidMap.get("3");
	if("1".equals(isAuhtor) || "2".equals(moduleidType)){
	%>
	<logic:notEqual value="1" name="eduCourseInfo" property="status">
	<div class="acts">
		<input class="btn btn-pop ml20" value="新增" onclick="addRecord('/eduCourseFilmAction.do?method=beforeAddFilm')" style="display:inline-block;" type="button">
		<input class="btn btn-pop ml20" value="删除" onclick="delRecord('/eduCourseFilmAction.do?method=delBatchRecord')" type="button">
	</div>
	</logic:notEqual>
	<%} %>
</div>

<table width="100%" class="table">
	<thead>
	<tr>
		<th width="45">
		<input type="checkbox" onclick="setState(this.checked)">
		</th>
		<th>微课名称</th>
		<th width="60">序号</th>
		<th width="160">上传时间</th>
		<th width="60">标价</th>
		<th width="60">状态</th>
		<th width="100">操作</th>
	</tr>
	</thead>
	<tbody>
	<%
			PageList pagelist = (PageList)request.getAttribute("pagelist");
			List list = pagelist.getDatalist();
			if(list != null && list.size() > 0){
			    Object[] obj = null;
			    EduCourseFilm ecf = null;
			    VwhFilmInfo vfi = null;
				for(int i=0, size=list.size(); i<size; i++){
					obj = (Object[])list.get(i);
					ecf = (EduCourseFilm)obj[0];
					vfi = (VwhFilmInfo)obj[1];
			%>
	<tr>
		<td colspan="7">
		<div class="tableBox box02">
			<table width="100%" style="border-collapse:separate;">
				<thead>
				<tr>
					<th width="45">
						<input class="checkShow msgCheck" type="checkbox" name="checkid" value="<%=ecf.getCoursefilmid() %>"/>
					</th>
					<th style="text-align:left;"><%=ecf.getTitle() %></th>
					<th width="60"><%=ecf.getOrderindex() %></th>
					<th width="160"><%=ecf.getCreatedate() %></th>
					<th width="60"><%=ecf.getPrice() %></th>
					<th width="60">
						<%if(ecf.getStatus().equals("1")){ %>
						正常
						<%}else if(ecf.getStatus().equals("2") ){ %>
						禁用
						<%} %>
					</th>
					<th width="100">
						<a class="alink" href="javascript:edit('<%=ecf.getCoursefilmid() %>')">编辑</a>
						<a class="alink" href="javascript:addFilmPix('<%=ecf.getFilmid() %>')">视频</a>
					</th>
				</tr>
				</thead>
			</table>
		</div>
		</td>
	</tr>
	<%}} %>
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