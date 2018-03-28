<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkBookContentGoodfilm"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@ page import="java.util.List"%>
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
 
 function init(){
	 alert("11");
	 
 }
</script>
</head>
  <body>
    <form name="pageForm" method="post">
     <div class="box3" panelTitle="作业本章节管理" style="margin-right:0px;padding-right:0px;">
      <table>
       <tr>
          <td>章节编号：</td>
          <td><input type="text"  name="contentno" value="${contentno }"/></td>
          <td>章节名称：</td>
          <td><input type="text" style="width: 200px;" name="title" value="${title}"/></td>
          <td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
       </tr>
      </table>
     </div>
  
	<div class="clear"></div>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02" width="80px;">章节编号</th>
			<th class="ali02">章节名称</th>
			<th class="ali02" width="300">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="bookcontentid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali02"><bean:write name="model" property="contentno"/> </td>
			<td class="ali02"><bean:write name="model" property="title"/></td>
			<logic:equal value="10" name="model" property="flago">
				<td class="ali02"><a href="tkBookContentGoodfilmAction.do?method=setGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=1&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: green ">设为精品解题微课</a></td>
			</logic:equal>
			<logic:equal value="11" name="model" property="flago">	
				<td class="ali02"><a href="tkBookContentGoodfilmAction.do?method=delGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=1&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: red ">删除精品解题微课</a></td>	
			</logic:equal>
			<logic:equal value="20" name="model" property="flago">
				<td class="ali02"><a href="tkBookContentGoodfilmAction.do?method=setGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=2&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: green ">设为精品举一反三微课</a></td>
			</logic:equal>
			<logic:equal value="21" name="model" property="flago">
				<td class="ali02"><a href="tkBookContentGoodfilmAction.do?method=delGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=2&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: red ">删除精品举一反三微课</a></td>
			</logic:equal>	
			<logic:equal value="300" name="model" property="flago">
				<td class="ali02"><a href="tkBookContentGoodfilmAction.do?method=setGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=1&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: green ">设为精品微课</a>
				/<a href="tkBookContentGoodfilmAction.do?method=setGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=2&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: green ">设为精品举一反三微课</a></td>
			</logic:equal>
			<logic:equal value="311" name="model" property="flago">
				<td class="ali02"><a href="tkBookContentGoodfilmAction.do?method=delGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=1&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: red ">删除精品微课</a>
				/<a href="tkBookContentGoodfilmAction.do?method=setGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=2&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: green ">设为精品举一反三微课</a></td>
			</logic:equal>
			<logic:equal value="312" name="model" property="flago">
				<td class="ali02"><a href="tkBookContentGoodfilmAction.do?method=setGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=1&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: green ">设为精品微课</a>
				/<a href="tkBookContentGoodfilmAction.do?method=delGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=2&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: red ">删除精品举一反三微课</a></td>
			</logic:equal>
            <logic:equal value="313" name="model" property="flago">
				<td class="ali02"><a href="tkBookContentGoodfilmAction.do?method=delGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=1&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: red ">删除精品微课</a>
				/<a href="tkBookContentGoodfilmAction.do?method=delGood&bookcontentid=${model.bookcontentid }&bookid=${bookid }&parentno=${parentno }&type=2&pager.pageNo=<bean:write name="pagelist" property="curPage"/>" style="COLOR: red ">删除精品举一反三微课</a></td>
			</logic:equal>  
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
<input type="hidden" name="bookid" value="${bookid }"/>
<input type="hidden" name="parentno" value="${parentno }"/>
<input type="hidden" name="pageno" value="${pageno }"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
  var num=<bean:write name="pagelist" property="pageCount"/>
//提交表单
	function postData(){
	    document.pageForm.action = '/tkBookContentGoodfilmAction.do?method=list&bookcontentid=${model.bookcontentid }&bookid=${bookid}&parentno=${parentno}&pageno=${pageno}';
   		document.pageForm.submit();
	}
	
</script>
</body>
</html>
