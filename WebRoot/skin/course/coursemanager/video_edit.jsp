<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<!--框架必需start-->
<script type="text/javascript" src="/libs/js/framework.js"></script>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="/libs/js/form/validation.js" type="text/javascript"></script>
<script src="/libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->

<!--弹窗start-->
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>
<body>
<div>
<div class="box1" position="center">
<html:form action="/eduCourseFilmAction.do" method="post">
	<div class="mainCon-bd">
	<div class="form-body creatCourseStep" style="width: 360px; margin: 0 auto;">
		<div class="form-row form-row01">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				视频名称：
				</label>
			</div>
			<div class="input">
				<input id="vwhFilmPix.name" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="vwhFilmPix.name" value="<bean:write property="name" name="model"/>" type="text" >
			</div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				缩略图：
				</label>
			</div>
			<div class="input">
				<img src="/upload/<bean:write property="imgpath" name="model"/>" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="80" height="107" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
	            <input type="hidden" id="uploadImg" name="vwhFilmPix.imgpath" value='${model.imgpath }'/>
			</div>
		</div>
		<div class="form-row form-row01">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				排序：
				</label>
			</div>
			<div class="input">
				<input id="vwhFilmPix.orderindex" keepDefaultStyle="true" class="validate[required,onlyNumber] ipt-text ipt-text01 formEleToVali" name="vwhFilmPix.orderindex" value="<bean:write property="orderindex" name="model"/>" type="text" >
			</div>
		</div>
		<div class="clearfix mt20" style="text-align:center;padding-left: 90px;">
			<logic:notEqual value="1" name="eduCourseInfo" property="status">
			<%
			String isAuhtor = (String)session.getAttribute("isAuhtor");
			Map moduleidMap = (Map)session.getAttribute("moduleidMap");
			String moduleidType = (String)moduleidMap.get("2");
			if("1".equals(isAuhtor) || "2".equals(moduleidType)){
			%>
			<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" style="display:inline-block;" type="button">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<%} %>
		</logic:notEqual>
			<input class="btn btn-pop ml20" value="返回" onclick="backRecord()" type="button">
		</div>
	</div>
	</div>
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
	if(validateForm('form[name=eduCourseFilmActionForm]')){
		var objectForm = document.eduCourseFilmActionForm;
	  	objectForm.action="eduCourseFilmAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.eduCourseFilmActionForm.action="/eduCourseFilmAction.do?method=videoList";
  	document.eduCourseFilmActionForm.submit();
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