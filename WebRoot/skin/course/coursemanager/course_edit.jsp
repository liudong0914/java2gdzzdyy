<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
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
<%
EduCourseInfo model = (EduCourseInfo)request.getAttribute("model");
%>
<html:form action="/courseCenter.do?method=updateSaveCourse" method="post">
<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">修改课程信息</h3>
		</div>
		<div class="mainCon">
		<div class="mainCon-bd">
			<div class="form-body creatCourseStep">
				<div class="form-row form-row01">
					<div class="label">
						<label>
						<i class="text-red must">*</i>
						课程分类：
						</label>
					</div>
					<div class="input jobWrap">
					 	<select name="eduCourseInfo.coursetypeid" keepDefaultStyle="true" class="ipt-text" style="width:120px;height:34px;">
			              	<option value="1" <logic:equal value="1" name="model" property="coursetypeid">selected="selected"</logic:equal>>院校企业</option>
			              	<option value="2" <logic:equal value="2" name="model" property="coursetypeid">selected="selected"</logic:equal>>退役军人</option>
			              	<option value="3" <logic:equal value="3" name="model" property="coursetypeid">selected="selected"</logic:equal>>医护行业</option>
		              	</select>
				    </div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						<i class="text-red must">*</i>
						课程名称：
						</label>
					</div>
					<div class="input">
						<input id="eduCourseInfo.title" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="eduCourseInfo.title" value="${model.title }" style="width:306px;" type="text">
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						<i class="text-red must">*</i>
						课程编号：
						</label>
					</div>
					<div class="input">
						<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="eduCourseInfo.courseno" value="${model.courseno }" style="width:160px;" type="text">
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						<i class="text-red must">*</i>
						课程批次：
						</label>
					</div>
					<div class="input">
						<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="classname" value="${eduCourseClass.classname }" style="width:160px;" type="text">
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						<i class="text-red must">*</i>
						课程价格：
						</label>
					</div>
					<div class="input">
						<input id="eduCourseInfo.price" keepDefaultStyle="true" class="validate[required,,custom[onlyNumberWide]] ipt-text ipt-text01 formEleToVali" name="eduCourseInfo.price" value="${model.price }" type="text" style="width:120px;"> (元)
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						<i class="text-red must">*</i>
						课程时数：
						</label>
					</div>
					<div class="input">
						<input id="eduCourseInfo.classhour" keepDefaultStyle="true" class="validate[required,custom[onlyNumber],length[1,3]] ipt-text ipt-text01 formEleToVali" name="eduCourseInfo.classhour" value="${model.classhour }" type="text" style="width:120px;"> (课时)
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
						<input type="text" name="eduCourseInfo.startdate" id="eduCourseInfo.startdate" readonly="readonly" value="${eduCourseClass.startdate }" class="date validate[required,custom[date]] ipt-text ipt-text01 formEleToVali" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:150px;"/>
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
						<input type="text" name="eduCourseInfo.enddate" id="eduCourseInfo.enddate" readonly="readonly" value="${eduCourseClass.enddate }" class="date validate[required,custom[date]] ipt-text ipt-text01 formEleToVali" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:150px;"/>
					</div>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						课程图片：
						</label>
					</div>
					<div class="input">
						<div class="fl" width="550">
							<img src="/upload/${model.sketch }" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="328" height="170" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
				            <input type="hidden" id="uploadImg" name="eduCourseInfo.sketch" value='${model.sketch }'/>
						</div>
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
					<label>课程目标：</label>
					</div>
					<div class="input">
					<div class="fl">
					<textarea keepDefaultStyle="true" id="eduCourseInfo.objectives" class="formEleToVali" name="eduCourseInfo.objectives" style="width:610px;height:160px;">${model.objectives }</textarea>
					</div>
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
					<label>课程描述：</label>
					</div>
					<div class="input">
						<div class="fl">
						<textarea id="eduCourseInfo.htmlcontent" class="formEleToVali" name="eduCourseInfo.htmlcontent" style="width:610px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getHtmlcontent())%></textarea>
						</div>
					</div>
				</div>
					
			<div class="clearfix mt20" style="text-align:center;">
				<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" style="display:inline-block;" type="button">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input class="btn btn-pop ml20" value="返回" onclick="window.history.back()" type="button">
			</div>
			</div>
		</div>
		</div>
	</div>
</div>

<input type="hidden" name="eduCourseInfo.courseid" value="<bean:write property="courseid" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.courseno" value="<bean:write property="courseno" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.status" value="<bean:write property="status" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.hits" value="<bean:write property="hits" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.collects" value="<bean:write property="collects" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.studys" value="<bean:write property="studys" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.score" value="<bean:write property="score" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.scoreusers" value="<bean:write property="scoreusers" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.createdate" value="<bean:write property="createdate" name="model"/>"/>
<input type="hidden" name="userid" value="<bean:write property="sysUserInfo.userid" name="model"/>"/>
<input type="hidden" name="eduCourseInfo.unitid" value="<bean:write property="unitid" name="model"/>"/>
</html:form>
</body>
<script>
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="eduCourseInfo.htmlcontent"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}

function saveRecord(){
	if(validateForm('form[name=eduCourseInfoActionForm]')){
		//同步数据后可以直接取得textarea的value
		keditor.sync();
		
		var objectForm = document.eduCourseInfoActionForm;
	  	objectForm.action="/courseCenter.do?method=updateSaveCourse";
	  	objectForm.submit();
	}
}

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=course&pathtype=D';
	diag.Width = 350;
	diag.Height = 180;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
			var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
			document.getElementById('uploadImgShow').src = "/upload/" + uploadimageurl
			document.getElementById('uploadImg').value = uploadimageurl;
		}
		diag.close();
	};
	diag.show();
}
</script>
</html>
<%!
private String htmlspecialchars(String str) {
	if(str == null || "".equals(str)) return "";
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>