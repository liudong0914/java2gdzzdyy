<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>教材详情</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-1.10.2.js" type="text/javascript"></script>
<%@ include file="/weixin/account/scan0.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>	
<div class="search" style="z-index:99;">
	<a href="javascript:history.back()">
	<div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">教材详情</p>
    </div>
</div>
<div class="search_padding"></div>
<!-----class_main-------->
<div class="testpaper_details">
	<p><img src="/upload/${textBookInfo.sketch }" style="display:block; margin:0 auto; width: 85%;"/></p>
	<p class="testpaper_details_title" style="text-align:center;">${textBookInfo.textbookname }</p>
   <%--  <p>作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;者：${textBookInfo.author }</p> --%>
    <p>出&nbsp;&nbsp;版&nbsp;&nbsp;社：${textBookInfo.press}</p>
    <p>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：<c:if test="${textBookInfo.price == '0'}">0.00</c:if><c:if test="${textBookInfo.price != '0'}"><fmt:formatNumber type="number" value="${textBookInfo.price }" pattern="#.00"/></c:if></p>
    <p>折&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;扣：<c:if test="${textBookInfo.discount == '10'}">视数量多少优惠</c:if><c:if test="${textBookInfo.discount != '10'}">${textBookInfo.discount}折</c:if></p>
    <p>售&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：<c:if test="${textBookInfo.sellprice == '0'}">0.00</c:if><c:if test="${textBookInfo.sellprice != '0'}"><fmt:formatNumber type="number" value="${textBookInfo.sellprice }" pattern="#.00"/></c:if></p>
    <p class="testpaper_details_p">范围种类：${textBookInfo.type }</p>
    <p class="testpaper_details_p">订购电话：${textBookInfo.phone}</p>
    <%-- <p>销&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：${textBookInfo.sellcount }&nbsp;笔</p> --%>
</div>
<div class="password_a payment_time_title" style="width:auto;">
    	<a href="/weixinTextBook.app?method=getTextBookBuy&userid=${userid }&textbookid=${textBookInfo.textbookid }">教材订购</a>
</div>
</body>
</html>