<!doctype html>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.util.common.Constants"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>提示</title>
<link href="/weixin/loginpc/css/common.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
 
<body>
<img src="/weixin/loginpc/img/img.png" class="img">
<p class="p"><%=Constants.PRODUCT_NAME %>电脑端登录确认</p>
<p class="p3">二维码过期，请重新扫码登录</p>
<a href="javascript:scanCode();"><div class="Feedback_button1">
	<p>重新扫描</p>
</div></a>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script type="text/javascript">
var scanResult;
function scanCode(){
	wx.scanQRCode({
	    needResult: 1,
	    scanType: ["qrCode","barCode"],
	    success: function (res) {
		    var result = res.resultStr;
		    alert(result);
		    window.location.href = result;
		}
	});
}
</script>
</body>
</html>