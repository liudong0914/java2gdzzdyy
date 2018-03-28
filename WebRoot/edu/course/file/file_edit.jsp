<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseFile"%>
<%@page import="com.wkmk.edu.bo.EduCourseFileColumn"%>
<%@page import="com.wkmk.util.common.Constants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<!--框架必需start-->
<script type="text/javascript" src="/libs/js/framework.js"></script>
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

<body style="background:#fcfcfc;">
<html:form action="/eduCourseFileAction.do?method=addSave" method="post" enctype="multipart/form-data">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
		<%
		EduCourseFile file = (EduCourseFile)request.getAttribute("model");
        List filecolumnList = (List)request.getAttribute("filecolumnList");
          %>
		<div class="form-row form-row01">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				资源类型
				</label>
			</div>
			<div id="selectBox" class="input jobWrap">
				 <select name="eduCourseFile.coursefiletype" keepDefaultStyle="true" class="ipt-text" style="width:120px;height:34px;">
              	<%
              	EduCourseFileColumn ecfc = null;
					for(int i=0, size=filecolumnList.size(); i<size; i++){
						ecfc = (EduCourseFileColumn)filecolumnList.get(i);
					%>
              	<option value="<%=ecfc.getFilecolumnid() %>" <%if(ecfc.getFilecolumnid().equals(file.getCoursefiletype())){ %>selected="selected"<%} %>><%=ecfc.getTitle() %></option>
              	<%} %>
              </select>
			</div>
		</div>
		
		<logic:equal value="addSave" name="act"> 
		<div class="form-row">
				<div class="label">
					<label>
					<i class="text-red must">*</i>
					文件名称
					</label>
				</div>
				<div class="input">
					<input keepDefaultStyle="true" class="ipt-text formEleToVali" name="fname" id="filename2" value="" style="width:300px;" type="text">
				</div>
		</div>
				
		<div class="form-row">
			<div class="label">
				<label>
				<i class="text-red must">*</i>
				资源上传
				</label>
			</div>
			<div class="input">
				<input type="file" name="file" id="file"   onchange="fileChange(this);" keepDefaultStyle="true"/>
			</div>
		</div>
		</logic:equal>
		
		<logic:equal value="updateSave" name="act">
			<div class="form-row">
				<div class="label">
					<label>
					<i class="text-red must">*</i>
					文件名称
					</label>
				</div>
				<div class="input">
					<input id="eduCourseFile.filename" keepDefaultStyle="true" class="validate[required] ipt-text formEleToVali" name="eduCourseFile.filename" value="${model.filename }" style="width:300px;" type="text">
				</div>
			</div>
			<%-- 
			<div class="form-row">
				<div class="label">
					<label>
					<i class="text-red must">*</i>
					文件类型
					</label>
				</div>
				<div class="input">
					<select name="eduCourseFile.filetype" keepDefaultStyle="true" class="ipt-text" style="width:120px;height:34px;">
	              		<option value="1" <%if("1".equals(file.getFiletype())){ %>selected="selected"<%} %>>文档</option>
	              		<option value="2" <%if("2".equals(file.getFiletype())){ %>selected="selected"<%} %>>图片</option>
	              		<option value="3" <%if("3".equals(file.getFiletype())){ %>selected="selected"<%} %>>音频</option>
	              		<option value="4" <%if("4".equals(file.getFiletype())){ %>selected="selected"<%} %>>视频</option>
	              	</select>
				</div>
			</div>	
			--%>
			<input type="hidden" name="eduCourseFile.filetype" value="<bean:write property="filetype" name="model"/>"/>
		</logic:equal>
		
		
		
	<div class="clearfix mt20" style="text-align:center;">
		<%
		String isAuhtor = (String)session.getAttribute("isAuhtor");
		Map moduleidMap = (Map)session.getAttribute("moduleidMap");
		String moduleidType = (String)moduleidMap.get("4");
		if("1".equals(isAuhtor) || "2".equals(moduleidType)){
		%>
		<input class="btn btn-pop ml20" value="保存" onclick="saveRecord()" id="btnsave" style="display:inline-block;" type="button">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<%} %>
		<input class="btn btn-pop ml20" value="返回" onclick="goBack()" type="button">
	</div>
	</div>
