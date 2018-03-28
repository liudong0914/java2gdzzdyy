<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>个人中心</title>
<link type="text/css" rel="stylesheet" href="/skin/pcenter/css/style.css">
<script type="text/javascript" src="/skin/pcenter/js/jquery-1.8.2.min.js"></script>

<!--框架必需start-->
<script type="text/javascript" src="../../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="../../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="../../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%@ include file="js.jsp"%>
<script type="text/javascript">
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
<body style="background-color:#f9f9f9;" onload="javascript:init()">
<!------头部-------->
<%@ include file="top.jsp"%>
<!------内容-------->
<html:form action="/pcenter.do" method="post" enctype="multipart/form-data">
<div class="personal">
	<%@ include file="left.jsp" %>
    <div class="information_right">
    	<div class="information_right_top">
			<p>教师认证  </p>
        </div>
        <div class="information_right_main teacher_main">
            <div class="information_right_main_02">
            	<p style="padding-left:14px;"><a class="teacher">*</a>真实姓名：</p>
                <p>
                	<input type="text" style="width:150px;background:none;" class="validate[required,length[0,25]]" name="sysTeacherQualification.username" value="<bean:write property="username" name="model"/>" a/>&nbsp;&nbsp;必须和身份证上面的姓名一致
                </p>
            </div>
            <div class="information_right_main_03">
            	<p style="padding-left:14px;"><a class="teacher">*</a>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</p>
                <p><java2code:option  name="sysTeacherQualification.sex" codetype="sex" property="sex" ckname="1"/></p>
            </div>
             <div class="information_right_main_02">
            	<p style="padding-left:14px;"><a class="teacher">*</a>手机号码：</p>
                <p>
                	<input type="text" style="width:200px;background:none;" class="validate[mobilephone]" name="sysTeacherQualification.mobile" value="<bean:write property="mobile" name="model"/>"/>
                </p>
            </div>
            <div class="information_right_main_02">
            	<p style="padding-left:14px;"><a class="teacher">*</a>身份证号：</p>
                <p>
                	<input type="text" style="width:200px;background:none;" class="validate[onlyNumber]" name="sysTeacherQualification.identitycardno" value="<bean:write property="identitycardno" name="model"/>"/>
                </p>
            </div>
            <div class="information_right_main_02">
            	<p><a class="teacher">*</a>手持证件照：</p>
                <p>
                	<img src="/upload/<bean:write property="idphto" name="model"/>"  width="220" height="120" border="1" id=uploadImgShow1 onclick="uploadPhoto1()"/>
            		<input type="hidden" id="uploadImg1" name="sysTeacherQualification.idphto" value='<bean:write property="idphto" name="model"/>'/>
                </p>
            </div>
            <div class="information_right_main_02">
            	<p><a class="teacher">*</a>教师资格证：</p>
                <p>
                	<img src="/upload/<bean:write property="imgpath" name="model"/>"  width="220" height="120" border="1" id=uploadImgShow2 onclick="uploadPhoto2()"/>
            		<input type="hidden" id="uploadImg2" name="sysTeacherQualification.imgpath" value='<bean:write property="imgpath" name="model"/>'/>
                </p>
            </div>
             <div class="information_right_main_02">
            	<p style="padding-left:54px">附件：</p>
                <p>
                	<input type="file" name="file" id="file"  keepdefaultstyle="true" onchange="fileChange(this);" style="background:none;border: none;"/>
                </p>
            </div>
            <p style="font-size: 14px;">附件必须上传zip格式的文件，里面可以包含教师资格证等证明文件的复件或者扫描件！</p>
            <a href="#" onclick="saveRecord()" class="information_right_main_a teacher_a02">确定</a>
        </div><!----information_right_main-e---->
        <div class="possword_bottom">
        	<img src="/skin/pcenter/images/icon10.png" class="teacher_img" />
            <div class="teacher_font">
                <span>通过身份验证后即可在平台上参与答疑回复挣学币，并可提现。</span><br />
                <span>进步学堂承诺，认证过程中填写的身份证号与上传的证明材料只做认证，不会用作其他任何用途。</span>
            </div>
        </div><!----possword_bottom-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
<input type="hidden" name="sysTeacherQualification.photo" value='<bean:write property="photo"  name="model"/>'/>
<input type="hidden" name="sysTeacherQualification.userid" value='<bean:write property="userid"  name="model"/>'/>
<input type="hidden" name="sysTeacherQualification.status" value='<bean:write property="status" name="model"/>' />
<input type="hidden" name="sysTeacherQualification.createdate" value='<bean:write property="createdate" name="model"/>'/>
<input type="hidden" name="sysTeacherQualification.returndescript" value='<bean:write property="returndescript"  name="model"/>' />
</html:form>

<%@ include file="footer.jsp"%>

<script>
function saveRecord(){
	if(validateForm('form[name=sysUserInfoActionForm]')){
		var objectForm = document.sysUserInfoActionForm;
	  	objectForm.action="/pcenter.do?method=<bean:write name="act"/>";
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