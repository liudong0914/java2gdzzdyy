<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>提示信息</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body style="background-color:#f3f3f3;">
<div id="container">
	<div class="container_nr">
    	<div class="conrainer_nr_font">
    		<logic:present name="promptinfo"><p><bean:write name="promptinfo" /></p></logic:present>
			<logic:notPresent name="promptinfo"><p>扫一扫出错了，请稍后再试！</p></logic:notPresent>
        </div>
    </div>
</div>
<%
String video = (String)request.getAttribute("video");
if("1".equals(video)){
%>
<div class="password_a">
	<a href="/weixinVod.app?method=booklist&userid=${userid }">查看其他解题微课</a>
</div>
<%} %>
<%
String audio = (String)request.getAttribute("audio");
if("1".equals(audio)){
%>
<div class="password_a">
	<a href="/weixinStudent.app?method=selectEnglishBook&userid=${userid }">去听其他英语听力</a>
</div>
<%} %>
<div id="footer">
	<a href="/weixinAccountIndex.app?method=index&userid=${userid }" class="footer_0">
    	<img src="/weixin/images/wd0.png" style="width:20px;height:20px;"/>
        <p>首页</p>
    </a>
    <%@ include file="/weixin/index/scan_footer.jsp"%>
	<a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}" class="footer_00">
    	<img src="/weixin/images/user0.png" style="width:20px;height:20px;"/>
    	<p>个人中心</p>
    </a>
</div>
</body>
</html>