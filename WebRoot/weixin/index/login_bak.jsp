<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>登录</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<script src="/weixin/js/jquery-1.10.2.js" type="text/javascript"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function login(){
    document.loginForm.action="/weixinAccountIndex.app?method=login";
    document.loginForm.submit();
}
function register(){
    document.loginForm.action="/weixinAccountIndex.app?method=register";
    document.loginForm.submit();
}
</script>

<body>
<form name="registerForm" method="post" action="/weixinAccountIndex.app?method=register">
<input type="hidden" name="openid" value="${openid }"/>
<input type="hidden" name="headimgurl" value="${headimgurl }"/>
<input type="hidden" name="nickname" value="${nickname }"/>
<input type="hidden" name="sex" value="${sex }"/>
<input type="hidden" name="scanResult" value="${scanResult }"/>
</form>
<script type="text/javascript">
$(function () {
   document.registerForm.submit();
});
</script>

<form name="loginForm" method="post">
<div id="content">
	<div class="content_user">
    	<img src="${headimgurl }" width="120" height="120"/>
        <p>${nickname }</p>
    </div>
    <div class="content_input">
        <img src="/weixin/images/icon1.png" class="content_input_img" />
		<input type="text" class="input" name="loginname" value=""/>
    </div>
    <div class="content_input">
        <img src="/weixin/images/icon2.png" class="content_input_img" />
		<input type="password" class="input" name="password" value=""/>
    </div>
    <div class="content_botton">
    	<a href="javascript:login()">
   			<div class="botton1">
    			<p>登录</p>
    		</div>
    	</a>
    	<a href="javascript:register()">
    		<div class="botton2">
    			<p>注册</p>
    		</div>
    	</a>
    </div>
    <logic:present name="errmsg"><div class="tips"><bean:write name="errmsg" /></div></logic:present>
</div>
<input type="hidden" name="openid" value="${openid }"/>
<input type="hidden" name="headimgurl" value="${headimgurl }"/>
<input type="hidden" name="nickname" value="${nickname }"/>
<input type="hidden" name="sex" value="${sex }"/>
<input type="hidden" name="scanResult" value="${scanResult }"/>
</form>
</body>
</html>