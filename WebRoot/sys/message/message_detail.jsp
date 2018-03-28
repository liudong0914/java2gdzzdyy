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
  </head>
  <body>
     <div class="box1" position=center>
       <table class="tableStyle" formMode="line" align="center">
         <tr>
            <th colspan="2">消息详情</th>
         </tr>
         <tr>
          <td class="ali03" width="120">消息标题：</td>
          <td class="ali01">${model.title}</td>
         </tr>
         <tr>
          <td class="ali03">消息内容：</td>
          <td class="ali01"><div style="width:400px;height:100px;">${model.content}</div></td>
         </tr>
       <tr>
          <td class="ali03">发送时间：</td>
          <td class="ali01">${model.createdate}</td>
         </tr>
         <tr>
	          <td colspan="2">
		          <button type="button" onclick="saveRecord()" class="margin_right5"><span class="icon_save">发送</span></button>
		          <button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            
	          </td>
         </tr>
       </table>
     </div>
  </body>
</html>
