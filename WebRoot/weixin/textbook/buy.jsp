<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkTextBookInfo"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>订购教材</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-1.10.2.js" type="text/javascript"></script>
<script>
</script>
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
        <p class="wk_buy_p3" id="a">¥<span style="font-size:12px;"></span></p> 	
        <p class="wk_buy_p4">余额：<fmt:formatNumber type="number" value="${sysUserInfo.money }" pattern="#.00"/><span style="font-size:14px;text-align:right;float:right;padding-right:10px;color:#0000ff;text-decoration:underline;" onclick="wxpay()">微信在线支付</span></p>
        <%
        String iswxpay = "0";
        String paypassword = (String)request.getAttribute("paypassword");
        TkTextBookInfo textBookInfo = (TkTextBookInfo)request.getAttribute("textBookInfo");
        SysUserInfo sysUserInfo = (SysUserInfo)request.getAttribute("sysUserInfo");
        if(sysUserInfo.getMoney() < textBookInfo.getSellprice() || "0".equals(sysUserInfo.getPaypassword())){
        	if(sysUserInfo.getMoney() <  textBookInfo.getSellprice()){
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
        <p style="font-size:12px;color:#00aa00;padding-left:10px;padding-top:2px;text-align:left;float:left;">支付密码当天输错3次，将在第2天解锁。</p>
        <%}else{ %>
        <p style="font-size:14px;color:#ff0000;padding-left:10px;padding-top:2px;text-align:left;float:left;">为了您的支付安全，当天支付密码已输错3次，支付功能已被锁定，将在明天凌晨解锁。</p>
        <%}} %>
        <input type="hidden" name="hiddenpwdid" id="hiddenpwdid" value="1"/>
    </div>
    <div class="pay" <%if(sysUserInfo.getMoney() <  textBookInfo.getSellprice() || "0".equals(sysUserInfo.getPaypassword()) || "0".equals(paypassword)){ %>style="display:none;"<%} %>>
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
	<a href="javascript:history.back();"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_wk">
		<a class="search_wk_a" style="margin-left:50px;">填写订购单</a>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<div class="wk">
	<img src="/upload/${textBookInfo.sketch }" class="wk_img" style="width: 65% !important;"/>
	<p class="wk_p">${textBookInfo.textbookname}</p>
    <div  class="wk_money">
    	<%if(textBookInfo.getSellprice() > 0){ %>
    	<p class="wk_money_p01">¥</p>
        <p class="wk_money_p"><fmt:formatNumber type="number" value="${textBookInfo.sellprice }" pattern="#.00"/><span style="font-size:12px;"></span></p>
        <%}else{ %>
        <p class="wk_money_p">免费</p>
        <%} %>
    </div>
    <%-- <div class="wk_font">
    	<span>折扣：<c:if test="${textBookInfo.discount == '10'}">无</c:if><c:if test="${textBookInfo.discount != '10'}">${textBookInfo.discount}折</c:if></span>
        <span class="wk_font_span" >定价： <strike>${textBookInfo.price }(学币)</strike></span>
    	<span class="wk_font_span">销量：${textBookInfo.sellcount }笔</span>
    </div> --%>
</div>

<!-----------------订单填写内容------------------>
<div class="personal_bj1">
    <span class="personal_bj1_title">订&nbsp;购&nbsp;数&nbsp;量：</span>  
    <input id="totalnum" type="text" placeholder="请输入订购数量" class="personal_bj_1_p" onchange="changeMoney()"></input>    
</div>
<div class="personal_bj1">
    <span class="personal_bj1_a01">总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：</span> 
    <input type="hidden" id="money" value=""/>  
    <span id="moneyspan" class="personal_bj1_a">¥</span> 
</div>
<div class="personal_bj1">
    <span>收件人姓名：</span>  
    <input id="recipientname" type="text" placeholder="请输入姓名" class="personal_bj_1_p"></input>    
</div>
<div class="personal_bj1">
    <span class="personal_bj1_title">收件人电话：</span>  
    <input id="recipientphone" type="text" placeholder="请输入联系电话" class="personal_bj_1_p"></input>    
</div>
<div class="Published">
	<p class="personal_bj1_p">收件人地址：</p>
    <div class="Published_div">
    	<textarea id="recipientaddress" type="text" class="Published_textarea" style="height:60px;" placeholder="请输入地址" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}"></textarea>
    </div>
