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
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount"/>

//提交表单
function postData(){
    document.pageForm.action = '/tkBookContentFilmAuditionAction.do?method=beforeAdd';
	document.pageForm.submit();
}
</script>
</head>
  <body>
    <form name="pageForm" method="post">
    <div class="box3" panelTitle="添加试听微课" style="margin-right:0px;padding-right:0px;">
     <table>
       <tr>
         <td>微课名称：</td>
		<td><input type="text" style="width:200px;" name="title" value="<bean:write name="title"/>"/></td>
         <td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
       </tr>
     </table>
    </div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="batchRecord('/tkBookContentFilmAuditionAction.do?method=addSave', '您确定添加微课到试听微课中?')"><span class="icon_reply">提交</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:history.back();"><span class="icon_recycle">返回</span></a>
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
			<th class="ali02">微课名称</th>
			<th class="ali02" width="80">微课类型</th>
			<th class="ali02" width="80">微课状态</th>
			<th class="ali02" width="80">作者</th>
			<th class="ali02" width="50">序号</th>
		</tr>
		<!--循环列出所有数据-->
	    <c:forEach items="${pagelist.datalist}" var="model" varStatus="status">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="fid" name="model"/>" /></td>
			<td class="ali02">${model.title }</td>
			<td class="ali02">
				<c:if test="${model.type=='1' }">
					解题微课
				</c:if>
				<c:if test="${model.type=='2' }">
					举一反三微课
				</c:if>
			</td>
			<td class="ali02">
				<c:if test="${model.status=='0' }">
					待审核
				</c:if>
				<c:if test="${model.status=='1' }">
					审核通过
				</c:if>
			</td>
			<td class="ali02">${model.sysUserInfo.username}</td>
			<td class="ali02">${model.orderindex }</td>
		</tr>
		</c:forEach>
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
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="<bean:write name="bookcontentid"/>" name="bookcontentid" id="bookcontentid"/>
</form>
  </body>
</html>
