<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>教师认证提示</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body style="background-color: #fff;">	
<div class="search search_write">
<c:if test="${empty(questionid)}"><a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}"></c:if>
<c:if test="${!empty(questionid)}"><a href="/weixinHelp.app?method=info&questionid=${questionid }&userid=${userid }"></c:if>
		<div  class="search_left">
	    	<img src="/weixin/images/a01.png" class="search_class" />
	    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">教师认证提示</p>
    </div>
</div>
<div class="search_padding"></div>

<p class="password_p password_p01" style="padding-right:10px;">
尊敬的[${sysUserInfo.username }]老师，您好！
</br></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了保障学生的利益，同时也为了保障您个人的利益，在线回答学生提问挣得学币（学币可提现），需要在龙门作业宝平台经过教师资格在线认证通过后，方可回答问题。
</br></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请通过电脑访问网址http://www.lmzyb.com/cert.jsp进行教师资格在线认证。
</p> 
</body>
</html>