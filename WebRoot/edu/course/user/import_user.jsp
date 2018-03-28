<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="java.util.Map"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数字分页start-->
<script type="text/javascript" src="../../../libs/js/nav/pageNumber.js"></script>
<script type="text/javascript" src="../../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../../libs/js/sk/comm.js"></script>
<!--数字分页end-->
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount" />

function postData(){
    document.pageForm.action = "/eduCourseUserAction.do?method=userList";
  	document.pageForm.submit();
}

function searchRecord(){
	document.pageForm.action="/eduCourseUserAction.do?method=userList";
	document.pageForm.submit();
}

function selectRecord(url){
	if(num>0){
		var str="确认选择以下选中用户？";
		var checkids="";
		if (num>1){
			for(i=0;i<num;i++){
				if (document.pageForm.checkid[i].checked==true){
					checkids=checkids+document.pageForm.checkid[i].value+",";
				}
			}
		}else{
			if (document.pageForm.checkid.checked==true)	{
				checkids=document.pageForm.checkid.value;
			}
		}
		if (checkids=="") {
			try{
				top.Dialog.alert("未找到可操作记录!");
	  	  	}catch(e){
	  	  		alert("未找到可操作记录!");
	  	  	}
		}else{
			var ckids = checkids.split(",");
			//if(ckids.length > 6){
			//	try{
		  		//  	top.Dialog.alert("知识点选择不能超过5个!");
		  	  //	}catch(e){
		  	  //		alert("知识点选择不能超过5个!");
		  	 // 	}
		  	  //	return false;
			//}
			document.pageForm.action=url;
			document.pageForm.submit();
		}
	}else{
		try{
  		  	top.Dialog.alert("未找到可操作记录!");
  	  	}catch(e){
  	  		alert("未找到可操作记录!");
  	  	}
	}
}
</script>
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="用户" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>用户名称：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td><button type="button" onclick="postData()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="selectRecord('/eduCourseUserAction.do?method=importDeal')"><span class="icon_ok">确认选择</span></a>
		<div class="clear"></div>
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
		<th class="ali02">用户名称</th>
	</tr>
	<logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
	<tr>
		<td>
			<input type="checkbox" name="checkid" value="<bean:write property="userid" name="model"/>"/>
		</td>
		<td class="ali01"><bean:write property="username" name="model"/></td>
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
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
<input type="hidden" name="unitid" value="<bean:write name="unitid"/>"/>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="courseclassid" value="<bean:write name="courseclassid"/>"/>
<input type="hidden" name="courseusertype" value="<bean:write name="courseusertype"/>"/>

<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
</body>
</html>