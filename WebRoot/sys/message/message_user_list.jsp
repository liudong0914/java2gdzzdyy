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
<div class="box3" panelTitle="接收消息用户查询" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>学段：</td>
			<td><java2code:option name="xueduan" codetype="xueduan" valuename="xueduan" selectall="2"/></td>
			<td>用户类型：</td>
			<td><java2code:option name="usertype" codetype="usertype" valuename="usertype" selectall="3"/></td>
			<td>读取状态：</td>
			<td>
				<select name="isread">
					<option value="">--请选择--</option>
					<option value="0" ${isread eq '0'?"selected":""}>未读</option>
					<option value="1" ${isread eq '1'?"selected":""}>已读</option>
				</select>
			</td>
			<td>用户姓名：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr> 
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02" width="70"><span onclick="sortHandler('b.username')" id="span_username">消息接收用户</span></th>
			<th class="ali02" width="70"><span onclick="sortHandler('b.xueduan')" id="span_username">学段</span></th>
			<th class="ali02" width="70"><span onclick="sortHandler('b.usertype')" id="span_username">用户类型</span></th>
			<th class="ali02" width="10"><span onclick="sortHandler('a.isread')" id="span_createdate">读取状态</span></th>
			<th class="ali02" width="10"><span onclick="sortHandler('a.readtime')" id="span_createdate">读取时间</span></th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02">${model[1].username }</td>
			<td class="ali02">${model[1].xueduan eq '1'?"小学":model[1].xueduan eq '2'?"初中":model[1].xueduan eq '3'?"高中":"" }</td>
			<td class="ali02">${model[1].usertype eq '0'?"系统用户":model[1].usertype eq '1'?"老师":model[1].usertype eq '2'?"学生":model[1].usertype eq '0'?"家长":""}</td>
			<td class="ali02">${model[0].isread eq '1'?'已读':'未读'}</td>
			<td class="ali02">${model[0].readtime}</td>
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
<input type="hidden" value="<bean:write name="messageid"/>" name="messageid"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>

</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/sysMessageInfoAction.do?method=messageUserList';
   		document.pageForm.submit();
	}
</script>
</body>
</html>