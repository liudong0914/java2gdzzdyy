<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@ page import="com.wkmk.edu.bo.EduCourseInfo" %>
<%@ page import="com.wkmk.edu.bo.EduCourseComment" %>
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
</head>

<body style="background:#fcfcfc;">
<html:form action="/eduCourseCommentAction.do?method=list" method="post">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
		<!-- <div class="form-row form-row01">
			<div class="label">
				<label>
				课程名称：
				</label>
			</div>
			<div class="input" style="margin-top: 3px;">
				${courseInfo.title }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				课程编号：
				</label>
			</div>
			<div class="input">
				${courseInfo.courseno }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				所属分类：
				</label>
			</div>
			<div class="input">
				<c:if test="${courseInfo.coursetypeid == '1' }">院校企业</c:if>
				<c:if test="${courseInfo.coursetypeid == '2' }">退役军人</c:if>
				<c:if test="${courseInfo.coursetypeid == '3' }">医护行业</c:if>
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				缩略图：
				</label>
			</div>
			<div class="input">
				<div class="fl" width="550">
					<img src="/upload/${courseInfo.sketch }"  keepDefaultStyle="true" style="cursor:pointer;" width="110" height="120" border="1" />
				</div>
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				总课时：
				</label>
			</div>
			<div class="input">
				<div class="fl" width="550">
					${courseInfo.classhour }
				</div>
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				总价：
				</label>
			</div>
			<div class="input">
				<div class="fl" width="550">
					${courseInfo.price }
				</div>
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				开始时间：
				</label>
			</div>
			<div class="input">
				${courseInfo.startdate }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				结束时间：
				</label>
			</div>
			<div class="input">
				${courseInfo.enddate }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
			<label>课程目标：</label>
			</div>
			<div class="input">
				${courseInfo.objectives }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
			<label>课程描述：</label>
			</div>
			<div class="input">
				${courseInfo.htmlcontent }
			</div>
		</div> -->
		
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
		
		<c:if test="${!empty model.replycontent }">
			<div class="form-row">
				<div class="label">
					<label>
					回复内容：
					</label>
				</div>
				<div class="input">
					${model.replycontent }
			    </div>
			</div>
			
			<div class="form-row">
				<div class="label">
					<label>
					回复用户ip：
					</label>
				</div>
				<div class="input">
					${model.replyuserip }
			    </div>
			</div>
			
			<div class="form-row">
				<div class="label">
				<label>回复时间：</label>
				</div>
				<div class="input">
					${model.replycreatedate }
				</div>
			</div>
		</c:if>
		
	<div class="clearfix mt20" style="text-align:center;">
		<input class="btn btn-pop ml20" value="返回" onclick="goBack()" type="button">
	</div>
	</div>
</div>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="courseclassid" value="<bean:write name="courseclassid"/>"/>

<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
</html:form>

</body>
<script>
function goBack(){
	var objectForm = document.eduCourseCommentActionForm;
  	objectForm.action="/eduCourseCommentAction.do?method=list";
  	objectForm.submit();
}
</script>
</html>
