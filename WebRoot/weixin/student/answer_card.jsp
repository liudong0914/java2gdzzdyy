<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.tk.bo.TkPaperContent"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsType"%>
<%@page import="com.wkmk.tk.bo.TkPaperAnswer"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>填答题卡</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function finishAnswerCard(){
	document.pageForm.action = "/weixinStudent.app?method=finishAnswerCard";
	document.pageForm.submit();
}
function hidetext(){
	var mychar = document.getElementById("con");
	mychar.style.display="none"
}  
function showtext(){
	var mychar = document.getElementById("con");
	mychar.style.display="block"
}
function goBack(){
	document.pageForm.action = "/weixinStudent.app?method=bookContent";
	document.pageForm.submit();
}
</script>
</head>

<body>
<!-------------弹出确认窗口----------->
<div class="fade1" id="con">
	<div class="tanchu_wx">   	
		<div class="tanchu_fade">
         	<div class="tanchu_fade_font">
         		<p>请确定答题卡全部填写完成，确认提交答题卡吗？</p>
         		<div class="tanchu_fade_font_form">
             		<input type="button" value="确定" onClick="finishAnswerCard()"/>
                 	<input type="button" value="取消" onClick="hidetext()"/>
                </div>
             </div>
         </div>     
     </div>
</div>
<%
String iscommit = (String)request.getAttribute("iscommit");
if(!"1".equals(iscommit)){
%>
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="javascript:goBack();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">填答题卡</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<%} %>
<form method="post" name="pageForm" >
<div id="container_answer_card">
	<%
	List questionList = (List)request.getAttribute("questionList");
	Map questionMap = (Map)request.getAttribute("questionMap");
	Map childMap = (Map)request.getAttribute("childMap");
	List childList = null;
	Map answerMap = (Map)request.getAttribute("answerMap");
	TkPaperContent paperContent = null;
	TkQuestionsInfo model = null;
	TkQuestionsType type = null;
	TkQuestionsInfo child = null;
	TkQuestionsType childType = null;
	TkPaperAnswer answer = null;
	String[] paperAnswer = null;
	for(int x=0, y=questionList.size(); x<y; x++){
		paperContent = (TkPaperContent)questionList.get(x);
		model = (TkQuestionsInfo)questionMap.get(paperContent.getQuestionid());
		type = model.getTkQuestionsType();
	%>
	<%
	if("A".equals(type.getType()) || "B".equals(type.getType()) || "C".equals(type.getType())){ 
		answer = (TkPaperAnswer)answerMap.get(paperContent.getContentid() + "_0");
	%>
	<div class="container_answer_card1">
    	<div class="container_answer_card1_size">
        	<p><%=x+1 %>、</p>
        	<%if("A".equals(type.getType())){ %>
        	<%
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    		%>
        	<div class="container_answer_card1_size_input">
        		<input type="radio" name="item_<%=x %>" id="item_<%=x %>_<%=i %>" value="<%=model.getOptionNo(i) %>" <%if(answer != null && model.getOptionNo(i).equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label class="container_answer_card1_size_radio"><%=model.getOptionNo(i) %></label>
            </div>
            <%} %>
        	<%}else if("B".equals(type.getType())){ %>
        	<%
    		for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
    		%>
        	<div class="container_answer_card1_size_input">
        		<input type="checkbox" name="item_<%=x %>" id="item_<%=x %>_<%=i %>" value="<%=model.getOptionNo(i) %>" <%if(answer != null && answer.getAnswer().indexOf(model.getOptionNo(i)) != -1){ %>checked="checked"<%} %>/>
        		<label class="container_answer_card1_size_radio" ><%=model.getOptionNo(i) %></label>
            </div>
            <%} %>
        	<%}else{ %>
        	<div class="container_answer_card1_size_input">
        		<input type="radio" name="item_<%=x %>" id="item_<%=x %>_1" value="1" <%if(answer != null && "1".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label class="container_answer_card1_size_radio">对</label><br/>
            </div>
            <div class="container_answer_card1_size_input">
        		<input type="radio" name="item_<%=x %>" id="item_<%=x %>_2" value="0" <%if(answer != null && "0".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
        		<label class="container_answer_card1_size_radio">错</label><br/>
            </div>
        	<%} %>
        </div>
    </div>
    <%}else if("F".equals(type.getType()) || "M".equals(type.getType())){ %>
    <div class="container_answer_card3">
    	<div class="container_answer_card3_size">
        	<p><%=x+1 %>、</p>
            <div class="container_answer_card3_right">
            	<%
            	childList = (List)childMap.get(paperContent.getQuestionid());
            	if(childList == null || childList.size() == 0) continue;
            	for(int m=0, n=childList.size(); m<n; m++){
            		child = (TkQuestionsInfo)childList.get(m);
            		answer = (TkPaperAnswer)answerMap.get(paperContent.getContentid() + "_" + child.getQuestionid());
            		childType = child.getTkQuestionsType();
            	%>
            	<%if("A".equals(childType.getType()) || "B".equals(childType.getType()) || "C".equals(childType.getType())){ %>
            	<div class="container_answer_card3_right_div">
        			<span><%=m+1 %>)</span>
        			<%if("A".equals(childType.getType())){ %>
        			<%
		    		for(int i=1, size=child.getOptionnum().intValue(); i<=size; i++){
		    		%>
		        	<div class="container_answer_card3_size_input">
		        		<input type="radio" name="item_<%=x %>_<%=m %>" id="item_<%=x %>_<%=m %>_<%=i %>" value="<%=child.getOptionNo(i) %>" <%if(answer != null && child.getOptionNo(i).equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
		        		<label class="container_answer_card3_size_radio"><%=child.getOptionNo(i) %></label>
		            </div>
		            <%} %>
		            <%}else if("B".equals(childType.getType())){ %>
		            <%
		    		for(int i=1, size=child.getOptionnum().intValue(); i<=size; i++){
		    		%>
		        	<div class="container_answer_card3_size_input">
		        		<input type="checkbox" name="item_<%=x %>_<%=m %>" id="item_<%=x %>_<%=m %>_<%=i %>" value="<%=child.getOptionNo(i) %>" <%if(answer != null && answer.getAnswer().indexOf(child.getOptionNo(i)) != -1){ %>checked="checked"<%} %>/>
		        		<label class="container_answer_card3_size_radio"><%=child.getOptionNo(i) %></label>
		            </div>
		            <%} %>
		            <%}else{ %>
		            <div class="container_answer_card3_size_input">
		        		<input type="radio" name="item_<%=x %>_<%=m %>" id="item_<%=x %>_<%=m %>_1" value="1" <%if(answer != null && "1".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
		        		<label class="container_answer_card3_size_radio">对</label><br/>
		            </div>
		            <div class="container_answer_card3_size_input">
		        		<input type="radio" name="item_<%=x %>_<%=m %>" id="item_<%=x %>_<%=m %>_2" value="0" <%if(answer != null && "0".equals(answer.getAnswer())){ %>checked="checked"<%} %>/>
		        		<label class="container_answer_card3_size_radio">错</label><br/>
		            </div>
		            <%} %>
                </div>
                <%}else{ %>
                <div class="container_answer_card3_right_div">
        			<span><%=m+1 %>)</span>
                    <div class="container_answer_card4_right">
                    	<%
                    	if(child.getOptionnum().intValue() > 1){ 
                    		if(answer != null){
                    			paperAnswer = answer.getAnswer().split("【】");
                    		}
                    	%>
                    	<%
		            	for(int i=1, size=child.getOptionnum().intValue(); i<=size; i++){
			    		%>
			    			<%if(paperAnswer != null && paperAnswer.length > 0){ %>
			        		<input type="text" name="item_<%=x %>_<%=m %>_<%=i %>" id="item_<%=x %>_<%=m %>_<%=i %>" class="container_answer_card3_size_text" value="<%=paperAnswer[i-1] %>"/>
			        		<%}else{ %>
			        		<input type="text" name="item_<%=x %>_<%=m %>_<%=i %>" id="item_<%=x %>_<%=m %>_<%=i %>" class="container_answer_card3_size_text" value=""/>
			        		<%} %>
			            <%} %>
                    	<%}else{ %>
            			<input type="text" name="item_<%=x %>_<%=m %>_1" id="item_<%=x %>_<%=m %>_1" class="container_answer_card4_size_text" <%if(answer != null){%>value="<%=answer.getAnswer() %>"<%}else{ %>value=""<%} %>/>
            			<%} %>
                    </div>
                </div>
                <%} %>
                <%} %>
            </div>
        </div>
    </div>
    <%
    }else{ 
    	answer = (TkPaperAnswer)answerMap.get(paperContent.getContentid() + "_0");
    %>
    <%
    if(model.getOptionnum().intValue() > 1){ 
		if(answer != null){
			paperAnswer = answer.getAnswer().split("【】");
		}
    %>
    <div class="container_answer_card3">
    	<div class="container_answer_card3_size">
        	<p><%=x+1 %>、</p>
            <div class="container_answer_card3_right">
            	<%
            	for(int i=1, size=model.getOptionnum().intValue(); i<=size; i++){
	    		%>
	    			<%if(paperAnswer != null && paperAnswer.length > 0){ %>
	        		<input type="text" name="item_<%=x %>_<%=i %>" id="item_<%=x %>_<%=i %>" class="container_answer_card3_size_text" value="<%=paperAnswer[i-1] %>"/>
	        		<%}else{ %>
	        		<input type="text" name="item_<%=x %>_<%=i %>" id="item_<%=x %>_<%=i %>" class="container_answer_card3_size_text" value=""/>
	        		<%} %>
	            <%} %>
            </div>
        </div>
    </div>
    <%}else{ %>
    <div class="container_answer_card2">
    	<div class="container_answer_card2_size">
        	<p><%=x+1 %>、</p>
        	<input type="text" name="item_<%=x %>_1" id="item_<%=x %>_1" class="container_answer_card2_size_text" <%if(answer != null){%>value="<%=answer.getAnswer() %>"<%}else{ %>value=""<%} %>/>
        </div>
    </div>
    <%} %>
    <%} %>
    <%} %>
</div>
<%
if("1".equals(iscommit)){
%>
<div id="foot">
	<a href="javascript:showtext();" class="foot_1" style="width:100%">
    	<img src="/weixin/images/dtk.png" />
    	<p>提交答题卡</p>
    </a>
</div>
<%} %>
<input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
<input type="hidden" name="tasktype" value="<bean:write name="tasktype"/>"/>
<input type="hidden" name="taskid" value="<bean:write name="taskid"/>"/>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
</form>
</body>
</html>