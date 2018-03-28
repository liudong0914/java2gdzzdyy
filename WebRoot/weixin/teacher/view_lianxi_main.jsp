<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>查看作业习题</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript" src="/weixin/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
var height = 500;
if(document.documentElement.clientHeight){
	height = document.documentElement.clientHeight - 35;
}
if(document.body.clientHeight){
	height = document.body.clientHeight - 35;
}
function SetCwinHeight(){
	var iframeid = document.getElementById("frmmain");
	iframeid.height = height;
	/*
	if (document.getElementById){
	   if (iframeid && !window.opera){
		   if (iframeid.contentDocument && iframeid.contentWindow.document.documentElement.scrollHeight){
		     var height1 = iframeid.contentWindow.document.documentElement.scrollHeight
		   	 iframeid.height = height1>550?height1:550;
		   }else if(iframeid.Document && iframeid.Document.body.scrollHeight){
		     var height2 = iframeid.Document.body.scrollHeight;
		  	 iframeid.height = height2>550?height2:550;
		   }
	   }
	}
	*/
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<div style="width:100%; float:left; margin-top:5px;">
<audio controls="controls" autoplay="autoplay" style="width:100%;height:30px;">
  <source src="/upload/${model.mp3path}" type="audio/mpeg" />
	<EMBED height=30 type=application/x-shockwave-flash pluginspage=http://www.macromedia.com/go/getflashplayer width=100% src=/libs/swf/mp3.swf?soundFile=/upload/${model.mp3path}&amp;bg=0xCDDFF3&amp;leftbg=0x357DCE&amp;lefticon=0xF2F2F2&amp;rightbg=0x357DCE&amp;rightbghover=0x4499EE&amp;righticon=0xF2F2F2&amp;righticonhover=0xFFFFFF&amp;text=0x357DCE&amp;slider=0x357DCE&amp;track=0xFFFFFF&amp;border=0xFFFFFF&amp;loader=0x8EC2F4&amp;autostart=yes&amp;loop=no allowscriptaccess="never" quality="high" wmode="transparent"></EMBED>
</audio>
</div>
<iframe scrolling="auto" width="100%" height="500" frameBorder="0" id="frmmain" name="frmmain" src="/weixinTeacher.app?method=viewLianxi&userid=${userid }&bookcontentid=${bookcontentid }&bookid=${bookid }&classid=${classid }&index=${index }&next=${next }" allowTransparency="true" onload="SetCwinHeight()"></iframe>
</body>
</html>