<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.sys.bo.SysUserInfoDetail"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
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
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/sysUserInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3"><logic:equal value="addSave" name="act">新增用户</logic:equal><logic:equal value="updateSave" name="act">修改用户</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">登录名：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required,length[0,25]]" name="sysUserInfo.loginname" value="<bean:write property="loginname" name="model"/>" <logic:equal value="updateSave" name="act">readonly="readonly"</logic:equal> onblur="checkCode(this.value, '1', 'div_loginname')"/><span class="star">*</span><span id="div_loginname" style="color:red;display:none;">此登录名已被其他用户注册!</span></td>
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
			<td class="ali03">昵称：</td>
			<td class="ali01"><input type="text" style="width:150px;" name="sysUserInfo.nickname" value="<bean:write property="nickname" name="model"/>"/></td>
		</tr>
		<logic:notEqual value="addSave" name="act">
		<tr>
			<td class="ali03">用户密码：</td>
			<td class="ali01"><input type="password" style="width:150px;" name="password" id="password" value=""/></td>
		</tr>
		<tr>
			<td class="ali03">确认密码：</td>
			<td class="ali01"><input type="password" style="width:150px;" name="passwordagain" id="passwordagain" value=""/></td>
		</tr>
		<tr>
			<td class="ali03">&nbsp;</td>
			<td class="ali01">(<font color="red">如果不修改,请保持用户密码和确认密码为空)</td>
		</tr>
		</logic:notEqual>
		<logic:equal value="addSave" name="act">
		<tr>
			<td class="ali03">用户密码：</td>
			<td class="ali01"><input type="password" style="width:150px;" class="validate[required]" name="password" id="password" value=""/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">确认密码：</td>
			<td class="ali01"><input type="password" style="width:150px;" class="validate[required]" name="passwordagain" id="passwordagain" value=""/><span class="star">*</span></td>
		</tr>
		</logic:equal>
		  <%
          SysUserInfo sui = (SysUserInfo)request.getAttribute("model");
          String[] usertypeids = Constants.getCodeTypeid("usertype");
          String[] usertypenames = Constants.getCodeTypename("usertype");
          %>
		<tr>
			<td class="ali03">用户类型：</td>
			<td class="ali01">
			  <select name="sysUserInfo.usertype" onchange="changeUsertype(this.value)">
              	<%
              	for(int i=0; i<usertypeids.length; i++){
              	%>
              	<option value="<%=usertypeids[i] %>" <%if(usertypeids[i].equals(sui.getUsertype())){ %>selected="selected"<%} %>><%=usertypenames[i] %></option>
              	<%} %>
              </select>
			</td>
		</tr>
		<tr id="tr_studentno" <%if(!"2".equals(sui.getUsertype())){ %>style="display:none"<%} %>>
			<td class="ali03">学号：</td>
			<td class="ali01"><input type="text" name="sysUserInfo.studentno" value="<bean:write property="studentno" name="model"/>" onblur="checkCode(this.value, '2', 'div_studentno')"/><span id="div_studentno" style="color:red;display:none;">学号输入错误, 当前学号已存在!</span></td>
		</tr>
		<tr id="tr_education" <%if("2".equals(sui.getUsertype())){ %>style="display:none"<%} %>>
			<td class="ali03">学历：</td>
			<td class="ali01"><java2code:option  name="sysUserInfoDetail.education" codetype="education" property="education" valuename="detail"/></td>
		</tr>
		<tr id="tr_jobtitle" <%if("2".equals(sui.getUsertype())){ %>style="display:none"<%} %>>
			<td class="ali03">职称：</td>
			<td class="ali01"><java2code:option  name="sysUserInfoDetail.jobtitle" codetype="jobtitle" property="jobtitle" valuename="detail"/></td>
		</tr>
		<tr>
			<td class="ali03">所属学段：</td>
			<td class="ali01"><java2code:option  name="sysUserInfo.xueduan" codetype="xueduan" property="xueduan" ckname="1"/></td>
		</tr>
		<tr>
			<td class="ali03">性别：</td>
			<td class="ali01"><java2code:option  name="sysUserInfo.sex" codetype="sex" property="sex" ckname="1"/></td>
		</tr>
		<tr>
			<td class="ali03">出生日期：</td>
			<td class="ali01">
				<input type="text" name="sysUserInfoDetail.birthday" readonly="readonly" value="<bean:write property="birthday"  name="detail"/>" class="date validate[required,custom[date]]"/>(日期格式：2008-08-08)
			</td>
		</tr>
		<tr>
			<td class="ali03">证件类型：</td>
			<td class="ali01"><java2code:option  name="sysUserInfoDetail.cardtype" codetype="cardtype" property="cardtype" valuename="detail"/></td>
		</tr>
		<tr>
			<td class="ali03">证件号：</td>
			<td class="ali01"><input type="text" style="width:200px;" name="sysUserInfoDetail.cardno" value="<bean:write property="cardno" name="detail"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">邮件地址：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[email]" name="sysUserInfo.email" value="<bean:write property="email" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">QQ：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[qq]" name="sysUserInfoDetail.qq" value="<bean:write property="qq" name="detail"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">MSN：</td>
			<td class="ali01"><input type="text" style="width:200px;" name="sysUserInfoDetail.msn" value="<bean:write property="msn" name="detail"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">联系电话：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[telephone]" name="sysUserInfoDetail.telephone" value="<bean:write property="telephone" name="detail"/>"/></td>
		</tr>
		<%-- 
		<tr>
			<td class="ali03">手机号：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[mobilephone]" name="sysUserInfo.mobile" value="<bean:write property="mobile" name="model"/>"/></td>
		</tr>
		--%>
		<tr>
			<td class="ali03">民族：</td>
			<td class="ali01"><java2code:option name="sysUserInfoDetail.nation" codetype="nation" property="nation" valuename="detail"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">籍贯：</td>
			<td class="ali01">
				<select name="sysUserInfoDetail.province" id="selectProvince" boxHeight="130" onchange="changeArea(this.value, '2', 'selectCity')">
					<option value="">请选择...</option>
					<%
					SysUserInfoDetail detail = (SysUserInfoDetail)request.getAttribute("detail");
					List provinceList = (List)request.getAttribute("provinceList");
					SysAreaInfo sai = null;
					for(int i=0, size=provinceList.size(); i<size; i++){
						sai = (SysAreaInfo)provinceList.get(i);
					%>
					<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(detail.getProvince())){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
					<%} %>
				</select>
				<select name="sysUserInfoDetail.city" id="selectCity" boxHeight="130" onchange="changeArea(this.value, '2', 'selectCounty')">
					<option value="">请选择...</option>
					<%
					List cityList = (List)request.getAttribute("cityList");
					if(cityList != null && cityList.size() > 0){
					for(int i=0, size=cityList.size(); i<size; i++){
						sai = (SysAreaInfo)cityList.get(i);
					%>
					<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(detail.getCity())){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
					<%}} %>
				</select>
				<select name="sysUserInfoDetail.county" id="selectCounty" boxHeight="130">
					<option value="">请选择...</option>
					<%
					List countyList = (List)request.getAttribute("countyList");
					if(countyList != null && countyList.size() > 0){
					for(int i=0, size=countyList.size(); i<size; i++){
						sai = (SysAreaInfo)countyList.get(i);
					%>
					<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(detail.getCounty())){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
					<%}} %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="ali03">邮编：</td>
			<td class="ali01"><input type="text" class="validate[zipcode]" name="sysUserInfoDetail.postcode" value="<bean:write property="postcode" name="detail"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">联系地址：</td>
			<td class="ali01"><input type="text" style="width:300px;" name="sysUserInfoDetail.address" value="<bean:write property="address" name="detail"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">个人简介：</td>
			<td class="ali01"><textarea style="width:300px;" name="sysUserInfoDetail.descript"><bean:write property="descript" name="detail"/></textarea></td>
		</tr>
		<tr id="tr_canaddcourse" <%if(!"1".equals(sui.getUsertype())){ %>style="display:none"<%} %>>
			<td class="ali03">创建课程：</td>
			<td class="ali01"><java2code:option  name="sysUserInfoDetail.canaddcourse" codetype="canaddcourse" valuename="detail" property="canaddcourse" ckname="1"/></td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
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

