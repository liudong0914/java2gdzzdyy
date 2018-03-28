<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@page import="com.util.date.DateTime"%>
<%@page import="com.wkmk.edu.bo.EduCourseClass"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Constants.PRODUCT_NAME %>-课程管理</title>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<script type="text/javascript" src="/libs/js/jquery.js"></script>
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
<script type="text/javascript">
function postData(){
    document.pageForm.action = "/courseCenter.do?method=courseList&mark=${mark }";
  	document.pageForm.submit();
}
function addCourseClass(courseid){
	var diag = new Dialog();
	diag.Title = "添加课程批次";
	diag.URL = "/courseCenter.do?method=beforeAddCourseClass&courseid=" + courseid;
	diag.Width = 450;
	diag.Height = 280;
	diag.CancelEvent = function(){
		postData();
		diag.close();
	};
	diag.show();
}
function commitCourseClass(courseclassid){
	var str = "提交课程批次需要管理员审核通过后才可使用，确定提交？";
	Dialog.confirm(str,function(){
		document.pageForm.action = "/courseCenter.do?method=commitCourseClass&mark=${mark }&courseclassid=" + courseclassid;
  		document.pageForm.submit();
	});
}
function recommitCourseClass(courseclassid){
	var str = "重新提交课程批次需要管理员审核通过后才可使用，确定提交？";
	Dialog.confirm(str,function(){
		document.pageForm.action = "/courseCenter.do?method=commitCourseClass&mark=${mark }&tag=1&courseclassid=" + courseclassid;
  		document.pageForm.submit();
	});
}
</script>
</head>

<body>
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
<%@ include file="left.jsp" %>

