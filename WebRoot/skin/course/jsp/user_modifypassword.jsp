<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Constants.PRODUCT_NAME %>-个人设置</title>
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

<!--弹窗start-->
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<!--弹窗end-->
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>

<body>
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
<%@ include file="left.jsp" %>

<html:form action="/courseCenter.do" method="post">
<div class="rightCon">
	<div class="main">
		<div class="mainBox">
			<div class="mainTit">
				<h3 class="title">个人设置</h3>
				<logic:present name="prompttag">
				<logic:equal value="1" name="prompttag"><span style="padding-left:20px;color:green;">修改密码成功！</span></logic:equal>
				<logic:equal value="0" name="prompttag"><span style="padding-left:20px;color:green;">修改密码失败！</span></logic:equal>
				</logic:present>
			</div>
			<div class="mainCon">
			<div class="tab" style="margin-top:-10px">
              <ul>
                <li class="borlnone"><a href="/courseCenter.do?method=beforeUpdateSelf" class="jobInfo">修改个人信息</a></li>
                <li class="current"><a href="/courseCenter.do?method=modifyPassword" class="course">修改密码</a></li>
              </ul>
            </div>
			<div class="mainCon-bd">
				<div class="form-body creatCourseStep">
					<div class="form-row form-row01">
						<div class="label">
							<label>
							<i class="text-red must">*</i>
							原密码：
							</label>
						</div>
						<div class="input">
							<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="oldpassword" value="" style="width:200px;" type="password">
							<logic:present name="prompttag"><logic:equal value="2" name="prompttag"><i class="text-red must">原密码输入不正确！</i></logic:equal></logic:present>
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							<i class="text-red must">*</i>
							新密码：
							</label>
						</div>
						<div class="input">
							<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="newpassword" id="newpassword" value="" style="width:200px;" type="password">
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							<i class="text-red must">*</i>
							确认密码：
							</label>
						</div>
						<div class="input">
							<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="repassword" id="repassword" value="" style="width:200px;" type="password">
							<i class="text-red must" style="display:none;" id="pwdtips">确认密码输入错误！</i>
						</div>
					</div>
					
				<div class="clearfix mt20" style="text-align:center;">
					<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" style="display:inline-block;" type="button">
				</div>
				</div>
			</div>
			</div>
		</div>
	</div>
</div>
</html:form>

</div>

<%@ include file="/skin/course01/jsp/footer.jsp"%>
</body>
<script>
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="descript"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}

function saveRecord(){
	if(validateForm('form[name=eduCourseInfoActionForm]')){
		var newpassword = document.getElementById("newpassword").value;
		var repassword = document.getElementById("repassword").value;
		if(newpassword != repassword){
			document.getElementById("pwdtips").style.display = "";
			return;
		}
	
		var objectForm = document.eduCourseInfoActionForm;
	  	objectForm.action="/courseCenter.do?method=modifySave";
	  	objectForm.submit();
	}
}
</script>
</html>