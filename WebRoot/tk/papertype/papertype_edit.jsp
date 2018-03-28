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
<!-- 编辑器end -->

<!-- 上传控件 -->
<link href="/swfupload/css/index.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/swfupload/js/swfupload/swfupload.js"></script>
<script type="text/javascript" src="/swfupload/js/swfupload/handlers_film.js"></script>
<script type="text/javascript">

 function saveRecord(){
   if(validateForm('form[name=tkPaperTypeActionForm]')){
     var objectForm = document.tkPaperTypeActionForm;
     var typeno=document.getElementById("typeno").value;
   //同步数据后可以直接取得textarea的value
		if(isNaN(typeno)){
		   top.Dialog.alert("试卷分类编号必须为数字!");
		   return false;
		}
		if(typeno.length>6){
		   top.Dialog.alert("试卷分类编号长度不能超过6个字符!");
		   return false;
		}
		if(document.getElementById("typename").value.length>15){
		    top.Dialog.alert("题库类型名称长度不能超过15个字符!");
		    return false;
		}
	  	objectForm.action="tkPaperTypeAction.do?method=${act}";
	  	objectForm.submit();
   }
 }
</script>
  </head>
  <body>
  <div>
   <div class="box1" position="center">
     <html:form action="/tkPaperTypeAction.do" method="post">
     <input type="hidden" name="tkPaperType.typeid" value="${model.typeid}"/>
      <table class="tableStyle" formMode="line" align="center">
       <tr>
        <td class="ali03">试卷分类编号：</td>
        <td class="ali01"><input type="text" id="typeno" class="validate[required]" style="width: 200px;" name="tkPaperType.typeno" value="${model.typeno }" /><span class="star">*</span></td>
       </tr>
       <tr>
        <td class="ali03">试卷分类名称：</td>
        <td class="ali01"><input type="text" id="typename" class="validate[required]" style="width: 400px;" name="tkPaperType.typename" value="${model.typename }"/><span class="star">*</span> </td>
       </tr>
       <tr>
        <td class="ali03">试卷分类状态：</td>
        <td class="ali01"><java2code:option name="tkPaperType.status" codetype="status2" valuename="model" property="status"></java2code:option></td>
       </tr>
       <tr>
        <td colspan="2">
        	<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
             <button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            
        </td>
       </tr>
      </table>
      <!-- 分页与排序 -->
     </html:form>
   </div>  
  </div>
  </body>
</html>
