var formChecker = null;
function swfUploadLoaded() {
	var btnsave = document.getElementById("btnsave");
	//btnsave.disabled = true;
	
	formChecker = window.setInterval(validateForm, 1000);
	validateForm();
}

function validateForm() {
	var txtFileName = document.getElementById("txtFileName");
	
	var isValid = true;
	if (txtFileName.value === "") {
		isValid = false;
	}
	
	//document.getElementById("btnsave").disabled = !isValid;

}

// Called by the submit button to start the upload
function doSubmit(e) {
	if (formChecker != null) {
		clearInterval(formChecker);
		formChecker = null;
	}
	
	e = e || window.event;
	if (e.stopPropagation) {
		e.stopPropagation();
	}
	e.cancelBubble = true;
	
	try {
		swfu.startUpload();
	} catch (ex) {

	}
	return false;
}

 // Called by the queue complete handler to submit the form
function uploadDone() {
	try {
		document.forms[0].submit();
	} catch (ex) {
		alert("Error submitting form");
	}
}

function fileDialogStart() {
	var txtFileName = document.getElementById("txtFileName");
	txtFileName.value = "";

	this.cancelUpload();
}

function fileQueueError(file, errorCode, message)  {
	try {
		// Handle this error separately because we don't want to create a FileProgress element for it.
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			//alert("上传文件数量不能超过"+swfu.settings.file_upload_limit+"个.");
			alert("\u4E0A\u4F20\u6587\u4EF6\u6570\u91CF\u4E0D\u80FD\u8D85\u8FC7"+swfu.settings.file_upload_limit+"\u4E2A.");
			return;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			//alert("上传文件大小不能超过"+swfu.settings.file_size_limit+".");
			alert("\u4E0A\u4F20\u6587\u4EF6\u5927\u5C0F\u4E0D\u80FD\u8D85\u8FC7"+swfu.settings.file_size_limit+".");
			return;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			//alert("不能上传空文件.");
			alert("\u4E0D\u80FD\u4E0A\u4F20\u7A7A\u6587\u4EF6.");
			return;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			//alert("不允许上传"+file.type+"类型的文件.");
			alert("\u4E0D\u5141\u8BB8\u4E0A\u4F20"+file.type+"\u7C7B\u578B\u7684\u6587\u4EF6.");
			return;
		default:
			//alert("文件上传出错.");
			alert("\u6587\u4EF6\u4E0A\u4F20\u51FA\u9519.");
			return;
		}
	} catch (e) {
	}
}

function fileQueued(file) {
	try {
		var fname = file.name;
		var txtFileName = document.getElementById("txtFileName");
		txtFileName.value = fname;
		document.getElementById('title').value = fname.substring(0, fname.lastIndexOf("."));
	} catch (e) {
	}

}
function fileDialogComplete(numFilesSelected, numFilesQueued) {
	validateForm();
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	this.setButtonDisabled(true);//开始上传后不能再选择文件
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		//计算文件总大小和已上传大小
		var bt = "";
		if(bytesTotal < 800*1024) bt = (Math.floor((bytesTotal/1024)*100)/100) + "KB";
	    if(bytesTotal >= 800*1024) bt = (Math.floor((bytesTotal/(1024*1024))*100)/100) + "MB";
	    var bl = "";
		if(bytesLoaded < 800*1024) bl = (Math.floor((bytesLoaded/1024)*100)/100) + "KB";
	    if(bytesLoaded >= 800*1024) bl = (Math.floor((bytesLoaded/(1024*1024))*100)/100) + "MB";

		file.id = "singlefile";	// This makes it so FileProgress only makes a single UI element, instead of one for each file
		var progress = new FileProgress(file, this.customSettings.progress_target);
		progress.setProgress(percent);
		//progress.setStatus("正在上传文件,总大小"+bt+",已上传"+bl+",完成比率:"+percent+"%");
		progress.setStatus("\u6B63\u5728\u4E0A\u4F20\u6587\u4EF6,\u603B\u5927\u5C0F"+bt+",\u5DF2\u4E0A\u4F20"+bl+",\u5B8C\u6210\u6BD4\u7387\:"+percent+"%");
	} catch (e) {
	}
}

