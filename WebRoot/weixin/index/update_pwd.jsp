<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>修改密码</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function updateSavePwd(){
    document.registerForm.action="/weixinAccountIndex.app?method=updateSavePwd";
    document.registerForm.submit();
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<form name="registerForm" method="post">
<div id="container">
    <div class="container_2">
    	<div class="container_2_size">
    		<input type="password" value="" placeholder="旧密码" name="oldpassword" class="container_2_input"/>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_2_size">
    		<input type="password" value="" placeholder="新密码" name="newpassword" class="container_2_input"/>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_2_size">
    		<input type="password" value="" placeholder="确认密码" name="repassword" class="container_2_input"/>
        </div>
    </div>
    <div class="container_3">
    	<a href="javascript:updateSavePwd();"><div class="container_3_size">
    		<span>修改密码</span>
        </div></a>
    </div>
    <logic:present name="errmsg"><div class="tips"><bean:write name="errmsg" /></div></logic:present>
    <logic:present name="msg"><div class="tips" style="color:#45d4a2;"><bean:write name="msg" /></div></logic:present>
</div>
<div id="footer">
	<a href="/weixinAccountIndex.app?method=userindex&userid=${userid }" class="footer_0">
    	<img src="/weixin/images/wd.png" />
        <p>首页</p>
    </a>
    <%@ include file="/weixin/index/scan_footer.jsp"%>
	<a href="/weixinAccountIndex.app?method=myAccount&userid=${userid }" class="footer_00">
    	<img src="/weixin/images/wm.png" />
    	<p>返回</p>
    </a>
</div>
<input type="hidden" name="userid" value="${userid }"/>
</form>
</body>
</html>