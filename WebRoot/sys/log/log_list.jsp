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

<!--数字分页start-->
<script type="text/javascript" src="../../libs/js/nav/pageNumber.js"></script>
<script type="text/javascript" src="../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../libs/js/sk/comm.js"></script>
<!--数字分页end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="系统日志" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>用户姓名：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td>内容：</td>
			<td><input type="text" name="descript" value="<bean:write name="descript"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02" width="50">序号</th>
			<th class="ali02" width="70"><span onclick="sortHandler('sysUserInfo.username')" id="span_sysUserInfo.username">用户名</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('userip')" id="span_userip">登录IP</span></th>
			<th class="ali02" width="130"><span onclick="sortHandler('createdate')" id="span_createdate">时间</span></th>
			<th class="ali02"><span onclick="sortHandler('descript')" id="span_descript">日志内容</span></th>
		</tr>
		<!--循环列出所有数据-->
		<%Integer startcount = (Integer)request.getAttribute("startcount"); %>
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02"><%=startcount+index+1%></td>
			<td class="ali02"><bean:write name="model" property="sysUserInfo.username"/></td>
			<td class="ali02"><bean:write name="model" property="userip"/></td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali01"><span class="textSlice" style="width:400px;" title="<bean:write name="model" property="descript"/>"><bean:write name="model" property="descript"/></span></td>
		</tr>
		</logic:iterate>
	</table>
</div>
<div style="height:35px;">
	<div class="float_left padding5">
		共<bean:write name="pagelist" property="totalCount"/>条记录
	</div>
	<div class="float_right padding5">
		<div class="pageNumber" total="<bean:write name="pagelist" property="totalCount"/>" pageSize="<bean:write name="pagesize"/>" page="<bean:write name="pagelist" property="curPage0"/>" showSelect="true" showInput="true" id="pageContent"></div>
	</div>
	<div class="clear"></div>
</div>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>

<input type="hidden" name="flag" value="<bean:write name="flag"/>"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/sysUserLogAction.do?method=list';
   		document.pageForm.submit();
	
	}
</script>
</body>
</html>