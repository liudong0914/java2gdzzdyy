<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkPaperAnswer"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>课前预习试题</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript" src="/weixin/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
function returnAnswer(answer){
	var finished = document.getElementById("finished").value;
	if(finished == '0'){
		//通过ajax提交数据，并显示答案和解析
		$.ajax({
	        type: "get",
	        async: false,
	        url: "/weixinStudent.app?method=answerBeforeClass&classid=${classid}&bookcontentid=${bookcontentid}&taskid=${taskid}&tasktype=${tasktype}&contentid=${model.questionid}&userid=${userid}&answer=" + answer + "&ram=" + Math.random(),
	        dataType: "text",
	        success: function(data){
	        	document.getElementById("finished").value = "1";
	        	document.getElementById("tk_descript").style.display = "block";
	        	document.getElementById("item_"+answer).checked = true;
	        	if(data == "1"){
	        		document.getElementById("option_"+answer).style.color = "green";
	        		document.getElementById("option_"+answer+"_style").innerHTML = "<span style='float:right;text-align:right;color:#009900;font-size:20px;'>√</span>";
				}else{
					document.getElementById("option_"+answer).style.color = "red";
					document.getElementById("option_"+answer+"_style").innerHTML = "<span style='float:right;text-align:right;color:#FF0000;font-size:20px;'>×</span>";
					document.getElementById("answer_show").style.color = "red";
				}
	        }
		});
	}
}
</script>
</head>

<body>
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinStudent.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">课前预习试题</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
String prebutton = (String)request.getAttribute("prebutton");
String nextbutton = (String)request.getAttribute("nextbutton");
TkPaperAnswer answer = (TkPaperAnswer)request.getAttribute("answer");
String display = "none";
if(answer != null) display = "block";
String answer_color = "red";//作答对错颜色标记
if(answer != null && model.getRightans().equals(answer.getAnswer())){
	answer_color = "green";
}
%>
<form method="post" name="pageForm" >
<div id="container" style="padding-bottom:55px;">
	<div class="container_question">
    	<div class="container_question_size">
           <span class="container_question_size_title">${index }.【<%=model.getQuestionno() %>】<%=model.getTitlecontent() %></span>
        </div>
    </div>
    <div class="container_answer">
    	<div class="container_answer_size">
        	<div class="container_answer_size_radio" onclick="returnAnswer('1')">
        		<input type="radio" name="item" id="item_1" value="1" <%if(answer != null && "1".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label id="option_1" <%if(answer != null && "1".equals(answer.getAnswer())){ %>style="color:<%=answer_color %>"<%} %>>A、对<a id="option_1_style"><%if(answer != null && "1".equals(answer.getAnswer())){ %><%if(model.getRightans().equals(answer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></a></label><br/>
            </div>
            <div class="container_answer_size_radio" onclick="returnAnswer('0')">
        		<input type="radio" name="item" id="item_0" value="0" <%if(answer != null && "0".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label id="option_0" <%if(answer != null && "0".equals(answer.getAnswer())){ %>style="color:<%=answer_color %>"<%} %>>B、错<a id="option_0_style"><%if(answer != null && "0".equals(answer.getAnswer())){ %><%if(model.getRightans().equals(answer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></a></label><br/>
            </div>
        </div>
    </div>
    <div class="container_answer" id="tk_descript" style="display:<%=display %>">
    	<div class="container_answer_size">
        	【参考答案】<a <%if(answer != null && !model.getRightans().equals(answer.getAnswer())){ %>style="color:red;"<%} %> id="answer_show"><%=model.getRightansName() %></a><br/><br/>
        	【解析】<%=model.getDescript() %>
        </div>
    </div>
    <%
    Integer filmCounts = (Integer)request.getAttribute("filmCounts");
    Integer similarCounts = (Integer)request.getAttribute("similarCounts");
    if(filmCounts.intValue() > 0 || similarCounts.intValue() > 0){
    %>
    <div class="conrainer_button">
    	<%if(similarCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=viewSimilar&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }&show=1&taskid=${taskid }&tasktype=${tasktype }&qid=${model.questionid }&vtype=1" class="conrainer_button1">举一反三</a><%} %>
        <%if(filmCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=videoPlay&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }&show=1&taskid=${taskid }&tasktype=${tasktype }&qid=${model.questionid }&vtype=1" class="conrainer_button1">看微课</a><%} %>
    </div>
    <%} %>
</div>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<div id="footer">
    <%if("1".equals(prebutton)){ %>
	<a href="/weixinStudent.app?method=doBeforeClass&userid=${userid }&bookcontentid=${bookcontentid }&classid=${classid }&index=${index-1 }&next=-1&show=1&taskid=${taskid }&tasktype=${tasktype }" class="footer_6">
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
	<a href="/weixinStudent.app?method=doBeforeClass&userid=${userid }&bookcontentid=${bookcontentid }&classid=${classid }&index=${index+1 }&next=1&show=1&taskid=${taskid }&tasktype=${tasktype }" class="footer_6">
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
<%if(answer != null){ %>
<input type="hidden" name="finished" id="finished" value="1"/>
<%}else{ %>
<input type="hidden" name="finished" id="finished" value="0"/>
<%} %>
</form>
</body>
</html>