<input type="hidden" name="sysUserInfoDetail.createdate" value='<bean:write property="createdate" name="detail"/>'/>
<input type="hidden" name="sysUserInfoDetail.pwdquestion" value='<bean:write property="pwdquestion"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.pwdanswer" value='<bean:write property="pwdanswer"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.flag" value='<bean:write property="flag"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.lastlogin" value='<bean:write property="lastlogin"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.logintimes" value='<bean:write property="logintimes"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.updattime" value='<bean:write property="updatetime"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.mood" value='<bean:write property="mood"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.thetitle" value='<bean:write property="thetitle"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.major" value='<bean:write property="major"  name="detail"/>' />

<input type="hidden" name="loginname" value='<bean:write name="loginname"/>'/>
<input type="hidden" name="username" value='<bean:write name="username"/>'/>
<input type="hidden" name="sex" value='<bean:write name="sex"/>'/>
<input type="hidden" name="usertype" value='<bean:write name="usertype"/>'/>
<input type="hidden" name="studentno" value='<bean:write name="studentno"/>'/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
var cflag1 = false;
var cflag2 = false;
function saveRecord(){
	if(validateForm('form[name=sysUserInfoActionForm]')){
		if(document.getElementById('password').value!=document.getElementById('passwordagain').value){
		    top.Dialog.alert("两次输入的密码不一致，请重试!");
		    return false;
		}
		if(cflag1){
		    document.getElementById('trid').style.display = '';
		 	return false;
		}
		if(cflag2){
		    document.getElementById('div_studentno').style.display = '';
		 	return false;
		}
  
		var objectForm = document.sysUserInfoActionForm;
	  	objectForm.action="sysUserInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function checkCode(value,checktype,divid){
  	var value0 = '';
  	if(checktype == '1'){
  		value0 = '<bean:write property="loginname" name="model"/>';
  	}
  	if(checktype == '2'){
  		value0 = '<bean:write property="studentno" name="model"/>';
  	}
  	if(value != '' && value != value0){
	  	var url = 'sysUserInfoAction.do?method=checkName&checkname='+value+'&checktype='+checktype+'&ram=' + Math.random();
	  	$.ajax({
		  type: 'post',
		  url: url,
		  async: false,//同步请求
		  //data: data,
		  dataType:'text',
		  success: function(data){
		  	if(data == '1'){
		  	  if(checktype == '1') cflag1 = true;
		  	  if(checktype == '2') cflag2 = true;
		  	  document.getElementById(divid).style.display = '';
		  	}else{
		  	  if(checktype == '1') cflag1 = false;
		  	  if(checktype == '2') cflag2 = false;
		  	  document.getElementById(divid).style.display = 'none';
		  	}
		  }
		});
  	}
}

function backRecord(){
  	document.sysUserInfoActionForm.action="/sysUserInfoAction.do?method=list";
  	document.sysUserInfoActionForm.submit();
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

function changeUsertype(usertype){
	if(usertype == '1'){//老师
		document.getElementById('tr_studentno').style.display = 'none';
		document.getElementById('tr_education').style.display = '';
		document.getElementById('tr_jobtitle').style.display = '';
		document.getElementById('tr_canaddcourse').style.display = '';
	}else if(usertype == '2'){//学生
		document.getElementById('tr_studentno').style.display = '';
		document.getElementById('tr_education').style.display = 'none';
		document.getElementById('tr_jobtitle').style.display = 'none';
		document.getElementById('tr_canaddcourse').style.display = 'none';
	}else{
		document.getElementById('tr_studentno').style.display = 'none';
		document.getElementById('tr_education').style.display = '';
		document.getElementById('tr_jobtitle').style.display = '';
		document.getElementById('tr_canaddcourse').style.display = 'none';
	}
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