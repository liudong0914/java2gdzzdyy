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
  var num=<bean:write name="pagelist" property="pageCount"/>;
  function postData(){
   document.pageForm.action='tkBookContentPowerAction.do?method=questionlilst';
   document.pageForm.submit();
  }
  	function detail(id){
	    document.pageForm.action='/tkQuestionsInfoAction.do?method=detail&id='+id;
	    document.pageForm.submit();   
	}
	
	function replace(bookcontentid,questionid){
 var diag=new top.Dialog();
 diag.URL='/tkBookContentAction.do?method=beforereplace&bookcontentid='+bookcontentid+'&questionid='+questionid;
 diag.Width=1000;
 diag.Height=800;
  diag.ShowMaxButton=true;
     diag.CancelEvent=function(){
        postData();
        diag.close();
      };
      diag.show();
 }
</script>
</head>
  <body>
    <form name="pageForm" method="post">
    <div class="box3" panelTitle="题库管理" style="margin-right:0px;padding-right:0px;">
     <table>
       <tr>
         <td>题目编号：</td>
         <td><input type="text"  name="questionno" value="${questionno }"/> </td>
         <td>题目名称：</td>
         <td><input type="text" name="title" value="${title }"/> </td>
         <td>题目难度：</td>
         <td><java2code:option  name="difficult" codetype="difficult" valuename="difficult" value="${difficult }"  selectall="1"/></td>
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
    <div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02" width="50">题号</th>
			<th class="ali02">题目名称</th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <c:forEach items="${pagelist.datalist}" var="model" varStatus="status">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="questionid" name="model"/>" /></td>
			<td class="ali02">第${((pagelist.curPage-1)*pagesize)+status.index + 1}题</td>
			<td class="ali02">${model.title }</td>
			<td class="ali02">
			<c:if test="${type=='1' }">
			<div class="img_find0 hand" title="详情" onclick="detail(${model.questionid})"></div>	
			</c:if>
			<c:if test="${type=='2' }">
			<div class="img_find0 hand" style="background-image: url(../libs/icons/refresh.gif);" title="替换" onclick="replace(${bookcontentid},${model.questionid })"></div>
			</c:if>
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
<input type="hidden" value="${type }" name="type"/>
<input type="hidden" value="${bookcontentid }" name="bookcontentid"/>
<input type="hidden" value="${paperid }" name="paperid"/>
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
  </body>
</html>
