<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>资讯栏目</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search">
	<a href="javascript:history.back()"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">资讯栏目</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<!-----------------内容------------------>
<div class="class_main library_content library_lm_main">
<logic:iterate id="model" name="columnlist">
	 <div class="library library_lm">
        <a href="/weixinAccountIndex.app?method=getNewsByColumn&columnid=${model.columnid}&userid=${userid}" class="library_a">${model.columnname}</a>     
    </div>
</logic:iterate>
</div>
</body>
</html>