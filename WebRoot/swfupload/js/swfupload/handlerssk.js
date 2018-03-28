function fileQueueError(file, errorCode, message) {
	try {
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			alert("�ϴ��ļ��������ܳ���"+swfu.settings.file_upload_limit+"��.");
			return;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			alert("�ϴ��ļ���СΪ0�ֽ�");
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			alert("�ϴ��ļ���С���ܳ���"+swfu.settings.file_size_limit);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			alert("�������ϴ�"+file.type+"���͵��ļ�");
			break;
		default:
			alert(message);
			break;
		}
	} catch (ex) {
		this.debug(ex);
	}
}

/**
 * ���ļ�ѡ��Ի���ر���ʧʱ�����ѡ����ļ��ɹ������ϴ����У�
 * ��ô���ÿ���ɹ�������ļ����ᴥ��һ�θ��¼���N���ļ��ɹ�������У��ʹ���N�δ��¼�����
 * @param {} file
 * id : string,			    // SWFUpload���Ƶ��ļ���id,ͨ��ָ����id���������ļ����ϴ����˳��ϴ���
 * index : number,			// �ļ���ѡ���ļ����У����������˳����Ŷӵ��ļ����е�������getFile��ʹ�ô�����
 * name : string,			// �ļ������������ļ���·����
 * size : number,			// �ļ��ֽ���
 * type : string,			// �ͻ��˲���ϵͳ���õ��ļ�����
 * creationdate : Date,		// �ļ��Ĵ���ʱ��
 * modificationdate : Date,	// �ļ�������޸�ʱ��
 * filestatus : number		// �ļ��ĵ�ǰ״̬����Ӧ��״̬����ɲ鿴SWFUpload.FILE_STATUS }
 */
function fileQueued(file){
	addReadyFileInfo(file);
}
function fileDialogComplete(numFilesSelected, numFilesQueued) {

}

function uploadProgress(file, bytesLoaded) {
	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);
		var progress = new FileProgress(file);
		if(percent > 100){
			percent = 100;
		}
		progress.setProgress(percent);
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file);
		progress.setComplete();
		var fileId = file.id;
		document.getElementById(fileId+"_filesize").value = file.size;
		document.getElementById(fileId+"_filename").value = file.name;
		document.getElementById(fileId+"_filepath").value = serverData;
		
		if(serverData.indexOf("exist_") != -1){
			document.getElementById(fileId).cells(0).innerHTML = document.getElementById(fileId).cells(0).innerHTML + "&nbsp;<font color='red'>(���ĵ��Ѿ��ϴ���!)</font>";
		}
	} catch (ex) {
		this.debug(ex);
	}
}


function addReadyFileInfo(file){
	//�ñ����ʾ
	var infoTable = document.getElementById("infoTable");
	var row = infoTable.insertRow(infoTable.rows.length);
	row.height="30px;";
	var fileid = file.id;
	row.id = fileid;
	row.style.background = "#ffffff";
	var size = file.size;
	var strsize = "";
	if(size <= 1024){
		strsize = parseInt(size) + " B";
	}else if(size <= 1024*1024){
		size = size/1024;
		strsize = parseInt(size) + " K";
	}else if(size <= 1024*1024*1024){
		size = size/1024;
		size = size/1024;
		strsize = parseInt(size) + " M";
	}else if(size <= 1024*1024*1024*1024){
		size = size/1024;
		size = size/1024;
		size = size/1024;
		strsize = parseInt(size) + " G";
	}
	var col1 = row.insertCell(0);
	var col2 = row.insertCell(1);
	var col3 = row.insertCell(2);
	var col4 = row.insertCell(3);
	col1.align="left";
	col2.align="center";
	col3.align="center";
	col4.align="center";
	
	col1.innerHTML = "&nbsp;&nbsp;" + file.name;
	col2.innerHTML = strsize + "<input type='hidden' id='"+fileid+"_filesize' name='filesize' value=''/><input type='hidden' id='"+fileid+"_filename' name='filename' value=''/><input type='hidden' id='"+fileid+"_filepath' name='filepath' value=''/>";
	col3.innerHTML = "<div class=jd1><span class=jd2><div id=jd_"+fileid+" class=jd3></div></span><span id=jdz_"+fileid+" class=jd4>0%</spanv></div>";
	col4.innerHTML = "<a href='javascript:deleteFile(\""+fileid+"\")'>ɾ��</a>";
	
	document.getElementById("td_btnsc").style.display = "";
	document.getElementById("td_btnxz").width = "50%";
	document.getElementById("td_btnxz").align = "right";
	var imgurl = "/public/images/photo/btn_01.jpg";
	swfu.settings.button_image_url = imgurl;
	swfu.callFlash("SetButtonImageURL", [imgurl]);
}

