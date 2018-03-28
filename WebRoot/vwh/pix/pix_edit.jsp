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
<html:form action="/vwhFilmPixAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<td class="ali03">视频名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:200px;" name="vwhFilmPix.name" value="<bean:write property="name" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">缩略图：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="imgpath" name="model"/>" title="点击修改图片" width="80" height="107" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
              	<input type="hidden" id="uploadImg" name="vwhFilmPix.imgpath" value='<bean:write property="imgpath" name="model"/>'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">排序：</td>
			<td class="ali01" colspan="2"><input type="text" class="validate[required,onlyNumber]" name="vwhFilmPix.orderindex" value="<bean:write property="orderindex" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="vwhFilmPix.pixid" value="<bean:write property="pixid" name="model"/>"/>

<input type="hidden" value="<bean:write name="filmid"/>" name="filmid"/>
<input type="hidden" value="<bean:write name="name"/>" name="name"/>
<input type="hidden" value="<bean:write name="convertstatus"/>" name="convertstatus"/>
<input type="hidden" value="<bean:write name="flag"/>" name="flag"/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
<input type="hidden" value="<bean:write name="mark"/>" name="mark" id="mark"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=vwhFilmPixActionForm]')){
		var objectForm = document.vwhFilmPixActionForm;
	  	objectForm.action="vwhFilmPixAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.vwhFilmPixActionForm.action="/vwhFilmPixAction.do?method=list";
  	document.vwhFilmPixActionForm.submit();
}

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=vwh&pathtype=ID&sketch=true&swidth=160&sheight=213';
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