</div>
<p class="password_p password_p01">
温馨提示：收件人姓名、收件人电话、收件人地址这三个信息请填写真实信息，不然会造成教材无法准确的送达！
</p>

<div class="password_a" style="width:auto;">   
	<a href="javascript:submitOrder()">立即订购</a>
</div>

<div id="div_hidden" style="width:100%; height:100%; overflow:hidden; position:absolute; top:0px; z-index:999999999;background:rgba(0,0,0,0.5); display:none;"><div style="z-index:9999999999;auto:30% 0px;top:45%;position:absolute;left:35%;color:#ff0000;background-color:#fff;border: 1px solid #eee; border-radius: 5px;padding: 4px 10px;">处理中...</div></div>

<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script>
$(function () {
    $(".payment_time_mask .wk_buy_a").on('click',function () {
        $("#bg,.payment_time_mask").css("display", "none");
       
    });
});
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
function changeMoney(){
	var sellprice =${textBookInfo.sellprice};//售价
	var totalnum = document.getElementById("totalnum").value;//数量
	if(/^[0-9]+$/.test(totalnum)){
		var money = sellprice*totalnum;//总价
		document.getElementById("moneyspan").innerText="¥"+money.toFixed(2);
		document.getElementById("money").value=money.toFixed(2);
		document.getElementById("a").innerText="¥"+money.toFixed(2);
	}else{
		alert("数量请输入正整数！");
		document.getElementById("totalnum").value="";
		document.getElementById("totalnum").focus();
	};
}
function submitOrder(){
	var state=0;
	var isMob=/^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
	var isPhone=/(^0?[1][358][0-9]{9}$)/;
	//获取总价
	var money = document.getElementById("money").value;
	//获取总数
	var totalnum = document.getElementById("totalnum").value;
	if(totalnum ==""){
		alert("订购数量不能为空！");
		state=1;
	}
	//获取收件人姓名
	var recipientname = document.getElementById("recipientname").value;
	var recipientnames = recipientname.replace(/(^\s*)|(\s*$)/g, "");
	if(recipientnames ==""){
		alert("收件人姓名不能为空！");
		state=1;
	}
	//获取收件人电话
	var recipientphone = document.getElementById("recipientphone").value;
	if(recipientphone ==""){
		alert("收件人电话不能为空！");
		state=1;
	}else if(!(isMob.test(recipientphone) || isPhone.test(recipientphone))){
		alert("收件人电话请输入有效的电话号码或者手机号,如:010-29292929/15012345678！");
		state=1;
	}
	//获取收件人地址
	var recipientaddress = document.getElementById("recipientaddress").value;
	var recipientaddresss = recipientaddress.replace(/(^\s*)|(\s*$)/g, "");
	if(recipientaddresss ==""){
		alert("收件人地址不能为空！");
		state=1;
	}
	if(state==0){
		orderPayOne();
	}
}

