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
<html:form action="/sysModuleInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增模块</logic:equal><logic:equal value="updateSave" name="act">修改模块</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">模块编号：</td>
			<td class="ali01"><bean:write property="parentno"  name="model"/><input type="text" style="width:90px;" watermark="请输入4位数字" class="validate[required,custom[onlyNumber],length[4,4]]" name="curno" id="curno" value="<bean:write name="curno"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">模块名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="sysModuleInfo.modulename" value="<bean:write property="modulename" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		<tr>
			<td class="ali03">模块状态：</td>
			<td class="ali01"><java2code:option name="sysModuleInfo.status" codetype="view" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">模块链接：</td>
			<td class="ali01"><input type="text" style="width:250px;" name="sysModuleInfo.linkurl" value="<bean:write property="linkurl" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">模块图片：</td>
			<td class="ali01"><input type="text" style="width:250px;" name="sysModuleInfo.moduleicon" value="<bean:write property="moduleicon" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">关闭菜单：</td>
			<td class="ali01"><java2code:option name="sysModuleInfo.autoopen" codetype="boolean" property="autoopen" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">模块说明：</td>
			<td class="ali01"><textarea name="sysModuleInfo.descript" value="<bean:write property="descript" name="model"/>"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysModuleInfo.moduleid" value="<bean:write property="moduleid"  name="model"/>"/>
<input type="hidden" name="sysModuleInfo.moduleno" id="sysModuleInfo.moduleno" value="<bean:write property="moduleno"  name="model"/>"/>
<input type="hidden" name="sysModuleInfo.parentno" id="sysModuleInfo.parentno" value="<bean:write property="parentno"  name="model"/>"/>
<input type="hidden" name="sysModuleInfo.productid" value="<bean:write property="productid"  name="model"/>"/>

<input type="hidden" name="modulename" value="<bean:write name="modulename"/>"/>
<input type="hidden" name="parentno" value="<bean:write name="parentno"/>" />
<input type="hidden" name="productid" value="<bean:write name="productid"/>" />
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=sysModuleInfoActionForm]')){
		document.getElementById('sysModuleInfo.moduleno').value=document.getElementById('sysModuleInfo.parentno').value + document.getElementById('curno').value;
		var objectForm = document.sysModuleInfoActionForm;
	  	objectForm.action="sysModuleInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.sysModuleInfoActionForm.action="/sysModuleInfoAction.do?method=list";
  	document.sysModuleInfoActionForm.submit();
}
</script>
</body>
</html>