function uploadSuccess(file, serverData,falg) {
	try {
	    document.getElementById("filesize").value=file.size;
	    document.getElementById("filename").value=file.name;
	    document.getElementById("filepath").value=serverData;
		var progress = new FileProgress(file, this.customSettings.progress_target);
		progress.setComplete();
		//progress.setStatus("上传完成.");
		progress.setStatus("\u4E0A\u4F20\u5B8C\u6210.");
		progress.toggleCancel(false);
		
		if (serverData === " ") {
			this.customSettings.upload_successful = false;
		} else {
			this.customSettings.upload_successful = true;
			document.getElementById("hidFileID").value = serverData;
		}
	} catch (e) {
	}
}

function uploadComplete(file) {
	try {
		if (this.customSettings.upload_successful) {
			this.setButtonDisabled(true);
			uploadDone();
		} else {
			file.id = "singlefile";	// This makes it so FileProgress only makes a single UI element, instead of one for each file
			var progress = new FileProgress(file, this.customSettings.progress_target);
			progress.setError();
			//progress.setStatus("文件上传出错!");
			progress.setStatus("\u6587\u4EF6\u4E0A\u4F20\u51FA\u9519\!");
			progress.toggleCancel(false);
			
			var txtFileName = document.getElementById("txtFileName");
			txtFileName.value = "";
			validateForm();

			//alert("文件上传发生错误,连不上服务器.");
		}
	} catch (e) {
	}
}

function uploadError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.UPLOAD_ERROR.FILE_CANCELLED) {
			// Don't show cancelled error boxes
			return;
		}
		
		var txtFileName = document.getElementById("txtFileName");
		txtFileName.value = "";
		validateForm();
		
		// Handle this error separately because we don't want to create a FileProgress element for it.
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
			//alert("文件上传出错.");
			alert("\u6587\u4EF6\u4E0A\u4F20\u51FA\u9519.");
			return;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			//alert("上传文件数量不能超过"+swfu.settings.file_upload_limit+"个.");
			alert("\u4E0A\u4F20\u6587\u4EF6\u6570\u91CF\u4E0D\u80FD\u8D85\u8FC7"+swfu.settings.file_upload_limit+"\u4E2A.");
			return;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			break;
		default:
			//alert("文件上传出错.");
			alert("\u6587\u4EF6\u4E0A\u4F20\u51FA\u9519.");
			return;
		}

		file.id = "singlefile";	// This makes it so FileProgress only makes a single UI element, instead of one for each file
		var progress = new FileProgress(file, this.customSettings.progress_target);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			//progress.setStatus("文件上传出错.");
			progress.setStatus("\u6587\u4EF6\u4E0A\u4F20\u51FA\u9519.");
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			//progress.setStatus("文件上传失败.");
			progress.setStatus("\u6587\u4EF6\u4E0A\u4F20\u5931\u8D25.");
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			//progress.setStatus("文件上传出错.");
			progress.setStatus("\u6587\u4EF6\u4E0A\u4F20\u51FA\u9519.");
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			//progress.setStatus("文件上传出错.");
			progress.setStatus("\u6587\u4EF6\u4E0A\u4F20\u51FA\u9519.");
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			//progress.setStatus("文件上传已取消.");
			progress.setStatus("\u6587\u4EF6\u4E0A\u4F20\u5DF2\u53D6\u6D88.");
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			//progress.setStatus("文件上传已停止.");
			progress.setStatus("\u6587\u4EF6\u4E0A\u4F20\u5DF2\u505C\u6B62.");
			break;
		}
	} catch (ex) {
	}
}
