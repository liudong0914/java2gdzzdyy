<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="../../" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<!--框架必需end-->

<!--数字分页start-->
<script type="text/javascript" src="../../libs/js/nav/pageNumber.js"></script>
<script type="text/javascript" src="../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../libs/js/sk/comm.js"></script>
<!--数字分页end-->
</head>
<body>
	<form name="pageForm" method="post">
		<div class="box3" panelTitle="作业本管理"
			style="margin-right:0px;padding-right:0px;">
			<table>
				<tr>
					<td>课本编号：</td>
					<td><input type="text" name="bookno" value="${bookno }" /></td>
					<td>课本名称：</td>
					<td><input type="text" style="width: 200px;" name="title"
						value="${title }" /></td>
					<td>主编：</td>
					<td><input type="text" name="editor" value="${editor }" /></td>
					<td>副主编：</td>
					<td><input type="text" name="deputyeditor"
						value="${deputyeditor}" /></td>
					<td><button type="button" onclick="searchRecord()">
							<span class="icon_find">查询</span>
						</button></td>
				</tr>
			</table>
		</div>
		<div class="box_tool_min padding_top2 padding_bottom2 padding_right0">
			<div class="center">
				<div class="left">
					<div class="right">
						<div class="padding_top5 padding_left10">
							<a href="javascript:;"
								onclick="addRecord('/tkBookInfoAction.do?method=beforeAdd&subjectid=${subjectid}')"><span
								class="icon_add">新增</span> </a>
							<div class="box_tool_line"></div>
							<a href="javascript:;"
								onclick="batchRecord('/tkBookInfoAction.do?method=delBatchRecord', '您确定删除选中作业本?')"><span
								class="icon_delete">删除</span> </a>

							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
		<div id="scrollContent">
			<table class="tableStyle" useClick="false" useCheckBox="true"
				sortMode="true">
				<tr>
					<th width="25"></th>
					<th class="ali02" width="80px;"><span
						onclick="sortHandler('bookno')">课本编号</span></th>
					<th class="ali02"><span onclick="sortHandler('title')">课本名称</span>
					</th>
					<th class="ali02" width="80px"><span
						onclick="sortHandler('gradeid')">所属年级</span></th>
					<th class="ali02" width="80px"><span
						onclick="sortHandler('version')">教材版本</span></th>
					<th class="ali02" width="80px"><span
						onclick="sortHandler('part')">上/下册</span></th>
					<th class="ali02" width="80px;"><span
						onclick="sortHandler('editor')">主编</span></th>
					<th class="ali02" width="80px;"><span
						onclick="sortHandler('deputyeditor')">副主编</span></th>
					<th class="ali02" width="80px;"><span
						onclick="sortHandler('status')">状态</span></th>
					<th class="ali02" width="80">操作</th>
				</tr>
				<!--循环列出所有数据-->
				<logic:iterate id="model" name="pagelist" property="datalist"
					indexId="index">
					<tr>
						<td><input type="checkbox" name="checkid"
							value="<bean:write property="bookid" name="model"/>"
							<bean:write property="flags" name="model"/> /></td>
						<td class="ali02"><bean:write name="model" property="bookno" />
						</td>
						<td class="ali02"><bean:write name="model" property="title" />
						</td>
						<td class="ali02"><bean:write name="model"
								property="gradename" />
						</td>
						<td class="ali02"><java2code:value codetype="version"
								property="version"></java2code:value></td>
						<td class="ali02"><logic:equal value="0" name="model"
								property="part">全册</logic:equal> <logic:equal value="1"
								name="model" property="part">上册</logic:equal> <logic:equal
								value="2" name="model" property="part">下册</logic:equal>
						</td>
						<td class="ali02"><bean:write name="model" property="editor" />
						</td>
						<td class="ali02"><bean:write name="model"
								property="deputyeditor" /></td>
						<td class="ali02"><logic:equal value="1" name="model"
								property="status">正常</logic:equal> <logic:equal value="2"
								name="model" property="status">禁用</logic:equal>
						</td>
						<td class="ali02">
							<div class="img_edit hand" title="修改"
								onclick="editThisRecord('/tkBookInfoAction.do','${model.bookid}')"></div>
							<!-- <div class="img_find hand" title="详情"
								onclick="detail(${model.bookid})"></div> -->
							<div class="img_find hand" title="下载作业本二维码"
								style="background-image: url(../libs/icons/export.gif);"
								onclick="exportcode(${model.bookid})"></div>
							<div class="img_chart hand" title="初始化价格" onclick="initialPrice(${model.bookid})"></div>
							<div class="img_find hand" title="导出解题微课二维码"
								style="background-image: url(../libs/icons/export.gif);"
								onclick="exportFilmCode(${model.bookid})"></div>
						</td>
					</tr>
				</logic:iterate>
			</table>			</div>
			<div class="float_right padding5">
				<div class="pageNumber"
					total="<bean:write name="pagelist" property="totalCount"/>"
					pageSize="<bean:write name="pagesize"/>"
					page="<bean:write name="pagelist" property="curPage0"/>"
					showSelect="true" showInput="true" id="pageContent"></div>
			</div>
			<div class="clear"></div>
		</div>
		<input type="hidden" value="<bean:write name="subjectid"/>"
			name="subjectid" />
		<!-- 分页与排序 -->
		<input type="hidden" value="<bean:write name="direction"/>"
			name="direction" id="direction" /> <input type="hidden"
			value="<bean:write name="sort"/>" name="sort" id="sort" /> <input
			type="hidden"
			value="<bean:write name="pagelist" property="curPage"/>"
			name="pager.pageNo" id="pageNo" /> <input type="hidden"
			value="<bean:write name="pagesize"/>" name="pager.pageSize"
			id="pageSize" />
	</form>
    
	<script type="text/javascript">
	var num=<bean:write name="pagelist" property="pageCount"/>
	
	//提交表单
	function postData(){
	    document.pageForm.action = '/tkBookInfoAction.do?method=list&subjectid=${subjectid}';
   		document.pageForm.submit();
	}
	function detail(id){
	    document.pageForm.action='/tkBookInfoAction.do?method=detail&id='+id;
	    document.pageForm.submit();   
	}
    function exportcode(id){
      document.pageForm.action='tkBookInfoAction.do?method=exportcode&bookid='+id;
      document.pageForm.submit();
    }
    function initialPrice(bookid){
       var diag=new top.Dialog();
       diag.Title="初始化价格";
       diag.URL= '/tkBookContentPriceAction.do?method=initialPrice&bookid='+bookid;
       diag.Width=600;
       diag.Height=300;
       diag.ShowMaxButton=true;
       diag.CancelEvent=function(){
         diag.close();
       };
       diag.show();
    }
    function exportFilmCode(bookid){
    	document.pageForm.action='tkBookInfoAction.do?method=exportFilmCode&bookid='+bookid;
        document.pageForm.submit();
    }
</script>
</body>
</html>