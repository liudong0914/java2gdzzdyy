<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmPix"%>
<%@page import="com.util.file.FileUtil"%>
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
<div class="box3" panelTitle="微课视频" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>视频名称：</td>
			<td><input type="text" name="name" value="<bean:write name="name"/>"/></td>
			<td>转码状态：</td>
			<td><java2code:option name="convertstatus" codetype="convertstatus" valuename="convertstatus" selectall="3"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div>
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02"><span onclick="sortHandler('name')" id="span_name">视频名称</span></th>
			<th class="ali02" width="70">缩略图</th>
			<th class="ali02" width="70"><span onclick="sortHandler('filesize')" id="span_filesize">视频大小</span></th>
			<th class="ali02" width="70"><span onclick="sortHandler('convertstatus')" id="span_convertstatus">转换状态</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('orderindex')" id="span_orderindex">排序</span></th>
			<th class="ali02" width="70"><span onclick="sortHandler('updateflag')" id="span_updateflag">关联状态</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
		<%
		PageList pagelist = (PageList)request.getAttribute("pagelist");
		List dataList = pagelist.getDatalist();
		VwhFilmPix pix = null;
		for(int i=0, size=dataList.size(); i<size; i++){
			pix = (VwhFilmPix)dataList.get(i);
		%>
		<tr>
			<td><input type="checkbox" name="checkid" value="<%=pix.getPixid() %>"/></td>
			<td class="ali01"><span class="text_slice" style="width:320px;" title="<%=pix.getName() %>"><%=pix.getName() %></span></td>
			<td class="ali02"><img src="/upload/<%=pix.getImgpath() %>" width="70" height="37"/></td>
			<td class="ali02"><%=FileUtil.getFileSizeName(pix.getFilesize(), 2) %></td>
			<td class="ali02">
			<%if("1".equals(pix.getConvertstatus())){ %>
			转码完成
			<%}else if("2".equals(pix.getConvertstatus())){ %>
			<font color="red">转码失败</font>
			<%}else{ %>
			<font color="blue">等待转码</font>
			<%} %>
			</td>
			<td class="ali02"><%=pix.getOrderindex() %></td>
			<td class="ali02">
			<%if("0".equals(pix.getUpdateflag())){ %><font color="green">已审核</font><%} %>
			<%if("1".equals(pix.getUpdateflag())){ %><font color="red">新上传</font><%} %>
			<%if("2".equals(pix.getUpdateflag())){ %><font color="blue">有修改</font><%} %>
			</td>
			<td class="ali02">
			<%if("1".equals(pix.getConvertstatus())){ %>
			<div class="img_video hand" title="预览" onclick="preview('<%=pix.getPixid() %>')"></div>
			<%}else{ %>
			<div class="img_page hand" title="待转换成功才能预览" style="filter:gray;"></div>
			<%} %>
			</td>
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
<input type="hidden" value="<bean:write name="filmid"/>" name="filmid"/>
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
	    document.pageForm.action = '/tkBookContentFilmAuditionAction.do?method=filmlist';
   		document.pageForm.submit();
	}
	
	function preview(pixid){
		var diag = new top.Dialog();
		diag.Title = "视频预览";
		diag.URL = '/vwhFilmPixAction.do?method=preview&pixid='+pixid;
		diag.Width = 620;
		diag.Height = 420;
		diag.ShowMaxButton=false;
		diag.show();
	}
</script>
</body>
</html>