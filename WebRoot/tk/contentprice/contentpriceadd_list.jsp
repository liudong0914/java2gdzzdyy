<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
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
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="价格列表" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>价格列表</td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_title">章节名称</span></th>
			<th class="ali02" width="100">定价</th>
			<th class="ali02" width="100">折扣</th>
			<th class="ali02" width="100">售卖价格</th>
			<th class="ali02" width="100">售卖数量</th>
			<th class="ali02" width="150">定价类型</th>
			<th class="ali02" width="60">操作</th>
		</tr>
		<tr>
			<td><input type="checkbox" > </td>
			<td class="ali01"><bean:write name="title"/></td>
			<td class="ali02"><bean:write name="price"/></td>
			<td class="ali02"><bean:write name="discount"/></td>
			<td class="ali02"><bean:write name="sellprice"/></td>
			<td class="ali02"><bean:write name="sellcount"/></td>
			<td class="ali02"><bean:write name="type1"/></td>
			<td>
			<div class="img_edit0 hand" title="修改" onclick="addRecord('<bean:write name="bookcontentid"/>','<bean:write name="type1"/>')"
			</td>
		</tr>
		<tr>
			<td><input type="checkbox" > </td>
			<td class="ali01"><bean:write name="title"/></td>
			<td class="ali02"><bean:write name="price"/></td>
			<td class="ali02"><bean:write name="discount"/></td>
			<td class="ali02"><bean:write name="sellprice"/></td>
			<td class="ali02"><bean:write name="sellcount"/></td>
			<td class="ali02"><bean:write name="type2"/></td>
			<td>
			<div class="img_edit0 hand" title="修改" onclick="addRecord('<bean:write name="bookcontentid"/>','<bean:write name="type2"/>')"
			</td>
		</tr>
	</table>
</div>
<div style="height:35px;">
	<div class="float_left padding5">
		共2条记录
	</div>
</div>


<input type="hidden" value="<bean:write name="bookcontentid"/>"/>
</form>
<script type="text/javascript">
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkBookContentPriceAction.do?method=list';
   		document.pageForm.submit();
	}
	function addRecord(bookcontentid,type){
	    document.pageForm.action="/tkBookContentPriceAction.do?method=beforeAdd&bookcontentid="+bookcontentid+"&type="+type;
	    document.pageForm.submit();
	}
</script>
</body>
</html>