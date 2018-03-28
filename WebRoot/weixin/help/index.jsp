<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduXueduanInfo"%>
<%@page import="com.wkmk.edu.bo.EduSubjectInfo"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestion"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.util.date.TimeUtil"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.util.date.DateTime"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>在线答疑</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function postData(){
	document.pageForm.action = "/weixinHelp.app?method=index&userid=${userid }";
	document.pageForm.submit();
}
function showDiv(divid, num){
	for(var i=1; i<=num; i++){
		document.getElementById("hiddendiv_" + i).style.display = "none";
	}
	document.getElementById("hiddendiv_" + divid).style.display = "block";
}
function hidDiv(divid){
	document.getElementById("hiddendiv_" + divid).style.display = "none";
}
function changeType(typeid, value){
	document.getElementById(typeid).value = value;
	postData();
}

function goMd(){
	var scrollHeight=document.getElementById("md").offsetTop-52;
	scroll(0,Math.round(scrollHeight));
}
</script>
</head>

<body onload="goMd()">	
<%@ include file="/weixin/account/dh.jsp"%>
<form name="pageForm" method="post">
<div class="search">
	<a href="/weixinAccountIndex.app?method=index&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_input01">
        <div  class="search_input_01_main">
            <input type="search" placeholder="搜索关键字" name="keywords" value="${keywords }" style="color:#000;"/>  
            <a href="javascript:postData()"><img src="/weixin/images/icon04.png" /></a> 
        </div>
     </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01" />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<!-----下拉菜单-------->
   <div class="menu01" >
	  <ul>
         <li class="menu_li01"><a class="menu_a" href="javascript:showDiv(1,4)">学科</a>
         </li>
         <li class="menu_li01"><a class="menu_a" href="javascript:showDiv(2,4)">年级</a>
         </li>
         <li class="menu_li01"><a class="menu_a" href="javascript:showDiv(3,4)">状态</a>
         </li>
         <li class="menu_li01"><a class="menu_a" href="javascript:showDiv(4,4)">支付</a>
         </li>
     </ul>
     <div class="menu_div" id="hiddendiv_1" style="display:none;">
     	<%
     	String subjectid = (String)request.getAttribute("subjectid");
     	List subjectList = (List)request.getAttribute("subjectList");
     	%>
     	<a href="javascript:changeType('subjectid', '')" class="menu_div_a <%if(subjectid == null || "".equals(subjectid)){ %>hover<%} %>">全部</a>
        <%
        EduSubjectInfo esi = null;
        for(int i=0, size=subjectList.size(); i<size; i++){
        	esi = (EduSubjectInfo)subjectList.get(i);
        %>
        <a href="javascript:changeType('subjectid', '<%=esi.getSubjectid() %>')" class="menu_div_a <%if(esi.getSubjectid().toString().equals(subjectid)){ %>hover<%} %>"><%=esi.getSubjectname() %></a>
        <%} %>
        <div class="menu_div_button">
            <a href="javascript:hidDiv(1)" class="menu_div_button_a" style="width:100%;">关闭</a>
        </div>
     </div>
     <div class="menu_div" id="hiddendiv_2" style="display:none;">
     	<%
     	String cxueduanid = (String)request.getAttribute("cxueduanid");
     	List cxueduanList = (List)request.getAttribute("cxueduanList");
     	%>
     	<a href="javascript:changeType('cxueduanid', '')" class="menu_div_a <%if(cxueduanid == null || "".equals(cxueduanid)){ %>hover<%} %>">全部</a>
        <logic:equal value="1" name="usertype">
        <%
        EduXueduanInfo exi = null;
        for(int i=0, size=cxueduanList.size(); i<size; i++){
        	exi = (EduXueduanInfo)cxueduanList.get(i);
        %>
        <a href="javascript:changeType('cxueduanid', '<%=exi.getXueduanid() %>')" class="menu_div_a <%if(exi.getXueduanid().toString().equals(cxueduanid)){ %>hover<%} %>"><%=exi.getName() %></a>
        <%} %>
        </logic:equal>
        <logic:equal value="2" name="usertype">
        <%
        String xueduan = (String)request.getAttribute("xueduan");
        if("2".equals(xueduan)){
        %>
        <a href="javascript:changeType('cxueduanid', '15')" class="menu_div_a <%if("15".equals(cxueduanid)){ %>hover<%} %>">七年级</a>
        <a href="javascript:changeType('cxueduanid', '16')" class="menu_div_a <%if("16".equals(cxueduanid)){ %>hover<%} %>">八年级</a>
        <a href="javascript:changeType('cxueduanid', '17')" class="menu_div_a <%if("17".equals(cxueduanid)){ %>hover<%} %>">九年级</a>
        <%}else if("3".equals(xueduan)){ %>
        <a href="javascript:changeType('cxueduanid', '19')" class="menu_div_a <%if("19".equals(cxueduanid)){ %>hover<%} %>">高一</a>
        <a href="javascript:changeType('cxueduanid', '20')" class="menu_div_a <%if("20".equals(cxueduanid)){ %>hover<%} %>">高二</a>
        <a href="javascript:changeType('cxueduanid', '21')" class="menu_div_a <%if("21".equals(cxueduanid)){ %>hover<%} %>">高三</a>
        <%}else if("1".equals(xueduan)){ %>
        <a href="javascript:changeType('cxueduanid', '9')" class="menu_div_a <%if("9".equals(cxueduanid)){ %>hover<%} %>">一年级</a>
        <a href="javascript:changeType('cxueduanid', '10')" class="menu_div_a <%if("10".equals(cxueduanid)){ %>hover<%} %>">二年级</a>
        <a href="javascript:changeType('cxueduanid', '11')" class="menu_div_a <%if("11".equals(cxueduanid)){ %>hover<%} %>">三年级</a>
        <a href="javascript:changeType('cxueduanid', '12')" class="menu_div_a <%if("12".equals(cxueduanid)){ %>hover<%} %>">四年级</a>
        <a href="javascript:changeType('cxueduanid', '13')" class="menu_div_a <%if("13".equals(cxueduanid)){ %>hover<%} %>">五年级</a>
        <a href="javascript:changeType('cxueduanid', '14')" class="menu_div_a <%if("14".equals(cxueduanid)){ %>hover<%} %>">六年级</a>
        <%} %>
        </logic:equal>
        <div class="menu_div_button">
            <a href="javascript:hidDiv(2)" class="menu_div_button_a" style="width:100%;">关闭</a>
        </div>
     </div>
     <div class="menu_div" id="hiddendiv_3" style="display:none;">
     	<%
     	String replystatus = (String)request.getAttribute("replystatus");
     	%>
     	<a href="javascript:changeType('replystatus', '')" class="menu_div_a <%if(replystatus == null || "".equals(replystatus)){ %>hover<%} %>">全部</a>
        <a href="javascript:changeType('replystatus', '1')" class="menu_div_a <%if("1".equals(replystatus)){ %>hover<%} %>">已答</a>
        <a href="javascript:changeType('replystatus', '0')" class="menu_div_a <%if("0".equals(replystatus)){ %>hover<%} %>">未答</a>
        <div class="menu_div_button">
            <a href="javascript:hidDiv(3)" class="menu_div_button_a" style="width:100%;">关闭</a>
        </div>
     </div>
     <div class="menu_div" id="hiddendiv_4" style="display:none;">
     	<%
     	String money = (String)request.getAttribute("money");
     	%>
     	<a href="javascript:changeType('money', '')" class="menu_div_a <%if(money == null || "".equals(money)){ %>hover<%} %>">全部</a>
        <a href="javascript:changeType('money', '0')" class="menu_div_a <%if("0".equals(money)){ %>hover<%} %>">免费</a>
        <a href="javascript:changeType('money', '1')" class="menu_div_a <%if("1".equals(money)){ %>hover<%} %>">付费</a>
        <div class="menu_div_button">
            <a href="javascript:hidDiv(4)" class="menu_div_button_a" style="width:100%;">关闭</a>
        </div>
     </div>
   </div>
   
