<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseUser" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!--数字分页start-->
<script type="text/javascript" src="../../../libs/js/nav/pageNumber.js"></script>
<script type="text/javascript" src="../../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../../libs/js/sk/comm.js"></script>
<!--数字分页end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="课程用户" style="margin-right:0px;padding-right:0px;">
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
		<!--<a href="javascript:;" onclick="addRecord('/eduCourseUserAction.do?method=beforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>-->
		<a href="javascript:;" onclick="addUser('<bean:write name="unitid"/>','<bean:write name="courseid"/>','<bean:write name="courseclassid"/>','<bean:write name="courseusertype"/>')"><span class="icon_add">导入</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="delRecord('/eduCourseUserAction.do?method=delBatchRecord')"><span class="icon_delete">删除</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="updateUserVip()"><span class="icon_jing">批量设置VIP</span></a>
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
			<th class="ali02"><span onclick="sortHandler('flagl')" id="span_flagl">用户姓名</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('createdate')" id="span_createdate">创建时间</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('usertype')" id="span_usertype">用户类型</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('vip')" id="span_vip">院企用户</span></th>
			<th class="ali02" width="250">VIP有效期</th>
			<th class="ali02" width="70">操作</th>
		</tr>
		<!--循环列出所有数据-->
	   <%
				PageList pagelist = (PageList)request.getAttribute("pagelist");
				List list = pagelist.getDatalist();
				if(list != null && list.size() > 0){
				    Object[] obj = null;
				    EduCourseUser ecu = null;
				    SysUserInfo sui = null;
					for(int i=0, size=list.size(); i<size; i++){
						obj = (Object[])list.get(i);
						ecu = (EduCourseUser)obj[0];
						sui = (SysUserInfo)obj[1];
				%>
		<tr>
			<td><input type="checkbox" name="checkid" value="<%=ecu.getCourseuserid() %>"/></td>
			<td class="ali01"><%=sui.getUsername() %></td>
			<td class="ali02">
				<%if(ecu.getStatus().equals("1")){ %>
				正常
				<%}else if(ecu.getStatus().equals("2")){ %>
				禁用
				<%} %>
			</td>
			<td class="ali02"><%=ecu.getCreatedate() %></td>
			<td class="ali02">
				<%if(ecu.getUsertype().equals("1")){ %>
				专家教师
				<%}else if(ecu.getUsertype().equals("2")){ %>
				助教
				<%}else if(ecu.getUsertype().equals("3")){ %>
				学员
				<%} %>
			</td>
			<td class="ali02">
				<%if(ecu.getVip().equals("0")){ %>
				否
				<%}else if(ecu.getVip().equals("1")){ %>
				是
				<%} %>
			</td>
			<td class="ali02">
			<%if(ecu.getVip().equals("0")){ %>
				无
			<%}else if(ecu.getVip().equals("1")){ %>
				<%=ecu.getStartdate() %>~<%=ecu.getEnddate() %>
			<%} %>
			</td>
			<td class="ali02">
			<div class="img_find0 hand" title="查看" onclick="view('<%=ecu.getCourseuserid() %>')"></div>
			</td>
		</tr>
		<%}} %>
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
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="courseclassid" value="<bean:write name="courseclassid"/>"/>
<input type="hidden" name="courseusertype" value="<bean:write name="courseusertype"/>"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	function postData(){
	    document.pageForm.action = '/eduCourseUserAction.do?method=list';
   		document.pageForm.submit();
	}
	
	function view(courseuserid){
	    document.pageForm.action = '/eduCourseUserAction.do?method=view&courseuserid='+courseuserid;
   		document.pageForm.submit();
	}
	function updateUserVip(){
		var check = document.getElementsByName("checkid");
		var len=check.length;
		var idAll="";
		for(var i=0;i<len;i++){
			if(check[i].checked){
				idAll+=check[i].value+",";
			}
		}
		if(idAll !=""){
			if(confirm("您确定要将选中的用户设置为院企用户吗？")){
	 			var diag = new top.Dialog();
				diag.Title = "院企用户设置";
				diag.URL = '/eduCourseUserAction.do?method=updateUserVipOne&checkid='+idAll
				diag.Width = 800;
				diag.Height = 500;
				diag.ShowMaxButton=true;
				diag.CancelEvent = function(){
					postData();
		   			diag.close();
				};
				diag.show();
   			}
   		}else{
   			alert("您还没有选择要操作的记录呢!");
   			return false;
   		}
	}
	/* function updateUserVip(){
		var check = document.getElementsByName("checkid");
		var len=check.length;
		var idAll="";
		for(var i=0;i<len;i++){
			if(check[i].checked){
				idAll+=check[i].value+",";
			}
		}
		if(idAll !=""){
			if(confirm("您确定要将选中的用户设置为院企用户吗？")){
	 			document.pageForm.action = '/eduCourseUserAction.do?method=updateUserVip&checkid='+idAll;
   				document.pageForm.submit();
   			}
   		}else{
   			alert("您还没有选择要操作的记录呢!");
   			return false;
   		}
	} */
	function addUser(unitid,courseid,courseclassid,courseusertype){
		var diag = new top.Dialog();
		diag.Title = "课程用户";
		diag.URL = '/eduCourseUserAction.do?method=userList&unitid='+unitid+'&courseid='+courseid+'&courseclassid='+courseclassid+'&courseusertype='+courseusertype;
		diag.Width = 800;
		diag.Height = 500;
		diag.ShowMaxButton=true;
		diag.CancelEvent = function(){
			postData();
   			diag.close();
		};
		diag.show();
	}
</script>
</body>
</html>