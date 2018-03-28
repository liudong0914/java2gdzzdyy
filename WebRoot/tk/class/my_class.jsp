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
<div class="box3" panelTitle="班级列表" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>班级编号：</td>
			<td><input type="text" name="classno" value="<bean:write name="classno"/>"/></td>
			<td>班级名称：</td>
			<td><input type="text" name="classname" value="<bean:write name="classname"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02"><span onclick="sortHandler('classno')" id="span_classno">班级编号</span></th>
			<th class="ali02" ><span onclick="sortHandler('classname')" id="span_classname">班级名称</span></th>
			<th class="ali02" >课本名称</th>
			<th class="ali02" ><span onclick="sortHandler('students')" id="span_students">学员数量</span></th>
			<th class="ali02" width="60">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="classes" indexId="index">
		<tr>
			<td class="ali01"><bean:write name="model" property="classno"/></td>
			<td class="ali02"><bean:write name="model" property="classname"/></td>
			<td class="ali02"><bean:write name="model" property="bookname"/></td>
			<td class="ali02"><bean:write name="model" property="students"/></td>
			<td class="ali02">
			<div style="margin-left:10px;" class="img_find hand" title="详情" onclick="detail('<bean:write name="model" property="classid"/>')"></div>
			<div class="img_edit hand" title="班级作业" style="background-image:url('../libs/icons/view.gif');" onclick="classBookContent('<bean:write name="model" property="classid"/>')"></div>
			</td>
		</tr>
		</logic:iterate>
	</table>

<div style="height:35px;">
	<div class="float_left padding5">
		共<bean:write name="pagelist" property="totalCount"/>条记录
	</div>
	<div class="float_right padding5">
		<div class="pageNumber" total="<bean:write name="pagelist" property="totalCount"/>" pageSize="<bean:write name="pagesize"/>" page="<bean:write name="pagelist" property="curPage0"/>" showSelect="true" showInput="true" id="pageContent"></div>
	</div>
	<div class="clear"></div>
</div>
</div>
<!-- 分页与排序 -->
<input type="hidden" value="${tag}" name="tag"/>
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkClassInfoAction.do?method=getClassListByUser';
   		document.pageForm.submit();
	
	}
	
	function detail(classid){
	location.href="/tkClassInfoAction.do?method=detail&classid="+classid;
	}
	
	function classBookContent(classid){
		var diag = new top.Dialog();
		diag.Title = "作业列表";
		diag.URL = '/tkClassInfoAction.do?method=getBookContentByClass&classid='+classid;
		diag.Width = 1000;
		diag.Height = 800;
		diag.ShowMaxButton=true;
		diag.show();
		}
</script>
</body>
</html>