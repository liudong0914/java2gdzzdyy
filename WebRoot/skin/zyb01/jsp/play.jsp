<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线学习</title>
<link type="text/css" rel="stylesheet" href="/skin/zyb01/css/style.css">
</head>

<body>
<%@ include file="top.jsp"%>

<div class="video">
	<div class="video_main">
    	<div class="video_main_left">
        	<p class="video_main_left_P">[<logic:equal value="1" name="contentFilm" property="type">解题微课</logic:equal><logic:equal value="2" name="contentFilm" property="type">举一反三微课</logic:equal>]${contentFilm.title }</p>
        	<div id="a1"></div>
			<script type="text/javascript" src="/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
			<script type="text/javascript">
				var flashvars={
					f:'/zyb.bo?method=url%26vid=${playid}',
					s:'1',
					c:'0',
					e:'1',
					v:'80',
					i:'/upload/${filmPix.imgpath}',
					p:'1',
					h:'3',
					my_url:encodeURIComponent(window.location.href)
					};
				var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always'};
				var video=['/zyb.bo?method=url&vid=${playid}->ajax/get/utf-8'];
				CKobject.embed('/ckplayer/ckplayer/ckplayer.swf','a1','ckplayer_a1','900','540',false,flashvars,video,params);
			</script>
        </div>
        <div class="video_main_menu">
        	<div class="video_main_menu_title">
            	<p class="video_main_menu_title_p">试听微课</p>
            </div>
            <div class="video_main_menu_font">
            	<%
		    	List filmlist = (List)request.getAttribute("filmlist");
		    	Object[] obj = null;
		    	VwhFilmInfo vfi = null;
		    	for(int i=0, size=filmlist.size(); i<size; i++){
		    		obj = (Object[])filmlist.get(i);
		    		vfi = (VwhFilmInfo)obj[0];
		    	%>
            	<a href="/zyb-play-1-<%=obj[2] %>.htm" target="_blank"><%if(i < 10){ %>0<%} %><%=i+1 %>&nbsp;<%=vfi.getTitle() %></a>
            	<%} %>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp"%>
</body>
</html>