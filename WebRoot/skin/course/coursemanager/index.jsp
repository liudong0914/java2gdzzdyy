 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseClass"%>
<%@page import="com.util.date.DateTime"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${eduCourseInfo.title }-课程管理</title>
<!--框架必需start-->
<link href="/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link href="/libs/skins/blue/style.css" rel="stylesheet" type="text/css" id="theme" themeColor="blue"/>
<link href="/sys/layout/skin/style.css" rel="stylesheet" type="text/css" id="skin" skinPath="sys/layout/skin/"/>
<script type="text/javascript" src="/libs/js/jquery.js"></script>
<!--框架必需end-->

<!--弹窗组件start-->
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
<link type="text/css" href="/skin/course/css/style.css" rel="stylesheet">
<script type="text/javascript" src="/libs/js/sk/iframe.js"></script>
<script type="text/javascript">
function gotoUrl(url, curid){
	document.getElementById('frmright').src = url;
	for(var i=1; i<=8; i++){
		if(document.getElementById('lmenu_' + i)){
			document.getElementById('lmenu_' + i).className = 'link';
		}
	}
	document.getElementById('lmenu_' + curid).className = 'homeLink1';
}
function commitCourseClass(courseclassid){
	var str = "提交课程批次需要管理员审核通过后才可使用，确定提交？";
	Dialog.confirm(str,function(){
		window.document.location.href = "/courseCenter.do?method=commitCourseClass&flag=1&courseclassid=" + courseclassid;
	});
}
function recommitCourseClass(courseclassid){
	var str = "重新提交课程批次需要管理员审核通过后才可使用，确定提交？";
	Dialog.confirm(str,function(){
		window.document.location.href = "/courseCenter.do?method=commitCourseClass&flag=1&tag=1&courseclassid=" + courseclassid;
	});
}
</script>
</head>
<body>
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
	<div class="crumbs mb10">位置导航： <a href="/courseCenter.do?method=index">个人中心</a>&nbsp;&gt;&nbsp;<a href="/courseCenter.do?method=courseList&mark=2">课程管理</a>&nbsp;&gt;&nbsp;<span id="showText">${eduCourseInfo.title }</span> </div>
	<div class="layout-hd mb20" style="margin-bottom:20px;">
	  <div class="hdCon clearfix">
	    <div class="imgs fl">
	    	<%
	    	EduCourseClass ecc = (EduCourseClass)request.getAttribute("eduCourseClass");
	    	if("0".equals(ecc.getStatus())){
	    	%>
	   		<i class="state" style="background:#fa5678;">未提交</i> 
	   		<%}else if("1".equals(ecc.getStatus())){ %>
	    		<%if(DateTime.getCompareToDate(ecc.getEnddate(), DateTime.getDate()) < 0){ %>
				<i class="state" style="background:#777;">已结束</i> 
				<%}else{ %>
				<i class="state do-ing">进行中</i>
				<%} %>
	   		<%}else if("2".equals(ecc.getStatus())){ %>
	   		<i class="state" style="background:#5678bc;">审核中</i> 
	   		<%}else if("3".equals(ecc.getStatus())){ %>
	   		<i class="state" style="background:#000;">未通过</i> 
	   		<%} %>
	    	<img src="/upload/${eduCourseInfo.sketch }" style="cursor:pointer;" onclick="window.open('/course/${eduCourseInfo.courseid }.htm')"> 
	    </div>
	    <div class="text-info">
	      <h3 class="tit">${eduCourseInfo.title }</h3>
	      <p class="sec-text" style="padding-top:15px;">${eduCourseClass.classname }</p>
	      <p class="sec-text" style="padding-top:15px;">共${eduCourseInfo.classhour }课时</p>
	    </div>
	    <div style="float:right;">
	    <%if("0".equals(ecc.getStatus())){ %>
	    <input class="btn btn-text btn-blue tipsUp" value="提交课程" onclick="commitCourseClass('<%=ecc.getCourseclassid() %>')" type="button">
	    <%}else if("3".equals(ecc.getStatus())){ %>
	   	<input class="btn btn-text btn-blue tipsUp" value="重新提交" onclick="recommitCourseClass('<%=ecc.getCourseclassid() %>')" type="button">
	   	<%} %>
	    </div>
	  </div>
	</div>

	<div class="leftCon">
		<%
		String isAuhtor = (String)session.getAttribute("isAuhtor");
		List moduleidList = (List)session.getAttribute("moduleidList");
		String tag = (String)request.getAttribute("tag");
		String righturl = "/sysLayoutAction.do?method=welcome";
		%>
		<%if("1".equals(isAuhtor) || moduleidList.contains("1")){ %>
		<div <%if("1".equals(tag)){righturl = "/courseManager.do?method=courseInfo"; %>class="homeLink1"<%}else{ %>class="link"<%} %> id="lmenu_1">
			<a href="javascript:gotoUrl('/courseManager.do?method=courseInfo', 1)">课程信息</a>
		</div>
		<%} %>
		<%if("1".equals(isAuhtor) || moduleidList.contains("2")){ %>
		<div <%if("2".equals(tag)){righturl = "/courseManager.do?method=columnMain"; %>class="homeLink1"<%}else{ %>class="link"<%} %> id="lmenu_2">
			<a href="javascript:gotoUrl('/courseManager.do?method=columnMain', 2)">课程目录</a>
		</div>
		<%} %>
		<%if("1".equals(isAuhtor) || moduleidList.contains("3")){ %>
		<div <%if("3".equals(tag)){righturl = "/eduCourseFilmAction.do?method=filmMain"; %>class="homeLink1"<%}else{ %>class="link"<%} %> id="lmenu_3">
			<a href="javascript:gotoUrl('/eduCourseFilmAction.do?method=filmMain', 3)">微课管理</a>
		</div>
		<%} %>
		<%if("1".equals(isAuhtor) || moduleidList.contains("4")){ %>
		<div <%if("4".equals(tag)){righturl = "/eduCourseFileAction.do?method=main"; %>class="homeLink1"<%}else{ %>class="link"<%} %> id="lmenu_4">
			<a href="javascript:gotoUrl('/eduCourseFileAction.do?method=main', 4)">课程资源</a>
		</div>
		<%} %>
		<%if("1".equals(isAuhtor) || moduleidList.contains("5")){ %>
		<div <%if("5".equals(tag)){righturl = "/eduCourseUserAction.do?method=teacherList"; %>class="homeLink1"<%}else{ %>class="link"<%} %> id="lmenu_5">
			<a href="javascript:gotoUrl('/eduCourseUserAction.do?method=teacherList', 5)">助教管理</a>
		</div>
		<%} %>
		<%if("1".equals(isAuhtor) || moduleidList.contains("6")){ %>
		<div <%if("6".equals(tag)){righturl = "/eduCourseUserAction.do?method=studentList"; %>class="homeLink1"<%}else{ %>class="link"<%} %> id="lmenu_6">
			<a href="javascript:gotoUrl('/eduCourseUserAction.do?method=studentList', 6)">学员管理</a>
		</div>
		<%} %>
		<%--if("1".equals(isAuhtor) || moduleidList.contains("7")){ %>
		<div <%if("7".equals(tag)){righturl = ""; %>class="homeLink1"<%}else{ %>class="link"<%} %> style="border-bottom:1px solid #dedede;" id="lmenu_7">
			<a href="javascript:gotoUrl('', 7)">课程答疑</a>
		</div>
		<%} --%>
		<%if("1".equals(isAuhtor) || moduleidList.contains("8")){ %>
		<div <%if("8".equals(tag)){righturl = "/eduCourseCommentAction.do?method=list"; %>class="homeLink1"<%}else{ %>class="link"<%} %> style="border-bottom:1px solid #dedede;" id="lmenu_8">
			<a href="javascript:gotoUrl('/eduCourseCommentAction.do?method=list', 8)">课程评价</a>
		</div>
		<%} %>
	</div>
	
	<div class="rightCon">
		<iframe id="frmright" name="frmright" width="100%" height="500" onload="SetCwinHeight('frmright', 1200)" frameborder="0"  marginheight="0" marginwidth="0" scrolling=auto src="<%=righturl %>" allowTransparency="true"></iframe>
	</div>
</div>

<!--浏览器resize事件修正start-->
<div id="resizeFix"></div>
<!--浏览器resize事件修正end-->

<!--载进度条start-->
<div class="progressBg" id="progress" style="display:none;"><div class="progressBar"></div></div>
<!--载进度条end-->

<%@ include file="/skin/course01/jsp/footer.jsp"%>
</body>
</html>
