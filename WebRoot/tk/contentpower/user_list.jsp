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
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="用户列表" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>登录名：</td>
			<td><input type="text" name="loginname" value="<bean:write name="loginname"/>"/></td>
			<td>真实姓名：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td>性别：</td>
			<td><java2code:option name="sex" codetype="sex" valuename="sex" selectall="3"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
	</div>		
	</div>	
	</div>
	<div class="clear"></div>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02"><span onclick="sortHandler('loginname')" id="span_loginname">登录名</span></th>
			<th class="ali02" width="150"><span onclick="sortHandler('username')" id="span_username">真实姓名</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('sex')" id="span_sex">性别</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('mobile')" id="span_mobile">手机号</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('usertype')" id="span_usertype">用户类型</span></th>
			<th class="ali02" width="60">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="userid" name="model"/>"/></td>
			<td class="ali01"><bean:write name="model" property="loginname"/></td>
			<td class="ali02"><bean:write name="model" property="username"/></td>
			<td class="ali02"><java2code:value codetype="sex" property="sex"/></td>
			<td class="ali02"><bean:write name="model" property="mobile"/></td>
			<td class="ali02"><java2code:value codetype="usertype" property="usertype"/></td>
			<td class="ali02">
			<c:if test="${type=='1' }">
			<div style="margin-left: 20px;" class="img_user_worker hand" title="内容校验授权" onclick="bookcontentcheck('<bean:write name="model" property="userid"/>',1)"></div>
			</c:if>
			<c:if test="${type=='2' }">
			<div style="margin-left: 20px;" class="img_user_worker hand" title="试题替换授权" onclick="bookcontentcheck('<bean:write name="model" property="userid"/>',2)"></div>
			</c:if>
			</td>
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
<input type="hidden" value="${type }" name="type" />
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkBookContentPowerAction.do?method=userlist';
   		document.pageForm.submit();
	
	}
	function bookcontentcheck(userid,type){
	 var diag=new top.Dialog();
	 diag.Title="内容校验授权";
	 diag.URL='/tkBookContentPowerAction.do?method=contentMain&userid='+userid+'&type='+type;
	 diag.Width=1000;
	 diag.Height=800;
	 diag.ShowMaxButton=true;
	 diag.show();
	}
	
</script>
</body>
</html>