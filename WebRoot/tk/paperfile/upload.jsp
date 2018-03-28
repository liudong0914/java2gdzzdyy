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

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<div class="box1" position="center">
<html:form action="/tkPaperFileAction.do" method="post" enctype="multipart/form-data">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">上传试卷附件</th>
		</tr>
		<tr>
			<td class="ali03">附件：</td>
			<td class="ali01">
			 	<input type="file" id="pf" name="thefile" contenteditable="false" keepdefaultstyle="true"/><span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td class="ali03">所属年份：</td>
			<td class="ali01"><input type="text" id="year" readonly="readonly" class="date validate[required]" dateFmt="yyyy" style="width:60px;" name="tkPaperFile.theyear" value="${model.theyear}"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">所属地区：</td>
			<td class="ali01">
				<input type="text" class="validate[required]" style="width:200px;" name="tkPaperFile.area"/><span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td class="ali03">所属分类：</td>
			<td class="ali01">
				<select name="filetypeid" class="validate[required]">
					<option value="">--请选择--</option>
				<c:forEach items="${typelist }" var="type">
					<option value="${type.typeid}">${type.typename }</option>
				</c:forEach>
				</select>
				<span class="star">*</span>
			</td>
		</tr>
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01"><java2code:option  name="tkPaperFile.status" codetype="status4" property="status" ckname="1"/></td>
		</tr>
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">上传</span></button>
				<button type="button" onclick="javascript:location.href='/tkPaperFileAction.do?method=list&subjectid=${model.subjectid}&filename=${filename}&theyear=${theyear}&typeid=${typeid }&area=${area}';"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" value="<bean:write name="model" property="subjectid"/>" name="tkPaperFile.subjectid"/>
<input type="hidden" value="<bean:write name="model" property="subjectid"/>" name="subjectid"/>
<!-- 分页与排序 -->
</html:form>
</div>
<script>
function saveRecord(){
	if($("#pf").val()==""){
		top.Dialog.alert("请上传试卷文件");
		return
	}
	var year=document.getElementById("year").value;
	if(validateForm('form[name=tkPaperFileActionForm]')){
		if(isNaN(year)){
			top.Dialog.alert("年份为数字");
			return false;
		}
		document.tkPaperFileActionForm.action="/tkPaperFileAction.do?method=addSave";
		document.tkPaperFileActionForm.submit();
	}
}
</script>
</body>
</html>