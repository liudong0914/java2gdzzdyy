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
<script type="text/javascript">
function shuaxin(select){
var state=select.value;
var fm=document.getElementById("fm");
fm.action="tkPaperAnswerAction.do?method=getUserQuestionsInfo&userid=${userid}&bookcontentid=${bookcontentid}&paperid=${paperid}&classid=${classid}&state="+state;
fm.submit();
}
function hf(){
var state=document.getElementById("state").value;
javascript:location.href='tkPaperAnswerAction.do?method=downloadUserBookContent&classid=${classid}&bookcontentid=${bookcontentid}&paperid=${paperid}&userid=${userid}&state='+state;
}

</script>
  </head>
  <body>
   		<div name="test" style="width:100%;text-align: right;margin-top:10px;"><a id="hf" onclick="hf();" style="margin-right:3%;font-size: 14px;">word导出</a></div>
        <form id="fm" method="post">
        <div style="width: 100%;text-align: left;margin-left: 30px;">
          <select id="state" name="state" onchange="shuaxin(this);">
          <option value="1" <logic:equal value="1" name="state">selected="selected"</logic:equal>>全部</option>
          <option value="0" <logic:equal value="0" name="state">selected="selected"</logic:equal>>答错</option>
          </select>
        </div>
        </form>
        <c:forEach items="${userQuestions}" var="uq" varStatus="s">
        <div class="box1" position=center>
        <table width="100%">
        <tr>
        	<td style="width:70%;">${s.index+1}、${uq.question.titlecontent}(${uq.question.tkQuestionsType.typename}:&nbsp;&nbsp;${uq.question.score}分)</td>
        	<%--<td rowspan="7" style="padding: 0;width:25%;"><c:if test="${!empty(uq.question.descript)}">答案解析：<br/>${uq.question.descript}</c:if></td>
        --%></tr>
        <c:if test="${uq.question.tkQuestionsType.type eq 'A'||uq.question.tkQuestionsType.type eq 'B'}">
        <c:if test="${!empty(uq.question.option1)}">
        	<tr>
        		<td>A.${uq.question.option1}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option2)}">
        	<tr>
        		<td>B.${uq.question.option2}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option3)}">
        	<tr>
        		<td>C.${uq.question.option3}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option4)}">
        	<tr>
        		<td>D.${uq.question.option4}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option5)}">
        	<tr>
        		<td>E.${uq.question.option5}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option6)}">
        	<tr>
        		<td>F.${uq.question.option6}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option7)}">
        	<tr>
        		<td>G.${uq.question.option7}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option8)}">
        	<tr>
        		<td>H.${uq.question.option8}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option9)}">
        	<tr>
        		<td>I.${uq.question.option9}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(uq.question.option10)}">
        	<tr>
        		<td>J.${uq.question.option10}</td>
        	</tr>
        </c:if>
        </c:if>
        <%--
        <c:if test="${!empty(uq.question.rightans)}">
        <tr>
        	<td colspan="2">正确答案：${uq.question.rightans}</td>
        </tr>
        </c:if>
        --%>
        <c:if test="${empty(uq.childQuesitons)}">
        <c:if test="${empty(uq.userAnswer)}">
        <tr>
        	<td colspan="2"><span style="color:red;">尚未作答</span></td>
        </tr>
        </c:if>
        </c:if>
        <c:if test="${!empty(uq.userAnswer)}">
        <tr>
        	<td colspan="2">
        	<table class="tableStyle">
        		<tr>
        			<td class="ali02">是否答对</td>
        			<td class="ali02">我的答案</td>
        			<td class="ali02">得分</td>
        			<td class="ali02">作答时间</td>
        		</tr>
        		<c:forEach items="${uq.userAnswer}" var="ua" varStatus="s">
        		<tr>
        			<td class="ali02">${ua.isright eq '1' ?"<span style='color:green;'>对</span>":"<span style='color:red;'>错</span>" }</td>
        			<td class="ali02">${ua.answer eq '1'?"对":ua.answer eq '0'?"错":ua.answer}</td>
        			<td class="ali02">${ua.score}</td>
        			<td class="ali02">${ua.createdate}</td>
        		</tr>
        		</c:forEach>
        	</table>
        	</td>
        </tr>
        </c:if>
        <c:forEach items="${uq.childQuesitons}" var="child" varStatus="s">
        	<table width="100%">
        <tr>
        	<td style="width:70%;">${s.index+1}、${child.que.titlecontent}&nbsp;&nbsp;&nbsp;&nbsp;(${child.que.tkQuestionsType.typename}:&nbsp;&nbsp;${child.que.score}分)</td>
        	<%--<td rowspan="7" style="padding: 0;width:25%;">答案解析：<br/>${child.que.descript}</td>
        --%></tr>
        <c:if test="${child.que.tkQuestionsType.type eq 'A'||child.que.tkQuestionsType.type eq 'B'}">
        <c:if test="${!empty(child.que.option1)}">
        	<tr>
        		<td>A.${child.que.option1}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option2)}">
        	<tr>
        		<td>B.${child.que.option2}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option3)}">
        	<tr>
        		<td>C.${child.que.option3}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option4)}">
        	<tr>
        		<td>D.${child.que.option4}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option5)}">
        	<tr>
        		<td>E.${child.que.option5}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option6)}">
        	<tr>
        		<td>F.${child.que.option6}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option7)}">
        	<tr>
        		<td>G.${child.que.option7}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option8)}">
        	<tr>
        		<td>H.${child.que.option8}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option9)}">
        	<tr>
        		<td>I.${child.que.option9}</td>
        	</tr>
        </c:if>
        <c:if test="${!empty(child.que.option10)}">
        	<tr>
        		<td>J.${child.que.option10}</td>
        	</tr>
        </c:if>
        </c:if>
        <%--<c:if test="${!empty(child.que.rightans)}">
        <tr>
        	<td colspan="2">正确答案：${child.que.rightans}</td>
        </tr>
        </c:if>
        --%>
        <c:if test="${empty(child.childAnswer)}">
        <tr>
        	<td colspan="2"><span style="color:red;">尚未作答</span></td>
        </tr>
        </c:if>
         <c:if test="${!empty(child.childAnswer)}">
        <tr>
        	<td colspan="2">
        	<table class="tableStyle" style="line-height:10px;">
        		<tr>
        			<td class="ali02">是否答对</td>
        			<td class="ali02">我的答案</td>
        			<td class="ali02">得分</td>
        			<td class="ali02">作答时间</td>
        		</tr>
        		<c:forEach items="${child.childAnswer}" var="a" varStatus="s">
        		<tr>
        			<td class="ali02">${a.isright eq '1' ?"<span style='color:green;'>对</span>":"<span style='color:red;'>错</span>"}</td>
        			<td class="ali02">${a.answer eq '1'?"对":a.answer eq '0'?"错":a.answer}</td>
        			<td class="ali02">${a.score}</td>
        			<td class="ali02">${a.createdate}</td>
        		</tr>
        		</c:forEach>
        	</table>
        	</td>
        </tr>
        </c:if>
        </table>
        </c:forEach>
        </table>
        </div>
        </c:forEach>
       </table>
  </body>
</html>
