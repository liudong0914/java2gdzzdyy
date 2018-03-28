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
<script type="text/javascript">
	function xianshi(imgpath){
		$("#image").attr("src",imgpath);
		document.getElementById("artwork").style.display="block";
	}
	function guanbi(){
		document.getElementById("artwork").style.display="none";
	}
</script>
  </head>
  <body>
  <div style="position: absolute;display:none;" id="artwork">
  <a onclick="javascript:guanbi();" style="float:right;">关闭</a>
  <img id="image" style="float:right;" alt="原图"></img>
  </div>
    <div class="box1" position=center>
    <table id="testTab" class="tableStyle" formMode="line" align="center">
     <tr>
     <th colspan="3">作业完成情况</th>
     </tr>
     <tr>
     <td class="ali03" width="160">班级名称：</td>
     <td class="ali01">${detail.classname}</td>
     </tr>
     <tr>
     <td class="ali03">学员姓名：</td>
     <td class="ali01">${detail.username}</td>
     </tr>
     <tr>
      <td class="ali03">作业标题：</td>
      <td class="ali01">${detail.title}</td>
     </tr>
     <tr>
      <td class="ali03">完成情况：</td>
      <td class="ali01">
      <div >
      		<c:forEach items="${detail.images}" var="d">
      		<img src="/upload/${d}" onclick="xianshi('/upload/${d}')" style="margin:2px;width:130px;height:150px;float:left;" alt="缩略图"/>
      		</c:forEach>
      </div>
      </td>
     </tr>
     <tr>
      <td colspan="2"><button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button></td>
     </tr>
     </table>
    </div>
  </body>
</html>
