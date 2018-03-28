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
<title>在线做题</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function returnAnswer(answer){
	document.getElementById("item_"+answer).checked = true;
	document.pageForm.action = "/weixinStudent.app?method=lianxi1&useranswer=" + answer;
	document.pageForm.submit();
}
//多选题提交
function returnAnswer1(select_option){
	var checkedvalue = "";
	for(var i=0;i<select_option;i++){
		if (document.pageForm.item[i].checked == true){
			checkedvalue = checkedvalue + document.pageForm.item[i].value;
		}
	}
	document.pageForm.action = "/weixinStudent.app?method=lianxi1&useranswer=" + checkedvalue;
	document.pageForm.submit();
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
function finish(){
	document.pageForm.action = "/weixinStudent.app?method=finishConfirm";
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
    	<p class="search_wk_p">在线做题</p>
    </div>
</div>
<div class="search_padding"></div>
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
String prebutton = (String)request.getAttribute("prebutton");
String nextbutton = (String)request.getAttribute("nextbutton");
TkPaperAnswer answer = (TkPaperAnswer)request.getAttribute("answer");
%>
<form method="post" name="pageForm" >
<div id="container" style="padding-bottom:55px;">
	<div class="container_question">
    	<div class="container_question_size">
           <span class="container_question_size_title">${index }.【<%=model.getQuestionno() %>】<%=model.getTitlecontent() %></span>
        </div>
    </div>
    <%
    TkQuestionsType type = model.getTkQuestionsType();
    if("A".equals(type.getType())){
    %>
    <div class="container_answer">
    	<div class="container_answer_size">
    		<%
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    			//当选项有图片并监听js方法时，和div监听的js方法有冲突，故在此判断
    			if(model.getOptionValue(i).indexOf("<img ") == -1){
    		%>
        	<div class="container_answer_size_radio" onclick="returnAnswer('<%=model.getOptionNo(i) %>')">
        		<input type="radio" name="item" id="item_<%=model.getOptionNo(i) %>" value="<%=model.getOptionNo(i) %>" <%if(answer != null && model.getOptionNo(i).equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label id="option_<%=model.getOptionNo(i) %>"><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %></label><br/>
            </div>
            <%}else{ %>
            <div class="container_answer_size_radio">
        		<input type="radio" name="item" onclick="returnAnswer('<%=model.getOptionNo(i) %>')" id="item_<%=model.getOptionNo(i) %>" value="<%=model.getOptionNo(i) %>" <%if(answer != null && model.getOptionNo(i).equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label id="option_<%=model.getOptionNo(i) %>"><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %></label><br/>
            </div>
            <%}} %>
        </div>
    </div>
    <%}else if("B".equals(type.getType())){ %>
    <div class="container_answer">
    	<div class="container_answer_size">
    		<%
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    		%>
        	<div class="container_answer_size_radio" onclick="changeChecked('<%=model.getOptionNo(i) %>', '1')">
        		<input type="checkbox" onclick="changeChecked0('<%=model.getOptionNo(i) %>')" name="item" id="item_<%=model.getOptionNo(i) %>" value="<%=model.getOptionNo(i) %>" <%if(answer != null && answer.getAnswer().indexOf(model.getOptionNo(i)) != -1){ %>checked="checked"<%} %>/>
        		<label id="option_<%=model.getOptionNo(i) %>"><%=model.getOptionNo(i) %>、<%=model.getOptionValue(i) %></label><br/>
            </div>
            <%} %>
        </div>
    </div>
    <%}else if("C".equals(type.getType())){ %>
    <div class="container_answer">
    	<div class="container_answer_size">
        	<div class="container_answer_size_radio" onclick="returnAnswer('1')">
        		<input type="radio" name="item" id="item_1" value="1" <%if(answer != null && "1".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label id="option_1">A、对</label><br/>
            </div>
            <div class="container_answer_size_radio" onclick="returnAnswer('0')">
        		<input type="radio" name="item" id="item_0" value="0" <%if(answer != null && "0".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label id="option_0">B、错</label><br/>
            </div>
        </div>
    </div>
    <%} %>
    <%if("B".equals(type.getType())){ %>
    <a href="javascript:returnAnswer1(<%=model.getOptionnum() %>)">
   		<div class="botton3" style="width:99%;margin-top:10px;">
   			<p>确定</p>
   		</div>
   	</a>
   	<%} %>
</div>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<div id="footer">
    <%if("1".equals(prebutton)){ %>
	<a href="/weixinStudent.app?method=lianxi1&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index-1 }&next=-1&taskid=${taskid }&tasktype=${tasktype }" class="footer_0">
    	<img src="/weixin/images/syt.png" />
    	<p>上一题</p>
    </a>
    <%}else{ %>
    <a class="footer_0">
    	<img src="/weixin/images/syt0.png" />
    	<p style="color:#777;">上一题</p>
    </a>
    <%} %>
    <%if("1".equals(nextbutton)){ %>
	<a href="/weixinStudent.app?method=lianxi1&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index+1 }&next=1&taskid=${taskid }&tasktype=${tasktype }" class="footer_0">
    	<img src="/weixin/images/xyt.png" />
    	<p>下一题</p>
    </a>
    <%}else{ %>
    <a class="footer_0">
    	<img src="/weixin/images/xyt0.png" />
    	<p style="color:#777;">下一题</p>
    </a>
    <%} %>
    <a href="javascript:finish();" class="footer_00">
    	<img src="/weixin/images/tjzy.png" />
    	<p>提交</p>
    </a>
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