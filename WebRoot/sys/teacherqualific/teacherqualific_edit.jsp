<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.sys.bo.SysTeacherQualification"%>
<%@page import="java.util.List"%>
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

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<script type="text/javascript">
function init(){
  <logic:present name="promptinfo">
      parent.parent.Dialog.alert("<bean:write name="promptinfo"/>")
  </logic:present>   
} 
function fileChange(target) {
    var fileSize = 0;         
    if (isIE && !target.files) {     
      var filePath = target.value;     
      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
      var file = fileSystem.GetFile (filePath);     
      fileSize = file.Size;    
    } else {    
     fileSize = target.files[0].size;     
     }   
     var size = fileSize / 1024;    
     if(size>50000){  
      alert("附件不能大于50M");
      target.value="";
      return
     }
     var name=target.value;
     var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
     if(fileName !="zip" && fileName !="rar"){
         alert("请选择压缩包格式文件上传！");
         target.value="";
         return
     }
} 
</script>
</head>
<body onload="javascript:init()">
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/sysTeacherQualificationAction.do" method="post" enctype="multipart/form-data">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3"><logic:equal value="addSave" name="act">认证申请</logic:equal><logic:equal value="updateSave" name="act">修改用户</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">教师姓名：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required,length[0,25]]" name="sysTeacherQualification.username" value="<bean:write property="username" name="model"/>" a/><span class="star">*</span><span>必须和身份证上面的姓名一致</span></td>
			<td class="ali01" width="90" rowspan="4" >
              <img src="/upload/<bean:write property="photo" name="model"/>" title="点击修改照片" width="90" height="120" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
              <input type="hidden" id="uploadImg" name="sysTeacherQualification.photo" value='<bean:write property="photo" name="model"/>'/>
            </td>
		</tr>
		<tr>
			<td class="ali03">性别：</td>
			<td class="ali01"><java2code:option  name="sysTeacherQualification.sex" codetype="sex" property="sex" ckname="1"/></td>
		</tr>
		<tr>
			<td class="ali03">手机号：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[mobilephone]" name="sysTeacherQualification.mobile" value="<bean:write property="mobile" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">身份证号：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[onlyNumber]" name="sysTeacherQualification.identitycardno" value="<bean:write property="identitycardno" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">手持证件照：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="idphto" name="model"/>" title="点击修改照片" width="220" height="120" border="1" id=uploadImgShow1 onclick="uploadPhoto1()"/>
            	<input type="hidden" id="uploadImg1" name="sysTeacherQualification.idphto" value='<bean:write property="idphto" name="model"/>'/>
			</td>
		</tr>	
		<tr>
			<td class="ali03">教师资格证：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="imgpath" name="model"/>" title="点击修改照片" width="220" height="120" border="1" id=uploadImgShow2 onclick="uploadPhoto2()"/>
            	<input type="hidden" id="uploadImg2" name="sysTeacherQualification.imgpath" value='<bean:write property="imgpath" name="model"/>'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">附件：</td>
			<td class="ali01">
		   		<input type="file" name="file" id="file"   onchange="fileChange(this);" />
		   	</td>
	  	</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysTeacherQualification.teacherid" value='<bean:write property="teacherid"  name="model"/>'/>
<input type="hidden" name="sysTeacherQualification.userid" value='<bean:write property="userid"  name="model"/>'/>
<input type="hidden" name="sysTeacherQualification.status" value='<bean:write property="status" name="model"/>' />
<input type="hidden" name="sysTeacherQualification.createdate" value='<bean:write property="createdate" name="model"/>'/>
<input type="hidden" name="sysTeacherQualification.returndescript" value='<bean:write property="returndescript"  name="model"/>' />
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=sysTeacherQualificationActionForm]')){
		var objectForm = document.sysTeacherQualificationActionForm;
	  	objectForm.action="sysTeacherQualificationAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=user&pathtype=ID';
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
function uploadPhoto1(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=user&pathtype=ID';
	diag.Width = 350;
	diag.Height = 180;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
			var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
			document.getElementById('uploadImgShow1').src = "/upload/" + uploadimageurl
			document.getElementById('uploadImg1').value = uploadimageurl;
		}
		diag.close();
	};
	diag.show();
}
function uploadPhoto2(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=user&pathtype=ID';
	diag.Width = 350;
	diag.Height = 180;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
			var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
			document.getElementById('uploadImgShow2').src = "/upload/" + uploadimageurl
			document.getElementById('uploadImg2').value = uploadimageurl;
		}
		diag.close();
	};
	diag.show();
}
</script>
</body>
</html>