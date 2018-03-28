<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@page import="java.util.List"%>
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
<html:form action="/eduCourseInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3"><logic:equal value="addSave" name="act">新增信息</logic:equal><logic:equal value="updateSave" name="act">修改信息</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">课程名称：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required,length[0,25]]" name="eduCourseInfo.title" value="<bean:write property="title" name="model"/>" /><span class="star">*</span></td>
			<td class="ali01" width="90" rowspan="4" >
              <img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改缩略图" width="90" height="120" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
              <input type="hidden" id="uploadImg" name="eduCourseInfo.sketch" value='<bean:write property="sketch" name="model"/>'/>
            </td>
		</tr>
		<tr>
			<td class="ali03">课程编号：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required,length[0,4]]" name="eduCourseInfo.courseno" value="<bean:write property="courseno" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		<tr>
			<td class="ali03">状态：</td>
			<td class="ali01">
					<c:if test="${model.status == '0' }">
					私有
					</c:if>
					<c:if test="${model.status == '1' }">
					审核通过
					</c:if>
					<c:if test="${model.status == '2' }">
					待审核
					</c:if>
					<c:if test="${model.status == '3' }">
					审核不通过
					</c:if>
					<c:if test="${model.status == '4' }">
					禁用
					</c:if>
			</td>
		</tr>
		  <%
		  String coursetypeid = (String)request.getAttribute("coursetypeid");
          %>
		<tr>
			<td class="ali03">课程类型：</td>
			<td class="ali01">
			  <select name="eduCourseInfo.coursetypeid">
              	<option value="1" <%if(coursetypeid.equals('1')){ %>selected="selected"<%} %>>院校企业</option>
              	<option value="2" <%if(coursetypeid.equals('2')){ %>selected="selected"<%} %>>退役军人</option>
              	<option value="3" <%if(coursetypeid.equals('3')){ %>selected="selected"<%} %>>医护行业</option>
              </select>
			</td>
		</tr>
		<tr>
			<td class="ali03">总价：</td>
			<td class="ali01" colspan="2"><input type="text" style="width:200px;" name="eduCourseInfo.price" value="<bean:write property="price" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">总课时：</td>
			<td class="ali01" colspan="2"><input type="text" style="width:200px;" name="eduCourseInfo.classhour" value="<bean:write property="classhour" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">查看次数：</td>
			<td class="ali01" colspan="2"><bean:write property="hits" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">收藏次数：</td>
			<td class="ali01" colspan="2"><bean:write property="collects" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">学习次数：</td>
			<td class="ali01" colspan="2"><bean:write property="studys" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">课程目标：</td>
			<td class="ali01" colspan="2"><textarea style="width:300px;" name="eduCourseInfo.objectives"><bean:write property="objectives" name="model"/></textarea></td>
		</tr>
		<tr>
			<td class="ali03">课程描述：</td>
			<td class="ali01" colspan="2"><textarea style="width:300px;" name="eduCourseInfo.htmlcontent"><bean:write property="htmlcontent" name="model"/></textarea></td>
		</tr>
		<tr>
			<td class="ali03">创建时间：</td>
			<td class="ali01" colspan="2">
				<bean:write property="createdate"  name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03">开始日期：</td>
			<td class="ali01" colspan="2">
				<input type="text" name="eduCourseInfo.startdate" readonly="readonly" value="<bean:write property="startdate"  name="model"/>" class="date validate[required,custom[date]]"/>(日期格式：2008-08-08)
			</td>
		</tr>
		<tr>
			<td class="ali03">结束日期：</td>
			<td class="ali01" colspan="2">
				<input type="text" name="eduCourseInfo.enddate" readonly="readonly" value="<bean:write property="enddate"  name="model"/>" class="date validate[required,custom[date]]"/>(日期格式：2008-08-08)
			</td>
		</tr>
		<tr>
			<td class="ali03">创建教师：</td>
			<td class="ali01" colspan="2">
				<bean:write property="sysUserInfo.username" name="model"/>
			</td>
		</tr>
		
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="eduCourseInfo.courseid" value='<bean:write property="courseid"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.unitid" value='<bean:write property="unitid"  name="model"/>'/>
<input type="hidden" name="userid" value='<bean:write property="sysUserInfo.userid"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.status" value='<bean:write property="status"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.createdate" value='<bean:write property="createdate"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.hits" value='<bean:write property="hits"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.collects" value='<bean:write property="collects"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.studys" value='<bean:write property="studys"  name="model"/>'/>

<input type="hidden" name="courseid" id="courseid" value='<bean:write name="courseid"/>'/>
<input type="hidden" name="courseclassid"  id="courseclassid" value='<bean:write name="courseclassid"/>'/>
<input type="hidden" name="tag" value='<bean:write name="tag"/>'/>

</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduCourseInfoActionForm]')){
  
		var objectForm = document.eduCourseInfoActionForm;
	  	objectForm.action="eduCourseInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
	var courseid =document.getElementById("courseid").value;
	var courseclassid =document.getElementById("courseclassid").value;
  	document.eduCourseInfoActionForm.action="/eduCourseInfoAction.do?method=index&courseid="+courseid+"&courseclassid="+courseclassid;
  	document.eduCourseInfoActionForm.submit();
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
</script>
</body>
</html>