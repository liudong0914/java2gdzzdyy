<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="/tk/papercart/css/subject.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery.tree.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery-1.4.2.min.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript">
   $(function(){
      var xtype="${xtype}";
      if(""!=xtype){
      parent.rfrmleft.location.reload();
      }
   });
 
 function displayclass(temp){
  temp.style.borderColor="#000000";
 }
 function hiddenclass(temp){
  temp.style.borderColor="#FFFFFF";
 }
 function updateScore(questionid){
   /* var diag=new Dialog();
    diag.Title="试题分值";
    diag.URL='/tkPaperCartAction.do?method=beforeupdateScore&userid=${userid}&subjectid=${subjectid}&xueduanid=${xueduanid}&type=${type}&questionid='+questionid;
    diag.Width=400;
    diag.Height=300;
    diag.CancelEvent=function(){	
       if(diag.innerFrame.contentWindow.document.getElementById('score')){
         var score=diag.innerFrame.contentWindow.document.getElementById('score').value;
         document.getElementById(questionid+"score").innerHTML="(分值："+parseFloat(score).toFixed(1)+")";
         parent.rfrmleft.location.reload();
       }
       diag.close();
      };
    diag.show();
  */
  /*
   if (navigator.userAgent.indexOf('MSIE') >= 0 || navigator.userAgent.indexOf('rv:11') >= 0){
    var returnValue = window.showModalDialog("/tkPaperCartAction.do?method=beforeupdateScore&userid=${userid}&subjectid=${subjectid}&xueduanid=${xueduanid}&type=${type}&questionid="+questionid,window, "dialogWidth=600px;dialogHeight=150px");
   }else{
    window.open("/tkPaperCartAction.do?method=beforeupdateScore&userid=${userid}&subjectid=${subjectid}&xueduanid=${xueduanid}&type=${type}&questionid="+questionid,"_blank","heignt=150px,width=600px");
   } 
   */
    window.open("/tkPaperCartAction.do?method=beforeupdateScore&userid=${userid}&subjectid=${subjectid}&xueduanid=${xueduanid}&type=${type}&questionid="+questionid,"_blank","heignt=150px,width=600px");
 }
 function deleteQuestion(questionid){
   $.ajax({
     url:"tkPaperCartAction.do?method=deleteQuestion&subjectid=${subjectid}&gradetype=${xueduanid}&questionid="+questionid,
     data:{},
     cache:false,
     async:false,
     dataType:'json'
   });
   parent.parent.parent.rfrmleft.location='/tkPaperCartAction.do?method=subject&type=${type}&gradetype=${xueduanid}&subjectid=${subjectid}';
   parent.parent.parent.rfrmright.location='/tkPaperCartAction.do?method=paperPervier&subjectid=${subjectid}&xueduanid=${xueduanid}';
 }
 function updateOrindex(status,questionid){
    $.ajax({
     url:"tkPaperCartAction.do?method=updateOrdeinxs&subjectid=${subjectid}&xueduanid=${xueduanid}&questionid="+questionid+"&status="+status,
     data:{},
     cache:false,
     async:false,
     dataType:'json'
    });
    parent.parent.parent.rfrmright.location='/tkPaperCartAction.do?method=paperPervier&subjectid=${subjectid}&xueduanid=${xueduanid}';
 }
