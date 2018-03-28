<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link type="text/css" href="/skin/course/css/style.css" rel="stylesheet">
<script type="text/javascript" src="/libs/js/sk/iframe.js"></script>
</head>
<body>
<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">课程目录</h3>
		</div>
		<div class="mainCon mainCon01">
			<table width="100%">
			<tr>
				<!--左侧区域start-->
				<td class="ver01" style="position: relative;">
					<div class="box3" overflow="hidden" showStatus="false" panelTitle="树列表" style="margin-top:0px; position: absolute; top: 0px">
					 	<div class="cusBoxContent" id="cusBoxContent" style="width:180px;">
					  		<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmleft name=rfrmleft onload="SetCwinHeight('rfrmleft', 600)" src="/courseManager.do?method=columnTree" allowTransparency="true"></IFRAME>
					  	</div>
				  	</div>
				</td>
				<!--左侧区域end-->
				
				<!--右侧区域start-->
				<td width="80%" class="ver01" >
					<div id="rfrmrightContent" style="margin: 0;padding: 0 5px 0 0;">
						<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmright name=rfrmright onload="SetCwinHeight('rfrmright', 600)" src="/courseManager.do?method=columnList&parentno=0000" allowTransparency="true"></IFRAME>
					</div>
				</td>
				<!--右侧区域end-->
			</tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>