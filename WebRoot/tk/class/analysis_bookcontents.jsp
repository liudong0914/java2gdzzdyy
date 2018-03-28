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
<div class="box3" panelTitle="作业列表" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>章节编号：</td>
			<td><input type="text" name="contentno" value="<bean:write name="contentno"/>"/></td>
			<td>章节名称：</td>
			<td><input type="text" name="title" value="<bean:write name="title"/>"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02"><span onclick="sortHandler('contentno')" id="span_contentno">章节编号</span></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_title">章节名称</span></th>
			<th class="ali02" width="60">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali01"><bean:write name="model" property="contentno"/></td>
			<td class="ali01"><bean:write name="model" property="title"/></td>
			<td class="ali02">
			<c:if test="${model.parentno ne '0000'}">
			<div style="margin-left:20px;" class="img_user_group hand" onclick="getStudents(${classid},${model.bookcontentid},${model.paperid})" title="作业分析"></div>
			</c:if>
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
<input type="hidden" value="<bean:write name="classid"/>" name="classid"/>
<input type="hidden" value="<bean:write name="bookid"/>" name="bookid"/>
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkClassInfoAction.do?method=getAnalysisBookcontents';
   		document.pageForm.submit();
	
	}
	
	function getStudents(classid,bookcontentid,paperid){
		var diag = new top.Dialog();
		diag.Title = "作业分析";
		diag.URL = '/tkPaperAnswerAction.do?method=main&classid='+classid+'&bookcontentid='+bookcontentid+'&paperid='+paperid;
		diag.Width = 1100;
		diag.Height = 900;
		diag.ShowMaxButton=true;
		diag.show();
	}
</script>
</body>
</html>