<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
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
<script type="text/javascript">
  function saveRecord(){
   var xls=document.getElementById("thefile");
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
    document.getElementById("btnsave").disabled = true;
    var objectForm = document.sysUserInfoActionForm;
    objectForm.action="sysUmsUnitUserInfoAction.do?method=schoolImport";
    objectForm.submit();
  }
</script>
  </head>
  
  <body>
  <html:form action="/sysUmsUnitUserInfoAction.do" method="post" enctype="multipart/form-data" >
  <table class="tableStyle" formMode="line" align="center">
   <tr>
    <td>上传学校</td>
    <td><input id="thefile" type="file" name="thefile" keepDefaultStyle="true" /></td>
   </tr>
     <tr>
      <td colspan="2" class="ali02">
      <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
       </td>
     </tr>
  </table>
  </html:form>
  </body>
</html>
