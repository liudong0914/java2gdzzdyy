<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>未提交作业学生</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=getQuestions&bookcontentid=${bookcontentid}&classid=${classid}&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">未提交作业学生</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div class="container_bj">
	<%
	List userList = (List)request.getAttribute("userList");
	if(userList != null && userList.size() > 0){
		SysUserInfo sui = null;
		for(int i=0, size=userList.size(); i<size; i++){
			sui = (SysUserInfo)userList.get(i);
	%>
    <div class="container_44">
    	<div class="conrainer_4_bj">
        	<p class="conrainer_4_bj_1"><%=i+1 %>、<%=sui.getUsername() %></p>
        </div>
        <font class="conrainer_4_bj_a"><%=Constants.getCodeTypevalue("sex", sui.getSex()) %></font>
    </div>
    <%}}else{ %>
    <div class="container_welcome">
		<div class="conrainer_welcome_font">
	    	<p>所有学生都已提交作业！</p>
	    </div>
	</div>
    <%} %>
</div>
</body>
</html>