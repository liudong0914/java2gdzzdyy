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
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount" />

function postData(){
    document.pageForm.action = "/eduCourseFileAction.do?method=adminList";
  	document.pageForm.submit();
}
function search(){
	document.getElementById('pageNo').value = 1;
	postData();
}
function edit(objid){
	document.pageForm.action = "/eduCourseFileAction.do?method=adminBeforeUpdate&objid=" + objid;
  	document.pageForm.submit();
}

function dowmFile(fileid){
	document.pageForm.action = "/eduCourseFileAction.do?method=downFile&fileid=" + fileid;
  	document.pageForm.submit();
}

function convertFile(fileid){
	document.pageForm.action = "/eduCourseFileAction.do?method=adminConvertFile&fileid=" + fileid;
  	document.pageForm.submit();
}
</script>
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="资源管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>资源名称：</td>
			<td><input type="text" name="filename" value="<bean:write name="filename"/>"/></td>
			<!-- <td>资源类型：</td>
			<td>
				<select name="coursefiletype"  onchange="search()">
					<option value="">全部</option>
					<option value="1" <logic:equal value="1" name="coursefiletype">selected="selected"</logic:equal>>教案</option>
					<option value="2" <logic:equal value="2" name="coursefiletype">selected="selected"</logic:equal>>课件</option>
					<option value="3" <logic:equal value="3" name="coursefiletype">selected="selected"</logic:equal>>习题</option>
					<option value="4" <logic:equal value="4" name="coursefiletype">selected="selected"</logic:equal>>素材</option>
				</select>
			</td> -->
			<td><button type="button" onclick="search()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/eduCourseFileAction.do?method=adminBeforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="delRecord('/eduCourseFileAction.do?method=adminDelBatchRecord')"><span class="icon_delete">删除</span></a>
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
			<th class="ali02"><span onclick="sortHandler('filename')" id="span_filename">资源名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('flago')" id="span_flago">大小</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('coursefiletype')" id="span_coursefiletype">资源类型</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('flagl')" id="span_flagl">上传用户</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('status')" id="span_status">转码状态</span></th>
			<th class="ali02" width="70">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="fileid" name="model"/>"/></td>
			<td class="ali02"><bean:write name="model" property="filename"/></td>
			<td class="ali02"><bean:write name="model" property="flago"/></td>
			<td class="ali02"><bean:write name="model" property="coursefiletype"/></td>
			<td class="ali02"><bean:write name="model" property="flagl"/></td>
			<td class="ali02">
				<c:if test="${model.convertstatus == '0' }">
				待转换<a href="javascript:convertFile('<bean:write property="fileid" name="model"/>')" style="color:green;">[优先转码]</a>
				</c:if>
				<c:if test="${model.convertstatus == '1' }">
				正常
				</c:if>
				<c:if test="${model.convertstatus == '2' }">
				失败<a href="javascript:convertFile('<bean:write property="fileid" name="model"/>')" style="color:red;">[重新转码]</a>
				</c:if>
			</td>
			<td class="ali02">
				<div class="img_edit hand" title="修改" onclick="edit('<bean:write name="model" property="fileid"/>')"></div>
				<div class="img_cd_driver hand" title="下载" onclick="dowmFile('<bean:write name="model" property="fileid"/>')"></div>
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
<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
</body>
</html>