<!--menu ed-->
<!-----class_main-------->
<div class="class_main_student" >
	<div id="class_main_student">
	<%
	List list = (List)request.getAttribute("list");
	String questionid = request.getAttribute("questionid").toString();
	if(list != null && list.size() > 0){
		ZxHelpQuestion question = null;
		SysUserInfo sui = null;
		String photo = null;
		for(int i=0, size=list.size(); i<size; i++){
			question = (ZxHelpQuestion)list.get(i);
			sui = question.getSysUserInfo();
			if(sui.getPhoto().startsWith("http://")){
				photo = sui.getPhoto();
			}else{
				photo = "/upload/" + sui.getPhoto();
			}
	%>
	<a href="javascript:questionInfo(<%=question.getQuestionid() %>)" <%if(question.getQuestionid().toString().equals(questionid)){%>id="md"<%}%>><div class="answer_student">
    	<div class="answer_student_top">
        	<img src="<%=photo %>" />
            <p><%=sui.getUsername() %> • <%=question.getSubjectname() %> - <%=question.getGradename() %></p>
            <p class="answer_student_top_p"><%=TimeUtil.getTimeName(question.getCreatedate()) %></p>
        </div>
        <div class="answer_student_font">
        	<%if("3".equals(question.getReplystatus())){ %>
        	<p class="answer_student_font_p answer_student_font_p01">已答</p>
        	<%}else{ %>
        	<p class="answer_student_font_p">未答</p>
        	<%} %>
            <p class="answer_student_font_pright"><%=question.getTitle() %></p>
        </div>
        <p class="answer_student_p"><%=question.getDescript() %></p>
		<p class="answer_student_p_teacher">
		<logic:equal value="1" name="usertype">
        <%if("0".equals(question.getReplystatus())){ %>
		回答截止时间：<%if(DateTime.getCompareToDate(question.getEnddate(), DateTime.getDate()) > 0){ %><%=question.getEnddate() %><%}else{ %>已结束<%} %>
		<%} %>
		</logic:equal>
		<%if(question.getMoney() > 0){ %>
		<%if("3".equals(question.getReplystatus())){ %>
		<font style="text-align:right;float:right;">价格：<%=question.getMoney()*Constants.BUY_QUESTION_DISCOUNT/10 %>学币</font>
		<%}else{ %>
		<font style="text-align:right;float:right;">价格：<%=question.getMoney() %>学币</font>
		<%}}else{ %>
		<font style="text-align:right;float:right;color:#1fcc8a;">免费</font>
		<%} %>
		</p>
    </div></a>
    <%}}else{ %>
    <div class="answer_student">暂无答疑！</div>
    <%} %>
    </div>
    <div class="loading" id="loading">
		<img src="/weixin/images/loading.gif" class="loadingimg"/>加载中
	</div>