function orderPayOne(){
	<%if(textBookInfo.getSellprice() > 0){ %>
		    	var iswxpay = '<%=iswxpay %>';
		    	if(iswxpay == "1"){
		    		wxpay();
		    	}else{
			    	$("#bg").css({
			            display: "block", height: $(document).height()
			        });
			        $(window).scrollTop(0);
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
    	//获取总价
		var money = document.getElementById("money").value;
		//获取总数
		var totalnum = document.getElementById("totalnum").value;
		//获取收件人姓名
		var recipientname = document.getElementById("recipientname").value;
		//获取收件人电话
		var recipientphone = document.getElementById("recipientphone").value;
		//获取收件人地址
		var recipientaddress = document.getElementById("recipientaddress").value;
    	//点击支付弹出蒙板，避免重复点击
    	document.getElementById("div_hidden").style.display = "block";
    	$.ajax({
	        type:"post",
	        url:"/weixinTextBook.app?method=pay",
	        data:"userid=${userid}&textbookid=${textBookInfo.textbookid }&paypwd=0&money="+money+"&totalnum="+totalnum+"&recipientname="+recipientname+"&recipientphone="+recipientphone+"&recipientaddress="+recipientaddress,
	        success:function(msg){
	        	if(msg == "ok"){
	        		window.location.replace("/weixinTextBook.app?method=paySuccess&userid=${userid }");
	        	}
   			}
		});
    	<%}%>
}
function pay(){
		//获取总价
		var money = document.getElementById("money").value;
		//获取总数
		var totalnum = document.getElementById("totalnum").value;
		//获取收件人姓名
		var recipientname = document.getElementById("recipientname").value;
		//获取收件人电话
		var recipientphone = document.getElementById("recipientphone").value;
		//获取收件人地址
		var recipientaddress = document.getElementById("recipientaddress").value;
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
		        url:"/weixinTextBook.app?method=pay",
		        data:"userid=${userid}&textbookid=${textBookInfo.textbookid}&paypwd=" + paypwd+"&money="+money+"&totalnum="+totalnum+"&recipientname="+recipientname+"&recipientphone="+recipientphone+"&recipientaddress="+recipientaddress,
		        success:function(msg){
		        	if(msg == "ok"){
		        		//订购成功页面
		        		window.location.replace("/weixinTextBook.app?method=paySuccess&userid=${userid }");
		        	}else{
		        		document.getElementById("pwdtips").innerHTML = "密码输入错误" + msg + "次！";
		        		var errcount = parseInt(msg);
		        		if(errcount < 3){
			        		for(var i=1; i<=6; i++){
						    	document.getElementById("pwd" + i).value = "";
						    }
						    document.getElementById("hiddenpwdid").value = "1";
		        		}else{
		        			window.location.replace("/weixinTextBook.app?method=getTextBookBuy&userid=${userid }&textbookid=${textBookInfo.textbookid }");
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
	var redirecturl = "%2fweixinTextBook.app%3fmethod%3dgetTextBookBuy%26userid%3d${userid }%26textbookid%3d${textBookInfo.textbookid }";//转码处理
	window.location.href = "/weixinAccountIndex.app?method=beforeUpdatePwd&userid=${userid}&flag=1&redirecturl=" + redirecturl;
}
function wxpay(){
		//获取总价
		var money = document.getElementById("money").value;
		//获取总数
		var totalnum = document.getElementById("totalnum").value;
		//获取收件人姓名
		var recipientname = document.getElementById("recipientname").value;
		//获取收件人电话
		var recipientphone = document.getElementById("recipientphone").value;
		//获取收件人地址
		var recipientaddress = document.getElementById("recipientaddress").value;
		//点击支付弹出蒙板，避免重复点击
	    document.getElementById("div_hidden").style.display = "block";
	    
	   var buytitle = "订购教材《${textBookInfo.textbookname }》";
	   	wx.checkJsApi({
	   	    jsApiList: ['chooseWXPay'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
	   	    success: function(res) {
	   	    	if(res.checkResult.chooseWXPay == true){
	   	    		//通过ajax获取统一支付接口返回的prepay_id、paySign
	   	    	    $.ajax({
	   	    	    	type:"post",
	   	    	       	url:"/wxpay/weixinPay.app?method=getPrepayid",
	   	    	       	data:"tradetype=6&money="+money+"&totalnum="+totalnum+"&recipientname="+recipientname+"&recipientphone="+recipientphone+"&recipientaddress="+recipientaddress+"&textbookid=${textBookInfo.textbookid }&openid=${openid}&userid=${userid}&body=" + buytitle + "&timestamp=<%=jsticket.get("timestamp") %>&nonce_str=<%=jsticket.get("nonceStr") %>",
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
	   	    	           		        ///weixinTextBook.app?method=paySuccess&userid=${userid }
	   	    	           		        var redirecturl = "%2fweixinTextBook.app%3fmethod%3dpaySuccess%26userid%3d${userid }";//转码处理
	   	    	           		     	window.location.replace("/wxpay/weixinPay.app?method=paySuccess&userid=${userid}&out_trade_no=" + result[2] + "&redirecturl=" + redirecturl);
	   	    	           		    },
	   	    	                    cancel: function () {
	   	    	                    	document.getElementById("div_hidden").style.display = "none";
	   	    	                        //alert("用户取消支付！");
	   	    	                        //window.location.replace("/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=${bookcontentid }&searchtype=${searchtype }");
	   	    	                    },
	   	    	                    error: function (e) {
	   	    	                        alert("支付失败！");
	   	    	                        window.location.replace("/weixinTextBook.app?method=getTextBookBuy&userid=${userid }&textbookid=${textBookInfo.textbookid }");
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
</script>
</body>
</html>