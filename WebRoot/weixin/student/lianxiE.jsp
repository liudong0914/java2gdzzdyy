<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkPaperAnswer"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>在线作答</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
TkPaperAnswer answer = (TkPaperAnswer)request.getAttribute("answer");
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
	document.pageForm.action = "/weixinStudent.app?method=lianxi1&useranswer=" + value;
	document.pageForm.submit();
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
    		int optionsize = 1;
			if(model.getOptionnum() != null){
				optionsize = model.getOptionnum().intValue();
			}
    		if(paperAnswer != null && paperAnswer.length > 0){
    			for(int i=1, size=optionsize; i<=size; i++){
    		%>
        	<div class="container_answer_size_radio">
        		作答<%=i %>、<input type="text" name="item" id="item_<%=i %>" class="container_answer_size_radio_input" value="<%=paperAnswer[i-1] %>"/>
            </div>
            <%
            	}
    		}else{
            	for(int i=1, size=optionsize; i<=size; i++){
            %>
            <div class="container_answer_size_radio">
        		作答<%=i %>、<input type="text" name="item" id="item_<%=i %>" class="container_answer_size_radio_input" value=""/>
            </div>
            <%}} %>
        </div>
    </div>
    <div class="container_question" style="display:none;" id="tips_div">
    	<div class="container_question_size">
    		<span class="container_question_size_title" style="font-size:14px;color:red;">请作答完整！</span>
    	</div>
    </div>
    <a href="javascript:returnAnswer(<%=model.getOptionnum() %>)" id="true_button">
   		<div class="botton3" style="width:99%;margin-top:10px;">
   			<p>确定</p>
   		</div>
   	</a>
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