</div>
<logic:equal value="2" name="usertype">
<a href="/weixinHelp.app?method=beforeAddQuestion&userid=${userid}"  class="Menubox01_button">发布问题</a>
</logic:equal>

<input type="hidden" name="subjectid" id="subjectid" value="${subjectid }"/>
<input type="hidden" name="cxueduanid" id="cxueduanid" value="${cxueduanid }"/>
<input type="hidden" name="replystatus" id="replystatus" value="${replystatus }"/>
<input type="hidden" name="money" id="money" value="${money }"/>
</form>
<script type="text/javascript">
var num = Number(${pagenum});
$(document).ready(function(){
	$(document).scroll(function(){
    	if($("#loading").is(":hidden")){
      		$("#loading").show();
      	}
    	var scrolltop = $(document).scrollTop();
        var dheight = $(document).height();
        var wheight = $(window).height();
        if(scrolltop >= dheight-wheight){
    		  $.ajax({
		        type:"post",
		        url:"/weixinHelp.app?method=getAjaxIndexQuestion",
		        data:"pagenum=" + num + "&userid=${userid}&subjectid=${subjectid}&cxueduanid=${cxueduanid }&replystatus=${replystatus }&money=${money }&keywords=${keywords }&questionid=${questionid}",
		        success:function(msg){
		        	if(msg != ""){
		        		$("#class_main_student").append(msg);
		        	}else{
		        		$("#loading").html("数据已全部加载完!");
		        		$(document).unbind('scroll');
		        	}
     			}
 			});
    	  	num++;
      	}
	});
});

function questionInfo(qid){
	location.href="/weixinHelp.app?method=info&menutype=${menutype }&userid=${userid }&tag=2&questionid="+qid+"&pagenum="+num;
}
</script>
</body>
</html>