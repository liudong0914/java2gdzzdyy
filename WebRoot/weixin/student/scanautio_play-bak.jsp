<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>在线试听</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/weixin/js/jquery-2.1.1.min.js"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body style="background-color:#f3f3f3;">
<form method="post" name="pageForm" >
<div id="container" style="padding-bottom:45px;">
    <div class="container_class_title">
    	<div class="container_class_title_size">
    		<p>${model.title }[听力]</p>
        </div>
    </div>
    <div class="container_class">
    	<div style="width:100%; float:left; margin-top:50px;">
		<audio controls="controls" autoplay="autoplay" style="width:100%;height:100px;">
		  <source src="/upload/${model.mp3path}" type="audio/mpeg" />
			<EMBED height=100 type=application/x-shockwave-flash pluginspage=http://www.macromedia.com/go/getflashplayer width=100% src=/libs/swf/mp3.swf?soundFile=/upload/${model.mp3path}&amp;bg=0xCDDFF3&amp;leftbg=0x357DCE&amp;lefticon=0xF2F2F2&amp;rightbg=0x357DCE&amp;rightbghover=0x4499EE&amp;righticon=0xF2F2F2&amp;righticonhover=0xFFFFFF&amp;text=0x357DCE&amp;slider=0x357DCE&amp;track=0xFFFFFF&amp;border=0xFFFFFF&amp;loader=0x8EC2F4&amp;autostart=yes&amp;loop=no allowscriptaccess="never" quality="high" wmode="transparent"></EMBED>
		</audio>
		</div>
    </div>
</div>
<div id="footer">
	<a href="/weixinAccountIndex.app?method=userindex&userid=${userid }" class="footer_0">
    	<img src="/weixin/images/wd.png" />
        <p>首页</p>
    </a>
    <%@ include file="/weixin/index/scan_footer.jsp"%>
    <a href="/weixinAccountIndex.app?method=myAccount&userid=${userid }" class="footer_00">
    	<img src="/weixin/images/user.png" />
    	<p>我的账号</p>
    </a>
</div>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
</form>
</body>
</html>