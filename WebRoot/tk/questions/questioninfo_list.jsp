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
   document.pageForm.action='tkQuestionsInfoAction.do?method=list';
   document.pageForm.submit();
  }
  	function detail(id){
	    document.pageForm.action='/tkQuestionsInfoAction.do?method=detail&id='+id;
	    document.pageForm.submit();   
	}
    function childrenQuestions(id){
      var diag=new top.Dialog();
      diag.Title="子题管理";
      diag.URL='/tkQuestionsInfoAction.do?method=childreaQuestion&subjectid=${subjectid}&parentid='+id;
      diag.Width=1000;
      diag.Height=800;
      diag.ShowMaxButton=true;
      diag.CancelEvent=function(){
         postData();
         diag.close();
      };
      diag.show();
    }
    function similarQuestion(id){
     var diag=new top.Dialog();
     diag.Title="举一反三";
     diag.URL='/tkQuestionsInfoAction.do?method=similarQuestion&questionid='+id;
     diag.Width=1000;
     diag.Height=800;
     diag.ShowMaxButton=true;
     diag.CancelEvent=function(){
        postData();
        diag.close();
      };
      diag.show();
    }
    function filmInfo(id){
     var diag=new top.Dialog();
     diag.Title="微课关联";
     diag.URL='/tkQuestionsInfoAction.do?method=filmInfo&questionid='+id;
     diag.Width=1000;
     diag.Height=800;
     diag.ShowMaxButton=true;
      diag.CancelEvent=function(){
        postData();
        diag.close();
      };
       diag.show();
    }
    function exportTwocode(subjectid){
    	document.pageForm.action='/tkQuestionsInfoAction.do?method=exportTwocode';
	    document.pageForm.submit();
        //this.location.href='/tkQuestionsInfoAction.do?method=exportTwocode&subjectid='+subjectid;
    }
</script>
</head>
  <body>
    <form name="pageForm" method="post">
    <div class="box3" panelTitle="题库管理" style="margin-right:0px;padding-right:0px;">
     <table>
       <tr>
         <td>题目名称：</td>
         <td><input type="text" name="title" value="${title }"/> </td>
         <td>题目难度：</td>
         <td><java2code:option  name="difficult" codetype="difficult" valuename="difficult" value="${difficult }"  selectall="1"/></td>
         <td>年级：</td>
         <td>
          <select id="grade" name="grade"  boxHeight="130" >
         <option value="">全部</option>
         <c:forEach items="${gradeLists }" var="g" >
         <option value="${g.gradeid }" ${grade eq g.gradeid?"selected":"" }>${g.gradename }</option>
         </c:forEach>
         </select>
          </td>
          <td>标签：</td>
          <td><input type="text" name="tag" value="${tag }"/> </td>
         <td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
       </tr>
     </table>
    </div>
   <div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/tkQuestionsInfoAction.do?method=beforeAdd&questiontypeid=${questiontypeid}&subjectid=${subjectid }')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/tkQuestionsInfoAction.do?method=updateStatus', '您确定禁用选中题目?')"><span class="icon_delete">禁用</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="exportTwocode(${subjectid})"><span class="icon_office">导出二维码</span></a>
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
			<th class="ali02" width="160"><span onclick="sortHandler('questionno')" id="span_questionno">题目编号</span></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_title">题目名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('questiontype')">题型</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('difficult')">难易程度</span></th>
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
			 <logic:equal value="1" name="model" property="status">正常</logic:equal>
			 <logic:equal value="2" name="model" property="status">禁用</logic:equal>
			</td>
			<td class="ali02">
		    <div class="img_edit hand" title="修改" onclick="editThisRecord('tkQuestionsInfoAction.do','<bean:write name="model" property="questionid"/>')"></div>	
			<div class="img_find hand" title="详情" onclick="detail(${model.questionid})"></div>	
			<c:if test="${model.tkQuestionsType.type=='F'||model.tkQuestionsType.type=='M'}">
			<div class="img_find hand" style="background-image: url(../libs/icons/list.gif);" title="子题管理" onclick="childrenQuestions(${model.questionid})"></div>	
			</c:if>
			<c:if test="${model.parentid=='0'}">
			<div class="img_find hand" style="background-image: url(../libs/icons/folderclosed.gif);" title="举一反三" onclick="similarQuestion(${model.questionid})"></div>
            <div class="img_find hand" style="background-image: url(../libs/icons/video.gif);" title="关联微课" onclick="filmInfo(${model.questionid})"></div>
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
<input type="hidden" value="<bean:write name="subjectid"/>" name="subjectid"/>
<input type="hidden" name="questiontypeid" value="${questiontypeid }"/>
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
  </body>
</html>
