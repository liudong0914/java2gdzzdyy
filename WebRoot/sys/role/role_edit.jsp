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
<html:form action="/sysRoleInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增角色</logic:equal><logic:equal value="updateSave" name="act">修改角色</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">角色编号：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="sysRoleInfo.roleno" value="<bean:write property="roleno" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">角色名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="sysRoleInfo.rolename" value="<bean:write property="rolename" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">角色状态：</td>
			<td class="ali01"><java2code:option name="sysRoleInfo.status" codetype="status2" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysRoleInfo.roleid" value="<bean:write property="roleid" name="model"/>"/>
<input type="hidden" name="sysRoleInfo.unitid" value="<bean:write property="unitid" name="model"/>"/>

<input type="hidden" name="rolename" value='<bean:write name="rolename"/>'/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=sysRoleInfoActionForm]')){
		var objectForm = document.sysRoleInfoActionForm;
	  	objectForm.action="sysRoleInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.sysRoleInfoActionForm.action="/sysRoleInfoAction.do?method=list";
  	document.sysRoleInfoActionForm.submit();
}
</script>
</body>
</html>