function deleteFile(fileId){
	//�ñ����ʾ
	var infoTable = document.getElementById("infoTable");
	var row = document.getElementById(fileId);
	infoTable.deleteRow(row.rowIndex);
	swfu.cancelUpload(fileId,false);
	
	if(infoTable.rows.length == 1){
		document.getElementById("td_btnxz").width = "100%";
		document.getElementById("td_btnxz").align = "center";
		document.getElementById("td_btnsc").style.display = "none";
		var imgurl = "/public/images/photo/btn_02.jpg";
		swfu.settings.button_image_url = imgurl;
		swfu.callFlash("SetButtonImageURL", [imgurl]);
	}
}

function uploadComplete(file) {
	try {
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			//�����ϴ����
			document.getElementById("tx_word").style.display = "block";
			document.getElementById("notice").style.display = "none";
			var objtable = document.getElementById("table_uploadbtn");
			var ronum = objtable.rows.length;
			for(var i=0;i<ronum;i++){
				objtable.deleteRow(objtable.rows.length-1);
			}
			//document.getElementById("table_uploadbtn").style.display = "none";
			
			
			var infoTable = document.getElementById("infoTable");
			var num = infoTable.rows.length;
			s_rownum = num-1;
			document.uploadform.rownum.value = s_rownum;
			for(var i=1;i<num;i++){
				var row = infoTable.rows[i];
				row.cells[3].innerHTML = "<a href='#box_"+i+"' onclick=doOpenBox('"+i+"') >�༭</a>";
				
				var filename = "";
				if(num == 2){
					filename = document.uploadform.filename.value;
				}else{
					filename = document.uploadform.filename[i-1].value;
				}
				var norder = parseInt(orderindex) + parseInt(i);
				
				var div = document.getElementById("tbox");
				var vdiv=document.createElement("div");
				vdiv.id="docListWrap";
				var value = "<div id='doc_title_name'><div id='doc_sort_name'>"+filename+"</div><div id='showOrHide'><div id='t_"+i+
				"' onclick=doOpen('"+i+"')  style='cursor:pointer;'>";
				if(i==1){
					value += "<img src='/public/images/photo/z01.gif' style='display:inline;' /> ����";
				}else{
					value += "<img src='/public/images/photo/z05.gif' style='display:inline;' /> չ��";
				}
				value += "</div></div></div><div id='box_"+i+"' ";
				if(i!=1){
					value += "style='display:none;'";
				}
				value += "><div id='toFixDoc'>"+
				"<dl><dt><span style='font-weight:normal; color:#FF0000; display:inline;'>*</span>���⣺</dt><dd><input id=title_"+i+" name='title' type='text' CK_NAME='����' CK_TYPE='NotEmpty' value='"+filename.substring(0,filename.lastIndexOf("."))+"'  style='width:360px; height:25px; line-height:25px;' maxlength='25'/></dd></dl>"+
				"<dl><dt><span style='font-weight:normal; color:#FF0000; display:inline;'>*</span>����ţ�</dt><dd><input id=index_"+i+" name='orderindex' type='text' CK_NAME='�����' CK_TYPE='NotEmpty,Number' value='"+norder+"'  style='width:100px; height:25px; line-height:25px;' maxlength='25'/></dd></dl>"+
				"<dl><dt>��Ƭ��飺</dt><dd><textarea name='descript' style='width:360px; height:100px;'>"+filename.substring(0,filename.lastIndexOf("."))+"</textarea></dd></dl>";
				if(i == 1 && num > 2){
					value += "<div id='docfixBtn'><input type='image' name='imageField2' src='/public/images/photo/z07.jpg' style='width:150px;height:30px;' onclick='doCopyAll();'/></div>";
				}
				value += "</div></div>"
				vdiv.innerHTML = value;
				div.appendChild(vdiv);
			}
			
			
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadDone() {
}

function uploadError(file, errorCode, message) {
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			//ȡ���ϴ�
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			//ֹͣ�ϴ�
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			alert("�ϴ��ļ�����");
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			alert("�ϴ��ļ��������ܳ���"+swfu.settings.file_upload_limit+"��!");
			break;
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			alert("�������HTTP");
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			alert("��������������ֹ");
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			alert("�������,����ȫ����");
			break;
		default:
			alert("�ļ��ϴ�����.");
			break;
		}
	} catch (ex3) {
		this.debug(ex3);
	}

}


