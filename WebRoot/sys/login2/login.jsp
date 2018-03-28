<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8" %>
<%@page import="com.util.date.DateTime"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Constants.PRODUCT_NAME %>-认证登录</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" type="text/css" href="/sys/login2/css/common.css" />
<link rel="stylesheet" type="text/css" href="/sys/login2/css/header.css" />
<script type="text/javascript" src="/sys/login2/js/jquery1.9.js"></script>
<script type="text/javascript" src="/sys/login2/js/jquery.qrcode.min.js"></script>
<script language="javaScript">
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
    	document.getElementById("validate").focus();
  	}  
}
function code_onkeypress(evt){
  	evt = (evt) ? evt : ((window.event) ? window.event : "");
  	keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
  	if (keyCode == 13){
    	keyCode=0;
    	indexLoginAction();
  	}
}
function indexLoginAction(){
	var loginname = document.getElementById("loginname");
	if(loginname.value == '' || loginname.value == '用户名'){
		document.getElementById("login_tip").innerHTML = "请输入用户名!";
		loginname.select();
		return;
	}
	var password = document.getElementById("password");
	if(password.value == '' || password.value == '********'){
		document.getElementById("login_tip").innerHTML = "请输入密码!";
		password.select();
		return;
	}
  	obj = document.loginForm;
  	obj.action='/plogin.do?method=sklogin';
  	obj.submit();
}

function init(){
	<logic:equal value="1" name="r" scope="request">
	document.getElementById("validate").focus();
	</logic:equal>
	<logic:notEqual value="1" name="r" scope="request">
	document.getElementById("loginname").focus();
	</logic:notEqual>
}

function setTab(name,cursel,n){
	for(var i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		var con=document.getElementById("con_"+name+"_"+i);
		menu.className=i==cursel?"hover":"";
		con.style.display=i==cursel?"block":"none";
	}
	if(cursel == "2"){
		scanLogin();
	}
}
</script>
</head>

<body onload="init()">
<div id="header1">
  <div class="logo">
    <a href="/index.html"></a>
  </div>
  <div class="seach">
    <div class="login">
      <a href="/index.html">首 页</a>
      <!-- 
      <span class="mar_1">|</span>
      <span style="padding-left:10px;">客服电话：010-64034373 (工作日:08:00 ~ 17:00)</span>
       -->
    </div>
  </div>  
</div>
<div id="login_main">
  <div class="login_main1">
    <div class="login_box "  id="Tab1">
      <div class="Menubox login_top">
        <ul>
            <li id="one1" onclick="setTab('one',1,2)"  class="hover">账户登录</li>
            <li id="one2" onclick="setTab('one',2,2)" >扫码登录</li>
        </ul>
     </div>
      <div class="Contentbox">
          <div class="login_bottom Contentbox hover" id="con_one_1">
            <form name="loginForm" method="post">
	          <dl class="login_tip" id="login_tip"><logic:notPresent name="promptinfo" scope="request">&nbsp;</logic:notPresent><logic:present name="promptinfo" scope="request"><bean:write name="promptinfo" scope="request"/></logic:present></dl>
	          <span class="login_pad">
	            <label class="user"></label>
	            <input type="text" name="loginname" id="loginname" onKeyPress="return loginname_onkeypress(event)" value="${loginname }" class="input_1" />
	          </span>
	          <span class="login_pad">
	            <label class="password"></label>
	            <input type="password" name="password" id="password" onKeyPress="return password_onkeypress(event)" value="${password }" class="input_1" />
	          </span>
	          <span class="login_pad">
	            <label class="code"></label>
	            <input type="text" name="validate" id="validate" maxlength="4" onKeyPress="return code_onkeypress(event)" value="" class="input_2" />
	            <img src="/sys/comm/randomcode.jsp" width="79" height="37" onclick="this.src='/sys/comm/randomcode.jsp?d='+Math.random();"/>
	          </span>
	          <span class="login_pad1">
	            <input type="checkbox" name="r" value="1" <logic:equal value="1" name="r" scope="request">checked="checked"</logic:equal>/>
	            <label>记住密码（有效期30天）</label>
	          </span>
	          <span class="login_pad">
	            <input type="button" value="登录" class="sub_1" onclick="indexLoginAction()"/>
	          </span>
	        <input type="hidden" name="redirecturl" value="${redirecturl }"/>
			<input type="hidden" name="hiddenpwd" value="${hiddenpwd }">
	        </form>
          </div>
          <div class="login_bottom Contentbox" id="con_one_2" style="display:none">
          	  <div class="Contentbox2_img_box" id="qrcode"></div>
              <p class="Contentbox2_p" >打开手机微信客户端右上角+，扫描二维码</p>
          </div>
        </div>
    </div>
  </div>
