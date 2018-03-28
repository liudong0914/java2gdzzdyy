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
   document.pageForm.action='tkQuestionsInfoAction.do?method=similarQuestion';
   document.pageForm.submit();
  }
  	function detail(id){
	    document.pageForm.action='/tkQuestionsInfoAction.do?method=detail&id='+id;
	    document.pageForm.submit();   
	}
 
</script>
</head>
  <body>
    <form name="pageForm" method="post">
    <div class="box3" panelTitle="已关联题列表" style="margin-right:0px;padding-right:0px;">
     <table>
       <tr>
         <td>题目编号：</td>
         <td><input type="text"  name="questionno" value="${questionno }"/> </td>
         <td>题目名称：</td>
         <td><input type="text" name="title" value="${title }"/> </td>
         <td>题目难度：</td>
         <td><java2code:option  name="difficult" codetype="difficult" valuename="difficult" value="${difficult }"  selectall="1"/></td>
         <td>题目类型：</td>
         <td>
         <select name="type">
         <option value="">全部</option>
         <c:forEach items="${typelist }" var="t">
         <option value="${t.typeid }" ${typeid eq t.typeid?"selected":"" }>${t.typename }</option>
         </c:forEach>
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
		<a href="javascript:;" onclick="addRecord('/tkQuestionsInfoAction.do?method=unsimilarQuestion&questionid=${questionid}')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/tkQuestionsInfoAction.do?method=bathdelsimilarQuestion&questionid=${questionid}', '您确定移除选中题目?')"><span class="icon_delete">移除</span></a>
		<div class="clear"></div>
		</div>
	</div>		
	</div>	
	</div>
	<div class="clear"></div>
</div>
    <div id="scrollContent" style="height: 100%">
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th width="25"></th>
			<th class="ali02" width="160"><span onclick="sortHandler('questionno')" id="span_title">题目编号</span></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_actor">题目名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('questiontype')">题型</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('difficult')">难易程度</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('a.tkQuestionsType.type')">题目类型</th>
			<th class="ali02" width="100"><span onclick="sortHandler('status')">状态</span></th>
			<th class="ali02" width="100">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="questionid" name="model"/>" /></td>
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
			 ${model.tkQuestionsType.typename }
			</td>
			<td class="ali02">
			 <logic:equal value="1" name="model" property="status">正常</logic:equal>
			 <logic:equal value="2" name="model" property="status">禁用</logic:equal>
			</td>
			<td class="ali02" ">
			<div class="img_find0 hand"  title="详情" onclick="detail(${model.questionid})"></div>	
			</td>
		</tr>
		</logic:iterate>
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
<input type="hidden" name="questionid" value="${questionid }" />
<input type="hidden" name="questiontypeid" value="${questiontypeid }"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
  </body>
</html>
