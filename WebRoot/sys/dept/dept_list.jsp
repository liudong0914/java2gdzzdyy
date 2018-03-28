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
<div class="box3" panelTitle="部门管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>部门名称：</td>
			<td><input type="text" name="deptname" value="<bean:write name="deptname"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/sysUnitDeptAction.do?method=beforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="delRecord('/sysUnitDeptAction.do?method=delBatchRecord')"><span class="icon_delete">删除</span></a>
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
			<th class="ali02"><span onclick="sortHandler('deptname')" id="span_deptname">部门名称</span></th>
			<th class="ali02" width="150"><span onclick="sortHandler('deptno')" id="span_deptno">部门编号</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('leader')" id="span_leader">部门领导</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('telephone')" id="span_telephone">部门电话</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="deptid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali01"><bean:write name="model" property="deptname"/></td>
			<td class="ali02"><bean:write name="model" property="deptno"/></td>
			<td class="ali02"><bean:write name="model" property="leader"/></td>
			<td class="ali02"><bean:write name="model" property="telephone"/></td>
			<td class="ali02">
			<div class="img_edit hand" title="修改" onclick="editThisRecord('sysUnitDeptAction.do','<bean:write name="model" property="deptid"/>')"></div>
			<div class="img_user_group hand" title="部门成员" onclick="addDeptMember('<bean:write name="model" property="deptid"/>')"></div>
			</td>
		</tr>
		</logic:iterate>
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
<input type="hidden" value="<bean:write name="parentno"/>" name="parentno"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount"/>
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/sysUnitDeptAction.do?method=list';
   		document.pageForm.submit();
	}
	
	function addDeptMember(deptid){
		var diag = new top.Dialog();
		diag.Title = "部门成员";
		diag.URL = '/sysUnitDeptMemberAction.do?method=main&deptid='+deptid;
		diag.Width = 800;
		diag.Height = 500;
		diag.ShowMaxButton=true;
		diag.CancelEvent = function(){
			document.pageForm.action = '/sysUnitDeptAction.do?method=list';
   			document.pageForm.submit();
   			diag.close();
		};
		diag.show();
	}
	
	$(function(){
	  	<logic:present name="reloadtree">
	    parent.rfrmleft.location ='/sysUnitDeptAction.do?method=tree&parentno=<bean:write name="parentno"/>';
	  	</logic:present>  
	})
</script>
</body>
</html>