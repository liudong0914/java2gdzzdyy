<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduGradeInfo"%>
<%@page import="com.wkmk.edu.bo.EduXueduanInfo"%>
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
<html:form action="/eduGradeInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增年级</logic:equal><logic:equal value="updateSave" name="act">修改年级</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">年级名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="eduGradeInfo.gradename" value="<bean:write property="gradename" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">所属学段：</td>
			<td class="ali01">
			<select name="eduGradeInfo.xueduanid" class="validate[required]" onchange="changeCxueduan(this.value)">
				<%
				List xdList = (List)request.getAttribute("xdList");
				EduGradeInfo grade = (EduGradeInfo)request.getAttribute("model");
				EduXueduanInfo xueduan = null;
				for(int i=0, size=xdList.size(); i<size; i++){
					xueduan = (EduXueduanInfo)xdList.get(i);
				%>
				<option <%if(grade.getXueduanid() != null && xueduan.getXueduanid().toString().equals(grade.getXueduanid().toString())){ %>selected="selected"<%} %> value="<%=xueduan.getXueduanid() %>"><%=xueduan.getName() %></option>
				<%} %>
			</select>
			<select name="eduGradeInfo.cxueduanid" id="cxueduanid" class="validate[required]">
				<%
				List cxdList = (List)request.getAttribute("cxdList");
				EduXueduanInfo cxueduan = null;
				for(int i=0, size=cxdList.size(); i<size; i++){
					cxueduan = (EduXueduanInfo)cxdList.get(i);
				%>
				<option <%if(grade.getCxueduanid() != null && cxueduan.getXueduanid().toString().equals(grade.getCxueduanid().toString())){ %>selected="selected"<%} %> value="<%=cxueduan.getXueduanid() %>"><%=cxueduan.getName() %></option>
				<%} %>
			</select>
			<span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01"><java2code:option name="eduGradeInfo.status" codetype="status2" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">排&nbsp;&nbsp;&nbsp;&nbsp;序：</td>
			<td class="ali01" colspan="2"><input type="text" class="validate[required,onlyNumber]" name="eduGradeInfo.orderindex" value="<bean:write property="orderindex" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="eduGradeInfo.gradeid" value="<bean:write property="gradeid" name="model"/>"/>
<input type="hidden" name="eduGradeInfo.subjectid" value="<bean:write property="subjectid" name="model"/>"/>

<input type="hidden" name="gradename" value='<bean:write name="gradename"/>'/>
<input type="hidden" name="subjectid" value='<bean:write name="subjectid"/>'/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduGradeInfoActionForm]')){
		var objectForm = document.eduGradeInfoActionForm;
	  	objectForm.action="eduGradeInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function changeCxueduan(xueduanid){
	var url = 'eduXueduanInfoAction.do?method=getCxueduanSelect&xueduanid=' + xueduanid;
	var result = '';
	$.ajax({
	  type: 'post',
	  url: url,
	  async: false,//同步请求
	  //data: data,
	  dataType:'text',
	  success: function(data){
	  	result = data;
	  }
	});
	document.getElementById('cxueduanid').innerHTML = result;
	$("#cxueduanid").render();//刷新下拉框
}

function backRecord(){
  	document.eduGradeInfoActionForm.action="/eduGradeInfoAction.do?method=list";
  	document.eduGradeInfoActionForm.submit();
}
</script>
</body>
</html>