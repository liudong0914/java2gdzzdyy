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
<logic:present name="jumpurl">
<meta http-equiv="refresh" content="5;url='<%=request.getAttribute("jumpurl")%>'">
</logic:present>
</head>
<body>
<div id="scrollContent">
	<div class="box1" panelWidth="450" position="center">
		<div class="msg_icon4"><span>操作提示</span></div>
		<div class="padding_left50 padding_right15 padding_top20 minHeight_100 font_14 font_bold ali02" style="color:green;font-size:16px;">
		   保存试卷成功！
		</div>
	</div>
</div>
</body>
</html>