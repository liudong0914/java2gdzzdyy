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
    jsApiList: ['scanQRCode','chooseImage','previewImage','uploadImage','downloadImage']
});
function previewPic(picurl){
	var imgurl = '<%=MpUtil.HOMEPAGE %>' + picurl;
	wx.previewImage({
	    current: imgurl,
	    urls: [imgurl]
	});
}
</script>