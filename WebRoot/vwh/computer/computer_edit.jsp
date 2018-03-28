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
<html:form action="/vwhComputerInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增视频服务器</logic:equal><logic:equal value="updateSave" name="act">修改视频服务器</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">服务器名称：</td>
			<td class="ali01"><input type="text" style="width:250px;" class="validate[required]" name="vwhComputerInfo.computername" value="<bean:write name="model" property="computername"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">服务器IP或域名：</td>
			<td class="ali01"><input type="text" style="width:250px;" watermark="格式：202.192.198.134或vod.wkmk.com" class="validate[required]" name="vwhComputerInfo.ip" value="<bean:write property="ip" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		<tr>
			<td class="ali03">服务器端口号：</td>
			<td class="ali01"><input type="text" style="width:90px;" watermark="请输入数字" class="validate[required,custom[onlyNumber],length[2,6]]" name="vwhComputerInfo.port" value="<bean:write name="model" property="port"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="vwhComputerInfo.computerid" value="<bean:write property="computerid" name="model"/>"/>
<input type="hidden" name="vwhComputerInfo.unitid" value="<bean:write name="model" property="unitid"/>"/>

<input type="hidden" name="computername" value="<bean:write name="computername"/>" />
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=vwhComputerInfoActionForm]')){
		var objectForm = document.vwhComputerInfoActionForm;
	  	objectForm.action="vwhComputerInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.vwhComputerInfoActionForm.action="/vwhComputerInfoAction.do?method=list";
  	document.vwhComputerInfoActionForm.submit();
}
</script>
</body>
</html>