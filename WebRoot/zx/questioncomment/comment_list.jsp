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
<div class="box3" panelTitle="评论管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>姓名：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td>状态：</td>
			<td>
				<select name="status">
				    <option value="">请选择</option>		
	  				<option value="1" <%if("1".equals(request.getAttribute("status")))out.print(" selected ");%>>显示</option>	
	  				<option value="2" <%if("2".equals(request.getAttribute("status")))out.print(" selected ");%>>禁用</option>	
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
		<a href="javascript:;" onclick="batchRecord('/zxHelpQuestionCommentAction.do?method=batchDisable', '您确定禁用选中评论?')"><span class="icon_add">批量禁用</span></a>
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
			<th class="ali02" width="250">评论用户</th>
			<th class="ali02" width="60"><span onclick="sortHandler('userip')" id="span_userip">用户ip</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('score')" id="span_score">评分</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('createdate')" id="span_createdate">时间</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" <logic:equal value="2" name="model" property="status">disabled="disabled"</logic:equal> value="<bean:write property="commentid" name="model"/>" /></td>
			<td class="ali02"><bean:write name="model" property="sysUserInfo.username"/></td>
			<td class="ali02"><bean:write name="model" property="userip"/></td>
			<td class="ali02"><bean:write name="model" property="score"/></td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali02">
				<c:if test="${model.status eq '1'}">
					显示
				</c:if>
				<c:if test="${model.status eq '2'}">
					禁用
				</c:if>
			</td>
			<td class="ali02">
				<div class="img_edit0 hand" title="修改" onclick="editThisRecord('zxHelpQuestionCommentAction.do','<bean:write name="model" property="commentid"/>')"></div>
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
	    document.pageForm.action = '/zxHelpQuestionCommentAction.do?method=list';
   		document.pageForm.submit();
	}
</script>
</body>
</html>