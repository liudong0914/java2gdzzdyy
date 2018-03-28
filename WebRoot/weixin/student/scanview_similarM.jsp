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
<title>查看举一反三</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style1.css" rel="stylesheet" type="text/css" />
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

<body>
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
String prebutton = (String)request.getAttribute("prebutton");
String nextbutton = (String)request.getAttribute("nextbutton");
%>
<form method="post" name="pageForm" >
<div id="container">
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
	        		<input type="radio" name="itemA_<%=x %>" value="<%=tqi.getOptionNo(i) %>"/>
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
	        		<input type="checkbox" name="itemB_<%=x %>" value="<%=tqi.getOptionNo(i) %>"/>
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
	        	<div class="container_answer_size_radio">
	        		<input type="radio" name="itemC_<%=x %>" value="1"/>
	        		<label>A、对</label><br/>
	            </div>
	            <div class="container_answer_size_radio">
	        		<input type="radio" name="itemC_<%=x %>" value="0"/>
	        		<label>B、错</label><br/>
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
    <div style="padding-bottom:55px;margin-top:10px;" id="true_button">
	    <a href="javascript:showAnswer()">
	   		<div class="botton3" style="width:99%;margin-top:10px;">
	   			<p>显示参考答案及解析</p>
	   		</div>
	   	</a>
   	</div>
    <div class="container_answer" id="answer_div" style="padding-bottom:55px;margin-top:10px;display:none;">
    	<div class="container_answer_size">
        	【参考答案】<%=model.getRightansName() %><br/><br/>
        	【解析】<%=model.getDescript() %>
        </div>
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