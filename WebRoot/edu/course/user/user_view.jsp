<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.sys.bo.SysUserInfoDetail"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@ page import="com.wkmk.edu.bo.EduCourseUser" %>
<%@ page import="java.util.*" %>
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

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%
EduCourseUser eduCourseUser=(EduCourseUser)request.getAttribute("eduCourseUser");
%>
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/eduCourseUserAction.do?method=addSaveStudent" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">用户信息</th>
		</tr>
		<tr>
			<td class="ali03">用户名：</td>
			<td class="ali01">${model.loginname }</td>
			<td class="ali01" width="90" rowspan="4" >
              <img src="/upload/<bean:write property="photo" name="model"/>" width="90" height="120" border="1" />
            </td>
		</tr>
		<tr>
			<td class="ali03">真实姓名：</td>
			<td class="ali01"><bean:write property="username" name="model"/></td>
		</tr>	
		
		<tr>
			<td class="ali03">性别：</td>
			<td class="ali01">
				<c:if test="${model.sex == '0' }">男</c:if>
				<c:if test="${model.sex == '1' }">女</c:if>
			</td>
		</tr>
		
		<tr>
			<td class="ali03">联系电话：</td>
			<td class="ali01"><bean:write property="telephone" name="detail"/></td>
		</tr>
		
		<tr>
			<td class="ali03">学历：</td>
			<td class="ali01">
				<c:if test="${detail.education == '1' }">博士及以上</c:if>
				<c:if test="${detail.education == '2' }">硕士</c:if>
				<c:if test="${detail.education == '3' }">本科</c:if>
				<c:if test="${detail.education == '4' }">大专</c:if>
				<c:if test="${detail.education == '9' }">其他</c:if>
			</td>
		</tr>
		
		<tr>
			<td class="ali03">联系地址：</td>
			<td class="ali01"><bean:write property="address" name="detail"/></td>
		</tr>
		<tr>
			<td class="ali03">个人简介：</td>
			<td class="ali01"><bean:write property="descript" name="detail"/></td>
		</tr>
		
		<tr>
			<td class="ali03">院企用户：</td>
			<td class="ali01">
				<c:if test="${eduCourseUser.vip == '0' }">否</c:if>
				<c:if test="${eduCourseUser.vip == '1' }">是</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" onclick="goBack()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="courseclassid" value="<bean:write name="courseclassid"/>"/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>

</html:form>
</div>
</div>
<script>

function goBack(){
	var objectForm = document.eduCourseUserActionForm;
  	objectForm.action="/eduCourseUserAction.do?method=list";
  	objectForm.submit();
}

</script>
</body>
</html>