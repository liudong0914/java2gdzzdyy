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

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/eduKnopointInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增知识点</logic:equal><logic:equal value="updateSave" name="act">修改知识点</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">知识点编号：</td>
			<td class="ali01"><bean:write property="parentno"  name="model"/><input type="text" style="width:90px;" watermark="请输入4位数字" class="validate[required,custom[onlyNumber],length[4,4]]" name="num" id="num" value="<bean:write name="num"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">知识点名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="eduKnopointInfo.title" value="<bean:write property="title" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		<tr>
			<td class="ali03">类型：</td>
			<td class="ali01"><java2code:option name="eduKnopointInfo.type" codetype="knopointtype" property="type" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="eduKnopointInfo.knopointid" value="<bean:write property="knopointid" name="model"/>"/>
<input type="hidden" name="eduKnopointInfo.kno" value="<bean:write name="model" property="kno"/>"/>
<input type="hidden" name="eduKnopointInfo.status" value="<bean:write name="model" property="status"/>"/>
<input type="hidden" name="eduKnopointInfo.descript" value="<bean:write name="model" property="descript"/>"/>
<input type="hidden" name="eduKnopointInfo.gradetype" value="<bean:write name="model" property="gradetype"/>"/>
<input type="hidden" name="eduKnopointInfo.subjectid" value="<bean:write name="model" property="subjectid"/>"/>
<input type="hidden" name="eduKnopointInfo.updatetime" value="<bean:write name="model" property="updatetime"/>"/>
<input type="hidden" name="eduKnopointInfo.parentno" id="eduKnopointInfo.parentno" value="<bean:write name="model" property="parentno"/>"/>
<input type="hidden" name="eduKnopointInfo.knopointno" id="eduKnopointInfo.knopointno" value="<bean:write name="model" property="knopointno"/>"/>

<input type="hidden" name="title" value="<bean:write name="title"/>" />
<input type="hidden" value="<bean:write name="gradetype"/>" name="gradetype"/>
<input type="hidden" value="<bean:write name="subjectid"/>" name="subjectid"/>
<input type="hidden" name="parentno" value="<bean:write name="parentno"/>" />
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduKnopointInfoActionForm]')){
	  //检查栏目编号是否重复
	  var knopointno = '<bean:write property="parentno"  name="model"/>' + document.getElementById('num').value;
	  if(knopointno != '' && knopointno != '<bean:write property="knopointno" name="model"/>'){
		  var url = '/eduKnopointInfoAction.do?method=checkKnopointno&gradetype=${model.gradetype}&subjectid=${model.subjectid}&knopointno='+knopointno+'&ram=' + Math.random();
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
	
		document.getElementById("eduKnopointInfo.knopointno").value = document.getElementById("eduKnopointInfo.parentno").value + document.getElementById("num").value;
		var objectForm = document.eduKnopointInfoActionForm;
	  	objectForm.action="eduKnopointInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.eduKnopointInfoActionForm.action="/eduKnopointInfoAction.do?method=list";
  	document.eduKnopointInfoActionForm.submit();
}
</script>
</body>
</html>