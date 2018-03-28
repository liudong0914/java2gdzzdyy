<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>查看投诉内容</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body style="background-color:#fff;">	
<div class="search search_write">
	<a href="/weixinHelp.app?method=info&userid=${userid }&questionid=${questionid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">查看投诉内容</p>
    </div>
</div>
<div class="search_padding"></div>

<div class="Published">
	<p>投诉时间：${complaint.createdate }</p>
</div>
<div class="Published">
	<p>
	投诉进度：<c:if test="${complaint.status == '0' }"><font color="blue">待受理</font></c:if><c:if test="${complaint.status == '1' }"><font color="red">接受投诉</font></c:if><c:if test="${complaint.status == '2' }"><font color="">投诉驳回</font></c:if>
	</p>
</div>
<div class="Published">
	<p>投诉内容</p>
    <div class="Published_div" style="margin-top:10px;">${complaint.descript }</div>
</div>
<c:if test="${complaint.status eq '1'||complaint.status eq '2' }">
<div class="Published" style="border-bottom:none;">
	<p>回复时间：${empty(complaint.replycreatedate)?"<font color='blue'>暂未回复</font>":complaint.replycreatedate}</p>
</div>
<div class="Published">
	<p>回复内容</p>
    <div class="Published_div" style="margin-top:10px;">${empty(complaint.replycontent)?"暂未回复":complaint.replycontent }</div>
</div>
</c:if>
</body>
</html>