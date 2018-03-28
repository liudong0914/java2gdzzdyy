<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="../../public/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>播放器演示</title>
</head>
<body>
<div id="a1"></div>
<script type="text/javascript" src="/ckplayer/ckplayer.js" charset="utf-8"></script>
<script type="text/javascript">
    var flashvars={
        f:'/vod/201706/1.mp4',
        s:1,
        c:0,
        p:1,
        h:1
    };
    var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always',wmode:'transparent'};
    CKobject.embedSWF('ckplayer/ckplayer.swf','a1','ckplayer_a1','800','600',flashvars,params);    
</script>
</body>
</html>
