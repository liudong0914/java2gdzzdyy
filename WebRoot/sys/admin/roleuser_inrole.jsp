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
<script type="text/javascript" src="../../libs/js/nav/pageArrow.js"></script>
<script type="text/javascript" src="../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../libs/js/sk/comm.js"></script>
<!--数字分页end-->
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="授权用户" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>真实姓名：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02"><span onclick="sortHandler('sysUserInfo.loginname')" id="span_sysUserInfo.loginname">登录名</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('sysUserInfo.username')" id="span_sysUserInfo.username">真实姓名</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('sysUserInfo.sex')" id="span_sysUserInfo.sex">性别</span></th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="userroleid" name="model"/>"/></td>
			<td class="ali01"><bean:write name="model" property="sysUserInfo.loginname"/></td>
			<td class="ali02"><bean:write name="model" property="sysUserInfo.username"/></td>
			<td class="ali02"><java2code:value codetype="sex" property="sysUserInfo.sex"/></td>
		</tr>
		</logic:iterate>
	</table>
</div>
<div style="height:35px;">
	<div class="float_left padding5">
		共<bean:write name="pagelist" property="totalCount"/>条记录
	</div>
	<div class="float_right padding5">
	<div class="pageArrow" total="<bean:write name="pagelist" property="totalCount"/>" pageSize="<bean:write name="pagesize"/>" page="<bean:write name="pagelist" property="curPage0"/>" id="pagearrow"></div>
	</div>
	<div class="clear"></div>
</div>
<input type="hidden" name="roleid" value='<bean:write name="roleid"/>'/>
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
	    document.pageForm.action = '/sysAdminUserRoleAction.do?method=inroleuser';
   		document.pageForm.submit();
	}
	
	$("#pagearrow").bind("pageChange",function(e,index){
		document.getElementById('pageNo').value = index+1;
		postData();		
	})
	
	
	function doOnLoad(){
	 	window.parent.frames["left"].location = "/sysAdminUserRoleAction.do?method=outroleuser&roleid=<bean:write name="roleid"/>" ; 
	}
	
	function delUser(){
	  if(num>0){
		  var str="此操作将给选中用户撤销权限，真的要继续么？";
		  var checkids="";
		  if (num>1){
		  for(i=0;i<num;i++){
		   if (document.pageForm.checkid[i].checked==true){
		      checkids=checkids+document.pageForm.checkid[i].value+",";
		   }
		 }
		}
		else{
		   if (document.pageForm.checkid.checked==true)	{
			checkids=document.pageForm.checkid.value;
		   }
		 }
		if (checkids=="") {
		     top.Dialog.alert("您还没有选择待撤销用户呢!")
		}
		else{
		   top.Dialog.confirm(str,
			   function(){
	               document.pageForm.action="/sysAdminUserRoleAction.do?method=deleteroleuser";
		   		   document.pageForm.submit(); 
		       }
		   );
		}
		}
		else{
		  top.Dialog.alert("未选择待撤销用户!")
		}
	}
	
	function delUserAll(){
	  if(num>0){
		  var str="此操作将给右侧所有用户撤销授权，真的要继续么？";
		  top.Dialog.confirm(str,
			   function(){
	               document.pageForm.action="/sysAdminUserRoleAction.do?method=deleteroleuserall";
		   		   document.pageForm.submit(); 
		       }
		   );
	  }
	}
	
	$(function(){
		<logic:present name="reload">
		doOnLoad();
		</logic:present>
	})
</script>
</body>
</html>