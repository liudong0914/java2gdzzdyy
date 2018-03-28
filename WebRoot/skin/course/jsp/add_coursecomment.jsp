<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>

<body>
<html:form action="/courseCenter.do?method=addCourseClass" method="post">
<div class="mainCon">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep" style="padding-left:10px;">
		<div class="form-row form-row01" style="margin-top:-20px;font-size:14px;">
			<div class="">
				说明：1分差评，2-3分中评，4分及以上好评
			</div>
		</div>
		
		<div class="form-row">
			<div class="">
				<input type="radio" name="score" value="1"/>1分&nbsp;&nbsp;
				<input type="radio" name="score" value="2"/>2分&nbsp;&nbsp;
				<input type="radio" name="score" value="3"/>3分&nbsp;&nbsp;
				<input type="radio" name="score" value="4"/>4分&nbsp;&nbsp;
				<input type="radio" name="score" value="5" checked="checked"/>5分
			</div>
		</div>
		
		<div class="form-row">
			<div class="">
			<div class="fl">
			<textarea keepDefaultStyle="true" class="formEleToVali" name="content" id="content" placeholder="请发表您对课程的真实评价" style="width:400px;height:100px;"></textarea>
			<span style="display:none;" id="contenttips"><br/><i class="text-red">请填写课程评价内容！</i></span>
			</div>
			</div>
		</div>
		
		<div class="form-row" style="margin-bottom:30px;">
			<div class="">
				<input type="checkbox" name="anonymous" value="1"/>匿名评价
			</div>
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
	var content = document.getElementById("content").value;
	if(content == ""){
		document.getElementById("contenttips").style.display = "";
		return;
	}
	var objectForm = document.eduCourseInfoActionForm;
  	objectForm.action="/courseCenter.do?method=addCourseComment";
  	objectForm.submit();
}
</script>
</html>
