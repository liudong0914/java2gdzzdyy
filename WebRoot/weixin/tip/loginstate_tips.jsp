<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>用户状态提示</title>
<style type="text/css">
.font{ width:90%; height:auto; margin:0 auto; text-align:center; font-size:18px; padding-top:10px; }
.code{ width:344px; height:380px; margin:0 auto; padding-top:10px; text-align:center; font-size:16px; line-height:16px; }
.font_p{ color:#ee9b00; }
.code p{ margin-top:-10px; }
</style>
</head>

<body>
<div class="font" style="margin-top:50px;">
    <span class="font_p">${promptinfo }</span>
</div>
<%
String promptinfo = (String)request.getAttribute("promptinfo");
String status = (String)request.getAttribute("status");
if(!"2".equals(status) || promptinfo.indexOf("您的账号已被禁用，请与管理员联系!") != -1){
%>
<div class="code" style="margin-top:20px;">
    <p>客服电话：010-64034373</p>
    <p>工作日：08:00 ~ 17:00</p>
</div>
<%} %>
</body>
</html>