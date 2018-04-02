<!doctype html>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>在线充值</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
 
<body>

<!-----------------内容------------------>
<div class="withdraw_top">
	 <a href="javascript:history.back()" class="withdraw_top_a">取消</a>
     <div class="withdraw_top_font"> 
     	<p>在线充值</p>
        <p class="withdraw_top_font_p2">微信安全支付</p></p>
     </div>
</div>
<logic:present name="moneyActivity">
<div style="font-size:12px;color:red;width:93%;margin:20px auto -15px auto;">【充值活动】${moneyActivity.name }</div>
</logic:present>
<div  class="withdraw_main">
    <div class=" withdraw_main_02">
        <p>金额(元)</p>
        <input type="text" placeholder="请输入金额" name="money" id="money" value="" style="color:#000;" maxlength="4"/>
    </div>
</div>
<a href="javascript:pay()"><div class="Feedback_button">
	<p>下一步</p>
</div></a>
<div id="div_hidden" style="width:100%; height:100%; overflow:hidden; position:absolute; top:0px; z-index:9999; opacity:.5;filter:alpha(opacity=50);background:rgba(0,0,0,0.6); display:none;"></div>

<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script type="text/javascript">
function pay(){
	var money = document.getElementById("money").value;
	if(money == ""){
		alert("请填写充值金额！");
		return;
	}
	//验证是否为正数，且小数位保留2位
	var re = /^(([0-9]+[\.]?[0-9]{1,2})|[1-9])$/;
    var recheck = re.test(money);
    if(!recheck){
    	alert("充值金额填写错误！");
		return;
    }
    //前期控制，充值金额不能大于500
    var moneyInt = parseInt(money);
    if(moneyInt > 500){
    	alert("充值金额不能超过500！");
		return;
    }
    
    //点击支付弹出蒙板，避免重复点击
    document.getElementById("div_hidden").style.display = "block";
    
   	wx.checkJsApi({
   	    jsApiList: ['chooseWXPay'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
   	    success: function(res) {
   	    	if(res.checkResult.chooseWXPay == true){
   	    		//通过ajax获取统一支付接口返回的prepay_id、paySign
   	    	    $.ajax({
   	    	    	type:"post",
   	    	       	url:"/wxpay/weixinPay.app?method=getPrepayid",
   	    	       	data:"tradetype=1&money=" + money + "&openid=${openid}&userid=${userid}&body=广东省中职德育云平台在线充值&timestamp=<%=jsticket.get("timestamp") %>&nonce_str=<%=jsticket.get("nonceStr") %>&activityid=${moneyActivity.activityid }",
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
   	    	           		     	window.location.replace("/wxpay/weixinPay.app?method=paySuccess&userid=${userid}&out_trade_no=" + result[2]);
   	    	           		    },
   	    	                    cancel: function () {
   	    	                    	document.getElementById("div_hidden").style.display = "none";
   	    	                        //alert("用户取消支付！");
   	    	                        //window.location.replace("/wxpay/weixinPay.app?method=index&userid=${userid}");
   	    	                    },
   	    	                    error: function (e) {
   	    	                        alert("支付失败！");
   	    	                        window.location.replace("/wxpay/weixinPay.app?method=index&userid=${userid}");
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
<p class="withdraw_button">【充值说明】1人民币=1学币。</p>
</body>
</html>