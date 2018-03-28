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
<script type="text/javascript">
 var num=<bean:write name="pagelist" property="pageCount"/>;
 function postData(){
    document.pageForm.action='tkBookContentAction.do?method=beforereplace';
    document.pageForm.submit(); 
 }
 function replaceQuestion(){
  document.pageForm.action='tkBookContentAction.do?method=replace';
  document.pageForm.submit();
  //top.Dialog.close();
 }
</script>
<!--数字分页end-->
  </head>
  <body>
    <form name="pageForm" method="post">
     <div class="box3" panelTitle="替换题库" style="margin-right:0px;padding-right:0px;">
      <table>
       <tr>
        <td>题目编号：</td>
        <td><input type="text" name="questionno" value="${questionno }"/> </td>
        <td>题目名称：</td>
        <td><input type="text" name="title" value="${title }" /> </td>
        <td>题目难度：</td>
        <td><java2code:option name="difficult" codetype="difficult" valuename="difficult" value="${difficult }" selectall="1"></java2code:option> </td>
        <td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
       </tr>
      </table>
     </div>
     <div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		</div>
	</div>
	</div>		
	</div>	
	<div class="clear"></div>
</div>
<div id="scrollContent">
  <table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
    <tr>
      <th width="25"></th>
      <th class="ali02" width="160"><span onclick="sortHandler('questionno')" id="span_title">题目编号</span></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_actor">题目名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('questiontype')">题型</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('difficult')">难易程度</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('status')">状态</span></th>
    </tr>
      <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="radio" name="checkid" value="<bean:write property="questionid" name="model"/>" /></td>
			<td class="ali02"><bean:write name="model" property="questionno"/></td>
			<td class="ali02"><bean:write name="model" property="title"/></td>
            <td class="ali02">
            <logic:equal value="O" name="model" property="questiontype" >客观题</logic:equal> 
            <logic:equal value="S" name="model" property="questiontype" >主观题</logic:equal>
            </td>
			<td class="ali02">
			<java2code:value codetype="difficult"  property="difficult"></java2code:value>
			</td>
			<td class="ali02">
			 <logic:equal value="1" name="model" property="status">正常</logic:equal>
			 <logic:equal value="2" name="model" property="status">禁用</logic:equal>
			</td>
		</tr>
		</logic:iterate>
		<tr>
		 <td colspan="6" class="ali02"><button type="button" onclick="replaceQuestion();"><span >替换确认</span></button></td>
		</tr>
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
<input type="hidden" value="<bean:write name="subjectid"/>" name="subjectid"/>
<input type="hidden" name="bookcontentid" value="${bookcontentid }"/>
<input type="hidden" name="questionid" value="${questionid }" />
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>	
    </form>  
  </body>
</html>
