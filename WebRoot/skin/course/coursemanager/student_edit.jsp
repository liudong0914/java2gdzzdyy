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
			<div class="input">
				<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="sysUserInfo.loginname" value="${model.loginname }" style="width:200px;" type="text" onblur="checkCode(this.value, '1')"><span id="div_loginname" style="color:red;display:none;">此登录名已被其他用户注册!</span></td>
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				真实姓名：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="sysUserInfo.username" value="${model.username }" style="width:200px;" type="text">
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				用户密码：
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
		
		<div class="form-row">
			<div class="label">
				<label>
				头像：
				</label>
			</div>
			<div class="input">
				<div class="fl" width="550">
					<img src="/upload/${model.photo }" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="110" height="120" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
		            <input type="hidden" id="uploadImg" name="sysUserInfo.photo" value='${model.photo }'/>
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
				<java2code:option  name="sysUserInfo.sex" codetype="sex" property="sex" ckname="1"/>
		    </div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				电话：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="ipt-text formEleToVali" name="sysUserInfoDetail.telephone" value="${detail.telephone }" style="width:200px;" type="text">
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				地址：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="ipt-text formEleToVali" name="sysUserInfoDetail.address" value="${detail.address }" style="width:300px;" type="text">
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				学历：
				</label>
			</div>
			<div class="input jobWrap">
			 	<select name="sysUserInfoDetail.education" keepDefaultStyle="true" class="ipt-text" style="width:120px;height:34px;">
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
		
		<div class="form-row">
			<div class="label">
			<label>个人介绍：</label>
			</div>
			<div class="input">
				<div class="fl">
				<textarea keepDefaultStyle="true" class="formEleToVali" name="sysUserInfoDetail.descript" style="width:610px;height:160px;"><bean:write property="descript" name="detail"/></textarea>
				</div>
			</div>
		</div>
		<%-- <div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				院企用户：
				</label>
			</div>
			<div class="input">
				<input type="radio" style="margin-right: 2px;" name="eduCourseUser.vip" value="0" <%if(eduCourseUser.getVip().equals("0")){ %>checked="checked"<%} %> />否&nbsp;
                <input type="radio" style="margin-right: 2px;" name="eduCourseUser.vip" value="1" <%if(eduCourseUser.getVip() .equals("1")){ %>checked="checked"<%} %>/>是&nbsp;
			</div>
		</div> --%>
		
	<div class="clearfix mt20" style="text-align:center;">
		<%
		String isAuhtor = (String)session.getAttribute("isAuhtor");
		Map moduleidMap = (Map)session.getAttribute("moduleidMap");
		String moduleidType = (String)moduleidMap.get("6");
		if("1".equals(isAuhtor) || "2".equals(moduleidType)){
		%>
		<input class="btn btn-pop ml20" value="保存" id="btn_save" onclick="saveRecord()" style="display:inline-block;" type="button">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<%} %>
		<input class="btn btn-pop ml20" value="返回" onclick="goBack()" type="button">
	</div>
	</div>
</div>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="courseclassid" value="<bean:write name="courseclassid"/>"/>
<input type="hidden" name="usertype" value="<bean:write name="usertype"/>"/>
<input type="hidden" name="eduCourseUser.courseuserid"   value="<bean:write property="courseuserid"  name="eduCourseUser"/>"/>
<input type="hidden" name="eduCourseUser.courseclassid" value="<bean:write property="courseclassid"  name="eduCourseUser"/>"/>
<input type="hidden" name="eduCourseUser.courseid"    value="<bean:write property="courseid"  name="eduCourseUser"/>"/>
<input type="hidden" name="eduCourseUser.status"    value="<bean:write property="status"  name="eduCourseUser"/>"/>
<input type="hidden" name="eduCourseUser.usertype"    value="<bean:write property="usertype"  name="eduCourseUser"/>"/>

