<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkBookContentFilm"%>
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
<div>
<div class="box1" position="center">
<html:form action="/tkBookContentFilmAuditionAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">修改试听微课信息</th>
		</tr>
		<tr>
			<td class="ali03" wdith="80">排序（数字越大越靠前）：</td>
			<td class="ali01"><input type="text" class="validate[required,custom[onlyNumber]]" style="width:400px;" name="tkBookContentFilmAudition.orderindex" value="<bean:write property="orderindex" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01">
			<select name="tkBookContentFilmAudition.status">
             	<option value="1" ${model.status eq 1 ?'selected':''}>显示</option>
             	<option value="0" ${model.status eq 0 ?'selected':''}>不显示</option>
            </select>
            <span class="star">*</span>
			</td>
		</tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="javascript:location.href='/tkBookContentFilmAuditionAction.do?method=list&bookcontentid=${bookcontentid}'"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="tkBookContentFilmAudition.auditionid" value="<bean:write name="model" property="auditionid"/>"/>
<input type="hidden" name="tkBookContentFilmAudition.contentfilmid" value="<bean:write name="model" property="contentfilmid"/>"/>
<input type="hidden" name="tkBookContentFilmAudition.hits" value="<bean:write name="model" property="hits"/>"/>
<input type="hidden" value="<bean:write name="bookcontentid"/>" name="bookcontentid"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=tkBookContentFilmActionForm]')){
		var objectForm = document.tkBookContentFilmAuditionActionForm;
	  	objectForm.action="/tkBookContentFilmAuditionAction.do?method=${act}";
	  	objectForm.submit();
	}
}
</script>
</body>
</html>