<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>学案试题</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function showAnswer(){
	document.getElementById("true_button").style.display = "none";
	document.getElementById("answer_div").style.display = "block";
}
</script>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">学案试题</p>
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
    		<%
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    		%>
        	<div class="container_answer_size_radio" >
        		<input type="radio" name="item" value="<%=model.getOptionNo(i) %>"/>
        		<label><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %></label><br/>
            </div>
            <%} %>
        </div>
    </div>
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
     <%
    Integer filmCounts = (Integer)request.getAttribute("filmCounts");
    Integer similarCounts = (Integer)request.getAttribute("similarCounts");
    if(filmCounts.intValue() > 0 || similarCounts.intValue() > 0){
    %>
    <div class="conrainer_button">
    	<%if(similarCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=viewSimilar&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }&show=1&qid=${model.questionid }&vtype=7" class="conrainer_button1">举一反三</a><%} %>
        <%if(filmCounts.intValue() > 0){ %><a href="/weixinStudent.app?method=videoPlay&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }&show=1&qid=${model.questionid }&vtype=7" class="conrainer_button1">看微课</a><%} %>
    </div>
    <%} %>
</div>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<div id="footer">
    <%if("1".equals(prebutton)){ %>
	<a href="/weixinTeacher.app?method=preparationQuestion2&userid=${userid }&bookcontentid=${bookcontentid }&classid=${classid }&index=${index-1 }&next=-1&show=1" class="footer_6">
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
	<a href="/weixinTeacher.app?method=preparationQuestion2&userid=${userid }&bookcontentid=${bookcontentid }&classid=${classid }&index=${index+1 }&next=1&show=1" class="footer_6">
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
</form>
</body>
</html>