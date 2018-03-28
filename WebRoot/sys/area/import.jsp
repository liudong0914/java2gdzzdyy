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
<script type="text/javascript">
var FileObj,FileExt;//全局变量
var DenyExt=".xls|.xlsx|"//允许上传格式

function CheckProperty(obj){
  	var ErrMsg="";
  	FileObj=obj;
  	if(obj.value=="")
   	return false;

  	FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
  	if(DenyExt.indexOf(FileExt+"|")==-1)  //判断文件类型是否允许上传
  	{
    	alert("不允许上传"+FileExt+"类型的文件,请上传 "+DenyExt+" 类型的文件。");
    	document.getElementById("uploadfile").value = "";
    	return false;
  	}
  	return true;
}

function CheckUploadFile(theobj){
 	//检测文件属性
 	if (!CheckProperty(theobj)){
    	theobj.focus();
    	document.fileForm.btnsave.disabled=true;
 	}else{
     	document.fileForm.btnsave.disabled=false;
 	}
}

function saveRecord(){
	if(document.getElementById("uploadfile").value == ""){
		alert("请选择文件。");
		return false;
	}
  	obj = document.all("fileForm");
  	obj.action="/sysAreaInfoAction.do?method=importSave";
  	obj.submit();
}
</script>
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<form name="fileForm" method="post" enctype="multipart/form-data">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2">导入地区</th>
		</tr>
		<tr>
			<td class="ali03">选择文件：</td>
			<td class="ali01">
				<input type="file" contenteditable="false" name="thefile" id="uploadfile" keepDefaultStyle="true" style="cursor:hand;background-color:#f5f5f5;color:#777777;" onchange="javascript:CheckUploadFile(this)">
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" id="btnsave" name="btnsave" class="margin_right5" onclick="saveRecord()"><span class="icon_save">导入</span></button>
			</td>
		</tr>
	</table>
</form>
</div>
</div>
</body>
</html>