<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.sys.bo.SysTeacherQualification"%>
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
<script type="text/javascript">
function init(){
  <logic:present name="promptinfo">
      parent.parent.Dialog.alert("<bean:write name="promptinfo"/>")
  </logic:present>   
} 
function downfile(){
	var objectForm = document.sysTeacherQualificationActionForm;
  	objectForm.action="sysTeacherQualificationAction.do?method=downFile";
  	objectForm.submit();
}
</script>
</head>
<body onload="javascript:init()">
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/sysTeacherQualificationAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">认证申请</th>
		</tr>
		<tr>
			<td class="ali03">教师名称：</td>
			<td class="ali01">
				<bean:write property="username" name="model"/>
			</td>
			<td class="ali01" width="90" rowspan="4" >
              <img src="/upload/<bean:write property="photo" name="model"/>" width="90" height="120" border="1" />
            </td>
		</tr>
		<tr>
			<td class="ali03">性别：</td>
			<td class="ali01">
				<bean:write property="flagl" name="model" />
			</td>
		</tr>
		<tr>
			<td class="ali03">手机号：</td>
			<td class="ali01">
				<bean:write property="mobile" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03">身份证号：</td>
			<td class="ali01">
				<bean:write property="identitycardno" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03">手持证件照：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="idphto" name="model"/>"  width="220" height="120" border="1" />
			</td>
		</tr>	
		<tr>
			<td class="ali03">教师资格证：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="imgpath" name="model"/>"  width="220" height="120" border="1" />
			</td>
		</tr>
		<logic:notEmpty name="model" property="filepath">
		<tr>
			<td class="ali03">附件下载：</td>
			<td class="ali01">
		   		<a href="#" onclick="downfile()"><img src="/libs/images/bfileext/bak/rar.gif" alt="点击下载附件" width="45px" height="45px"/></a>
		   	</td>
	  	</tr>
	  	</logic:notEmpty>
	  	<tr>
			<td class="ali03">申请状态：</td>
			<td class="ali01">
				<c:if test="${model.status == '0'}">
					审核中
				</c:if>
				<c:if test="${model.status == '1'}">
					通过
				</c:if>
				<c:if test="${model.status == '2'}">
					驳回
				</c:if>
			</td>
		</tr>
	  	<tr>
			<td class="ali03">驳回原因：</td>
			<td class="ali01">
				<bean:write property="returndescript" name="model"/>
			</td>
		</tr>
	</table>
	<input type="hidden" name="filepath" value="<bean:write property="filepath" name="model"/>" />
</html:form>
</div>
</div>
<script>

</script>
</body>
</html>