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
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
  <body>
    <div class="sui-questions pd10_1">
   <c:forEach items="${list}" var="question" varStatus="status">
   <c:set var="model" value="${question }" scope="request"></c:set>
   <div  class="sui-question-box" style="border-color:#FFFFFF; "  onmousemove="displayclass(this);" onmouseout="hiddenclass(this);">
          <div class="hd">
              <div class="float1" >
             
              </div>
          </div>
          <c:if test="${question.tkQuestionsType.type=='A'||question.tkQuestionsType.type=='B'}">   
          <div class="js-quiz bd">
            <div class="question-box fn-clear">
              <div class="question-content fn-clear class0">
                <div class="question">
                  <div class="quiz-body"><span class="meta">${status.index+1 }.</span>${question.titlecontent }</div>
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
              <div class="question-content fn-clear class0">
                <div class="question">
                  <div class="quiz-body"><span class="meta">${status.index+1 }.</span>${question.titlecontent }</div>
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
                  <div class="quiz-body"><span class="meta">${status.index+1 }.</span>${question.titlecontent }</div>
                </div>
              </div>
              <c:forEach items="${question.childrenquestions }" var="cq" varStatus="cqstatus">
              <div class="hd">
                 <div class="float1" >
                  
                 </div>
              </div>
              <c:if test="${cq.tkQuestionsType.type=='A'||cq.tkQuestionsType.type=='B'}">
              <c:set var="cqmodel" value="${cq }" scope="request"></c:set>                
                 <div class="js-quiz bd">
            <div class="question-box fn-clear">
              <div class="question-content fn-clear class0">
                <div class="question">
                  <div class="quiz-body"> <span class="meta">${cqstatus.index+1 }.</span>${cq.titlecontent }</div>
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
                  <div class="quiz-body"><span class="meta">${cqstatus.index+1 }.</span>${cq.titlecontent }</div>
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
       <button type="button" onclick="javascript:history.go(-1)"><span class="icon_back">返回</span></button>            
   </div>
  </body>
</html>
