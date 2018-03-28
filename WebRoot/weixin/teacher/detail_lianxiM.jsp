<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsType"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>查看作业习题</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style1.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
%>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid}" target="_top"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">查看作业习题</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<%
String prebutton = (String)request.getAttribute("prebutton");
String nextbutton = (String)request.getAttribute("nextbutton");
%>
<form method="post" name="pageForm" >
<div id="container" style="padding-bottom:55px;">
	<div class="conrainer_bz">
    	<div class="container_bz_size">
    		<span>${index }.【<%=model.getQuestionno() %>】<%=model.getTitlecontent() %></span>
        </div>
    </div>
    <div class="box_question">
    	<%
    	List childList = (List)request.getAttribute("childList");
    	TkQuestionsInfo tqi = null;
    	TkQuestionsType tqt = null;
    	for(int x=0, y=childList.size(); x<y; x++){
    		tqi = (TkQuestionsInfo)childList.get(x);
    		tqt = tqi.getTkQuestionsType();
    	%>
    	<%if(x==0){ %><div style="margin-top:10px;margin-bottom:-10px;"></div><%} %>
    	<%if("A".equals(tqt.getType())){//单选 %>
		<div class="container_question">
    		<div class="container_question_size">
            	<span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span>
        	</div>
    	</div>
   	 	<div class="container_answer">
    		<div class="container_answer_size">
    			<%
	    		for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
	    		%>
	        	<div class="container_answer_size_radio">
	        		<input type="radio" name="itemA" value="<%=tqi.getOptionNo(i) %>" />
	        		<label><%=tqi.getOptionNo(i) %>、<%=tqi.getOptionValue(i) %></label><br/>
	            </div>
	            <%} %>
        	</div>
    	</div>
    	<%}else if("B".equals(tqt.getType())){//多选 %>
    	<div class="container_question">
    		<div class="container_question_size">
            	<span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span>
        	</div>
    	</div>
   	 	<div class="container_answer">
    		<div class="container_answer_size">
    			<%
	    		for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
	    		%>
	        	<div class="container_answer_size_radio">
	        		<input type="checkbox" name="itemB" value="<%=tqi.getOptionNo(i) %>" />
	        		<label><%=tqi.getOptionNo(i) %>、<%=tqi.getOptionValue(i) %></label><br/>
	            </div>
	            <%} %>
        	</div>
    	</div>
    	<%}else if("C".equals(tqt.getType())){//判断 %>
    	<div class="container_question">
	    	<div class="container_question_size">
	           <span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span>
	        </div>
	    </div>
	    <div class="container_answer">
	    	<div class="container_answer_size">
	        	<div class="container_answer_size_radio" >
	        		<input type="radio" name="itemC" id="item_<%=x %>_1" value="1" />
	        		<label id="option_<%=x %>_1">A、对</label><br/>
	            </div>
	            <div class="container_answer_size_radio">
	        		<input type="radio" name="itemC" id="item_<%=x %>_2" value="0" />
	        		<label id="option_<%=x %>_2">B、错</label><br/>
	            </div>
	        </div>
	    </div>
    	<%}else if("E".equals(tqt.getType())){//填空 %>
    	<div class="container_question">
	    	<div class="container_question_size">
	           <span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span>
	        </div>
	    </div>
    	<%} %>
    	<%} %>
    <%
    Integer filmCounts = (Integer)request.getAttribute("filmCounts");
    Integer similarCounts = (Integer)request.getAttribute("similarCounts");
    if(filmCounts.intValue() > 0 || similarCounts.intValue() > 0){
    %>
    <div class="conrainer_button">
    	<%if(similarCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=viewSimilar&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }&qid=${model.questionid }&vtype=5" class="conrainer_button1" target="_top">举一反三</a><%} %>
        <%if(filmCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=videoPlay&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }&qid=${model.questionid }&vtype=5" class="conrainer_button1" target="_top">看微课</a><%} %>
    </div>
    <%} %>
    <div class="container_answer" style="padding-bottom:55px;"></div>
</div>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<div id="footer">
    <%if("1".equals(prebutton)){ %>
	<a href="/weixinTeacher.app?method=viewLianxi&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index-1 }&next=-1" class="footer_6">
    	<img src="/weixin/images/syt.png" />
    	<p>上一题</p>
    </a>
    <%}else{ %>
    <a class="footer_6">
    	<img src="/weixin/images/syt0.png" />
    	<p style="color:#777;">上一题</p>
    </a>
    <%} %>
    <%if("1".equals(nextbutton)){ %>
	<a href="/weixinTeacher.app?method=viewLianxi&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index+1 }&next=1" class="footer_6">
    	<img src="/weixin/images/xyt.png" />
    	<p>下一题</p>
    </a>
    <%}else{ %>
    <a class="footer_6">
    	<img src="/weixin/images/xyt0.png" />
    	<p style="color:#777;">下一题</p>
    </a>
    <%} %>
</div>
<input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
<input type="hidden" name="index" value="<bean:write name="index"/>"/>
<input type="hidden" name="next" value="<bean:write name="next"/>"/>
</form>
</body>
</html>