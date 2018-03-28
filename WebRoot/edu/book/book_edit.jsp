<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.edu.bo.EduBookInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
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
<html:form action="/eduBookInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3"><logic:equal value="addSave" name="act">新增教材课本</logic:equal><logic:equal value="updateSave" name="act">修改教材课本</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">标题：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required,length[0,25]]" name="eduBookInfo.title" value="<bean:write property="title" name="model"/>" <logic:equal value="updateSave" name="act">readonly="readonly"</logic:equal> onblur="checkCode(this.value, '1', 'div_loginname')"/><span class="star">*</span><span id="div_loginname" style="color:red;display:none;">此登录名已被其他用户注册!</span></td>
		</tr>
		<tr>
			<td class="ali03">副标题：</td>
			<td class="ali01"><input type="text" style="width:150px;"  name="eduBookInfo.subtitle" value="<bean:write property="subtitle" name="model"/>"/></td>
		</tr>	
		<tr>
			<td class="ali03">序号：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required,custom[onlyNumber],length[1,25]]" name="eduBookInfo.orderindex" value="<bean:write property="orderindex" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">缩略图：</td>
			<td class="ali01">
              <img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改照片" width="120" height="90" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
			  <input type="hidden" id="uploadImg" name="eduBookInfo.sketch" value='<bean:write property="sketch" name="model"/>'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">状&nbsp;&nbsp;&nbsp;&nbsp;态：</td>
			<td class="ali01"><java2code:option name="eduBookInfo.status" codetype="status4" property="status" ckname="1"></java2code:option></td>
		</tr>
		<tr>
			<td class="ali03">定价：</td>
			<td class="ali01"><input type="text" style="width:150px;" class="validate[required]" name="eduBookInfo.price" value="<bean:write property="price" name="model"/>"/><span class="star">*</span></td>
		</tr>
		  <%
		  EduBookInfo ebi = (EduBookInfo)request.getAttribute("model");
          String[] coursetypeids = Constants.getCodeTypeid("coursetypeid");
          String[] coursetypenames = Constants.getCodeTypename("coursetypeid");
          %>
		<tr>
			<td class="ali03">教材所属分类：</td>
			<td class="ali01">
			  <select name="eduBookInfo.coursetypeid">
              	<%
              	for(int i=0; i<coursetypeids.length; i++){
              	%>
              	<option value="<%=coursetypeids[i] %>" <%if(coursetypeids[i].equals(ebi.getCoursetypeid())){ %>selected="selected"<%} %>><%=coursetypenames[i] %></option>
              	<%} %>
              </select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="eduBookInfo.bookid" value='<bean:write property="bookid"  name="model"/>'/>
<input type="hidden" name="eduBookInfo.createdate" value='<bean:write property="createdate"  name="model"/>'/>
<input type="hidden" name="eduBookInfo.status" value='<bean:write property="status"  name="model"/>'/>


<input type="hidden" name="title" value='<bean:write name="title"/>'/>
<input type="hidden" name="subtitle" value='<bean:write name="subtitle"/>'/>
<input type="hidden" name="coursetypeid" value='<bean:write name="coursetypeid"/>'/>
<input type="hidden" name="status" value='<bean:write name="status"/>'/>
<input type="hidden" name="createdate" value='<bean:write name="createdate"/>'/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=eduBookInfoActionForm]')){
		var objectForm = document.eduBookInfoActionForm;
	  	objectForm.action="eduBookInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.eduBookInfoActionForm.action="/eduBookInfoAction.do?method=list";
  	document.eduBookInfoActionForm.submit();
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