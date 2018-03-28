<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>修改班级名称</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
img{border:0 none; display:inline;}
</style>
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function updateSaveClass(){
    document.pageForm.action="/weixinTeacher.app?method=classSave";
    document.pageForm.submit();
}
function selectClassName(){
	document.getElementById("classname").select();
}
</script>
</head>

<body onload="selectClassName()">
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=classInfo&userid=${userid }&classid=${classInfo.classid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">修改班级名称</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form name="pageForm" method="post">
<div id="container" style="padding-bottom:45px;">
	<div class="container_bjxq">
    	<p><img src="/upload/<bean:write name="classInfo" property="twocodepath"/>" width="200" height="220"/></p>
    </div>
	<div class="container_1">
    	<div class="container_bjxq_font">
        	<p>班级编号：<bean:write name="classInfo" property="classno"/></p>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_bjxq_font">
        	<p>班级名称：<input type="text" value="${classInfo.classname }" name="classname" id="classname" placeholder="请输入班级名称" class="container_1_radio2"/></p>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_bjxq_font">
        	<p>班级人数：<bean:write name="classInfo" property="students"/></p>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_bjxq_font">
    		<%
    		TkBookInfo bookInfo = (TkBookInfo)request.getAttribute("bookInfo");
    		%>
        	<p>作业本：<%=bookInfo.getTitle() %><!-- （<%=Constants.getCodeTypevalue("version", bookInfo.getVersion()) %>） --></p>
        </div>
    </div>
    <div class="content_botton">
    	<a href="javascript:updateSaveClass()">
    		<div class="botton4">
    			<p>提交保存</p>
    		</div>
    	</a>
    </div>
    <logic:present name="errmsg"><div class="tips"><bean:write name="errmsg" /></div></logic:present>
</div>
<input type="hidden" name="classid" value="${classInfo.classid }"/>
<input type="hidden" name="userid" value="${userid }"/>
</form>
</body>
</html>