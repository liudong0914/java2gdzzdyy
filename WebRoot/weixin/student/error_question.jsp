<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>我的错题本</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinStudent.app?method=bookContent&userid=${userid }&classid=${classid }&bookid=${bookid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">我的错题本</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<div id="container_list">
	<%
	List list = (List)request.getAttribute("list");
	if(list != null && list.size() > 0){
	Object[] obj = null;
	for(int i=0, size=list.size(); i<size; i++){
		obj = (Object[])list.get(i);
	%>
	<div <%if(i==0){ %>class="container_9"<%}else{ %>class="container_10"<%} %>>
    	<div class="container_9_size">
            <a href="/weixinStudent.app?method=errorQuestionInfo&userid=${userid }&classid=${classid }&bookid=${bookid }&bookcontentid=${bookcontentid }&questionid=<%=obj[0] %>&uerrorid=<%=obj[4] %>" class="container_9_size_moble">
           		<div class="conrainer_9_moble_span">
                	<span><%=i+1 %>、<%=obj[2] %><%if("2".equals(obj[3].toString())){ %><font style="font-size:10px;">[课前预习]</font><%} %></span>
                </div>
                <span><img src="/weixin/images/school_11.png" class="conrainer_9_size_moble_img" /></span>
            </a>
    	</div>
    </div>
    <%}}else{ %>
    <div class="container_welcome">
    	<div class="conrainer_welcome_font">
        	<p>当前作业暂无错题！</p>
        </div>
    </div>
    <%} %>
</div>
</body>
</html>