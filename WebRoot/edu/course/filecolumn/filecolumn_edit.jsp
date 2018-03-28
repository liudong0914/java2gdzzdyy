<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.wkmk.edu.bo.EduCourseFileColumn" %>
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
<html:form action="/eduCourseFileColumnAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增分类</logic:equal><logic:equal value="updateSave" name="act">修改目录</logic:equal></th>
		</tr>
			<td class="ali03">分类编号：</td>
			<td><input type="text" style="width:90px;" watermark="请输入4位数字" class="validate[required,custom[onlyNumber],length[4,4]]" name="eduCourseFileColumn.columnno" id="eduCourseFileColumn.columnno" value="<bean:write  name="model" property="columnno"/>"</td>
		</tr>
		<tr>
			<td class="ali03">分类名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="eduCourseFileColumn.title" value="<bean:write property="title" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">状&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td class="ali01"><java2code:option name="eduCourseFileColumn.status" codetype="status4" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">是否开放：</td>
			<td class="ali01"><java2code:option name="eduCourseFileColumn.isopen" codetype="boolean" property="isopen" ckname="1" onchange="changeTitle(this.value)"></java2code:option></td>
		</tr>
		<tr id="secondTitleId" <c:if test="${model.isopen == '0' }"> style="display: none;" </c:if>>
			<td class="ali03">开放别名：</td>
			<td class="ali01"><input type="text"  id="eduCourseFileColumn.secondtitle" name="eduCourseFileColumn.secondtitle" value="<bean:write property="secondtitle" name="model"/>"/></td>
		</tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="eduCourseFileColumn.unitid"   value="<bean:write property="unitid"  name="model"/>"/>
<input type="hidden" name="eduCourseFileColumn.filecolumnid"   id="eduCourseFileColumn.filecolumnid" value="<bean:write property="filecolumnid"  name="model"/>"/>

<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseFileColumnActionForm]')){
	  //检查栏目编号是否重复
	  var columnno = document.getElementById('eduCourseFileColumn.columnno').value;
	  if(columnno != '' && columnno != '<bean:write property="columnno" name="model"/>'){
		  var url = '/eduCourseFileColumnAction.do?method=checkColumnno&columnno='+columnno+'&ram=' + Math.random();
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
		  		top.Dialog.alert("分类编号已存在，请更换编号再试!");
		  		return false;
		  	}
	  	}
		
		//检查当开放别名，别名不能为空
		var chkObjs=null; 

		var obj=document.getElementsByName("eduCourseFileColumn.isopen");
		for (var i=0;i<obj.length;i++){ //遍历Radio 
			if(obj[i].checked){ 
			chkObjs=obj[i].value; 
			} 
		} 
		if(chkObjs == "1"){
			var secondTitle= document.getElementById("eduCourseFileColumn.secondtitle").value;
			var secondTitles = secondTitle.replace(/(^\s*)|(\s*$)/g, "");
			if(secondTitles == ""){
				top.Dialog.alert("开放别名不能为空！");
		  		return false;
			}
		}
		
		var objectForm = document.eduCourseFileColumnActionForm;
	  	objectForm.action="eduCourseFileColumnAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.eduCourseFileColumnActionForm.action="/eduCourseFileColumnAction.do?method=list";
  	document.eduCourseFileColumnActionForm.submit();
}
function changeTitle(obj){
	if("0" == obj){
		document.getElementById("secondTitleId").style.display="none";
	}else{
		document.getElementById("secondTitleId").style.display="";
	}
}

</script>
</body>
</html>