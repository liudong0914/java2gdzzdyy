<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%
EduCourseUser eduCourseUser=(EduCourseUser)request.getAttribute("eduCourseUser");
%>
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/eduCourseUserAction.do?method=addSaveStudent" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3"><logic:equal value="addSave" name="act">新增用户</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">用户名：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required,length[0,25]]" name="sysUserInfo.loginname" value="${model.loginname }" onblur="checkCode(this.value, '1')"/><span class="star">*</span><span id="div_loginname" style="color:red;display:none;">此登录名已被其他用户注册!</span></td>
			<td class="ali01" width="90" rowspan="4" >
              <img src="/upload/<bean:write property="photo" name="model"/>" title="点击修改照片" width="90" height="120" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
              <input type="hidden" id="uploadImg" name="sysUserInfo.photo" value='<bean:write property="photo" name="model"/>'/>
            </td>
		</tr>
		<tr>
			<td class="ali03">真实姓名：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required,length[0,20]]" name="sysUserInfo.username" value="<bean:write property="username" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		
		<tr>
			<td class="ali03">用户密码：</td>
			<td class="ali01"><input type="password" style="width:150px;" class="validate[required]" name="newpassword" id="newpassword" value=""/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">确认密码：</td>
			<td class="ali01"><input type="password" style="width:150px;" class="validate[required]" name="repassword" id="repassword" value=""/><span class="star">*</span><i class="text-red must" style="display:none;" id="pwdtips">确认密码输入错误！</i></td>
		</tr>
		 
		<tr>
			<td class="ali03">性别：</td>
			<td class="ali01"><java2code:option  name="sysUserInfo.sex" codetype="sex" property="sex" ckname="1"/></td>
		</tr>
		
		<tr>
			<td class="ali03">联系电话：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[telephone]" name="sysUserInfoDetail.telephone" value="<bean:write property="telephone" name="detail"/>"/></td>
		</tr>
		
		<tr>
			<td class="ali03">学历：</td>
			<td class="ali01">
				<select name="sysUserInfoDetail.education" >
	              	<%
	              	SysUserInfoDetail detail = (SysUserInfoDetail)request.getAttribute("detail");
	              	String[] typename = Constants.getCodeTypename("education");
	              	String[] typeid = Constants.getCodeTypeid("education");
	              	for(int i=0, size=typename.length; i<size; i++){
	              	%>
	              	<option value="<%=typeid[i] %>" <%if(typeid[i].equals(detail.getEducation())){ %>selected="selected"<%} %>><%=typename[i] %></option>
	              	<%} %>
              	</select>
			</td>
		</tr>
		
		<tr>
			<td class="ali03">联系地址：</td>
			<td class="ali01"><input type="text" style="width:300px;" name="sysUserInfoDetail.address" value="<bean:write property="address" name="detail"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">个人简介：</td>
			<td class="ali01"><textarea style="width:300px;" name="sysUserInfoDetail.descript"><bean:write property="descript" name="detail"/></textarea></td>
		</tr>
		
		<%-- <tr>
			<td class="ali03">院企用户：</td>
			<td class="ali01">
				<input type="radio" style="margin-right: 2px;" name="eduCourseUser.vip" value="0" <%if(eduCourseUser.getVip().equals("0")){ %>checked="checked"<%} %> />否&nbsp;
                <input type="radio" style="margin-right: 2px;" name="eduCourseUser.vip" value="1" <%if(eduCourseUser.getVip() .equals("1")){ %>checked="checked"<%} %>/>是&nbsp;
			</td>
		</tr> --%>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="goBack()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
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
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>

<input type="hidden" id="tag" value=""/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseUserActionForm]')){
		var newpassword = document.getElementById("newpassword").value;
		var repassword = document.getElementById("repassword").value;
		var tag = document.getElementById("tag").value;
		if(newpassword != repassword){
			document.getElementById("pwdtips").style.display = "";
			return;
		}
		if(tag == "1"){
			alert("登录名重复，请重新输入！");
			return false;
		}
		var objectForm = document.eduCourseUserActionForm;
	  	objectForm.action="/eduCourseUserAction.do?method=${act }";
	  	objectForm.submit();
	}
}
function goBack(){
	var objectForm = document.eduCourseUserActionForm;
  	objectForm.action="/eduCourseUserAction.do?method=list";
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
		  	 document.getElementById("tag").value="1";
		  	}else{
		  		document.getElementById("div_loginname").style.display = 'none';
		  		document.getElementById("tag").value="0";
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
</body>
</html>