<!doctype html>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>扫码登录</title>
<link href="/weixin/loginpc/css/common.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function login(){
	document.loginForm.action = "/weixinLoginPcConfirm.app?type=1";
	document.loginForm.submit();
}
function logout(){
	document.loginForm.action = "/weixinLoginPcConfirm.app?type=0";
	document.loginForm.submit();
}
</script>
</head>
 
<body>
<form name="loginForm" method="post">
<img src="/weixin/loginpc/img/img.png" class="img">
<p class="p"><%=Constants.PRODUCT_NAME %>电脑端登录确认</p>
<a href="javascript:login();"><div class="Feedback_button">
	<p>确认登录</p>
</div></a>
<a href="javascript:logout();" class="login_a">取消</a>
<input type="hidden" name="userid" value="<bean:write name="userid"/>">
<input type="hidden" name="randomcode" value="<bean:write name="randomcode"/>">
</form>
</body>
</html>