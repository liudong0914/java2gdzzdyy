<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>加入班级</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/weixin/js/jweixin-1.0.0.js"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script type="text/javascript">
function addClass1(){
    document.pageForm.action="/weixinStudent.app?method=addClass1";
    document.pageForm.submit();
}
var scanCodeResult;
function scanClass(){
	wx.scanQRCode({
	    needResult: 1,
	    scanType: ["qrCode","barCode"],
	    success: function (res) {
		    var result = res.resultStr;
		    scanCodeResult = result;
		    //window.location.href = "";
		    //由于以上直接跳转地址的访问不稳定，会经常在页面上再覆盖一个页面直接显示二维码返回值，故采用下面的方式处理
		    setTimeout("gotoLink()", 500);
		}
	});
}
function gotoLink(){
	document.pageForm.action="/weixinStudent.app?method=scanClass&scanCodeResult=" + scanCodeResult;
    document.pageForm.submit();
}
</script>
<style>
:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
    color: #bdbdbd;  
}
::-moz-placeholder { /* Mozilla Firefox 19+ */
    color: #bdbdbd;
}
input:-ms-input-placeholder,
textarea:-ms-input-placeholder {
    color: #bdbdbd;
}
.botton6 input::-webkit-input-placeholder,
textarea::-webkit-input-placeholder {
    color: #bdbdbd;
}
</style>
</head>

<body>
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=student&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">加入班级-第一步</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form name="pageForm" method="post">
<div id="content">
    <div class="content_botton">
		<div class="botton3" style="display:none;">
			<input type="text" name="classno" value="" placeholder="输入班级编码" class="botton6"/>
		</div>
    	<a href="javascript:scanClass()">
    		<div class="botton3">
    			<p>扫一扫</p>
    		</div>
    	</a>
    	<a href="javascript:addClass1()">
    		<div class="botton4">
    			<p>下一步</p>
    		</div>
    	</a>
    	<a href="/weixinStudent.app?method=addBook&userid=${userid }" class="botton5">没有班级？直接绑定作业本</a>
    </div>
    <logic:present name="errmsg"><div class="tips"><bean:write name="errmsg" /></div></logic:present>
</div>
<input type="hidden" name="userid" value="${userid }"/>
</form>
</body>
</html>