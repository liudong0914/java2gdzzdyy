<!doctype html>
<%@page import="com.wkmk.weixin.mp.MpUtil"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.util.common.Constants"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>登录成功</title>
<link href="/weixin/loginpc/css/common.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
 
<body>

<div class="dl">
    <img src="/weixin/loginpc/img/Contentbox2_img.png" class="img11" />
    <p class="p1">电脑端已登录成功！</p>
</div>
<a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=MpUtil.APPID %>&redirect_uri=<%=MpUtil.HOMEPAGE %>/weixinMenu.app&response_type=code&scope=snsapi_userinfo&state=K1001_0000#wechat_redirect"><div class="Feedback_button">
	<p><%=Constants.PRODUCT_NAME %></p>
</div></a>
</body>
</html>