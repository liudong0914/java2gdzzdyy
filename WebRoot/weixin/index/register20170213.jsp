<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>注册</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function register(){
	var username = document.getElementById("username").value;
	if(username == ""){
		document.getElementById("tips").innerHTML = "请填写真实姓名！";
		return;
	}
	//点击支付弹出蒙板，避免重复点击
    document.getElementById("div_hidden").style.display = "block";
    document.registerForm.action="/weixinAccountIndex.app?method=addRegister";
    document.registerForm.submit();
}
function selectSchool(){
	document.registerForm.action="/weixinAccountIndex.app?method=selectSchool";
    document.registerForm.submit();
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<form name="registerForm" method="post">
<div id="container">
	<div class="tips" style="margin-left:2px;width:100%;margin-bottom:-15px;">说明: 身份绑定后不可修改，请认真选择注册用户身份。</div>
	<div class="container_1">
    	<div class="container_1_size" style="margin-left:18px;">身份：
       		<label class="radio">教师</label><input type="radio" name="usertype" value="1" class="container_1_radio" <logic:equal value="1" name="usertype">checked="checked"</logic:equal>/>
           	<label class="label">学生</label><input type="radio" name="usertype" value="2"  class="container_1_radio" <logic:equal value="2" name="usertype">checked="checked"</logic:equal>/>
        </div>
    </div>
    <div class="container_1">
    	<div class="container_1_size" style="margin-left:18px;">学段：
       		<label class="radio">初中</label><input type="radio" name="xueduan" value="2" class="container_1_radio" <logic:equal value="2" name="xueduan">checked="checked"</logic:equal>/>
           	<label class="label">高中</label><input type="radio" name="xueduan" value="3"  class="container_1_radio" <logic:equal value="3" name="xueduan">checked="checked"</logic:equal>/>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_2_size">
    		姓名：<input type="text" value="" placeholder="真实姓名" name="username" id="username" class="container_2_input" style="width:75%;padding-left:7px;"/>
        </div>
    </div>
    <%-- 
    <div class="container_2">
    	<div class="container_2_size">
    		姓名：<input type="text" value="${username }" placeholder="真实姓名必须手动填写" name="username" class="container_2_input" style="width:75%;padding-left:7px;"/>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_2_size">
    		<input type="text" value="${loginname }" placeholder="用户名" name="loginname" class="container_2_input"/>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_2_size">
    		<input type="password" value="${password }" placeholder="密码" name="password" class="container_2_input"/>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_2_size">
    		<input type="password" value="${repassword }" placeholder="确认密码" name="repassword" class="container_2_input"/>
        </div>
    </div>
    --%>
    <%-- 
    <div class="container_2">
    	<div class="container_2_size">
    		<input type="text" value="${mobile }" placeholder="手机号码（找回密码用）" name="mobile" class="container_2_input"/>
        </div>
    </div>
    <div class="container_2">
    	<a href="javascript:selectSchool();"><div class="container_2_size">
    		<span class="container_2_size_span" id="schoolname">${unitname }</span>
    		<input type="hidden" name="unitid" id="unitid" value="${unitid }"/>
    		<input type="hidden" name="unitname" id="unitname" value="${unitname }"/>
            <span class="container_2_more"><img src="/weixin/images/school.png" /></span>
        </div></a>
    </div>
    --%>
    <div class="tips" style="margin-left:18px;margin-bottom:-30px;" id="tips"><logic:present name="errmsg"><bean:write name="errmsg" /></logic:present></div>
    <div class="container_3">
    	<a href="javascript:register();"><div class="container_3_size">
    		<span>立即注册</span>
        </div></a>
    </div>
</div>
<div id="div_hidden" style="width:100%; height:100%; overflow:hidden; position:absolute; top:0px; z-index:999999999;background:rgba(0,0,0,0.5); display:none;"><div style="z-index:9999999999;auto:30% 0px;top:45%;position:absolute;left:35%;color:#ff0000;background-color:#fff;border: 1px solid #eee; border-radius: 5px;padding: 4px 10px;">请稍等...</div></div>
<input type="hidden" name="openid" value="${openid }"/>
<input type="hidden" name="headimgurl" value="${headimgurl }"/>
<input type="hidden" name="nickname" value="${nickname }"/>
<input type="hidden" name="sex" value="${sex }"/>
<input type="hidden" name="scanResult" value="${scanResult }"/>
<input type="hidden" name="mobile" value=""/>
<input type="hidden" name="unitid" id="unitid" value="1"/>
<input type="hidden" name="unitname" id="unitname" value="龙门书局"/>
<input type="hidden" name="autoRegister" value="1"/>
</form>
</body>
</html>