</div>
<div id="login_footer">
  <span>Copyright © 2015-<%=DateTime.getDateYear() %> 中国职业核心能力 版权所有</span><br />
  <!-- <span>电子邮件：<a href="mailto:longmenzuoyebao@163.com">longmenzuoyebao@163.com</a>&nbsp;&nbsp;客户电话：010-64034373 (工作日:08:00 ~ 17:00)</span><br /> -->
  <span>技术支持：<a href="http://www.edutech.com.cn" target="_blank">北京师科阳光信息技术有限公司</a>&nbsp;&nbsp;京ICP备15064195号&nbsp;&nbsp;<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1260889959'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1260889959%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script></span>
</div>
<script type="text/javascript">
var randomcode = "";
var seconds = 0;

var ScanStatusId, LoginStatusId;
function getScanStatus(){
	$.ajax({
        type: "get",
        url: "/plogin.do?method=getScanStatus&randomcode=" + randomcode,
        dataType: "text",
        async: false,
        success: function(data){
        	if(data != ""){
        		window.clearInterval(ScanStatusId); 
        		if(data == "1"){
        			document.getElementById("con_one_2").innerHTML = '<img src="/sys/login2/images/Contentbox2_img.png" class="conentbox3_img" /><p class="Contentbox3_p" >扫描成功！</p><p class="Contentbox2_p" >请勿刷新此页面，按照手机提示操作</p>';
        			
        			LoginStatusId = window.setInterval(getLoginStatus, 2000);
        		}else if(data == "0"){
        			document.getElementById("qrcode").innerHTML = '<div class="Contentbox2_img_box_top"><p class="Contentbox2_img_box_top1">二维码失效</p><a  href="javascript:scanLogin();" class="Contentbox2_img_box_top2">刷新</a></div><img src="/sys/login2/images/qrcode.png" class="Contentbox2_img" />';
        		}
        	}
        }
    });
}

function getLoginStatus(){
	$.ajax({
        type: "get",
        url: "/plogin.do?method=getLoginStatus&randomcode=" + randomcode,
        dataType: "text",
        async: false,
        success: function(data){
        	if(data != ""){
        		window.clearInterval(LoginStatusId);
        		if(data == "0"){
        			document.getElementById("con_one_2").innerHTML = '<div class="Contentbox2_img_box" id="qrcode"><div class="Contentbox2_img_box_top"><p class="Contentbox2_img_box_top1">二维码失效</p><a  href="javascript:scanLogin();" class="Contentbox2_img_box_top2">刷新</a></div><img src="/sys/login2/images/qrcode.png" class="Contentbox2_img" /></div><p class="Contentbox2_p" >打开手机微信客户端右上角+，扫描二维码</p>';
        		}else{
            		document.loginForm.action='/plogin.do?method=scanAutoLogin&userid=' + data;
            		document.loginForm.submit();
        		}
        	}
        }
    });
}

function scanLogin(){
	randomcode = "";
	seconds = 0;
	var qrcodeStr = "";
	$.ajax({
        type: "get",
        url: "/plogin.do?method=getQrCodeStr&ram=" + Math.random(),
        dataType: "text",
        async: false,
        success: function(data){
        	if(data != ""){
        		var datas = data.split(";");
        		qrcodeStr = datas[0];
        		randomcode = datas[1];
        	}
        }
    });
	
	document.getElementById("qrcode").innerHTML = "";
	jQuery('#qrcode').qrcode({width:175,height:175,text:qrcodeStr});
	
	ScanStatusId = window.setInterval(getScanStatus, 2000);
}
</script>
</body>
</html>