<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType = "text/html;charset=utf-8"%>
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
</head>
<body>
<input type="hidden" name="uploadimageurl" id="uploadimageurl" value="<%=(String)request.getAttribute("uploadimagefile")%>"/>
</body>
<script>
$(function(){
	//var imageurl = '<%--=(String)request.getAttribute("uploadimagefile")--%>';
	//top.frmright.document.getElementById('uploadImgShow').src = "/upload/" + imageurl;
	//top.frmright.document.getElementById('uploadImg').value = imageurl;
	top.Dialog.close();
})
</script>
</html>