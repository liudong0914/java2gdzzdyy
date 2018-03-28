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
<div class="box3" panelTitle="短信查询" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>手机号码：</td>
			<td><input type="text" name="mobile" value="${mobile}"/></td>
			<td>接收用户姓名：</td>
			<td><input type="text" name="username" value="${username}"/></td>
			<td>发送状态：</td>
			<td>
				<select name="state">
					<option value="">--请选择--</option>
					<option value="0" ${state eq '0' ?"selected":""}>未发送</option>
					<option value="1" ${state eq '1' ?"selected":""}>发送成功</option>
					<option value="2" ${state eq '2' ?"selected":""}>发送失败</option>
				</select>
			</td>
			<td>短信类型：</td>
			<td>
				<select name="type">
					<option value="">--请选择--</option>
					<option value="1" ${type eq '1' ?"selected":""}>普通短信</option>
					<option value="2" ${type eq '2' ?"selected":""}>手机号码变更验证码</option>
					<option value="3" ${type eq '3' ?"selected":""}>支付密码修改验证码</option>
				</select>
			</td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr> 
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02">手机号码</th>
			<th class="ali02"><span onclick="sortHandler('username')" id="span_username">接收短信用户</span></th>
			<th class="ali02" style="width:180px;"><span onclick="sortHandler('sysMessageInfo.createdate')" id="span_createdate">发送时间</span></th>
			<th class="ali02" style="width:120px;"><span onclick="sortHandler('state')" id="span_state">发送状态</span></th>
			<th class="ali02" style="width:40px;">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02">${model[0].mobile}</td>
			<td class="ali02">${model[1]}</td>
			<td class="ali02">${model[0].createdate}</td>
			<td class="ali02">${model[0].state eq '1'?"发送成功":model[0].state eq '2'?"发送失败":model[0].state eq '0'?"未发送":""}</td>
			<td class="ali02">
				<c:if test="${model[0].state eq '2'}"><div class="img_edit hand" title="重发" onclick="smsRetransmission(${model[0].smsid})"></div></c:if>
				<div class="img_find${model[0].state ne '2'?"0":""} hand" title="详情" onclick="smsDetail(${model[0].smsid})"></div>
				
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
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>

</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/sysSmsInfoAction.do?method=list';
   		document.pageForm.submit();
	}
	
	function smsRetransmission(obj){
		document.pageForm.action = '/sysSmsInfoAction.do?method=smsRetransmission&smsid='+obj;
   		document.pageForm.submit();
	}
	
	function smsDetail(obj){
		document.pageForm.action = '/sysSmsInfoAction.do?method=smsDetail&smsid='+obj;
   		document.pageForm.submit();
	}
</script>
</body>
</html>