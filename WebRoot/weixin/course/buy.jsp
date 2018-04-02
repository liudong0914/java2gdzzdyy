<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.edu.bo.EduCourseFilm"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>购买课程微课</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-1.10.2.js" type="text/javascript"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<div id="bg">
</div>
<div class="payment_time_mask">
    <div class="wk_buy" >
        <a class="wk_buy_a"></a> 
        <p class="wk_buy_p">请输入支付密码</p>    
        <p class="wk_buy_p2">广东省中职德育云平台</p> 
        <p class="wk_buy_p3">¥<fmt:formatNumber type="number" value="${eduCourseFilm.sellprice }" pattern="#.00"/><span style="font-size:12px;">(学币)</span></p> 	
        <p class="wk_buy_p4">余额：<fmt:formatNumber type="number" value="${sysUserInfo.money }" pattern="#.00"/>(学币)<span style="font-size:14px;text-align:right;float:right;padding-right:10px;color:#0000ff;text-decoration:underline;" onclick="wxpay()">微信在线支付</span></p>
        <%
        String iswxpay = "0";
        String paypassword = (String)request.getAttribute("paypassword");
        EduCourseFilm eduCourseFilm = (EduCourseFilm)request.getAttribute("eduCourseFilm");
        SysUserInfo sysUserInfo = (SysUserInfo)request.getAttribute("sysUserInfo");
        if(sysUserInfo.getMoney() < eduCourseFilm.getSellprice() || "0".equals(sysUserInfo.getPaypassword())){
        	if(sysUserInfo.getMoney() < eduCourseFilm.getSellprice()){
        		iswxpay = "1";//微信直接在线购买
        %>
        <p class="wk_buy_p" style="color:#00aa00;text-decoration:underline;cursor:pointer;" onclick="recharge()">余额不足，去充值</p>
        <%}else{%>
        <p class="wk_buy_p" style="color:#00aa00;text-decoration:underline;cursor:pointer;" onclick="getPwd()">首次使用，去设置支付密码</p>
        <%}}else{ %>
        <%if("1".equals(paypassword)){ %>
        <div class="input">
        	<input type="password" name="pwd1" id="pwd1" value="" maxlength="1" readonly="readonly"/>   
            <input type="password" name="pwd2" id="pwd2" value="" maxlength="1" readonly="readonly"/>
            <input type="password" name="pwd3" id="pwd3" value="" maxlength="1" readonly="readonly"/>
            <input type="password" name="pwd4" id="pwd4" value="" maxlength="1" readonly="readonly"/>
            <input type="password" name="pwd5" id="pwd5" value="" maxlength="1" readonly="readonly"/>
            <input type="password" name="pwd6" id="pwd6" value="" maxlength="1" readonly="readonly"/>  
        </div>
        <p style="padding-top:5px;">
        <span style="font-size:12px;color:#ff0000;padding-left:10px;text-align:left;float:left;" id="pwdtips"></span>
        <span style="font-size:12px;color:#777;text-align:right;float:right;padding-right:10px;" onclick="getPwd()">忘记密码？</span>
        </p>
        <p style="font-size:12px;color:#00aa00;padding-left:10px;padding-top:8px;text-align:left;float:left;">支付密码当天输错3次，将在第2天解锁。</p>
        <%}else{ %>
        <p style="font-size:14px;color:#ff0000;padding-left:10px;padding-top:8px;text-align:left;float:left;">为了您的支付安全，当天支付密码已输错3次，支付功能已被锁定，将在明天凌晨解锁。</p>
        <%}} %>
        <input type="hidden" name="hiddenpwdid" id="hiddenpwdid" value="1"/>
    </div>
    <div class="pay" <%if(sysUserInfo.getMoney() < eduCourseFilm.getSellprice() || "0".equals(sysUserInfo.getPaypassword()) || "0".equals(paypassword)){ %>style="display:none;"<%} %>>
    	<div class="pay_module">
            <a href="javascript:setPwd('1')" class="pay_module_a">
                <p class="pay_module01">1</p>
            </a>
            <a href="javascript:setPwd('2')"  class="pay_module_a">
                <p class="pay_module01">2</p>
                <p class="pay_module02">ABC</p>
            </a>
            <a href="javascript:setPwd('3')" >
                <p class="pay_module01">3</p>
                <p class="pay_module02">DEF</p>
            </a>
        </div>
        <div class="pay_module">
            <a href="javascript:setPwd('4')"  class="pay_module_a">
                <p class="pay_module01">4</p>
                <p class="pay_module02">GHI</p>
                
            </a>
            <a href="javascript:setPwd('5')"  class="pay_module_a">
                <p class="pay_module01">5</p>
                <p class="pay_module02">JKL</p>
            </a>
            <a href="javascript:setPwd('6')" >
                <p class="pay_module01">6</p>
                <p class="pay_module02">MNO</p>
            </a>
        </div>
        <div class="pay_module">
            <a href="javascript:setPwd('7')"  class="pay_module_a">
                <p class="pay_module01">7</p>
                <p class="pay_module02">PQRS</p>
                
            </a>
            <a href="javascript:setPwd('8')"  class="pay_module_a">
                <p class="pay_module01">8</p>
                <p class="pay_module02">TUV</p>
            </a>
            <a href="javascript:setPwd('9')">
                <p class="pay_module01">9</p>
                <p class="pay_module02">WXYZ</p>
            </a>
        </div>
        <div class="pay_module pay_module_mian">
            <a class="pay_module_a pay_module_a_left">                
            </a>
            <a href="javascript:setPwd('0')" class="pay_module_a pay_module_mian_a">
                <p class="pay_module01 pay_module03">0</p>
            </a>
            <a href="javascript:delPwd()" class="pay_module_a_left">
            	<img src="/weixin/images/pay.png" class="pay_module_a_img" />
            </a>
        </div>
    </div>
