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

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!-- 进度条 -->
<script language="javaScript" type="text/javascript" src="/libs/js/msg.js"></script>
<link href="/libs/css/msg/msg.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/sysUserInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">批量生成用户</th>
		</tr>
		<tr>
			<td class="ali03">用户名前缀：</td>
			<td class="ali01">
			<input type="text" name="start" id="start" value=""/>(请输入a~z之间的英文字母)
			</td>
		</tr>
		<tr>
			<td class="ali03">生成数量：</td>
			<td class="ali01">
			<input type="text" name="num" id="num" value=""/>
			</td>
		</tr>		
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>
</html:form>
</div>
</div>
<script>
function saveRecord(){
		var start =document.getElementById("start").value;
		start.replace(/[^\d.]/g, "");
		if(start == ""){
			　alert("用户名前缀不能为空！");
			　　start.focus();
			　　return false;
		}
		//var type="(^[1-9]{1}[0-9]*$)|(^[0-9]*\.[0-9]{2}$)"; 
		//var type="^\d+(\.\d{1,2})?$";
		//var r=new RegExp(type); 
		var flag=/^[a-zA-Z]+$/.test(start);
		if(!flag){
		　　alert("用户前缀请输入英文字母！");
		　　start.focus();
		　　return false;
		}
		
		var num =document.getElementById("num").value;
		num.replace(/[^\d.]/g, "");
		if(num == ""){
			　alert("生成数量不能为空！");
			　　num.focus();
			　　return false;
		}
		var numtype="^[0-9]*[1-9][0-9]*$"; 
		var r1=new RegExp(numtype); 
		var flag1=r1.test(num);
		if(!flag1){
		　　alert("生成数量应为大于0 的正整数！");
		　　num.focus();
		　　return false;
		}
		//if(num < 1 || num >9000){
		//	　alert("生成数量范围在1~9000！");
		//	　　num.focus();
		//	　　return false;
		//}
		Showbo.Msg.wait('正在生成，请等待...','提示信息');
		var objectForm = document.sysUserInfoActionForm;
	  	objectForm.action="/sysUserInfoAction.do?method=saveAddUserInfo";
	  	objectForm.submit();
}

</script>
</body>
</html>