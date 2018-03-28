<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>修改支付密码</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	var code="";
	var smsid="";
	var counttime=60;
	//一分钟内不能再次获取验证码
	function countdown(){
		$("#rc").html(counttime+"");
		if(counttime<0){
			counttime=60;
			$("#rc").html("获取验证码");
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
			$("#msg").html("尚未绑定手机号码，请先绑定手机号码");
			location.href="/weixinAccountIndex.app?method=beforeUpdateMobile&userid=${userid}";
		}else{
			if(counttime==60){
				$.ajax({
			        type:"post",
			        url:"weixinAccountIndex.app?method=getCodeByAjax1",
			        data:"userid=${userid}",
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
	
	function savePassWord(){  
		var reg=/^[0-9]{6}$/;  
		if(${userinfo.paypassword ne '0'}){
			if($("#oldpwd").val()==""){
				$("#msg").html("请输入旧密码");
				$("#oldpwd").focus();
			}else if(!reg.test($("#oldpwd").val())){
				$("#msg").html("旧密码格式不正确，请输入6位数字支付密码。");
				$("#oldpwd").focus();
			}else if($("#newpwd").val()==""){
				$("#msg").html("请输入新密码");
				newpwd.focus();
			}else if(!reg.test($("#newpwd").val())){
				$("#msg").html("新密码格式不正确，请输入6位数字支付密码。");
				$("#newpwd").focus();
			}else if($("#repeatpwd").val()==""){
				$("#msg").html("请输入确认密码");
				$("#repeatpwd").focus();
			}else if($("#repeatpwd").val()!=$("#newpwd").val()){
				$("#msg").html("两次输入的密码不一致，请重新输入");
				$("#repeatpwd").focus();
			}else{
				location.href="/weixinAccountIndex.app?method=updateSavePwd&userid=${userid}&oldpassword="+$("#oldpwd").val()+"&newpassword="+$("#newpwd").val();
			}
		}else{
			if(${empty(userinfo.mobile)}){
				$("#msg").html("尚未绑定手机号码，请先绑定手机号码");
				location.href="/weixinAccountIndex.app?method=beforeUpdateMobile&userid=${userid}";
			}else{
				if($("#newpwd").val()==""){
					$("#msg").html("请输入新密码");
					newpwd.focus();
				}else if(!reg.test($("#newpwd").val())){
					$("#msg").html("新密码格式不正确，请输入6位数字支付密码。");
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
					location.href="/weixinAccountIndex.app?method=updateSavePwd&smsid="+smsid+"&userid=${userid}&newpassword="+$("#newpwd").val();
				}
			}
		}
	}  
	
	window.onload=function(){
		if(${!empty(errmsg)}){
			$("#msg").html("${errmsg}");
			document.getElementById("oldpwd").focus();
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
    	<p class="search_wk_p">修改支付密码</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div class="password">
<logic:notEqual value="0" name="userinfo" property="paypassword">
	<div class="password_input">
        <input type="password" placeholder="旧密码" id="oldpwd" name="oldpwd" style="color:#000;font-size:16px;"/>
    </div>
</logic:notEqual>   
    <div class="password_input">
        <input type="password" placeholder="新密码" id="newpwd" name="newpwd" style="color:#000;font-size:16px;"/>
    </div>
	<div class="password_input">
        <input type="password" placeholder="确认新密码" id="repeatpwd" name="repeatpwd" style="color:#000;font-size:16px;"/>
    </div>
<logic:equal value="0" name="userinfo" property="paypassword">
    <div class="password_input">
        <input type="text" value="请输入短信校验码" id="code"  onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}" style="font-size:16px;"/>
        <a href="javascript:getVerificationcode()" class="password_input_a" id="rc">获取校验码</a>
    </div>
</logic:equal>
    <span id="msg" style="color:red;font-size:14px;"></span>
     <p class="password_p">6位数字支付密码修改</p>
    <div class="password_a">
    	<a href="javascript:savePassWord()">提交</a>
    </div>
</div>
</body>
</html>