<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType = "text/html;charset=utf-8"%>
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
<style>
*{padding:0px; margin:0px;}
ul,li{ list-style:none;}
.kecheng{ width:270px; border:1px solid #3f5d75;height:auto!important;height:379px;min-height:379px;background:#dcffff;padding:2px;}
.kecheng li{ width:131px; margin:2px; float:left; height:38px; display:inline;}
.kecheng li a{width:129px; border:1px solid #3f6276;text-align:center; height:36px; line-height:36px; background:#b9dbf9; display:block; color:#3f5d75; text-decoration:none; font-size:14px; font-weight:normal;}
.kecheng li a:hover{ color:#000; background:#fff;font-size:14px; font-weight:normal;}
.clear{clear:both; font-size:0px; height:0px;}
</style>
</head>
<body style="background:#dcffff;">
<form name="pageForm" method=post>
<ul class="kecheng">
	<logic:iterate id="model" name="list">
	<li><a href="javascript:toOnclick('<bean:write name="model" property="subjectid"/>')" title='<bean:write name="model" property="subjectname"/>'><bean:write name="model" property="subjectname"/></a></li>
    </logic:iterate>
    <div class="clear"></div>
</ul>
</form>

<script type="text/javascript">
function toOnclick(subjectid){
	document.pageForm.action = 'eduSelectAction.do?method=deal&tag=selectsubject&objid=' + subjectid;
	document.pageForm.submit();
}
</script>
</body>
</html>