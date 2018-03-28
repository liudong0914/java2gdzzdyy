<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysUserTeaching"%>
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
<div id="scrollContent">
<div class="box1" position="center" panelHeight="450">
<html:form action="/sysUserTeachingAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center" id="t_table">
		<tr>
			<th colspan="2">教学设置</th>
		</tr>
		<%
	  	  	List list = (List)request.getAttribute("list");
			if(list != null && list.size() > 0){
	  	  	SysUserTeaching model = null;
	  	  	for(int i=0; i<list.size(); i++){
	  	  		model = (SysUserTeaching)list.get(i);
	  	%>
		<tr>
			<td class="ali03"></td>
			<td class="ali02">
			学&nbsp;&nbsp;科：&nbsp;<input keepDefaultStyle="true" name="subjectname<%=i+1 %>" id="subjectname<%=i+1 %>" readonly="readonly" onclick="selectVersion(0, <%=i+1 %>)" type="text" value='<%=model.getFlags() %>' style="width:80px;"/>
			年&nbsp;&nbsp;级：&nbsp;<input keepDefaultStyle="true" name="gradename<%=i+1 %>" id="gradename<%=i+1 %>" readonly="readonly" onclick="selectVersion(1, <%=i+1 %>)" type="text" value='<%=model.getFlago() %>' style="width:80px;"/>
			<%if(i == 0){ %><span class="star">*</span><INPUT onClick="addRow(this)" type="button" value="添加" keepDefaultStyle="true"><%}else{ %><span class="star">&nbsp;</span><INPUT onClick="delRow(this, <%=i+1 %>)" type="button" value="删除" keepDefaultStyle="true"><%} %></td>
		</tr>
		<script language=javascript>
			document.getElementById('subjectid<%=i+1 %>').value = '<%=model.getSubjectid() %>';
		  	document.getElementById('gradeid<%=i+1 %>').value = '<%=model.getGradeid() %>';
		</script>
		<%}}else{ %>
		<tr>
			<td class="ali03"></td>
			<td class="ali02">
			学&nbsp;&nbsp;科：&nbsp;<input keepDefaultStyle="true" name="subjectname1" id="subjectname1" readonly="readonly" onclick="selectVersion(0, 1)" type="text" value='' style="width:80px;"/>
			年&nbsp;&nbsp;级：&nbsp;<input keepDefaultStyle="true" name="gradename1" id="gradename1" readonly="readonly" onclick="selectVersion(1, 1)" type="text" value='' style="width:80px;"/>
			<span class="star">*</span><INPUT onClick="addRow(this)" type="button" value="添加" keepDefaultStyle="true"></td>
		</tr>
		<%} %>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>
	<!-- 隐藏域供调用 -->
  	<input type="hidden" name="subjectid1" id="subjectid1" value=""/>
  	<input type="hidden" name="gradeid1" id="gradeid1" value=""/>
  	<input type="hidden" name="subjectid2" id="subjectid2" value=""/>
  	<input type="hidden" name="gradeid2" id="gradeid2" value=""/>
  	<input type="hidden" name="subjectid3" id="subjectid3" value=""/>
  	<input type="hidden" name="gradeid3" id="gradeid3" value=""/>
  	<input type="hidden" name="subjectid4" id="subjectid4" value=""/>
  	<input type="hidden" name="gradeid4" id="gradeid4" value=""/>
  	<input type="hidden" name="subjectid5" id="subjectid5" value=""/>
  	<input type="hidden" name="gradeid5" id="gradeid5" value=""/>
  	<input type="hidden" name="subjectid6" id="subjectid6" value=""/>
  	<input type="hidden" name="gradeid6" id="gradeid6" value=""/>
  	<input type="hidden" name="subjectid7" id="subjectid7" value=""/>
  	<input type="hidden" name="gradeid7" id="gradeid7" value=""/>
  	<input type="hidden" name="subjectid8" id="subjectid8" value=""/>
  	<input type="hidden" name="gradeid8" id="gradeid8" value=""/>
  	<input type="hidden" name="subjectid9" id="subjectid9" value=""/>
  	<input type="hidden" name="gradeid9" id="gradeid9" value=""/>
  	<input type="hidden" name="subjectid10" id="subjectid10" value=""/>
  	<input type="hidden" name="gradeid10" id="gradeid10" value=""/>
		  
	<input type="hidden" name="rowcount" id="rowcount" value="<bean:write name="rowcount"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	var objectForm = document.sysUserTeachingActionForm;
  	objectForm.action="sysUserTeachingAction.do?method=updateSave";
  	objectForm.submit();
}
function addRow(obj){
	var rowcount = document.getElementById('rowcount');
	if(rowcount.value == '5'){
		alert("选择学科年级不能超过5个!");
		return false;
	}
	var newrowcount = parseInt(rowcount.value)+1;
	document.getElementById('rowcount').value = newrowcount;
	
	var table = document.getElementById("t_table");
    var row = table.insertRow(table.rows.length-1);
  	row.insertCell(row.cells.length).innerHTML='';
    row.insertCell(row.cells.length).innerHTML='学&nbsp;&nbsp;科：&nbsp;<input name="subjectname'+newrowcount+'" id="subjectname'+newrowcount+'" readonly="readonly" onclick="selectVersion(0, '+newrowcount+')" type="text" value="" class=input style="width:80px;"/>&nbsp;年&nbsp;&nbsp;级：&nbsp;<input name="gradename'+newrowcount+'" id="gradename'+newrowcount+'" readonly="readonly" onclick="selectVersion(1, '+newrowcount+')" type="text" value="" class=input style="width:80px;"/><span class="star">&nbsp;</span>&nbsp;<INPUT onClick="delRow(this)" readonly type="button" value="删除">';
	row.cells[0].className="ali03";
	row.cells[1].className="ali02";
	//row.cells[0].style.color="#000";
	//row.cells[0].style["padding-left"]="42px";
}
function delRow(obj, currowcount){
	var row = obj.parentNode.parentNode;
	row.parentNode.removeChild(row);
	document.getElementById('rowcount').value = parseInt(document.getElementById('rowcount').value)-1
	document.getElementById('subjectid'+currowcount).value = '';
	document.getElementById('gradeid'+currowcount).value = '';
}
function selectVersion(flag, currowcount){
	var subjectid = document.getElementById('subjectid'+currowcount).value;
	if(flag == 1 && subjectid == ''){
		top.Dialog.alert("请先选择教学学科!");
		return;
	}
	var title = "选择教学学科";
	var url = "/eduSelectAction.do?method=selectSubject";
	if(flag == 1){
		title = "选择教学年级";
		url = "/eduSelectAction.do?method=selectGrade&subjectid=" + subjectid;
	}
	
	var diag = new top.Dialog();
	diag.Title = title;
	diag.URL = url;
	diag.Width = 310;
	diag.Height = 385;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('selectobjname')){
			var selectobjid = diag.innerFrame.contentWindow.document.getElementById('selectobjid').value;
			var selectobjname = diag.innerFrame.contentWindow.document.getElementById('selectobjname').value;
			if(flag == 0){
		  		document.getElementById('subjectname'+currowcount).value = selectobjname;
		  		document.getElementById('subjectid'+currowcount).value = selectobjid;
		  		//修改时把子集去掉
		  		document.getElementById('gradename'+currowcount).value = '';
		  		document.getElementById('gradeid'+currowcount).value = '';
			}
			if(flag == 1){
		  		document.getElementById('gradename'+currowcount).value = selectobjname;
		  		document.getElementById('gradeid'+currowcount).value = selectobjid;
			}
		}
		diag.close();
	};
	diag.show();
}
</script>
</body>
</html>