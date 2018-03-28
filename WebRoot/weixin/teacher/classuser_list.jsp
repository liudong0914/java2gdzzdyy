<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>班级学员</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=getQuestions&bookcontentid=${bookcontentid }&classid=${classid}&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">班级学员</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div id="container_bj">
	<%
	List userList = (List)request.getAttribute("userList");
	List useridList = (List)request.getAttribute("useridList");
	SysUserInfo sui = null;
	String isupload = null;
	for(int i=0, size=userList.size(); i<size; i++){
		sui = (SysUserInfo)userList.get(i);
		isupload = "0";
		if(useridList.contains(sui.getUserid())){
			isupload = "1";
		}
	%>
    <div class="container_44" style="height:40px;">
    	<%if("1".equals(isupload)){ %><a href="/weixinTeacher.app?method=classUserUpload&bookcontentid=${bookcontentid}&classid=${classid}&userid=${userid}&studentid=<%=sui.getUserid() %>"><%} %>
        	<div class="conrainer_4_bj">
            	<p class="conrainer_4_bj_1"><%=i+1 %>、<%=sui.getUsername() %></p>
            </div>
            <%if("1".equals(isupload)){ %>
            <font class="conrainer_4_bj_a">已提交</font>
            <%}else{ %>
            <font class="conrainer_4_bj_a" style="color:red;">未提交</font>
            <%} %>
        <%if("1".equals(isupload)){ %></a><%} %>
    </div>
    <%} %>
</div>
</body>
</html>