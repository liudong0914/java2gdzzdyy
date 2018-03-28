<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>修改昵称</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	function saveNickName(){  
		var nick=document.getElementById("nickname");
		var nickname=nick.value;
		if(nickname==""){
			$("#msg").html("请输入昵称");
			nick.focus();
		}else{
			document.pageForm.action = "/weixinAccountIndex.app?method=saveNickName&userid=${userid}";
			document.pageForm.submit();
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
    	<p class="search_wk_p">修改昵称</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form name="pageForm" method="post">
<div class="name">
	<input type="text" value="用户名：${userinfo.loginname}" disabled="disabled" style="font-size:16px;background-color:#fcfcfc;"/>
	<p></p>
	<input type="text" name="nickname" id="nickname" value="${userinfo.username}" style="font-size:16px;"/>
    <span id="msg" style="color:red;font-size:14px;"></span>
    <p>个人昵称修改</p>
    <a href="javascript:saveNickName()">提交</a>
</div>
</form>
</body>
</html>