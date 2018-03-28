<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>班级信息</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
img{border:0 none; display:inline;}
</style>
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function addSaveClass(){
	var hascommit = document.getElementById("hascommit").value;
	if(hascommit == "0"){
		document.getElementById("hascommit").value = "1";
		document.pageForm.action="/weixinStudent.app?method=addSaveClass";
    	document.pageForm.submit();
	}
}
</script>
</head>

<body>
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinStudent.app?method=addClass&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">加入班级-第二步</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form name="pageForm" method="post">
<div id="container" style="padding-bottom:55px;">
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
        	<p>班级名称：<bean:write name="classInfo" property="classname"/></p>
        </div>
    </div>
    <logic:equal value="1" name="classInfo" property="pwd">
    <div class="container_2">
    	<div class="container_bjxq_font">
        	<p>进班密码：<input type="text" value="${students }" name="password" placeholder="请输入班级密码" class="container_1_radio2" /></p>
        </div>
    </div>
    </logic:equal>
    <div class="container_2">
    	<div class="container_bjxq_font">
    		<%
    		TkBookInfo bookInfo = (TkBookInfo)request.getAttribute("bookInfo");
    		%>
        	<p>作业本：<%=bookInfo.getTitle() %><!-- （<%=Constants.getCodeTypevalue("version", bookInfo.getVersion()) %>） --></p>
        </div>
    </div>
    <div class="content_botton">
    	<a href="javascript:addSaveClass()">
    		<div class="botton4">
    			<p>确认加入</p>
    		</div>
    	</a>
    </div>
    <logic:present name="errmsg"><div class="tips"><bean:write name="errmsg" /></div></logic:present>
</div>
<input type="hidden" name="classid" value="${classInfo.classid }"/>
<input type="hidden" name="bookid" value="${bookInfo.bookid }"/>
<input type="hidden" name="userid" value="${userid }"/>
<input type="hidden" name="hascommit" id="hascommit" value="0"/><!-- 控制重复提交 -->
</form>
</body>
</html>