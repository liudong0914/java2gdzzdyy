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
  $(function(){ 
    <c:if test="${reloadtree==1}">
    parent.rfrmleft.location ='/tkBookContentAction.do?method=tree';
    </c:if>
  });
  function tkcontentQuestion(a,b){
   var diag=new top.Dialog();
   diag.Title="习题管理";
   diag.URL='/tkBookContentQuestionAction.do?method=list&bookid=${bookid}&bookcontenid='+a+'&type='+b;
   diag.Width=1000;
   diag.Height=550;
   diag.ShowMaxButton=true;
   diag.CancelEvent=function(){
        diag.close();
      };
    diag.show();
  }
  function selectPage(bookcontenid){
    var diag=new top.Dialog();
    diag.Title="关联试卷";
    diag.URL='/tkBookContentAction.do?method=pagelist&bookcontenid='+bookcontenid;
    diag.Width=1000;
    diag.Height=800;
    diag.ShowMaxButton=true;
    diag.CancelEvent=function(){
         diag.close();
       };
       diag.show();
  }
  function uploadMp3(bookcontenid){
   var diga=new top.Dialog();
   diag.Title="上传MP3";
   diag.URL='/tkBookContentAction.do?method=beforeuploadMp3&bookcontentid='+bookcontentid;
   diag.Width=1000;
   diag.Height=800;
   diag.ShowMaxButton=true;
   diag.CancelEvent=function(){
         diag.close();
       };
      diag.show();
  }
  function replaceQuestion(bookcontenid){
    //document.pageForm.action='/tkBookContentAction.do?method=replaceQuestion?bookcontenid='+bookcontenid;
    //document.pageForm.submit();
    this.location.href='/tkBookContentAction.do?method=replaceQuestion&bookcontentid='+bookcontenid;
  }
  function tkcontentPrice(bookcontenid){
    var diag=new top.Dialog();
    diag.Title="修改价格";
    diag.URL='/tkBookContentPriceAction.do?method=list&bookcontenid='+bookcontenid;
    diag.Width=1000;
    diag.Height=800;
    diag.ShowMaxButton=true;
    diag.CancelEvent=function(){
         diag.close();
       };
       diag.show();
  }
  
</script>
</head>
  <body >
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
     <div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/tkBookContentAction.do?method=beforeAdd&bookid=${bookid}&parentno=${parentno }')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/tkBookContentAction.do?method=delBatchRecord', '您确定删除选中章节内容?')"><span class="icon_delete">删除</span></a>
		
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
			<th class="ali02" width="80px;"><span onclick="sortHandler('contentno')" >章节编号</span></th>
			<th class="ali02"><span onclick="sortHandler('title')" >章节名称</span></th>
			<th class="ali02" width="120">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="bookcontentid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali02"><bean:write name="model" property="contentno"/> </td>
			<td class="ali02"><bean:write name="model" property="title"/></td>
			<td class="ali02">
			  <c:if test="${model.parentno=='0000' }">	
			  <div class="img_edit0 hand" title="修改" onclick="editThisRecord('/tkBookContentAction.do','${model.bookcontentid}')"></div>	 
			  </c:if>
           <c:if test="${model.parentno!='0000' }">			
			<div class="img_edit hand" title="修改" onclick="editThisRecord('/tkBookContentAction.do','${model.bookcontentid}')"></div>	
			<div class="img_edit hand" title="课前预习题" style="background-image: url(../libs/icons/view.gif);" onclick="tkcontentQuestion(${model.bookcontentid},'1')" ></div>
			<div class="img_edit hand" title="教学案题"  style="background-image: url(../libs/icons/view2.gif);" onclick="tkcontentQuestion(${model.bookcontentid},'2')" ></div>
			<div class="img_edit hand" title="指定试卷" style="background-image: url(../libs/icons/page.gif);" onclick="selectPage(${model.bookcontentid})" ></div>
			<div class="img_edit hand" title="替换试题" style="background-image: url(../libs/icons/edit2.gif);" onclick="replaceQuestion(${model.bookcontentid})" ></div>
		    <div class="img_edit hand" title="修改价格"  style="background-image: url(../libs/icons/chart.gif);" onclick="tkcontentPrice(${model.bookcontentid})" ></div>

			
			</c:if>
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
<input type="hidden" name="bookid" value="${bookid }"/>
<input type="hidden" name="parentno" value="${parentno }"/>
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
	    document.pageForm.action = '/tkBookContentAction.do?method=list&bookid=${bookid}&parentno=${parentno}';
   		document.pageForm.submit();
	}
	
</script>
</body>
</html>