</div>
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search" style="z-index:999999;">
	<a href="/weixinCourse.app?method=courseFilmList&userid=${userid}&courseid=${eduCourseFilm.courseid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_wk">
		<a class="search_wk_a" style="margin-left:50px;">课程微课</a>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<div class="wk">
	<img src="/upload/${vwhFilmInfo.sketch }" class="wk_img"/>
	<p class="wk_p">${eduCourseInfo.title }／<logic:present name="parent">${parent.title }／</logic:present>${column.title }／${eduCourseFilm.title }</p>
    <div  class="wk_money">
    	<%if(eduCourseFilm.getSellprice() > 0){ %>
    	<p class="wk_money_p01">¥</p>
        <p class="wk_money_p"><fmt:formatNumber type="number" value="${eduCourseFilm.sellprice }" pattern="#.00"/><span style="font-size:12px;">(学币)</span></p>
        <%}else{ %>
        <p class="wk_money_p">免费</p>
        <%} %>
    </div>
    <div class="wk_font">
    	<span>${eduCourseFilm.discount}折</span>
        <span class="wk_font_span" >定价： <strike><fmt:formatNumber type="number" value="${eduCourseFilm.price }" pattern="#.00"/>(学币)</strike></span>
    	<span class="wk_font_span">销量：${eduCourseFilm.sellcount }笔</span>
    </div>
</div>

<div id="Tab1">
    <div class="Menubox">
    <ul>
        <li class="hover" style="text-align:center;margin:0px 40%;">简介</li>
    </ul>
    </div>
    <div class="Contentbox">
        <div>
        	<p class="Contentbox_p02">播放次数：<span>${eduCourseFilm.hits }</span>次</p>
        	<p class="Contentbox_p02">所属课程：${eduCourseInfo.title } > <logic:present name="parent">${parent.title } > </logic:present>${column.title } > ${eduCourseFilm.title }</p>
        </div>
    </div>
</div>
<%if(eduCourseFilm.getSellprice() > 0){ %>
<div class="payment_time_title">   
	<a class="button" >立即购买</a>
</div>
<%}else{ %>
<div>   
	<a class="button" style="background:#3cd5a2;" href="/weixinCourse.app?method=play&userid=${userid }&coursefilmid=${eduCourseFilm.coursefilmid }">立即查看</a>
</div>
<%} %>

<div id="div_hidden" style="width:100%; height:100%; overflow:hidden; position:absolute; top:0px; z-index:999999999;background:rgba(0,0,0,0.5); display:none;"><div style="z-index:9999999999;auto:30% 0px;top:45%;position:absolute;left:35%;color:#ff0000;background-color:#fff;border: 1px solid #eee; border-radius: 5px;padding: 4px 10px;">处理中...</div></div>

