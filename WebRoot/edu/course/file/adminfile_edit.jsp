<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseFile"%>
<%@page import="com.wkmk.edu.bo.EduCourseFileColumn"%>
<%@page import="com.wkmk.util.common.Constants"%>
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
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
<!--弹窗end-->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/eduCourseFileAction.do?method=addSave" method="post" enctype="multipart/form-data">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="adminAddSave" name="act">新增资源</logic:equal><logic:equal value="adminUpdateSave" name="act">修改资源</logic:equal></th>
		</tr>
		<%
		EduCourseFile file = (EduCourseFile)request.getAttribute("model");
           List filecolumnList = (List)request.getAttribute("filecolumnList");
          %>
			<td class="ali03">资源类型：</td>
			<td>
				<select name="eduCourseFile.coursefiletype">
              	<%
              	EduCourseFileColumn ecfc = null;
					for(int i=0, size=filecolumnList.size(); i<size; i++){
						ecfc = (EduCourseFileColumn)filecolumnList.get(i);
              	%>
              	<option value="<%=ecfc.getFilecolumnid() %>" <%if(ecfc.getFilecolumnid().equals(file.getCoursefiletype())){ %>selected="selected"<%} %>><%=ecfc.getTitle() %></option>
              	<%} %>
              </select>
			</td>
		</tr>
		<logic:equal value="adminAddSave" name="act"> 
		<tr>
				<td class="ali03">文件名称：</td>
				<td class="ali01" id="filename2"></td>
		</tr>	
		<tr>
			<td class="ali03">资源上传：</td>
			<td class="ali01">
				<input type="file" name="file" id="file" onchange="fileChange(this);" keepDefaultStyle="true"/>			
			</td>
		</tr>
		</logic:equal>
		<logic:equal value="adminUpdateSave" name="act">
		<tr>
				<td class="ali03">文件名称：</td>
				<td class="ali01"><input type="text" style="width: 300px;" class="validate[required]" name="eduCourseFile.filename" value="<bean:write property="filename" name="model"/>"/></td>
			<input type="hidden" name="eduCourseFile.filetype" value="<bean:write property="filetype" name="model"/>"/>
		</tr>
		</logic:equal>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="goBack()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
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
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
<input type="hidden" name="courseid" value="<bean:write name="courseid"/>"/>
<input type="hidden" name="columnid" value="<bean:write name="columnid"/>"/>
<input type="hidden" id="act" value="<bean:write name="act"/>"/>
</html:form>
</div>
</div>
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
     document.getElementById("filename2").innerHTML=fileName2;
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
		if(act == 'adminAddSave'){
			var file = document.getElementById("file").value;
			if("" == file){
				alert("请选择需要上传的资源文件！");
				return false;
			}
		}
	  	objectForm.action="eduCourseFileAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
}
function goBack(){
	var objectForm = document.eduCourseFileActionForm;
  	objectForm.action="/eduCourseFileAction.do?method=adminList";
  	objectForm.submit();
}
</script>
</body>
</html>