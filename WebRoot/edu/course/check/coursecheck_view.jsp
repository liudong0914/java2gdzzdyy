<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="java.util.List"%>
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

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->

<!-- 籍贯start -->
<script type="text/javascript" src="../../libs/js/sk/PCASClass.js" charset="uft-8"></script>
<!-- 籍贯end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/eduCourseInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="5">课程信息</th>
		</tr>
		<tr>
			<td class="ali03">课程编号：</td>
			<td class="ali01" colspan="2"><bean:write property="courseno" name="model"/></td>
			<td class="ali01" rowspan="3">
				<img src="/upload/<bean:write property="sketch" name="model"/>"  />
			</td>
		</tr>
		<tr>	
			<td class="ali03">课程名称：</td>
			<td class="ali01" colspan="2"><bean:write property="title" name="model"/></td>
		</tr>
		<tr>	
			<td class="ali03">状态：</td>
			<td class="ali01" colspan="2">
				<c:if test="${model.status == '0' }">
				私有
				</c:if>
				<c:if test="${model.status == '1' }">
				审核通过
				</c:if>
				<c:if test="${model.status == '2' }">
				发布待审核
				</c:if>
				<c:if test="${model.status == '3' }">
				审核不通过
				</c:if>
				<c:if test="${model.status == '4' }">
				禁用
				</c:if>
				
			</td>
		</tr>
		<tr>	
			<td class="ali03">所属分类：</td>
			<td class="ali01"><java2code:value codetype="coursetypeid" property="coursetypeid"/></td>
			
			<td class="ali03">总价：</td>
			<td class="ali01"><bean:write property="price" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">总课时数：</td>
			<td class="ali01"><bean:write property="classhour" name="model"/></td>
		
			<td class="ali03">查看次数：</td>
			<td class="ali01"><bean:write property="hits" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">收藏次数：</td>
			<td class="ali01"><bean:write property="collects" name="model"/></td>
		
			<td class="ali03">学习次数：</td>
			<td class="ali01"><bean:write property="studys" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">详细描述：</td>
			<td class="ali01" colspan="3"><bean:write property="htmlcontent" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">创建时间：</td>
			<td class="ali01"><bean:write property="createdate" name="model"/></td>
		
			<td class="ali03">开始时间：</td>
			<td class="ali01"><bean:write property="startdate" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">结束时间：</td>
			<td class="ali01"><bean:write property="enddate" name="model"/></td>
		
			<td class="ali03">创建教师：</td>
			<td class="ali01"><bean:write property="sysUserInfo.username" name="model"/></td>
		</tr>
		
	</table>

</html:form>
</div>
</div>
<script>
function backRecord(){
  	document.eduCourseInfoActionForm.action="/eduCourseInfoAction.do?method=checkCourseList";
  	document.eduCourseInfoActionForm.submit();
}
</script>
</body>
</html>