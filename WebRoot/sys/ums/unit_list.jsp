<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
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
<div class="box3" panelTitle="单位管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<%-- 
			<td>单位性质：</td>
			<td><java2code:option name="schooltype" codetype="schooltype" valuename="schooltype" selectall="3" onchange="searchRecord()"/></td>
			--%>
			<td>单位名称：</td>
			<td><input type="text" name="unitname" style="width:230px;" value="<bean:write name="unitname"/>"/></td>
			<td>状态：</td>
			<td><java2code:option name="status" codetype="status2" valuename="status" selectall="3" onchange="searchRecord()"/></td>
		</tr>
		<tr>
			<td>所属地区：</td>
			<td colspan="4">
			<select name="province" boxHeight="130" onchange="searchRecord()">
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
			<select name="city" boxHeight="130" onchange="searchRecord()">
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
			<select name="county" boxHeight="130" onchange="searchRecord()">
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
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/sysUmsUnitInfoAction.do?method=beforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/sysUmsUnitInfoAction.do?method=delBatchRecord&setstatus=2','确定要禁用所选中单位?')"><span class="icon_no">禁用</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/sysUmsUnitInfoAction.do?method=delBatchRecord&setstatus=1','确定要启用所选中单位？')"><span class="icon_ok">启用</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="importschools()"><span class="icon_import">导入学校</span></a>
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
			<th class="ali02"><span onclick="sortHandler('unitname')" id="span_unitname">机构名称</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('linkman')" id="span_linkman">联系人</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('job')" id="span_job">职务</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('telephone')" id="span_telephone">电话</span></th>
			<th class="ali02" width="130"><span onclick="sortHandler('createdate')" id="span_createdate">创建时间</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="unitid" name="model"/>"/></td>
			<td class="ali01"><bean:write name="model" property="unitname"/></td>
			<td class="ali02"><bean:write name="model" property="linkman"/></td>
			<td class="ali02"><bean:write name="model" property="job"/></td>
			<td class="ali02"><bean:write name="model" property="telephone"/></td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali02"><java2code:value codetype="status4" property="status" color="red" colorvalue="2"/></td>
			<td class="ali02">
			<div class="img_edit hand" title="修改" onclick="editThisRecord('sysUmsUnitInfoAction.do','<bean:write name="model" property="unitid"/>')"></div>
			<div class="img_user hand" title="单位用户" onclick="addUser('<bean:write name="model" property="unitid"/>')"></div>
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
	    document.pageForm.action = '/sysUmsUnitInfoAction.do?method=list';
   		document.pageForm.submit();
	}
	
	function addUser(unitid){
		var diag = new top.Dialog();
		diag.Title = "单位用户";
		diag.URL = '/sysUmsUnitUserInfoAction.do?method=list&unitid='+unitid;
		diag.Width = 800;
		diag.Height = 500;
		diag.ShowMaxButton=true;
		diag.show();
	}
	function importschools(){
	   var diag= new top.Dialog();
	   diag.Title="导入学校";
	   diag.URL='sysUmsUnitUserInfoAction.do?method=importSchool';
	   diag.Width=400;
	   diag.Height=300;
	   diag.ShowMaxButton=true;
	   diag.show();
	}

</script>
</body>
</html>