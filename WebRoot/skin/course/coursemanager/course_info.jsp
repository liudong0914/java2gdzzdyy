<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>

<body>
<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">课程信息</h3>
			<logic:notEqual value="1" name="model" property="status">
			<%
			String isAuhtor = (String)session.getAttribute("isAuhtor");
			Map moduleidMap = (Map)session.getAttribute("moduleidMap");
			String moduleidType = (String)moduleidMap.get("1");
			if("1".equals(isAuhtor) || "2".equals(moduleidType)){
			%>
			<a href="/courseCenter.do?method=beforeUpdateCourse" style="float:right;padding-top:10px;padding-right:5px;">编辑</a>
			<%} %>
			</logic:notEqual>
		</div>
		<div class="mainCon">
		<div class="mainCon-bd">
			<div class="form-body creatCourseStep">
				<div class="form-row form-row01">
					<div class="label">
						<label>
						课程分类：
						</label>
					</div>
					<div class="input jobWrap">
						<java2code:value codetype="coursetypeid" property="coursetypeid"></java2code:value>
				    </div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						课程名称：
						</label>
					</div>
					<div class="input">
						${model.title }
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						课程编号：
						</label>
					</div>
					<div class="input">
						${model.courseno }
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						课程批次：
						</label>
					</div>
					<div class="input">
						${eduCourseClass.classname }
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						课程价格：
						</label>
					</div>
					<div class="input">
						${model.price } (元)
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						课程时数：
						</label>
					</div>
					<div class="input">
						${model.classhour } (课时)
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						开始时间：
						</label>
					</div>
					<div class="input">
						${eduCourseClass.startdate }
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						结束时间：
						</label>
					</div>
					<div class="input">
						${eduCourseClass.enddate }
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
						<label>
						课程图片：
						</label>
					</div>
					<div class="input">
						<div class="fl">
							<img src="/upload/${model.sketch }" width="328" height="170" border="1"/>
						</div>
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
					<label>课程目标：</label>
					</div>
					<div class="input">
					<div class="fl">
						${model.objectives }
						&nbsp;
					</div>
					</div>
				</div>
				
				<div class="form-row">
					<div class="label">
					<label>课程描述：</label>
					</div>
					<div class="input">
						<div class="fl">
							${model.htmlcontent }
							&nbsp;
						</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
</div>
</body>
</html>
