<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkBookContentFilm"%>
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

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->
</head>
<body>
<div class="box1" position="center">
<html:form action="/tkBookContentCommentAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2">修改作业解题微课评价</th>
		</tr>
		<tr>
			<td class="ali03" wdith="50">评价用户：</td>
			<td class="ali01">${model.sysUserInfo.username}</td>
		</tr>
		<tr>
			<td class="ali03">评分：</td>
			<td class="ali01">
				${model.score}（分）
			</td>
		</tr>
		<tr>
			<td class="ali03">微课类型：</td>
			<td class="ali01">
				${model.type eq '1'?"解题微课":model.type eq '2'?"举一反三":""}
			</td>
		</tr>
		<tr>
			<td class="ali03">是否匿名评价：</td>
			<td class="ali01">
				${model.anonymous eq '1'?"是":"否" }
			</td>
		</tr>
		<tr>
			<td class="ali03">评价时间：</td>
			<td class="ali01">${model.createdate}</td>
		</tr>
		<tr>
			<td class="ali03">评价内容：</td>
			<td class="ali01">${model.content}</td>
		</tr>
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01">
				<select name="tkBookContentComment.status">
					<option value="1" ${model.status eq '1'?"selected":"" }>显示</option>
					<option value="2" ${model.status eq '2'?"selected":"" }>禁用</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="ali03">回复内容：</td>
			<td class="ali01">
				<textarea style="width:600px;height:150px;" name="tkBookContentComment.replycontent">${model.replycontent}</textarea>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="tkBookContentComment.commentid" value="${model.commentid }"/>
<input type="hidden" name="tkBookContentComment.bookcontentid" value="${model.bookcontentid}"/>
<input type="hidden" name="tkBookContentComment.content" value="${model.content }"/>
<input type="hidden" name="tkBookContentComment.score" value="${model.score }"/>
<input type="hidden" name="tkBookContentComment.userip" value="${model.userip }"/>
<input type="hidden" name="tkBookContentComment.createdate" value="${model.createdate }"/>
<input type="hidden" name="tkBookContentComment.anonymous" value="${model.anonymous }"/>
<input type="hidden" name="tkBookContentComment.type" value="${model.type }"/>
<input type="hidden" name="tkBookContentComment.createdate" value="${model.createdate }"/>
<input type="hidden" name="userid" value="${model.sysUserInfo.userid}"/>
<input type="hidden" name="bookid" value="${bookid}"/>
<input type="hidden" name="bookcontentid" value="${bookcontentid}"/>
</html:form>
</div>
<script>
function saveRecord(){
		var objectForm = document.tkBookContentCommentActionForm;
	  	objectForm.action="tkBookContentCommentAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	
}

function backRecord(){
  	document.tkBookContentCommentActionForm.action="/tkBookContentCommentAction.do?method=list";
  	document.tkBookContentCommentActionForm.submit();
}
</script>
</body>
</html>