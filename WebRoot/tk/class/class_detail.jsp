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
  
  <body>
    <div class="box1" position=center>
    <table id="testTab" class="tableStyle" formMode="line" align="center">
     <tr>
     <th colspan="3">班级详情</th>
     </tr>
     <tr>
     <td class="ali03" width="160">班级名称：</td>
     <td class="ali01">${cla.classname}</td>
     </tr>
     <tr>
     <td class="ali03">班级编号：</td>
     <td class="ali01">${cla.classno}</td>
     </tr>
     <tr>
      <td class="ali03">学员数量：</td>
      <td class="ali01">${cla.students}</td>
     </tr>
     <tr>
      <td class="ali03">是否有密码：</td>
      <td class="ali01">
      ${cla.pwd eq 1 ?"有":"没有" }
      </td>
     </tr>
     <tr>
     <td class="ali03">二维码图片：</td>
     <td class="ali01"><img src="/upload/${cla.twocodepath}" alt="/upload/${cla.twocodepath}"/></td>
     </tr>
     <tr>
      <td colspan="2"><button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button></td>
     </tr>
     </table>
    </div>
  </body>
</html>
