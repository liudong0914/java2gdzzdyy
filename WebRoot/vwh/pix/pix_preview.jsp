<%@page import="com.wkmk.vwh.bo.VwhFilmPix"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="/ckplayer/js/offlights.js"></script>
</head>

<body>
<div id="video" style="position:relative;z-index: 100;width:600px;height:400px;float: left;"><div id="a1" style="width:100%;height:400px;"></div></div>
<%
VwhFilmPix model = (VwhFilmPix)request.getAttribute("model"); 
%>
<script type="text/javascript" src="/ckplayerX/ckplayer/ckplayer.js" charset="utf-8"></script>
<script type="text/javascript">
	var videoObject = {
		container: '#a1',
		variable: 'player',
		flashplayer:true,
		video:'CE:${playurl}'
	};
	var player=new ckplayer(videoObject);
  </script>

</body>
</html>
