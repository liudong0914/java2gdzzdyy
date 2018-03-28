<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>功能导航</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
</head>

<body>
<div class="search">
	<a href="javascript:scanCode()"><div  class="search_left">
    	<img src="/weixin/images/icon03.png" />
        <p>扫一扫</p>
    </div></a>
    <div  class="search_input">
    	<a href="/weixinVod.app?method=inSearchIndex&userid=${userid}">搜索解题微课作业</a>   
    </div>
     <a href="/weixinAccountIndex.app?method=messageList&userid=${userid}">
	    <div  class="search_left search_right">
		    <div class="search_right_p">
		    	<img src="/weixin/images/icon05.png" />    	
		        <p>${unread}</p>
		    </div>
	        <p>消息</p>
	    </div>
    </a>
</div>
<div class="search_padding"></div>
<div style="border-bottom:1px solid #c5c4c4;"></div> 
<a href="/weixinAccountIndex.app?method=index&userid=${userid }" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon09.png"  class="fade_module01_img"/>
    <p>首页</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
 <a href="/weixinVod.app?method=goodFilmBookList&userid=${userid }" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon10.png"  class="fade_module01_img"/>
    <p>精品微课</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
<a href="/weixinHelp.app?method=index&userid=${userid}" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon12.png"  class="fade_module01_img"/>
    <p>在线答疑</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
<a href="/weixinStudent.app?method=selectEnglishBook&userid=${userid }" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon-02.png"  class="fade_module01_img"/>
    <p>英语听力</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
 <a href="/weixinVod.app?method=booklist&userid=${userid }" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon10.png"  class="fade_module01_img"/>
    <p>解题微课</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
<a href="/weixinAccountIndex.app?method=tip&userid=${userid}" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon11.png"  class="fade_module01_img"/>
    <p>试卷下载</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
<logic:equal value="1" name="usertype">
 <a href="/weixinAccountIndex.app?method=userindex&userid=${userid }" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon14.png"  class="fade_module01_img"/>
    <p>检查作业</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
</logic:equal>
<logic:equal value="2" name="usertype">
 <a href="/weixinAccountIndex.app?method=userindex&userid=${userid }" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon14.png"  class="fade_module01_img"/>
    <p>在线做题</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
</logic:equal>
 <a href="/weixinVod.app?method=getFilmAuditionList&userid=${userid}" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon10.png"  class="fade_module01_img"/>
    <p>试听专享</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
<a href="/weixinAccountIndex.app?method=getNewsByColumn&userid=${userid}" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon08.png"  class="fade_module01_img"/>
    <p>资讯</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
 <a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}" class="fade_module01 fade_module02">
	<img src="/weixin/images/icon07.png"  class="fade_module01_img"/>
    <p>个人中心</p>
    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
</a>
<div style="padding-bottom:52px;"></div>
<%@ include file="/weixin/account/footer.jsp"%>
<%@ include file="/weixin/account/scan.jsp"%>
</body>
</html>