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
<div class="box3" panelTitle="微课管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>微课名称：</td>
			<td><input type="text" style="width:200px;" name="title" value="<bean:write name="title"/>"/></td>
			<td>主讲教师：</td>
			<td><input type="text" name="actor" value="<bean:write name="actor"/>"/></td>
		</tr>
		<tr>
			<td>上传用户：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="diagAddRecord('/vwhUploadFilmInfoAction.do?method=beforeUpload')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/vwhFilmInfoAction.do?method=delBatchRecord', '您确定删除选中微课并存入回收站中?')"><span class="icon_delete">删除</span></a>
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
			<th class="ali02"><span onclick="sortHandler('title')" id="span_title">微课名称</span></th>
			<th class="ali02" width="70"><span onclick="sortHandler('actor')" id="span_actor">主讲教师</span></th>
			<th class="ali02" width="50">上传用户</th>
			<th class="ali02" width="120"><span onclick="sortHandler('createdate')" id="span_createdate">上传时间</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="filmid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali01"><span class="text_slice" style="width:230px;" title="<bean:write name="model" property="title"/>"><bean:write name="model" property="title"/></span></td>
			<td class="ali02"><bean:write name="model" property="actor"/></td>
			<td class="ali02"><bean:write name="model" property="sysUserInfo.username"/></td>
			<td class="ali02"><java2page:write name="model" property="createdate"/></td>
			<td class="ali02">
			<div class="img_edit hand" title="修改" onclick="editThisRecord('vwhFilmInfoAction.do','<bean:write name="model" property="filmid"/>')"></div>
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
<input type="hidden" value="<bean:write name="searchSubjectid"/>" name="searchSubjectid"/>
<input type="hidden" value="<bean:write name="searchGradeid"/>" name="searchGradeid"/>
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
	    document.pageForm.action = '/vwhFilmInfoAction.do?method=list';
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
	
	function diagAddRecord(url){
		var diag = new top.Dialog();
		diag.Title = "添加微课";
		diag.URL = url;
		diag.Width = 850;
		diag.Height = 500;
		diag.ShowMaxButton=true;
		diag.CancelEvent = function(){
   			diag.close();
		};
		diag.show();
	}
</script>
</body>
</html>