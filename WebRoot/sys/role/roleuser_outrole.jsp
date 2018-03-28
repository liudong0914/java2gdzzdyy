<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysUnitDept"%>
<%@page import="com.util.string.Encode"%>
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
<div class="box3" panelTitle="待选用户" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>机构:</td>
            <td>
             <select name="deptid" boxHeight="130" onchange="selectGroup(this.value)" style="width:90px;" keepDefaultStyle="false">
	             <option value="">选择机构</option>
		          <%
		          List lst=(List)request.getAttribute("list");
		          String deptid=Encode.nullToBlank(request.getAttribute("deptid"));
		          SysUnitDept group = null;
		    	  for(int i=0;i<lst.size();i++) {
		            group=(SysUnitDept)lst.get(i);
		            int len = (group.getDeptno().length() - 8)/4;
		            if(len < 0) len = 0;
		          %>
	             <option value="<%=group.getDeptid()%>" <%if(deptid.equals(group.getDeptid().toString())){ %>selected="selected"<%} %>><%for(int j=0; j<len; j++){%> ├<%}%><%=group.getDeptname()%></option>
	             <%}%>
             </select>
            </td>
			<td>姓名：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>" style="width:50px;"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02"><span onclick="sortHandler('loginname')" id="span_loginname">登录名</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('username')" id="span_username">真实姓名</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('sex')" id="span_sex">性别</span></th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="userid" name="model"/>"/></td>
			<td class="ali01"><bean:write name="model" property="loginname"/></td>
			<td class="ali02"><bean:write name="model" property="username"/></td>
			<td class="ali02"><java2code:value codetype="sex" property="sex"/></td>
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
	    document.pageForm.action = '/sysUserRoleAction.do?method=outroleuser';
   		document.pageForm.submit();
	}
	
	$("#pagearrow").bind("pageChange",function(e,index){
		document.getElementById('pageNo').value = index+1;
		postData();		
	})
	
	
	function doOnLoad(){
	 	window.parent.frames["right"].location = "/sysUserRoleAction.do?method=inroleuser&roleid=<bean:write name="roleid"/>";
	}
	
	function addUser(){
	  if(num>0){
		  var str="此操作将给选中用户授权，真的要继续么？";
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
		     top.Dialog.alert("您还没有选择待授权用户呢!")
		}
		else{
		 top.Dialog.confirm(str,
			   function(){
	               document.pageForm.action="/sysUserRoleAction.do?method=addroleuser";
		   		   document.pageForm.submit(); 
		       }
		   );
		}
		}
		else{
		  top.Dialog.alert("未选择待授权用户!")
		}
	}
	
	function addUserAll(){
	  if(num>0){
		  var str="此操作将给左侧所有用户授权，真的要继续么？";
		  top.Dialog.confirm(str,
			   function(){
	               document.pageForm.action="/sysUserRoleAction.do?method=addroleuserall";
		   		   document.pageForm.submit(); 
		       }
		   );
	  }
	}
	
	function selectGroup(groupid){
		document.pageForm.action="/sysUserRoleAction.do?method=outroleuser";
	 	document.pageForm.submit();
	}
	
	$(function(){
		<logic:present name="reload">
		doOnLoad();
		</logic:present>
	})
</script>
</body>
</html>