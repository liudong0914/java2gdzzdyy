<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkTextBookInfo"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>教材订购成功</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
</script>
</head>

<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<form name="pageForm" method="post">
<div class="search" style="z-index:99;">
	<a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}">
	<div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">教材订购成功</p>
    </div>
</div>
<div class="search_padding"></div>

<img src="/weixin/images/paper02.png" class="paper_img" />
<p class="paper_p" >订购成功，请等待收货~</p>
</form>
</body>
</html>