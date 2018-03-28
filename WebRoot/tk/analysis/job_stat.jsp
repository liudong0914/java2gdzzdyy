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
<link rel="stylesheet" type="text/css" id="skin" prePatd="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<script type="text/javascript">
 function shuaxin(select){
var state=select.value;
var fm=document.getElementById("fm");
fm.action="tkPaperAnswerAction.do?method=getStatQuestions&contentid.do?&contentid=${contentid}&bookcontentid=${bookcontentid}&classid=${classid}&state="+state;
fm.submit();
}
function hf(){
var state=document.getElementById("state").value;
javascript:location.href='tkPaperAnswerAction.do?method=downloadJobStat&classid=${classid}&bookcontentid=${bookcontentid}&contentid=${contentid}&state='+state;
}
</script>
<!-- 表单验证end -->
  </head>
  <body>
    <div>
    <div name="test" style="width:100%;text-align: right;margin-top:10px;"><a onclick="hf();"  style="margin-right:3%;font-size: 14px;">word导出</a></div>
      <form id="fm" method="post">
     <div style="width: 100%;text-align: left;margin-left: 30px;">
          <select id="state" name="state" onchange="shuaxin(this);">
          <option value="1" <logic:equal value="1" name="state">selected="selected"</logic:equal>>全部</option>
          <option value="0" <logic:equal value="0" name="state">selected="selected"</logic:equal>>答错</option>
          </select>
        </div>
      </form>  
     <div class="box1" position=left>
     	<table width="100%" style="padding:0px 20px;">
     		<tr>
     			<td colspan="3" align="left" style="color:#139;">试题编号：${jobStat.questionno}&nbsp;&nbsp;&nbsp;&nbsp;(${jobStat.typename})</td>
     		</tr>
     		<tr>
     			<td colspan="3" align="left">题干：${jobStat.titlecontent}</td>
     		</tr>
     		<c:if test="${jobStat.questiontype eq 'A' || jobStat.questiontype eq 'B' }">
     		<c:if test="${!empty(jobStat.option1)}">
     		<tr>
     			<td colspan="3" align="left">A：${jobStat.option1}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option2)}">
     		<tr>
     			<td colspan="3" align="left">B：${jobStat.option2}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option3)}">
     		<tr>
     			<td colspan="3" align="left">C：${jobStat.option3}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option4)}">
     		<tr>
     			<td colspan="3" align="left">D：${jobStat.option4}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option5)}">
     		<tr>
     			<td colspan="3" align="left">E：${jobStat.option5}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option6)}">
     		<tr>
     			<td colspan="3" align="left">F：${jobStat.option6}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option7)}">
     		<tr>
     			<td colspan="3" align="left">G：${jobStat.option7}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option8)}">
     		<tr>
     			<td colspan="3" align="left">H：${jobStat.option8}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option9)}">
     		<tr>
     			<td colspan="3" align="left">I：${jobStat.option9}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(jobStat.option10)}">
     		<tr>
     			<td colspan="3" align="left">J：${jobStat.option10}</td>
     		</tr>
     		</c:if>
     		</c:if>
     		<tr style="color:red;">
     			<td>总作答次数：${jobStat.paCount}</td>
     			<td>答对次数：${jobStat.correctCount}</td>
     			<td>答错次数：${jobStat.errorCount}</td>
     		</tr>
     	</table>
     	<c:forEach items="${childQuestions}" var="q" varStatus="s">
     		<table width="100%" style="padding:0px 20px;">
     		<tr>
     			<td colspan="3" align="left" style="color:#139;">${s.index+1}、试题编号：${q.questionno}&nbsp;&nbsp;&nbsp;&nbsp;(${q.tkQuestionsType.typename})</td>
     		</tr>
     		<tr>
     			<td colspan="3" align="left">题干：${q.titlecontent}</td>
     		</tr>
     		<c:if test="${q.tkQuestionsType.type eq 'A' || q.tkQuestionsType.type eq 'B' }">
     		<c:if test="${!empty(q.option1)}">
     		<tr>
     			<td colspan="3" align="left">A：${q.option1}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option2)}">
     		<tr>
     			<td colspan="3" align="left">B：${q.option2}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option3)}">
     		<tr>
     			<td colspan="3" align="left">C：${q.option3}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option4)}">
     		<tr>
     			<td colspan="3" align="left">D：${q.option4}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option5)}">
     		<tr>
     			<td colspan="3" align="left">E：${q.option5}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option6)}">
     		<tr>
     			<td colspan="3" align="left">F：${q.option6}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option7)}">
     		<tr>
     			<td colspan="3" align="left">G：${q.option7}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option8)}">
     		<tr>
     			<td colspan="3" align="left">H：${q.option8}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option9)}">
     		<tr>
     			<td colspan="3" align="left">I：${q.option9}</td>
     		</tr>
     		</c:if>
     		<c:if test="${!empty(q.option10)}">
     		<tr>
     			<td colspan="3" align="left">J：${q.option10}</td>
     		</tr>
     		</c:if>
     		</c:if>
     	</table>
     	</c:forEach>
     	</div>
     	<div class="box1" position=center>
       <table class="tableStyle">
         <tr>
	          <td class="ali02">登录名</td>
	          <td class="ali02">姓名</td>
	          <td class="ali02">作答次数</td>
	          <td class="ali02">答对次数</td>
	          <td class="ali02">答错次数</td>
	          <c:forEach items="${childQuestions}" var="c" varStatus="s">
	          <td class="ali02">${s.index+1}题</td>
	          </c:forEach>         
         </tr>
         <c:forEach items="${jobStat.userStat}" var="us">
         <tr>
         	<td class="ali02">${us.loginname}</td>
         	<td class="ali02">${us.username}</td>
         	<td class="ali02">${us.paCount}</td>
         	<td class="ali02">${us.correctCount}</td>
         	<td class="ali02">${us.errorCount}</td>
         	<c:forEach items="${us.childStat}" var="c">
         	<td class="ali02"> 总:${c.sumCount}&nbsp;对:${c.correctCount}&nbsp;错:${c.errorCount}</td>
         	</c:forEach>
         </tr>
         </c:forEach>
       </table>
     </div>
    </div>
  </body>
</html>
