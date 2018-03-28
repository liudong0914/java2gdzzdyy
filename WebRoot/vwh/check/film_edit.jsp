<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmInfo"%>
<%@page import="com.wkmk.vwh.bo.VwhComputerInfo"%>
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

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->
<%@ include file="/edu/select/select_js.jsp"%>
<%VwhFilmInfo model = (VwhFilmInfo)request.getAttribute("model"); %>
</head>
<body>
<div>
<div class="box1" position="center">
<html:form action="/vwhFilmInfoCheckAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="3">修改微课信息</th>
		</tr>
		<tr>
			<td class="ali03">微课标题：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:400px;" name="vwhFilmInfo.title" value="<bean:write property="title" name="model"/>"/><span class="star">*</span></td>
			<td class="ali01" width="90" rowspan="4" >
              <img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改照片" width="90" height="120" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
              <input type="hidden" id="uploadImg" name="vwhFilmInfo.sketch" value='<bean:write property="sketch" name="model"/>'/>
            </td>
		</tr>
		<tr>
			<td class="ali03">主讲教师：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:200px;" name="vwhFilmInfo.actor" value="<bean:write property="actor" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">微课分类：</td>
			<td class="ali01">
				学科：<input type="text" style="width:80px;background-color:#f7f8f9;color:#888;" readonly="readonly" name="subjectname" id="subjectname" value="${subjectInfo.subjectname }" onclick="selectSubject()"/>
                    年级：<input type="text" style="width:100px;background-color:#f7f8f9;color:#888;" readonly="readonly" name="gradename" id="gradename" value="${model.eduGradeInfo.gradename }" onclick="selectGrade()"/>
                    知识点：<input type="text" style="width:180px;background-color:#f7f8f9;color:#888;" readonly="readonly" name="knopointname" id="knopointname" value="${knopointnames }" onclick="selectKnopoint()"/>
                <input type="hidden" name="subjectid" id="subjectid" value="${subjectInfo.subjectid }"/>
                <input type="hidden" name="gradeid" id="gradeid" value="${model.eduGradeInfo.gradeid }"/>
                <input type="hidden" name="knopointid" id="knopointid" value="${knopointids }"/>
                <input type="hidden" name="knopointidupdate" id="knopointidupdate" value="0"/>
			</td>
		</tr>
		<tr>
			<td class="ali03">视频服务器：</td>
			<td class="ali01">
			   <select name="vwhFilmInfo.computerid">
               <%
               List computerList = (List)request.getAttribute("computerList");
               VwhComputerInfo computer = null;
               for(int i=0, size=computerList.size(); i<size; i++){
              	computer = (VwhComputerInfo)computerList.get(i);
              %>
                   <option value="<%=computer.getComputerid() %>" <%if(computer.getComputerid().intValue() == model.getComputerid().intValue()){ %>selected="selected"<%} %>><%=computer.getComputername() %></option>
               <%} %>
               </select>
			</td>
		</tr>
        <tr>
			<td class="ali03">微课简介：</td>
			<td class="ali01"><textarea name="vwhFilmInfo.keywords" style="width:400px;"><bean:write property="keywords" name="model"/></textarea></td>
		</tr>
		<tr>
			<td class="ali03">详细描述：</td>
			<td class="ali01" colspan="2">
				<textarea name="vwhFilmInfo.descript" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getDescript())%></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord('1')" id="btnsave"><span class="icon_item">审核</span></button>
				<button type="button" class="margin_right5" onclick="saveRecord('0')" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="vwhFilmInfo.filmid" value="<bean:write name="model" property="filmid"/>"/>

<input type="hidden" value="<bean:write name="title"/>" name="title"/>
<input type="hidden" value="<bean:write name="actor"/>" name="actor"/>
<input type="hidden" value="<bean:write name="username"/>" name="username"/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="vwhFilmInfo.descript"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}

function saveRecord(status){
	if(validateForm('form[name=vwhFilmInfoActionForm]')){
		var objectForm = document.vwhFilmInfoActionForm;
		
		if(document.getElementById("knopointid").value == ""){
			top.Dialog.alert("请选择知识点!");
	    	return false;
	  	}
	  	
		//同步数据后可以直接取得textarea的value
		keditor.sync();
	  	objectForm.action="vwhFilmInfoCheckAction.do?method=<bean:write name="act"/>&status=" + status;
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.vwhFilmInfoActionForm.action="/vwhFilmInfoCheckAction.do?method=list";
  	document.vwhFilmInfoActionForm.submit();
}

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=vwh&pathtype=ID&sketch=true&swidth=160&sheight=213';
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