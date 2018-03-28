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
<title><%=Constants.PRODUCT_NAME %>-课程学习</title>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<script type="text/javascript" src="/libs/js/jquery.js"></script>
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>
<script type="text/javascript">
function postData(){
    document.pageForm.action = "/courseCenter.do?method=courseStudy&mark=${mark }";
  	document.pageForm.submit();
}
function addCourseComment(courseid){
	var diag = new Dialog();
	diag.Title = "评价课程";
	diag.URL = "/courseCenter.do?method=beforeAddCourseComment&courseid=" + courseid;
	diag.Width = 450;
	diag.Height = 330;
	diag.CancelEvent = function(){
		diag.close();
	};
	diag.show();
}
</script>
<body>
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
<%@ include file="left.jsp" %>
<div class="rightCon">
	<div class="main">
		<div class="mainBox">
			<div class="mainTit">
				<h3 class="title">课程学习</h3>
			</div>
			<%
			PageList pagelist = (PageList)request.getAttribute("pagelist");
			List list = pagelist.getDatalist();
			if(list != null && list.size() > 0){
			%>
			<form name="pageForm" method="post">
			<div class="mainCon mainCon01">
				<table class="mb20" width="100%">
				<tbody>
					<%
					Map usertitleMap = (Map)request.getAttribute("usertitleMap");
					Object[] obj = null;
					EduCourseInfo eci = null;
					EduCourseClass ecc = null;
					String zhujiaoname = null;
					String nowDte = DateTime.getDate();
					for(int i=0, size=list.size(); i<size; i++){
						String isend = "0";
						obj = (Object[])list.get(i);
						eci = (EduCourseInfo)obj[0];
						ecc = (EduCourseClass)obj[1];
						if(DateTime.getCompareToDate(ecc.getEnddate(), DateTime.getDate()) < 0){
							isend = "1";
						}
						zhujiaoname = (String)usertitleMap.get(ecc.getCourseclassid());
					%>
					<tr class="endType">
						<td class="endType_td">
							<div class="imgs">
							<%if("1".equals(isend)){ %>
							<i class="state end">已结束</i>
							<%}else{ %>
							<i class="state do-ing">进行中</i>
							<%} %>
							<!-- 此处连接课程前台展示界面，课程学习按钮直接进入学习播放界面 -->
							<a href="/course/<%=eci.getCourseid() %>.htm" target="_blank">
								<img src="/upload/<%=eci.getSketch() %>" width="210" height="125">
							</a>
							</div>
						</td>
						<td>
							<dl>
							<a href="/course/<%=eci.getCourseid() %>.htm" target="_blank"><dt title="<%=eci.getTitle() %>" class="endType_dt"><%=SubStringDirectiveModel.subString(eci.getTitle(), 27, "...") %></dt></a>
							<dd>
							<p class="tipsUp_p"><%=ecc.getClassname() %>（<%=ecc.getStartdate().substring(0,10) %> ~ <%=ecc.getEnddate().substring(0,10) %>）</p>
							<p class="tipsUp_p">主讲：<%=eci.getSysUserInfo().getUsername() %><%if(zhujiaoname != null){ %>，助教：<%=zhujiaoname.substring(1) %><%} %></p>
							<p class="tipsUp_p">学员：<%=ecc.getUsercount() %>人，总课时：<%=eci.getClasshour() %>课时</p>
							</dd>
							</dl>
						</td>
						<td class="relative" width="110">
							<%if("1".equals(isend)){ %>
							<a href="/course-play-<%=ecc.getCourseid() %>-0.htm" target="_blank"><input class="btn btn-text btn-blue tipsUp" value="回顾课程" onclick="" type="button"/></a>
							<%}else{ %>
							<a href="/course-play-<%=ecc.getCourseid() %>-0.htm" target="_blank"><input class="btn btn-text btn-blue tipsUp" value="学习课程" style="background:#9ad286;border-bottom:2px solid #9ac786;" type="button"/></a>
							<%} %>
							<br/><br/>
							<input class="btn btn-text btn-blue tipsUp" value="评价课程" style="background:#b3907c;border-bottom:2px solid #b3907c;" onclick="addCourseComment('<%=eci.getCourseid() %>')" type="button">
						</td>
					</tr>
					<%} %>
				</tbody>
				</table>
				<%@ include file="page.jsp"%>
			</div>
			<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
			<input type="hidden" value="5" name="pager.pageSize" id="pageSize"/>
			<input type="hidden" value="1" name="pager.type"/>
			</form>
			<%}else{ %>
			<div class="mainCon pad10">
	            <div class="mainCon-bd">
	              <div class="learningCourse-con">
	                <div class="tableBox box01">
	                  <div class="no-data01">
				          <p>暂无任何学习课程信息！</p>
				      </div>
	                </div>
	              </div>
	            </div>  
	        </div>
			<%} %>
		</div>
	</div>
</div>
  </div>
  
<%@ include file="/skin/course01/jsp/footer.jsp"%>
</body>
</html>
