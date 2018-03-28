<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysUserSearchKeywords"%>

<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>搜索</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<link href="/weixin/css/common.css" rel="stylesheet" type="text/css">
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script type="text/javascript">
function search(){
	var keywords = document.getElementById("keywords").value;
	if(keywords == ""){
		alert("请输入搜索关键字!");
		return;
	}
	//document.pageForm.action = "/weixinVod.app?method=toSearch&userid=${userid }";
	document.pageForm.action = "/weixinCourse.app?method=toSearch&userid=${userid }";
	document.pageForm.submit();
}
function search0(keywords){
	document.getElementById("keywords").value = keywords;
	search();
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>
<form name="pageForm" method="post">
<!-----------------内容------------------>
<div class="search_h1">
	 <div class="search_h1_box">
	 	<p>课程微课</p>
        <input type="search" placeholder="搜索关键字" id="keywords" name="keywords" value="${keywords }" style="color:#000;"  onKeyPress="if(event.keyCode==13){javascript:search();return false;}"/>  
     </div>
     <a href="/weixinAccountIndex.app?method=index&userid=${userid }" class="search_h1_a">取消</a>
</div>
<%
List list = (List)request.getAttribute("list");
SysUserSearchKeywords susk = null;
%>
<div class="search_h2">
	<p class="search_h2_p">历史搜索</p>
	<% 
	if(list != null && list.size() > 0){
   			for(int i=0, size=list.size(); i<size; i++){
   				susk = (SysUserSearchKeywords)list.get(i);
   	   %>
   	   <a href="javascript:search0('<%=susk.getKeywords()%>')"><%=susk.getKeywords() %></a>
   	   <%} %>
       <%}else{ %>
       暂无数据！
       <%} %>
</div>

<!--------------底部------------>
</form>
</body>
</html>