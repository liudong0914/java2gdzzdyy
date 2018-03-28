<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
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

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
  </head>
   <script type="text/javascript">
  function previewPic(pid,url){
  var diag=new top.Dialog();
  diag.Tilte="图片展示";
  diag.URL='/tkQuestionsInfoAction.do?method=picdetail&url='+url;
  diag.Width=600;
  diag.Height=400;
  diag.CancelEvent=function(){
    diag.close();
  };
    diag.show();
 }	
  </script>
  <body>
    <div class="box1" position=center>
    <table id="testTab" class="tableStyle" formMode="line" align="center">
     <tr>
     <th colspan="3">题目详情</th>
     </tr>
     <tr>
     <td class="ali03" width="160">标题</td>
     <td class="ali01">${model.title }</td>
     </tr>
     <tr>
     <td class="ali03">试题内容：</td>
     <td class="ali01">${model.titlecontent }</td>
     </tr>
     <tr>
      <td class="ali03">正确答案：</td>
      <td class="ali01">${model.rightans }</td>
     </tr>
     <tr>
      <td class="ali03">是否有固定标准答案：</td>
      <td class="ali01">
      <logic:equal value="1" name="model" property="isrightans">是</logic:equal>
      <logic:equal value="0" name="model" property="isrightans">否</logic:equal>
      </td>
     </tr>
     <tr>
     <td class="ali03">难易程度：</td>
     <td class="ali01">${difficultName }</td>
     </tr>
      <tr>
      <td class="ali03">参考分值：</td>
      <td class="ali01">${model.score }</td>
     </tr> 
     <tr>
      <td class="ali03">状态：</td>
      <td class="ali01">${statusName }</td>
     </tr>
     <tr>
      <td class="ali03">知识点：</td>
      <td class="akl01">${knopointnames }</td>
     </tr>
     <tr>
       <td class="ali03">地区：</td>
       <td class="ali01">${model.area }</td>
     </tr>
     <tr>
       <td class="ali03">标签 ：</td>
       <td class="ali01">${model.tag }</td>
     </tr>
      <tr>
      <td class="ali03">答案解析：</td>
      <td class="ali01">${model.descript }</td>
     </tr>
     <tr>
      <td class="ali03">年份：</td>
      <td class="ali01">${model.theyear }</td>
     </tr>
     <tr>
       <td class="ali03">创建人：</td>
       <td class="ali01">${model.authorname }</td>
     </tr>
     <tr>
      <td class="ali03">创建时间：</td>
      <td class="ali01"">${model.cretatdate }</td>
     </tr>
     <tr>
       <td class="ali03">修改时间：</td>
       <td class="ali01">${model.updatetime }</td>
     </tr>
     <tr>
      <td colspan="2"><button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            </td>
     </tr>
     </table>
            <input type="hidden" name="questionno" value="${questionno }"/>
       <input type="hidden" name="title" value="${title }"/>
       <input type="hidden" name="difficult" value="${difficult }"/>
    </div>
  </body>
</html>
