<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo" %>
<%@page import="com.wkmk.edu.bo.EduCourseClass" %>
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
<div class="box3" panelTitle="课程审核" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>课程名称：</td>
			<td><input type="text" name="title" value="<bean:write name="title"/>"/></td>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td>所属分类：</td>
			<td>
			<select name="coursetypeid">
			    <option value="">请选择</option>	
  				<option value="1" <%if("1".equals(request.getAttribute("coursetypeid")))out.print(" selected ");%>>院校企业</option>	
  				<option value="2" <%if("2".equals(request.getAttribute("coursetypeid")))out.print(" selected ");%>>退役军人</option>	
  				<option value="3" <%if("3".equals(request.getAttribute("coursetypeid")))out.print(" selected ");%>>医护行业</option>	
	 		</select>
			</td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>

<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02" width="150"><span onclick="sortHandler('courseno')" id="span_courseno">课程编号</span></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_title">课程名称</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('coursetypeid')" id="span_coursetypeid">所属分类</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('price')" id="span_price">总价</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('createdate')" id="span_createdate">创建时间</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('sysUserInfo')" id="span_sysUserInfo">创建教师</span></th>
			<th class="ali02" width="80">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <%
			PageList pagelist = (PageList)request.getAttribute("pagelist");
			List list = pagelist.getDatalist();
			if(list != null && list.size() > 0){
			    EduCourseInfo eci = null;
				for(int i=0, size=list.size(); i<size; i++){
					eci = (EduCourseInfo)list.get(i);
			%>
		<tr>
			<td class="ali02"><%=eci.getCourseno() %></td>
			<td class="ali02"><%=eci.getTitle() %></td>
			<td class="ali02">
				<%if(eci.getCoursetypeid().equals("1")){ %>
				院校企业
				<%}else if(eci.getCoursetypeid().equals("2") ){ %>
				退役军人
				<%}else if(eci.getCoursetypeid().equals("3") ){ %>
				医护行业
				<%} %>
			</td>
			<td class="ali02">
				<%if(eci.getStatus().equals("0")){ %>
				私有
				<%}else if(eci.getStatus().equals("1") ){ %>
				审核通过
				<%}else if(eci.getStatus().equals("2") ){ %>
				发布待审核
				<%}else if(eci.getStatus().equals("3") ){ %>
				审核不通过
				<%}else if(eci.getStatus().equals("4") ){ %>
				禁用
				<%} %>
			</td>
			<td class="ali02"><%=eci.getPrice() %></td>
			<td class="ali02"><%=eci.getCreatedate() %></td>
			<td class="ali02"><%=eci.getSysUserInfo().getUsername() %></td>
			<td class="ali02">
				<!--  <div class="img_find hand" title="课程信息" onclick="view('<%=eci.getCourseid() %>')"></div>-->
				<div class="img_list hand" title="课程目录" onclick="viewColumn('<%=eci.getCourseid() %>')"></div>
				<div class="img_folder hand" title="课程资源" onclick="viewFile('<%=eci.getCourseid() %>')"></div>
				<div class="img_cd_driver hand" title="课程视频" onclick="viewFilm('<%=eci.getCourseid() %>')"></div>
				<div class="img_view hand" title="课程批次" onclick="viewClass('<%=eci.getCourseid() %>')"></div>
				<div class="img_user_off hand" title="课程用户" onclick="viewUser('<%=eci.getCourseid() %>')"></div>
				<div class="img_edit hand" title="课程审核" onclick="beforeEditCheck('<%=eci.getCourseid() %>')"></div>
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
    	document.pageForm.action = '/eduCourseInfoAction.do?method=checkCourseList';
  		document.pageForm.submit();
	
	}
	
	function beforeEditCheck(courseid){
		document.pageForm.action  = '/eduCourseInfoAction.do?method=beforeEditCheck&courseid='+courseid;
		document.pageForm.submit();
	}
	
	function view(courseid){
		var diag = new top.Dialog();
		diag.Title = "课程信息";
		diag.URL = '/eduCourseInfoAction.do?method=view&courseid='+courseid;
		diag.ShowMaxButton=true;
		diag.ShowMinButton=true;
		diag.show();
		diag.max();
	}

	function viewFile(courseid){
		var diag = new top.Dialog();
		diag.Title = "课程资源";
		//diag.URL = '/eduCourseInfoAction.do?method=viewFileMain&courseid='+courseid;
		diag.URL = '/eduCourseFileAction.do?method=adminMain&courseid='+courseid;
		diag.ShowMaxButton=true;
		diag.ShowMinButton=true;
		diag.show();
		diag.max();
	}
	
	function viewFilm(courseid){
		var diag = new top.Dialog();
		diag.Title = "课程视频";
		//diag.URL = '/eduCourseInfoAction.do?method=viewFilmMain&courseid='+courseid;
		diag.URL = '/eduCourseFilmAction.do?method=main&courseid='+courseid;
		diag.ShowMaxButton=true;
		diag.ShowMinButton=true;
		diag.show();
		diag.max();
	}
	
	function viewColumn(courseid){
		var diag = new top.Dialog();
		diag.Title = "课程目录";
		//diag.URL = '/eduCourseInfoAction.do?method=viewColumn&courseid='+courseid;
		diag.URL = '/eduCourseColumnAction.do?method=main&courseid='+courseid;
		diag.ShowMaxButton=true;
		diag.ShowMinButton=true;
		diag.show();
		diag.max();
	}
	
	function viewClass(courseid){
		var diag = new top.Dialog();
		diag.Title = "课程批次";
		diag.URL = '/eduCourseClassAction.do?method=main&courseid='+courseid;
		diag.ShowMaxButton=true;
		diag.ShowMinButton=true;
		diag.CancelEvent = function(){
			postData();
   			diag.close();
		};
		diag.show();
		diag.max();
	}
	
	function viewUser(courseid){
		var diag = new top.Dialog();
		diag.Title = "课程用户";
		//diag.URL = '/eduCourseInfoAction.do?method=viewUserMain&courseid='+courseid;
		diag.URL = '/eduCourseUserAction.do?method=main&courseid='+courseid;
		diag.ShowMaxButton=true;
		diag.ShowMinButton=true;
		diag.show();
		diag.max();
	}
</script>
</body>
</html>