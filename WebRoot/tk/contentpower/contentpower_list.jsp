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
  function postData(){
  
	    document.tkBookContentPowerActionForm.action = '/tkBookContentPowerAction.do?method=list';
   		document.tkBookContentPowerActionForm.submit();
	
	}

  $(function(){ 
    <c:if test="${reloadtree==1}">
    parent.rfrmleft.location ='/tkBookContentPowerAction.do?method=tree';
    </c:if>
  });
</script>
</head>
  <body >
  <html:form action="tkBookContentPowerAction.do"  method="post">
    <c:if test="${type=='1' }">
     <div class="box3" panelTitle="内容校验授权管理" style="margin-right:0px;padding-right:0px;">
     </c:if>
      <c:if test="${type=='1' }">
     <div class="box3" panelTitle="试题替换授权管理" style="margin-right:0px;padding-right:0px;">
     </c:if>
     </div>
     <div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a id="addpower" href="javascript:;" onclick="userAddBookContent();"><span class="icon_add">授权</span></a>
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
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="bookContent" indexId="index">
	     <%request.setAttribute("check", 1);%>
	    <bean:define id="bcid" name="model" property="bookcontentid"></bean:define>
		<tr>
			<td>
			<input type="checkbox" name="checkid" ${model.flags eq 'true' ?"checked=checked":""} value="<bean:write property="bookcontentid" name="model"/>" <bean:write property="flags" name="model"/>/></td>
			<td class="ali02"><bean:write name="model" property="contentno"/> </td>
			<td class="ali02" style="text-align: left;">
			<bean:write name="model" property="title"/></td>
		</tr>
		</logic:iterate>
	</table>
</div>
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" name="type" value="${type }"/>
<input type="hidden" name="bookid" value="${bookid}"/>
<input type="hidden" name="userid" value="${userid}"/>
</html:form>
<script type="text/javascript">
	function userAddBookContent(){
	    document.getElementById("addpower").removeAttribute("onclick");
		document.tkBookContentPowerActionForm.action='/tkBookContentPowerAction.do?method=addSave';
		document.tkBookContentPowerActionForm.submit();
	}
</script>
</body>
</html>
