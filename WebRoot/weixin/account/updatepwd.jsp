<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.util.encrypt.DES"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>设置支付密码</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	var code="";
	var smsid="";
	var counttime=60;
	//一分钟内不能再次获取验证码
	function countdown(){
		$("#rc").html(counttime+"秒");
		if(counttime<0){
			counttime=60;
			$("#rc").html("重新获取");
			$("#rc").attr("href","javascript:getVerificationcode()");
		}else{
			$("#rc").attr("href","javascript:");
			setTimeout("countdown()", 1000);
			counttime--;
		}	
	}
	
	//获取验证码
	function getVerificationcode() {
		if(${empty(userinfo.mobile)}){
			//$("#msg").html("尚未绑定手机号码，请先绑定手机号码");
			alert("尚未绑定手机号码，请先绑定手机号码");
			location.href="/weixinAccountIndex.app?method=beforeUpdateMobile&userid=${userid}&redirecturl=${redirecturl}";
		}else{
			var reg=/^[0-9]{6}$/;
			if($("#newpwd").val()==""){
				$("#msg").html("请输入新密码");
				newpwd.focus();
			}else if(!reg.test($("#newpwd").val())){
				$("#msg").html("新密码格式不正确，请输入6位数字支付密码。");
				$("#newpwd").focus();
			}else if($("#newpwd").val()=="000000" || $("#newpwd").val()=="111111" || $("#newpwd").val()=="222222" || $("#newpwd").val()=="333333" || $("#newpwd").val()=="444444" || $("#newpwd").val()=="555555" || $("#newpwd").val()=="666666" || $("#newpwd").val()=="777777" || $("#newpwd").val()=="888888" || $("#newpwd").val()=="999999" || $("#newpwd").val()=="123456" || $("#newpwd").val()=="654321"){
				$("#msg").html("支付密码过于简单，请重新输入。");
				$("#newpwd").focus();
			}else if($("#repeatpwd").val()==""){
				$("#msg").html("请输入确认密码");
				$("#repeatpwd").focus();
			}else if($("#repeatpwd").val()!=$("#newpwd").val()){
				$("#msg").html("两次输入的密码不一致，请重新输入");
				$("#repeatpwd").focus();
			}else{
				$("#msg").html("");
				if(counttime==60){
					$.ajax({
				        type:"post",
				        url:"weixinAccountIndex.app?method=getCodeByAjax1",
				        data:"&userid=${userid}",
				        success:function(data){
				        	if(data!=""){
				        		var obj=eval("("+data+")");
								if(obj.result=="1"){
									smsid=obj.smsid;
									code=obj.sixrandom;
									countdown();
								}else if(obj.result=="-1"){
									$("#msg").html("每天最多只能发送10次校验码");
								}else if(obj.result=="0"){
									$("#msg").html("校验码发送失败");
								}
				        	}
						}
					});
				}
			}
		}
	} 
	
	function savePassWord(){  
		var reg=/^[0-9]{6}$/;  
		if(${empty(userinfo.mobile)}){
			//$("#msg").html("尚未绑定手机号码，请先绑定手机号码");
			alert("尚未绑定手机号码，请先绑定手机号码");
			location.href="/weixinAccountIndex.app?method=beforeUpdateMobile&userid=${userid}&redirecturl=${redirecturl}";
		}else{
			if($("#newpwd").val()==""){
				$("#msg").html("请输入新密码");
				newpwd.focus();
			}else if(!reg.test($("#newpwd").val())){
				$("#msg").html("新密码格式不正确，请输入6位数字支付密码。");
				$("#newpwd").focus();
			}else if($("#newpwd").val()=="000000" || $("#newpwd").val()=="111111" || $("#newpwd").val()=="222222" || $("#newpwd").val()=="333333" || $("#newpwd").val()=="444444" || $("#newpwd").val()=="555555" || $("#newpwd").val()=="666666" || $("#newpwd").val()=="777777" || $("#newpwd").val()=="888888" || $("#newpwd").val()=="999999" || $("#newpwd").val()=="123456" || $("#newpwd").val()=="654321"){
				$("#msg").html("支付密码过于简单，请重新输入。");
				$("#newpwd").focus();
			}else if($("#repeatpwd").val()==""){
				$("#msg").html("请输入确认密码");
				$("#repeatpwd").focus();
			}else if($("#repeatpwd").val()!=$("#newpwd").val()){
				$("#msg").html("两次输入的密码不一致，请重新输入");
				$("#repeatpwd").focus();
			}else if($("#code").val()=="请输入短信校验码"){
				$("#msg").html("请输入短信校验码");
				$("#code").focus();
			}else if($("#code").val()!=code){
				$("#msg").html("校验码不正确");
				$("#code").focus();
			}else{
				$("#msg").html("");
				$.ajax({
			        type:"post",
			        url:"/weixinAccountIndex.app?method=updateSavePwd",
			        data:"smsid="+smsid+"&userid=${userid}&newpassword="+$("#newpwd").val(),
			        success:function(data){
			        	if(data=="1"){
			        		alert("支付密码修改成功~");
			        		<%
			        		String redirecturl = (String)request.getAttribute("redirecturl");
			        		if(redirecturl != null && !"".equals(redirecturl)){
			        			redirecturl = DES.getDecryptPwd(redirecturl);
			        		%>
			        		location.href = '<%=redirecturl %>';
			        		<%}else{%>
			        		location.href="/weixinAccountIndex.app?method=userInfoManager&userid=${userid}";
			        		<%}%>
			        	}else if(data=="1"){
			        		$("#msg").html("校验码已超时");
							$("#code").focus();
			        	}
					}
				});
			}
		}
	}  
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=userInfoManager&userid=${userid}"><div  class="search_left">
				<img src="/weixin/images/a01.png" class="search_class" />
			</div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">设置支付密码</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div class="password">
    <div class="password_input">
        <input type="password" placeholder="新密码" id="newpwd" name="newpwd" style="color:#000;font-size:16px;" maxlength="6"/>
    </div>
	<div class="password_input">
        <input type="password" placeholder="确认新密码" id="repeatpwd" name="repeatpwd" style="color:#000;font-size:16px;" maxlength="6"/>
    </div>
    <div class="password_input">
        <input type="text" value="请输入短信校验码" id="code"  onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}" style="font-size:16px;"/>
        <a href="javascript:getVerificationcode()" class="password_input_a" id="rc">获取校验码</a>
    </div>
    <span id="msg" style="color:red;font-size:14px;"></span>
     <p class="password_p">支付密码为6位数字</p>
    <div class="password_a">
    	<a href="javascript:savePassWord()">提交</a>
    </div>
</div>
</body>
</html>