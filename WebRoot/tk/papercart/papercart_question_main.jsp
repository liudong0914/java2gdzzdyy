<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="/tk/papercart/css/subject.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery.tree.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery-1.4.2.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  </head>
  <script type="text/javascript">
$(function(){ 
   var dda=document.getElementsByName("questiontype");
   var df=document.getElementsByName("df");
   jump(dda[0].id);
   df[0].className="filter-item filter-item-on";
 })
 function changedifficult(temp){
  document.getElementById("difficult").value=temp.id;
  var questions=document.getElementsByName("questiontype");
  var df=document.getElementsByName("df");
  for(var j=0;j<df.length;j++){
     df[j].className="filter-item";
  }
  temp.className="filter-item filter-item-on";
  for(var i=0;i<questions.length;i++){
    if(questions[i].className=="filter-item filter-item-on"){
       jump(questions[i].id);
    }
  }
 }
 function jump(id){
  var ifarme=document.getElementById("subjectFrame");
  var difficult=document.getElementById("difficult").value;
  var title=document.getElementById("title").value;
  var dda=document.getElementsByName("questiontype");
  for(var i=0;i<dda.length;i++){
    dda[i].className="filter-item";
  }
  document.getElementById(id).className="filter-item filter-item-on";
  ifarme.src="/tkPaperCartAction.do?method=questions&subjectid=${subjectid }&questiontypeid="+id+"&knopointid=${knopointid}&gradetype=${gradetype}&difficult="+difficult+"&title="+title;;
 }
 function sosuo(){
  var title=document.getElementById("title").value;
 var ifarme=document.getElementById("subjectFrame");
  var difficult=document.getElementById("difficult").value;
  var questiontypeid="";
  var questions=document.getElementsByName("questiontype");
  for(var i=0;i<questions.length;i++){
    if(questions[i].className=="filter-item filter-item-on"){
      questiontypeid=questions[i].id;
    }
  }
  ifarme.src="/tkPaperCartAction.do?method=questions&subjectid=${subjectid }&questiontypeid="+questiontypeid+"&knopointid=${knopointid}&gradetype=${gradetype}&difficult="+difficult+"&title="+title;
 }
</script>
  <body style="overflow: hidden;">
   <div class="hd pd10_1">
   <dl id="quiz-type-select" class="sui-filter fn-clear clearfix">
    <dt class="filter-type">题型</dt>
    <c:forEach items="${questiontypes}" var="q">
    <dd><a id="${q.typeid }" class="filter-item" name="questiontype" href="javascript:jump(${q.typeid });">${q.typename }</a></dd>
    </c:forEach>
   <dd style="margin-left: 50px;"><input type="text" id="title" style="width: 200px;height: 25px;"></dd><dd><a style="cursor: pointer;" onclick="sosuo();" class="filter-item">搜索</a></dd>
    </dl>
    <dl id="quiz-type-select" class="sui-filter fn-clear clearfix">
    <dt class="filter-type">难易度</dt>
    <dd><a name="df" style="cursor: pointer;" onclick="changedifficult(this)" id="" class="filter-item">全部</a><a name="df" style="cursor: pointer;" class="filter-item" onclick="changedifficult(this)" id="A">容易</a><a name="df" style="cursor: pointer;" class="filter-item" id="B" onclick="changedifficult(this)">较易</a><a name="df" style="cursor: pointer;" class="filter-item" onclick="changedifficult(this)" id="C" >一般</a><a name="df" style="cursor: pointer;" class="filter-item" onclick="changedifficult(this)" id="D" >较难</a><a name="df" style="cursor: pointer;" class="filter-item" onclick="changedifficult(this)" id="E" >困难</a> </dd>
    <input type="hidden" id="difficult" value=""/>
    </dl>
   </div>
   <div style="height: 100%;">
      <div style="margin: 0;padding: 0;height: 680px;">
      <IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=subjectFrame name=subjectFrame  src="/tkPaperCartAction.do?method=questions&subjectid=${subjectid }&questiontypeid=${questiontypeid}&gradetype=${gradetype}" allowTransparency="true" style="overflow: hidden;"></IFRAME>
    </div>
   </div>
  </body>
</html>