<form name="pageForm" method="post">
<div class="rightCon">
	<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">课程管理</h3>
		</div>
		<div class="mainCon mainCon01">
			<div class="tab">
				<div class="acts" style="left:0px;">
					<logic:equal value="1" name="s_sysuserinfodetail" property="canaddcourse">
					状态：
					<select name="status" class="ipt-text" style="width:100px;height:34px;" onchange="postData();">
						<option value="">全部</option>
						<option value="0" <logic:equal value="0" name="status">selected="selected"</logic:equal>>未提交</option>
						<option value="2" <logic:equal value="2" name="status">selected="selected"</logic:equal>>审核中</option>
						<option value="1" <logic:equal value="1" name="status">selected="selected"</logic:equal>>已通过</option>
						<option value="3" <logic:equal value="3" name="status">selected="selected"</logic:equal>>未通过</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					</logic:equal>
					课程名称：<input type="text" class="ipt-text" name="title" value="${title }" style="width:314px;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-pop ml20" value="查询" onclick="postData()">
				</div>
			</div>
		
			<table width="100%" class="table1">
				<thead>
					<tr>
						<th width="100">状态</th>
						<th>课程批次信息</th>
						<th width="200">开课时间段</th>
						<th width="100">操作</th>
					</tr>
				</thead>
				<%
				List courseList = (List)request.getAttribute("courseList");
				if(courseList != null && courseList.size() > 0){
				%>
				<tbody>
					<tr>
						<td colspan="5">&nbsp;</td>
					</tr>
					<%
					Map classMap = (Map)request.getAttribute("classMap");
					List classList = null;
					EduCourseInfo eci = null;
					EduCourseClass ecc = null;
					String isend = "0";
					for(int i=0, size=courseList.size(); i<size; i++){
						eci = (EduCourseInfo)courseList.get(i);
					%>
					<tr>
						<td colspan="5">
						<table width="100%" class="table">
							<thead>
								<tr>
									<%if("0".equals(eci.getFlago())){ %>
									<th width="100"><a style="padding:5px 10px;background:#fa5678;font-size:12px;color:#fff;">未提交</a></th>
									<%}else if("1".equals(eci.getFlago())){ %>
									<%if(DateTime.getCompareToDate(eci.getEnddate(), DateTime.getDate()) < 0){ %>
									<th width="100"><a style="padding:5px 10px;background:#777;font-size:12px;color:#fff;">已结束</a></th>
									<%}else{ %>
									<th width="100"><a style="padding:5px 10px;background:#56bc78;font-size:12px;color:#fff;">进行中</a></th>
									<%} %>
									<%}else if("2".equals(eci.getFlago())){ %>
									<th width="100"><a style="padding:5px 10px;background:#5678bc;font-size:12px;color:#fff;">审核中</a></th>
									<%}else if("3".equals(eci.getFlago())){ %>
									<th width="100"><a style="padding:5px 10px;background:#000;font-size:12px;color:#fff;">未通过</a></th>
									<%} %>
									<th style="text-align:left;"><a href="/course/<%=eci.getCourseid() %>.htm" target="_blank"><%=eci.getTitle() %></a></th>
									<th width="200">&nbsp;</th>
									<%if("add".equals(eci.getFlags())){ %>
									<%if("1".equals(eci.getFlago())){ %>
									<%if(DateTime.getCompareToDate(eci.getEnddate(), DateTime.getDate()) < 0){ %>
									<th width="100"><input class="btn btn-text btn-blue tipsUp" value="增加课程批次" onclick="addCourseClass('<%=eci.getCourseid() %>')" type="button"></th>
									<%}}else if("0".equals(eci.getFlago())){ %>
									<th width="100"><input class="btn btn-text btn-blue tipsUp" value="提交课程批次" onclick="commitCourseClass('<%=eci.getFlagl() %>')" type="button"></th>
									<%}else if("3".equals(eci.getFlago())){ %>
									<th width="100"><input class="btn btn-text btn-blue tipsUp" value="重新提交课程批次" onclick="recommitCourseClass('<%=eci.getFlagl() %>')" type="button"></th>
									<%}}else{ %>
									<th width="100">&nbsp;</th>
									<%} %>
								</tr>
							</thead>
							<tbody>
								<%
								classList = (List)classMap.get(eci.getCourseid());
								for(int m=0, n=classList.size(); m<n; m++){
									ecc = (EduCourseClass)classList.get(m);
									if(DateTime.getCompareToDate(ecc.getEnddate(), DateTime.getDate()) < 0){
										isend = "1";
									}
								%>
								<%if("1".equals(isend)){ %>
								<tr>
									<td colspan="5">
									<div class="tableBox box02">
									<table width="100%" style="border-collapse:separate;">
										<thead>
										<tr>
										<th width="100" style="color:#ccc;">已结束</th>
										<th style="text-align:left;color:#ccc;"><%=ecc.getClassname() %>（<%=ecc.getUsercount() %>人）</th>
										<th width="200" style="color:#ccc;"><%=ecc.getStartdate().substring(0, 10) %>~<%=ecc.getEnddate().substring(0, 10) %></th>
										<th width="100">
										<a class="alink" href="/courseManager.do?method=index&tag=1&courseid=<%=ecc.getCourseid() %>&courseclassid=<%=ecc.getCourseclassid() %>" style="color:#ccc;">查看</a>
										</th>
										</tr>
										</thead>
									</table>
									</div>
									</td>
								</tr>
								<%}else{ %>
								<tr>
									<td colspan="5">
									<div class="tableBox box02">
									<table width="100%" style="border-collapse:separate;">
										<thead>
										<tr>
										<%if("0".equals(ecc.getStatus())){ %>
										<th width="100">未提交</th>
										<%}else if("1".equals(ecc.getStatus())){ %>
										<th width="100">进行中</th>
										<%}else if("2".equals(ecc.getStatus())){ %>
										<th width="100">审核中</th>
										<%}else if("3".equals(ecc.getStatus())){ %>
										<th width="100">未通过</th>
										<%} %>
										<th style="text-align:left;"><%=ecc.getClassname() %>（<%=ecc.getUsercount() %>人）</th>
										<th width="200"><%=ecc.getStartdate().substring(0, 10) %>~<%=ecc.getEnddate().substring(0, 10) %></th>
										<th width="100">
										<a class="alink" href="/courseManager.do?method=index&tag=1&courseid=<%=ecc.getCourseid() %>&courseclassid=<%=ecc.getCourseclassid() %>">管理</a>
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
						</td>
					</tr>
					<%} %>
				</tbody>
				<%}else{ %>
				<tbody>
					<tr>
						<td colspan="5" style="text-align:left;">暂无任何课程信息！</td>
					</tr>
				</tbody>
				<%} %>
			</table>
		</div>
	</div>
	</div>
</div>
</form>
</div>
<form name="courseClassForm" method="post">
<input type="hidden" value="" name="courseclassname" id="courseclassname"/>
<input type="hidden" value="" name="courseclassstartdate" id="courseclassstartdate"/>
<input type="hidden" value="" name="courseclassenddate" id="courseclassenddate"/>
</form>

<%@ include file="/skin/course01/jsp/footer.jsp"%>
</body>
</html>
