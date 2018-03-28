<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkPaperType"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduSubjectInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>试卷下载</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function postData(){
	document.pageForm.action = "/weixinPaper.app?method=index&userid=${userid}";
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
</head>

<body>	
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
         <li class="menu_li"><a class="menu_a" href="javascript:showDiv(2,3)">分类</a>
         </li>
         <li class="menu_li"><a class="menu_a" href="javascript:showDiv(3,3)">年份</a>
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
     	String typeid = (String)request.getAttribute("typeid");
     	List typelist = (List)request.getAttribute("typelist");
     	%>
     	<a href="javascript:changeType('typeid', '')" class="menu_div_a <%if(typeid == null || "".equals(typeid)){ %>hover<%} %>">全部</a>
        <%
        TkPaperType pt = null;
        for(int i=0, size=typelist.size(); i<size; i++){
        	pt = (TkPaperType)typelist.get(i);
        %>
        <a href="javascript:changeType('typeid', '<%=pt.getTypeid() %>')" class="menu_div_a <%if(pt.getTypeid().toString().equals(typeid)){ %>hover<%} %>"><%=pt.getTypename() %></a>
        <%} %>
        <div class="menu_div_button">
            <a href="javascript:hidDiv(2)" class="menu_div_button_a" style="width:100%;">关闭</a>
        </div>
     </div>
     <div class="menu_div" id="hiddendiv_3" style="display:none;">
     	<%
     	String theyear = (String)request.getAttribute("theyear");
     	List yearlist = (List)request.getAttribute("yearlist");
     	%>
     	<a href="javascript:changeType('theyear', '')" class="menu_div_a <%if(theyear == null || "".equals(theyear)){ %>hover<%} %>">全部</a>
        <%
        String year = "";
        for(int i=0, size=yearlist.size(); i<size; i++){
        	year = yearlist.get(i).toString();
        %>
        <a href="javascript:changeType('theyear', '<%=year %>')" class="menu_div_a <%if(year.equals(theyear)){ %>hover<%} %>"><%=year%></a>
        <%} %>
        <div class="menu_div_button">
            <a href="javascript:hidDiv(3)" class="menu_div_button_a" style="width:100%;">关闭</a>
        </div>
     </div>
   </div>
   
<!--menu ed-->
<!-----class_main-------->
<div class="testpaper" id="testpaper">
	<%
	List filelist = (List)request.getAttribute("filelist");
	if(filelist != null && filelist.size() > 0){
	%>
	<c:forEach items="${filelist}" var="pf">
		<a href="/weixinPaper.app?method=getPaperFileDetail&userid=${userid }&fileid=${pf[0].fileid}">
			<div class="testpaper_main">
				<p class="testpaper_main_p">${pf[0].filename }</p>
				<div class="testpaper_main_right">
					 <p>${pf[0].theyear }</p>
					 <p>${pf[1] }</p>
					 <p>${pf[0].area }</p>
				 </div>
			 </div>
	 	</a>
	 </c:forEach>
	 <%}else{ %>
	 暂无数据！
	 <%} %>
</div>
<div class="loading" id="loading">
		<img src="/weixin/images/loading.gif" class="loadingimg"/>加载中
	</div>
<input type="hidden" name="subjectid" id="subjectid" value="${subjectid }"/>
<input type="hidden" name="typeid" id="typeid" value="${typeid }"/>
<input type="hidden" name="theyear" id="theyear" value="${theyear }"/>
</form>

<div style="margin-top:45px;">
	<a href="/weixinVod.app?method=goodFilmBookList&userid=${userid }" class="Menubox01_button">查看试卷解题微课</a>
</div>

<script type="text/javascript">
var num = 1;
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
		        url:"/weixinPaper.app?method=getPaperFileByAjax",
		        data:"pagenum=" + num + "&userid=${userid}&subjectid=${subjectid}&typeid=${typeid }&theyear=${theyear }&keywords=${keywords }",
		        success:function(msg){
		        	if(msg != ""){
		        		$("#testpaper").append(msg);
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
</script>
</body>
</html>