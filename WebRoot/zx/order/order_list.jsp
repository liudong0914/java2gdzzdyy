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

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<form name="pageForm" method="post">
<div class="box3" panelTitle="我的订单" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td>订单状态：</td>
			<td>
				<select name="status">
				    <option value="">请选择</option>	
	  				<option value="1" <%if("1".equals(request.getAttribute("status")))out.print(" selected ");%>>未回复</option>	
	  				<option value="2" <%if("2".equals(request.getAttribute("status")))out.print(" selected ");%>>已回复</option>	
		 		</select>
			</td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02" width="250"><span onclick="sortHandler('flagl')" id="span_flagl">问题标题</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('money')" id="span_money">定价</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('createdate')" id="span_createdate">时间</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('status')" id="span_sex">状态</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02"><bean:write name="model" property="flagl"/></td>
			<td class="ali02"><bean:write name="model" property="money"/></td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali02">
				<c:if test="${model.status == '1'}">
					未回复
				</c:if>
				<c:if test="${model.status == '2'}">
					已回复
				</c:if>
				<c:if test="${model.status == '3'}">
					已超时
				</c:if>
			</td>
			<td class="ali02">
			<div class="img_find hand" title="查看问题" onclick="viewQuestion('<bean:write name="model" property="questionid"/>')"></div>
			<logic:equal name="model" property="status" value="2">
				<div class="img_view hand" title="查看回复" onclick="viewAnswer('<bean:write name="model" property="orderid"/>')"></div>
			</logic:equal>
			<logic:equal name="model" property="status" value="1">
				<div class="img_edit0 hand" title="处理" onclick="doOrder('<bean:write name="model" property="orderid"/>')"></div>
			</logic:equal>
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
<!-- 分页与排序 -->
<input type="hidden" value="<bean:write name="direction"/>" name="direction" id="direction"/>
<input type="hidden" value="<bean:write name="sort"/>" name="sort" id="sort"/>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="<bean:write name="pagesize"/>" name="pager.pageSize" id="pageSize"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/zxHelpOrderAction.do?method=list';
   		document.pageForm.submit();
	
	}
	//查看问题
	function viewQuestion(objid){
	    document.pageForm.action = '/zxHelpOrderAction.do?method=viewQuestion&objid='+objid;
   		document.pageForm.submit();
	
	}
	//查看回复
	function viewAnswer(objid){
	    document.pageForm.action = '/zxHelpOrderAction.do?method=viewAnswer&objid='+objid;
   		document.pageForm.submit();
	
	}
	//处理订单
	function doOrder(objid){
	    document.pageForm.action = '/zxHelpOrderAction.do?method=beforeUpdate&objid='+objid;
   		document.pageForm.submit();
	
	}
</script>
</body>
</html>