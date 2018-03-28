<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>退役军人培训项目首页_<%=Constants.PRODUCT_NAME %></title>
<link href="/skin/course01/css/sytle.css" rel="stylesheet" type="text/css">
<style>
	body{ min-width:1200px;}
</style>
</head>

<body>
<%@ include file="top.jsp"%>

<div class="content content01">
	<img src="/skin/course01/images/a20.jpg" class="banner" />
    <div class="content_training">
    	<p class="content_training_title">培训课程</p>
        <div class="content_training_main">
        	<%
        	List list = (List)request.getAttribute("list");
        	EduCourseInfo model = null;
        	for(int i=0, size=list.size(); i<size; i++){
        		model = (EduCourseInfo)list.get(i);
        	%>
        	<a href="/course/<%=model.getCourseid() %>.htm"><div class="content_training_main_div">
            	<img src="/upload/<%=model.getSketch() %>" />
                <p class="content_training_main_div_p01" style="padding-top:10px; padding-bottom:3px;"><%=model.getTitle() %></p>
                <p class="content_training_main_div_p02">主讲教师：<%=model.getSysUserInfo().getUsername() %></p>
            </div></a>
            <%} %>
        </div><!------content_training_main-e------>
    </div><!------content_training-e------>
    <div class="content_training">
    	<div class="content_training_div">
    		<p class="content_training_title content_training_title02">配套教材</p>
            <p class="content_training_div_p">教材订购：010-84650630&nbsp;&nbsp;&nbsp;&nbsp;邮箱：cgl@hxnl.com</p>
        </div>
        <div class="content_training_main">
        	<logic:iterate id="book" name="bookList">
        	<div class="content_training_main_book" title="${book.title }">
            	<img src="/upload/${book.sketch }" />
                <p class="content_training_main_div_p"><span class="content_training_main_div_p_span">¥</span><span>${book.price }</span></p>
                <p class="content_training_main_div_p01">${book.title }</p>
                <p class="content_training_main_div_p02">${book.subtitle }</p>
            </div>
            </logic:iterate>
        </div><!------content_training_main-e------>
    </div><!------content_training-e------>
</div><!------content-e------>
<!------中间内容结束------->

<%@ include file="footer.jsp"%>
</body>
</html>