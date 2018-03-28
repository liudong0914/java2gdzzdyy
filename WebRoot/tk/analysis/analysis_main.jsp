<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<script type="text/javascript" src="/libs/js/sk/iframe.js"></script>
<script type="text/javascript">
$(function(){ 
   var tds=document.getElementsByTagName("td");
   tds[0].style.backgroundImage="url(../upload/button1.jpg)";
 })
 
 function jump(id,url){
  var ifarme=document.getElementById("subjectFrame");
  var tds=document.getElementsByTagName("td");
  for(var i=0;i<tds.length;i++){
   tds[i].style.backgroundImage="url(../upload/button2.jpg)";
  }
  tds[id].style.backgroundImage="url(../upload/button1.jpg)";
  ifarme.src=url;
 }
</script>
</head>

  <body>
   <div style="margin-left:16px;margin-top:6px;margin-bottom: 5px;">
	    <table>
	     <tr>
	        <td style="padding-left: 10px;padding-right: 10px;background-image: url('../upload/button2.jpg');"><a onclick="javascript:jump(0,'/tkPaperAnswerAction.do?method=getStudentsByClass&classid=${classid}&bookcontentid=${bookcontentid}&paperid=${paperid}');"  class="current" style="text-decoration: none;cursor: pointer;" >作业分析</a></td>
	         <td style="padding-left: 10px;padding-right: 10px;background-image: url('../upload/button2.jpg');"><a onclick="javascript:jump(1,'/tkPaperAnswerAction.do?method=getErrorStatistics&classid=${classid}&bookcontentid=${bookcontentid }&paperid=${paperid }')">错题统计</a></td>
	        <!--  <td style="padding-left: 10px;padding-right: 10px;background-image: url('../upload/button2.jpg');"><a onclick="javascript:jump(1,'/tkPaperAnswerAction.do?method=getPiechartStat&classid=${classid}&bookcontentid=${bookcontentid}&paperid=${paperid}')" >错题统计</a></td>
	        <td style="padding-left: 5px;padding-right: 5px;background-image: url('../upload/button2.jpg');"><a onclick="javascript:jump(2,'/tkPaperAnswerAction.do?method=getFirstPiechartStat&classid=${classid}&bookcontentid=${bookcontentid}&paperid=${paperid}')" >首次作答错题统计</a></td>-->
	     </tr>
	    </table>   
   </div>
   <div>
	   <div style="margin: 0;padding: 0;">
	      <IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=subjectFrame name=subjectFrame onload="SetCwinHeight('subjectFrame', 510)" src="/tkPaperAnswerAction.do?method=getStudentsByClass&classid=${classid}&bookcontentid=${bookcontentid}&paperid=${paperid}" allowTransparency="true"></IFRAME>
	   </div>
   </div>
  </body>
</html>
