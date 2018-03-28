<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestion"%>
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestionComplaint"%>
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
<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
</head>
 <body>
    <div class="box1" position=center>
    <table id="testTab" class="tableStyle" formMode="line" align="center">
    <tr>
			<th colspan="2">禁用记录详情</th>
	</tr>
     <td class="ali03" width="160">姓名：</td>
     <td class="ali01"><bean:write name="username"/></td>
     </tr>
     <tr>
     <td class="ali03">禁用开始时间：</td>
     <td class="ali01"><bean:write name="model" property="starttime"/></td>
     </tr>
     <tr>
      <td class="ali03">禁用结束时间：</td>
      <td class="ali01"><bean:write name="model" property="endtime"/></td>
     </tr>
     <tr>
      <td class="ali03">禁用原因描述：</td>
      <td class="ali01">${model.descript }</td>
     </tr>
     <tr>
      <td colspan="2"><button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            </td>
     </tr>
     </table>
    </div>
  </body>
<script>
function backRecord(){
  	document.sysUserDisableActionForm.action="/sysUserDisableAction.do?method=list";
  	document.sysUserDisableActionForm.submit();
}
</script>
</body>
</html>