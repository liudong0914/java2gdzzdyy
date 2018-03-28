<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.util.search.PageList"%>
<%@page import="com.util.file.FileUtil"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmPix"%>
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
<div class="box3" panelTitle="转码视频" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>视频名称：</td>
			<td><input type="text" style="width:200px;" name="name" value="<bean:write name="name"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<logic:equal value="0" name="convertstatus">
		<a href="javascript:;" onclick="batchRecord('/vwhFilmPixConvertAction.do?method=convertBatchRecord', '您确定优先转换选中视频?')"><span class="icon_office">优先转换</span></a>
		<div class="box_tool_line"></div>
		</logic:equal>
		<logic:equal value="2" name="convertstatus">
		<a href="javascript:;" onclick="batchRecord('/vwhFilmPixConvertAction.do?method=convertBatchRecord', '您确定重新转换选中视频?')"><span class="icon_refresh">重新转换</span></a>
		<div class="box_tool_line"></div>
		</logic:equal>
		<a href="javascript:;" onclick="delRecord('/vwhFilmPixConvertAction.do?method=delBatchRecord')"><span class="icon_delete">删除</span></a>
		<span style="float:right;">
		<a href="javascript:setTab('0')" class="convert_tab" <logic:equal value="0" name="convertstatus">style="background-color:#afd0e7;font-weight:bold;"</logic:equal>>等待转换</a><a href="javascript:setTab('2')" class="convert_tab" <logic:equal value="2" name="convertstatus">style="background-color:#afd0e7;font-weight:bold;"</logic:equal>>转换失败</a>
		</span>
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
			<th class="ali02"><span onclick="sortHandler('name')" id="span_name">视频名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('filesize')" id="span_filesize">视频大小</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('fileext')" id="span_fileext">视频类型</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('createdate')" id="span_createdate">上传时间</span></th>
			<logic:equal value="0" name="convertstatus"><th class="ali02" width="80">视频处理</th></logic:equal>
		</tr>
		<!--循环列出所有数据-->
	    <%
		PageList pagelist = (PageList)request.getAttribute("pagelist");
		List datalist = pagelist.getDatalist();
		VwhFilmPix pix = null;
		for(int i=0, size=datalist.size(); i<size; i++){
			pix = (VwhFilmPix)datalist.get(i);
		%>
		<tr>
			<td><input type="checkbox" name="checkid" value="<%=pix.getPixid() %>"/></td>
			<td class="ali01"><span class="text_slice" style="width:445px;" title="<%=pix.getName() %>"><%=pix.getName() %></span></td>
			<td class="ali02"><%=FileUtil.getFileSizeName(pix.getFilesize(), 2) %></td>
			<td class="ali02"><%=pix.getFileext() %></td>
			<td class="ali02"><%=pix.getCreatedate() %></td>
			<logic:equal value="0" name="convertstatus">
			<td class="ali02"><%if("1".equals(pix.getNotifystatus())){ %>转码中<%}else{ %><font color="red">未提交转码</font><%} %></td>
			</logic:equal>
		</tr>
		<%} %>
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
<input type="hidden" value="<bean:write name="convertstatus"/>" name="convertstatus" id="convertstatus"/>
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
	    document.pageForm.action = '/vwhFilmPixConvertAction.do?method=list';
   		document.pageForm.submit();
	}
	
	function setTab(convertstatus){
		document.getElementById('convertstatus').value = convertstatus;
		postData();
	}
</script>
</body>
</html>