<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>试卷详情</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
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
function downloadFile(){
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
	    	window.location.href = "/weixinPaper.app?method=downloadPaperFile&userid=${userid}&fileid=${fileinfo.fileid}";
	    }
	}else{
		window.location.href = "/weixinPaper.app?method=downloadPaperFile&userid=${userid}&fileid=${fileinfo.fileid}";
	}
}

function showMb(mobile){
	if(mobile == "ios"){
		$(".testpaper_details_main").html('<div class="testpaper_details_main_01"><p class="testpaper_details_main_p01">1</p><p>点击右上角"</p><img src="/weixin/images/details_03.png" style="height:6px;margin:0px 3px;margin-top:10px;"/><p>"按钮</p></div><div class="testpaper_details_main_01"><p class="testpaper_details_main_p01">2</p><p>选择&nbsp;"&nbsp;在Safari中打开&nbsp;"</p></div>');
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
	    title: '${fileinfo.filename }', // 分享标题 
	    desc: '年份:${fileinfo.theyear }，学科:${subjectname}，分类:${fileinfo.tkPaperType.typename }，所属地区:${fileinfo.area }', // 分享描述
	    link: '<%=MpUtil.HOMEPAGE %>/weixinPaper.app?method=getPaperFileDetail&userid=${userid}&fileid=${fileinfo.fileid}', // 分享链接
	    imgUrl: '<%=MpUtil.HOMEPAGE %>/weixin/images/${fileext_img }', // 分享图标
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
	    title: '${fileinfo.filename}【进步学堂】', // 分享标题
	    link: '<%=MpUtil.HOMEPAGE %>/weixinPaper.app?method=getPaperFileDetail&userid=${userid}&fileid=${fileinfo.fileid}', // 分享链接
	    imgUrl: '<%=MpUtil.HOMEPAGE %>/weixin/images/${fileext_img }', // 分享图标
	    success: function () { 
	    },
	    cancel: function () {  
	    }
	});
	
	//分享給QQ好友
	wx.onMenuShareQQ({
	    title: '${fileinfo.filename}', // 分享标题
	    desc: '年份:${fileinfo.theyear }，学科:${subjectname}，分类:${fileinfo.tkPaperType.typename }，所属地区:${fileinfo.area }', // 分享描述
	    link: '<%=MpUtil.HOMEPAGE %>/weixinPaper.app?method=getPaperFileDetail&userid=${userid}&fileid=${fileinfo.fileid}', // 分享链接
	    imgUrl: '<%=MpUtil.HOMEPAGE %>/weixin/images/${fileext_img }', // 分享图标
	    success: function () { 
	       // 用户确认分享后执行的回调函数
	    },
	    cancel: function () { 
	      
	    }
	});
	
	//分享到QQ空間
	wx.onMenuShareQZone({
	    title: '${fileinfo.filename}', // 分享标题
	    desc: '年份:${fileinfo.theyear }，学科:${subjectname}，分类:${fileinfo.tkPaperType.typename }，所属地区:${fileinfo.area }', // 分享描述
	    link: '<%=MpUtil.HOMEPAGE %>/weixinPaper.app?method=getPaperFileDetail&userid=${userid}&fileid=${fileinfo.fileid}', // 分享链接
	    imgUrl: '<%=MpUtil.HOMEPAGE %>/weixin/images/${fileext_img }', // 分享图标
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
<body>	
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
            <p>选择&nbsp;"&nbsp;在浏览器中打开&nbsp;"</p>
        </div>
    </div>
</div>	
<div class="search" style="z-index:99;">
	<a href="javascript:history.back()">
	<div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">试卷详情</p>
    </div>
</div>
<div class="search_padding"></div>
<!-----class_main-------->
<div class="testpaper_details">
	<p class="testpaper_details_title">${fileinfo.filename }</p>
    <p>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;份：${fileinfo.theyear }年</p>
    <p>学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;科：${subjectname}</p>
    <p>分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类：${fileinfo.tkPaperType.typename }</p>
    <p class="testpaper_details_p">所属地区：${fileinfo.area }</p>
    <p class="testpaper_details_p">试卷大小：${filesize}</p>
    <p class="testpaper_details_p">下载次数：${fileinfo.downloads}&nbsp;次</p>
</div>
<div class="password_a payment_time_title" style="width:auto;">
    	<a href="javascript:downloadFile()">下载试卷</a>
</div>
<div class="password_a payment_time_title" style="width:auto;">
	<a href="/weixinVod.app?method=goodFilmBookList&userid=${userid }">查看试卷解题微课</a>
</div>
</body>
</html>