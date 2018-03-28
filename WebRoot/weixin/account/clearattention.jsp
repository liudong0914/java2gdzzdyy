<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>取消微信绑定</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	function clearAttention(){  
	window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=MpUtil.APPID %>&redirect_uri=<%=MpUtil.HOMEPAGE %>/weixinAccountIndex.app?method=clearAttention&response_type=code&scope=snsapi_userinfo&state=scan#wechat_redirect";
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
    	<p class="search_wk_p">取消微信绑定</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form name="pageForm" method="post">
<div class="name">
	<input type="text" value="当前绑定用户名：${userinfo.loginname}" disabled="disabled" style="font-size:16px;background-color:#fcfcfc;"/>
	<p></p>
	<input type="text" value="当前绑定用户昵称：${userinfo.username}" disabled="disabled" style="font-size:16px;background-color:#fcfcfc;"/>
    <p></p>
    <a href="/weixinAccountIndex.app?method=userInfoManager&userid=${userid}">返回</a>
    <a href="javascript:clearAttention()">确定</a>
</div>
</form>
</body>
</html>