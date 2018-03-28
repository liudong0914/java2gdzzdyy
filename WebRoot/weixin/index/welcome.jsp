<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.weixin.mp.MpUtil"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
//苹果手机无法自动跳转，故显示简介手动触发按钮
window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=MpUtil.APPID %>&redirect_uri=<%=MpUtil.HOMEPAGE %>/weixinRegister.app&response_type=code&scope=snsapi_userinfo&state=account#wechat_redirect";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title><%=MpUtil.APPNAME %></title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<div id="container">
	<div class="container_welcome">
    	<div class="conrainer_welcome_font" style="font-size:14px;">
        	<p>进步学堂是一款智能化学习平台。进步学堂能够帮助学生实现个性化学习，辅助老师实现智能批改作业，同时可以帮助老师了解到全班每个学习的个性化学习需求，从而进行针对性的教学。</p>
        	<br />
			<p>进步学堂的图书，给广大师生提供线上智能平台+线下纸本图书的全新立体式学习模式。</p>
        </div>
    </div>
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