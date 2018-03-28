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

<!-- 上传控件 -->
<link href="/swfupload/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/swfupload/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="/swfupload/js/swfupload/handlers_film.js"></script>
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
<%@ include file="/edu/select/select_js.jsp"%>
</head>
<body>
<div>
<div class="box1" position="center">
<html:form action="/eduCourseFilmAction.do" method="post">
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
	
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<td class="ali03">微课标题：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:400px;" name="vwhFilmInfo.title" value="<bean:write property="title" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">缩略图：</td>
			<td class="ali01">
				<img src="/upload/${model.sketch }" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="220" height="112" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
	            <input type="hidden" id="uploadImg" name="vwhFilmInfo.sketch" value='${model.sketch }'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">主讲教师：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:200px;" name="vwhFilmInfo.actor" value="<bean:write property="actor" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">二维码编号：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:200px;" name="qrcodeno" value=""/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">标价：</td>
			<td class="ali01"><input type="text" id="price" class="validate[required]" style="width:200px;" name="price" value="<bean:write  name="price"/>" onchange="showSellprice()"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">折扣：</td>
			<td class="ali01"><input type="text" id="discount" class="validate[required]" style="width:200px;" name="discount" value="<bean:write  name="discount"/>" onchange="showSellprice()" /><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">售价：</td>
			<td class="ali01"><input type="text" id="sellprice" class="validate[required]" style="width:200px;" name="sellprice" value="<bean:write  name="sellprice"/>" readonly="readonly" /><span class="star">*</span></td>
		</tr>
        <tr>
			<td class="ali03">微课简介：</td>
			<td class="ali01" colspan="2"><textarea name="vwhFilmInfo.keywords" style="width:400px;"><bean:write property="keywords" name="model"/></textarea></td>
		</tr>
		<tr>
			<td class="ali03">详细描述：</td>
			<td class="ali01" colspan="2">
				<textarea name="vwhFilmInfo.descript" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars("")%></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<logic:notEmpty name="tag" scope="request">
				<button type="button" onclick="javascript:history.go(-1);"><span class="icon_back">返回</span></button>
				</logic:notEmpty>
			</td>
		</tr>
	</table>
	<input type="hidden" name="imgwidth" value="0"/>
	<input type="hidden" name="imgheight" value="0"/>
	<input type="hidden" name="second" value="15"/>
	<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
	<input type="hidden" name="courseclassid" value="<bean:write name="courseclassid"/>"/>
	<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
</html:form>
</div>
</div>
<script>
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="vwhFilmInfo.descript"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}

function saveRecord(){
	if(validateForm('form[name=eduCourseFilmActionForm]')){
		var objectForm = document.eduCourseFilmActionForm;
		
	  	
		//同步数据后可以直接取得textarea的value
		keditor.sync();
		document.getElementById("btnsave").disabled = true;
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
</body>
</html>
<%!
private String htmlspecialchars(String str) {
	if(str == null || "".equals(str)) return "";
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>