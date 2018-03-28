<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
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
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/cmsImageInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增图片</logic:equal><logic:equal value="updateSave" name="act">修改图片</logic:equal></th>
		<tr>
			<td class="ali03">标&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[required]" name="cmsImageInfo.title" value="<bean:write property="title" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">跳转地址：</td>
			<td class="ali01"><input type="text" style="width:200px;" name="cmsImageInfo.url" value="<bean:write property="url" name="model"/>"/></td>
		</tr>
		<tr>
		    <td class="ali03">状&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
		    <td class="ali01"><java2code:option name="cmsImageInfo.status" codetype="status2" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">缩&nbsp;略&nbsp;图：</td>
			<td class="ali01">
              <img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改照片" height="104" width="213"  border="1" id=uploadImgShow onclick="uploadPhoto()"/>
			  <input type="hidden" id="uploadImg" name="cmsImageInfo.sketch" value='<bean:write property="sketch" name="model"/>'/>
			</td>
		</tr>
        <tr>
			<td class="ali03">排&nbsp;&nbsp;&nbsp;&nbsp;序：</td>
			<td class="ali01"><input type="text" style="width:80px;" name="cmsImageInfo.orderindex" value="<bean:write property="orderindex" name="model"/>"/></td>
		</tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="cmsImageInfo.imageid" value="<bean:write property="imageid" name="model"/>"/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
<input type="hidden" name="cmsImageInfo.createdate" value="<bean:write property="createdate" name="model"/>"/>

</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=cmsImageInfoActionForm]')){

		var objectForm = document.cmsImageInfoActionForm;
	  	objectForm.action="cmsImageInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.cmsImageInfoActionForm.action="/cmsImageInfoAction.do?method=list";
  	document.cmsImageInfoActionForm.submit();
}

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=image&pathtype=ID';
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