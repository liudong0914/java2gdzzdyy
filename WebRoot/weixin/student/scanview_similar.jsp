<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsType"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>查看举一反三</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function preQuestion(){
	document.getElementById("index0").value = '${index0-1 }';
	document.getElementById("next0").value = '-1';
	document.pageForm.action = "/weixinStudent.app?method=scanViewSimilar";
	document.pageForm.submit();
}
function nextQuestion(){
	document.getElementById("index0").value = '${index0+1 }';
	document.getElementById("next0").value = '1';
	document.pageForm.action = "/weixinStudent.app?method=scanViewSimilar";
	document.pageForm.submit();
}
function showAnswer(){
	document.getElementById("true_button").style.display = "none";
	document.getElementById("answer_div").style.display = "block";
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
String prebutton = (String)request.getAttribute("prebutton");
String nextbutton = (String)request.getAttribute("nextbutton");
TkQuestionsType type = model.getTkQuestionsType();
%>
<form method="post" name="pageForm" >
<div id="container" style="padding-bottom:55px;">
	<div class="container_question">
    	<div class="container_question_size">
           <span class="container_question_size_title">${index0 }.【<%=model.getQuestionno() %>】<%=model.getTitlecontent() %></span>
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
        		<label><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %></label><br/>
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
        		<label><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %></label><br/>
            </div>
            <%} %>
        </div>
    </div>
    <%}else if("C".equals(type.getType())){ %>
    <div class="container_answer">
    	<div class="container_answer_size">
        	<div class="container_answer_size_radio">
        		<input type="radio" name="item" value="1"/>
        		<label>A、对</label><br/>
            </div>
            <div class="container_answer_size_radio">
        		<input type="radio" name="item" value="0"/>
        		<label>B、错</label><br/>
            </div>
        </div>
    </div>
    <%} %>
    <a href="javascript:showAnswer()" id="true_button">
   		<div class="botton3" style="width:99%;margin-top:10px;">
   			<p>显示参考答案及解析</p>
   		</div>
   	</a>
    <div class="container_answer" id="answer_div" style="display:none;">
    	<div class="container_answer_size">
        	【参考答案】<%=model.getRightansName() %><br/><br/>
        	【解析】<%=model.getDescript() %>
        </div>
    </div>
</div>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<div id="footer">
	<a href="/weixinAccountIndex.app?method=index&userid=${userid}" class="footer_01">
    	<img src="/weixin/images/wd0.png" style="width:20px;height:20px;"/>
        <p>首页</p>
    </a>
	<%if("1".equals(prebutton)){ %>
	<a href="javascript:preQuestion()" class="footer_01">
    	<img src="/weixin/images/syt.png" />
    	<p>上一题</p>
    </a>
    <%}else{ %>
    <a class="footer_01">
    	<img src="/weixin/images/syt0.png" />
    	<p style="color:#777;">上一题</p>
    </a>
    <%} %>
    <%if("1".equals(nextbutton)){ %>
	<a href="javascript:nextQuestion()" class="footer_01">
    	<img src="/weixin/images/xyt.png" />
    	<p>下一题</p>
    </a>
    <%}else{ %>
    <a class="footer_01">
    	<img src="/weixin/images/xyt0.png" />
    	<p style="color:#777;">下一题</p>
    </a>
    <%} %>
    <a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}" class="footer_01" style="border-right:none;">
    	<img src="/weixin/images/user0.png" style="width:20px;height:20px;"/>
    	<p>个人中心</p>
    </a>
</div>
<input type="hidden" name="qid" value="<bean:write name="qid"/>"/>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
<input type="hidden" name="index0" id="index0" value="<bean:write name="index0"/>"/>
<input type="hidden" name="next0" id="next0" value="<bean:write name="next0"/>"/>
</form>
</body>
</html>