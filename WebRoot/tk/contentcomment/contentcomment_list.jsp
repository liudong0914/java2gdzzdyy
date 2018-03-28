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
    document.pageForm.action = '/tkBookContentCommentAction.do?method=list';
	document.pageForm.submit();
}
function editFilm(obj){
	document.pageForm.action = '/tkBookContentCommentAction.do?method=beforeUpdate&objid='+obj;
	document.pageForm.submit();
}
</script>
</head>
  <body>
    <form name="pageForm" method="post">
    <div class="box3" panelTitle="微课审核" style="margin-right:0px;padding-right:0px;">
     <table>
       <tr>
         <td>评价用户：</td>
		 <td><input type="text" style="width:200px;" name="username" value="${username}"/></td>
		 <td>微课类型：</td>
		 <td>
		 	<select name="type">
		 		<option value="">--请选择--</option>
		 		<option value="1" ${type eq '1'?"selected":"" }>解题微课</option>
		 		<option value="2" ${type eq '2'?"selected":"" }>举一反三</option>
		 	</select>
		 </td>
		 <td>状态：</td>
		 <td>
			<select name="status">
		 		<option value="">--请选择--</option>
		 		<option value="1" ${status eq '1'?"selected":"" }>显示</option>
		 		<option value="2" ${status eq '2'?"selected":"" }>禁用</option>
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
		<a href="javascript:batchRecord('/tkBookContentCommentAction.do?method=checkBatchRecord&bookid=${bookid}&bookcontentid=${bookcontentid}', '您确定禁用选中评价?');"><span class="icon_reply">批量禁用</span></a>
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
			<th class="ali02">评价用户</th>
			<th class="ali02" width="120">评价时间</th>
			<th class="ali02" width="80">评分</th>
			<th class="ali02" width="80">微课类型</th>
			<th class="ali02" width="50">状态</th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <c:forEach items="${pagelist.datalist}" var="model" varStatus="status">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="commentid" name="model"/>" /></td>
			<td class="ali02">${model.sysUserInfo.username }</td>
			<td class="ali02">${model.createdate}</td>
			<td class="ali02">${model.score}</td>
			<td class="ali02">
				${model.type eq '1'?"解题微课":model.type eq '2'?"举一反三":""}
			</td>
			<td class="ali02">
				${model.status eq '1'?"显示":model.status eq '2'?"<span style='color:#ff0000;'>禁用</span>":""}
			</td>
			<td class="ali02">
				<div class="img_edit0 hand" title="修改" onclick="editFilm('${model.commentid}')"></div>
			</td>
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
<input type="hidden" value="<bean:write name="bookcontentid"/>" name="bookcontentid" />
<input type="hidden" value="<bean:write name="bookid"/>" name="bookid"/>
</form>
  </body>
</html>
