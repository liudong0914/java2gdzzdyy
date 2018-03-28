<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsType"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>查看错题</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=getErrQuestionByClassBook&userid=${userid }&bookid=${bookid }&classid=${classid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">查看错题</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
TkQuestionsType type = model.getTkQuestionsType();
%>
<form method="post" name="pageForm" >
<div id="container" style="padding-bottom:55px;">
	<div class="container_question">
    	<div class="container_question_size">
           <span class="container_question_size_title">【<%=model.getQuestionno() %>】 <%=model.getTitlecontent() %></span>
        </div>
    </div>
    <%
    if("A".equals(type.getType())){
    %>
    <div class="container_answer">
    	<div class="container_answer_size">
    		<%
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    		%>
        	<div class="container_answer_size_radio">
        		<input type="radio" name="item" value="<%=model.getOptionNo(i) %>" />
                 <lable><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %></lable>
            </div>
            <%} %>
        </div>
    </div>
    <%}else if("B".equals(type.getType())){ %>
    <div class="container_answer">
    	<div class="container_answer_size">
    		<%
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    		%>
        	<div class="container_answer_size_radio">
        		<input type="checkbox" name="item" value="<%=model.getOptionNo(i) %>"/>
                 <lable><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %></lable>
            </div>
            <%} %>
        </div>
    </div>
    <%}else if("C".equals(type.getType())){ %>
    <div class="container_answer">
    	  	<div class="container_answer_size">
	        	<div class="container_answer_size_radio">
	        		<input type="radio" name="itemC" value="1" />
	                <label id="option_1" >A、对</label><br/>
	            </div>
	            <div class="container_answer_size_radio">
	        		<input type="radio" name="itemC" value="0" />
	                <label id="option_0">B、错</label><br/>
	            </div>
	        </div>
    </div>
    <%}else if("E".equals(type.getType())){ %>
    <div class="container_answer">
    </div>
    <%} %>
    <%
    Integer filmCounts = (Integer)request.getAttribute("filmCounts");
    Integer similarCounts = (Integer)request.getAttribute("similarCounts");
    if(filmCounts.intValue() > 0 || similarCounts.intValue() > 0){
    %>
    <div class="conrainer_button">
    	<%if(similarCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=viewSimilar&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&questionid=${model.questionid }&type=3&qid=${model.questionid }&vtype=10" class="conrainer_button1">举一反三</a><%} %>
        <%if(filmCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=videoPlay&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&questionid=${model.questionid }&type=3&qid=${model.questionid }&vtype=10" class="conrainer_button1">看微课</a><%} %>
    </div>
    <%} %>
</div>

</form>
</body>
</html>