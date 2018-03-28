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
<title>查看作答</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function repeatLianxi(){
	document.pageForm.action = "/weixinStudent.app?method=repeatLianxi";
	document.pageForm.submit();
}
function goBack(){
	document.pageForm.action = "/weixinStudent.app?method=bookContent";
	document.pageForm.submit();
}
</script>
</head>

<body>
<div class="search search_write">
	<a href="/weixinStudent.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid }" target="_top"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">查看作答</p>
    </div>
</div>
<div class="search_padding"></div>
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
String prebutton = (String)request.getAttribute("prebutton");
String nextbutton = (String)request.getAttribute("nextbutton");
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
<form method="post" name="pageForm" target="_top">
<div id="container" style="padding-bottom:55px;">
	<div class="container_question">
    	<div class="container_question_size">
           <span class="container_question_size_title">${index }.【<%=model.getQuestionno() %>】<%=model.getTitlecontent() %></span><%if(answer != null){ %><%if("1".equals(answer.getIsright())){ %><span class="tips_right">√</span><%}else{%><span class="tips_wrong">×</span><%}}else{ %><span class="tips_noanswer">[没有作答]</span><%} %>
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
        		<label id="option_1" <%if(answer != null && "1".equals(answer.getAnswer())){ %>style="color:<%=answer_color %>"<%} %>>A、对<%if(answer != null && "1".equals(answer.getAnswer())){ %><%if(model.getRightans().equals(answer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></label><br/>
            </div>
            <div class="container_answer_size_radio">
        		<input type="radio" name="item" value="0" <%if(answer != null && "0".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label id="option_0" <%if(answer != null && "0".equals(answer.getAnswer())){ %>style="color:<%=answer_color %>"<%} %>>B、错<%if(answer != null && "0".equals(answer.getAnswer())){ %><%if(model.getRightans().equals(answer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></label><br/>
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
    	<%if(similarCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=viewSimilar&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }&taskid=${taskid }&tasktype=${tasktype }&qid=${model.questionid }&vtype=2" class="conrainer_button1" target="_top">举一反三</a><%} %>
        <%if(filmCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=videoPlay&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }&taskid=${taskid }&tasktype=${tasktype }&qid=${model.questionid }&vtype=2" class="conrainer_button1" target="_top">看微课</a><%} %>
    </div>
    <%} %>
</div>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<div id="footer">
	<%-- 
	<a href="javascript:repeatLianxi();" class="footer_01">
    	<img src="/weixin/images/chongzuo.png" />
    	<p>重做</p>
    </a>
    
    <a href="/weixinAccountIndex.app?method=index&userid=${userid}" class="footer_01" target="_top">
    	<img src="/weixin/images/wd.png" />
        <p>首页</p>
    </a>
    --%>
    <%if("1".equals(prebutton)){ %>
	<a href="/weixinStudent.app?method=viewLianxi&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index-1 }&next=-1&taskid=${taskid }&tasktype=${tasktype }" class="footer_6">
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
	<a href="/weixinStudent.app?method=viewLianxi&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index+1 }&next=1&taskid=${taskid }&tasktype=${tasktype }" class="footer_6">
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
<input type="hidden" name="tasktype" value="<bean:write name="tasktype"/>"/>
<input type="hidden" name="taskid" value="<bean:write name="taskid"/>"/>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
<input type="hidden" name="index" value="<bean:write name="index"/>"/>
<input type="hidden" name="next" value="<bean:write name="next"/>"/>
</form>
</body>
</html>