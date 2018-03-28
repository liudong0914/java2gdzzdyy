<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.wkmk.edu.bo.EduKnopointInfo"%>
<%@page import="java.util.List"%>
<link href="/tk/papercart/css/subject.css" rel="stylesheet"
	type="text/css" />
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery.tree.js"></script>
<script type="text/javascript" src="../../tk/papercart/js/jquery-1.4.2.min.js"></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<script>
 $(function(){
  var num=${num};
  var width=num*380;
  document.getElementById("knopoint").style.width=width+"px";
 });	
  function InteligencPaper(){
   var form=document.getElementById("inteligencform");
   form.submit();
  }	
</script>
<style>
.boxs {
	width: 350px;
	height: auto;
	padding-top: 2px;
	padding-bottom: 10px;
	font-size: 15px;
	float: left;
	margin-left: 2px;
	color: #000;
}

.boxs ul {
	margin: 0px;
	padding: 0px;
}

.boxs ul li {
	list-style: none;
	line-height: 26px;
	margin-top: 10px;
	margin-left: 26px;
}

.boxs label {
	margin-left: 5px;
}
</style>
</head>
<body>
<form id="inteligencform" action="tkPaperCartAction.do?method=InteligencePaper" method="post">
	<div class="sui-main" id="quiz-list-box"
		style="height: 100%;overflow: auto;margin-left: 10px;">
		<div class="main-right" style="margin-left: 10px">
			<div class="wh2_1">
				<h2>选择题型数量</h2>
				<div class="intop clearfix">
					<c:forEach items="${questionTypes }" var="q">
						<div class="typelist">
							<h3>${q.typename }</h3>
							<select name="${q.type }">
								<c:forEach begin="0" end="30" var="x">
									<option value="${x}">${x }</option>
								</c:forEach>
							</select>
							<h3>分值</h3>
							<input name="${q.type }_score"
								style="line-height: 24px;height: 24px;" type="text" size="10"
								value="10" />
						</div>
					</c:forEach>
				</div>
			</div>
			<h3>难度</h3>
			<select name="difficult">
				<option value="">全部</option>
				<option value="A">容易</option>
				<option value="B">较易</option>
				<option value="C">一般</option>
				<option value="D">较难</option>
				<option value="E">很难</option>
			</select>
			<h3>知识点</h3>
			<div id="knopoint" class="bd_1" style="height: 400px;">
				<c:forEach var="x" begin="0" end="${num -1}">
					<c:set var="tempnum" value="${x }" scope="request"></c:set>
					<div id="boxs" class="boxs" style="height: auto;">
						<ul>
							<%
								if (request.getAttribute("tempnum") != null) {
										int num = Integer.parseInt(request.getAttribute("tempnum").toString());
										List list = (List) request.getAttribute("knopoinlist" + num);
										EduKnopointInfo model = null;
										for (int i = 0; i < list.size(); i++) {
											model = (EduKnopointInfo) list.get(i);
							%>
							<li><input name="knopoint" type="checkbox"
								value="<%=model.getKnopointid()%>" /><label><%=model.getTitle()%></label>
							</li>
							<%
								}
									}
							%>
						</ul>
					</div>
				</c:forEach>
			</div>
		</div>
		<div class="bd_1">
				<div class="preview pd10">
				  <a class="preview-btn" onclick="javascript:InteligencPaper();" style="cursor: pointer;">确定</a>
				</div>
		   </div>		
	</div>
	<input type="hidden" name="xtype" value="1"/>
	<input type="hidden" name="type"  value="${type }"/>
	<input type="hidden" name="subjectid" value="${subjectid }" />
	<input type="hidden" name="xueduanid" value="${xueduanid }"/>
	</form>
</body>
</html>
