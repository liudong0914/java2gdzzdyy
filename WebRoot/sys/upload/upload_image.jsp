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
		<td class="ali01"><div id="previewimgdiv"><img src="/libs/images/sk/preview_img.gif" width="180" height="70" border="1" id="previewimg"/></div></td>
	</tr>
	<tr>
		<td class="ali03">选择图片：</td>
		<td class="ali01">
			<input type="file" contenteditable="false" keepDefaultStyle="true" name="thefile" onchange="javascript:onUploadImgChange(this)"/>
			<img id="previewimgsize"/>
		</td>
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
<style type="text/css">
#previewimgdiv{ /* 该对象用于在IE下显示预览图片 */
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);
}
#previewimgsize{ /* 该对象只用来在IE下获得图片的原始尺寸，无其它用途 */
    filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);    
    visibility:hidden;
}
#previewimg{ /* 该对象用于在FF下显示预览图片 */
    display: block;
    width:180px;
    height:70px;
}
</style>
<script type="text/javascript">
var AllImgExt=".jpg|.jpeg|.gif|.png|"//全部图片格式类型
var AllowImgFileSize=parseInt('<%=request.getAttribute("filesize") %>');//允许上传图片文件的大小  单位：KB

//上传文件
function onUploadImgChange(sender){
    //单纯只为了文件名
    var filePath = sender.value;
    var fileExt = filePath.substring(filePath.lastIndexOf(".")).toLowerCase();
    if(!checkFileExt(fileExt)){
        top.Dialog.alert("您上传的文件不是图片,请上传 "+AllImgExt+" 类型的文件。");
        sender.value = '';
        return;
    }
    var objPreview = document.getElementById('previewimg');
        objPreviewFake = document.getElementById('previewimgdiv'),
        objPreviewSizeFake = document.getElementById('previewimgsize');
    try{
        //先采用HTML5方法
        if(typeof FileReader !== 'undefined'){
            var file = sender.files[0];
            var fileSize = file.fileSize || file.size;
            if(checkFileSize(fileSize)){
                var reader = new FileReader();
                reader.readAsDataURL(file);
                reader.onload = function(e){
                    objPreview.src = this.result;
                }
            }
        }else if(sender.files){
            //非IE，不支持HTML5方法
            var file = sender.files[0];            
            fileSize = file.fileSize;
            if(checkFileSize(fileSize)){
                filePath = file.getAsDataURL();
                objPreview.src = filePath;
            }

        }else if( objPreviewFake.filters ){
            // IE7,IE8 在设置本地图片地址为 img.src 时出现莫名其妙的后果
            //（相同环境有时能显示，有时不显示），因此只能用滤镜来解决
            // IE7, IE8因安全性问题已无法直接通过 input[file].value 获取完整的文件路径
            sender.select();
            var imgSrc = document.selection.createRange().text;
            objPreviewFake.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = imgSrc;
            objPreviewSizeFake.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = imgSrc;    
            autoSizePreview( objPreviewFake,objPreviewSizeFake.offsetWidth, objPreviewSizeFake.offsetHeight );
            objPreview.style.display = 'none';
            //读取图片文件大小        
            var sh = setInterval(function(){
                    var img = document.createElement("img");
                    img.src = imgSrc;
                    fileSize = img.fileSize;
                    if (fileSize > 0){
                        checkFileSize(fileSize);
                        clearInterval(sh);
                    }
                    img = null;
                }
            ,100);
        }
        document.imageForm.btnsave.disabled=false;
    }catch(e){
         //alert("调用后台方法");
    }    
}
//检测文件类型
function checkFileExt(ext){
    if (AllImgExt!=0&&AllImgExt.indexOf(ext+"|")==-1) {
        return false;
    }
    return true;
}
//检测文件大小
function checkFileSize(fileSize){
    if(fileSize > AllowImgFileSize*1000){
        top.Dialog.alert("图片文件大小超过限制，请上传小于"+AllowImgFileSize+"KB的文件。");
        return false;
    }
    return true;
}
//居中显示
function autoSizePreview( objPre, originalWidth, originalHeight ){
    var zoomParam = clacImgZoomParam(180, 70, originalWidth, originalHeight );
    objPre.style.width = zoomParam.width + 'px';
    objPre.style.height = zoomParam.height + 'px';
    objPre.style.marginTop = zoomParam.top + 'px';
    objPre.style.marginLeft = zoomParam.left + 'px';
}
//图像缩放
function clacImgZoomParam( maxWidth, maxHeight, width, height ){
    var param = { width:width, height:height, top:0, left:0 };
    if( width>maxWidth || height>maxHeight ){
        rateWidth = width / maxWidth;
        rateHeight = height / maxHeight;
        
        if( rateWidth > rateHeight ){
            param.width =  maxWidth;
            param.height = height / rateWidth;
        }else{
            param.width = width / rateHeight;
            param.height = maxHeight;
        }
    }
    param.left = (maxWidth - param.width) / 2;
    param.top = (maxHeight - param.height) / 2;
    return param;
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