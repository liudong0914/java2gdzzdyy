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
<%@ page import="com.wkmk.edu.bo.EduCourseUserModule" %>
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
EduCourseUser model=(EduCourseUser)request.getAttribute("model");
EduCourseUserModule module=(EduCourseUserModule)request.getAttribute("module");
%>
</head>

<body style="background:#fcfcfc;">
<html:form action="/eduCourseUserAction.do?method=addSaveTeacher" method="post">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
		<div class="form-row form-row01">
			<div class="label">
				<label>
				用户名：
				</label>
			</div>
			<div class="input">
				${userinfo.loginname }
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				院企用户：
				</label>
			</div>
			<div class="input">
				<input type="radio" style="margin-right: 2px;" name="eduCourseUser.vip" value="0" <%if(model.getVip().equals("0")){ %>checked="checked"<%} %> />否&nbsp;
                <input type="radio" style="margin-right: 2px;" name="eduCourseUser.vip" value="1" <%if(model.getVip().equals("1")){ %>checked="checked"<%} %>/>是&nbsp;
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				管理模块：
				</label>
			</div>
			<%
			Map moduleidMap=(Map)request.getAttribute("moduleidMap");
			%>
			<div class="input">
				<input type="checkbox" style="margin-right: 2px;" name="moduleid" <%if(moduleidMap.containsKey("1")){ %>checked="checked"<%} %> value="1" />课程信息&nbsp;&nbsp;&nbsp;&nbsp;
               	<input type="radio" style="margin-right: 2px;" name="moduletype0" <%if(moduleidMap.containsKey("1")){if(moduleidMap.get("1").equals("1")){ %>checked="checked"<%}} %> value="1" checked="checked"/>查看
                <input type="radio" style="margin-right: 2px;" name="moduletype0" <%if(moduleidMap.containsKey("1")){if(moduleidMap.get("1").equals("2")){ %>checked="checked"<%}} %> value="2" />管理</br>
               
                <input type="checkbox" style="margin-right: 2px;" name="moduleid" <%if(moduleidMap.containsKey("2")){ %>checked="checked"<%} %> value="2" />课程目录&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" style="margin-right: 2px;" name="moduletype1" <%if(moduleidMap.containsKey("2")){if(moduleidMap.get("2").equals("1")){ %>checked="checked"<%}} %> value="1" checked="checked"/>查看
                <input type="radio" style="margin-right: 2px;" name="moduletype1" <%if(moduleidMap.containsKey("2")){if(moduleidMap.get("2").equals("2")){ %>checked="checked"<%}} %> value="2" />管理</br>
                
                <input type="checkbox" style="margin-right: 2px;" name="moduleid" <%if(moduleidMap.containsKey("3")){ %>checked="checked"<%} %> value="3" />微课管理&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" style="margin-right: 2px;" name="moduletype2" <%if(moduleidMap.containsKey("3")){if(moduleidMap.get("3").equals("1")){ %>checked="checked"<%}} %> value="1" checked="checked"/>查看
                <input type="radio" style="margin-right: 2px;" name="moduletype2" <%if(moduleidMap.containsKey("3")){if(moduleidMap.get("3").equals("2")){ %>checked="checked"<%}} %> value="2" />管理</br>
                
                <input type="checkbox" style="margin-right: 2px;" name="moduleid" <%if(moduleidMap.containsKey("4")){ %>checked="checked"<%} %> value="4" />课程资源&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" style="margin-right: 2px;" name="moduletype3" <%if(moduleidMap.containsKey("4")){if(moduleidMap.get("4").equals("1")){ %>checked="checked"<%}} %> value="1" checked="checked"/>查看
                <input type="radio" style="margin-right: 2px;" name="moduletype3" <%if(moduleidMap.containsKey("4")){if(moduleidMap.get("4").equals("2")){ %>checked="checked"<%}} %> value="2" />管理</br>
                
                <input type="checkbox" style="margin-right: 2px;" name="moduleid" <%if(moduleidMap.containsKey("6")){ %>checked="checked"<%} %> value="6" />学员管理&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" style="margin-right: 2px;" name="moduletype4" <%if(moduleidMap.containsKey("6")){if(moduleidMap.get("6").equals("1")){ %>checked="checked"<%}} %> value="1" checked="checked"/>查看
                <input type="radio" style="margin-right: 2px;" name="moduletype4" <%if(moduleidMap.containsKey("6")){if(moduleidMap.get("6").equals("2")){ %>checked="checked"<%}} %> value="2" />管理</br>
                <!-- 
                <input type="checkbox" style="margin-right: 2px;" name="moduleid" <%if(moduleidMap.containsKey("7")){ %>checked="checked"<%} %> value="7" />课程答疑&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" style="margin-right: 2px;" name="moduletype5" <%if(moduleidMap.containsKey("7")){if(moduleidMap.get("7").equals("1")){ %>checked="checked"<%}} %> value="1" checked="checked"/>查看
                <input type="radio" style="margin-right: 2px;" name="moduletype5" <%if(moduleidMap.containsKey("7")){if(moduleidMap.get("7").equals("2")){ %>checked="checked"<%}} %> value="2" />管理</br>
                 -->
			</div>
		</div>
		
	<div class="clearfix mt20" style="text-align:center;">
		<input class="btn btn-pop ml20" value="保存" id="btn_save" onclick="saveRecord()" style="display:inline-block;" type="button">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-pop ml20" value="返回" onclick="goBack()" type="button">
	</div>
	</div>
