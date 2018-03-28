<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<!--框架必需start-->
<script type="text/javascript" src="/libs/js/framework.js"></script>
<link href="/libs/css/framework/form.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="/libs/js/form/validation.js" type="text/javascript"></script>
<script src="/libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 上传控件 -->
<link href="/swfupload/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/swfupload/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="/swfupload/js/swfupload/handlers_coursefilm.js"></script>
<script type="text/javascript">
	var swfu;
	
	window.onload = function () {
		swfu = new SWFUpload({
			upload_url: "/fileUploadAction.do?ftag=vwhfilm&unitid=<%=session.getAttribute("s_unitid") %>",
			
			// File Upload Settings
			file_size_limit : "2048 MB",	// 100MB
			file_types : "*.avi;*.wmv;*.3gp;*.mov;*.asf;*.asx;*.flv;*.f4v;*.mp4;*.mpg;*.mpeg;*.vob;*.mkv;*.ts;*.wmv9;*.rm;*.rmvb",
			file_types_description : "视频",
			file_upload_limit : "1",
							
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			file_queued_handler : fileQueued,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
			upload_success_handler : uploadSuccess,
			upload_complete_handler : uploadComplete,

			// Button Settings
			button_image_url : "/swfupload/images/btn_02.jpg",
			button_placeholder_id : "spanButtonPlaceholder",
			button_width: 156,
			button_height: 36,
			button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
			button_text_top_padding: 0,
			button_text_left_padding: 18,
			button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
			button_cursor: SWFUpload.CURSOR.HAND,
			button_action : SWFUpload.BUTTON_ACTION.SELECT_FILES, //SWFUplaod.BUTTON_ACTION.SELECT_FILE ,SWFUpload.BUTTON_ACTION.SELECT_FILES
			
			// Flash Settings
			flash_url : "/swfupload/js/swfupload/swfupload.swf",
			prevent_swf_caching : true,

			//custom_settings : {
			//	upload_target : "divFileProgressContainer"
			//},
			// Debug Settings
			debug: false  //是否显示调试窗口
		});
	};
</script> 
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>

<body style="background:#fcfcfc;">
<html:form action="/eduCourseFilmAction.do?method=addSaveFilm" method="post">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
	<table id="infoTable" align="center" width="99%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
	  <tr>
	    <td width="60%" height="32" background="/swfupload/images/d11.gif" >&nbsp;你选择的视频</td>
	    <td width="16%" align="center" background="/swfupload/images/d11.gif" >视频大小</td>
	    <td width="14%" align="center" background="/swfupload/images/d11.gif" >状态</td>
	    <td width="10%" align="center" background="/swfupload/images/d11.gif">功能</td>
	  </tr>
	</table>
	
	<table width="100%" border="0" cellspacing="0" cellpadding="0" id="table_uploadbtn" style="background-color:#FFFFFF;">
	  <tr>
	    <td height="30" colspan="4" align="center" style="padding-left:10px;"></td>
	  </tr>
	  <tr>
	    <td height="25" colspan="4" align="center" style="padding-left:10px;">
	    <table width="100%" align="center">
	    <tr>
	    <td align="center" width="100%" id="td_btnxz">
	    <span id="spanButtonPlaceholder" ></span>
	    </td>
	    </tr>
	    </table>
	    </td>
	  </tr>
	  <tr>
	    <td height="10" colspan="4" align="center" style="padding-left:10px;"></td>
	  </tr>
	</table>
	
		<div class="form-row form-row01">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				微课标题：
				</label>
			</div>
			<div class="input">
				<input id="vwhFilmInfo.title" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="vwhFilmInfo.title" value="${model.title }" style="width:306px;" type="text">
			</div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				缩略图：
				</label>
			</div>
			<div class="input">
				<div class="fl" width="550">
					<img src="/upload/${model.sketch }" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="220" height="112" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
		            <input type="hidden" id="uploadImg" name="vwhFilmInfo.sketch" value='${model.sketch }'/>
				</div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				二维码编号：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" id="qrcodeno" name="qrcodeno" value="" style="width:150px;" type="text">
		    </div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				标价：
				</label>
			</div>
			<div class="input">
				<input id="price" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="price" value="${price }" style="width:150px;" type="text" onchange="showSellprice()">
		    </div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				折扣：
				</label>
			</div>
			<div class="input">
				<input id="discount" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="discount" value="${discount }" style="width:150px;" type="text" onchange="showSellprice()">折扣最高不能超过10！
		    </div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				售价：
				</label>
			</div>
			<div class="input">
				<input id="sellprice" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="sellprice" value="${sellprice }" style="width:150px;" type="text" readonly="readonly">
		    </div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				序号：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="validate[required,custom[onlyNumber],length[1,4]] ipt-text formEleToVali" name="orderindex" value="" style="width:150px;" type="text">按序号顺序排列
		    </div>
		</div>
		<div class="clearfix mt20" style="text-align:center;">
			<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" id="btnsave" style="display:inline-block;" type="button">
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input class="btn btn-pop ml20" value="返回" onclick="goBack()" type="button">
		</div>
	<input type="hidden" name="imgwidth" value="0"/>
	<input type="hidden" name="imgheight" value="0"/>
	<input type="hidden" name="second" value="15"/>
	<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
	<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
</div>
</div>		
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
</html:form>

</body>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseFilmActionForm]')){
		var qrcodeno = document.getElementById("qrcodeno").value;
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
		  	
		document.getElementById("btnsave").disabled = true;
		var objectForm = document.eduCourseFilmActionForm;
	  	objectForm.action="/eduCourseFilmAction.do?method=${act }";
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
function goBack(){
	var objectForm = document.eduCourseFilmActionForm;
  	objectForm.action="/eduCourseFilmAction.do?method=filmList";
  	objectForm.submit();
}

function showSellprice(){
	var price = document.getElementById("price").value;
	var discount = document.getElementById("discount").value;
	var sellprice = "";
	if(price !="" && discount != ""){
		if(discount <= 10){
			sellprice = ((price*discount)/10).toFixed(2);
			document.getElementById("sellprice").value =sellprice;
			document.getElementById("sellpriceId").style.display='';
		}else{
			alert("折扣不能大于10！")
		}
	}
}
var btnsave = document.getElementById("btnsave");
btnsave.disabled = true;
</script>
</html>