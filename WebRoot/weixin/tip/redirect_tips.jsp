<%@page import="com.wkmk.weixin.mp.MpUtil"%>
<%
String requestQueryString = (String)request.getAttribute("requestQueryString");
%>
<script type="text/javascript">
window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=<%=MpUtil.APPID %>&redirect_uri=<%=MpUtil.HOMEPAGE %>/weixinScanTwoCode.app%3frequestQueryString%3d<%=requestQueryString %>&response_type=code&scope=snsapi_userinfo&state=scan#wechat_redirect";
</script>
