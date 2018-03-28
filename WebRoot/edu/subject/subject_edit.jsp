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
<html:form action="/eduSubjectInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增学科</logic:equal><logic:equal value="updateSave" name="act">修改学科</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">学科名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="eduSubjectInfo.subjectname" value="<bean:write property="subjectname" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">学科全称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="eduSubjectInfo.fullname" value="<bean:write property="fullname" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01"><java2code:option name="eduSubjectInfo.status" codetype="status2" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">排&nbsp;&nbsp;&nbsp;&nbsp;序：</td>
			<td class="ali01"><input type="text" class="validate[required,onlyNumber]" name="eduSubjectInfo.orderindex" value="<bean:write property="orderindex" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">banner图片：</td>
			<td class="ali01">
			<img src="/upload/<bean:write property="banner" name="model"/>" title="点击修改照片" width="360" height="90" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
              <input type="hidden" id="uploadImg" name="eduSubjectInfo.banner" value='<bean:write property="banner" name="model"/>'/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="eduSubjectInfo.subjectid" value="<bean:write property="subjectid" name="model"/>"/>

<input type="hidden" name="subjectname" value='<bean:write name="subjectname"/>'/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduSubjectInfoActionForm]')){
		var objectForm = document.eduSubjectInfoActionForm;
	  	objectForm.action="eduSubjectInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.eduSubjectInfoActionForm.action="/eduSubjectInfoAction.do?method=list";
  	document.eduSubjectInfoActionForm.submit();
}

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=banner&pathtype=I';
	diag.Width = 350;
	diag.Height = 180;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
			var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
			document.getElementById('uploadImgShow').src = "/upload/" + uploadimageurl
			document.getElementById('uploadImg').value = uploadimageurl;
		}
		diag.close();
	};
	diag.show();
}
</script>
</body>
</html>