<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<base target="_self"/>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 上传文件列表start -->
<script type="text/javascript" src="../../libs/js/form/upload/fileUpload.js"></script>
<!-- 上传文件列表end -->
<body>
<div style="height:12px;"></div>
<div>
<form method="post" enctype="multipart/form-data" name="imageForm">
<table class="tableStyle" formMode="line" align="center">
	<tr>
		<td class="ali03">预览图片：</td>
		<td class="ali01"><img src="/libs/images/sk/preview_img.gif" width="180" height="70" border="1" id="previewimg"/></td>
	</tr>
	<tr>
		<td class="ali03">选择图片：</td>
		<td class="ali01"><input type="file" contenteditable="false" keepDefaultStyle="true" name="thefile" onchange="javascript:CheckUploadFile(this)"/></td>
	</tr>
	<%-- 
	<!-- google浏览器处理 -->
	<script type="text/javascript">
        var Sys = {};
        var ua = navigator.userAgent.toLowerCase();
        if (window.MessageEvent && !document.getBoxObjectFor)
            Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]
        
        if(Sys.chrome){
        	document.write('<tr>\n');
        	document.write('<td class="ali03">选择图片：</td>\n');
        	document.write('<td class="ali01"><input type="file" contenteditable="false" keepDefaultStyle="true" name="thefile" onchange="javascript:CheckUploadFile(this)"/></td>\n');
        	document.write('</tr>\n');
        }else{
        	document.write('<tr>\n');
        	document.write('<td class="ali03">选择图片：</td>\n');
        	document.write('<td class="ali01"><input type="file" contenteditable="false" name="thefile" onchange="javascript:CheckUploadFile(this)"/></td>\n');
        	document.write('</tr>\n');
        }
    </script>
	<!-- google浏览器处理 -->
	--%>
	<tr>
		<td colspan="2">
			<button type="button" name="btnsave" disabled="disabled" class="margin_right5" onclick="uploadImage()">上载图片</button>
			<button type="button" onclick="top.Dialog.close();">关闭窗口</button>
		</td>
	</tr>
</table>
<input type="hidden" name="savepath" value="<bean:write name="savepath"/>"/>
<input type="hidden" name="pathtype" value="<bean:write name="pathtype"/>"/>
<input type="hidden" name="filename" value="<bean:write name="filename"/>"/>
<input type="hidden" name="filesize" value="<bean:write name="filesize"/>"/>
<input type="hidden" name="sketch" value="<bean:write name="sketch"/>"/>
<input type="hidden" name="swidth" value="<bean:write name="swidth"/>"/>
<input type="hidden" name="sheight" value="<bean:write name="sheight"/>"/>
</form>
</div>
<script type="text/javascript">
var ImgObj = new Image();//建立一个图像对象
var FileObj,ImgFileSize,ImgWidth,ImgHeight,FileExt;//全局变量 图片相关属性

var AllImgExt=".jpg|.jpeg|.gif|.png|"//全部图片格式类型
var AllowImgFileSize=parseInt('<%=request.getAttribute("filesize") %>');//允许上传图片文件的大小  单位：KB
var AllowImgWidth=1400;//允许上传的图片的宽度　单位：px(像素)
var AllowImgHeight=2000;//允许上传的图片的高度　单位：px(像素)

function CheckProperty(obj)//检测图像属性
{
    var ErrMsg="";
  	FileObj=obj;
  	if(obj.value=="")
   		return false;

  	FileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
  	if(AllImgExt!=0&&AllImgExt.indexOf(FileExt+"|")==-1)  //判断文件类型是否允许上传
  	{
    	top.Dialog.alert("不允许上传"+FileExt+"类型的文件,请上传 "+AllImgExt+" 类型的文件。");
    	return false;
  	}
  	else{
    	ImgObj.src=obj.value;
    	FileObj=obj;
    	if(ImgObj.readyState!="complete")  //如果图像是未加载完成进行循环检测
    	{
     		setTimeout("CheckProperty(FileObj)",500);
    	}

    	ImgFileSize=Math.round(ImgObj.fileSize/1024*100)/100;//取得图片文件的大小
    	ImgWidth=ImgObj.width      //取得图片的宽度
    	ImgHeight=ImgObj.height;    //取得图片的高度

    	if(AllowImgWidth!=0&&AllowImgWidth<ImgWidth)
      		ErrMsg+="\n图片宽度超过限制。请上传宽度小于"+AllowImgWidth+"px的文件，当前图片宽度为"+ImgWidth+"px";

    	if(AllowImgHeight!=0&&AllowImgHeight<ImgHeight)
      		ErrMsg+="\n图片高度超过限制。请上传高度小于"+AllowImgHeight+"px的文件，当前图片高度为"+ImgHeight+"px";

    	if(AllowImgFileSize!=0&&AllowImgFileSize<ImgFileSize)
      		ErrMsg+="\n图片文件大小超过限制。请上传小于"+AllowImgFileSize+"KB的文件，当前文件大小为"+ImgFileSize+"KB";

    	if(ErrMsg!=""){
      		top.Dialog.alert(ErrMsg);
      		return false;
    	}
    	else{
      		return true;
    	}
  	}
}

ImgObj.onerror=function(){
  	ErrMsg='\n图片格式不正确或者图片已损坏!';
}

function CheckUploadFile(theobj){
	//检测文件属性
	if (!CheckProperty(theobj)){
    	theobj.focus();
    	document.imageForm.btnsave.disabled=true;
	}
	else{
     	//预览
     	//document.getElementById('previewimg').src = theobj.value;
     	//alert(document.getElementById('previewimg').src);
     	document.imageForm.btnsave.disabled=false;
  	}
}

function uploadImage(){
    document.imageForm.action="/sysImageUploadAction.do?method=uploadimagedeal";
    document.imageForm.submit();
}

$(function(){
	<%
	String uploadimageerror = (String)request.getAttribute("uploadimageerror");
	if(uploadimageerror != null && !"".equals(uploadimageerror)){
	%>
		top.Dialog.alert('<%=uploadimageerror %>');
	<%}%>
})
</script>
</body>
</html>