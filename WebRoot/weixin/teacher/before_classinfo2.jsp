<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.weixin.mp.MpUtil"%>
<%@page import="com.wkmk.tk.bo.TkBookContent"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>学案内容</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">课前预习内容</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div id="container">
	<%
	TkBookContent bookContent = (TkBookContent)request.getAttribute("bookContent");
	String show = (String)request.getAttribute("show");
	%>
	<div class="container_welcome">
    	<div class="conrainer_welcome_font">
        	<p><%if(bookContent.getBeforeclass() != null && !"".equals(bookContent.getBeforeclass())){ %><%=bookContent.getBeforeclass() %><%}else{ %>暂无内容！<%} %></p>
        </div>
    </div>
    <%if("1".equals(show)){ %>
	<div class="container_welcome_moble">
    	<a href="/weixinTeacher.app?method=preparationQuestion2&userid=${userid }&bookcontentid=${bookcontentid }&classid=${classid }&index=1&next=1&show=1">
        	<div class="container_welcome_moble0">
    			<span>查看试题</span>
        	</div>
        </a>
    </div>
    <%} %>
</div>
</body>
</html>