<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@ page import="com.wkmk.tk.bo.TkQuestionsInfo" %>
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

  </head>
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
     document.getElementById("btnsave").disabled = true;
     objectForm.action="tkPaperCartAction.do?method=addSavePaperInfo";
     objectForm.submit();
   }
 }
 function closes(){
  window.close();
 }
</script>
  <body>
  <html:form action="/tkPaperInfoAction.do" method="post" >
 <table class="tableStyle"  formMode="line" align="center">
   <tr>
   <th style="color: #ff6600;font-size: 16pt;" colspan="2">保存试卷</th>
   </tr>
    <tr>
      <td class="ali03">试卷名称：</td>
      <td class="ali01"><input type="text" name="tkPaperInfo.title" class="validate[required]" style="width: 670px;"/></td>
    </tr>
    <tr>
     <td class="ali03">试卷描述：</td>
     <td class="ali01">
    <textarea name="tkPaperInfo.descript"style="width:670px;height:230px;visibility:hidden;"></textarea>
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
       <select id="grade" name="tkPaperInfo.gradeid"  boxHeight="130"  onchange="changeGrade();">
        <c:forEach items="${gradeinfos }" var="g" >
        <option value="${g.gradeid }">${g.gradename }</option>
        </c:forEach>
       </select>
      </td>
      </tr> 
     <tr>
   <td colspan="2">
    <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
   <button type="button" onclick="closes();"><span class="icon_back">关闭</span></button>            
   </td>
   </tr>
   </table>
  <input type="hidden" name="tkPaperInfo.userid" value="${model.userid }"/>
  <input type="hidden" name="tkPaperInfo.unitid" value="${model.unitid }"/>
  <input type="hidden" name="tkPaperInfo.subjectid" value="${model.subjectid }"/>
  <input type="hidden" name="tkPaperInfo.papertype" value="${model.papertype }"/>
  <input type="hidden" name="xueduanid" value="${xueduanid }"/>
</html:form>
  </body>
</html>