</div>
<input type="hidden" name="eduCourseFile.fileid" value="<bean:write property="fileid" name="model"/>"/>
<input type="hidden" name="eduCourseFile.filepath"  value="<bean:write property="filepath" name="model"/>"/>
<input type="hidden" name="eduCourseFile.pdfpath" value="<bean:write property="pdfpath" name="model"/>"/>
<input type="hidden" name="eduCourseFile.pagenum"  value="<bean:write property="pagenum" name="model"/>"/>
<input type="hidden" name="eduCourseFile.sketch"  value="<bean:write property="sketch" name="model"/>"/>
<input type="hidden" name="eduCourseFile.filesize" value="<bean:write property="filesize" name="model"/>"/>	
<input type="hidden" name="eduCourseFile.fileext" value="<bean:write property="fileext" name="model"/>"/>	
<input type="hidden" name="eduCourseFile.createdate" value="<bean:write property="createdate" name="model"/>"/>	
<input type="hidden" name="eduCourseFile.convertstatus" value="<bean:write property="convertstatus" name="model"/>"/>	
<input type="hidden" name="eduCourseFile.hits" value="<bean:write property="hits" name="model"/>"/>	
<input type="hidden" name="eduCourseFile.downloads" value="<bean:write property="downloads" name="model"/>"/>	
<input type="hidden" name="eduCourseFile.coursecolumnid" value="<bean:write property="coursecolumnid" name="model"/>"/>	
<input type="hidden" name="eduCourseFile.courseid" value="<bean:write property="courseid" name="model"/>"/>	
<input type="hidden" name="eduCourseFile.userid" value="<bean:write property="userid" name="model"/>"/>	
		
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
<input type="hidden" id="act" value="<bean:write name="act"/>"/>
</html:form>

</body>
<script>
function doUpload(){
	document.getElementById("docfileview").style.display = "none";
	document.getElementById("docfileedit").style.display = "";
}
var AllImgExt=".doc|.docx|.ppt|.pptx|.xls|.xlsx|.pdf|.txt|"//全部图片格式类型
function fileChange(target) {
    var fileSize = 0;         
    if (isIE && !target.files) {     
      var filePath = target.value;     
      var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
      var file = fileSystem.GetFile (filePath);     
      fileSize = file.Size;    
    } else {    
     fileSize = target.files[0].size;     
     }   
     var size = fileSize / 1024;    
     if(size>50000){  
      alert("附件不能大于500M");
      target.value="";
      return
     }
     var name=target.value;
     var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
     var fileName2 = name.substring(name.lastIndexOf("\\")+1,name.lastIndexOf(".")).toLowerCase();
     document.getElementById("filename2").value=fileName2;
     if(!checkFileExt(fileName)){
     	alert("请选择"+AllImgExt+"格式文件上传！");
         target.value="";
         return
    }
} 
//检测文件类型
function checkFileExt(ext){
    if (AllImgExt!=0&&AllImgExt.indexOf(ext+"|")==-1) {
        return false;
    }
    return true;
}
function saveRecord(){
		var objectForm = document.eduCourseFileActionForm;
		var act = document.getElementById("act").value;
		if(act == 'addSave'){
			var file = document.getElementById("file").value;
			if("" == file){
				alert("请选择需要上传的资源文件！");
				return false;
			}
		}
		document.getElementById("btnsave").disabled = true;
	  	objectForm.action="eduCourseFileAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
}
function goBack(){
	var objectForm = document.eduCourseFileActionForm;
  	objectForm.action="/eduCourseFileAction.do?method=list";
  	objectForm.submit();
}
</script>
</html>
