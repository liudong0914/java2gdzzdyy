<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>个人中心</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<div class="teacher_header">
	<div class="teacher_header_main">
    	<img src="${userinfo.photo}" />
        <p class="teacher_header_main_P">${userinfo.username}</p>
        <a href="/wxpay/weixinPay.app?method=index&userid=${userid }"><p class="teacher_header_main_P02"><span class="teacher_header_main_P02_span">¥</span><span>学币</span><span  class="teacher_header_main_P02_span02">${userinfo.money}</span></p></a>
    </div>
	<!-- <a href="/weixinAccountIndex.app?method=userInfoManager&userid=${userid}"><img src="/weixin/images/b01.png"  class="teacher_header_img02" /></a> -->	
	  <a href="/weixinAccountIndex.app?method=messageList&userid=${userid}">
	  <img src="/weixin/images/b02.png"  class="teacher_header_img03" />
    	<img src="/weixin/images/b04.png" class="teacher_header_img"/>    	
        <p style="position: absolute;color: #fff;font-size: 8px;font-weight: bold;right: 15px;top: 8px;background-color: #ff5317;padding: 0px 4px;line-height: 13px !important;border-radius: 50%;">${unread}</p>
      </a>
</div>
<div id="bottom">
    <a href="/weixinCourse.app?method=myCourse&userid=${userid}">
	    <div class="number01">
	        <img src="/weixin/images/c12.png"  class="number01_img"/>
	        <p>课程学习</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
    <a href="/wxpay/weixinPay.app?method=index&userid=${userid }">
	    <div class="number01">
	        <img src="/weixin/images/c13.png"  class="number01_img"/>
	        <p>在线充值</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
     <a href="/weixinTextBook.app?method=myTextBookOrder&userid=${userid}">
	    <div class="number01">
	        <img src="/weixin/images/hj02.png"  class="number01_img"/>
	        <p>教材订购记录</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=serverCenter&userid=${userid}">
	    <div class="number01">
	        <img src="/weixin/images/c07.png"  class="number01_img"/>
	        <p>客服中心</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=userInfoManager&userid=${userid}">
	    <div class="number01">
	        <img src="/weixin/images/c15.png"  class="number01_img"/>
	        <p>设置</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
 </div>
<%@ include file="/weixin/account/footer.jsp"%>
<%@ include file="/weixin/account/scan.jsp"%>
</body>
</html>