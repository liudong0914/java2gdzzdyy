<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.edu.bo.EduCourseComment"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="java.util.Map"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<!--框架必需start-->
<script type="text/javascript" src="/libs/js/framework.js"></script>
<link href="/libs/css/framework/form0.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="/libs/js/form/validation.js" type="text/javascript"></script>
<script src="/libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->

<!--弹窗start-->
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="/libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>

<body>

<html:form action="/eduCourseCommentAction.do?method=updateSave" method="post">
<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">回复评价</h3>
		</div>
		<div class="mainCon">
		<div class="mainCon-bd">
			<div class="form-body creatCourseStep">
				<div class="form-row form-row01">
					<div class="label">
						<label>
						评价内容：
						</label>
					</div>
					<div class="input">
						${model.content }
				    </div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						评分：
						</label>
					</div>
					<div class="input">
						${model.score }
					</div>
				</div>
		
				<div class="form-row">
					<div class="label">
						<label>
						评价用户：
						</label>
					</div>
					<div class="input">
						${model.sysUserInfo.username }
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						ip地址：
						</label>
					</div>
					<div class="input">
						${model.userip }
				    </div>
				</div>
				
				<div class="form-row">
					<div class="label">
					<label>评价时间：</label>
					</div>
					<div class="input">
						${model.createdate }
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
					<label><i class="text-red must">*</i>回复内容：</label>
					</div>
					<div class="input">
					<div class="fl">
					<textarea keepDefaultStyle="true" id="eduCourseComment.replycontent" class="formEleToVali" name="eduCourseComment.replycontent" style="width:610px;height:160px;">${model.replycontent }</textarea>
					</div>
					</div>
				</div>
					
			<div class="clearfix mt20" style="text-align:center;">
				<%
				String isAuhtor = (String)session.getAttribute("isAuhtor");
				Map moduleidMap = (Map)session.getAttribute("moduleidMap");
				String moduleidType = (String)moduleidMap.get("8");
				if("1".equals(isAuhtor) || "2".equals(moduleidType)){
				%>
				<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" style="display:inline-block;" type="button">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<%} %>
				<input class="btn btn-pop ml20" value="返回" onclick="window.history.back()" type="button">
			</div>
			</div>
		</div>
		</div>
	</div>
</div>
<input type="hidden" name="eduCourseComment.commentid" value="<bean:write property="commentid" name="model"/>"/>
<input type="hidden" name="eduCourseComment.courseid" value="<bean:write property="courseid" name="model"/>"/>
<input type="hidden" name="eduCourseComment.content" value="<bean:write property="content" name="model"/>"/>
<input type="hidden" name="eduCourseComment.score" value="<bean:write property="score" name="model"/>"/>
<input type="hidden" name="eduCourseComment.userip" value="<bean:write property="userip" name="model"/>"/>
<input type="hidden" name="eduCourseComment.createdate" value="<bean:write property="createdate" name="model"/>"/>
<input type="hidden" name="eduCourseComment.anonymous" value="<bean:write property="anonymous" name="model"/>"/>
<input type="hidden" name="eduCourseComment.status" value="<bean:write property="status" name="model"/>"/>
<input type="hidden" name="userid" value="<bean:write property="sysUserInfo.userid" name="model"/>"/>
</html:form>
</body>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseCommentActionForm]')){
		var content = document.getElementById("eduCourseComment.replycontent").value;
		if(content == ""){
			alert("回复内容不能为空！");
			return  false;
		}
		var objectForm = document.eduCourseCommentActionForm;
	  	objectForm.action="/eduCourseCommentAction.do?method=updateSave";
	  	objectForm.submit();
	}
}

</script>
</html>
