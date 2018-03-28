<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@page import="com.wkmk.edu.bo.EduCourseClass"%>
<%@page import="com.util.date.DateTime"%>
<%@page import="com.util.html.SubStringDirectiveModel"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Constants.PRODUCT_NAME %>-课程资源</title>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<script type="text/javascript" src="/libs/js/jquery.js"></script>
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>
<script type="text/javascript">
function postData(){
    document.pageForm.action = "/courseCenter.do?method=courseFile&mark=${mark }";
  	document.pageForm.submit();
}
function coursefileList(courseid){
	document.pageForm.action = "/courseCenter.do?method=courseFileMain&mark=${mark }&courseid="+courseid;
  	document.pageForm.submit();
}
</script>
<body>
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
<%@ include file="left.jsp" %>
<form name="pageForm" method="post">
<div class="rightCon">
	<div class="main">
		<div class="mainBox">
			<div class="mainTit">
				<h3 class="title">课程资源</h3>
			</div>
			<div class="mainCon mainCon01">
				<div class="tab">
					<div class="acts" style="left:0px;">
						课程名称：<input type="text" class="ipt-text" name="title" value="${title }" style="width:200px;">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn btn-pop ml20" value="查询" onclick="postData()">
					</div>
				</div>
			
				<table width="100%" class="table">
					<thead>
						<tr>
							<th>课程名称</th>
							<th width="80">总课时数</th>
							<th width="60">总价</th>
							<th width="80">操作</th>
						</tr>
					</thead>
					<tbody>
						<%
						PageList pagelist = (PageList)request.getAttribute("pagelist");
						List list = pagelist.getDatalist();
						if(list != null && list.size() > 0){
							EduCourseInfo eci = null;
							for(int i=0, size=list.size(); i<size; i++){
								eci = (EduCourseInfo)list.get(i);
						%>
						<tr>
							<td colspan="7">
							<div class="tableBox box02">
								<table width="100%" style="border-collapse:separate;">
									<thead>
									<tr>
										<th><%=eci.getTitle() %></th>
										<th width="80"><%=eci.getClasshour() %></th>
										<%if(eci.getPrice().floatValue() > 0){ %>
										<th width="60"><%=eci.getPrice() %></th>
										<%}else{ %>
										<th width="60" style="color:#56bc78;">免费</th>
										<%} %>
										<th width="80">
											<input class="btn btn-text btn-blue tipsUp" value="课程资源" style="background:#b3907c;border-bottom:2px solid #b3907c;" onclick="coursefileList('<%=eci.getCourseid() %>')" type="button">
										</th>
									</tr>
									</thead>
								</table>
							</div>
							</td>
						</tr>
						<%}}else{ %>
						<tr>
							<td colspan="7" style="text-align:left;">暂无任何课程信息！</td>
						</tr>
						<%} %>
					</tbody>
				</table>
				<%@ include file="/skin/course/jsp/page.jsp"%>
			</div>
		</div>
	</div>
</div>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="5" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
</form>
</div>

<%@ include file="/skin/course01/jsp/footer.jsp"%>
</body>
</html>
