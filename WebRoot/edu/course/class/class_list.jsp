<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--数字分页start-->
<script type="text/javascript" src="../../../libs/js/nav/pageNumber.js"></script>
<script type="text/javascript" src="../../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../../libs/js/sk/comm.js"></script>
<!--数字分页end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="课程批次" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>批次名称：</td>
			<td><input type="text" name="classname" value="<bean:write name="classname"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/eduCourseClassAction.do?method=beforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="delRecord('/eduCourseClassAction.do?method=delBatchRecord')"><span class="icon_delete">删除</span></a>
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
			<th class="ali02"><span onclick="sortHandler('classname')" id="span_classname">批次名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('startdate')" id="span_startdate">开始时间</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('enddate')" id="span_enddate">结束时间</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('usercount')" id="span_usercount">学员人数</span></th>
			<th class="ali02" width="70">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="courseclassid" name="model"/>"/></td>
			<td class="ali01"><bean:write name="model" property="classname"/></td>
			<td class="ali02">
				<c:if test="${model.status == '0' }">
				待发布
				</c:if>
				<c:if test="${model.status == '1' }">
				通过
				</c:if>
				<c:if test="${model.status == '2' }">
				待审核
				</c:if>
				<c:if test="${model.status == '3' }">
				关闭
				</c:if>
			</td>
			<td class="ali02"><bean:write name="model" property="startdate"/></td>
			<td class="ali02"><bean:write name="model" property="enddate"/></td>
			<td class="ali02"><bean:write name="model" property="usercount"/></td>
			<td class="ali02">
			<div class="img_edit0 hand" title="修改" onclick="editThisRecord('eduCourseClassAction.do','<bean:write name="model" property="courseclassid"/>')"></div>
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
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	function postData(){
	    document.pageForm.action = '/eduCourseClassAction.do?method=list';
   		document.pageForm.submit();
	}
</script>
</body>
</html>