</div>
<input type="hidden" name="eduCourseUser.courseuserid"   value="<bean:write property="courseuserid"  name="model"/>"/>
<input type="hidden" name="eduCourseUser.courseclassid" value="<bean:write property="courseclassid"  name="model"/>"/>
<input type="hidden" name="eduCourseUser.courseid"    value="<bean:write property="courseid"  name="model"/>"/>
<input type="hidden" name="eduCourseUser.status"    value="<bean:write property="status"  name="model"/>"/>
<input type="hidden" name="eduCourseUser.createdate"    value="<bean:write property="createdate"  name="model"/>"/>
<input type="hidden" name="eduCourseUser.usertype"    value="<bean:write property="usertype"  name="model"/>"/>
<input type="hidden" name="eduCourseUser.userid"    value="<bean:write property="userid"  name="model"/>"/>

<input type="hidden" name="sysUserInfo.userid" value='<bean:write property="userid"  name="userinfo"/>'/>
<input type="hidden" name="sysUserInfo.stno" value='<bean:write property="stno"  name="userinfo"/>'/>
<input type="hidden" name="sysUserInfo.password" value='<bean:write property="password"  name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.status" value='<bean:write property="status" name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.unitid" id="sysUserInfo.unitid" value='<bean:write property="unitid"  name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.temppass" value='<bean:write property="temppass"  name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.uuid" value='<bean:write property="uuid"  name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.unitid" value='<bean:write property="unitid"  name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.money" value='<bean:write property="money"  name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.authentication" value='<bean:write property="authentication"  name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.mobile" value='<bean:write property="mobile"  name="userinfo"/>' />
<input type="hidden" name="sysUserInfo.usertype" value='<bean:write property="usertype"  name="userinfo"/>' />

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
			var moduleid = document.getElementsByName("moduleid");
			var isChecked = false;
			for (var i = 0, l = moduleid.length; i < l; i++) {
			    if (moduleid[i].checked == true) {
			        isChecked = true;
			    }
			}
			if(!isChecked){
				alert('至少选择一个模块');
				return false;
			}
			var objectForm = document.eduCourseUserActionForm;
		  	objectForm.action="/eduCourseUserAction.do?method=${act }";
		  	objectForm.submit();
		}
}
function goBack(){
	var objectForm = document.eduCourseUserActionForm;
  	objectForm.action="/eduCourseUserAction.do?method=teacherList";
  	objectForm.submit();
}
</script>
</html>
