<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.wkmk.edu.bo.EduCourseClass" %>
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

<!-- 日期控件start -->
<script type="text/javascript" src="../../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%
EduCourseClass ecc = (EduCourseClass)request.getAttribute("model");
%>
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/eduCourseClassAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增批次</logic:equal><logic:equal value="updateSave" name="act">修改批次</logic:equal></th>
		<tr>
			<td class="ali03">批次名称：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="eduCourseClass.classname" value="<bean:write property="classname" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">开始日期：</td>
			<td class="ali01">
				<input type="text" style="width: 150px;" name="eduCourseClass.startdate" readonly="readonly" value="<bean:write property="startdate"  name="model"/>" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td class="ali03">介绍日期：</td>
			<td class="ali01">
				<input type="text" style="width: 150px;" name="eduCourseClass.enddate" readonly="readonly" value="<bean:write property="enddate"  name="model"/>" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
		<logic:equal value="updateSave" name="act">
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01">
				<input type="radio" name="eduCourseClass.status" <%if("0".equals(ecc.getStatus()))out.print(" checked ");%> value="0"/>待发布
				<input type="radio" name="eduCourseClass.status" <%if("1".equals(ecc.getStatus()))out.print(" checked ");%> value="1"/>审核通过
                <input type="radio" name="eduCourseClass.status" <%if("2".equals(ecc.getStatus()))out.print(" checked ");%> value="2"/>发布待审核
                <input type="radio" name="eduCourseClass.status" <%if("3".equals(ecc.getStatus()))out.print(" checked ");%> value="3"/>审核不通过
                <input type="radio" name="eduCourseClass.status" <%if("4".equals(ecc.getStatus()))out.print(" checked ");%> value="3"/>禁用
			</td>
		</tr>
		</logic:equal>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="eduCourseClass.courseclassid"   value="<bean:write property="courseclassid"  name="model"/>"/>
<input type="hidden" name="eduCourseClass.courseid"    value="<bean:write property="courseid"  name="model"/>"/>
<input type="hidden" name="eduCourseClass.createdate"    value="<bean:write property="createdate"  name="model"/>"/>
<input type="hidden" name="eduCourseClass.usercount"    value="<bean:write property="usercount"  name="model"/>"/>

<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseClassActionForm]')){
	
		var objectForm = document.eduCourseClassActionForm;
	  	objectForm.action="eduCourseClassAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.eduCourseClassActionForm.action="/eduCourseClassAction.do?method=list";
  	document.eduCourseClassActionForm.submit();
}


</script>
</body>
</html>