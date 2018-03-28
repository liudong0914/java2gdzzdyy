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
<html:form action="/sysUnitInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">SEO优化</th>
		</tr>
		<tr>
			<td class="ali03">站点关键字：</td>
			<td class="ali01"><input type="text" name="sysUnitInfo.keywords" style="width:500px;" value="<bean:write property="keywords" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">单位简介：</td>
			<td class="ali01"><textarea name="sysUnitInfo.descript" style="width:500px;"><bean:write property="descript" name="model"/></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysUnitInfo.unitid" value="<bean:write property="unitid"  name="model"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=sysUnitInfoActionForm]')){
		var objectForm = document.sysUnitInfoActionForm;
	  	objectForm.action="sysUnitInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}
</script>
</body>
</html>