<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
</head>

<body>
<div class="mainCon">
<div class="mainCon-bd">
	<div class="form-body creatCourseStep">
		<div class="form-row form-row01">
			<div class="label">
				<label>
				消息主题：
				</label>
			</div>
			<div class="input">${sysMessageUser.sysMessageInfo.title }</div>
		</div>
		
		<div class="form-row" style="margin-bottom:30px;">
			<div class="label">
				<label>
				消息内容：
				</label>
			</div>
			<div class="input">${sysMessageUser.sysMessageInfo.content }</div>
		</div>
		
		<div class="form-row" style="margin-bottom:30px;">
			<div class="label">
				<label>
				发件人：
				</label>
			</div>
			<div class="input">
				<logic:equal value="1" name="sysMessageUser" property="sysMessageInfo.type">
				系统消息
				</logic:equal>
				<logic:notEqual value="1" name="sysMessageUser" property="sysMessageInfo.type">
				${sysUserInfo.username }
				</logic:notEqual>
			</div>
		</div>
		
		<div class="form-row" style="margin-bottom:30px;">
			<div class="label">
				<label>
				发送时间：
				</label>
			</div>
			<div class="input">${sysMessageUser.sysMessageInfo.createdate }</div>
		</div>
	</div>
</div>
</div>
</body>
</html>
