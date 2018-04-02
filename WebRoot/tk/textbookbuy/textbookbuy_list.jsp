<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.tk.bo.TkTextBookBuy" %>
<%@page import="com.wkmk.tk.bo.TkTextBookInfo" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--数字分页start-->
<script type="text/javascript" src="../../libs/js/nav/pageNumber.js"></script>
<script type="text/javascript" src="../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../libs/js/sk/comm.js"></script>
<!--数字分页end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="教材订购记录" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>教材名称：</td>
			<td><input type="text" name="textbookname" value="<bean:write name="textbookname"/>"/></td>
			<td>订购日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td>订购人姓名：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td>收件人姓名：</td>
			<td><input type="text" name="recipientname" value="<bean:write name="recipientname"/>"/></td>
			<td>发货状态：</td>
			<td>
				<%String isdelivery = (String)request.getAttribute("isdelivery"); %>
				<select name="isdelivery">
				<option value="">请选择...</option>
				<option value="0" <%if("0".equals(isdelivery)){ %>selected="selected"<%} %>>未发货</option>
				<option value="1" <%if("1".equals(isdelivery)){ %>selected="selected"<%} %>>已发货</option>
				</select>
			</td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="exportOrders()"><span class="icon_export">导出订单</span></a>
		<div class="clear"></div>
		</div>
	</div>		
	</div>	
	</div>
	<div class="clear"></div>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02" width="20">序号</th>
			<th class="ali02"><span onclick="sortHandler('username')" id="span_username">订购教材名称</span></th>
			<!-- <th class="ali02" width="50"><span onclick="sortHandler('userip')" id="span_userip">单价</span></th> -->
			<th class="ali02" width="50"><span onclick="sortHandler('amount')" id="span_amount">售价</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('price')" id="span_price">订购总数</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('createdate')" id="span_createdate">订购总价</span></th>
			<th class="ali02" width="90"><span onclick="sortHandler('quantity')" id="span_quantity">购买人姓名</span></th>
			<th class="ali02" width="70"><span onclick="sortHandler('paytype')" id="span_paytype">收件人姓名</span></th>
			<th class="ali02" width="90"><span onclick="sortHandler('state')" id="span_state">收件人电话</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('isdelivery')" id="span_isdelivery">发货状态</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('createdate')" id="span_createdate">订购日期</span></th>
			<th class="ali02" width="50">操作</th>
		</tr>
		<!--循环列出所有数据-->
		<%
			PageList pagelist = (PageList)request.getAttribute("pagelist");
			List list = pagelist.getDatalist();
			if(list != null && list.size() > 0){
			 	Object[] obj = null;
			    TkTextBookBuy bookBuy = null;
			    TkTextBookInfo bookInfo = null;
			    SysUserInfo userInfo = null;
				for(int i=0, size=list.size(); i<size; i++){
					obj = (Object[])list.get(i);
					bookBuy = (TkTextBookBuy)obj[0];
					bookInfo = (TkTextBookInfo)obj[1];
					userInfo = (SysUserInfo)obj[2];
			%>
		<tr>
			<td><input type="checkbox" name="checkid" value="<%=bookBuy.getTextbookbuyid() %>"/></td>
			<td class="ali02"><%=i+1%></td>
			<td class="ali02"><%=bookInfo.getTextbookname() %></td>
			<%-- <td class="ali02"><%=bookInfo.getPrice() %></td> --%>
			<td class="ali02"><%=bookInfo.getSellprice() %></td>
			<td class="ali02"><%=bookBuy.getTotalnum() %></td>
			<td class="ali02"><%=bookBuy.getTotalprice() %></td>
			<td class="ali02"><%=userInfo.getUsername() %></td>
			<td class="ali02"><%=bookBuy.getRecipientname() %></td>
			<td class="ali02"><%=bookBuy.getRecipientphone() %></td>
			<td class="ali02">
			<%if("0".equals(bookBuy.getIsdelivery())){ %>
				未发货
			<%}else{ %>
				 已发货
			<%} %>
			</td>
			<td class="ali02"><%=bookBuy.getCreatedate() %></td>
			<td class="ali02">
				<div class="img_edit0 hand" title="修改" onclick="editThisRecord('tkTextBookBuyAction.do','<%=bookBuy.getTextbookbuyid() %>')"></div>
			</td>
		</tr>
		<%}} %>
	</table>
</div>
<div style="height:35px;">
	<div class="float_left padding5">
		共<bean:write name="pagelist" property="totalCount"/>条记录
	</div>
	<div class="float_right padding5">
		<div class="pageNumber" total="<bean:write name="pagelist" property="totalCount"/>" pageSize="<bean:write name="pagesize"/>" page="<bean:write name="pagelist" property="curPage0"/>" showSelect="true" showInput="true" id="pageContent"></div>
	</div>
	<div class="clear"></div>
</div>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>

</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkTextBookBuyAction.do?method=list';
   		document.pageForm.submit();
	
	}
	
	//教材订购记录导出excel
	function exportOrders(){
		var id = document.getElementsByName('checkid');
	    var value = new Array();
	    for(var i = 0; i < id.length; i++){
	     if(id[i].checked)
	     value.push(id[i].value);
	    } 
		if("" == value.toString()){
			alert("至少选择一个订购记录进行导出！")
			return false;
		}
		console.log(value.toString());
		
	    document.pageForm.action = '/tkTextBookBuyAction.do?method=exportOrders&idArray='+value.toString();
		document.pageForm.submit();
	}
</script>
</body>
</html>