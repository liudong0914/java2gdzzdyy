<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>在线听力</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/weixin/js/jquery-2.1.1.min.js"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body  style="background-color:#f7f8f8;">
<logic:equal value="1" name="flag">
<div class="search search_write">
	<a href="javascript:history.back();">
		<div  class="search_left">
	    	<img src="/weixin/images/a01.png" class="search_class" />
	    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">在线听力</p>
    </div>
</div>
<div style="padding-top:40px;"></div>
</logic:equal>
<form method="post" name="pageForm" >
<div class="music">
	<div class="music_tltle">
    	<div class="music_tltle_font">
    		<span>${model.title }[听力]</span>
       	</div>
    </div>
    <!-- 说明：由于特殊原因导致有的手机无法播放audio标签文件，所以改为video标签，具体原因未查明（20170208）。手机上视频标签是全屏播放，体验不好 -->
    <div style="width:100%;height:35px;" id="audioPlayDiv">
    <audio controls="controls" autoplay="autoplay" volume="1" style="width:100%;height:35px;" id="audioPlay">
	  <source src="/upload/${model.mp3path}" type="audio/mpeg" />
		<EMBED height=100 type=application/x-shockwave-flash pluginspage=http://www.macromedia.com/go/getflashplayer width=100% src=/libs/swf/mp3.swf?soundFile=/upload/${model.mp3path}&amp;bg=0xCDDFF3&amp;leftbg=0x357DCE&amp;lefticon=0xF2F2F2&amp;rightbg=0x357DCE&amp;rightbghover=0x4499EE&amp;righticon=0xF2F2F2&amp;righticonhover=0xFFFFFF&amp;text=0x357DCE&amp;slider=0x357DCE&amp;track=0xFFFFFF&amp;border=0xFFFFFF&amp;loader=0x8EC2F4&amp;autostart=yes&amp;loop=no allowscriptaccess="never" quality="high" wmode="transparent"></EMBED>
	</audio>
	</div>
    <div class="music_main" style="height:300px;">
    	<img src="/weixin/images/music.gif"/>
    </div>
</div>
<%@ include file="/weixin/account/footer.jsp"%>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
</form>
<script>
function playAudio(){
	var audio = document.getElementById('audioPlay');
	if(audio){
		var duration = audio.duration;
		if(isNaN(duration)){
			document.getElementById('audioPlayDiv').innerHTML = '<video controls="controls" autoplay="autoplay" volume="1" style="width:100%;height:35px;"><source src="/upload/${model.mp3path}" type="audio/mpeg" /></video>';
		}
	}
}
setTimeout("playAudio()",5000);
</script>
</body>
</html>