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
  </head>
  <body>
   <table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="5">当前已关联问卷</th>
		</tr>
		<tr>
		 <td clias="ali03" style="width: 100px;">试卷名称：</td>
		 <td class="ali01">${papermodel.title }</td>
		 <td class="ali03" style="width: 100px;">创建时间：</td>
		 <td class="ali01">${papermodel.createdate }</td>
		 <td style="width: 200px;"><button type="button" onclick="deletePage()"><span class="icon_save">删除关联</span></button></td>
		</tr>
  </table>		
  <form name="pageForm" method="post">
   <div class="box3" panelTitle="试卷列表" style="margin-right:0px;padding-right:0px;">
    <table>
       <tr>
         <td>试卷名称：</td>
         <td><input type="text" name="title" value="${title }" /> </td>
         <td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
       </tr>
    </table>
   </div>
   <div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02"><span onclick="sortHandler('title')" >试卷名称</span></th>
			<th class="ali02"><span onclick="sortHandler('title')" >创建时间</span></th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="radio" name="checkid" value="<bean:write property="paperid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali02"><bean:write name="model" property="title"/> </td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
		</tr>
		</logic:iterate>
	    <tr>
	      <td colspan="3" style="text-align: center;">
	      <button type="button" onclick="selectPage()"><span class="icon_save">关联试卷</span></button>
	      <button type="button" onclick="closethis()"><span class="icon_back">关闭</span></button>
	      </td>
	    </tr>
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
<input type="hidden" name="bookcontenid" value="${bookcontenid }"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
  </form>
  <script type="text/javascript">
  var num=<bean:write name="pagelist" property="pageCount"/>
//提交表单
	function postData(){
	    document.pageForm.action ="tkBookContentAction.do?method=pagelist";
   		document.pageForm.submit();
	}
	function selectPage(){
        document.pageForm.action="tkBookContentAction.do?method=selectPage";
        document.pageForm.submit();	
	}
	function closethis(){
	  top.Dialog.close();
	}
	function deletePage(){
	    document.pageForm.action="tkBookContentAction.do?method=deletePage";
	    document.pageForm.submit();
	}
</script>
  </body>
</html>
