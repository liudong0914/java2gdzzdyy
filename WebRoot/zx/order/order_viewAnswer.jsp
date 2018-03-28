<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestion"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.wkmk.zx.bo.ZxHelpOrder"%>
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
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

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/zxHelpOrderAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="4">回复详情</th>
		</tr>
		<tr>
			<td class="ali03" width="95px;">完成时间：</td>
			<td class="ali01" colspan="3">
				<bean:write property="replycreatedate" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">付款状态：</td>
			<td class="ali01" colspan="3">
				<c:if test="${model.paystatus == '0'}">
					学生已付款
				</c:if>
				<c:if test="${model.paystatus == '1'}">
					已到账
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">回复内容：</td>
			<td class="ali01" colspan="3">
				<bean:write property="content" name="zxHelpAnswer"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">视频：</td>
			<td colspan="3">
				<button type="button" onclick="addFilmVideo('<bean:write name="zxHelpAnswer" property="answerid"/>')">播放视频</button>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">录音：</td>
			<td colspan="3">
				<%
				List list2 = (List)request.getAttribute("list2");
				if(list2 !=null && list2.size()>0){
				    for(int i=0;i<list2.size();i++){
				        ZxHelpFile file =(ZxHelpFile)list2.get(i);
				%>
				<audio id="myaudio" src="/upload/<%=file.getFlago() %>" controls="controls" style="margin:auto">
				</audio></br>
				<%}}else{ %>
				暂无录音
				<%} %>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">图片：</td>
			<td colspan="3" id="img">
				<%
				List list1 = (List)request.getAttribute("list1");
				if(list1 !=null && list1.size()>0){
				    for(int i=0;i<list1.size();i++){
				        ZxHelpFile file =(ZxHelpFile)list1.get(i);
				%>
					<img  src="/upload/<%=file.getThumbpath() %>" alt="<%=file.getFlagl() %>" width="200px" height="150px" onclick="javascript:window.open('/upload/<%=file.getFlago() %>');"/>
				<%}}else{ %>
				暂无图片
				<%} %>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
</html:form>
</div>
</div>
<script>
function backRecord(){
  	document.zxHelpOrderActionForm.action="/zxHelpOrderAction.do?method=list";
  	document.zxHelpOrderActionForm.submit();
}
function addFilmVideo(answerid){
	var diag = new top.Dialog();
	diag.Title = "回复视频";
	diag.URL = '/zxHelpOrderAction.do?method=videoListAnswer&flag=1&answerid='+answerid;
	diag.Width = 800;
	diag.Height = 500;
	diag.ShowMaxButton=true;
	diag.CancelEvent = function(){
		diag.close();
	};
	diag.show();
}
function autoPlay(src){
	var myAuto = document.getElementById('myaudio');
	myAuto.src = src;
	myAuto.play();
	}
</script>
</body>
</html>