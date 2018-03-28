<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.sys.bo.SysUserInfoDetail"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@ page import="com.wkmk.edu.bo.EduCourseUser" %>
<%@ page import="java.util.*" %>
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
<link href="/skin/course/css/style.css" rel="stylesheet"/>
<%
EduCourseUser eduCourseUser=(EduCourseUser)request.getAttribute("eduCourseUser");
%>
</head>

<body style="background:#fcfcfc;">
<html:form action="/eduCourseUserAction.do?method=addSaveStudent" method="post">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
		<div class="form-row form-row01">
			<div class="label">
				<label>
				用户名：
				</label>
			</div>
			<div class="input" style="margin-top: 3px;">
				${model.loginname }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				真实姓名：
				</label>
			</div>
			<div class="input">
				${model.username }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				头像：
				</label>
			</div>
			<div class="input">
				<div class="fl" width="550">
					<img src="/upload/${model.photo }"  keepDefaultStyle="true" style="cursor:pointer;" width="110" height="120" border="1" />
				</div>
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				性别：
				</label>
			</div>
			<div class="input">
				<c:if test="${model.sex == '0' }">男</c:if>
				<c:if test="${model.sex == '1' }">女</c:if>
		    </div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				电话：
				</label>
			</div>
			<div class="input">
				${detail.telephone }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				地址：
				</label>
			</div>
			<div class="input">
				${detail.address }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				学历：
				</label>
			</div>
			<div class="input">
				<c:if test="${detail.education == '1' }">博士及以上</c:if>
				<c:if test="${detail.education == '2' }">硕士</c:if>
				<c:if test="${detail.education == '3' }">本科</c:if>
				<c:if test="${detail.education == '4' }">大专</c:if>
				<c:if test="${detail.education == '9' }">其他</c:if>
		    </div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>个人介绍：</label>
			</div>
			<div class="input">
				<bean:write property="descript" name="detail"/>
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				院企用户：
				</label>
			</div>
			<div class="input">
				<c:if test="${eduCourseUser.vip == '0' }">否</c:if>
				<c:if test="${eduCourseUser.vip == '1' }">是</c:if>
			</div>
		</div>
		
	<div class="clearfix mt20" style="text-align:center;">
		<input class="btn btn-pop ml20" value="返回" onclick="goBack()" type="button">
	</div>
	</div>
</div>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="courseclassid" value="<bean:write name="courseclassid"/>"/>
<input type="hidden" name="usertype" value="<bean:write name="usertype"/>"/>

<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
</html:form>

</body>
<script>
function goBack(){
	var objectForm = document.eduCourseUserActionForm;
  	objectForm.action="/eduCourseUserAction.do?method=studentList";
  	objectForm.submit();
}
</script>
</html>
