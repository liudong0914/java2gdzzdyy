<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkPaperAnswer"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsType"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>在线作答</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style1.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
TkPaperAnswer answer = (TkPaperAnswer)request.getAttribute("answer");
%>
<script type="text/javascript">
function returnAnswer(childcounts){
	var value = "";
	var optionnum = 0;
	var type = "";
	for(var i=0;i<childcounts;i++){
		optionnum = document.getElementById("hiddenitem_optionnum_"+i).value
		type = document.getElementById("hiddenitem_type_"+i).value
		if(type == "A" || type == "C"){
			var temp = "0";
			for(var m=1; m<=optionnum; m++){
				if(document.getElementById("item_"+i+"_"+m).checked == true){
					temp = "1";
					value = value + "【】" + document.getElementById("item_"+i+"_"+m).value;
					break;
				}
			}
			if(temp == "0"){
				document.getElementById("tips_div").style.display = "block";
				return ;
			}
		}else if(type == "B"){
			var checkedvalue = "";
			for(var m=1;m<=optionnum;m++){
				if (document.getElementById("item_"+i+"_"+m).checked == true){
					checkedvalue = checkedvalue + document.getElementById("item_"+i+"_"+m).value;
				}
			}
			if(checkedvalue == ""){
				document.getElementById("tips_div").style.display = "block";
				return ;
			}
			value = value + "【】" + checkedvalue;
		}else{
			var tempvalue = "";
			for(var m=1;m<=optionnum;m++){
				if (document.getElementById("item_"+i+"_"+m).value != ""){
					if(m == 1){
						tempvalue = document.getElementById("item_"+i+"_"+m).value;
					}else{
						tempvalue = tempvalue + "【;】" + document.getElementById("item_"+i+"_"+m).value;
					}
				}else{
					document.getElementById("tips_div").style.display = "block";
					return ;
				}
			}
			value = value + "【】" + tempvalue;
		}
	}
	if(value.length > 2){
		value = value.substring(2);
	}
	document.getElementById("tips_div").style.display = "none";
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
	<div class="conrainer_bz">
    	<div class="container_bz_size">
    		<span>${index }.【<%=model.getQuestionno() %>】<%=model.getTitlecontent() %></span>
        </div>
    </div>
    <div class="box_question">
    	<%
    	List childList = (List)request.getAttribute("childList");
    	List allChildAnswerList = (List)request.getAttribute("allChildAnswerList");
    	Map childAnswerMap = (Map)request.getAttribute("childAnswerMap");
    	TkQuestionsInfo tqi = null;
    	TkQuestionsType tqt = null;
    	TkPaperAnswer childAnswer = null;
    	String[] paperAnswer = null;
    	for(int x=0, y=childList.size(); x<y; x++){
    		tqi = (TkQuestionsInfo)childList.get(x);
    		tqt = tqi.getTkQuestionsType();
    		childAnswer = (TkPaperAnswer)childAnswerMap.get(tqi.getQuestionid());
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
	        		<input type="radio" name="itemA_<%=x %>" id="item_<%=x %>_<%=i %>" value="<%=tqi.getOptionNo(i) %>" <%if(childAnswer != null && tqi.getOptionNo(i).equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label id="option_<%=x %>_<%=i %>" ><%=tqi.getOptionNo(i) %>、<%=tqi.getOptionValue(i) %></label><br/>
	            </div>
	            <%} %>
        	</div>
    	</div>
    	<!-- 每道题放个隐藏域选项个数和类型供判断 -->
    	<input type="hidden" name="hiddenitem_optionnum_<%=x %>" id="hiddenitem_optionnum_<%=x %>" value="<%=tqi.getOptionnum() %>"/>
    	<input type="hidden" name="hiddenitem_type_<%=x %>" id="hiddenitem_type_<%=x %>" value="A"/>
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
	        		<input type="checkbox" name="itemB_<%=x %>" id="item_<%=x %>_<%=i %>" value="<%=tqi.getOptionNo(i) %>" <%if(childAnswer != null && childAnswer.getAnswer().indexOf(tqi.getOptionNo(i)) != -1){ %>checked="checked"<%} %>/>
	        		<label id="option_<%=x %>_<%=i %>"><%=tqi.getOptionNo(i) %>、<%=tqi.getOptionValue(i) %></label><br/>
	            </div>
	            <%} %>
        	</div>
    	</div>
    	<!-- 每道题放个隐藏域选项个数和类型供判断 -->
    	<input type="hidden" name="hiddenitem_optionnum_<%=x %>" id="hiddenitem_optionnum_<%=x %>" value="<%=tqi.getOptionnum() %>"/>
    	<input type="hidden" name="hiddenitem_type_<%=x %>" id="hiddenitem_type_<%=x %>" value="B"/>
    	<%}else if("C".equals(tqt.getType())){//判断 %>
    	<div class="container_question">
	    	<div class="container_question_size">
	           <span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span>
	        </div>
	    </div>
	    <div class="container_answer">
	    	<div class="container_answer_size">
	        	<div class="container_answer_size_radio" onclick="changeRadio(2, <%=x %>, 1)">
	        		<input type="radio" name="itemC_<%=x %>" id="item_<%=x %>_1" value="1" <%if(childAnswer != null && "1".equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label id="option_<%=x %>_1">A、对</label><br/>
	            </div>
	            <div class="container_answer_size_radio">
	        		<input type="radio" name="itemC_<%=x %>" id="item_<%=x %>_2" value="0" <%if(childAnswer != null && "0".equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label id="option_<%=x %>_2">B、错</label><br/>
	            </div>
	        </div>
	    </div>
	    <!-- 每道题放个隐藏域选项个数和类型供判断 -->
    	<input type="hidden" name="hiddenitem_optionnum_<%=x %>" id="hiddenitem_optionnum_<%=x %>" value="2"/>
    	<input type="hidden" name="hiddenitem_type_<%=x %>" id="hiddenitem_type_<%=x %>" value="C"/>
    	<%}else if("E".equals(tqt.getType())){//填空 %>
    	<div class="container_question">
	    	<div class="container_question_size">
	           <span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span>
	        </div>
	    </div>
	    <div class="container_answer">
	    	<div class="container_answer_size">
	    		<%
	    		if(childAnswer != null){
		    		paperAnswer = childAnswer.getAnswer().split("【】");
		            for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
	            %>
	            <div class="container_answer_size_radio">
	        		作答<%=i %>、<input type="text" name="itemE" id="item_<%=x %>_<%=i %>" class="container_answer_size_radio_input" value="<%=paperAnswer[i-1] %>"/>
	            </div>
	            <%
	            	}
		        }else{
		        	for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
		        %>
	            <div class="container_answer_size_radio">
	        		作答<%=i %>、<input type="text" name="itemE" id="item_<%=x %>_<%=i %>" class="container_answer_size_radio_input" value=""/>
	            </div>
	            <%}} %>
	        </div>
	    </div>
	    <!-- 每道题放个隐藏域选项个数和类型供判断 -->
    	<input type="hidden" name="hiddenitem_optionnum_<%=x %>" id="hiddenitem_optionnum_<%=x %>" value="<%=tqi.getOptionnum() %>"/>
    	<input type="hidden" name="hiddenitem_type_<%=x %>" id="hiddenitem_type_<%=x %>" value="E"/>
    	<%} %>
    	<%} %>
    <div class="container_question" style="display:none;" id="tips_div">
    	<div class="container_question_size">
    		<span class="container_question_size_title" style="font-size:14px;color:red;">请作答完整！</span>
    	</div>
    </div>
    <a href="javascript:returnAnswer(<%=childList.size() %>)" id="true_button">
		<div class="botton3" style="width:99%;margin-top:10px;">
			<p>确定</p>
		</div>
	</a>
    <div class="container_answer" style="padding-bottom:55px;"></div>
    </div>
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