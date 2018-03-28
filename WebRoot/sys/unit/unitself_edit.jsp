<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUnitInfo"%>
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

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->
<%SysUnitInfo model=(SysUnitInfo)request.getAttribute("model");%>
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/sysUnitInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2">站点信息</th>
		</tr>
		<tr>
			<td class="ali03">单位名称：</td>
			<td class="ali01"><input type="text" style="width:300px;" class="validate[required,length[0,100]]" name="sysUnitInfo.unitname" value="<bean:write property="unitname" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">机构代码：</td>
			<td class="ali01"><input type="text" style="width:300px;" class="validate[required]" name="sysUnitInfo.unitno" value="<bean:write property="unitno" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		<tr>
			<td class="ali03">单位简称：</td>
			<td class="ali01"><input type="text" style="width:300px;" class="validate[required]" name="sysUnitInfo.shortname" value="<bean:write property="shortname" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">英文名称：</td>
			<td class="ali01"><input type="text" style="width:300px;" name="sysUnitInfo.ename" value="<bean:write property="ename" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">单位网址：</td>
			<td class="ali01"><input type="text" style="width:300px;" name="sysUnitInfo.homepage" value="<bean:write property="homepage" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">学校类型：</td>
			<td class="ali01">
				<java2code:option name="sysUnitInfo.schooltype" codetype="schooltype" property="schooltype" ckname="1"/>
			</td>
		</tr>
		<tr id="schooltype_1" <%if(!"1".equals(model.getSchooltype())){ %>style="display:none;"<%} %>>
			<td class="ali03">学校性质：</td>
			<td class="ali01">
				<input type="checkbox" name="schooltype1" value="1" id="schooltype1_1" <%if(model.getType().indexOf("1") != -1){ %>checked="checked"<%} %> disabled="disabled"/>幼教&nbsp;&nbsp;&nbsp;
              	<input type="checkbox" name="schooltype1" value="2" id="schooltype1_2" <%if(model.getType().indexOf("2") != -1){ %>checked="checked"<%} %> disabled="disabled"/>小学&nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="schooltype1" value="3" id="schooltype1_3" <%if(model.getType().indexOf("3") != -1){ %>checked="checked"<%} %> disabled="disabled"/>初中&nbsp;&nbsp;&nbsp;
				<input type="checkbox" name="schooltype1" value="4" id="schooltype1_4" <%if(model.getType().indexOf("4") != -1){ %>checked="checked"<%} %> disabled="disabled"/>高中
			</td>
		</tr>
		<tr id="schooltype_2" <%if(!"2".equals(model.getSchooltype())){ %>style="display:none;"<%} %>>
			<td class="ali03">学校性质：</td>
			<td class="ali01">
				<input type="checkbox" name="schooltype2" value="1" id="schooltype2_1" <%if(model.getType().indexOf("1") != -1){ %>checked="checked"<%} %> disabled="disabled"/>中职&nbsp;&nbsp;&nbsp;
              	<input type="checkbox" name="schooltype2" value="2" id="schooltype2_2" <%if(model.getType().indexOf("2") != -1){ %>checked="checked"<%} %> disabled="disabled"/>高职&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td class="ali03">联系人：</td>
			<td class="ali01"><input type="text" style="width:100px;" class="validate[required]" name="sysUnitInfo.linkman" value="<bean:write property="linkman" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">职务：</td>
			<td class="ali01"><input type="text" style="width:100px;" class="validate[required]" name="sysUnitInfo.job" value="<bean:write property="job" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">联系电话：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[telephone]" name="sysUnitInfo.telephone" value="<bean:write property="telephone" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">传真号码：</td>
			<td class="ali01"><input type="text" style="width:200px;" name="sysUnitInfo.fax" value="<bean:write property="fax" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">电子邮箱：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[email]" name="sysUnitInfo.email" value="<bean:write property="email" name="model"/>"/></td>
		</tr>
		
		<tr>
			<td class="ali03">邮政编码：</td>
			<td class="ali01"><input type="text" style="width:60px;" class="validate[postcode,length[4,6]]" name="sysUnitInfo.postcode" value="<bean:write property="postcode" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">所属地区：</td>
			<td class="ali01">
				<select name="sysUnitInfo.province" id="selectProvince" boxHeight="130" onchange="changeArea(this.value, '2', 'selectCity')">
					<option value="">请选择...</option>
					<%
					List provinceList = (List)request.getAttribute("provinceList");
					SysAreaInfo sai = null;
					for(int i=0, size=provinceList.size(); i<size; i++){
						sai = (SysAreaInfo)provinceList.get(i);
					%>
					<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(model.getProvince())){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
					<%} %>
				</select>
				<select name="sysUnitInfo.city" id="selectCity" boxHeight="130" onchange="changeArea(this.value, '2', 'selectCounty')">
					<option value="">请选择...</option>
					<%
					List cityList = (List)request.getAttribute("cityList");
					if(cityList != null && cityList.size() > 0){
					for(int i=0, size=cityList.size(); i<size; i++){
						sai = (SysAreaInfo)cityList.get(i);
					%>
					<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(model.getCity())){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
					<%}} %>
				</select>
				<select name="sysUnitInfo.county" id="selectCounty" boxHeight="130">
					<option value="">请选择...</option>
					<%
					List countyList = (List)request.getAttribute("countyList");
					if(countyList != null && countyList.size() > 0){
					for(int i=0, size=countyList.size(); i<size; i++){
						sai = (SysAreaInfo)countyList.get(i);
					%>
					<option value="<%=sai.getCitycode() %>" <%if(sai.getCitycode().equals(model.getCounty())){ %>selected="selected"<%} %>><%=sai.getAreaname() %></option>
					<%}} %>
				</select>
			</td>
		</tr>
		<tr>
			<td class="ali03">联系地址：</td>
			<td class="ali01"><input type="text" style="width:300px;" name="sysUnitInfo.address" value="<bean:write property="address" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">ICP备案号：</td>
			<td class="ali01"><input type="text" style="width:300px;" name="sysUnitInfo.icp" value="<bean:write property="icp" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">logo图片：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="logo" name="model"/>" title="点击修改照片" width="200" height="65" border="1" id=uploadImgShow1 onclick="uploadPhoto('1')"/><span class="star">建议图片大小:200*65</span>
              	<input type="hidden" id="uploadImg1" name="sysUnitInfo.logo" value='<bean:write property="logo" name="model"/>'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">缩略图：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改照片" width="250" height="140" border="1" id=uploadImgShow2 onclick="uploadPhoto('2')"/><span class="star">建议图片大小:240*140</span>
              	<input type="hidden" id="uploadImg2" name="sysUnitInfo.sketch" value='<bean:write property="sketch" name="model"/>'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">banner图片：</td>
			<td class="ali01">
				<img src="/upload/<bean:write property="banner" name="model"/>" title="点击修改照片" width="325" height="170" border="1" id=uploadImgShow3 onclick="uploadPhoto('3')"/><span class="star">建议图片大小:650*340</span>
              	<input type="hidden" id="uploadImg3" name="sysUnitInfo.banner" value='<bean:write property="banner" name="model"/>'/>
			</td>
		</tr>
		<tr>
			<td class="ali03">单位简介：</td>
			<td class="ali01">
				<textarea name="sysUnitInfo.descript" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getDescript())%></textarea>
			</td>
		</tr>
		<%-- 
		<tr>
			<td class="ali03">单位排序：</td>
			<td class="ali01"><bean:write property="orderindex" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">是否推荐：</td>
			<td class="ali01"><java2code:value codetype="boolean" property="recommand"/></td>
		</tr>
		<tr>
			<td class="ali03">推荐编号：</td>
			<td class="ali01"><bean:write property="recommandno" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">点击量：</td>
			<td class="ali01"><bean:write property="hits" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">点赞次数：</td>
			<td class="ali01"><bean:write property="praise" name="model"/></td>
		</tr>
		--%>
		<tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="sysUnitInfo.unitid" value="<bean:write property="unitid"  name="model"/>"/>
