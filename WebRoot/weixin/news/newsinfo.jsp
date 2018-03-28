<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>${newsinfo.title}</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body style="background-color:#fff;">
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=getNewsByColumn&userid=${userid}">
		<div  class="search_left">
	    	<img src="/weixin/images/a01.png" class="search_class" />
	    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">资讯详情</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<!-----------------内容------------------>
<div class="library_font">
	<p class="library_font_title">${newsinfo.title}</p>
    <p  class="library_font_p"><span>${newsinfo.happendate}</span><span>${newsinfo.author}</span></p>
    ${newsinfo.htmlcontent}
</div>
</body>
</html>