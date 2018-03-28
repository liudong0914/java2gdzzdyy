<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>投诉教师回答</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function commitComplaint(){
	var descript = document.getElementById("descript").value;
	if(descript == ""){
		document.getElementById("msg").innerHTML = "请输入投诉内容。";
		return;
	}
	document.pageForm.action = "/weixinHelp.app?method=commitComplaint";
	document.pageForm.submit();
}
</script>
</head>

<body>	
<form name="pageForm" method="post">
<div class="search search_write">
	<a href="/weixinHelp.app?method=info&userid=${userid }&questionid=${questionid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">投诉教师回答</p>
    </div>
</div>
<div class="search_padding"></div>

<div class="Published">
	<p>投诉内容</p>
    <div class="Published_div">
    	<textarea type="text" name="descript" id="descript" class="Published_textarea" style="height:100px;" placeholder="请输入投诉详细内容描述" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}"></textarea>
    </div>
</div>
<span id="msg" style="color:red;font-size:14px;">&nbsp;</span>
<div class="password_a" style="margin-top:-20px;">
	<a href="javascript:commitComplaint()">提交</a>
</div>

<p class="password_p password_p01">
1、请不要恶意投诉，否则账户将被禁用。<br />
2、当老师对提问的回答是属于明显的恶意抢单挣取学币时，可在线投诉，受理成功后老师将受到惩罚，并退回当前作业学币值。
</p> 
<input type="hidden" name="userid" value="${userid }"/>
<input type="hidden" name="questionid" value="${questionid }"/>
<input type="hidden" name="orderid" value="${orderid }"/>
</form>
</body>
</html>