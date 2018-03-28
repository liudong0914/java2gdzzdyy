<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>确认信息</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body style="background-color:#f3f3f3;">
<div id="container">
	<div class="container_nr">
    	<div class="conrainer_nr_font">
        	<p>取消绑定后，您可以重新绑定新的账号，也可以用新的微信号与此账号绑定，您真的要取消绑定账号吗？</p>
        </div>
    </div>
    
	<div class="container_nr_moble">
    	<a href="/weixinAccountIndex.app?method=logout&userid=${userid }">
        	<div class="container_nr_moble1">
    			<span>确定</span>
        	</div>
        </a>
        <a href="/weixinAccountIndex.app?method=myAccount&userid=${userid }">
        	<div class="container_nr_moble3">
    			<span>取消</span>
        	</div>
        </a>
    </div>
</div>

</body>
</html>