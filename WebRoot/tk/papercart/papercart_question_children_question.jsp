<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="/tk/papercart/css/subject.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery.tree.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery-1.4.2.min.js"></script>
<head>
  <script type="text/javascript">
$(function(){ 
   var dda=document.getElementsByName("questiontype");
   jump(dda[0].id);
 })
 
 function jump(id){
  var ifarme=document.getElementById("subjectFrame");
  var dda=document.getElementsByName("questiontype");
  for(var i=0;i<dda.length;i++){
    dda[i].className="filter-item";
  }
  document.getElementById(id).className="filter-item filter-item-on";
  ifarme.src="/tkPaperCartAction.do?method=childrenQuestionList&questionid=${questionid}&questiontype="+id;
 }
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  </head>
  <body style="overflow: hidden;">
   <div class="hd pd10_1">
   <dl id="quiz-type-select" class="sui-filter fn-clear clearfix">
    <dt class="filter-type">题型</dt>
    <c:forEach items="${questiontypes}" var="q">
    <dd><a id="${q.typeid }" class="filter-item" name="questiontype" href="javascript:jump(${q.typeid });">${q.typename }</a></dd>
    </c:forEach>
    </dl>
   </div>
   <div style="height: 100%;">
      <div style="margin: 0;padding: 0;height: 680px;">
      <IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=subjectFrame name=subjectFrame src="" ></IFRAME>
    </div>
   </div>
  </body>
</html>
