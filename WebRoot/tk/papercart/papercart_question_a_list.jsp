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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript">
 var tempvalue="1";
 function divclikc(temp){
  var answer=document.getElementById(temp+"answer");
  if(tempvalue=="1"){
      answer.style.display="inline";
      tempvalue="2";
   }else{
    answer.style.display="none";
    tempvalue="1";
   }
 }
 
 function insertQuestion(questionid,score){
  var add=document.getElementById(questionid+"add");
  var deletes=document.getElementById(questionid+"delete");
  $.ajax({
    url:"tkPaperCartAction.do?method=addQuestions&subjectid=${subjectid}&gradetype=${gradetype}&questionid="+questionid+"&score="+score,
    data:{},
    cache:false,
    async:false,
    dataType:'json'
  });
   add.style.display="none";
   deletes.style.display="inline";
    var type=<%= request.getSession().getAttribute("type")%>;
   var subjectid=<%= request.getSession().getAttribute("subjectid")%>;
   var gradetype=<%= request.getSession().getAttribute("gradetype")%>;
   parent.parent.parent.rfrmleft.location="/tkPaperCartAction.do?method=subject&type="+type+"&gradetype="+gradetype+"&subjectid="+subjectid;
 }
 function deleteQuestion(questionid){
   var add=document.getElementById(questionid+"add");
   var deletes=document.getElementById(questionid+"delete");
   $.ajax({
     url:"tkPaperCartAction.do?method=deleteQuestion&subjectid=${subjectid}&gradetype=${gradetype}&questionid="+questionid,
     data:{},
     cache:false,
     async:false,
     dataType:'json'
   });
   add.style.display="inline";
   deletes.style.display="none";
       var type=<%= request.getSession().getAttribute("type")%>;
   var subjectid=<%= request.getSession().getAttribute("subjectid")%>;
   var gradetype=<%= request.getSession().getAttribute("gradetype")%>;
   parent.parent.parent.rfrmleft.location="/tkPaperCartAction.do?method=subject&type="+type+"&gradetype="+gradetype+"&subjectid="+subjectid;
 }
</script>
  </head>
  <body>
  <form name="searchForm" method="post">
   <div class="top-count">
        <span>共计<em id="quiz-count" class="num">${questionsize }&nbsp;</em>道题 （温馨提示：点击题目可快速查看答案和解析）</span>
   </div>
   <div class="sui-questions pd10_1">
   <c:forEach items="${list}" var="question">
   <c:set var="model" value="${question }" scope="request"></c:set>
   <div  class="sui-question-box">
          <div class="hd">
            <div class="float1">
              <span class="meta">编号：${question.questionno}</span>
              <span class="meta">难易度：
              <c:if test="${question.difficult=='A'}">容易</c:if>
              <c:if test="${question.difficult=='B'}">较易</c:if>
              <c:if test="${question.difficult=='C'}">一般</c:if>
              <c:if test="${question.difficult=='D'}">较难</c:if>
              <c:if test="${question.difficult=='E'}">很难</c:if>
              </span>
              <span class="meta">更新日期：${question.updatetime }</span>
              <span class="meta">参考分值：${question.score }</span>
            </div>
            <div class="float1">
              <span class="meta">标签：${question.tag }</span>
            </div>
            <!-- 
            <div class="float1">
              <span class="meta">标题：${question.title }</span>
            </div>
             -->
            <c:if test="${question.flags=='1' }">
            <div class="float2 btn">
              <a id="${question.questionid }add" onclick="insertQuestion(${question.questionid },${question.score });"   class="btn_1" style="display: none;cursor:pointer;">加入试卷</a>
              <a id="${question.questionid }delete" onclick="deleteQuestion(${question.questionid})"   class="btn_2"  style="cursor: pointer;">撤销</a>
            </div>
            </c:if>
            <c:if test="${question.flags!='1' }">
            <div class="float2 btn">
              <a id="${question.questionid }add" onclick="insertQuestion(${question.questionid },${question.score });"  class="btn_1" style="cursor: pointer;">加入试卷</a>
              <a id="${question.questionid }delete" onclick="deleteQuestion(${question.questionid})" class="btn_2" style="display: none;cursor: pointer;">撤销</a>
            </div>
            </c:if>
          </div>
          <div class="js-quiz bd" onclick="divclikc(${question.questionid});">
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
                  <div class="answer" id="${question.questionid}answer" style="display: none;"><br>
                    <div class="da_1">
                      <font color="#ff6867">【答案】</font>
                      <div style="margin-left:15px;">${question.rightans }</div>
                    </div><br><br>
                    <div class="da_1">
                      <font color="#ff9933">【解析】</font>
                      <div style="margin-left:15px;">${question.descript }</div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </c:forEach>
   </div>
   <%@ include file="/tk/papercart/page.jsp"%>
   <script type="text/javascript">
function gotoPageSize(size){
	document.getElementById('startcount').value = size;
	document.searchForm.action = "/tkPaperCartAction.do?method=questions";
	document.searchForm.submit();
}
</script>
 <input type="hidden" name="startcount" id="startcount"/>
  <input type="hidden" name="knopointid" value="${knopointid }"/>
  <input type="hidden" name="questiontypeid" value="${questiontypeid }"/>
  <input type="hidden" name="subjectid" value="${subjectid }"/>
  <input type="hidden" name="gradetype" value="${gradetype }"/>
 </form>
  </body>
</html>
