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

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="试卷附件管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>所属分类：</td>
			<td>
				<select name="typeid">
				<option value="">--请选择--</option>
				<logic:iterate id="t" name="typelist">
					<option value="${t.typeid}" ${typeid eq t.typeid?"selected":"" }>${t.typename}</option>
				</logic:iterate>
				</select>
			</td>
			<td>附件名称：</td>
			<td><input type="text" name="filename" value="<bean:write name="filename"/>"/></td>
			<td>所属年份：</td>
			<td><input type="text" name="theyear" value="<bean:write name="theyear"/>" readonly="readonly" class="date validate[required]" dateFmt="yyyy"/></td>
			<td>所属地区：</td>
			<td><input type="text" name="area" value="<bean:write name="area"/>"/></td>
		    <td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/tkPaperFileAction.do?method=beforeAdd&subjectid=${subjectid}&typeid=${typeid}&filename=${filename}&theyear=${theyear}&area=${area}')"><span class="icon_add">上传</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/tkPaperFileAction.do?method=delBatchRecord', '您确定删除选中试卷附件?')"><span class="icon_delete">删除</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/tkPaperFileAction.do?method=disableBatchRecord&setstatus=2','确定要禁用所选中试卷附件?')"><span class="icon_no">禁用</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/tkPaperFileAction.do?method=disableBatchRecord&setstatus=1','确定要启用所选中试卷附件？')"><span class="icon_ok">启用</span></a>
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
			<th class="ali02"><span onclick="sortHandler('filename')" id="span_filename">附件名称</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('fileext')" id="span_fileext">文件类型</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('theyear')" id="span_theyear">所属年份</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('area')" id="span_area">所属地区</span></th>
			<th class="ali02" width="100">所属分类</th>
			<th class="ali02" width="70"><span onclick="sortHandler('downloads')" id="span_downloads">下载次数</span></th>
			<th class="ali02" width="70"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="fileid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali01"><span class="text_slice" style="width:360px;" title="<bean:write name="model" property="filename"/>"><bean:write name="model" property="filename"/></span></td>
			<td class="ali02"><bean:write name="model" property="fileext"/></td>
			<td class="ali02"><bean:write name="model" property="theyear"/></td>
			<td class="ali02"><bean:write name="model" property="area"/></td>
			<td class="ali02"><bean:write name="model" property="tkPaperType.typename"/></td>
			<td class="ali02"><bean:write name="model" property="downloads"/></td>
			<td class="ali02"><java2code:value codetype="status4" property="status" colorvalue="2" color="red"/></td>
			<td class="ali02">
		   <div class="img_edit hand" title="修改" onclick="editPaperFile(${model.fileid})"></div>
		   <div class="img_expand hand" title="下载" onclick="downloadPaperFile(${model.fileid})"></div>	
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
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="subjectid"/>" name="subjectid"/>
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount"/>
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkPaperFileAction.do?method=list';
   		document.pageForm.submit();
	}
	
	function editPaperFile(objid){
		document.pageForm.action = '/tkPaperFileAction.do?method=beforeUpdate&typeid=${typeid}&filename=${filename}&theyear=${theyear}&area=${area}&objid='+objid;
   		document.pageForm.submit();
	}
	function downloadPaperFile(objid){
		document.pageForm.action = '/tkPaperFileAction.do?method=downloadPaperFile&objid='+objid;
   		document.pageForm.submit();
	}
</script>
</body>
</html>