<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.cms.bo.CmsNewsInfo"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
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
     <td class="ali03" width="160">标题</td>
     <td class="ali01">${model.title }</td>
     </tr>
     <tr>
     <td class="ali03">作者：</td>
     <td class="ali01">${model.author }</td>
     </tr>
     <tr>
      <td class="ali03">创建时间：</td>
      <td class="ali01">${model.createdate }</td>
     </tr>
     <tr>
      <td class="ali03">发布时间：</td>
      <td class="ali01">${model.happendate }</td>
     </tr>
     <%-- 
     <tr>
      <td class="ali03">资讯链接：</td>
      <td class="ali01">${model.linkurl }</td>
     </tr>
     <tr>
     <td class="ali03">关键字：</td>
     <td class="ali01">${model.status }</td>
     </tr>
      <tr>
      <td class="ali03">缩略图：</td>
      <td class="ali01"><img src="/upload/<bean:write property="sketch" name="model"/>"</td>
     </tr> 
     --%>
     <tr>
      <td class="ali03">资讯内容：</td>
      <td class="ali01" colspan="4">${model.htmlcontent }</td>
     </tr>
     <tr>
      <td colspan="2"><button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            </td>
     </tr>
     </table>
            <input type="hidden" name="newsid" value="${newsid }"/>
    </div>
  </body>
</html>
