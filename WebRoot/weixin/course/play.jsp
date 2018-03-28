<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>${eduCourseInfo.title }</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/ckplayerX/ckplayer/ckplayer.js" charset="utf-8"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search">
	<a href="/weixinCourse.app?method=courseFilmList&userid=${userid}&courseid=${eduCourseFilm.courseid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">课程微课学习</p>
    </div>
    <a href="javascript:showDH()" onClick="showtext()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding" ></div>

<div class="play">
<div id="a1" style="width:100%;height:100%;"></div>

	<script type="text/javascript">
		var videoObject = {
			container: '#a1',
			variable: 'player',
			flashplayer:false,
			video:'${playurl}'
		};
		var player=new ckplayer(videoObject);
	</script>
</div>

<div id="Tab1" class="play_menu" style="display:block">
    <div class="Menubox">
    <ul>
        <li class="hover" style="text-align:center;margin:0px 40%;">简介</li>
    </ul>
    </div>
    <div class="Contentbox">
        <div>
        	<p class="Contentbox_p02">播放次数：<span>${eduCourseFilm.hits }</span>次</p>
        	<p class="Contentbox_p02">所属课程：${eduCourseInfo.title } > <logic:present name="parent">${parent.title } > </logic:present>${column.title } > ${eduCourseFilm.title }</p>
        </div>
    </div>
</div>
</body>
</html>