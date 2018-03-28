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
<div>
<div class="box1" position="center">
<html:form action="/tkBookContentFilmUploadAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">修改微课信息</th>
		</tr>
		<tr>
			<td class="ali03" wdith="80">微课标题：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:400px;" name="tkBookContentFilm.title" value="<bean:write property="title" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">微课排序：</td>
			<td class="ali01" colspan="2"><input type="text" class="validate[required,onlyNumber]" name="tkBookContentFilm.orderindex" value="<bean:write property="orderindex" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
		<tr>
			<td class="ali03">微课分类：</td>
			<td class="ali01">
			<select name="tkBookContentFilm.type">
             	<option value="1" ${model.type eq 1 ?'selected':''}>解题微课</option>
             	<option value="2" ${model.type eq 2 ?'selected':''}>举一反三微课</option>
            </select>
            <span class="star">*</span>
			</td>
		</tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="tkBookContentFilm.fid" value="<bean:write name="model" property="fid"/>"/>
<input type="hidden" name="tkBookContentFilm.status" value="<bean:write name="model" property="status"/>"/>
<input type="hidden" name="tkBookContentFilm.bookcontentid" value="<bean:write name="model" property="bookcontentid"/>"/>
<input type="hidden" value="<bean:write name="bookcontentid"/>" name="bookcontentid" id="bookcontentid"/>
<input type="hidden" name="tkBookContentFilm.bookid" value="<bean:write name="model" property="bookid"/>"/>
<input type="hidden" name="tkBookContentFilm.filmid" value="<bean:write name="model" property="filmid"/>"/>
<input type="hidden" name="tkBookContentFilm.filmtwocode" value="<bean:write name="model" property="filmtwocode"/>"/>
<input type="hidden" name="userid" value="<bean:write name="model" property="sysUserInfo.userid"/>"/>
<input type="hidden" name="tkBookContentFilm.createdate" value="<bean:write name="model" property="createdate"/>"/>
<input type="hidden" name="tkBookContentFilm.hits" value="<bean:write name="model" property="hits"/>"/>
</html:form>
</div>
</div>
<script>

function saveRecord(){
	if(validateForm('form[name=tkBookContentFilmActionForm]')){
		var objectForm = document.tkBookContentFilmActionForm;
		var bookcontentid = document.getElementById("bookcontentid").value;
	  	objectForm.action="tkBookContentFilmUploadAction.do?method=<bean:write name="act"/>&bookcontentid="+bookcontentid;
	  	objectForm.submit();
	}
}

function backRecord(){
	var bookcontentid = document.getElementById("bookcontentid").value;
  	document.tkBookContentFilmActionForm.action="/tkBookContentFilmUploadAction.do?method=list&bookcontentid="+bookcontentid;
  	document.tkBookContentFilmActionForm.submit();
}
</script>
</body>
</html>