<input type="hidden" name="sysUserInfo.userid" value='<bean:write property="userid"  name="model"/>'/>
<input type="hidden" name="sysUserInfo.stno" value='<bean:write property="stno"  name="model"/>'/>
<input type="hidden" name="sysUserInfo.password" value='<bean:write property="password"  name="model"/>' />
<input type="hidden" name="sysUserInfo.status" value='<bean:write property="status" name="model"/>' />
<input type="hidden" name="sysUserInfo.unitid" id="sysUserInfo.unitid" value='<bean:write property="unitid"  name="model"/>' />
<input type="hidden" name="sysUserInfo.temppass" value='<bean:write property="temppass"  name="model"/>' />
<input type="hidden" name="sysUserInfo.uuid" value='<bean:write property="uuid"  name="model"/>' />
<input type="hidden" name="sysUserInfo.unitid" value='<bean:write property="unitid"  name="model"/>' />
<input type="hidden" name="sysUserInfo.money" value='<bean:write property="money"  name="model"/>' />
<input type="hidden" name="sysUserInfo.authentication" value='<bean:write property="authentication"  name="model"/>' />
<input type="hidden" name="sysUserInfo.mobile" value='<bean:write property="mobile"  name="model"/>' />
<input type="hidden" name="sysUserInfo.usertype" value='<bean:write property="usertype"  name="model"/>' />

<input type="hidden" name="sysUserInfoDetail.createdate" value='<bean:write property="createdate" name="detail"/>'/>
<input type="hidden" name="sysUserInfoDetail.pwdquestion" value='<bean:write property="pwdquestion"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.pwdanswer" value='<bean:write property="pwdanswer"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.flag" value='<bean:write property="flag"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.lastlogin" value='<bean:write property="lastlogin"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.logintimes" value='<bean:write property="logintimes"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.updattime" value='<bean:write property="updatetime"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.mood" value='<bean:write property="mood"  name="detail"/>' />
		
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
</html:form>

</body>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseUserActionForm]')){
		var newpassword = document.getElementById("newpassword").value;
		var repassword = document.getElementById("repassword").value;
		if(newpassword != repassword){
			document.getElementById("pwdtips").style.display = "";
			return;
		}
		var objectForm = document.eduCourseUserActionForm;
	  	objectForm.action="/eduCourseUserAction.do?method=${act }";
	  	objectForm.submit();
	}
}
function goBack(){
	var objectForm = document.eduCourseUserActionForm;
  	objectForm.action="/eduCourseUserAction.do?method=studentList";
  	objectForm.submit();
}
function checkCode(value,checktype){
  	if(value != ''){
	  	var url = 'sysUserInfoAction.do?method=checkName&checkname='+value+'&checktype='+checktype+'&ram=' + Math.random();
	  	$.ajax({
		  type: 'post',
		  url: url,
		  async: false,//同步请求
		  //data: data,
		  dataType:'text',
		  success: function(data){
		  	if(data == '1'){
		  	  document.getElementById("div_loginname").style.display = '';
		  	 document.getElementById("btn_save").style.display = 'none';
		  	}else{
		  		document.getElementById("div_loginname").style.display = 'none';
			  	document.getElementById("btn_save").style.display = '';
		  	}
		  }
		});
  	}
}
function uploadPhoto(){
	var diag = new top.Dialog();
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
function changeArea(citycode, type, selectid){
	if(citycode != ""){
		$.ajax({
	        type: "get",
	        async: false,
	        url: "sysAreaInfoAction.do?method=getAareByCitycode&citycode=" + citycode + "&type=" + type + "&ram=" + Math.random(),
	        dataType: "text",
	        success: function(data){
	        	$("#"+selectid).html(data);
	        	$("#"+selectid).render();//刷新下拉框
	        }
		});
	}else{
		if(selectid == 'selectCity'){
			$("#selectCity").html("<option value=''>请选择...</option>");
			$("#selectCity").render();//刷新下拉框
			$("#selectCounty").html("<option value=''>请选择...</option>");
			$("#selectCounty").render();//刷新下拉框
		}else if(selectid == 'selectCounty'){
			$("#selectCounty").html("<option value=''>请选择...</option>");
			$("#selectCounty").render();//刷新下拉框
		}
	}
}
</script>
</html>
