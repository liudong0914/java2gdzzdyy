<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>抢单失败</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>	
<div class="search search_write">
	<a href="/weixinHelp.app?method=info&questionid=${question.questionid }&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">抢单失败</p>
    </div>
</div>
<div class="search_padding"></div>

<img src="/weixin/images/sb.png" class="sb" />
<p class="sb_p">当前答疑提问已被其他老师抢走</p>

</body>
</html>