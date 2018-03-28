<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>我的作业本错题集</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinStudent.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">我的作业本错题集</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<%int temp=0; %>
<c:forEach items="${list }" var="t" >
<%
int i=0; 
temp++;
%>
<div id="container_list" style="height:auto; padding-bottom: 0px;">
	<div class="container_10" style="overflow: hidden;">
				<div class="container_9_size">
						<div class="conrainer_9_moble_span">
							<span style="font-size:16px;">${t.title }</span>
						</div> 
				</div>
	</div>
    <c:forEach items="${t.errorquestionlist }" var="e">
    	<div class="container_10">
				<div class="container_9_size">
					<a href="weixinStudent.app?method=errorQuestionInfo2&userid=${userid }&classid=${classid }&bookid=${bookid }&bookcontentid=${t.bookcontentid }&questionid=${e.questionid }&uerrorid=${e.flagl}" class="container_7_size_moble">
						<div class="conrainer_9_moble_span">
							<span><%i++ ;%><%=i %>.${e.titlecontent}</span>
						</div> <span><img src="weixin/images/school_11.png"
							class="conrainer_9_size_moble_img" /> </span> </a>
				</div>
			</div>
    </c:forEach>    
</div>
</c:forEach>
<%if(temp == 0){ %>
<div class="container_welcome">
	<div class="conrainer_welcome_font">
    	<p>当前作业本没有错题！</p>
    </div>
</div>
<%} %>
</body>
</html>