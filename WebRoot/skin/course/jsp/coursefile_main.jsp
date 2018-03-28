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

<link type="text/css" href="/skin/course/css/style.css" rel="stylesheet">
<script type="text/javascript" src="/libs/js/sk/iframe.js"></script>
</head>

<body>
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
<%@ include file="left.jsp" %>
<div class="rightCon">
	<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">课程资源</h3>
		</div>
		<div class="mainCon mainCon01">
			<table width="100%">
			<tr>
				<!--左侧区域start-->
				<td class="ver01" style="position: relative;">
					<div class="box3" overflow="hidden" showStatus="false" panelTitle="树列表" style="margin-top:0px; position: absolute; top: 0px">
					 	<div class="cusBoxContent" id="cusBoxContent" style="width:130px;">
					  		<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmleft name=rfrmleft onload="SetCwinHeight('rfrmleft', 600)" src="/courseCenter.do?method=courseFileTree&mark=${mark }&courseid=${courseid}" allowTransparency="true"></IFRAME>
					  	</div>
				  	</div>
				</td>
				<!--左侧区域end-->
				
				<!--右侧区域start-->
				<td width="85%" class="ver01" >
					<div id="rfrmrightContent" style="margin: 0;padding: 0 5px 0 0;">
						<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmright name=rfrmright onload="SetCwinHeight('rfrmright', 600)" src="/sysLayoutAction.do?method=welcome" allowTransparency="true"></IFRAME>
					</div>
				</td>
				<!--右侧区域end-->
			</tr>
			</table>
		</div>
	</div>
</div>
</div>
  </div>
  
<%@ include file="/skin/course01/jsp/footer.jsp"%>
</body>
</html>
