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
			<th class="ali02"><span>章节名称</span></th>
			<th class="ali02" width="100"><span id="span_price">定价</span></th>
			<th class="ali02" width="100"><span id="span_columntype">折扣</span></th>
			<th class="ali02" width="100"><span id="span_sellprice">售卖价格</span></th>
			<th class="ali02" width="100"><span id="span_sellcount">售卖数量</span></th>
			<th class="ali02" width="150"><span id="span_type">定价类型</span></th>
			<th class="ali02" width="60">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="priceid" value="<bean:write property="priceid" name="model"/>" </td>
			<td class="ali02"><bean:write name="title"/></td>
			<td class="ali02"><bean:write name="model" property="price"/></td>
			<td class="ali02"><bean:write name="model" property="discount"/></td>
			<td class="ali02"><bean:write name="model" property="sellprice"/></td>
			<td class="ali02"><bean:write name="model" property="sellcount"/></td>
			<td class="ali02"><java2code:value codetype="pricetype" property="type"/>
			<td>
			<div class="img_edit0 hand" title="修改" onclick="updateRecord('<bean:write name="model" property="priceid"/>','<bean:write name="bookcontentid"/>')">
			</td>
		</tr>
		</logic:iterate>
	</table>
</div>
<div style="height:35px;">
	<div class="float_left padding5">
		共<bean:write name="pagelist" property="totalCount"/>条记录
	</div>
	<div class="clear"></div>
</div>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>

<input type="hidden" value="<bean:write name="bookcontentid"/>"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkBookContentPriceAction.do?method=list';
   		document.pageForm.submit();
	}
	function updateRecord(objid,bookcontentid){
	    document.pageForm.action="/tkBookContentPriceAction.do?method=beforeUpdate&objid="+objid+"&bookcontentid="+bookcontentid;
	    document.pageForm.submit();
	}
</script>
</body>
</html>