<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script>
function setPwd(pwd){
	var hiddenpwdid = document.getElementById("hiddenpwdid").value;
	document.getElementById("pwd" + hiddenpwdid).value = pwd;
	if(hiddenpwdid == "6"){
		pay();
	}else{
		var newhiddenpwdid = parseInt(hiddenpwdid) + 1;
		document.getElementById("hiddenpwdid").value = newhiddenpwdid;
	}
}
function delPwd(){
	var hiddenpwdid = document.getElementById("hiddenpwdid").value;
	if(hiddenpwdid != "1"){
		var newhiddenpwdid = parseInt(hiddenpwdid) - 1;
		document.getElementById("pwd" + newhiddenpwdid).value = "";
		document.getElementById("hiddenpwdid").value = newhiddenpwdid;
	}
}
function pay(){
	var check = "1";
	var paypwd = "";
	for(var i=1; i<=6; i++){
    	if(document.getElementById("pwd" + i).value == ""){
    		document.getElementById("pwdtips").innerHTML = "密码输入错误！";
    		check = "0";
    		break;
    	}else{
    		paypwd = paypwd + document.getElementById("pwd" + i).value;
    	}
    }
    if(check == "1"){
    	$.ajax({
	        type:"post",
	        url:"/weixinCourse.app?method=pay",
	        data:"userid=${userid}&coursefilmid=${eduCourseFilm.coursefilmid}&paypwd=" + paypwd,
	        success:function(msg){
	        	if(msg == "ok"){
	        		window.location.replace("/weixinCourse.app?method=play&userid=${userid }&coursefilmid=${eduCourseFilm.coursefilmid}");
	        	}else{
	        		document.getElementById("pwdtips").innerHTML = "密码输入错误" + msg + "次！";
	        		var errcount = parseInt(msg);
	        		if(errcount < 3){
		        		for(var i=1; i<=6; i++){
					    	document.getElementById("pwd" + i).value = "";
					    }
					    document.getElementById("hiddenpwdid").value = "1";
	        		}else{
	        			window.location.replace("/weixinCourse.app?method=buy&userid=${userid }&coursefilmid=${eduCourseFilm.coursefilmid}");
	        		}
	        	}
   			}
		});
    }
}
function recharge(){
	window.location.href = "/wxpay/weixinPay.app?method=index&userid=${userid }";
}
function getPwd(){
	//支付密码设置成功后，自动跳转到此再次购买
	var redirecturl = "%2fweixinCourse.app%3fmethod%3dbuy%26userid%3d${userid }%26coursefilmid%3d${eduCourseFilm.coursefilmid }";//转码处理
	window.location.href = "/weixinAccountIndex.app?method=beforeUpdatePwd&userid=${userid}&flag=1&redirecturl=" + redirecturl;
}
$(function () {
    $(".payment_time_title a").click(function () {
    	<%if(eduCourseFilm.getSellprice() > 0){ %>
    	var iswxpay = '<%=iswxpay %>';
    	if(iswxpay == "1"){
    		wxpay();
    	}else{
	    	$("#bg").css({
	            display: "block", height: $(document).height()
	        });
	        var $box = $('.payment_time_mask');
	        $box.css({
	            display: "block",
	        });
	        for(var i=1; i<=6; i++){
		    	document.getElementById("pwd" + i).value = "";
		    }
		    document.getElementById("hiddenpwdid").value = "1";
    	}
    	<%}else{%>
    	//点击支付弹出蒙板，避免重复点击
    	document.getElementById("div_hidden").style.display = "block";
    	$.ajax({
	        type:"post",
	        url:"/weixinCourse.app?method=pay",
	        data:"userid=${userid}&coursefilmid=${eduCourseFilm.coursefilmid}&paypwd=0",
	        success:function(msg){
	        	if(msg == "ok"){
	        		window.location.replace("/weixinCourse.app?method=play&userid=${userid }&coursefilmid=${eduCourseFilm.coursefilmid}");
	        	}
   			}
		});
    	<%}%>
    });
    $(".payment_time_mask .wk_buy_a").on('click',function () {
        $("#bg,.payment_time_mask").css("display", "none");
       
    });
});
function wxpay(){
	//点击支付弹出蒙板，避免重复点击
    document.getElementById("div_hidden").style.display = "block";
    
    //var buytitle = "购买《${eduCourseInfo.title }》课程微课【${eduCourseFilm.title }】";//内容太长，微信支付内容描述有现在长度
    var buytitle = "购买《${eduCourseInfo.title }》课程微课";
    
   	wx.checkJsApi({
   	    jsApiList: ['chooseWXPay'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
   	    success: function(res) {
   	    	if(res.checkResult.chooseWXPay == true){
   	    		//通过ajax获取统一支付接口返回的prepay_id、paySign
   	    	    $.ajax({
   	    	    	type:"post",
   	    	       	url:"/wxpay/weixinPay.app?method=getPrepayid",
   	    	       	data:"tradetype=5&money=${eduCourseFilm.sellprice }&coursefilmid=${eduCourseFilm.coursefilmid }&openid=${openid}&userid=${userid}&body=" + buytitle + "&timestamp=<%=jsticket.get("timestamp") %>&nonce_str=<%=jsticket.get("nonceStr") %>",
   	    	       	async: false,
   	    	       	success:function(msg){
   	    	       		//ajax返回消息后，可以去掉蒙板
   	    	       		if(msg != "0"){
   	    	       			var result = msg.split(";");
   	    	       			wx.chooseWXPay({
   	    	           		    timestamp: <%=jsticket.get("timestamp") %>, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
   	    	           		    nonceStr: '<%=jsticket.get("nonceStr") %>', // 支付签名随机串，不长于 32 位【以上两个参数和wx.config保持一致】
   	    	           		    package: 'prepay_id=' + result[0], // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
   	    	           		    signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
   	    	           		    paySign: result[1], // 支付签名
   	    	           		    success: function (res) {
   	    	           		        // 支付成功后的回调函数
   	    	           		        //alert("res:"+res);
   	    	           		        //js返回支付成功，跳转到成功页面，具体支付成功与否，可通过回调函数真正写入数据库
   	    	           		        var redirecturl = "%2fweixinCourse.app%3fmethod%3dplay%26userid%3d${userid }%26coursefilmid%3d${eduCourseFilm.coursefilmid }";//转码处理
   	    	           		     	window.location.replace("/wxpay/weixinPay.app?method=paySuccess&userid=${userid}&out_trade_no=" + result[2] + "&redirecturl=" + redirecturl);
   	    	           		    },
   	    	                    cancel: function () {
   	    	                    	document.getElementById("div_hidden").style.display = "none";
   	    	                        //alert("用户取消支付！");
   	    	                        //window.location.replace("/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=${bookcontentid }&searchtype=${searchtype }");
   	    	                    },
   	    	                    error: function (e) {
   	    	                        alert("支付失败！");
   	    	                        window.location.replace("/weixinCourse.app?method=buy&userid=${userid }&coursefilmid=${eduCourseFilm.coursefilmid }");
   	    	                    }
   	    	           		});
   	    	       		}else{
   	    	       			alert("数据异常，请稍后再试！");
   	    	       			return;
   	    	       		}
   	    	  		}
   	    		});
   	    	}else{
   	    		alert("请在手机上支付！");
   	    		 //点击支付弹出蒙板，避免重复点击
   	    	    document.getElementById("div_hidden").style.display = "none";
   	    		return;
   	    	}
   	    }
   	});
}

function autoPay(){
	<%if(eduCourseFilm.getSellprice() > 0){ %>
   	var iswxpay = '<%=iswxpay %>';
   	if(iswxpay == "1"){
   		wxpay();
   	}else{
    	$("#bg").css({
            display: "block", height: $(document).height()
        });
        $("#bg").html('');
        var $box = $('.payment_time_mask');
        $box.css({
            display: "block",
        });
        for(var i=1; i<=6; i++){
	    	document.getElementById("pwd" + i).value = "";
	    }
	    document.getElementById("hiddenpwdid").value = "1";
   	}
   	<%} %>
}

<logic:equal value="1" name="autopay">
	<%if(eduCourseFilm.getSellprice() > 0){ %>
   	var iswxpay = '<%=iswxpay %>';
   	if(iswxpay == "1"){
   		document.getElementById("div_hidden").style.display = "block";
   	}else{
    	$("#bg").css({
            display: "block", height: $(document).height()
        });
        $("#bg").html('<div style="z-index:2;auto:30% 0px;top:45%;position:absolute;left:35%;color:#ff0000;background-color:#fff;border: 1px solid #eee; border-radius: 5px;padding: 4px 10px;">加载中...</div>');
   	}
   	<%} %>
	setTimeout(autoPay,2000);
</logic:equal>
</script>
</body>
</html>