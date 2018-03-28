<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
<%@page import="com.wkmk.sys.bo.SysUnitInfo"%>
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
<div class="box3" panelTitle="教材信息管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>教材名称：</td>
			<td><input type="text" style="width: 200px;" name="textbookname" value="${textbookname }" /></td>
			<td>作者：</td>
			<td><input type="text" name="author" value="${author }" /></td>
			<td>状态：</td>
			<td><java2code:option name="status" codetype="status4" valuename="status" selectall="3"/></td>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
	<div class="center">
	<div class="left">
	<div class="right">
		<div class="padding_top5 padding_left10">
		<a href="javascript:;" onclick="addRecord('/tkTextBookInfoAction.do?method=beforeAdd')"><span class="icon_add">新增</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/tkTextBookInfoAction.do?method=delBatchRecord&setstatus=2', '确定禁用选中教材?')"><span class="icon_no">禁用</span> </a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="batchRecord('/tkTextBookInfoAction.do?method=delBatchRecord&setstatus=1','确定要启用所选中教材？')"><span class="icon_ok">启用</span></a>
		<div class="box_tool_line"></div>
		<a href="javascript:;" onclick="exportTwocode()"><span class="icon_export">导出二维码</span></a>
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
			<th class="ali02" width="30"><span onclick="sortHandler('orderindex')" id="span_orderindex">排序</span></th>
			<th class="ali02"><span onclick="sortHandler('textbookname')" id="span_textbookname">教材名称</span></th>
			<th class="ali02" width="150"><span onclick="sortHandler('textbookno')" id="span_textbookno">书号</span></th>
			<th class="ali02" width="150"><span onclick="sortHandler('press')" id="span_press">出版社</span></th>
			<th class="ali02" width="150"><span onclick="sortHandler('type')" id="span_type">范围和种类</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('price')" id="span_price">单价</span></th>
			<!-- <th class="ali02" width="50"><span onclick="sortHandler('discount')" id="span_discount">折扣</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('sellprice')" id="span_sellprice">售价</span></th> -->
			<th class="ali02" width="50"><span onclick="sortHandler('sellcount')" id="span_sellcount">销量</span></th>
			<!-- <th class="ali02" width="80"><span onclick="sortHandler('phone')" id="span_phonet">联系电话</span></th> -->
			<th class="ali02" width="80"><span onclick="sortHandler('createdate')" id="span_createdate">时间</span></th>
			<th class="ali02" width="50"><span onclick="sortHandler('status')" id="span_status">状态</span></th>
			<th class="ali02" width="40">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td><input type="checkbox" name="checkid" value="<bean:write property="textbookid" name="model"/>"/></td>
			<td class="ali02"><bean:write name="model" property="orderindex"/></td>
			<td class="ali02"><bean:write name="model" property="textbookname"/></td>
			<td class="ali02"><bean:write name="model" property="textbookno"/></td>
			<td class="ali02"><bean:write name="model" property="press"/></td>
			<td class="ali02"><bean:write name="model" property="type"/></td>
			<td class="ali02"><bean:write name="model" property="price"/></td>
			<!-- <td class="ali02"><bean:write name="model" property="discount"/></td>
			<td class="ali02"><bean:write name="model" property="sellprice"/></td> -->
			<td class="ali02"><bean:write name="model" property="sellcount"/></td>
			<!-- <td class="ali02"><bean:write name="model" property="phone"/></td> -->
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali02"><java2code:value codetype="status4" property="status"/></td>
			<td class="ali02">
			<div class="img_edit0 hand" title="修改" onclick="editThisRecord('tkTextBookInfoAction.do','<bean:write name="model" property="textbookid"/>')"></div>
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
<input type="hidden" value="<bean:write name="pagelist" property="datalist" />" name="datalist" id="datalist"/>
</form>
<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount" />
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkTextBookInfoAction.do?method=list';
   		document.pageForm.submit();
	
	}
	function exportTwocode(){
	    var selectID = "";  
        var checkedID = document.getElementsByName("checkid");  
        for (var i = 0; i < checkedID.length; i++) {  
            if (checkedID[i].checked) {  
                selectID += checkedID[i].value + ",";  
            }  
        }
        if (selectID == "") {  
            alert("请至少选择一条记录进行导出！");
            return false;  
        }else{
        	this.location.href='/tkTextBookInfoAction.do?method=exportBatchTwoCode&checkids='+selectID;
        }
    }
</script>
</body>
</html>