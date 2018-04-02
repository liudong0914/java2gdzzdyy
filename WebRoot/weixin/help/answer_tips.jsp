<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>教师认证提示</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<script src="/weixin/js/jquery-1.10.2.js" type="text/javascript"></script>
<%@ include file="/weixin/account/scan0.jsp"%>
<script type="text/javascript">
var browser = {
    versions: function () {
        var u = navigator.userAgent, app = navigator.appVersion;
        return {         //移动终端浏览器版本信息
            trident: u.indexOf('Trident') > -1, //IE内核
            presto: u.indexOf('Presto') > -1, //opera内核
            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
            iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
            iPad: u.indexOf('iPad') > -1, //是否iPad
            webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
        };
    }(),
    language: (navigator.browserLanguage || navigator.language).toLowerCase()
}
function shareUrl(){
	var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
	if (browser.versions.mobile) {//判断是否是移动设备打开。browser代码在下面
	    var ua = navigator.userAgent.toLowerCase();//获取判断用的对象
	    if (ua.match(/MicroMessenger/i) == "micromessenger") {
	    	if (browser.versions.ios) {
	    		showMb("ios");
	    	}else{
	    		showMb("android");
	    	}
	    }else{
	    	window.location.href = "<%=MpUtil.HOMEPAGE %>/plogin.do?method=cert&key=${key }";
	    }
	}else{
		window.location.href = "<%=MpUtil.HOMEPAGE %>/plogin.do?method=cert&key=${key }";
	}
}

function showMb(mobile){
	if(mobile == "ios"){
		$(".testpaper_details_main").html('<div class="testpaper_details_main_01"><p class="testpaper_details_main_p01">1</p><p>点击右上角"</p><img src="/weixin/images/details_03.png" style="height:6px;margin:0px 3px;margin-top:10px;"/><p>"按钮</p></div><div class="testpaper_details_main_01"><p class="testpaper_details_main_p01">2</p><p>选择&nbsp;"&nbsp;分享到手机QQ&nbsp;"</p></div>');
		$("#bg").css({ height: $(document).height()});
	    $("#bg,.payment_time_mask").show();
	}else{
		$("#bg").css({ height: $(document).height()});
	    $("#bg,.payment_time_mask").show();
	}
}

function hideMb(){
	 $("#bg,.payment_time_mask").hide();
}

wx.ready(function(){
	wx.showOptionMenu();
	
	//分享給微信好友
	wx.onMenuShareAppMessage({
	    title: '教师资格认证', // 分享标题 
	    desc: '教师在线资格认证申请', // 分享描述
	    link: '<%=MpUtil.HOMEPAGE %>/plogin.do?method=cert&key=${key }', // 分享链接
	    imgUrl: '<%=MpUtil.HOMEPAGE %>/weixin/images/share.png', // 分享图标
	    type: '', // 分享类型,music、video或link，不填默认为link
	    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
	    success: function () { 
	        // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
	
	//分享到朋友圈
	wx.onMenuShareTimeline({
	    title: '[分享到朋友圈失效]', // 分享标题
	    link: ' ', // 分享链接
	    imgUrl: '', // 分享图标
	    success: function () { 
	    },
	    cancel: function () {  
	    }
	});
	
	//分享給QQ好友
	wx.onMenuShareQQ({
	    title: '教师资格认证', // 分享标题
	    desc: '教师在线资格认证申请', // 分享描述
	    link: '<%=MpUtil.HOMEPAGE %>/plogin.do?method=cert&key=${key }', // 分享链接
	    imgUrl: '<%=MpUtil.HOMEPAGE %>/weixin/images/share.png', // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	      
	    }
	});
	
	//分享到QQ空間
	wx.onMenuShareQZone({
	    title: '[分享到QQ空间失效]', // 分享标题
	    desc: ' ', // 分享描述
	    link: ' ', // 分享链接
	    imgUrl: '', // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	        // 用户取消分享后执行的回调函数
	    }
	});
});
</script>
</head>

<body style="background-color: #fff;">	
<div id="bg">
<a href="javascript:hideMb()"  class="details_a" ></a> 
</div>
<div class="payment_time_mask">
    <img src="/weixin/images/details.png"  class="testpaper_details_img"  />
    <div class="testpaper_details_main">
    	<div class="testpaper_details_main_01">
            <p class="testpaper_details_main_p01">1</p>
            <p>点击右上角"</p>
            <img src="/weixin/images/details_02.png" />
            <p>"按钮</p>
        </div>
        <div class="testpaper_details_main_01">
        	<p class="testpaper_details_main_p01">2</p>
            <p>选择&nbsp;"&nbsp;分享到手机QQ&nbsp;"</p>
        </div>
    </div>
</div>

<div class="search search_write" style="z-index:99;">
	<a href="javascript:history.back();">
		<div  class="search_left">
	    	<img src="/weixin/images/a01.png" class="search_class" />
	    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">教师认证提示</p>
    </div>
</div>
<div class="search_padding"></div>

<p class="password_p password_p01" style="padding-right:10px;">
尊敬的[${sysUserInfo.username }]老师，您好！
</br></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;为了保障学生的利益，同时也为了保障您个人的利益，在线回答学生提问挣得学币（学币可提现），需要在广东省中职德育云平台平台经过教师资格在线认证通过后，方可回答问题。
</br></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请通过电脑访问网址<%=MpUtil.HOMEPAGE %>/plogin.do?method=cert&key=${key }进行教师资格在线认证，认证地址有效期为5分钟，如失效请重新获取。
</p> 

<div class="password_a payment_time_title" style="width:auto;">
    <a href="javascript:shareUrl()">分享地址到电脑端打开</a>
</div>
</body>
</html>