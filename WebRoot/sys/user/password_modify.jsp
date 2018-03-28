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

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/sysUserInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">修改密码</th>
		</tr>
		<logic:present name="promptinfo" scope="request">
		<tr>
			<td class="ali03">&nbsp;</td>
			<td class="ali01"><span class="star"><bean:write name="promptinfo" scope="request"/></span></td>
		</tr>
		</logic:present>
		<tr>
			<td class="ali03" width="260">原始密码：</td>
			<td class="ali01"><input type="password" class="validate[required]" name="oldpassword" id="oldpassword" value="" style="width:200px;"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">新密码：</td>
			<td class="ali01"><input type="password" class="validate[required]" name="sysUserInfo.password" id="password" value="" style="width:200px;"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">确认密码：</td>
			<td class="ali01"><input type="password" class="validate[required]" name="passwordagain" id="passwordagain" value="" style="width:200px;"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysUserInfo.userid" value='<bean:write property="userid"  name="model"/>'/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=sysUserInfoActionForm]')){
		if(document.getElementById('password').value!=document.getElementById('passwordagain').value){
		    top.Dialog.alert("两次输入的密码不一致，请重试!");
		    return false;
		}
		var objectForm = document.sysUserInfoActionForm;
	  	objectForm.action="sysUserInfoAction.do?method=modifySave";
	  	objectForm.submit();
	}
}
</script>
</body>
</html>