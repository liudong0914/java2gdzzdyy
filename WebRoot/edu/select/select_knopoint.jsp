<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.edu.bo.EduKnopointInfo"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base target="_self"/>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<script type="text/javascript" src="../../libs/js/sk/comm.js"></script>
</head>

<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="知识点" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>知识点名称：</td>
			<td><input type="text" name="title" value="<bean:write name="title"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="selectRecord('/eduSelectAction.do?method=deal&tag=selectknopoint')"><span class="icon_ok">确认选择</span></a>
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
			<th class="ali02">知识点名称</th>
		</tr>
		<%
		String knopointid = (String)request.getAttribute("knopointid");
		List knopointList = (List)request.getAttribute("knopointList");
		EduKnopointInfo model = null;
		int temp = 0;
		for(int i=0, size=knopointList.size(); i<size; i++){
			model = (EduKnopointInfo)knopointList.get(i);
			temp = model.getKnopointno().length() - 8;
		%>
		<tr>
	      	<td>
	      		<%if("1".equals(model.getType())){ %>
	      		<input type="checkbox" name="checkid" value="<%=model.getKnopointid() %>" <%if(knopointid.indexOf(model.getKnopointid().toString()) != -1){ %>checked="checked"<%} %>>
	      		<%}else{ %>
	      		&nbsp;
	      		<%} %>
	      	</td>
	      	<td class="ali01"><%for(int m=0; m<temp; m++){%>&nbsp;<%}%><%=model.getTitle() %></td>
		</tr>
		<%} %>
	</table>
</div>
<input type="hidden" name="subjectid" value="<bean:write name="subjectid"/>" />
<input type="hidden" name="gradeid" value="<bean:write name="gradeid"/>" />
</form>
<script type="text/javascript">
var num=<bean:write name="size"/>;

function searchRecord(){
	document.pageForm.action="/eduSelectAction.do?method=selectKnopoint";
	document.pageForm.submit();
}

function selectRecord(url){
	if(num>0){
		var str="确认选择以下选中知识点？";
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
				top.Dialog.confirm("确认清空知识点选择？",function(){
					document.pageForm.action=url;
	   				document.pageForm.submit();
				});
	  	  	}catch(e){
		  	  	if(confirm("确认清空知识点选择？")){
					document.pageForm.action=url;
					document.pageForm.submit();
				}
	  	  	}
		}else{
			var ckids = checkids.split(",");
			if(ckids.length > 6){
				try{
		  		  	top.Dialog.alert("知识点选择不能超过5个!");
		  	  	}catch(e){
		  	  		alert("知识点选择不能超过5个!");
		  	  	}
		  	  	return false;
			}
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
</body>
</html>