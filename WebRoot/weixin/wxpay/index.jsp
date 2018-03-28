<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysUserMoney"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>交易明细</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
<style type="text/css">
img{border:0 none; display:block;}
</style>
</head>

<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">交易明细</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<div class="recharge">
	<img src="${sysUserInfo.photo }" class="recharge_img01" />
    <p>余额：${sysUserInfo.money }<span style="font-size:12px;">(学币)</span></p>
    <img src="/weixin/images/c01.png" class="recharge_img02" />
</div>
<div id="bottom">
	<div id="money_list">
	<%
	List list = (List)request.getAttribute("list");
	if(list != null && list.size() > 0){
		SysUserMoney sum = null;
		for(int i=0, size=list.size(); i<size; i++){
			sum = (SysUserMoney)list.get(i);
	%>
    <div class="recharge_main">
    	<div class="recharge_main_font">
    		<%if(sum.getChangetype().intValue() == -1){ %>
        	<p class="recharge_main_p">-</p>
        	<%}else if(sum.getChangetype().intValue() == 1){ %>
        	<p class="recharge_main_p recharge_main_p02">+</p>
        	<%} %>
            <p class="recharge_main_font_p"><%=sum.getChangemoney() %>学币</p>
            <p class="recharge_main_font_p01"><%=sum.getCreatedate() %></p>
        </div>
         <p class="recharge_main_font_p02"><%=sum.getDescript() %></p>
    </div>
    <%}}else{ %>
    <div class="recharge_main">暂无交易数据！</div>
    <%} %>
    </div>
    <div class="loading" id="loading">
		<img src="/weixin/images/loading.gif" class="loadingimg"/>加载中
	</div>
</div>

<a href="/wxpay/weixinPay.app?method=money2account&userid=${userid }"  class="Menubox01_button">立即充值</a>
<script type="text/javascript">
var num = 1;
$(document).ready(function(){
	$(document).scroll(function(){
    	if($("#loading").is(":hidden")){
      		$("#loading").show();
      	}
    	var scrolltop = $(document).scrollTop();
        var dheight = $(document).height();
        var wheight = $(window).height();
        if(scrolltop >= dheight-wheight){
    		  $.ajax({
		        type:"post",
		        url:"/wxpay/weixinPay.app?method=getAjaxUserMoney",
		        data:"pagenum=" + num + "&userid=${userid}",
		        success:function(msg){
		        	if(msg != ""){
		        		$("#money_list").append(msg);
		        	}else{
		        		$("#loading").html("数据已全部加载完!");
		        	}
     			}
 			});
    	  	num++;
      	}
	});
});
</script>
</body>
</html>