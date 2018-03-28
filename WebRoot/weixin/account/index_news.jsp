<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
*{ margin:0; padding:0;}
ul,li{margin:0;padding:0}
#scrollDiv{width:100%;height:28px;line-height:28px;overflow:hidden;text-overflow: ellipsis;white-space: nowrap;display: -webkit-box; -webkit-line-clamp: 2;-webkit-box-orient: vertical;}
#scrollDiv li{height:28px;padding-left:8px; line-height:35px; font-size:15px; color:#000000; letter-spacing:0.5px;overflow:hidden;text-overflow: ellipsis;white-space: nowrap}
#scrollDiv a{ text-decoration:none;}
</style>
<script type="text/javascript" src="/weixin/js/jquery-2.1.1.min.js" ></script>
<script type="text/javascript">
function AutoScroll(obj){
        $(obj).find("ul:first").animate({
                marginTop:"-28px"
        },1000,function(){
                $(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
        });
}
$(document).ready(function(){
setInterval('AutoScroll("#scrollDiv")',3000)
});
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<div id="scrollDiv">
  <ul>
  <logic:iterate id="model" name="newslist">
    <a href="/weixinAccountIndex.app?method=getNews&newsid=${model.newsid}&userid=${userid}" target="_parent"><li>${model.title}</li></a>
  </logic:iterate>
  </ul>
</div>
</body>
</html>