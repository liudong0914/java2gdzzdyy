<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo" %>
<%@page import="com.wkmk.edu.bo.EduCourseClass" %>
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

<script type="text/javascript" src="/libs/js/sk/iframe.js"></script>
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>

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
<!-- 进度条 -->
<script language="javaScript" type="text/javascript" src="/libs/js/msg.js"></script>
<link href="/libs/css/msg/msg.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
  function saveRecord(){
   var xls=document.getElementById("file");
   if(xls.value.lastIndexOf(".")!=-1){
     var ext=xls.value.substr((xls.value.lastIndexOf(".")+1)).toUpperCase();
     if(ext!='XLS'){
      top.Dialog.alert("只能上传xls的表格文件");
        return false;
     }else{
            //检测上传文件的大小        
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;  
    var fileSize = 0;           
    if (isIE && !xls.files){       
        var filePath = xls.value;       
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");          
        var file = fileSystem.GetFile (filePath);       
        fileSize = file.Size;      
    } else {      
        fileSize = xls.files[0].size;       
    }     


    var size = fileSize / 1024*1024;   


    if(size>(1024*1024*100)){    
        top.Dialog.alert("文件大小不能超过100Mb");   
        return false;
    }    
        
     
     }
   }
   
   	var bb = "";
	var temp = "";
	var a = document.getElementsByName("checkid");
	for ( var i = 0; i < a.length; i++) {
	if (a[i].checked) {
	temp = a[i].value;
	bb = bb +temp+ ",";
	}
	}
    document.getElementById("btnsave").disabled = true;
    Showbo.Msg.wait('正在导入，请等待...','提示信息');
    var objectForm = document.sysUserInfoActionForm;
    objectForm.action="sysUmsUserInfoAction.do?method=userImport&checkid="+bb;
    objectForm.submit();
  }
</script>
  </head>
  
  <body>
  <html:form action="/sysUmsUserInfoAction.do" method="post" enctype="multipart/form-data" >
  <table class="tableStyle" formMode="line" align="center">
  	<tr>
			<td>模板：</td>
			<td>
				<a href="/sys/ums/excel/template.xls" style="color:green;">模板.xls</a>&nbsp;&nbsp;&nbsp;&nbsp;(请先下载模板，按模板格式填写)
            </td>
		</tr>
   <tr>
    <td>上传用户</td>
    <td><input  type="file" name="file" id="file"  keepDefaultStyle="true" /></td>
   </tr>
   <tr>
    <td>VIP有效期</td>
    <td><input  type="text" name="viptime" id="viptime"  keepDefaultStyle="true"  class="validate[required,onlyNumber]" />(时间为天)</td>
   </tr>
     <tr>
      <td colspan="2" class="ali02">
      <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
       </td>
     </tr>
  </table>  
  
  <table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
    <tr>
      <th width="25"></th>
			<th class="ali02"><span onclick="sortHandler('title')" id="span_actor">批次名称</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('questiontype')">开始时间</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('difficult')">结束时间</span></th>
			<th class="ali02" width="100"><span onclick="sortHandler('status')">人数</span></th>
    </tr>
      <%
      List classList = (List)request.getAttribute("classList");
      if(classList != null && classList.size() > 0){
			    EduCourseClass ecc = null;
				for(int i=0, size=classList.size(); i<size; i++){
					ecc = (EduCourseClass)classList.get(i);
       %>
		<tr>
			<td><input type="checkbox" name="checkid" value="<%=ecc.getCourseid() %>_<%=ecc.getCourseclassid() %>" /></td>
			<td class="ali02"><%=ecc.getClassname() %></td>
			<td class="ali02"><%=ecc.getStartdate() %></td>
			<td class="ali02"><%=ecc.getEnddate() %></td>
            <td class="ali02"><%=ecc.getUsercount() %></td>
		</tr>
		<%}} %>
  </table>

  </html:form>
  </body>
</html>
