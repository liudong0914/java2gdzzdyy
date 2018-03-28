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
 function postData(){
   document.pageForm.action='/tkQuestionsInfoAction.do?method=unfilmInfo';
   document.pageForm.submit();
 }
 function saveRecords(){
   document.pageForm.action='/tkQuestionsInfoAction.do?method=batchaddFilminfo&questionid=${questionid}';
   document.pageForm.submit();
 }
 function back(){
  document.pageForm.reset();
       document.getElementById("pageNo").value="1";
     document.getElementById("pageSize").value="10";
  document.pageForm.action='/tkQuestionsInfoAction.do?method=filmInfo&questionid=${questionid}';
  document.pageForm.submit();
 }
 
  function detail(filmid){
	 document.pageForm.action='/tkQuestionsInfoAction.do?method=filmDetail&filmid='+filmid;
	 document.pageForm.submit();   
 }
</script>
</head>
  <body>
   <form name="pageForm" method="post">
   <div class="box3" panelTitle="未关联微课列表" style="margin-right:0px;padding-right:0px;">
    <table>
      <tr>
        <td>微课名称：</td>
        <td><input type="text" style="width: 200px;" name="title" value="${title }"> </td>
        <td>主讲教师：</td>
        <td><input type="text" name="actor" value="${actor }"/></td>
        <td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
      <tr>
    </table>
    </div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_title">微课名称</span></th>
			<th class="ali02" width="70"><span onclick="sortHandler('actor')" id="span_actor">主讲教师</span></th>
			<th class="ali02" width="40">推荐</th>
			<th class="ali02" width="50">上传用户</th>
			<th class="ali02" width="120"><span onclick="sortHandler('createdate')" id="span_createdate">上传时间</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="filmid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali01"><span class="text_slice" style="width:150px;" title="<bean:write name="model" property="title"/>"><bean:write name="model" property="title"/></span></td>
			<td class="ali02"><bean:write name="model" property="actor"/></td>
			<td class="ali02"><java2code:value codetype="boolean" property="recommand" color="blue" colorvalue="1"></java2code:value></td>
			<td class="ali02"><bean:write name="model" property="sysUserInfo.username"/></td>
			<td class="ali02"><java2page:write name="model" property="createdate"/></td>
			<td class="ali02">
			<div class="img_find0 hand" title="详情" onclick="detail(${model.filmid})"></div>	
			</td>
		</tr>
		</logic:iterate>
		<tr>
		  <td colspan="8" class="ali02">
		   <button type="button" class="margin_right5" onclick="saveRecords()" id="btnsave"><span class="icon_save">保存</span></button>
           <button type="button" onclick="back()"><span class="icon_back">返回</span></button>            
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
<input type="hidden" name="questionid" value="${questionid }"/>
<input type="hidden" name="title" value="${title }"/>
<input type="hidden" name="actor" value="${actor }"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
   </form>
  </body>
</html>
