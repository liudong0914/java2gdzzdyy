<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.util.date.DateTime"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>客服中心</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">客服中心</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<img src="/weixin/images/logo-01.png" class="service_img" />
<div class="service">
	<!-- 
    <p class="service_p">客服电话：010-64034373</p>
    <p class="service_p">电子邮箱：longmenzuoyebao@163.com</p>
    <p class="service_p">联系地址：北京市东城区安定门外大街138号皇城国际中心B座806室</p>
    <p class="service_p service_p01">上班时间：08:00 ~ 17:00（工作日）</p>
     -->
    <p class="service_p">组织名称：广东省中等职业学校德育研究会</p>
</div>
<div class="service_bottom">Copyright © 2017-<%=DateTime.getDateYear() %></div>
</body>
</html>