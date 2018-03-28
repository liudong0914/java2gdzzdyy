<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.date.DateTime"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>龙门作业宝-认证登录</title>
<meta name="keywords" content="龙门作业宝 龙门书局 龙门 启东作业本 启东中学作业本 黄冈小状元 黄冈小状元作业本 黄冈小状元达标卷 黄冈小状元数学小秘招 龙门专题 龙门名师 蓝卡英语 龙门工具书 三点一测 秒杀小题 课时作业 北京龙腾八方文化有限责任公司" />
<meta name="description" content="龙门作业宝 龙门书局 龙门 启东作业本 启东中学作业本 黄冈小状元 黄冈小状元作业本 黄冈小状元达标卷 黄冈小状元数学小秘招 龙门专题 龙门名师 蓝卡英语 龙门工具书 三点一测 秒杀小题 课时作业 北京龙腾八方文化有限责任公司 龙门书局的主要产品有：中小学同步类学习辅导书，产品有讲解类、练习类、中高考类。其中《黄冈小状元》、《三点一测》、《状元笔记》、《启东中学作业本》、《课时作业》等系列图书年年增补、年年修订，年年出新，成为千千万万学生有口皆碑的品牌，也成为了教辅图书的经典之作。《黄冈小状元》系列图书上市十余年，好评如潮，累计发行数千万册，是小学练习类最具市场影响力的图书" />
<link rel="stylesheet" href="/sys/login1/css/style.css" type="text/css"/>
</head>

<body>
<div class="box">
	<form name="loginForm" id="loginForm" action="/sysUserLoginAction.do?method=login1" method="post">
	<div class="content">
		<a href="/index.htm"><img src="/skin/zyb01/images/logo.png" /></a>
		<p class="content_ts" id="err_tips">${promptinfo }</p>
        <div class="content_input">
        	<img src="/sys/login1/images/icon2.png" />
            <input type="text" placeholder="用户名" class="search_input" maxlength="38" name="loginname" id="loginname" value="" onKeyPress="return loginname_onkeypress(event)">
        </div>
        <div class="content_input" style="margin-top:30px;">
        	<img src="/sys/login1/images/icon1.png" />
            <input type="password" placeholder="密码" class="search_input" maxlength="38" name="password" id="password" value="" onKeyPress="return password_onkeypress(event)">
        </div>
        <div class="content_yzm" >
            <input type="text" placeholder="请输入验证码" class="search_input1" maxlength="4" name="code" id="code" value="" onKeyPress="return code_onkeypress(event)">
            <img src="/sys/comm/randomcode.jsp" width="100" height="44"/>
        </div>
        <a href="javascript:login()"><div class="content_button">
            <span>登录</span>
        </div></a>
        <div class="content_img">
        	<img src="/sys/login1/images/img.jpg" />
        	<p>[微信扫一扫进入龙门作业宝]</p>
        </div>
	</div>
	</form>
	<div class="footer">
    	<div>
    		<span>Copyright © 2015-<%=DateTime.getDateYear() %> 龙门作业宝 版权所有</span>
    	</div>
    	<div>
    		<span>电子邮件：<a href="mailto:longmenzuoyebao@163.com">longmenzuoyebao@163.com</a>&nbsp;&nbsp;客户电话：010-64034373 (工作日:08:00 ~ 17:00)</span>
    	</div>
    	<div>
    		<span>技术支持：</span>
        	<a href="http://www.edutech.com.cn" target="_blank">北京师科阳光信息技术有限公司</a>
        	<span>&nbsp;&nbsp;京ICP备15064195号&nbsp;&nbsp;<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1260889959'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1260889959%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script></span>
    	</div>
	</div>	
</div>
<script>
function loginname_onkeypress(evt){
	evt = (evt) ? evt : ((window.event) ? window.event : "");
  	keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
  	if (keyCode == 13){         
    	keyCode=0;
    	document.getElementById("password").focus();
  	}  
}

function password_onkeypress(evt){
  	evt = (evt) ? evt : ((window.event) ? window.event : "");
  	keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
  	if (keyCode == 13){         
    	keyCode=0;
    	document.getElementById("code").focus();
  	}  
}

function code_onkeypress(evt){
  	evt = (evt) ? evt : ((window.event) ? window.event : "");
  	keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
  	if (keyCode == 13){
    	keyCode=0;
    	login();
  	}
}

function login() {
	var loginName = document.getElementById("loginname");
	if(!loginName.value || loginName.value == ""){
		document.getElementById("err_tips").innerHTML = "用户名不能为空!";
		return;
	}
	var password = document.getElementById("password");
	if(!password.value || password.value == ""){
		document.getElementById("err_tips").innerHTML = "密码不能为空!";
		return;
	}
	var code = document.getElementById("code");
	if(!code.value || code.value == ""){
		document.getElementById("err_tips").innerHTML = "验证码不能为空!";
		return;
	}

	document.loginForm.action="/sysUserLoginAction.do?method=login1";
	document.loginForm.submit();
}
</script>
</body>
</html>
