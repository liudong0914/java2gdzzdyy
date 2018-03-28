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
</head>
<body>
<table class="tableStyle" align="center">
	<tr class="ali02">
		<th colspan="3">部门[<bean:write name="deptname"/>]</th>
	</tr>
	<tr style="background-color:#fff;">
		<td class="ali02" width="48%">
		<IFRAME scrolling="auto" width="100%" height="460px" frameBorder=0 id=left name=left src="/sysUnitDeptMemberAction.do?method=outmember&deptid=<bean:write name="deptid"/>" allowTransparency="true"></IFRAME>
		</td>
		<td class="ali02" width="4%">
		<table class="tableStyle" align="center">
			<tr height="50" style="background-color:#fff;">
				<td><input type="button" style="width:60px;" value="全部授权" onclick="doAddAll()"/></td>
			</tr>
			<tr height="50" style="background-color:#fff;">
				<td><input type="button" style="width:60px;" value="授权" onclick="doAdd()"/></td>
			</tr>
			<tr height="50" style="background-color:#fff;">
				<td><input type="button" style="width:60px;" value="撤销" onclick="doDelete()"/></td>
			</tr>
			<tr height="50" style="background-color:#fff;">
				<td><input type="button" style="width:60px;" value="全部撤销" onclick="doDeleteAll()"/></td>
			</tr>
		</table>
		</td>
		<td class="ali02" width="48%">
		<IFRAME scrolling="auto" width="100%" height="460px" frameBorder=0 id=right name=right src="/sysUnitDeptMemberAction.do?method=inmember&deptid=<bean:write name="deptid"/>" allowTransparency="true"></IFRAME>
		</td>
	</tr>
</table>
<script>
function doAdd(){
	left.addUser();
}
function doAddAll(){
	left.addUserAll();
}
function doDelete(){
	right.delUser();
}
function doDeleteAll(){
	right.delUserAll();
}
</script>
</body>
</html>