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
</head>
<body>
<div>
<div class="box1" position="center">
<html:form action="/vwhFilmPixAction.do" method="post">
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
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="imgwidth" value="0"/>
<input type="hidden" name="imgheight" value="0"/>
<input type="hidden" name="second" value="15"/>
	
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
		document.getElementById("btnsave").disabled = true;
	  	objectForm.action="vwhFilmPixAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.vwhFilmPixActionForm.action="/vwhFilmPixAction.do?method=list";
  	document.vwhFilmPixActionForm.submit();
}

var btnsave = document.getElementById("btnsave");
btnsave.disabled = true;
</script>
</body>
</html>