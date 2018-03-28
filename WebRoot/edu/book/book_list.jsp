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
<div class="box3" panelTitle="教材课本管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>标题：</td>
			<td><input type="text" name="title" value="<bean:write name="title"/>" style="width: 240px"/></td>
			<td>副标题：</td>
			<td><input type="text" name="subtitle" value="<bean:write name="subtitle"/>" style="width: 240px"/></td>
		</tr>
		<tr>
			<td>状态：</td>
			<td><java2code:option name="status" codetype="status4" valuename="status" selectall="3"/></td>
			<td>教材所属分类：</td>
			<td>
				<select name="coursetypeid">
				 	<option value="">请选择</option>	
	  				<option value="1" <%if("1".equals(request.getAttribute("coursetypeid")))out.print(" selected ");%>>院校企业</option>	
	  				<option value="2" <%if("2".equals(request.getAttribute("coursetypeid")))out.print(" selected ");%>>退役军人</option>	
	  				<option value="3" <%if("3".equals(request.getAttribute("coursetypeid")))out.print(" selected ");%>>医护行业</option>	
				</select>
			</td>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/eduBookInfoAction.do?method=beforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<!--  <a href="javascript:;" onclick="batchRecord('/eduBookInfoAction.do?method=delBatchRecord','确定要禁用所选中教材课本?')"><span class="icon_no">禁用</span></a>-->
		<a href="javascript:;" onclick="batchRecord('/eduBookInfoAction.do?method=delBatchRecord','确定要删除所选中教材课本?')"><span class="icon_delete">删除</span></a>
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
			<th class="ali02" width="60"><span onclick="sortHandler('orderindex')" id="span_orderindex">序号</span></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_title">标题</span></th>
			<th class="ali02" width="180"><span onclick="sortHandler('subtitle')" id="span_subtitle">副标题</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('price')" id="span_price">定价</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('coursetypeid')" id="span_coursetypeid">所属分类</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('createdate')" id="span_createdate">创建时间</span></th>
			<th class="ali02" width="60">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="bookid" name="model"/>"/></td>
			<td class="ali02"><bean:write name="model" property="orderindex"/></td>
			<td class="ali02"><bean:write name="model" property="title"/></td>
			<td class="ali02"><bean:write name="model" property="subtitle"/></td>
			<td class="ali02"><java2code:value codetype="status4" property="status"/></td>
			<td class="ali02"><bean:write name="model" property="price"/></td>
			<td class="ali02">
				<c:if test="${model. coursetypeid == '1'}">
				院校企业
				</c:if>
				<c:if test="${model. coursetypeid == '2'}">
				退役军人
				</c:if>
				<c:if test="${model. coursetypeid == '3'}">
				医护行业
				</c:if>
			</td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali02">
			<div class="img_edit0 hand" title="修改" onclick="editThisRecord('eduBookInfoAction.do','<bean:write name="model" property="bookid"/>')"></div>
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
	    document.pageForm.action = '/eduBookInfoAction.do?method=list';
   		document.pageForm.submit();
	
	}
</script>
</body>
</html>