<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
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
<html:form action="/courseCenter.do?method=addCourseClass" method="post">
<div class="mainCon">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
		<div class="form-row form-row01">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				课程批次：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="courseclassname" value="" style="width:150px;" type="text">
			</div>
		</div>
		
		<div class="form-row" style="margin-bottom:30px;">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				开始时间：
				</label>
			</div>
			<div class="input">
				<input type="text" name="courseclassstartdate" readonly="readonly" value="${startdate }" class="date validate[required,custom[date]] ipt-text ipt-text01 formEleToVali" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:150px;"/>
			</div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		
		<div class="form-row" style="margin-bottom:30px;">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				结束时间：
				</label>
			</div>
			<div class="input">
				<input type="text" name="courseclassenddate" readonly="readonly" value="${enddate }" class="date validate[required,custom[date]] ipt-text ipt-text01 formEleToVali" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:150px;"/>
			</div>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		
			
	<div class="clearfix mt20" style="text-align:center;">
		<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" style="display:inline-block;" type="button">
	</div>
	</div>
</div>
</div>
<input type="hidden" value="${courseid }" name="courseid"/>
</html:form>
</body>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseInfoActionForm]')){
		var objectForm = document.eduCourseInfoActionForm;
	  	objectForm.action="/courseCenter.do?method=addCourseClass";
	  	objectForm.submit();
	}
}
</script>
</html>
