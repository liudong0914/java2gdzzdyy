<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
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
			<div class="tab">
				<div class="acts" style="left:0px;">
					视频名称：<input type="text" class="ipt-text" name="name" value="${name }" style="width:200px;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					转码状态：
					<select name="convertstatus" class="ipt-text" style="width:110px;height:34px;" onchange="search()">
						<option value="">全部</option>
						<option value="0" <logic:equal value="0" name="convertstatus">selected="selected"</logic:equal>>等待转换</option>
						<option value="1" <logic:equal value="1" name="convertstatus">selected="selected"</logic:equal>>转换成功</option>
						<option value="2" <logic:equal value="2" name="convertstatus">selected="selected"</logic:equal>>转换失败</option>
					</select>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-pop ml20" value="查询" onclick="search()">
				</div>
				<logic:notEqual value="1" name="eduCourseInfo" property="status">
				<%
				String isAuhtor = (String)session.getAttribute("isAuhtor");
				Map moduleidMap = (Map)session.getAttribute("moduleidMap");
				String moduleidType = (String)moduleidMap.get("3");
				if("1".equals(isAuhtor) || "2".equals(moduleidType)){
				%>
				<div class="acts">
					<%
					PageList pagelist = (PageList)request.getAttribute("pagelist");
					List dataList = pagelist.getDatalist();
					if(dataList == null || dataList.size()<=0){
					%>
					<input class="btn btn-pop ml20" value="新增" onclick="addRecord('/eduCourseFilmAction.do?method=beforeAddVideo')" style="display:inline-block;" type="button">
					<%} %>
					<input class="btn btn-pop ml20" value="删除" onclick="delRecord('/eduCourseFilmAction.do?method=delBatchRecordVideo')" type="button">
					<input class="btn btn-pop ml20" value="设为封面" onclick="setPhoto()" type="button">
				</div>
				<%} %>
				</logic:notEqual>
			</div>
		
			<table width="100%" class="table">
				<thead>
					<tr>
						<th width="45">
						<input type="checkbox" onclick="setState(this.checked)">
						</th>
						<th>视频名称</th>
						<th width="100">缩略图</th>
						<th width="100">视频大小</th>
						<th width="100">转换状态</th>
						<th width="80">操作</th>
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
									<th width="45">
										<input class="checkShow msgCheck" type="checkbox" name="checkid" value="<%=pix.getPixid() %>"/>
									</th>
									<th><%=pix.getName() %></th>
									<th width="100"><img src="/upload/<%=pix.getImgpath() %>" width="70" height="37"/></th>
									<th width="100"><%=FileUtil.getFileSizeName(pix.getFilesize(), 2) %></th>
									<th width="100">
										<%if("1".equals(pix.getConvertstatus())){ %>
										转码完成
										<%}else if("2".equals(pix.getConvertstatus())){ %>
										<font color="red">转码失败</font>
										<%}else{ %>
										<font color="blue">等待转码</font>
										<%} %>
									</th>
									<th width="80">
										<a class="alink" href="javascript:;" onclick="beforeUpdateVideo('<%=pix.getPixid() %>')">修改</a>
										<%if("1".equals(pix.getConvertstatus())){ %>
										<a class="alink" href="javascript:;" onclick="preview('<%=pix.getPixid() %>')">预览</a>
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
<%@ include file="/skin/course/jsp/page.jsp"%>

<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
<input type="hidden" value="<bean:write name="filmid"/>" name="filmid"/>
<input type="hidden" value="<bean:write name="flag"/>" name="flag"/>
<input type="hidden" value="<bean:write name="mark"/>" name="mark" id="mark"/>
		</div>
	</div>
</div>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount"/>
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/eduCourseFilmAction.do?method=videoList';
   		document.pageForm.submit();
	}
	function search(){
		document.getElementById('pageNo').value = 1;
		postData();
	}
	function beforeUpdateVideo(pixid){
		document.pageForm.action = '/eduCourseFilmAction.do?method=beforeUpdateVideo&objid='+pixid;
   		document.pageForm.submit();
	}
	
	function preview(pixid){
		var diag = new top.Dialog();
		diag.Title = "视频预览";
		diag.URL = '/eduCourseFilmAction.do?method=preview&pixid='+pixid;
		diag.Width = 620;
		diag.Height = 420;
		diag.ShowMaxButton=false;
		diag.show();
	}
	
	function setPhoto(url){
		if(num>0){
	 		var str="确定把此缩略图设为封面？";
	 		var checkids="";
	 		if (num>1){
	 			for(i=0;i<num;i++){
	   				if (document.pageForm.checkid[i].checked==true){
	      				checkids=checkids+document.pageForm.checkid[i].value+",";
	   				}
	 			}
			}else{
	   			if (document.pageForm.checkid.checked==true){
					checkids=document.pageForm.checkid.value;
	   			}
	 		}
			if (checkids=="") {
	     		top.Dialog.alert("您还没有选择要操作的记录呢!")
			}else if(checkids.indexOf(",") != checkids.lastIndexOf(",")){
				top.Dialog.alert("设置封面只能选择一张图片!")
			}else{
	 			top.Dialog.confirm(str,function(){
					document.pageForm.action='/eduCourseFilmAction.do?method=setPhoto';
	   				document.pageForm.submit();
				});
			}
		}else{
	  		top.Dialog.alert("未找到可操作记录!")
		}
	}
</script>
</body>
</html>