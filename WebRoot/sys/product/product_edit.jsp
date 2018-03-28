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

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/sysProductInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增产品</logic:equal><logic:equal value="updateSave" name="act">修改产品</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">产品编号：</td>
			<td class="ali01"><input type="text" watermark="请输入4位数字" class="validate[required]" name="sysProductInfo.productno" value="<bean:write property="productno" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">产品名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="sysProductInfo.productname" value="<bean:write property="productname" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		<tr>
			<td class="ali03">产品简称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="sysProductInfo.shortname" value="<bean:write property="shortname" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">产品图标：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改图标" width="58" height="62" border="1" id="uploadImgShow" onclick="uploadPhoto()"/>
                <input type="hidden" id="uploadImg" name="sysProductInfo.sketch" value='<bean:write property="sketch" name="model"/>'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">发布地址：</td>
			<td class="ali01"><input type="text" name="sysProductInfo.linkurl" value="<bean:write property="linkurl" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">产品类型：</td>
			<td class="ali01"><java2code:option name="sysProductInfo.type" codetype="producttype" property="type"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">产品状态：</td>
			<td class="ali01"><java2code:option name="sysProductInfo.status" codetype="status2" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">产品排序：</td>
			<td class="ali01"><input type="text" watermark="请输入数字" class="validate[required,custom[onlyNumber],length[1,4]]" name="sysProductInfo.orderindex" value="<bean:write property="orderindex" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">产品说明：</td>
			<td class="ali01"><textarea name="sysProductInfo.descript" value="<bean:write property="descript" name="model"/>"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysProductInfo.productid" value="<bean:write property="productid" name="model"/>"/>

<input type="hidden" name="productname" value="<bean:write name="productname"/>"/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=sysProductInfoActionForm]')){
		var objectForm = document.sysProductInfoActionForm;
	  	objectForm.action="sysProductInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.sysProductInfoActionForm.action="/sysProductInfoAction.do?method=list";
  	document.sysProductInfoActionForm.submit();
}

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=product&pathtype=D';
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