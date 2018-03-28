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
<div class="box3" panelTitle="院企用户授权管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>登录名：</td>
			<td><input type="text" name="loginname" value="<bean:write name="loginname"/>"/></td>
			<td>真实姓名：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td>性别：</td>
			<td><java2code:option name="sex" codetype="sex" valuename="sex" selectall="3"/></td>
		</tr>
		<tr>
			<td>用户类型：</td>
			<td><java2code:option name="usertype" codetype="usertype" valuename="usertype" selectall="3"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:nextStep()"><span class="icon_add">下一步</span></a>
		</div>
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
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="userid" name="model"/>" <logic:match value="${model.userid}," name="userids">checked</logic:match>/></td>
			<td class="ali01"><bean:write name="model" property="loginname"/></td>
			<td class="ali02"><bean:write name="model" property="username"/></td>
			<td class="ali02"><java2code:value codetype="sex" property="sex"/></td>
			<td class="ali02"><bean:write name="model" property="mobile"/></td>
			<td class="ali02"><java2code:value codetype="usertype" property="usertype"/></td>
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
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	function nextStep(){
			var bb = "";
			var temp = "";
			var a = document.getElementsByName("checkid");
			for ( var i = 0; i < a.length; i++) {
				if (a[i].checked) {
				temp = a[i].value;
				bb = bb +temp+ ",";
				}
			}
		if(bb==""){
			alert("请选择至少一位用户");
		}else{
			bb=bb.substring(0,bb.length-1);
			location.href = '/eduCourseUserAction.do?method=beforeSetVip&userids='+bb;	
		}
	}
	//提交表单
	function postData(){
		var userids="${userids}";
		$("input[name='checkid']").each(function(){
			if($(this).is(":checked")){
				if(userids.indexOf($(this).val()+",")<0){
					userids+=$(this).val()+",";
				}
			}else{
				if(userids.indexOf($(this).val()+",")>-1){
					userids=userids.replace($(this).val()+",", "");
				}
			}
		});
	    document.pageForm.action = '/eduCourseUserAction.do?method=setVipUserList&userids='+userids;
   		document.pageForm.submit();
	}
</script>
</body>
</html>