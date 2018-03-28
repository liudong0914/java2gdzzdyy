<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script type="text/javascript">
var scanResult;
function scanCode(){
	wx.scanQRCode({
	    needResult: 1,
	    scanType: ["qrCode","barCode"],
	    success: function (res) {
		    var result = res.resultStr;
		    scanResult = result;
		    setTimeout("gotoLink()", 500);
		}
	});
}
function gotoLink(){
	window.location.href = "/weixinScanQRCode.app?method=scan&userid=${userid }&scanResult=" + scanResult;
}
</script>
<a class="footer_0" onclick="scanCode()" style="cursor:pointer;">
	<img src="/weixin/images/sys.png"/>
	<p>扫一扫</p>
</a>