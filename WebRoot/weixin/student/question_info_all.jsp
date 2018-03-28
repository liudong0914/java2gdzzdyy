<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkPaperAnswer"%>
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
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinStudent.app?method=getUserErrQuestionByClassBook&userid=${userid }&bookid=${bookid }&classid=${classid}"><div  class="search_left">
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
TkPaperAnswer answer = (TkPaperAnswer)request.getAttribute("answer");
String answer_color = "red";//作答对错颜色标记
if(answer != null && model.getRightans().equals(answer.getAnswer())){
	answer_color = "green";
}
TkQuestionsType type = model.getTkQuestionsType();
String[] rightAnswer = model.getRightans().split("【】");
String[] paperAnswer = null;
if(answer != null){
	paperAnswer = answer.getAnswer().split("【】");
}
%>
<form method="post" name="pageForm" >
<div id="container">
	<div class="container_question">
    	<div class="container_question_size">
           <span class="container_question_size_title">【<%=model.getQuestionno() %>】<%=model.getTitlecontent() %></span><%if(answer != null){ %><%if("1".equals(answer.getIsright())){ %><span class="tips_right">√</span><%}else{%><span class="tips_wrong">×</span><%}}else{ %><span class="tips_noanswer">[没有作答]</span><%} %>
        </div>
    </div>
	<%if("A".equals(type.getType())){ %>
    <div class="container_answer">
    	<div class="container_answer_size">
    		<%
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    		%>
        	<div class="container_answer_size_radio">
        		<input type="radio" name="item" value="<%=model.getOptionNo(i) %>" <%if(answer != null && model.getOptionNo(i).equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label <%if(answer != null && model.getOptionNo(i).equals(answer.getAnswer())){ %>style="color:<%=answer_color %>"<%} %>><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %><%if(answer != null && model.getOptionNo(i).equals(answer.getAnswer())){ %><%if(model.getRightans().equals(answer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></label><br/>
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
        		<input type="checkbox" name="item" value="<%=model.getOptionNo(i) %>" <%if(answer != null && answer.getAnswer().indexOf(model.getOptionNo(i)) != -1){ %>checked="checked"<%} %>/>
        		<label <%if(answer != null && answer.getAnswer().indexOf(model.getOptionNo(i)) != -1){ %><%if(model.getRightans().indexOf(model.getOptionNo(i)) != -1){ %>style="color:green"<%}else{%>style="color:red"<%}} %>><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %><%if(answer != null && answer.getAnswer().indexOf(model.getOptionNo(i)) != -1){ %><%if(model.getRightans().indexOf(model.getOptionNo(i)) != -1){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></label><br/>
            </div>
            <%} %>
        </div>
    </div>
    <%}else if("C".equals(type.getType())){ %>
    <div class="container_answer">
    	<div class="container_answer_size">
        	<div class="container_answer_size_radio">
        		<input type="radio" name="item" value="1" <%if(answer != null && "1".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label <%if(answer != null && "1".equals(answer.getAnswer())){ %>style="color:<%=answer_color %>"<%} %>>A、对<%if(answer != null && "1".equals(answer.getAnswer())){ %><%if(model.getRightans().equals(answer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></label><br/>
            </div>
            <div class="container_answer_size_radio">
        		<input type="radio" name="item" value="0" <%if(answer != null && "0".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label <%if(answer != null && "0".equals(answer.getAnswer())){ %>style="color:<%=answer_color %>"<%} %>>B、错<%if(answer != null && "0".equals(answer.getAnswer())){ %><%if(model.getRightans().equals(answer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></label><br/>
            </div>
        </div>
    </div>
    <%}else if("E".equals(type.getType())){ %>
    <div class="container_answer">
    	<div class="container_answer_size">
    		<%
    		if(paperAnswer != null && paperAnswer.length > 0){
    			boolean isright = false;
    			for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    				isright = model.getIsRight(rightAnswer[i-1], paperAnswer[i-1]);
    		%>
        	<div class="container_answer_size_radio">
        		作答<%=i %>、<input type="text" name="item" class="container_answer_size_radio_input" value="<%=paperAnswer[i-1] %>" <%if(isright){ %>style="color:green"<%}else{%>style="color:red"<%} %>/><%if(isright){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%} %>
            </div>
            <%}} %>
        </div>
    </div>
    <%} %>
    <%
    Integer filmCounts = (Integer)request.getAttribute("filmCounts");
    Integer similarCounts = (Integer)request.getAttribute("similarCounts");
    if(filmCounts.intValue() > 0 || similarCounts.intValue() > 0){
    %>
    <div class="conrainer_button">
    	<%if(similarCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=viewSimilar&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&questionid=${model.questionid }&uerrorid=${uerrorid }&qid=${model.questionid }&vtype=4" class="conrainer_button1">举一反三</a><%} %>
        <%if(filmCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=videoPlay&userid=${userid }&classid=${classid }&bookid=${bookid }&bookcontentid=${bookcontentid }&questionid=${model.questionid }&uerrorid=${uerrorid}&qid=${model.questionid }&vtype=4" class="conrainer_button1">看微课</a><%} %>
    </div>
    <%} %>
</div>
</form>
</body>
</html>