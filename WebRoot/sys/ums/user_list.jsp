<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
<%@page import="com.wkmk.sys.bo.SysUnitInfo"%>
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
<div class="box3" panelTitle="用户管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>学校地区：</td>
			<td colspan="3">
			<select name="province" id="selectProvince" boxHeight="130" onchange="changeArea(this.value, '2', 'selectCity')">
				<option value="">请选择...</option>
				<%
				String province = (String)request.getAttribute("province");
				List provinceList = (List)request.getAttribute("provinceList");
				SysAreaInfo sai = null;
				for(int i=0, size=provinceList.size(); i<size; i++){
					sai = (SysAreaInfo)provinceList.get(i);
				%>
				<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(province)){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
				<%} %>
			</select>
			<select name="city" id="selectCity" boxHeight="130" onchange="changeArea(this.value, '2', 'selectCounty')">
				<option value="">请选择...</option>
				<%
				String city = (String)request.getAttribute("city");
				List cityList = (List)request.getAttribute("cityList");
				if(cityList != null && cityList.size() > 0){
				for(int i=0, size=cityList.size(); i<size; i++){
					sai = (SysAreaInfo)cityList.get(i);
				%>
				<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(city)){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
				<%}} %>
			</select>
			<select name="county" id="selectCounty" boxHeight="130" onchange="changeArea1(this.value, '2')">
				<option value="">请选择...</option>
				<%
				String county = (String)request.getAttribute("county");
				List countyList = (List)request.getAttribute("countyList");
				if(countyList != null && countyList.size() > 0){
				for(int i=0, size=countyList.size(); i<size; i++){
					sai = (SysAreaInfo)countyList.get(i);
				%>
				<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(county)){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
				<%}} %>
			</select>
			</td>
			<td>学校名称：</td>
			<td>
			<select name="unitid" id="selectUnit" boxHeight="130" onchange="searchRecord()">
				<option value="">请选择...</option>
				<%
				String unitid = (String)request.getAttribute("unitid");
				List schoolList = (List)request.getAttribute("schoolList");
				SysUnitInfo sui = null;
				if(schoolList != null && schoolList.size() > 0){
				for(int i=0, size=schoolList.size(); i<size; i++){
					sui = (SysUnitInfo)schoolList.get(i);
				%>
				<option value="<%=sui.getUnitid() %>" <%if(sui.getUnitid().toString().equals(unitid)){ %>selected="selected"<%} %>><%=sui.getUnitname() %></option>
				<%}} %>
			</select>
			</td>
		</tr>
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
			<td>所属学段：</td>
			<td><java2code:option name="xueduan" codetype="xueduan" valuename="xueduan" selectall="3"/></td>
			<!-- 
			<td>学号：</td>
			<td><input type="text" name="studentno" value="<bean:write name="studentno"/>"/></td>
			 -->
			<td>状态：</td>
			<td><java2code:option name="status" codetype="status3" valuename="status" selectall="3"/></td>
		</tr>
		<tr>
			<td>用户认证：</td>
			<td><java2code:option name="authentication" codetype="authentication" valuename="authentication" selectall="3"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/sysUmsUserInfoAction.do?method=beforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/sysUmsUserInfoAction.do?method=delBatchRecord&setstatus=2','确定要禁用所选中用户?')"><span class="icon_no">禁用</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/sysUmsUserInfoAction.do?method=delBatchRecord&setstatus=1','确定要启用所选中用户？')"><span class="icon_ok">启用</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="importusers()"><span class="icon_import">导入用户</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="exportusers()"><span class="icon_export">导出用户</span></a>
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
			<th class="ali02"><span onclick="sortHandler('loginname')" id="span_loginname">登录名</span></th>
			<th class="ali02" width="150"><span onclick="sortHandler('username')" id="span_username">真实姓名</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('sex')" id="span_sex">性别</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('mobile')" id="span_mobile">手机号</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('usertype')" id="span_usertype">用户类型</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('xueduan')" id="span_xueduan">所属学段</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('authentication')" id="span_authentication">用户认证</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="40">操作</th>
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
			<td class="ali02"><java2code:value codetype="xueduan" property="xueduan"/></td>
			<td class="ali02"><java2code:value codetype="authentication" property="authentication"/></td>
			<td class="ali02"><java2code:value codetype="status3" property="status"/></td>
			<td class="ali02">
			<div class="img_edit hand" title="修改" onclick="editThisRecord('sysUmsUserInfoAction.do','<bean:write name="model" property="userid"/>')"></div>
			<div class="img_user_worker hand" title="人员角色" onclick="addUserRole('<bean:write name="model" property="userid"/>')"></div>
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
<input type="hidden" value="<bean:write name="pagelist" property="datalist" />" name="datalist" id="datalist"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/sysUmsUserInfoAction.do?method=list';
   		document.pageForm.submit();
	
	}
	function exportusers(){
		document.pageForm.action = '/sysUmsUserInfoAction.do?method=userExport';
   		document.pageForm.submit();
	}
	
	function addUserRole(userid){
		var diag = new top.Dialog();
		diag.Title = "人员角色";
		diag.URL = '/sysUmsUserRoleAction.do?method=frame&userid='+userid;
		diag.Width = 380;
		diag.Height = 530;
		diag.ShowMaxButton=true;
		diag.show();
	}
	
	function changeArea(citycode, type, selectid){
		if(citycode != ""){
			$.ajax({
		        type: "get",
		        async: false,
		        url: "sysAreaInfoAction.do?method=getAareByCitycode&citycode=" + citycode + "&type=" + type + "&ram=" + Math.random(),
		        dataType: "text",
		        success: function(data){
		        	$("#"+selectid).html(data);
		        	$("#"+selectid).render();//刷新下拉框
		        }
			});
			
			if(selectid == 'selectCity'){
				var selectProvince = document.getElementById("selectProvince").value;
				$.ajax({
			        type: "get",
			        async: false,
			        url: "sysUmsUserInfoAction.do?method=getUnitList&tag=1&citycode=" + selectProvince + "&type=" + type + "&ram=" + Math.random(),
			        dataType: "text",
			        success: function(data){
			        	$("#selectUnit").html(data);
			        	$("#selectUnit").render();//刷新下拉框
			        }
				});
			}else if(selectid == 'selectCounty'){
				var selectCity = document.getElementById("selectCity").value;
				$.ajax({
			        type: "get",
			        async: false,
			        url: "sysUmsUserInfoAction.do?method=getUnitList&tag=2&citycode=" + selectCity + "&type=" + type + "&ram=" + Math.random(),
			        dataType: "text",
			        success: function(data){
			        	$("#selectUnit").html(data);
			        	$("#selectUnit").render();//刷新下拉框
			        }
				});
			}
		}else{
			if(selectid == 'selectCity'){
				$("#selectCity").html("<option value=''>请选择...</option>");
				$("#selectCity").render();//刷新下拉框
				$("#selectCounty").html("<option value=''>请选择...</option>");
				$("#selectCounty").render();//刷新下拉框
				$("#selectUnit").html("<option value=''>请选择...</option>");
				$("#selectUnit").render();//刷新下拉框
			}else if(selectid == 'selectCounty'){
				$("#selectCounty").html("<option value=''>请选择...</option>");
				$("#selectCounty").render();//刷新下拉框
				var selectProvince = document.getElementById("selectProvince").value;
				$.ajax({
			        type: "get",
			        async: false,
			        url: "sysUmsUserInfoAction.do?method=getUnitList&tag=1&citycode=" + selectProvince + "&type=" + type + "&ram=" + Math.random(),
			        dataType: "text",
			        success: function(data){
			        	$("#selectUnit").html(data);
			        	$("#selectUnit").render();//刷新下拉框
			        }
				});
			}
		}
	}
	
	function changeArea1(citycode, type){
		if(citycode != ""){
			var selectCounty = document.getElementById("selectCounty").value;
			$.ajax({
		        type: "get",
		        async: false,
		        url: "sysUmsUserInfoAction.do?method=getUnitList&tag=3&citycode=" + selectCounty + "&type=" + type + "&ram=" + Math.random(),
		        dataType: "text",
		        success: function(data){
		        	$("#selectUnit").html(data);
		        	$("#selectUnit").render();//刷新下拉框
		        }
			});
		}else{
			var selectCity = document.getElementById("selectCity").value;
			$.ajax({
		        type: "get",
		        async: false,
		        url: "sysUmsUserInfoAction.do?method=getUnitList&tag=2&citycode=" + selectCity + "&type=" + type + "&ram=" + Math.random(),
		        dataType: "text",
		        success: function(data){
		        	$("#selectUnit").html(data);
		        	$("#selectUnit").render();//刷新下拉框
		        }
			});
		}
	}
function importusers(){
	   var diag= new top.Dialog();
	   diag.Title="导入用户";
	   diag.URL='sysUmsUserInfoAction.do?method=importUser';
	   diag.Width=800;
	   diag.Height=500;
	   diag.ShowMaxButton=true;
	   diag.show();
}
</script>
</body>
</html>