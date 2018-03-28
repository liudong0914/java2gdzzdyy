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
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/libs/js/sk/comm.js"></script>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>
<body>
<form name="pageForm" method="post">
<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">微课视频</h3>
		</div>
		<div class="mainCon mainCon01">
			<table width="100%" class="table">
				<thead>
					<tr>
						<th width="120">视频大小</th>
						<th width="120">转换状态</th>
						<th width="80">预览</th>
					</tr>
				</thead>
				<tbody>
				<%
				PageList pagelist = (PageList)request.getAttribute("pagelist");
				List dataList = pagelist.getDatalist();
				VwhFilmPix pix = null;
				for(int i=0, size=dataList.size(); i<size; i++){
					pix = (VwhFilmPix)dataList.get(i);
				%>
					<tr>
						<td colspan="6">
						<div class="tableBox box02">
							<table width="100%" style="border-collapse:separate;">
								<thead>
								<tr>
									<th width="120"><%=FileUtil.getFileSizeName(pix.getFilesize(), 2) %></th>
									<th width="120">
										<%if("1".equals(pix.getConvertstatus())){ %>
										转码完成
										<%}else if("2".equals(pix.getConvertstatus())){ %>
										<font color="red">转码失败</font>
										<%}else{ %>
										<font color="blue">等待转码</font>
										<%} %>
									</th>
									<th width="80">
										<%if("1".equals(pix.getConvertstatus())){ %>
										<a class="alink" href="javascript:;" onclick="preview('<%=pix.getPixid() %>')">预览</a>
										<%}else{ %>
										无法预览
										<%} %>
									</th>
								</tr>
								</thead>
							</table>
						</div>
						</td>
					</tr>
				<%} %>	
				</tbody>
			</table>
		</div>
	</div>
</div>
</form>
<script type="text/javascript">
	function preview(pixid){
		var diag = new top.Dialog();
		diag.Title = "视频预览";
		diag.URL = '/eduCourseFilmAction.do?method=preview&pixid='+pixid;
		diag.Width = 620;
		diag.Height = 420;
		diag.ShowMaxButton=false;
		diag.show();
	}
</script>
</body>
</html>