function addImage(src) {
	var newImg = document.createElement("img");
	newImg.style.margin = "5px";

	document.getElementById("thumbnails").appendChild(newImg);
	if (newImg.filters) {
		try {
			newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
		} catch (e) {
			// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
			newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
		}
	} else {
		newImg.style.opacity = 0;
	}

	newImg.onload = function () {
		fadeIn(newImg, 0);
	};
	newImg.src = src;
}

function fadeIn(element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30;	// 15 fps


	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}

		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}

	if (opacity < 100) {
		setTimeout(function () {
			fadeIn(element, opacity);
		}, rate);
	}
}



/* ******************************************
 *	FileProgress Object
 *	Control object for displaying file info
 * ****************************************** */

function FileProgress(file) {
	//this.fileProgressID = "divFileProgress";

	//this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	//if (!this.fileProgressWrapper) {
	//	this.fileProgressWrapper = document.createElement("div");
	//	this.fileProgressWrapper.className = "progressWrapper";
	//	this.fileProgressWrapper.id = this.fileProgressID;

	//	this.fileProgressElement = document.createElement("div");
	//	this.fileProgressElement.className = "progressContainer";

	//	var progressCancel = document.createElement("a");
	//	progressCancel.className = "progressCancel";
	//	progressCancel.href = "#";
	//	progressCancel.style.visibility = "hidden";
	//	progressCancel.appendChild(document.createTextNode(" "));

	//	var progressText = document.createElement("div");
	//	progressText.className = "progressName";
	//	progressText.appendChild(document.createTextNode(""));

	//	var progressBar = document.createElement("div");
	//	progressBar.className = "progressBarInProgress";

	//	var progressStatus = document.createElement("div");
	//	progressStatus.className = "progressBarStatus";
	//	progressStatus.innerHTML = "&nbsp;";

	//	this.fileProgressElement.appendChild(progressCancel);
	//	this.fileProgressElement.appendChild(progressText);
	//	this.fileProgressElement.appendChild(progressStatus);
	//	this.fileProgressElement.appendChild(progressBar);

	//	this.fileProgressWrapper.appendChild(this.fileProgressElement);
	//	document.getElementById(targetID).style.height = "75px";
	//	document.getElementById(targetID).appendChild(this.fileProgressWrapper);
	//	fadeIn(this.fileProgressWrapper, 0);

	//} else {
	//	this.fileProgressElement = this.fileProgressWrapper.firstChild;
	//	this.fileProgressElement.childNodes[1].firstChild.nodeValue = "";
	//}

	//this.height = this.fileProgressWrapper.offsetHeight;
	
	this.fileProgressElement = document.getElementById("jd_"+file.id);
	this.fileProgressElement1 = document.getElementById("jdz_"+file.id);
	this.fileProgressElement2 = document.getElementById(file.id).cells[2];
}
FileProgress.prototype.setProgress = function (percentage) {
	//this.fileProgressElement.className = "progressContainer green";
	//this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	//this.fileProgressElement.childNodes[3].style.width = percentage + "%";
	this.fileProgressElement.style.width = percentage + "%";
	this.fileProgressElement1.innerHTML = percentage + "%";
};
FileProgress.prototype.setComplete = function () {
	//this.fileProgressElement.className = "progressContainer blue";
	//this.fileProgressElement.childNodes[3].className = "progressBarComplete";
	//this.fileProgressElement.childNodes[3].style.width = "";
	this.fileProgressElement2.innerHTML = "���";

};
FileProgress.prototype.setError = function () {
	//this.fileProgressElement.className = "progressContainer red";
	//this.fileProgressElement.childNodes[3].className = "progressBarError";
	//this.fileProgressElement.childNodes[3].style.width = "";
	this.fileProgressElement2.innerHTML = "�ϴ�����";

};
FileProgress.prototype.setCancelled = function () {
	//this.fileProgressElement.className = "progressContainer";
	//this.fileProgressElement.childNodes[3].className = "progressBarError";
	//this.fileProgressElement.childNodes[3].style.width = "";
	this.fileProgressElement2.innerHTML = "�ϴ�ȡ��";

};
FileProgress.prototype.setStatus = function (status) {
	//this.fileProgressElement.childNodes[2].innerHTML = status;
	this.fileProgressElement2.innerHTML = status;
};

FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};
