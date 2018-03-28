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
   document.pageForm.action='tkPaperInfoAction.do?method=list';
   document.pageForm.submit();
 }
 function childrenQuestion(paperid){
   var diag = new top.Dialog();
   diag.Title="查看试题";
   diag.URL='/tkPaperInfoAction.do?method=detatilQuestions&paperid='+paperid;
   diag.Width=800;
   diag.Height=600;
   diag.ShowMaxButton=true;
   diag.CancelEvent=function(){
        diag.close();
      };
    diag.show();
   // window.open("/tkPaperInfoAction.do?method=detatilQuestions&paperid="+paperid,"_blank","scrollbars=yes,resizable=yes,heignt=800px,width=1000px");
 }
</script>
</head>
  
  <body>
    <form name="pageForm" method="post">
     <div class="box3" panelTitle="试卷管理" style="margin-right:0px;padding-right:0px;">
       <table>
           <tr>
             <td>试卷标题：</td>
             <td><input type="text" style="width: 200px;" name="title" value="${title }"/></td>
             <td>年级：</td>
              <td>
             <select id="grade" name="grade"  boxHeight="130" >
             <option value="">全部</option>
             <c:forEach items="${gradeLists }" var="g" >
             <option value="${g.gradeid }" ${grade eq g.gradeid?"selected":"" }>${g.gradename }</option>
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
		<a href="javascript:;" onclick="batchRecord('/tkPaperInfoAction.do?method=delBatchRecord', '您确定删除选中试卷')"><span class="icon_delete">删除</span></a>
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
			<th class="ali02" ><span onclick="sortHandler('title')" >试卷标题</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('papertype')" >试卷类型</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('gradeid')">所属年级</span> </th>
			<th class="ali02" width="120"><span onclick="sortHandler('createdate')">创建时间</span> </th>
            <th class="ali02" width="80"><span onclick="sortHandler('status')">状态</span></th>
			<th class="ali02" width="80">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="paperid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali01">${model.title }</td>
			<td class="ali02">
			<c:if test="${model.papertype==1 }">
			 普通卷
			</c:if>
			<c:if test="${model.papertype==2 }">
			手机卷
			</c:if>
			</td>
			<td class="ali02">${model.gradeName}</td>
			<td class="ali02">${model.createdate }</td>
			<td class="ali02">
			<c:if test="${model.status==1 }">
			 正常
			</c:if>
			<c:if test="${model.status==2 }">
			 禁用
			</c:if>
			</td>
			<td class="ali02">
		   <div class="img_edit hand" title="修改" onclick="editThisRecord('tkPaperInfoAction.do','<bean:write name="model" property="paperid"/>')"></div>	
		   <div class="img_find hand" title="查看试题" onclick="childrenQuestion('${model.paperid}');"></div>		
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
<input type="hidden" value="${subjectid }" name="subjectid"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
    </form>
  </body>
</html>
