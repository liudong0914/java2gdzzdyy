<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestion"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>确认信息</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function confirmButton(){
	//点击支付弹出蒙板，避免重复点击
    document.getElementById("div_hidden").style.display = "block";
	window.location.replace("/weixinHelp.app?method=confirmOrder&userid=${userid }&questionid=${questionid }&orderid=${orderid }");
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<div class="search">
	<a href="/weixinHelp.app?method=info&userid=${userid }&questionid=${questionid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">答案付款确认</p>
    </div>
</div>
<div class="search_padding"></div>
<%
ZxHelpQuestion question = (ZxHelpQuestion)request.getAttribute("question");
%>
<div id="container">
	<div class="container_nr">
    	<div class="conrainer_nr_font">
        	<p>1、请确定当前教师回答的内容是否和你的提问对应相符。</p>
        	<p>2、确认答案付款前可进行投诉，确认答案付款后可进行售卖。</p>
        	<p>3、售卖的答疑提问，自己也可从中获得利润，当前答疑每卖一次可获得收益：<%=question.getMoney() %>(原价)*<%=Constants.BUY_QUESTION_DISCOUNT/10 %>(折扣)*<%=Constants.SELL_QUESTION_PROPORTION %>(分成比例) = <%=question.getMoney()*(Constants.BUY_QUESTION_DISCOUNT/10)*Constants.SELL_QUESTION_PROPORTION %>(学币)</p>
        </div>
    </div>
    
	<div class="container_nr_moble">
    	<a href="javascript:confirmButton()">
        	<div class="container_nr_moble1">
    			<span>确定</span>
        	</div>
        </a>
        <a href="/weixinHelp.app?method=info&userid=${userid }&questionid=${questionid }">
        	<div class="container_nr_moble3">
    			<span>取消</span>
        	</div>
        </a>
    </div>
</div>

<div id="div_hidden" style="width:100%; height:100%; overflow:hidden; position:absolute; top:0px; z-index:999999999;background:rgba(0,0,0,0.5); display:none;"><div style="z-index:9999999999;auto:30% 0px;top:45%;position:absolute;left:35%;color:#ff0000;background-color:#fff;border: 1px solid #eee; border-radius: 5px;padding: 4px 10px;">请稍等...</div></div>
</body>
</html>