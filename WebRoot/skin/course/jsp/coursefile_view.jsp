<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.edu.bo.EduCourseFile"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${model.filename}--在线预览</title>
<script type="text/javascript" src="/libs/js/flexpaper_flash.js"></script>
</head>

<body>
<%
EduCourseFile model = (EduCourseFile)request.getAttribute("model"); 
if("1".equals(model.getConvertstatus())){
%>
	<iframe width="99%" height="589px" src="/libs/pdfshow/web/viewer.html?file=/upload/${model.pdfpath}" ></iframe>
<%}else if("2".equals(model.getConvertstatus())){ %>
<center style="text-align:center; font-size:26px; color:red; height:300px; padding-top:200px;">
文档转码失败，当前文档无法在线预览！
</center>
<%}else{ %>
<center style="text-align:center; font-size:26px; color:green; height:300px; padding-top:200px;">
文档正在转码中，当前文档无法在线预览！
</center>
<%} %>
</body>
</html>
