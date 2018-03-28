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
<script type="text/javascript" src="../../libs/js/nav/pageArrow.js"></script>
<script type="text/javascript" src="../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../libs/js/sk/comm.js"></script>
<!--数字分页end-->
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="产品列表" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>产品名称：</td>
			<td><input type="text" name="productname" value="<bean:write name="productname"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02" width="100"><span onclick="sortHandler('productno')" id="span_productno">产品编号</span></th>
			<th class="ali02"><span onclick="sortHandler('productname')" id="span_productname">产品名称</span></th>
			<th class="ali02" width="150"><span onclick="sortHandler('shortname')" id="span_shortname">产品简称</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('type')" id="span_type">产品类型</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('orderindex')" id="span_orderindex">排序</span></th>
			<th class="ali02" width="80">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02"><bean:write name="model" property="productno"/></td>
			<td class="ali01"><bean:write name="model" property="productname"/></td>
			<td class="ali02"><bean:write name="model" property="shortname"/></td>
			<td class="ali02"><java2code:value codetype="producttype" property="type"/></td>
			<td class="ali02"><java2code:value codetype="status2" property="status" colorvalue="0"/></td>
			<td class="ali02"><bean:write name="model" property="orderindex"/></td>
			<td class="ali02"><div class="img_layers0 hand" title="模块管理" onclick="moduleManager('<bean:write name="model" property="productid"/>')"></div></td>
		</tr>
		</logic:iterate>
	</table>
</div>
<div style="height:35px;">
	<div class="float_left padding5">
		共<bean:write name="pagelist" property="totalCount"/>条记录
	</div>
	<div class="float_right padding5">
	<div class="pageArrow" total="<bean:write name="pagelist" property="totalCount"/>" pageSize="<bean:write name="pagesize"/>" page="<bean:write name="pagelist" property="curPage0"/>" id="pagearrow"></div>
	</div>
	<div class="clear"></div>
</div>
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/sysModuleInfoAction.do?method=productList';
   		document.pageForm.submit();
	}
	
	$("#pagearrow").bind("pageChange",function(e,index){
		document.getElementById('pageNo').value = index+1;
		postData();		
	})
	
	function moduleManager(productid){
		var diag = new top.Dialog();
		diag.Title = "模块管理";
		diag.URL = "/sysModuleInfoAction.do?method=main&productid="+productid;
		diag.Width = 800;
		diag.Height = 460;
		diag.ShowMaxButton=true;
		diag.show();
	}
</script>
</body>
</html>