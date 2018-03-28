<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.edu.bo.EduCourseUser" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/libs/js/sk/comm.js"></script>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount" />

function postData(){
    document.pageForm.action = "/eduCourseUserAction.do?method=teacherList";
  	document.pageForm.submit();
}
function search(){
	document.getElementById('pageNo').value = 1;
	postData();
}
function updateTeacher(courseuserid){
    document.pageForm.action = "/eduCourseUserAction.do?method=beforeUpdateTeacher&objid="+courseuserid;
  	document.pageForm.submit();
}

</script>
</head>

<body>
<form name="pageForm" method="post">
<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">助教管理</h3>
		</div>
		<div class="mainCon mainCon01">
			<div class="tab">
				<div class="acts" style="left:0px;">
					真实姓名：<input type="text" class="ipt-text" name="username" value="${username }" style="width:200px;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					院企用户：
					<select name="vip" class="ipt-text" style="width:100px;height:34px;" onchange="search()">
						<option value="">全部</option>
						<option value="1" <logic:equal value="1" name="vip">selected="selected"</logic:equal>>是</option>
						<option value="0" <logic:equal value="0" name="vip">selected="selected"</logic:equal>>否</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-pop ml20" value="查询" onclick="search()">
				</div>
				<div class="acts">
					<input class="btn btn-pop ml20" value="新增" onclick="addRecord('/eduCourseUserAction.do?method=beforeAddTeacher')" style="display:inline-block;" type="button">
					<input class="btn btn-pop ml20" value="删除" onclick="delRecord('/eduCourseUserAction.do?method=delBatchRecordTeacher')" type="button">
				</div>
			</div>
		
			<table width="100%" class="table">
				<thead>
					<tr>
						<th width="45">
						<input type="checkbox" onclick="setState(this.checked)">
						</th>
						<th width="160">用户名</th>
						<th>真实姓名</th>
						<th width="150">加入时间</th>
						<th width="100">用户类型</th>
						<th width="100">院企用户</th>
						<th width="60">操作</th>
					</tr>
				</thead>
				<tbody>
				<%
				PageList pagelist = (PageList)request.getAttribute("pagelist");
				List list = pagelist.getDatalist();
				if(list != null && list.size() > 0){
				    Object[] obj = null;
				    EduCourseUser ecu = null;
				    SysUserInfo sui = null;
					for(int i=0, size=list.size(); i<size; i++){
						obj = (Object[])list.get(i);
						ecu = (EduCourseUser)obj[0];
						sui = (SysUserInfo)obj[1];
				%>
					<tr>
						<td colspan="7">
						<div class="tableBox box02">
							<table width="100%" style="border-collapse:separate;">
								<thead>
								<tr>
									<th width="45">
										<input class="checkShow msgCheck" type="checkbox" name="checkid" value="<%=ecu.getCourseuserid() %>" />
									</th>
									<th width="160"><%=sui.getLoginname() %></th>
									<th><%=sui.getUsername() %></th>
									<th width="150"><%=ecu.getCreatedate() %></th>
									<th width="100">
										<%if(ecu.getUsertype().equals("1")){ %>
										专家教师
										<%}else if(ecu.getUsertype().equals("2")){ %>
										助教
										<%}else if(ecu.getUsertype().equals("3")){ %>
										学员
										<%} %>
									</th>
									<th width="100">
										<%if(ecu.getVip().equals("0")){ %>
										否
										<%}else if(ecu.getVip().equals("1")){ %>
										是
										<%} %>
									</th>
									<th width="60">
										<a class="alink" href="#"  onclick="updateTeacher('<%=ecu.getCourseuserid() %>')">编辑</a>
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
		</div>
	</div>
</div>
</form>
</body>
</html>
