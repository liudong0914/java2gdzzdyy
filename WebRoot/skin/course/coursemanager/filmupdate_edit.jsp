<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmInfo" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<!--框架必需start-->
<script type="text/javascript" src="/libs/js/framework.js"></script>
<link href="/libs/css/framework/form.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="/libs/js/form/validation.js" type="text/javascript"></script>
<script src="/libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<!--弹窗end-->
<link href="/skin/course/css/style.css" rel="stylesheet"/>
<%
VwhFilmInfo filminfo = (VwhFilmInfo)request.getAttribute("filminfo");
%>
</head>

<body style="background:#fcfcfc;">
<html:form action="/eduCourseFilmAction.do?method=updateSave" method="post">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
		<div class="form-row form-row01">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				微课标题：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="eduCourseFilm.title" value="${model.title }" style="width:306px;" type="text">
			</div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				缩略图：
				</label>
			</div>
			<div class="input">
				<div class="fl" width="550">
					<img src="/upload/${filminfo.sketch }" title="点击修改图片" keepDefaultStyle="true" style="cursor:pointer;" width="220" height="112" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
		            <input type="hidden" id="uploadImg" name="vwhFilmInfo.sketch" value='${filminfo.sketch }'/>
				</div>
			</div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				二维码编号：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" id="qrcodeno" name="eduCourseFilm.qrcodeno" value="${model.qrcodeno }" style="width:150px;" type="text">
		    </div>
		</div>
		<div class="form-row form-row01">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				标价：
				</label>
			</div>
			<div class="input">
				<input id="price" keepDefaultStyle="true" class="validate[required,onlyNumber] ipt-text ipt-text01 formEleToVali" name="eduCourseFilm.price" value="<bean:write property="price" name="model"/>" type="text" onchange="showSellprice()">
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				折扣：
				</label>
			</div>
			<div class="input">
				<input id="discount" keepDefaultStyle="true" class="validate[required,onlyNumber] ipt-text ipt-text01 formEleToVali" name="eduCourseFilm.discount" value="<bean:write property="discount" name="model"/>" type="text" onchange="showSellprice()">折扣最高不能超过10！
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				售价：
				</label>
			</div>
			<div class="input">
				<input id="sellprice" keepDefaultStyle="true" class="validate[required,onlyNumber] ipt-text ipt-text01 formEleToVali" name="eduCourseFilm.sellprice" value="<bean:write property="sellprice" name="model"/>" type="text" readonly="readonly">
			</div>
		</div>
		
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				序号：
				</label>
			</div>
			<div class="input">
				<input keepDefaultStyle="true" class="validate[required,custom[onlyNumber],length[1,4]] ipt-text formEleToVali" name="eduCourseFilm.orderindex" value="${model.orderindex }" style="width:150px;" type="text">按序号顺序排列
		    </div>
		</div>
		<div class="form-row">
			<div class="label">
				<label>
				状态：
				</label>
			</div>
			<div class="input">
				<java2code:option name="eduCourseFilm.status" codetype="status4" property="status" ckname="1"></java2code:option>
			</div>
		</div>
		
	<div class="clearfix mt20" style="text-align:center;">
		<logic:notEqual value="1" name="eduCourseInfo" property="status">
		<%
		String isAuhtor = (String)session.getAttribute("isAuhtor");
		Map moduleidMap = (Map)session.getAttribute("moduleidMap");
		String moduleidType = (String)moduleidMap.get("3");
		if("1".equals(isAuhtor) || "2".equals(moduleidType)){
		%>
		<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" style="display:inline-block;" type="button">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<%} %>
		</logic:notEqual>
		<input class="btn btn-pop ml20" value="返回" onclick="goBack()" type="button">
	</div>
	</div>
</div>
<input type="hidden" name="eduCourseFilm.coursefilmid" value="<bean:write property="coursefilmid" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.filmid" value="<bean:write property="filmid" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.coursecolumnid"  value="<bean:write property="coursecolumnid" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.courseid" value="<bean:write property="courseid" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.orderindex"  value="<bean:write property="orderindex" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.hits"  value="<bean:write property="hits" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.sellcount" value="<bean:write property="sellcount" name="model"/>"/>
<input type="hidden" name="eduCourseFilm.createdate" value="<bean:write property="createdate" name="model"/>"/>

<input type="hidden" name="vwhFilmInfo.filmid" value="<bean:write property="filmid" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.title" value="<bean:write property="title" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.sketchimg" value="<bean:write property="sketchimg" name="filminfo"/>"/>
<input type="hidden" name="VwhFilmInfo.orderindex"  value="<bean:write property="orderindex" name="filminfo"/>"/>
<input type="hidden" name="VwhFilmInfo.hits" value="<bean:write property="hits" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.status"  value="<bean:write property="status" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.createdate"  value="<bean:write property="createdate" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.updatetime" value="<bean:write property="updatetime" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.computerid" value="<bean:write property="computerid" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.unitid" value="<bean:write property="unitid" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.twocodepath" value="<bean:write property="twocodepath" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.actor" value="<bean:write property="actor" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.keywords" value="<bean:write property="keywords" name="filminfo"/>"/>
<input type="hidden" name="vwhFilmInfo.descript" value="<bean:write property="descript" name="filminfo"/>"/>
		
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
	<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
</html:form>

</body>
<script>
var keditor;
function saveRecord(){
	if(validateForm('form[name=eduCourseFilmActionForm]')){
		var qrcodeno0 = '${model.qrcodeno }';
		var qrcodeno = document.getElementById("qrcodeno").value;
		if(qrcodeno0 != qrcodeno){
			var url = '/eduCourseFilmAction.do?method=checkQrcodeno&qrcodeno='+qrcodeno+'&ram=' + Math.random();
			$.ajax({
			  type: 'post',
			  url: url,
			  async: false,//同步请求
			  dataType:'text',
			  success: function(data){
			  	temp = data;
			  }
			});
		  	if(temp == '1'){
		  		top.Dialog.alert("二维码编号已存在，请更换编号再试!");
		  		return false;
		  	}
		}
	  	
		var objectForm = document.eduCourseFilmActionForm;
	  	objectForm.action="/eduCourseFilmAction.do?method=${act }";
	  	objectForm.submit();
	}
}
function goBack(){
	var objectForm = document.eduCourseFilmActionForm;
  	objectForm.action="/eduCourseFilmAction.do?method=filmList";
  	objectForm.submit();
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
function showSellprice(){
	var price = document.getElementById("price").value;
	var discount = document.getElementById("discount").value;
	var sellprice = "";
	if(discount <= 10){
		sellprice = ((price*discount)/10).toFixed(2);
		document.getElementById("sellprice").value =sellprice;
	}else{
		alert("折扣不能大于10！")
	}
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