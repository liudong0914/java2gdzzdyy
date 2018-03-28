<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Constants.PRODUCT_NAME %>-我的订单</title>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<script type="text/javascript" src="/libs/js/jquery.js"></script>
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
<script type="text/javascript">
function postData(){
    document.pageForm.action = "/courseCenter.do?method=myTrade&mark=${mark }";
  	document.pageForm.submit();
}
function search(){
	document.getElementById('pageNo').value = 1;
	postData();
}
</script>
</head>

<body>
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
<%@ include file="left.jsp" %>

<form name="pageForm" method="post">
<div class="rightCon">
	<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">我的订单</h3>
		</div>
		<div class="mainCon mainCon01">
			<div class="tab">
				<div class="acts" style="left:0px;">
					支付状态：
					<select name="state" class="ipt-text" style="width:120px;height:34px;" onchange="search()">
						 <option value="">全部</option>	
  						<option value="0" <%if("0".equals(request.getAttribute("state")))out.print(" selected ");%>>支付未完成</option>	
						<option value="1" <%if("1".equals(request.getAttribute("state")))out.print(" selected ");%>>支付异常</option>	
  						<option value="2" <%if("2".equals(request.getAttribute("state")))out.print(" selected ");%>>支付完成</option>	
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<!-- 
					支付方式：
					<select name="paytype" class="ipt-text" style="width:100px;height:34px;">
						 <option value="">请选择</option>	
		 				<option value="wxpay" <%if("wxpay".equals(request.getAttribute("paytype")))out.print(" selected ");%>>微信支付</option>	
		 				<option value="alipay" <%if("alipay".equals(request.getAttribute("paytype")))out.print(" selected ");%>>支付宝</option>	
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					 -->
					订单内容：<input type="text" class="ipt-text" name="body" value="${body }" style="width:314px;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-pop ml20" value="查询" onclick="search()">
				</div>
			</div>
		
			<table width="100%" class="table1">
				<thead>
					<tr>
						<th width="160">时间</th>
						<th width="90">订单金额</th>
						<th width="90">支付状态</th>
						<th>订单内容</th>
					</tr>
				</thead>
				
				<logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
				<tr>
					<td colspan="4">
					<div class="tableBox box02">
						<table width="100%" style="border-collapse:separate;">
							<thead>
							<tr>
								<th width="160"><bean:write property="createdate" name="model"/></th>
								<th width="90"><bean:write property="amount" name="model"/></th>
								<th width="90">
									<logic:equal value="支付未完成" name="model" property="state">
									<span style="color:#ff0000;"><bean:write name="model" property="state"/></span>
									</logic:equal>
									<logic:equal value="支付异常" name="model" property="state">
									<span style="color:#ccc;"><bean:write name="model" property="state"/></span>
									</logic:equal>
									<logic:equal value="支付完成" name="model" property="state">
									<bean:write name="model" property="state"/>
									</logic:equal>
								</th>
								<th style="text-align:left;"><bean:write property="body" name="model"/></th>
							</tr>
							</thead>
						</table>
					</div>
					</td>
				</tr>
				</logic:iterate>
					
			</table>
			<%@ include file="/skin/course/jsp/page.jsp"%>

<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
		</div>
	</div>
	</div>
</div>
</form>
</div>

<%@ include file="/skin/course01/jsp/footer.jsp"%>
</body>
</html>
