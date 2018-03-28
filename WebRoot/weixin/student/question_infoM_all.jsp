<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkPaperAnswer"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsType"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>查看错题</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style1.css" rel="stylesheet" type="text/css" />
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
%>
<form method="post" name="pageForm" >
<div id="container">
	<div class="conrainer_bz">
    	<div class="container_bz_size">
    		<span>【<%=model.getQuestionno() %>】<%=model.getTitlecontent() %></span>
        </div>
    </div>
    <div class="box_question">
    	<%
    	List childList = (List)request.getAttribute("childList");
    	List allChildAnswerList = (List)request.getAttribute("allChildAnswerList");
    	Map childAnswerMap = (Map)request.getAttribute("childAnswerMap");
    	TkQuestionsInfo tqi = null;
    	TkQuestionsType tqt = null;
    	TkPaperAnswer childAnswer = null;
    	String[] rightAnswer = null;
    	String[] paperAnswer = null;
    	for(int x=0, y=childList.size(); x<y; x++){
    		tqi = (TkQuestionsInfo)childList.get(x);
    		tqt = tqi.getTkQuestionsType();
    		childAnswer = (TkPaperAnswer)childAnswerMap.get(tqi.getQuestionid());
    	%>
    	<%if(x==0){ %><div style="margin-top:10px;margin-bottom:-10px;"></div><%} %>
    	<%if("A".equals(tqt.getType())){//单选 %>
		<div class="container_question">
    		<div class="container_question_size">
            	<span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span><%if(childAnswer != null){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %><span class="tips_right">√</span><%}else{%><span class="tips_wrong">×</span><%}}else{ %><span class="tips_noanswer">[没有作答]</span><%} %>
        	</div>
    	</div>
   	 	<div class="container_answer">
    		<div class="container_answer_size">
    			<%
	    		for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
	    		%>
	        	<div class="container_answer_size_radio">
	        		<input type="radio" name="itemA_<%=x %>" value="<%=tqi.getOptionNo(i) %>" <%if(childAnswer != null && tqi.getOptionNo(i).equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label <%if(childAnswer != null && tqi.getOptionNo(i).equals(childAnswer.getAnswer())){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %>style="color:green"<%}else{ %>style="color:red"<%}} %>><%=tqi.getOptionNo(i) %>、<%=tqi.getOptionValue(i) %></label><br/>
	            </div>
	            <%} %>
        	</div>
    	</div>
    	<%}else if("B".equals(tqt.getType())){//多选 %>
    	<div class="container_question">
    		<div class="container_question_size">
            	<span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span><%if(childAnswer != null){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %><span class="tips_right">√</span><%}else{%><span class="tips_wrong">×</span><%}}else{ %><span class="tips_noanswer">[没有作答]</span><%} %>
        	</div>
    	</div>
   	 	<div class="container_answer">
    		<div class="container_answer_size">
    			<%
	    		for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
	    		%>
	        	<div class="container_answer_size_radio">
	        		<input type="checkbox" name="itemB_<%=x %>" value="<%=tqi.getOptionNo(i) %>" <%if(childAnswer != null && childAnswer.getAnswer().indexOf(tqi.getOptionNo(i)) != -1){ %>checked="checked"<%} %>/>
	        		<label <%if(childAnswer != null && childAnswer.getAnswer().indexOf(tqi.getOptionNo(i)) != -1){ %><%if(tqi.getRightans().indexOf(tqi.getOptionNo(i)) != -1){ %>style="color:green"<%}else{%>style="color:red"<%}} %>><%=tqi.getOptionNo(i) %>、<%=tqi.getOptionValue(i) %></label><br/>
	            </div>
	            <%} %>
        	</div>
    	</div>
    	<%}else if("C".equals(tqt.getType())){//判断 %>
    	<div class="container_question">
	    	<div class="container_question_size">
	           <span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span><%if(childAnswer != null){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %><span class="tips_right">√</span><%}else{%><span class="tips_wrong">×</span><%}}else{ %><span class="tips_noanswer">[没有作答]</span><%} %>
	        </div>
	    </div>
	    <div class="container_answer">
	    	<div class="container_answer_size">
	        	<div class="container_answer_size_radio">
	        		<input type="radio" name="itemC_<%=x %>" value="1" <%if(childAnswer != null && "1".equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label <%if(childAnswer != null && "1".equals(childAnswer.getAnswer())){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %>style="color:green"<%}else{ %>style="color:red"<%}} %>>A、对</label><br/>
	            </div>
	            <div class="container_answer_size_radio">
	        		<input type="radio" name="itemC_<%=x %>" value="0" <%if(childAnswer != null && "0".equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label <%if(childAnswer != null && "0".equals(childAnswer.getAnswer())){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %>style="color:green"<%}else{ %>style="color:red"<%}} %>>B、错</label><br/>
	            </div>
	        </div>
	    </div>
    	<%}else if("E".equals(tqt.getType())){//填空 %>
    	<div class="container_question">
	    	<div class="container_question_size">
	           <span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span><%if(childAnswer != null){ %><%if("1".equals(childAnswer.getIsright())){ %><span class="tips_right">√</span><%}else{%><span class="tips_wrong">×</span><%}}else{ %><span class="tips_noanswer">[没有作答]</span><%} %>
	        </div>
	    </div>
	    <div class="container_answer">
	    	<div class="container_answer_size">
	    		<%
	    		if(childAnswer != null){
	    			boolean isright = false;
		    		rightAnswer = tqi.getRightans().split("【】");
		    		paperAnswer = childAnswer.getAnswer().split("【】");
		            for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
		            	isright = tqi.getIsRight(rightAnswer[i-1], paperAnswer[i-1]);
	            %>
	            <div class="container_answer_size_radio">
	        		作答<%=i %>、<input type="text" name="itemE_<%=x %>" class="container_answer_size_radio_input" value="<%=paperAnswer[i-1] %>" <%if(isright){ %>style="color:green"<%}else{%>style="color:red"<%} %>/>
	            </div>
	            <%}} %>
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
    	<%if(similarCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=viewSimilar&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&questionid=${model.questionid }&uerrorid=${uerrorid }&qid=${model.questionid }&vtype=4" class="conrainer_button1">举一反三</a><%} %>
        <%if(filmCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=videoPlay&userid=${userid }&classid=${classid }&bookid=${bookid }&bookcontentid=${bookcontentid }&questionid=${model.questionid }&uerrorid=${uerrorid}&qid=${model.questionid }&vtype=4" class="conrainer_button1">看微课</a><%} %>
    </div>
    <%} %>
    <div class="container_answer" style="padding-bottom:35px;"></div>
    </div>
</div>
</form>
</body>
</html>