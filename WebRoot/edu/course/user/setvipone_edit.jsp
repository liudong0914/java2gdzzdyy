<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
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

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->

<!-- 籍贯start -->
<script type="text/javascript" src="../../libs/js/sk/PCASClass.js" charset="uft-8"></script>
<!-- 籍贯end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<form name="pageForm" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<td class="ali03">开始时间：</td>
			<td class="ali01"><input type="text" style="width: 150px;" name="startdate" value="" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/></td>
			
			<td class="ali03">结束时间：</td>
			<td class="ali01"><input type="text" style="width: 150px;" name="enddate"  value="" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td colspan="5">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" value="<bean:write name="checkid"/>" name="checkid" id="checkid"/>
</form>
</div>
</div>
<script>

function saveRecord(){
	  	document.pageForm.action="/eduCourseUserAction.do?method=updateUserVip";
	  	document.pageForm.submit();
}

function backRecord(){
  	document.pageForm.action="/eduCourseUserAction.do?method=list";
  	document.pageForm.submit();
}
</script>
</body>
</html>
