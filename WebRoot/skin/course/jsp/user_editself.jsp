<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfoDetail"%>
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
				<logic:equal value="1" name="prompttag"><span style="padding-left:20px;color:green;">修改个人信息成功！</span></logic:equal>
				<logic:equal value="0" name="prompttag"><span style="padding-left:20px;color:green;">修改个人信息失败！</span></logic:equal>
				</logic:present>
			</div>
			<div class="mainCon">
			<div class="tab" style="margin-top:-10px">
              <ul>
                <li class="borlnone current"><a href="/courseCenter.do?method=beforeUpdateSelf" class="jobInfo">修改个人信息</a></li>
                <li class=""><a href="/courseCenter.do?method=modifyPassword" class="course">修改密码</a></li>
              </ul>
            </div>
			<div class="mainCon-bd">
				<div class="form-body creatCourseStep">
					<div class="form-row form-row01">
						<div class="label">
							<label>
							用户名：
							</label>
						</div>
						<div class="input">${model.loginname }</div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							<i class="text-red must">*</i>
							真实姓名：
							</label>
						</div>
						<div class="input">
							<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="username" value="${model.username }" style="width:200px;" type="text">
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
								<img src="${model.flags }" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="110" height="120" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
					            <input type="hidden" id="uploadImg" name="photo" value='${model.photo }'/>
							</div>
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							性别：
							</label>
						</div>
						<div class="input jobWrap">
							<java2code:option  name="sex" codetype="sex" property="sex" ckname="1"/>
					    </div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							电话：
							</label>
						</div>
						<div class="input">
							<input keepDefaultStyle="true" class="ipt-text formEleToVali" name="telephone" value="${detail.telephone }" style="width:200px;" type="text">
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							地址：
							</label>
						</div>
						<div class="input">
							<input keepDefaultStyle="true" class="ipt-text formEleToVali" name="address" value="${detail.address }" style="width:300px;" type="text">
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							学历：
							</label>
						</div>
						<div class="input jobWrap">
						 	<select name="education" keepDefaultStyle="true" class="ipt-text" style="width:120px;height:34px;">
				              	<%
				              	SysUserInfoDetail detail = (SysUserInfoDetail)request.getAttribute("detail");
				              	String[] typename = Constants.getCodeTypename("education");
				              	String[] typeid = Constants.getCodeTypeid("education");
				              	for(int i=0, size=typename.length; i<size; i++){
				              	%>
				              	<option value="<%=typeid[i] %>" <%if(typeid[i].equals(detail.getEducation())){ %>selected="selected"<%} %>><%=typename[i] %></option>
				              	<%} %>
			              	</select>
					    </div>
					</div>
					
					<logic:equal value="1" name="model" property="usertype">
					<div class="form-row">
						<div class="label">
							<label>
							<i class="text-red must">*</i>
							职称：
							</label>
						</div>
						<div class="input">
							<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="thetitle" value="${detail.thetitle }" style="width:300px;" type="text">
						</div>
					</div>
					
					<div class="form-row">
						<div class="label">
							<label>
							<i class="text-red must">*</i>
							专业：
							</label>
						</div>
						<div class="input">
							<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="major" value="${detail.major }" style="width:300px;" type="text">
						</div>
					</div>
					</logic:equal>
					
					<div class="form-row">
						<div class="label">
						<label>个人介绍：</label>
						</div>
						<div class="input">
							<div class="fl">
							<textarea class="formEleToVali" name="descript" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(detail.getDescript())%></textarea>
							</div>
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
		//同步数据后可以直接取得textarea的value
		keditor.sync();
		
		var objectForm = document.eduCourseInfoActionForm;
	  	objectForm.action="/courseCenter.do?method=updateSaveSelf";
	  	objectForm.submit();
	}
}

function uploadPhoto(){
	var diag = new Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=user&pathtype=ID';
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