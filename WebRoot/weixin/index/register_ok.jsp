<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
//苹果手机无法自动跳转，故显示简介手动触发按钮
window.location.href = "/weixinAccountIndex.app?method=index&userid=${userid}";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>注册成功</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function createClass(){
    document.pageForm.action="/weixinTeacher.app?method=createClass";
    document.pageForm.submit();
}
function addClass(){
    document.pageForm.action="/weixinStudent.app?method=addClass";
    document.pageForm.submit();
}
function addBook(){
    document.pageForm.action="/weixinStudent.app?method=addBook";
    document.pageForm.submit();
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<form name="pageForm" method="post">
<div id="content">
	<div class="content_user">
    	<img src="${headimgurl }" width="120" height="120"/>
        <p>${userInfo.username }</p>     
    </div>
    <div class="content_font">
    	<p><logic:equal value="1" name="userInfo" property="usertype">教师</logic:equal><logic:equal value="2" name="userInfo" property="usertype">学生</logic:equal></p>
        <p>${unitname }</p>
    </div>
    
    <div class="content_botton">
    	<logic:equal value="1" name="userInfo" property="usertype">
		<a href="javascript:createClass();">
			<div class="botton3">
				<p>注册成功</p>
			</div>
		</a>
    	<a href="javascript:createClass();">
    		<div class="botton4">
    			<p>创建班级</p>
    		</div>
    	</a>
    	</logic:equal>
    	<logic:equal value="2" name="userInfo" property="usertype">
    	<a href="javascript:addClass();">
			<div class="botton3">
				<p>注册成功</p>
			</div>
		</a>
    	<a href="javascript:addClass();">
    		<div class="botton4">
    			<p>加入班级</p>
    		</div>
    	</a>
        <a href="javascript:addBook();" class="botton5">没有班级？绑定作业本</a>
    	</logic:equal>
    </div>
</div>
<input type="hidden" name="userid" value="${userid }"/>
<input type="hidden" name="scanResult" value="${scanResult }"/>
</form>
</body>
</html>