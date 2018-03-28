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
<%TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model"); %>
<script type="text/javascript">
function returnAnswer(optionnum){
	var checkedvalue = "";
	for(var i=0;i<optionnum;i++){
		if (document.pageForm.item[i].checked == true){
			checkedvalue = checkedvalue + document.pageForm.item[i].value;
		}
	}
	//通过ajax提交数据，并显示答案和解析
	$.ajax({
        type: "get",
        async: false,
        url: "/weixinStudent.app?method=answerBeforeClass&classid=${classid}&bookcontentid=${bookcontentid}&taskid=${taskid}&tasktype=${tasktype}&contentid=${model.questionid}&userid=${userid}&answer=" + checkedvalue + "&ram=" + Math.random(),
        dataType: "text",
        success: function(data){
        	document.getElementById("tk_descript").style.display = "block";
        	document.getElementById("true_button").style.display = "none";
        	var rightAnswer = '<%=model.getRightans() %>';
        	for(var i=0;i<optionnum;i++){
    			if (document.pageForm.item[i].checked == true){
    				if(rightAnswer.indexOf(document.pageForm.item[i].value) != -1){
    					document.getElementById("option_"+document.pageForm.item[i].value).style.color = "green";
    					document.getElementById("option_"+document.pageForm.item[i].value+"_style").innerHTML = "<span style='float:right;text-align:right;color:#009900;font-size:20px;'>√</span>";
    				}else{
    					document.getElementById("option_"+document.pageForm.item[i].value).style.color = "red";
    					document.getElementById("option_"+document.pageForm.item[i].value+"_style").innerHTML = "<span style='float:right;text-align:right;color:#FF0000;font-size:20px;'>×</span>";
    				}
    			}
    		}
        	if(data != "1"){
        		document.getElementById("answer_show").style.color = "red";
			}
        }
	});
}
function changeChecked(item, tag){
	if(tag == '1'){
		var item = document.getElementById("item_" + item);
		if(item.checked == true){
			item.checked = false;
		}else{
			item.checked = true;
		}
	}
}
function changeChecked0(item){
	changeChecked(item, '1');
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
TkPaperAnswer answer = (TkPaperAnswer)request.getAttribute("answer");
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
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    		%>
        	<div class="container_answer_size_radio" onclick="changeChecked('<%=model.getOptionNo(i) %>', '1')">
        		<input type="checkbox" onclick="changeChecked0('<%=model.getOptionNo(i) %>')" name="item" id="item_<%=model.getOptionNo(i) %>" value="<%=model.getOptionNo(i) %>" <%if(answer != null && answer.getAnswer().indexOf(model.getOptionNo(i)) != -1){ %>checked="checked"<%} %>/>
        		<label id="option_<%=model.getOptionNo(i) %>" <%if(answer != null && answer.getAnswer().indexOf(model.getOptionNo(i)) != -1){ %><%if(model.getRightans().indexOf(model.getOptionNo(i)) != -1){ %>style="color:green"<%}else{%>style="color:red"<%}} %>><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %><a id="option_<%=model.getOptionNo(i) %>_style"><%if(answer != null && answer.getAnswer().indexOf(model.getOptionNo(i)) != -1){ %><%if(model.getRightans().indexOf(model.getOptionNo(i)) != -1){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></a></label><br/>
            </div>
            <%} %>
        </div>
    </div>
    <%if(answer == null){ %>
    <a href="javascript:returnAnswer(<%=model.getOptionnum() %>)" id="true_button">
   		<div class="botton3" style="width:99%;margin-top:10px;">
   			<p>确定</p>
   		</div>
   	</a>
   	<%} %>
    <div class="container_answer" id="tk_descript" style="display:<%=display %>">
    	<div class="container_answer_size">
        	【参考答案】<a <%if(answer != null && !model.getRightans().equals(answer.getAnswer())){ %>style="color:red;"<%} %> id="answer_show"><%=model.getRightans() %></a><br/><br/>
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