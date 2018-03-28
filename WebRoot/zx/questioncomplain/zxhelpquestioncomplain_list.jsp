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
<div class="box3" panelTitle="投诉管理" style="margin-right:0px;padding-right:0px;">
	<table>
		<tr>
			<td>投诉内容：</td>
			<td><input type="text" name="descript" value="<bean:write name="descript"/>"/></td>
			<td>日期：</td>
			<td><input type="text" name="createdate" value="<bean:write name="createdate"/>" class="date validate[custom[date]]"/></td>
			<td>投诉状态：</td>
			<td>
				<select name="status">
				    <option value="">请选择</option>	
	  				<option value="0" <%if("0".equals(request.getAttribute("status")))out.print(" selected ");%>>待受理</option>	
	  				<option value="1" <%if("1".equals(request.getAttribute("status")))out.print(" selected ");%>>接受投诉</option>	
	  				<option value="2" <%if("2".equals(request.getAttribute("status")))out.print(" selected ");%>>驳回投诉</option>	
		 		</select>
			</td>
			<td><button type="button" onclick="searchRecord()"><span class="icon_find">查询</span></button></td>
		</tr>
	</table>
</div>
<div id="scrollContent" >
	<table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
		<tr>
			<th class="ali02" width="200"><span onclick="sortHandler('descript')" id="span_descript">投诉内容</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('userid')" id="span_userid">投诉人</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('userip')" id="span_userip">投诉人IP</span></th>
			<th class="ali02" width="80"><span onclick="sortHandler('createdate')" id="span_createdate">时间</span></th>
			<th class="ali02" width="60"><span onclick="sortHandler('status')" id="span_sex">状态</span></th>
			<th class="ali02" width="20">操作</th>
		</tr>
		<!--循环列出所有数据-->
	    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
		<tr>
			<td class="ali02"><bean:write name="model" property="descript"/></td>
			<td class="ali02"><bean:write name="model" property="flagl"/></td>
			<td class="ali02"><bean:write name="model" property="userip"/></td>
			<td class="ali02"><bean:write name="model" property="createdate"/></td>
			<td class="ali02">
				<c:if test="${model.status == '0'}">
					待受理
				</c:if>
				<c:if test="${model.status == '1'}">
					接受投诉
				</c:if>
				<c:if test="${model.status == '2'}">
					驳回投诉
				</c:if>
			</td>
			<td class="ali02">
			<div class="img_find hand" title="查看" onclick="view('<bean:write name="model" property="complaintid"/>')"></div>
			<div class="img_find hand" title="禁用教师" style="background-image: url(../libs/icons/delete.gif);" onclick="userDisable('<bean:write name="model" property="complaintid"/>',1)" ></div>
			<div class="img_find hand" title="禁用学生" style="background-image: url(../libs/icons/butExpand.gif);" onclick="userDisable('<bean:write name="model" property="complaintid"/>',2)" ></div>
			<logic:equal name="model" property="status" value="0">
				<div class="img_edit hand" title="处理" onclick="editThisRecord('zxHelpQuestionComplaintAction.do','<bean:write name="model" property="complaintid"/>')"></div>
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
	    document.pageForm.action = '/zxHelpQuestionComplaintAction.do?method=list';
   		document.pageForm.submit();
	
	}
	//查看详情
	function view(objid){
	    document.pageForm.action = '/zxHelpQuestionComplaintAction.do?method=view&objid='+objid;
   		document.pageForm.submit();
	
	}
	function userDisable(complaintid,type){
		    var diag=new top.Dialog();
		    if(type == 1){
		        diag.Title="禁用教师";
		    }else if(type == 2){
		    	diag.Title="禁用学生";
		    }
		    diag.URL='/sysUserDisableAction.do?method=list&complaintid='+complaintid+'&type='+type;
		    diag.Width=1000;
		    diag.Height=800;
		    diag.ShowMaxButton=true;
		    diag.CancelEvent=function(){
		         diag.close();
		       };
		       diag.show();


		//document.pageForm.action = '/sysUserDisableAction.do?method=beforeAdd&complaintid='+complaintid+'&type='+type;
		//document.pageForm.submit();
	}
</script>
</body>
</html>