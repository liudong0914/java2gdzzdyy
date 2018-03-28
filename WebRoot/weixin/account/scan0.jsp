<script type="text/javascript" src="/weixin/js/jweixin-1.0.0.js"></script>
<%@page import="com.wkmk.weixin.mp.MpUtil"%>
<%@page import="java.util.Map"%>
<%Map<String, String> jsticket = (Map<String, String>)request.getAttribute("jsticket"); %>
<script>
wx.config({
    debug: false,
    appId: '<%=MpUtil.APPID %>',
    timestamp: <%=jsticket.get("timestamp") %>,
    nonceStr: '<%=jsticket.get("nonceStr") %>',
    signature: '<%=jsticket.get("signature") %>',
    jsApiList: ['scanQRCode',
                'checkJsApi',
            	'startRecord',
            	'stopRecord',
            	'onVoiceRecordEnd',
            	'playVoice',
            	'pauseVoice',
            	'stopVoice',
            	'onVoicePlayEnd',
            	'uploadVoice',
            	'downloadVoice',
            	'chooseImage',
            	'uploadImage',
            	'onMenuShareTimeline', 
            	'onMenuShareAppMessage', 
            	'onMenuShareQQ', 
            	'onMenuShareQZone',
            	'chooseWXPay'
               ]
});

function previewPic(piv,url){
 	var image=document.getElementById(piv);
 	var oldurl=document.getElementById(piv).src; 
 	//image.src=url;
 	var a=document.getElementById("A_"+piv);
 	if(oldurl.indexOf("/s_") == -1){
 		a.innerHTML = "<img id=\"" + piv + "\" src=\"" + url + "\"/>";
 	}else{
 		a.innerHTML = "<div style=\"overflow-x:auto!important;width:100%!important;\"><img id=\"" + piv + "\" src=\"" + url + "\"/></div>";
 	}
 	
 	//var index=oldurl.lastIndexOf("/upload");
 	//oldurl=oldurl.substring(index);
 	a.href="javascript:previewPic('"+piv+"','"+oldurl+"')";
 	/*
 	if(window.VBArray){
 		document.execCommand("Stop");
 	}else{
  		window.stop(); 
 	}
 	*/
}
</script>
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