<input type="hidden" name="sysUnitInfo.parentno" value="<bean:write property="parentno"  name="model"/>"/>
<input type="hidden" name="sysUnitInfo.createdate" value="<bean:write property="createdate"  name="model"/>"/>
<input type="hidden" name="sysUnitInfo.updatetime" value="<bean:write property="updatetime"  name="model"/>"/>
<input type="hidden" name="sysUnitInfo.keywords" value="<bean:write property="keywords"  name="model"/>"/>
<input type="hidden" name="sysUnitInfo.hits" value="<bean:write property="hits" name="model"/>"/>
<input type="hidden" name="sysUnitInfo.praise" value="<bean:write property="praise" name="model"/>"/>
<input type="hidden" name="sysUnitInfo.domain" value="<bean:write property="domain" name="model"/>"/>
<input type="hidden" name="sysUnitInfo.status" value="<bean:write property="status" name="model"/>"/>
<input type="hidden" name="sysUnitInfo.orderindex" value="<bean:write property="orderindex" name="model"/>"/>
<input type="hidden" name="sysUnitInfo.recommand" value="<bean:write property="recommand" name="model"/>"/>
<input type="hidden" name="sysUnitInfo.recommandno" value="<bean:write property="recommandno" name="model"/>"/>
<input type="hidden" name="sysUnitInfo.type" value="<bean:write property="type" name="model"/>"/>
</html:form>
</div>
</div>
<script>
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="sysUnitInfo.descript"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}

function saveRecord(){
	if(validateForm('form[name=sysUnitInfoActionForm]')){
		var selectCounty = document.getElementById('selectCounty').value;
		if(selectCounty == ""){
			top.Dialog.alert("请选择所属地区!");
			return false;
		}
	
		//同步数据后可以直接取得textarea的value
		keditor.sync();
		var objectForm = document.sysUnitInfoActionForm;
	  	objectForm.action="sysUnitInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function uploadPhoto(id){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=unit&pathtype=ID';
	diag.Width = 350;
	diag.Height = 180;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
			var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
			document.getElementById('uploadImgShow'+id).src = "/upload/" + uploadimageurl
			document.getElementById('uploadImg'+id).value = uploadimageurl;
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