<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.weixin.mp.MpUtil"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestion"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestionComplaint"%>
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

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/zxHelpQuestionCommentAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">评论处理</th>
		</tr>
		<tr>
			<td class="ali01" rowspan="13" width="65px;">答疑详情</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">答疑标题：</td>
			<td class="ali01">
				<bean:write property="title" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">答疑内容：</td>
			<td class="ali01">
				<bean:write property="descript" name="zxHelpQuestion"/>
			</td>
		</tr>	
		
			<tr>
				<td class="ali03" width="95px;">答疑图片：</td>
				<td class="ali01">
					<logic:empty name="imglist">暂无图片</logic:empty>	
					<logic:iterate id="qi" name="imglist">
						<img src="/upload/${qi.thumbpath}" onclick="imgdetail('/upload/${qi.filepath}')" style="float:left;margin:5px;" />
					</logic:iterate>
				</td>
			</tr>
		
		
			<tr>
				<td class="ali03" width="95px;">答疑语音：</td>
				<td class="ali01">
					<logic:empty name="audiolist">暂无语音</logic:empty>
					<logic:iterate id="file" name="audiolist">
						<audio src="/upload/${file.mp3path }" controls="controls" style="width:230px;height:40px;margin:5px;"></audio>
					</logic:iterate>
				</td>
			</tr>	
		
		<tr>
			<td class="ali03" width="95px;">提问时间：</td>
			<td class="ali01">
				<bean:write property="createdate" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">奖金：</td>
			<td class="ali01">
				<bean:write property="money" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">提问人IP：</td>
			<td class="ali01">
				<bean:write property="userip" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">提问人姓名：</td>
			<td class="ali01">
				<bean:write property="sysUserInfo.username" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">状态：</td>
			<td class="ali01">
				<c:if test="${zxHelpQuestion.status == '0'}">
					待发布
				</c:if>
				<c:if test="${zxHelpQuestion.status == '1'}">
					已发布
				</c:if>
				<c:if test="${zxHelpQuestion.status == '2'}">
					评论中
				</c:if>
				<c:if test="${zxHelpQuestion.status == '3'}">
					评论已处理
				</c:if>
				<c:if test="${zxHelpQuestion.status == '9'}">
					已关闭
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">回复状态：</td>
			<td class="ali01">
				<c:if test="${zxHelpQuestion.replystatus == '0'}">
					未回复
				</c:if>
				<c:if test="${zxHelpQuestion.replystatus == '1'}">
					已被抢单
				</c:if>
				<c:if test="${zxHelpQuestion.replystatus == '2'}">
					已回复
				</c:if>
				<c:if test="${zxHelpQuestion.replystatus == '3'}">
					确认付款
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">浏览次数：</td>
			<td class="ali01">
				<bean:write property="hits" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">销量：</td>
			<td class="ali01">
				<bean:write property="sellcount" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali01" rowspan="10" width="65px;">评论详情</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">评论内容：</td>
			<td class="ali01">
				<bean:write property="content" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">评分：</td>
			<td class="ali01">
				<bean:write property="score" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">评论时间：</td>
			<td class="ali01">
				<bean:write property="createdate" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">评论人：</td>
			<td class="ali01">
				${model.sysUserInfo.username }
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">评论人IP：</td>
			<td class="ali01">
				<bean:write property="userip" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">是否匿名评论：</td>
			<td class="ali01">
				<logic:equal value="0" name="model" property="anonymous">不匿名</logic:equal>
				<logic:equal value="1" name="model" property="anonymous">匿名</logic:equal>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">评论状态：</td>
			<td class="ali01">
				<select name="zxHelpQuestionComment.status">	
	  				<option value="1" ${model.status eq '1'?"selected":"" }>显示</option>	
	  				<option value="2" ${model.status eq '2'?"selected":"" }>禁用</option>	
		 		</select>
			</td>
		</tr>		
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="javascript:history.back()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="zxHelpQuestionComment.commentid" value='<bean:write property="commentid"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.questionid" value='<bean:write property="questionid"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.content" value='<bean:write property="content"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.score" value='<bean:write property="score"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.userip" value='<bean:write property="userip"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.createdate" value='<bean:write property="createdate"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.anonymous" value='<bean:write property="anonymous"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.status" value='<bean:write property="status"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.replyuserid" value='<bean:write property="replyuserid"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.replycontent" value='<bean:write property="replycontent"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.replyuserip" value='<bean:write property="replyuserip"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComment.replycreatedate" value='<bean:write property="replycreatedate"  name="model"/>'/>
<input type="hidden" name="userid" value='<bean:write property="sysUserInfo.userid"  name="model"/>'/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
		var objectForm = document.zxHelpQuestionCommentActionForm;
	  	objectForm.action="zxHelpQuestionCommentAction.do?method=updateSave";
	  	objectForm.submit();
}

function backRecord(){
  	document.zxHelpQuestionCommentActionForm.action="/zxHelpQuestionCommentAction.do?method=list";
  	document.zxHelpQuestionCommentActionForm.submit();
}

function imgdetail(imgpath){
	var imgDetail=window.open(imgpath,'图片详情','left=100,width=500,resizable=yes');
}
</script>
</body>
</html>