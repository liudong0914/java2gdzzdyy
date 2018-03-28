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
   tds[0].style.backgroundImage="url(/upload/button1.jpg)";
 })
 
 function jump(id){
  var ifarme=document.getElementById("subjectFrame");
  var tds=document.getElementsByTagName("td");
  for(var i=0;i<tds.length;i++){
   tds[i].style.backgroundImage="url(/upload/button2.jpg)";
  }
  document.getElementById(id).style.backgroundImage="url(/upload/button1.jpg)";
  ifarme.src="/tkQuestionsInfoAction.do?method=list&subjectid=${subjectid }&questiontypeid="+id;
 }
</script>
</head>

  <body>
   <div>
    <table>
     <tr>
       <c:forEach items="${questiontypes}" var="q">
        <td id="${q.typeid }" style="padding-left: 20px;padding-right: 20px;background-image: url('/upload/button2.jpg');"><a onclick="jump(${q.typeid});"  class="current" style="text-decoration: none;cursor: pointer;" >${q.typename }</a></td>
       </c:forEach>
     </tr>
    </table>   
   </div>
   <div>
      <div style="margin: 0;padding: 0;">
      <IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=subjectFrame name=subjectFrame onload="SetCwinHeight('subjectFrame', 510)" src="/tkQuestionsInfoAction.do?method=list&subjectid=${subjectid }&questiontypeid=${questiontypeid}" allowTransparency="true"></IFRAME>
    </table>
   </div>
   </div>
  </body>
</html>
