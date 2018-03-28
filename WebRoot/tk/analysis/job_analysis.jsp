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
  </head>
  <body>
    <div>
     <div class="box1" position=center>
       <table class="tableStyle" useClick="false" useCheckBox="true" sortMode="true">
         <tr>
          <th class="ali02">姓名</th>
          <c:forEach items="${paperContents}" var="pc" varStatus="s">
          <th class="ali02"><a href="javascript:" onclick="getStatQuestions(${pc.contentid},${classid},${bookcontentid},${paperid})">${s.index+1}题</a></th>
          </c:forEach>
         </tr>
	         <c:forEach items="${userPaperInfo}" var="u">
	     <tr>
	         <td class="ali02"><a href="javascript:" onclick="getUserQuestionsInfo(${u.userid},${classid},${bookcontentid},${paperid})">${u.username}</a></td>
	         <c:forEach items="${u.userPaperAnswer}" var="a">
	         <td class="ali02">
	           <c:if test="${a.isright eq '1' }">
	           <span style='color:green;font-size:17px;'>√</span>
	           </c:if>
	           <c:if test="${a.isright eq '0' }">
	           <span style='color:red;font-size: 20px;'>×</span>
	           </c:if>
	           <c:if test="${a.isright eq '2' }">
	            
	           </c:if>
	         	<!--  ${a.isright eq '1' ?"<span style='color:green;font-size:17px;'>√</span>":"<span style='color:red;font-size: 20px;'>×</span>"}-->
	         </td>
	         </c:forEach>
	      </tr>
	         </c:forEach>
       </table>
     </div>
    </div>
    <script type="text/javascript">
    function getStatQuestions(contentid,classid,bookcontentid){
		var diag = new top.Dialog();
		diag.Title = "试题统计";
		diag.URL = '/tkPaperAnswerAction.do?method=getStatQuestions&contentid='+contentid+'&bookcontentid='+bookcontentid+'&classid='+classid+'&state=1';
		diag.Width = 1000;
		diag.Height = 800;
		diag.ShowMaxButton=true;
		diag.show();
	}
    
	function getUserQuestionsInfo(userid,classid,bookcontentid,paperid){
		var diag = new top.Dialog();
		diag.Title = "作业详情";
		diag.URL = '/tkPaperAnswerAction.do?method=getUserQuestionsInfo&userid='+userid+'&bookcontentid='+bookcontentid+'&paperid='+paperid+'&classid='+classid+'&state=1';
		diag.Width = 1200;
		diag.Height = 1000;
		diag.ShowMaxButton=true;
		diag.show();
	}
</script>
  </body>
</html>
