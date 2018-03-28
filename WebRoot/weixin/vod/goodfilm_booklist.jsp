<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduSubjectInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>精品微课作业本</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function postData(){
	document.pageForm.action = "/weixinVod.app?method=goodFilmBookList&userid=${userid }";
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
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/dh.jsp"%>
<form name="pageForm" method="post">
<div class="search">
	<a href="/weixinAccountIndex.app?method=index&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_input01">
        <div  class="search_input_01_main">
            <input type="search" placeholder="搜索关键字" name="keywords" value="${keywords }" style="color:#000;"/>  
            <a href="javascript:postData()"><img src="/weixin/images/icon04.png" /></a> 
        </div>
     </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<!-----下拉菜单-------->
   <div class="menu" >
	  <ul>
	     <li class="menu_li"><a class="menu_a" href="javascript:showDiv(1,3)">学科</a>
         </li>
         <li class="menu_li"><a class="menu_a" href="javascript:showDiv(2,3)">年级</a>
         </li>
         <li class="menu_li"><a class="menu_a" href="javascript:showDiv(3,3)">版本</a>
         </li>
     </ul>
     <div class="menu_div" id="hiddendiv_1" style="display:none;">
     	<%
     	String subjectid = (String)request.getAttribute("subjectid");
     	String cxueduanid = (String)request.getAttribute("cxueduanid");
     	String version = (String)request.getAttribute("version");
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
     	<a href="javascript:changeType('cxueduanid', '')" class="menu_div_a <%if(cxueduanid == null || "".equals(cxueduanid)){ %>hover<%} %>">全部</a>
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
        <div class="menu_div_button">
            <a href="javascript:hidDiv(2)" class="menu_div_button_a" style="width:100%;">关闭</a>
        </div>
    </div>
    <div class="menu_div" id="hiddendiv_3" style="display:none;">
     	<a href="javascript:changeType('version', '')" class="menu_div_a <%if(version == null || "".equals(version)){ %>hover<%} %>">全部</a>
        <%
        String[] versionid = Constants.getCodeTypeid("version");
        String[] versionname = Constants.getCodeTypename("version");
        for(int i=0, size=versionid.length; i<size; i++){
        %>
        <a href="javascript:changeType('version', '<%=versionid[i] %>')" class="menu_div_a <%if(versionid[i].equals(version)){ %>hover<%} %>"><%=versionname[i] %></a>
        <%} %>
        <div class="menu_div_button">
            <a href="javascript:hidDiv(3)" class="menu_div_button_a" style="width:100%;">关闭</a>
        </div>
    </div>
   </div>
   
<!--menu ed-->
<!-----class_main-------->
<div class="class_main">
	<%
	List bookList = (List)request.getAttribute("bookList");
	if(bookList != null && bookList.size() > 0){
		TkBookInfo tbi = null;
		for(int i=0, size=bookList.size(); i<size; i++){
			tbi = (TkBookInfo)bookList.get(i);
	%>
	<%if(i%2 == 0){ %>
	<div class="class_main01">
	<%} %>
		<a href="/weixinVod.app?method=bookContentGoodFilmList&userid=${userid }&bookid=<%=tbi.getBookid() %>">
	    <div class="class_main_moudle <%if(i%2 != 0){ %>class_main_moudle_right<%} %>">
	    	<img src="/upload/<%=tbi.getSketch() %>" />
	        <p><%=tbi.getTitle() %>(<%=Constants.getCodeTypevalue("version", tbi.getVersion()) %>)</p>
	    </div>
	    </a>
	<%if((i != 0 && (i+1)%2 == 0) || i == size-1){ %>
    </div>
    <%} %>
    <%}}else{ %>
    暂无数据！
    <%} %>
</div>
<input type="hidden" name="subjectid" id="subjectid" value="${subjectid }"/>
<input type="hidden" name="cxueduanid" id="cxueduanid" value="${cxueduanid }"/>
<input type="hidden" name="version" id="version" value="${version }"/>
</form>
</body>
</html>