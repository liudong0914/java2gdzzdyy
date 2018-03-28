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
<div class="box3" panelTitle="微课管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>创建时间：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/eduCourseFilmAction.do?method=beforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="delRecord('/eduCourseFilmAction.do?method=adminDelBatchRecord')"><span class="icon_delete">删除</span></a>
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
			<th class="ali02"><span onclick="sortHandler('title')" id="span_title">视频名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('flago')" id="span_flago">主讲人</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('createdate')" id="span_createdate">创建时间</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('price')" id="span_price">标价</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="70">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="coursefilmid" name="model"/>"/></td>
			<td class="ali02"><bean:write name="model" property="title"/></td>
			<td class="ali02"><bean:write name="model" property="flago"/></td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali02"><bean:write name="model" property="price"/></td>
			<td class="ali02"><java2code:value codetype="status2" property="status" color="red" colorvalue="0"></java2code:value></td>
			<td class="ali02">
				<div class="img_edit hand" title="修改" onclick="adminBeforeUpdate('<bean:write name="model" property="coursefilmid"/>')"></div>
				<div class="img_cd_driver hand" title="微课视频" onclick="addFilmPix('<bean:write name="model" property="filmid"/>')"></div>
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
<input type="hidden" name="courseclassid" value="<bean:write name="courseclassid"/>"/>
<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	function postData(){
	    document.pageForm.action = '/eduCourseFilmAction.do?method=list';
   		document.pageForm.submit();
	}
	function adminBeforeUpdate(coursefilmid){
	    document.pageForm.action = '/eduCourseFilmAction.do?method=adminBeforeUpdate&objid='+coursefilmid;
   		document.pageForm.submit();
	}
	
	
	function addFilmPix(filmid){
		var diag = new top.Dialog();
		diag.Title = "微课视频";
		diag.URL = '/vwhFilmPixAction.do?method=list&flag=1&filmid='+filmid;
		diag.Width = 800;
		diag.Height = 500;
		diag.ShowMaxButton=true;
		diag.CancelEvent = function(){
			postData();
   			diag.close();
		};
		diag.show();
	}
</script>
</body>
</html>