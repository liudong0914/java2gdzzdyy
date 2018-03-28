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
<title>课前预习试题</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style1.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript" src="/weixin/js/jquery-2.1.1.min.js"></script>
<%
TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model");
TkPaperAnswer answer = (TkPaperAnswer)request.getAttribute("answer");
%>
<script type="text/javascript">
function returnAnswer(childcounts){
	var value = "";
	for(var i=0;i<childcounts;i++){
		if (document.getElementById("hiddenitem_"+i).value != ""){
			if(i == 0){
				value = document.getElementById("hiddenitem_"+i).value;
			}else{
				value = value + "【】" + document.getElementById("hiddenitem_"+i).value;
			}
		}else{
			document.getElementById("tips_div").style.display = "block";
			return ;
		}
	}
	//通过ajax提交数据，并显示答案和解析
	$.ajax({
        type: "get",
        async: false,
        url: "/weixinStudent.app?method=answerBeforeClassM&classid=${classid}&bookcontentid=${bookcontentid}&taskid=${taskid}&tasktype=${tasktype}&contentid=${model.questionid}&userid=${userid}&answer=" + encodeURI(encodeURI(value)) + "&ram=" + Math.random(),
        dataType: "text",
        success: function(data){
        	document.getElementById("tk_descript").style.display = "block";
        	document.getElementById("true_button").style.display = "none";
        	document.getElementById("tips_div").style.display = "none";
        	var answer = data.split(",");
        	for(var i=0;i<childcounts;i++){
   				if(answer[i] == 1){
   					document.getElementById("option_"+i+"_style").innerHTML = "<span style='float:right;text-align:right;color:#009900;font-size:20px;'>√</span>";
   				}else{
   					document.getElementById("option_"+i+"_style").innerHTML = "<span style='float:right;text-align:right;color:#FF0000;font-size:20px;'>×</span>";
   				}
    		}
        	if(data.indexOf("0") != -1){
        		document.getElementById("answer_show").style.color = "red";
			}
        }
	});
}
function changeRadio(count, cur0, cur){
	for(var i=1;i<=count;i++){
		document.getElementById("item_" + cur0 + "_" + i).checked = false;
	}
	document.getElementById("item_" + cur0 + "_" + cur).checked = true;
	document.getElementById("hiddenitem_" + cur0).value = document.getElementById("item_" + cur0 + "_" + cur).value;
}
function changeChecked(cur0, cur, tag){
	if(tag == '1'){
		var item = document.getElementById("item_" + cur0 + "_" + cur);
		if(item.checked == true){
			item.checked = false;
			var str = document.getElementById("hiddenitem_" + cur0).value;
			var curvalue = document.getElementById("item_" + cur0 + "_" + cur).value;
	     	var index = str.indexOf(curvalue);
	     	if(index==0){
	        	str=str.replace(curvalue,"");
	     	}else{
	        	str=str.replace(","+curvalue,"");
	     	}
	     	if(str.substring(0,1)==","){
	         	str=str.substring(1);
	     	}
	     	document.getElementById("hiddenitem_" + cur0).value = str;
		}else{
			item.checked = true;
			var hiddenitem= document.getElementById("hiddenitem_" + cur0);
			var curvalue = document.getElementById("item_" + cur0 + "_" + cur).value;
			if(hiddenitem.value == ""){
				hiddenitem.value = curvalue;
			}else{
				if(hiddenitem.value.indexOf(curvalue)<0){
			     	var s = hiddenitem.value + "," + curvalue;
			     	var shu = s.split(",");
			        hiddenitem.value = shu.sort();//js排序
			    }
			}
		}
	}
}
function changeChecked0(cur0, cur){
	changeChecked(cur0, cur, '1');
}
function changeValue(cur0, cur, value){
	var hiddenitem= document.getElementById("hiddenitem_" + cur0);
	if(hiddenitem.value == ""){
		hiddenitem.value = "【" + cur + "=】" + value;
	}else{
		var curvalue = "【" + cur + "=】" + value;
		if(hiddenitem.value.indexOf("【" + cur + "=】")<0){
			hiddenitem.value = hiddenitem.value + "【;】" + curvalue;
		}else{
			var hiddenitemvalue = hiddenitem.value;
			var hvalue = hiddenitemvalue.split("【;】");
			var hvalue1 = "";
			for(var i=0, size=hvalue.length; i<size; i++){
				if(hvalue[i].indexOf("【" + cur + "=】")<0){
					hvalue1 = hvalue1 + "【;】" + hvalue[i];
				}else{
					hvalue1 = hvalue1 + "【;】" + curvalue;
				}
			}
			hvalue1 = hvalue1.substring(3);
			hiddenitem.value = hvalue1;
		}
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
String prebutton = (String)request.getAttribute("prebutton");
String nextbutton = (String)request.getAttribute("nextbutton");
String display = "none";
if(answer != null) display = "block";
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
    	String[] rightAnswer = null;
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
            	<span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span><a id="option_<%=x %>_style"><%if(childAnswer != null){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></a>
        	</div>
    	</div>
   	 	<div class="container_answer">
    		<div class="container_answer_size">
    			<%
	    		for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
	    		%>
	        	<div class="container_answer_size_radio" onclick="changeRadio(<%=size %>, <%=x %>, <%=i %>)">
	        		<input type="radio" name="itemA_<%=x %>" id="item_<%=x %>_<%=i %>" value="<%=tqi.getOptionNo(i) %>" <%if(childAnswer != null && tqi.getOptionNo(i).equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label id="option_<%=x %>_<%=i %>" <%if(childAnswer != null && tqi.getOptionNo(i).equals(childAnswer.getAnswer())){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %>style="color:green"<%}else{ %>style="color:red"<%}} %>><%=tqi.getOptionNo(i) %>、<%=tqi.getOptionValue(i) %></label><br/>
	            </div>
	            <%} %>
        	</div>
    	</div>
    	<%}else if("B".equals(tqt.getType())){//多选 %>
    	<div class="container_question">
    		<div class="container_question_size">
            	<span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span><a id="option_<%=x %>_style"><%if(childAnswer != null){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></a>
        	</div>
    	</div>
   	 	<div class="container_answer">
    		<div class="container_answer_size">
    			<%
	    		for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
	    		%>
	        	<div class="container_answer_size_radio" onclick="changeChecked(<%=x %>, <%=i %>, '1')">
	        		<input type="checkbox" onclick="changeChecked0(<%=x %>, <%=i %>)" name="itemB_<%=x %>" id="item_<%=x %>_<%=i %>" value="<%=tqi.getOptionNo(i) %>" <%if(childAnswer != null && childAnswer.getAnswer().indexOf(tqi.getOptionNo(i)) != -1){ %>checked="checked"<%} %>/>
	        		<label id="option_<%=x %>_<%=i %>" <%if(childAnswer != null && childAnswer.getAnswer().indexOf(tqi.getOptionNo(i)) != -1){ %><%if(tqi.getRightans().indexOf(tqi.getOptionNo(i)) != -1){ %>style="color:green"<%}else{%>style="color:red"<%}} %>><%=tqi.getOptionNo(i) %>、<%=tqi.getOptionValue(i) %></label><br/>
	            </div>
	            <%} %>
        	</div>
    	</div>
    	<%}else if("C".equals(tqt.getType())){//判断 %>
    	<div class="container_question">
	    	<div class="container_question_size">
	           <span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span><a id="option_<%=x %>_style"><%if(childAnswer != null){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></a>
	        </div>
	    </div>
	    <div class="container_answer">
	    	<div class="container_answer_size">
	        	<div class="container_answer_size_radio" onclick="changeRadio(2, <%=x %>, 1)">
	        		<input type="radio" name="itemC_<%=x %>" id="item_<%=x %>_1" value="1" <%if(childAnswer != null && "1".equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label id="option_<%=x %>_1" <%if(childAnswer != null && "1".equals(childAnswer.getAnswer())){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %>style="color:green"<%}else{ %>style="color:red"<%}} %>>A、对</label><br/>
	            </div>
	            <div class="container_answer_size_radio" onclick="changeRadio(2, <%=x %>, 2)">
	        		<input type="radio" name="itemC_<%=x %>" id="item_<%=x %>_2" value="0" <%if(childAnswer != null && "0".equals(childAnswer.getAnswer())){ %>checked="checked"<%} %>/>
	        		<label id="option_<%=x %>_2" <%if(childAnswer != null && "0".equals(childAnswer.getAnswer())){ %><%if(tqi.getRightans().equals(childAnswer.getAnswer())){ %>style="color:green"<%}else{ %>style="color:red"<%}} %>>B、错</label><br/>
	            </div>
	        </div>
	    </div>
    	<%}else if("E".equals(tqt.getType())){//填空 %>
    	<div class="container_question">
	    	<div class="container_question_size">
	           <span class="container_question_size_title"><%=x+1 %>).<%=tqi.getTitlecontent() %></span><a id="option_<%=x %>_style"><%if(childAnswer != null){ %><%if("1".equals(childAnswer.getIsright())){ %><span style="float:right; text-align:right; color:#009900; font-size:20px;">√</span><%}else{%><span style="float:right; text-align:right; color:#FF0000; font-size:20px;">×</span><%}} %></a>
	        </div>
	    </div>
	    <div class="container_answer">
	    	<div class="container_answer_size">
	    		<%
	    		if(childAnswer != null){
	    			boolean isright = false;
		    		rightAnswer = tqi.getRightans().split("【】");
		    		paperAnswer = childAnswer.getAnswer().split("【】");
		            for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
		            	isright = tqi.getIsRight(rightAnswer[i-1], paperAnswer[i-1]);
	            %>
	            <div class="container_answer_size_radio">
	        		作答<%=i %>、<input type="text" name="itemE_<%=x %>" id="item_<%=x %>_<%=i %>" class="container_answer_size_radio_input" value="<%=paperAnswer[i-1] %>" <%if(isright){ %>style="color:green"<%}else{%>style="color:red"<%} %>/>
	            </div>
	            <%
	            	}
		        }else{
		        	for(int i=1, size=tqi.getOptionnum().intValue(); i<=size; i++){
		        %>
	            <div class="container_answer_size_radio">
	        		作答<%=i %>、<input type="text" name="itemE_<%=x %>" id="item_<%=x %>_<%=i %>" class="container_answer_size_radio_input" value="" onblur="changeValue(<%=x %>,<%=i %>,this.value)"/>
	            </div>
	            <%}} %>
	        </div>
	    </div>
    	<%} %>
    	<!-- 每道题放个隐藏域值供判断 -->
    	<input type="hidden" name="hiddenitem_<%=x %>" id="hiddenitem_<%=x %>" value=""/>
    	<%} %>
    <div class="container_question" style="display:none;" id="tips_div">
    	<div class="container_question_size">
    		<span class="container_question_size_title" style="font-size:14px;color:red;">请作答完整！</span>
    	</div>
    </div>
    <%if(answer == null){ %>
    <a href="javascript:returnAnswer(<%=childList.size() %>)" id="true_button">
		<div class="botton3" style="width:99%;margin-top:10px;">
			<p>确定</p>
		</div>
	</a>
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
    <div class="container_answer" style="padding-bottom:55px;"></div>
    </div>
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