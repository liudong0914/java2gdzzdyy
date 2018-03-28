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
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->


<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->
<script type="text/javascript">
  function saveRecord(){
   if(validateForm('form[name=tkPaperCartActionForm]')){
      var score=document.getElementById("score").value;
      if(score>100){
       top.Dialog.alert("分数不能超过100分！");
       return false;
      }
      var objectForm=document.tkPaperCartActionForm;
      objectForm.action="tkPaperCartAction.do?method=updateScore&userid=${userid}&subjectid=${subjectid}&xueduanid=${xueduanid}&questionid=${questionid}&score="+score;
      objectForm.submit();
  }
  }

 function saveRecord2(){
     if(validateForm('form[name=tkPaperCartActionForm]')){
      var score=document.getElementById("score").value;
      if(score>100){
       top.Dialog.alert("分数不能超过100分！");
       return false;
      }
      var id="${questionid}"+"score";
      $.post("tkPaperCartAction.do?method=updateScore&userid=${userid}&subjectid=${subjectid}&xueduanid=${xueduanid}&questionid=${questionid}&score="+score);
   
     /*  if (navigator.userAgent.indexOf('MSIE') >= 0 || navigator.userAgent.indexOf('rv:11') >= 0){
      window.dialogArguments.document.getElementById(id).innerHTML="(分值："+parseFloat(score).toFixed(1)+")";
      window.dialogArguments.parent.rfrmleft.location.reload();
     }else{
       window.opener.document.getElementById(id).innerHTML="(分值："+parseFloat(score).toFixed(1)+")";
         window.opener.parent.rfrmleft.location.reload();
     }
     window.close(); */
  }
 }
 function closes(){
   window.close();
 }
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  </head>
  <body>
    <html:form action="/tkPaperCartAction.do" method="post">
    <table class="tableStyle"  formMode="line" align="center">
   <tr >
   <th style="color: #ff6600;font-size: 16pt;" colspan="2">试题分值</th>
   </tr>
   <td class="ali03">主标题:</td>
   <td class="ali01"><input type="text" id="score" name="score" value="${score }" class="validate[custom[onlyNumberWide],required]"/>(必填)</td>
   <tr>
   <td colspan="2">
    <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
   <button type="button" onclick="closes();"><span class="icon_back">关闭</span></button>            
   </td>
   </tr>
   </table>
   <input type="hidden" name="userid" value="${userid }"/>
   <input type="hidden" name="subjectid" value="${subjectid }" />
   <input type="hidden" name="xueduanid" value="${xueduanid }" />
   <input type="hidden" name="questionid" value="${questionid }" />
   </html:form>
  </body>
</html>
