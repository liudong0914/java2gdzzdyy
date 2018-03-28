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
</head>
<body>
<div>
<div class="box1" position="center">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">二维码生成工具</th>
		</tr>
		<tr>
			<td class="ali03">二维码内容：</td>
			<td class="ali01">
				<textarea style="width:360px;height:80px;float:left;margin-right:10px;" placeholder="请输入生成二维码的地址内容" name="linkurl" id="linkurl"></textarea>
				<a href="javascript:;" onclick="saveRecord()"><span class="icon_add">生成二维码</span></a>
			</td>
		</tr>
		<tr>
			<td class="ali03">二维码图片：</td>
			<td class="ali01">
				<a href="javascript:downloadQrcode()" title="点击下载二维码图片"><img src="" width="200" height="200" id="qrcodeimg"/></a>
				<a href="javascript:downloadQrcode()">下载二维码图片</a>
				<input type="hidden" name="qrcodepath" id="qrcodepath" value=""/>
			</td>
		</tr>
	</table>
</div>
</div>
<script>
function saveRecord(){
	var linkurl = document.getElementById("linkurl").value;
	if(linkurl == ""){
		top.Dialog.alert("请填写二维码内容!");
		return false;
	}
	
	var url = '/qrcodeToolsAction.do?method=getQrcodeUrl&linkurl='+linkurl+'&ram=' + Math.random();
  	$.ajax({
	  type: 'post',
	  url: url,
	  async: false,
	  dataType:'text',
	  success: function(data){
	  	if(data != ""){
	  	  document.getElementById("qrcodeimg").src = data;
	  	  document.getElementById("qrcodepath").value = data;
	  	}
	  }
	});
}
function downloadQrcode(){
	var qrcodepath = document.getElementById("qrcodepath").value;
	if(qrcodepath != ""){
		window.location.href = "/qrcodeToolsAction.do?method=downloadQrcode&qrcodepath=" + qrcodepath;
	}
}
</script>
</body>
</html>