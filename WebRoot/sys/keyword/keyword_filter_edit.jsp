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
<html:form action="/sysKeywordFilterAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2">关键词过滤</th>
		</tr>
		<tr>
			<td class="ali03">&nbsp;</td>
			<td class="ali01"><input type="checkbox" name="status" id="status" onclick="changeValue()" <logic:equal value="1" name="model" property="status">checked="checked"</logic:equal>/>系统启用关键词过滤器</td>
		</tr>
		<tr>
			<td class="ali03">&nbsp;</td>
			<td class="ali01" style="color:green;">格式：关键词|关键词(说明：用英文中的'|'字符)</td>
		</tr>
		<tr>
			<td class="ali03">关键字：</td>
			<td class="ali01"><textarea style="width:400px;height:120px;" name="sysKeywordFilter.filtercontent"><bean:write property="filtercontent" name="model"/></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysKeywordFilter.keywordid" value="<bean:write property="keywordid" name="model"/>"/>
<input type="hidden" name="sysKeywordFilter.status" id="sysKeywordFilter.status" value="<bean:write property="status" name="model"/>"/>
<input type="hidden" name="sysKeywordFilter.unitid" value="<bean:write property="unitid" name="model"/>"/>
<input type="hidden" name="keywordid" value="<bean:write property="keywordid" name="model"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=sysKeywordFilterActionForm]')){
		var objectForm = document.sysKeywordFilterActionForm;
	  	objectForm.action="sysKeywordFilterAction.do?method=updateSave";
	  	objectForm.submit();
	}
}

function changeValue(){
	var status = document.getElementById('status');
	if(status.checked == true){
		document.getElementById('sysKeywordFilter.status').value = "1";
	}else{
		document.getElementById('sysKeywordFilter.status').value = "0";
	}
}
</script>
</body>
</html>
