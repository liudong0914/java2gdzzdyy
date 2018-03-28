<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<!--框架必需start-->
<script type="text/javascript" src="/libs/js/framework.js"></script>
<link href="/libs/css/framework/form.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="/libs/js/form/validation.js" type="text/javascript"></script>
<script src="/libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<!--弹窗end-->
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>

<body style="background:#fcfcfc;">
<html:form action="/courseManager.do?method=addSaveColumn" method="post">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
		<div class="form-row form-row01">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				编号：
				</label>
			</div>
			<div class="input">
				${parentno }<input id="num" keepDefaultStyle="true" class="validate[required,custom[onlyNumber],length[4,4]] ipt-text ipt-text01 formEleToVali" name="num" value="${num }" type="text" style="width:120px;" placeholder="请输入4位数字编号">
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				目录名称：
				</label>
			</div>
			<div class="input">
				<input id="eduCourseColumn.title" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="eduCourseColumn.title" value="${model.title }" style="width:190px;" type="text">
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				状态：
				</label>
			</div>
			<div class="input">
				<java2code:option name="eduCourseColumn.status" codetype="status4" property="status" ckname="1"></java2code:option>
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				是否开放：
				</label>
			</div>
			<div class="input">
				<java2code:option name="eduCourseColumn.isopen" codetype="boolean" property="isopen" ckname="1" onchange="changeTitle(this.value)"></java2code:option>
			</div>
		</div>
		<div class="form-row" id="secondTitleId" <c:if test="${model.isopen == '0' }"> style="display: none;"  </c:if>>
			<div class="label">
				<label>
				开放别名：
				</label>
			</div>
			<div class="input">
				<input id="eduCourseColumn.secondTitle" keepDefaultStyle="true" class="ipt-text formEleToVali" name="eduCourseColumn.secondTitle" value="${model.secondTitle }" style="width:190px;" type="text">
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				外部链接：
				</label>
			</div>
			<div class="input">
				<input id="eduCourseColumn.linkurl" keepDefaultStyle="true" class="ipt-text formEleToVali" name="eduCourseColumn.linkurl" value="${model.linkurl }" style="width:320px;" type="text">
			</div>
		</div>
	<div class="clearfix mt20" style="text-align:center;">
		<logic:notEqual value="1" name="eduCourseInfo" property="status">
		<%
		String isAuhtor = (String)session.getAttribute("isAuhtor");
		Map moduleidMap = (Map)session.getAttribute("moduleidMap");
		String moduleidType = (String)moduleidMap.get("2");
		if("1".equals(isAuhtor) || "2".equals(moduleidType)){
		%>
		<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" style="display:inline-block;" type="button">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<%} %>
		</logic:notEqual>
		<input class="btn btn-pop ml20" value="返回" onclick="goBack()" type="button">
	</div>
	</div>
</div>
<input type="hidden" name="eduCourseColumn.courseid" value="<bean:write property="courseid" name="model"/>"/>
<input type="hidden" name="eduCourseColumn.unitid" value="<bean:write property="unitid" name="model"/>"/>
<input type="hidden" name="eduCourseColumn.parentno" id="eduCourseColumn.parentno" value="<bean:write property="parentno" name="model"/>"/>
<input type="hidden" name="eduCourseColumn.columnid" value="<bean:write property="columnid" name="model"/>"/>
<input type="hidden" name="eduCourseColumn.columnno" id="eduCourseColumn.columnno" value="<bean:write property="columnno" name="model"/>"/>
		
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
<input type="hidden" name="parentno" id="parentno" value="<bean:write name="parentno"/>"/>
</html:form>

</body>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseColumnActionForm]')){
		//检查栏目编号是否重复
	  	var columnno = '<bean:write property="parentno" name="model"/>' + document.getElementById('num').value;
	  	if(columnno != '' && columnno != '<bean:write property="columnno" name="model"/>'){
		  	var url = '/eduCourseColumnAction.do?method=checkColumnno&courseid=${courseid }&columnno='+columnno+'&ram=' + Math.random();
		  	var temp = '';
		  	$.ajax({
			  type: 'post',
			  url: url,
			  async: false,//同步请求
			  dataType:'text',
			  success: function(data){
			  	temp = data;
			  }
			});
		  	if(temp == '1'){
		  		top.Dialog.alert("目录编号已存在，请更换编号再试!");
		  		return false;
		  	}
	  	}
		document.getElementById("eduCourseColumn.columnno").value = document.getElementById("eduCourseColumn.parentno").value + document.getElementById("num").value;
		//检查当开放别名，别名不能为空
		var chkObjs=null; 

		var obj=document.getElementsByName("eduCourseColumn.isopen");
		for (var i=0;i<obj.length;i++){ //遍历Radio 
			if(obj[i].checked){ 
			chkObjs=obj[i].value; 
			} 
		} 
		if(chkObjs == "1"){
			var secondTitle= document.getElementById("eduCourseColumn.secondTitle").value;
			var secondTitles = secondTitle.replace(/(^\s*)|(\s*$)/g, "");
			if(secondTitles == ""){
				top.Dialog.alert("开放别名不能为空！");
		  		return false;
			}
		}
		var objectForm = document.eduCourseColumnActionForm;
	  	objectForm.action="/courseManager.do?method=${act }";
	  	objectForm.submit();
	}
}
function goBack(){
	var objectForm = document.eduCourseColumnActionForm;
  	objectForm.action="/courseManager.do?method=columnList";
  	objectForm.submit();
}
function changeTitle(obj){
	if("0" == obj){
		document.getElementById("secondTitleId").style.display="none";
	}else{
		document.getElementById("secondTitleId").style.display="";
	}
}
</script>
</html>
