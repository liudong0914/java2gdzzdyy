<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
<div id="video" style="position:relative;z-index: 100;width:600px;height:400px;float: left;"><div id="a1"></div></div>
<%
ZxHelpFile model = (ZxHelpFile)request.getAttribute("model"); 
%>
<script type="text/javascript" src="/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
<script type="text/javascript">
		 var flashvars={
			        f:'${homePage}/upload/<%=model.getFlagl() %>',
			        c:0,
			        p:1,
			        h:3
			    };
			    var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
			    var video=['${homePage}/upload/<%=model.getFlagl() %>->video/mp4'];
			    CKobject.embed('/ckplayer/ckplayer/ckplayer.swf','a1','ckplayer_a1','600','400',false,flashvars,video,params);
</script>

</body>
</html>
