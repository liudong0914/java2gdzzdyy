<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkTextBookInfo"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>教材订购</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="/weixin/js/public.js"></script>
<script type="text/javascript" src="/weixin/js/jquery.SuperSlide.js"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
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
            <input type="search" placeholder="搜索教材名称" name="keywords" value="${keywords }" style="color:#000;"/>  
            <a href="javascript:postData()"><img src="/weixin/images/icon04.png" /></a> 
        </div>
     </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<!--开始-->
<div class="class_main">
	<%
	List textbooklist = (List)request.getAttribute("textbooklist");
	if(textbooklist != null && textbooklist.size() > 0){
		TkTextBookInfo textBookInfo = null;
		for(int i=0, size=textbooklist.size(); i<size; i++){
			textBookInfo = (TkTextBookInfo)textbooklist.get(i);
	%>
	<%if(i%2 == 0){ %>
	<div class="class_main01">
	<%} %>
		<a href="/weixinTextBook.app?method=getTextBookDetail&userid=${userid }&textbookid=<%=textBookInfo.getTextbookid() %>">
	    <div class="class_main_moudle <%if(i%2 != 0){ %>class_main_moudle_right<%} %>">
	    	<img src="/upload/<%=textBookInfo.getSketch() %>" />
	        <p><%=textBookInfo.getTextbookname() %></p>
	    </div>
	    </a>
	<%if((i != 0 && (i+1)%2 == 0) || i == size-1){ %>
    </div>
    <%} %>
    <%}}else{ %>
    暂无数据！
    <%} %>
</div>
<!--结束-->
</form>

<script type="text/javascript">
	function postData(){
		document.pageForm.action = "/weixinTextBook.app?method=index&userid=${userid}";
		document.pageForm.submit();
	}
</script>
</body>
</html>