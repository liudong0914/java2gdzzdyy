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
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
TkPaperAnswer answer = (TkPaperAnswer)request.getAttribute("answer");
String[] rightAnswer = model.getRightans().split("【】");
String[] paperAnswer = null;
if(answer != null){
	paperAnswer = answer.getAnswer().split("【】");
}
%>
<script type="text/javascript">
function returnAnswer(optionnum){
	var value = "";
	if(optionnum > 1){
		for(var i=1;i<=optionnum;i++){
			if (document.getElementById("item_"+i).value != ""){
				if(i == 1){
					value = document.getElementById("item_"+i).value;
				}else{
					value = value + "【】" + document.getElementById("item_"+i).value;
				}
			}else{
				document.getElementById("tips_div").style.display = "block";
				return ;
			}
		}
	}else{
		if (document.getElementById("item_1").value != ""){
			value = document.getElementById("item_1").value;
		}else{
			document.getElementById("tips_div").style.display = "block";
			return ;
		}
	}
	//通过ajax提交数据，并显示答案和解析
	$.ajax({
        type: "get",
        async: false,
        url: "/weixinStudent.app?method=answerBeforeClassE&classid=${classid}&bookcontentid=${bookcontentid}&taskid=${taskid}&tasktype=${tasktype}&contentid=${model.questionid}&userid=${userid}&answer=" + encodeURI(encodeURI(value)) + "&ram=" + Math.random(),
        dataType: "text",
        success: function(data){
        	document.getElementById("tk_descript").style.display = "block";
        	document.getElementById("true_button").style.display = "none";
        	document.getElementById("tips_div").style.display = "none";
        	var rightAnswer = '<%=model.getRightans() %>';
        	if(optionnum > 1){
        		var rightAnswers = rightAnswer.split("【】");
	        	for(var i=1;i<=optionnum;i++){
	   				if(getIsRight(rightAnswers[i-1], document.getElementById("item_"+i).value) == 1){
	   					document.getElementById("item_"+i).style.color = "green";
	   					document.getElementById("option_"+i+"_style").innerHTML = "<span style='float:right;text-align:right;color:#009900;font-size:20px;'>√</span>";
	   				}else{
	   					document.getElementById("item_"+i).style.color = "red";
	   					document.getElementById("option_"+i+"_style").innerHTML = "<span style='float:right;text-align:right;color:#FF0000;font-size:20px;'>×</span>";
	   				}
	    		}
        	}else{
        		if(document.getElementById("item_1").value == rightAnswer){
   					document.getElementById("item_1").style.color = "green";
   					document.getElementById("option_1_style").innerHTML = "<span style='float:right;text-align:right;color:#009900;font-size:20px;'>√</span>";
   				}else{
   					document.getElementById("item_1").style.color = "red";
   					document.getElementById("option_1_style").innerHTML = "<span style='float:right;text-align:right;color:#FF0000;font-size:20px;'>×</span>";
   				}
        	}
        	if(data != "1"){
        		document.getElementById("answer_show").style.color = "red";
			}
        }
	});
}
function getIsRight(rightanswer, answer){
	if(rightanswer != "" && answer != ""){
		var rights = rightanswer.split("【或】");
		var result = "0";
		for(var i=0, size=rights.length; i<size; i++){
			if(rights[i] == answer){
				result = "1";
				break;
			}
		}
		return result;
	}else {
		return "0";
	}
}
function showAnswer(){
	document.getElementById("tk_descript").style.display = "block";
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
String prebutton = (String)request.getAttribute("prebutton");
String nextbutton = (String)request.getAttribute("nextbutton");
String display = "none";
if(answer != null) display = "block";
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
    		if(paperAnswer != null && paperAnswer.length > 0){
    			boolean isright = false;
    			for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    				isright = model.getIsRight(rightAnswer[i-1], paperAnswer[i-1]);
    		%>
        	<div class="container_answer_size_radio">
        		作答<%=i %>、<input type="text" name="item" id="item_<%=i %>" class="container_answer_size_radio_input" value="<%=paperAnswer[i-1] %>" <%if(isright){ %>style="color:green"<%}else{%>style="color:red"<%} %>/><%if(isright){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%} %>
            </div>
            <%
            	}
    		}else{
            	for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
            %>
            <div class="container_answer_size_radio">
        		作答<%=i %>、<input type="text" name="item" id="item_<%=i %>" class="container_answer_size_radio_input" value=""/><a id="option_<%=i %>_style"></a>
            </div>
            <%}} %>
        </div>
    </div>
    <div class="container_question" style="display:none;" id="tips_div">
    	<div class="container_question_size">
    		<span class="container_question_size_title" style="font-size:14px;color:red;">请作答完整！</span>
    	</div>
    </div>
    <%if(answer == null){ %>
    <%if(model.getOptionnum().intValue() > 0){ %>
    <a href="javascript:returnAnswer(<%=model.getOptionnum() %>)" id="true_button">
   		<div class="botton3" style="width:99%;margin-top:10px;">
   			<p>确定</p>
   		</div>
   	</a>
   	<%}else{ %>
   	<a href="javascript:showAnswer()" id="true_button">
   		<div class="botton3" style="width:99%;margin-top:10px;">
   			<p>显示参考答案及解析</p>
   		</div>
   	</a>
   	<%} %>
   	<%} %>
    <div class="container_answer" id="tk_descript" style="display:<%=display %>">
    	<div class="container_answer_size">
        	【参考答案】<a <%if(answer != null && !"1".equals(answer.getIsright())){ %>style="color:red;"<%} %> id="answer_show"><%=model.getRightansName() %></a><br/><br/>
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
</form>
</body>
</html>