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
<div class="box3" panelTitle="交易查询" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td>用户名称：</td>
			<td><input type="text" name="username" value="<bean:write name="username"/>"/></td>
			<td>交易内容：</td>
			<td><input type="text" name="descript" value="<bean:write name="descript"/>"/></td>
		</tr>
		<tr>
		<td>用户类型：</td>
			<td>
			<select name="usertype">
			    <option value="">请选择</option>	
  				<option value="0" <%if("0".equals(request.getAttribute("usertype")))out.print(" selected ");%>>系统用户</option>	
  				<option value="1" <%if("1".equals(request.getAttribute("usertype")))out.print(" selected ");%>>老师</option>	
  				<option value="2" <%if("2".equals(request.getAttribute("usertype")))out.print(" selected ");%>>学生</option>	
  				<option value="3" <%if("3".equals(request.getAttribute("usertype")))out.print(" selected ");%>>家长</option>	
	 		</select>
			</td>
			<%--
			<td>用户学段：</td>
			<td>
			<select name="xueduan">
			    <option value="">请选择</option>	
  				<option value="1" <%if("1".equals(request.getAttribute("xueduan")))out.print(" selected ");%>>小学</option>	
  				<option value="2" <%if("2".equals(request.getAttribute("xueduan")))out.print(" selected ");%>>初中</option>	
  				<option value="3" <%if("3".equals(request.getAttribute("xueduan")))out.print(" selected ");%>>高中</option>	
	 		</select>
			</td>
			--%>
			<td>交易类型：</td>
			<td>
			<select name="changetype">
			    <option value="">请选择</option>	
  				<option value="-1" <%if("-1".equals(request.getAttribute("changetype")))out.print(" selected ");%>>支出</option>	
  				<option value="1" <%if("1".equals(request.getAttribute("changetype")))out.print(" selected ");%>>收入</option>	
	 		</select>
			</td>	
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02" width="50">序号</th>
			<th class="ali02" width="70"><span onclick="sortHandler('username')" id="span_username">用户名</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('userip')" id="span_userip">用户IP</span></th>
			<th class="ali02" width="130"><span onclick="sortHandler('createdate')" id="span_createdate">时间</span></th>
			<th class="ali02" width="120"><span onclick="sortHandler('changemoney')" id="span_changemoney">交易金额</span></th>
			<th class="ali02"><span onclick="sortHandler('descript')" id="span_descript">交易内容</span></th>
		</tr>
		<!--循环列出所有数据-->
		<%Integer startcount = (Integer)request.getAttribute("startcount"); %>
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02"><%=startcount+index+1%></td>
			<td class="ali02"><bean:write name="model" property="flago"/></td>
			<td class="ali02"><bean:write name="model" property="userip"/></td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali02">
				<c:if test="${model.changetype == '-1'}">
				-
				</c:if>
				<c:if test="${model.changetype == '1'}">
				+
				</c:if>
				<bean:write name="model" property="changemoney"/>
			</td>
			<td class="ali01"><span class="textSlice" style="width:400px;" title="<bean:write name="model" property="descript"/>"><bean:write name="model" property="descript"/></span></td>
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
	    document.pageForm.action = '/sysUserMoneyAction.do?method=list';
   		document.pageForm.submit();
	
	}
</script>
</body>
</html>