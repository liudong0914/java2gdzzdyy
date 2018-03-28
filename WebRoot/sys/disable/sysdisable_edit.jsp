<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.sys.bo.SysUserDisable"%>
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

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/sysUserDisableAction.do" method="post">
    <%SysUserDisable model = (SysUserDisable)request.getAttribute("model"); %>
    <%!
    private String htmlspecialchars(String str) {
	if(str == null || "".equals(str)) return "";
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="1" name="type">禁用教师</logic:equal><logic:equal value="2" name="type">禁用学生</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03"><logic:equal value="1" name="type">教师姓名</logic:equal><logic:equal value="2" name="type">学生姓名</logic:equal></td>
			<td class="ali01"><input type="text" readonly="readonly" name="username" value="<bean:write name="username"/>"></td>
		</tr>
		<tr>
			<td class="ali03">禁用开始时间：</td>
			<td><input type="text" id="sysUserDisable.starttime" name="sysUserDisable.starttime" style="width:150px;" readonly="readonly" value="<bean:write property="starttime"  name="model"/>" /></td>
		</tr>	
		<tr>
			<td class="ali03">禁用结束时间：</td>
			<td><input type="text" id="sysUserDisable.endtime" name="sysUserDisable.endtime" style="width:150px;" readonly="readonly" value="<bean:write property="endtime"  name="model"/>" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
		    <td class="ali03">禁用原因</td>
		    <td><textarea name="sysUserDisable.descript" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getDescript())%></textarea></td>
		    
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
    <input type="hidden" name="userid" value="<bean:write name="userid" />"/>
    <input type="hidden" name="complaintid" value="<bean:write name="complaintid"/>"/>
    <input type="hidden" name="type" value="<bean:write name="type"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=sysUserDisableActionForm]')){
		var objectForm = document.sysUserDisableActionForm;
		var starttime = document.getElementById("sysUserDisable.starttime").value;
	    var start = starttime.split(/[- :]/); 
	    var endtime = document.getElementById("sysUserDisable.endtime").value;
	    var end = endtime.split(/[- :]/);
	    if(end <= start){
	    	alert("禁用结束时间不能小于禁用开始时间");
	    	return false;
	    }

		//同步数据后可以直接取得textarea的value
		keditor.sync();
		objectForm.action="sysUserDisableAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="sysUserDisable.descript"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}
function backRecord(){
  	document.sysUserDisableActionForm.action="/sysUserDisableAction.do?method=list";
  	document.sysUserDisableActionForm.submit();
}
</script>
</body>
</html>