</script>
</head>
  <body>
    <div class="sui-questions pd10_1">
   <c:forEach items="${list}" var="question" varStatus="status">
   <c:set var="model" value="${question }" scope="request"></c:set>
   <div  class="sui-question-box" style="border-color:#FFFFFF; "  onmousemove="displayclass(this);" onmouseout="hiddenclass(this);">
          <div class="hd">
              <div class="float1" >
                <span class="meta">${status.index+1 }.</span>
              </div>
            <div class="float1" style="float: right;">
              <span class="meta" id="${question.questionid}score">(分值：${question.papercartSocre })</span>
              <img onclick="updateScore(${question.questionid});" src="/tk/papercart/images/1.png" style="width: 25px;height: 22px;cursor: pointer;"/>
              <img onclick="deleteQuestion(${question.questionid});" src="/tk/papercart/images/4.png" style="width: 25px;height: 22px;cursor: pointer;"/>
              <img onclick="updateOrindex('up',${question.questionid});" src="/tk/papercart/images/3.png" style="width: 25px;height: 22px;cursor: pointer;" />
              <img onclick="updateOrindex('down',${question.questionid});" src="/tk/papercart/images/2.png" style="width: 25px;height: 22px;cursor: pointer;"/>
            </div>
          </div>
          <c:if test="${question.tkQuestionsType.type=='A'||question.tkQuestionsType.type=='B'}">   
          <div class="js-quiz bd">
            <div class="question-box fn-clear">
              <div class="question-content fn-clear class0">
                <div class="question">
                  <div class="quiz-body">${question.titlecontent }</div>
                  <div class="quiz-options">
                  <ul>
                  <c:forEach var="x" begin="1" end="${question.optionnum }" step="1">
                  <c:set var="num" value="${x }" scope="request"></c:set>
                  <%
                    TkQuestionsInfo model=(TkQuestionsInfo)request.getAttribute("model");
                  %>
                  <li class="q-ic"><span class="q-ic"><%=model.getOptionNo(Integer.parseInt(request.getAttribute("num").toString()))%></span>.&nbsp;<%=model.getOptionValue(Integer.parseInt(request.getAttribute("num").toString())) %></li>
                  </c:forEach>
                  </ul>
                  </div>
                </div>
              </div>
            </div>
          </div>
            </c:if>
          <c:if test="${question.tkQuestionsType.type=='C'||question.tkQuestionsType.type=='E'||question.tkQuestionsType.type=='S'}">
          <div class="js-quiz bd">
            <div class="question-box fn-clear">
              <div class="question-content fn-clear class0"s>
                <div class="question">
                  <div class="quiz-body">${question.titlecontent }</div>
                </div>
              </div>
            </div>
          </div>
        </c:if>
        <c:if test="${question.tkQuestionsType.type=='F'||question.tkQuestionsType.type=='M' }">
         <div class="js-quiz bd">
            <div class="question-box fn-clear">
              <div class="question-content fn-clear class0">
                <div class="question">
                  <div class="quiz-body">${question.titlecontent }</div>
                </div>
              </div>
              <c:forEach items="${question.childrenquestions }" var="cq" varStatus="cqstatus">
              <div class="hd">
                 <div class="float1" >
                   <span class="meta">${cqstatus.index+1 }.</span>
                 </div>
              </div>
              <c:if test="${cq.tkQuestionsType.type=='A'||cq.tkQuestionsType.type=='B'}">
              <c:set var="cqmodel" value="${cq }" scope="request"></c:set>                
                 <div class="js-quiz bd">
            <div class="question-box fn-clear">
              <div class="question-content fn-clear class0">
                <div class="question">
                  <div class="quiz-body">${cq.titlecontent }</div>
                  <div class="quiz-options">
                  <ul>
                  <c:forEach var="x" begin="1" end="${cq.optionnum }" step="1">
                  <c:set var="num" value="${x }" scope="request"></c:set>
                  <%
                    TkQuestionsInfo cqmodel=(TkQuestionsInfo)request.getAttribute("cqmodel");
                  %>
                  <li class="q-ic"><span class="q-ic"><%=cqmodel.getOptionNo(Integer.parseInt(request.getAttribute("num").toString()))%></span>.&nbsp;<%=cqmodel.getOptionValue(Integer.parseInt(request.getAttribute("num").toString())) %></li>
                  </c:forEach>
                  </ul>
                  </div>
                </div>
              </div>
            </div>
            </div>
          </c:if>
              
              
              <c:if test="${cq.tkQuestionsType.type!='A'&&cq.tkQuestionsType.type!='B'}">
                <div class="js-quiz bd">
                  <div class="question-box fn-clear">
                    <div class="question-content fn-clear class0">
                  <div class="question">
                  <div class="quiz-body">${cq.titlecontent }</div>
                </div>
                </div>
               </div>
              </div>
             </c:if>
              </c:forEach>
            </div>
        </c:if>
        </div>
      </c:forEach>
   </div>
  </body>
</html>
