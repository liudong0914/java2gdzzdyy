<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.util.encrypt.DES"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title><logic:notEmpty name="redirecturl">绑定手机号</logic:notEmpty><logic:empty name="redirecturl">变更手机号</logic:empty></title>
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
		$("#msg").html("");
		var mobile="";
		if(${empty(userinfo.mobile)}){
			mobile=$("#mobile").val();
		}else if($("#mobile").val()=="${userinfo.mobile}"){
			$("#msg").html("请输入需要变更的手机号码");
			return;
		}else{
			mobile="${userinfo.mobile}";
		}
		if(counttime==60){
			if(mobile!="请输入手机号码"){
				$.ajax({
			        type:"post",
			        url:"weixinAccountIndex.app?method=getCodeByAjax",
			        data:"userid=${userid}&mobile="+mobile,
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
			}else{
				$("#msg").html("请输入手机号码");
				$("#mobile").focus();
			}
		}
	} 
	
	function saveMobile(){  
		var reg=/^1(3|4|5|7|8)\d{9}$/;
		if($("#mobile").val()=="请输入手机号码"){
			$("#msg").html("请输入手机号码");
			$("#mobile").focus();
		}else if(!reg.test(mobile.value)){
			$("#msg").html("手机号码格式无效");
			$("#mobile").focus();
		}else if($("#code").val()=="请输入短信校验码"){
	    	$("#msg").html("请输入校验码");
	    	$("#code").focus();
	    }else if($("#code").val()!=code){
	    	$("#msg").html("校验码不正确");
	    	$("#code").focus();
	    }else{
	    	$.ajax({
		        type:"post",
		        url:"/weixinAccountIndex.app?method=saveMobile",
		        data:"smsid="+smsid+"&userid=${userid}&mobile="+$("#mobile").val(),
		        success:function(msg){
		        	if(msg=="1"){
		        		<%
		        		String redirecturl = (String)request.getAttribute("redirecturl");
		        		if(redirecturl != null && !"".equals(redirecturl)){
		        		%>
		        		alert("手机号码绑定成功");
		        		location.href = "/weixinAccountIndex.app?method=beforeUpdatePwd&userid=${userid}&redirecturl=<%=redirecturl %>";
		        		<%}else{%>
		        		alert("手机号码变更成功");
	    				location.href="/weixinAccountIndex.app?method=userInfoManager&userid=${userid}";
		        		<%}%>
	    			}else if(msg=="2"){
	    				$("#msg").html("校验码已失效~");
	    				$("#code").focus();
	    			}
				}
			});			
		}
	}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=userInfoManager&userid=${userid}">
		<div  class="search_left">
	    	<img src="/weixin/images/a01.png" class="search_class" />
	    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p"><logic:notEmpty name="redirecturl">绑定手机号</logic:notEmpty><logic:empty name="redirecturl">变更手机号</logic:empty></p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div class="password">
	<div class="password_input">
        <input type="text" value="${empty(userinfo.mobile)?'请输入手机号码':userinfo.mobile}" id="mobile"  onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}"  style="font-size:16px;"/>
    </div>
    <div class="password_input">
        <input type="text" value="请输入短信校验码" id="code"  onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}" style="font-size:16px;"/>
        <a href="javascript:getVerificationcode()" class="password_input_a" id="rc">获取校验码</a>
    </div>
    <span id="msg" style="color:red;font-size:14px;"></span>
     <p class="password_p" id="msg">未绑定手机号码的账号，可直接验证<br/>已绑定手机号码的账号需验证原手机号码</p>
    <div class="password_a">
    	<a href="javascript:saveMobile()">提交</a>
    </div>
    <a href="/weixinAccountIndex.app?method=serverCenter&userid=${userid}" class="wfjs">无法接收短信</a>
</div>
</body>
</html>