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
<script type="text/javascript">
 var keditor;
function initComplete(){
 KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="tkPaperInfo.descript"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		    afterBlur: function(){this.sync();}
		});
	});	
}
function saveRecord(){
  if(validateForm('form[name=tkPaperInfoActionForm]')){
     var objectForm=document.tkPaperInfoActionForm;
      keditor.sync();
      keditor.sync();
     document.getElementById("btnsave").disabled = true;
     objectForm.action="tkPaperInfoAction.do?method=updateSave";
     objectForm.submit();
  }

}
</script>
  </head>
  <body>
  <html:form action="/tkPaperInfoAction.do" method="post">
   <table class="tableStyle"  formMode="line" align="center">
   <tr>
   <th style="color: #ff6600;font-size: 16pt;" colspan="2">修改试卷</th>
   </tr>
    <tr>
      <td class="ali03">试卷名称：</td>
      <td class="ali01"><input type="text" name="tkPaperInfo.title" class="validate[required]" value="${model.title }" /></td>
    </tr>
    <tr>
     <td class="ali03">试卷描述：</td>
     <td class="ali01">
    <textarea name="tkPaperInfo.descript"style="width:670px;height:230px;visibility:hidden;">${model.descript }</textarea>
     </td>
    </tr>
     <tr>
       <td class="ali03">状态：</td>
       <td class="ali01">
       <java2code:option name="tkPaperInfo.status" codetype="status4" valuename="model" property="status"></java2code:option>
       </td>
       </tr>
      <tr>
      <td class="ali03">年级：</td>
      <td class="ali01">
       ${model.gradeName }
      </td>
      </tr> 
     <tr>
   <td colspan="2">
    <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
   <button type="button" onclick="javascript:history.go(-1)"><span class="icon_back">返回</span></button>            
   </td>
   </tr>
   </table>
    <input type="hidden" name="title" value="${title }"/>
    <input type="hidden" name="subjectid" value="${subjectid }"/>
    <input type="hidden" name="tkPaperInfo.gradeid" value="${model.gradeid }"/>
    <input type="hidden" name="tkPaperInfo.paperid" value="${model.paperid }"/>
    <input type="hidden" name="tkPaperInfo.createdate" value="${model.createdate }" />
    <input type="hidden" name="tkPaperInfo.userid" value="${model.userid }"/>
   <input type="hidden" name="tkPaperInfo.unitid" value="${model.unitid }"/>
   <input type="hidden" name="tkPaperInfo.subjectid" value="${model.subjectid }"/>
   <input type="hidden" name="tkPaperInfo.papertype" value="${model.papertype }"/>
   <!-- 分页与排序 -->
 <input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
 <input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
 <input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
  </html:form>   
   </div>
  </body>
</html>
