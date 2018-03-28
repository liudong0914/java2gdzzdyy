<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.tk.bo.TkBookContent"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>英语听力</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=index&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">英语听力</p>
    </div>
</div>
<div class="search_padding"></div>
<div id="container_list">
	<%
	List parentList = (List)request.getAttribute("parentList");
	Map childMap = (Map)request.getAttribute("childMap");
	if(parentList != null && parentList.size() > 0){
	List childList = null;
	TkBookContent tbc = null;
	for(int i=0, size=parentList.size(); i<size; i++){
		tbc = (TkBookContent)parentList.get(i);
	%>
	<div class="container_7">
    	<div class="container_7_size">
			<span><%=tbc.getTitle() %></span>
        </div>
    </div>
    <%
    childList = (List)childMap.get(tbc.getContentno());
    if(childList != null && childList.size() > 0){
    	for(int m=0, n=childList.size(); m<n; m++){
    		tbc = (TkBookContent)childList.get(m);
    %>
    <div class="container_8" style="padding:10px 0px;">
    	<div class="container_8_size">
            <a href="/weixinStudent.app?method=scanAudioPlay&flag=1&userid=${userid }&bookcontentid=<%=tbc.getBookcontentid() %>" class="container_7_size_moble">
           		<div class="conrainer_7_moble_span">
            		<span><%=tbc.getTitle() %></span>
                </div>
                <span><img src="/weixin/images/school_11.png" class="conrainer_7_size_moble_img school_img" /></span>
            </a>
        </div>
    </div>
    <%}} %>
    <%}}else{ %>
    <div class="container_welcome">
		<div class="conrainer_welcome_font">
	    	<p>当前作业本没有内容！</p>
	    </div>
	</div>
    <%} %>
</div>
<%@ include file="/weixin/account/footer.jsp"%>
</body>
</html>