<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.wkmk.cms.bo.CmsNewsColumn" %>
<%@ page import="java.util.*" %>
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

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/cmsNewsColumnAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增栏目</logic:equal><logic:equal value="updateSave" name="act">修改学科</logic:equal></th>
		</tr>
			<td class="ali03">栏目编号：</td>
			<td><bean:write property="parentno" name="model"/><input type="text" style="width:90px;" watermark="请输入4位数字" class="validate[required,custom[onlyNumber],length[4,4]]" name="num" id="num" value="<bean:write  name="num"/>"</td>
		</tr>
		<tr>
			<td class="ali03">栏目名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="cmsNewsColumn.columnname" value="<bean:write property="columnname" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">状&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td class="ali01"><java2code:option name="cmsNewsColumn.status" codetype="status2" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">缩略图：</td>
			<td class="ali01">
              <img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改照片" width="120" height="90" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
			  <input type="hidden" id="uploadImg" name="cmsNewsColumn.sketch" value='<bean:write property="sketch" name="model"/>'/>
			</td>
		</tr>
		<tr>
            <td  class="ali03">栏目类型：</td>
            <td class="ali01">
              <input type="radio" name="cmsNewsColumn.columntype" value='1' <logic:equal property="columntype" name="model" value="1">checked="checked"</logic:equal> style="vertical-align:middle;">&nbsp;资讯列表&nbsp;&nbsp;
            </td>
          </tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="parentno"   id="parentno" value="<bean:write property="parentno"  name="model"/>"/>
<input type="hidden" name="cmsNewsColumn.parentno"   id="cmsNewsColumn.parentno" value="<bean:write property="parentno"  name="model"/>"/>
<input type="hidden" name="cmsNewsColumn.columnid"  id="cmsNewsColumn.columnid" value="<bean:write property="columnid"  name="model"/>"/>
<input type="hidden" name="cmsNewsColumn.columnno"   id="cmsNewsColumn.columnno" value="<bean:write property="columnno"  name="model"/>"/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=cmsNewsColumnActionForm]')){
	  //检查栏目编号是否重复
	  var columnno = '<bean:write property="parentno"  name="model"/>' + document.getElementById('num').value;
	  if(columnno != '' && columnno != '<bean:write property="columnno" name="model"/>'){
		  var url = '/cmsNewsColumnAction.do?method=checkColumnno&columnno='+columnno+'&ram=' + Math.random();
		  var temp = '';
		  $.ajax({
			  type: 'post',
			  url: url,
			  async: false,//同步请求
			  //data: data,
			  dataType:'text',
			  success: function(data){
			  	temp = data;
			  }
			});
		  	if(temp == '1'){
		  		top.Dialog.alert("知识点编号已存在，请更换编号再试!");
		  		return false;
		  	}
	  	}
	
		document.getElementById("cmsNewsColumn.columnno").value = document.getElementById("cmsNewsColumn.parentno").value + document.getElementById("num").value;
		var objectForm = document.cmsNewsColumnActionForm;
	  	objectForm.action="cmsNewsColumnAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.cmsNewsColumnActionForm.action="/cmsNewsColumnAction.do?method=list";
  	document.cmsNewsColumnActionForm.submit();
}

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=column&pathtype=ID&sketch=true';
	diag.Width = 350;
	diag.Height = 180;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
			var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
			document.getElementById('uploadImgShow').src = "/upload/" + uploadimageurl
			document.getElementById('uploadImg').value = uploadimageurl;
		}
		diag.close();
	};
	diag.show();
}
</script>
</body>
</html>