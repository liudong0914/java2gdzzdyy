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

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<script>
function saveRecord(){
	if(validateForm('form[name=sysUserGiveMoneyActivityActionForm]')){
    var objectForm = document.sysUserGiveMoneyActivityActionForm;
    var starttime = document.getElementById("sysUserGiveMoneyActivity.startdate").value;
    var start = starttime.split(/[- :]/); 
    var endtime = document.getElementById("sysUserGiveMoneyActivity.enddate").value;
    var end = endtime.split(/[- :]/);
    if(end <= start){
    	alert("结束时间不能小于开始时间");
    	return false;
    }
	objectForm.action="sysUserGiveMoneyActivityAction.do?method=<bean:write name="act"/>";
	objectForm.submit();
}
}
function backRecord(){
  	document.sysUserGiveMoneyActivityActionForm.action="/sysUserGiveMoneyActivityAction.do?method=list";
  	document.sysUserGiveMoneyActivityActionForm.submit();
}

function changeType(typevalue){
	if(typevalue == 1){
		document.getElementById("type1").style.display="";
		document.getElementById("type2").style.display="none";
		document.getElementById("type3").style.display="none";
	}else if(typevalue == 2){
		document.getElementById("type1").style.display="none";
		document.getElementById("type2").style.display="";
		document.getElementById("type3").style.display="none";
	}else if(typevalue == 3){
		document.getElementById("type1").style.display="none";
		document.getElementById("type2").style.display="none";
		document.getElementById("type3").style.display="";
	}
}

function init(){
	var typevalue = "<bean:write name="model" property="type"/>";
	changeType(typevalue);
}

</script>
</head>
<body onload="init()">
<div class="box1" position="center">
<html:form action="/sysUserGiveMoneyActivityAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增学币活动</logic:equal><logic:equal value="updateSave" name="act">修改学币活动</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">活动名称：</td>
			<td class="ali01"><input type="text" style="width:300px;" class="validate[required]" name="sysUserGiveMoneyActivity.name" value="<bean:write property="name" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">活动类型：</td>
			<td class="ali01">
			<input type="radio" name="sysUserGiveMoneyActivity.type" value="1" onclick="changeType('1')" <logic:equal value="1" name="model" property="type" >checked="checked"</logic:equal>/>注册赠送&nbsp;
			<input type="radio" name="sysUserGiveMoneyActivity.type" value="2" onclick="changeType('2')" <logic:equal value="2" name="model" property="type" >checked="checked"</logic:equal>/>充值赠送&nbsp;
			<input type="radio" name="sysUserGiveMoneyActivity.type" value="3" onclick="changeType('3')" <logic:equal value="3" name="model" property="type" >checked="checked"</logic:equal>/>充值打折&nbsp;
			</td>
		</tr>
	
		<tr id="type1">
			<td class="ali03">赠送：</td>
			<td class="ali01"><input type="text" name="sysUserGiveMoneyActivity.money" value="<bean:write property="money" name="model"/>"/>(学币)</td>
		</tr>
	
		<tr id="type2">
			<td class="ali03">赠送不超过：</td>
			<td class="ali01"><input type="text" name="sysUserGiveMoneyActivity.maxmoney" value="<bean:write property="maxmoney" name="model"/>"/>(学币)&nbsp;&nbsp;<span style="color:red;">说明：此充值赠送活动是买多少送多少。</span></td>
		</tr>
		
		<tr id="type3">
			<td class="ali03">充值折扣：</td>
			<td class="ali01"><input type="text" name="sysUserGiveMoneyActivity.discount" value="<bean:write property="discount" name="model"/>"/>(折)&nbsp;&nbsp;<span style="color:red;">如：7.5折</span></td>
		</tr>
		
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01"><java2code:option name="sysUserGiveMoneyActivity.status" codetype="status2" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">开始时间：</td>
			<td class="ali01"><input type="text" id="sysUserGiveMoneyActivity.startdate" name="sysUserGiveMoneyActivity.startdate" style="width:150px;" readonly="readonly" value="<bean:write property="startdate"  name="model"/>" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">结束时间：</td>
			<td class="ali01"><input type="text" id="sysUserGiveMoneyActivity.enddate" name="sysUserGiveMoneyActivity.enddate" style="width:150px;" readonly="readonly" value="<bean:write property="enddate"  name="model"/>" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysUserGiveMoneyActivity.activityid" value="<bean:write property="activityid" name="model"/>"/>

<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>

</body>
</html>