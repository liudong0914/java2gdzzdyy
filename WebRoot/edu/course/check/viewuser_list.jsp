<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
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
<div class="box3" panelTitle="课程用户" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td>用户类型：</td>
			<td>
			<select name="usertype">
			    <option value="">请选择</option>	
  				<option value="1" <%if("1".equals(request.getAttribute("usertype")))out.print(" selected ");%>>专家教师</option>	
  				<option value="2" <%if("2".equals(request.getAttribute("usertype")))out.print(" selected ");%>>助教</option>	
  				<option value="3" <%if("3".equals(request.getAttribute("usertype")))out.print(" selected ");%>>学生</option>	
	 		</select>
			</td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>

<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02" width="150"><span onclick="sortHandler('flagl')" id="span_flagl">所属课程</span></th>
			<th class="ali02"><span onclick="sortHandler('flago')" id="span_flago">所属批次</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('flags')" id="span_flags">用户姓名</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('usertype')" id="span_usertype">用户类型</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02"><bean:write name="model" property="flagl"/></td>
			<td class="ali02"><bean:write name="model" property="flago"/></td>
			<td class="ali02"><bean:write name="model" property="flags"/></td>
			<td class="ali02">
				<c:if test="${model.usertype == '1' }">
				专家教师
				</c:if>
				<c:if test="${model.usertype == '2' }">
				助教
				</c:if>
				<c:if test="${model.usertype == '3' }">
				学生
				</c:if>
			</td>
			<td class="ali02">
			<c:if test="${model.status == '1' }">
				正常
				</c:if>
				<c:if test="${model.status == '2' }">
				禁用
				</c:if>
			</td>
		</tr>
		</logic:iterate>
	</table>
</div>
<input type="hidden" value="<bean:write name="courseid"/>" name="courseid"/>
<input type="hidden" value="<bean:write name="courseclassid"/>" name="courseclassid"/>

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
    	document.pageForm.action = '/eduCourseInfoAction.do?method=viewUserList';
  		document.pageForm.submit();
	
	}
	
	
</script>
</body>
</html>