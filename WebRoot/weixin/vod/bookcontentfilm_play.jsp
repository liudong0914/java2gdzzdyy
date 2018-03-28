<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>试听专享</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script>
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		var con=document.getElementById("con_"+name+"_"+i);
		menu.className=i==cursel?"hover":"";
		con.style.display=i==cursel?"block":"none";
	}
}

function changePlay(imgpath,mp4path,obj){
	var newAddress = "{i->/upload/" + imgpath + "}{html5->http://${computerinfo.ip}:${computerinfo.port}/upload/" + mp4path + "->video/mp4}";
	CKobject.getObjectById('a1').newAddress(newAddress);
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search">
	<a href="javascript:history.back();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">试听专享</p>
    </div>
    <a href="javascript:showDH()" onClick="showtext()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div class="play" class="play_img">
    <div id="a1"></div>
	<script type="text/javascript" src="/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
	<script type="text/javascript">
		var flashvars={
			p:1,
			e:1,
			i:'/upload/${filmpix.imgpath}'
			};
		var video=['http://${computerinfo.ip}:${computerinfo.port}/upload/${filmpix.flvpath}->video/mp4'];
		var support=['all'];
		CKobject.embedHTML5('a1','ckplayer_a1','100%','100%',video,flashvars,support);
	</script>
</div>

<div id="Tab1" class="play_menu">
    <div class="Menubox">
    <ul>
    	<li class="play_menu_ul_li"></li>
        <li id="one1"style="width:76%;" class="hover">简介</li>
    </ul>
    </div>
    <div class="Contentbox">
        <div id="con_one_1">
        	<p class="Contentbox_p">[${bookcontentfilm.type eq '1'?"解题微课":bookcontentfilm.type eq '2'?"举一反三":"" }]</p>
            <p>微课名称:${bookcontentfilm.title}</p>
			<p>播放次数:${audition.hits}次</p>
			<p>所属作业:${book.title}, ${bookcontent.title}</p>
        </div>
    </div>
    <div class="password_a">
    	<a href="/weixinVod.app?method=booklist&userid=${userid }">购买解题微课</a>
    </div>
</div>
</body>
</html>