<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.weixin.mp.MpUtil"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title><%=MpUtil.APPNAME %></title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body style="background-color:#f3f3f3;">
<div id="container">
	<div class="container_welcome">
    	<div class="conrainer_welcome_font">
        	<p>取消绑定后，您可以重新绑定新的账号，也可以用新的微信号与此账号绑定，您真的要取消绑定账号吗？</p>
        </div>
    </div>
    <div style="width:90%;margin:0 auto;"><img src="/upload/banner.jpg" border="0" style="margin: 0.5em auto 0.5em auto; padding: 0; width:100%;height:50%;"/></div>
	<div class="container_welcome_moble">
    	<a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=MpUtil.APPID %>&redirect_uri=<%=MpUtil.HOMEPAGE %>/weixinRegister.app&response_type=code&scope=snsapi_userinfo&state=account#wechat_redirect">
        	<div class="container_welcome_moble0">
    			<span>开始绑定</span>
        	</div>
        </a>
    </div>
</div>

</body>
</html>