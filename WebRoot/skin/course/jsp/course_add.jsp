<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.date.DateTime"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Constants.PRODUCT_NAME %>-创建课程</title>
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
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
<%@ include file="left.jsp" %>

<html:form action="/courseCenter.do?method=saveCourse" method="post">
<div class="rightCon">
	<div class="main">
		<div class="mainBox">
			<div class="mainTit">
				<h3 class="title">创建课程</h3>
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
				              	<option value="1">院校企业</option>
				              	<option value="2">退役军人</option>
				              	<option value="3">医护行业</option>
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
							<input id="eduCourseInfo.title" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="eduCourseInfo.title" value="" style="width:306px;" type="text">
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
							<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="eduCourseInfo.courseno" value="" style="width:120px;" type="text">（按编号排序）
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
							<input id="eduCourseInfo.price" keepDefaultStyle="true" class="validate[required,custom[onlyNumberWide]] ipt-text ipt-text01 formEleToVali" name="eduCourseInfo.price" value="" type="text" style="width:120px;"> (元)
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							价格注明文字：
							</label>
						</div>
						<div class="input">
							<input id="eduCourseInfo.note" keepDefaultStyle="true" class="ipt-text formEleToVali" name="eduCourseInfo.note" value="" style="width:306px;" type="text">(可以为空)
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
							<input id="eduCourseInfo.classhour" keepDefaultStyle="true" class="validate[required,custom[onlyNumber],length[1,3]] ipt-text ipt-text01 formEleToVali" name="eduCourseInfo.classhour" value="" type="text" style="width:120px;"> (课时)
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
							<input type="text" name="eduCourseInfo.startdate" id="eduCourseInfo.startdate" readonly="readonly" value="<%=DateTime.getDate() %>" class="date validate[required,custom[date]] ipt-text ipt-text01 formEleToVali" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:150px;"/>
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
							<input type="text" name="eduCourseInfo.enddate" id="eduCourseInfo.enddate" readonly="readonly" value="" class="date validate[required,custom[date]] ipt-text ipt-text01 formEleToVali" dateFmt="yyyy-MM-dd HH:mm:ss" style="width:150px;"/>
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
								<img src="/upload/default.jpg" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="328" height="170" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
					            <input type="hidden" id="uploadImg" name="eduCourseInfo.sketch" value='default.jpg'/>
							</div>
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
						<label>课程目标：</label>
						</div>
						<div class="input">
						<div class="fl">
						<textarea keepDefaultStyle="true" id="eduCourseInfo.objectives" class="formEleToVali" name="eduCourseInfo.objectives" style="width:610px;height:160px;"></textarea>
						</div>
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
						<label>课程描述：</label>
						</div>
						<div class="input">
							<div class="fl">
							<textarea id="eduCourseInfo.htmlcontent" class="formEleToVali" name="eduCourseInfo.htmlcontent" style="width:670px;height:230px;visibility:hidden;"></textarea>
							</div>
						</div>
					</div>
						
				<div class="next-steps clearfix mt20">
					<span class="next" style="cursor: pointer;" onclick="saveRecord()">下一步</span>
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
function getEndFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1 + 6;//默认结束时间延长6个月
    if(month > 12){
    	year = year + 1;
    	month = month - 12;
    }
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate + " 23:59:59";
    return currentdate;
}

var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="eduCourseInfo.htmlcontent"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
	document.getElementById("eduCourseInfo.enddate").value = getEndFormatDate();
}

function saveRecord(){
	if(validateForm('form[name=eduCourseInfoActionForm]')){
		//同步数据后可以直接取得textarea的value
		keditor.sync();
		
		var objectForm = document.eduCourseInfoActionForm;
	  	objectForm.action="/courseCenter.do?method=saveCourse";
	  	objectForm.submit();
	}
}

function uploadPhoto(){
	var diag = new Dialog();
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
