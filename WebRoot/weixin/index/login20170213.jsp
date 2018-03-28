<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title></title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>

<body>
<form name="registerForm" method="post" action="/weixinAccountIndex.app?method=register">
<input type="hidden" name="openid" value="${openid }"/>
<input type="hidden" name="headimgurl" value="${headimgurl }"/>
<input type="hidden" name="nickname" value="${nickname }"/>
<input type="hidden" name="sex" value="${sex }"/>
<input type="hidden" name="scanResult" value="${scanResult }"/>
</form>
<div id="content">
	<div class="content_user">
        <p>加载中...</p>
    </div>
</div>
<script type="text/javascript">
function sub(){
	document.registerForm.submit();
}
setTimeout(sub,100);
</script>
</body>
</html>