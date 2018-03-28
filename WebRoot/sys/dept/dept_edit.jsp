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
<html:form action="/sysUnitDeptAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增部门</logic:equal><logic:equal value="updateSave" name="act">修改部门</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">部门编号：</td>
			<td class="ali01"><bean:write property="parentno"  name="model"/><input type="text" style="width:90px;" watermark="请输入4位数字" class="validate[required,custom[onlyNumber],length[4,4]]" name="deptnum" id="deptnum" value="<bean:write name="deptnum"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">部门名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="sysUnitDept.deptname" value="<bean:write property="deptname" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		<tr>
			<td class="ali03">联系电话：</td>
			<td class="ali01"><input type="text" name="sysUnitDept.telephone" value="<bean:write property="telephone" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">传真：</td>
			<td class="ali01"><input type="text" name="sysUnitDept.fax" value="<bean:write property="fax" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">部门领导：</td>
			<td class="ali01"><input type="text" name="sysUnitDept.leader" value="<bean:write property="leader" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01"><java2code:option name="sysUnitDept.status" codetype="status2" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysUnitDept.deptid" value="<bean:write property="deptid" name="model"/>"/>
<input type="hidden" name="sysUnitDept.unitid" value="<bean:write name="model" property="unitid"/>"/>
<input type="hidden" name="sysUnitDept.parentno" id="sysUnitDept.parentno" value="<bean:write name="model" property="parentno"/>"/>
<input type="hidden" name="sysUnitDept.deptno" id="sysUnitDept.deptno" value="<bean:write name="model" property="deptno"/>"/>

<input type="hidden" name="deptname" value="<bean:write name="deptname"/>" />
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
	if(validateForm('form[name=sysUnitDeptActionForm]')){
	  //检查栏目编号是否重复
	  var deptno = '<bean:write property="parentno"  name="model"/>' + document.getElementById('deptnum').value;
	  if(deptno != '' && deptno != '<bean:write property="deptno" name="model"/>'){
		  var url = '/sysUnitDeptAction.do?method=checkDeptno&deptno='+deptno+'&ram=' + Math.random();
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
		  		top.Dialog.alert("部门编号已存在，请更换编号再试!");
		  		return false;
		  	}
	  	}
	
		document.getElementById("sysUnitDept.deptno").value = document.getElementById("sysUnitDept.parentno").value + document.getElementById("deptnum").value;
		var objectForm = document.sysUnitDeptActionForm;
	  	objectForm.action="sysUnitDeptAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.sysUnitDeptActionForm.action="/sysUnitDeptAction.do?method=list";
  	document.sysUnitDeptActionForm.submit();
}
</script>
</body>
</html>