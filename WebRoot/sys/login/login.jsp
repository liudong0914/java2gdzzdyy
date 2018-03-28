<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<title>用户登录</title>
<link href="../../sys/login/skin/style.css" rel="stylesheet" type="text/css" id="skin"/>
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/login.js"></script>

<!--居中显示start-->
<script type="text/javascript" src="../../libs/js/method/center-plugin.js"></script>
<!--居中显示end-->

<!--修正IE6支持透明png图片start-->
<script type="text/javascript" src="../../libs/js/method/pngFix/supersleight.js"></script>
<!--修正IE6支持透明png图片end-->
<style>
/*提示信息*/	
#cursorMessageDiv {
	position: absolute;
	z-index: 99999;
	border: solid 1px #cc9933;
	background: #ffffcc;
	padding: 2px;
	margin: 0px;
	display: none;
	line-height:150%;
}
/*提示信息*/
</style>
</head>
<body >
	<div class="login_main">
		<div class="login_top">
			<!--背景LOGO <div class="login_title"></div> -->
		</div>
		<div class="login_middle">
			<div class="login_middleleft"></div>
			<div class="login_middlecenter">
					<form name="loginForm" id="loginForm" action="/sysUserLoginAction.do?method=login" class="login_form" method="post">
					<div class="login_user"><input type="text" name="loginname" id="loginname" value=""></div>
					<div class="login_pass"><input type="password" name="password" id="password" value=""></div>
					<div class="clear"></div>
					<div class="login_button">
						<div class="login_button_left"><input type="button" onclick="login()"/></div>
						<div class="login_button_right"><input type="reset" value=""/></div>
						<div class="clear"></div>
					</div>
					</form>
					<div class="login_info" style="display:none;"></div>
			</div>
			<div class="login_middleright"></div>
			<div class="clear"></div>
		</div>
		<div class="login_bottom">
			<div class="login_copyright">版权所有：http://www.wkmk.com</div>
		</div>
	</div>
<script>
	$(function(){
		//居中
		 $('.login_main').center();
		 document.getElementById("loginname").focus();
		 $("#loginname").keydown(function(event){
		 	if(event.keyCode==13){
				document.getElementById("password").focus();
			}
		 })
		 $("#password").keydown(function(event){
		 	if(event.keyCode==13){
				login()
			}
		 })
		 <logic:present name="promptinfo" scope="request">
		 <logic:notEmpty name="promptinfo" scope="request">
		 $(".login_info").html('<bean:write name="promptinfo" scope="request"/>');
		 $(".login_info").show();
		 </logic:notEmpty>
		 </logic:present>
	})

	//登录
	function login() {
		var errorMsg = "";
		var loginName = document.getElementById("loginname");
		var password = document.getElementById("password");
		if(!loginName.value){
			errorMsg += "&nbsp;&nbsp;用户名不能为空!";
		}
		if(!password.value){
			errorMsg += "&nbsp;&nbsp;密码不能为空!";
		}

		if(errorMsg != ""){
			$(".login_info").html(errorMsg);
			$(".login_info").show();
		}
		else{
			$(".login_info").html("&nbsp;&nbsp;登录中，正在验证用户名和密码...");
			document.loginForm.action="/sysUserLoginAction.do?method=login";
  			document.loginForm.submit();
		}
	}
</script>
</body>
</html>

