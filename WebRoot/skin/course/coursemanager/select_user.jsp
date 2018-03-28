<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="java.util.Map"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/libs/js/sk/comm.js"></script>
<link type="text/css" href="/skin/course/css/style.css" rel="stylesheet">
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount" />

function postData(){
    document.pageForm.action = "/eduCourseUserAction.do?method=selectUser";
  	document.pageForm.submit();
}

function searchRecord(){
	document.pageForm.action="/eduCourseUserAction.do?method=selectUser";
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
<body style="background:#fcfcfc;">
<form name="pageForm" method="post">
<div class="tab">
	<div class="acts" style="left:0px;">
		用户名称：<input type="text" class="ipt-text" name="username" value="${username }" style="width:100px;">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="btn btn-pop ml20" value="查询" onclick="postData()">
	</div>
	<div class="acts">
		<input class="btn btn-pop ml20" value="确定选择" onclick="selectRecord('/eduCourseUserAction.do?method=deal')" style="display:inline-block;" type="button">
	</div>
</div>

<table width="100%" class="table">
	<thead>
	<tr>
		<th width="45">
		<input type="checkbox" onclick="setState(this.checked)">
		</th>
		<th width="160">用户名称</th>
	</tr>
	</thead>
	<tbody>
	<logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
	<tr>
		<td colspan="9">
		<div class="tableBox box02">
			<table width="100%" style="border-collapse:separate;">
				<thead>
				<tr>
					<th width="45">
						<input class="checkShow msgCheck" type="checkbox" name="checkid" value="<bean:write property="userid" name="model"/>"/>
					</th>
					<th width="160"><bean:write property="username" name="model"/></th>
				</tr>
				</thead>
			</table>
		</div>
		</td>
	</tr>
	</logic:iterate>
	</tbody>
</table>
<%@ include file="/skin/course/jsp/page.jsp"%>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
</form>
</body>
</html>