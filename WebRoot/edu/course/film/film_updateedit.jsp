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

<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->

<%@ include file="/edu/select/select_js.jsp"%>
</head>
<body>
<div>
<div class="box1" position="center">
<html:form action="/eduCourseFilmAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<td class="ali03">微课标题：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:400px;" name="eduCourseFilm.title" value="${model.title }"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">缩略图：</td>
			<td class="ali01">
				<img src="/upload/${filminfo.sketch }" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="220" height="112" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
	            <input type="hidden" id="uploadImg" name="vwhFilmInfo.sketch" value='${filminfo.sketch }'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">二维码编号：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:200px;" id="qrcodeno" name="eduCourseFilm.qrcodeno" value="${model.qrcodeno }"/></td>
		</tr>
		<tr>
			<td class="ali03">标价：</td>
			<td class="ali01"><input type="text" class="validate[required]" id="price" style="width:200px;" name="eduCourseFilm.price" value="<bean:write property="price" name="model"/>" onchange="showSellprice()"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">折扣：</td>
			<td class="ali01"><input type="text" class="validate[required]" id="discount" style="width:200px;" name="eduCourseFilm.discount" value="<bean:write property="discount" name="model"/>"   onchange="showSellprice()"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">售价：</td>
			<td class="ali01"><input type="text" class="validate[required]" id="sellprice" style="width:200px;" name="eduCourseFilm.sellprice" value="<bean:write property="sellprice" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">序号：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:200px;" name="eduCourseFilm.orderindex" value="${model.orderindex }"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01">
				<java2code:option name="eduCourseFilm.status" codetype="status4" property="status" ckname="1"></java2code:option>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="javascript:history.go(-1);"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
	<input type="hidden" name="imgwidth" value="0"/>
	<input type="hidden" name="imgheight" value="0"/>
	<input type="hidden" name="second" value="15"/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
	
<input type="hidden" name="eduCourseFilm.coursefilmid" value="<bean:write property="coursefilmid" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.filmid" value="<bean:write property="filmid" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.coursecolumnid"  value="<bean:write property="coursecolumnid" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.courseid" value="<bean:write property="courseid" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.orderindex"  value="<bean:write property="orderindex" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.hits"  value="<bean:write property="hits" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.sellcount" value="<bean:write property="sellcount" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.createdate" value="<bean:write property="createdate" name="model"/>"/>

<input type="hidden" name="vwhFilmInfo.filmid" value="<bean:write property="filmid" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.title" value="<bean:write property="title" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.sketchimg" value="<bean:write property="sketchimg" name="filminfo"/>"/>
<input type="hidden" name="VwhFilmInfo.orderindex"  value="<bean:write property="orderindex" name="filminfo"/>"/>
<input type="hidden" name="VwhFilmInfo.hits" value="<bean:write property="hits" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.status"  value="<bean:write property="status" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.createdate"  value="<bean:write property="createdate" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.updatetime" value="<bean:write property="updatetime" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.computerid" value="<bean:write property="computerid" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.unitid" value="<bean:write property="unitid" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.twocodepath" value="<bean:write property="twocodepath" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.actor" value="<bean:write property="actor" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.keywords" value="<bean:write property="keywords" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.descript" value="<bean:write property="descript" name="filminfo"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseFilmActionForm]')){
		var objectForm = document.eduCourseFilmActionForm;
		
		var qrcodeno0 = '${model.qrcodeno }';
		var qrcodeno = document.getElementById("qrcodeno").value;
		if(qrcodeno0 != qrcodeno){
			var url = '/eduCourseFilmAction.do?method=checkQrcodeno&qrcodeno='+qrcodeno+'&ram=' + Math.random();
			$.ajax({
			  type: 'post',
			  url: url,
			  async: false,//同步请求
			  dataType:'text',
			  success: function(data){
			  	temp = data;
			  }
			});
		  	if(temp == '1'){
		  		top.Dialog.alert("二维码编号已存在，请更换编号再试!");
		  		return false;
		  	}
		}
	  	
	  	objectForm.action="eduCourseFilmAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
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

function showSellprice(){
	var price = document.getElementById("price").value;
	var discount = document.getElementById("discount").value;
	var sellprice = "";
	if(discount <= 10){
		sellprice = ((price*discount)/10).toFixed(2);
		document.getElementById("sellprice").value =sellprice;
	}else{
		alert("折扣不能大于10！")
	}
}
</script>
</body>
</html>
