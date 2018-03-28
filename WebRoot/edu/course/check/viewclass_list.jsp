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
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="课程批次" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>批次名称：</td>
			<td><input type="text" name="classname" value="<bean:write name="classname"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>

<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02"><span onclick="sortHandler('classname')" id="span_classname">批次名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('startdate')" id="span_startdate">开始时间</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('enddate')" id="span_enddate">结束时间</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('usercount')" id="span_usercount">批次人数</span></th>
			<th class="ali02" width="80">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02">
				<bean:write name="model" property="classname"/>
			</td>
			<td class="ali02">
			  <c:if test="${model.status == '0' }">
			 	 待发布
			  </c:if>
			  <c:if test="${model.status == '1' }">
			 	 审核通过
			  </c:if>
			  <c:if test="${model.status == '2' }">
			 	 发布待审核
			  </c:if>
			  <c:if test="${model.status == '3' }">
			 	 关闭
			  </c:if>
			</td>
			<td class="ali02"><bean:write name="model" property="startdate"/></td>
			<td class="ali02"><bean:write name="model" property="enddate"/></td>
			<td class="ali02"><bean:write name="model" property="usercount"/></td>
			<td class="ali02">
				<c:if test="${model.flagl == '1' }">
					<div class="padding_top5 padding_left10">
					<a href="javascript:;" onclick="passClass('<bean:write name="model" property="courseclassid"/>')"><span class="icon_ok">审核通过</span></a>
					<a href="javascript:;" onclick="noClass('<bean:write name="model" property="courseclassid"/>')"><span class="icon_delete">审核不通过</span></a>
					<a href="javascript:;" onclick="stopClass('<bean:write name="model" property="courseclassid"/>')"><span class="icon_remove">禁用</span></a>
					</div>
				</c:if>
				<c:if test="${model.flagl != '1' }">
					该批次已审核
				</c:if>
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
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
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
	    document.pageForm.action = '/eduCourseInfoAction.do?method=viewClass&courseid='+${courseid};
   		document.pageForm.submit();
	}
	//审核通过
	function passClass(courseclassid){
	    document.pageForm.action = '/eduCourseInfoAction.do?method=passClass&courseclassid='+courseclassid;
   		document.pageForm.submit();
	}
	
	//审核不通过
	function noClass(courseclassid){
	    document.pageForm.action = '/eduCourseInfoAction.do?method=noClass&courseclassid='+courseclassid;
   		document.pageForm.submit();
	}
	
	//禁用
	function stopClass(courseclassid){
	    document.pageForm.action = '/eduCourseInfoAction.do?method=stopClass&courseclassid='+courseclassid;
   		document.pageForm.submit();
	}
</script>
</body>
</html>