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
<div class="box3" <logic:equal value="1" name="type">panelTitle="该教师禁用记录"</logic:equal><logic:equal value="2" name="type">panelTitle="该学生禁用记录"</logic:equal> style="margin-right:0px;padding-right:0px;">
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<logic:equal value="1" name="type"><a href="javascript:;" onclick="addRecord('<bean:write name="userid"/>','1')"><span class="icon_add">新增</span></a></logic:equal>
		<logic:equal value="2" name="type"><a href="javascript:;" onclick="addRecord('<bean:write name="userid"/>','2')"><span class="icon_add">新增</span></a></logic:equal>
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
			<th class="ali02" width="80"><logic:equal value="1" name="type">教师姓名</logic:equal><logic:equal value="2" name="type">学生姓名</logic:equal></th>
			<th class="ali02" width="150">禁用开始时间</th>
			<th class="ali02" width="150">禁用结束时间</th>
			<th class="ali02">禁用说明</th>
			<th class="ali02" width="70">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="disableid" name="model"/>"/></td>
			<td class="ali02"><bean:write name="username"/></td>
			<td class="ali02"><bean:write name="model" property="starttime"/></td>
			<td class="ali02"><bean:write name="model" property="endtime"/></td>
			<td class="ali02"><bean:write name="model" property="descript"/></td>
			<td class="ali02"><div class="img_find0 edit"  title="查看" style="cursor: pointer" onclick="view('<bean:write name="model" property="disableid"/>')"></div></td>
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
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
<input type="hidden" name="complaintid" value="<bean:write name="complaintid"/>"/>
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
	    document.pageForm.action = '/sysUserDisableAction.do?method=list';
   		document.pageForm.submit();
	}
	function addRecord(userid,type){
	    document.pageForm.action = '/sysUserDisableAction.do?method=beforeAdd&userid='+userid+'&type='+type;
		document.pageForm.submit();
	}
	function view(disableid){
	    document.pageForm.action = '/sysUserDisableAction.do?method=view&disableid='+disableid;
	    document.pageForm.submit();
	}
</script>
</body>
</html>