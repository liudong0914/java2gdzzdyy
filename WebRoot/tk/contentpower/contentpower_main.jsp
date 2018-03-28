<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<script type="text/javascript" src="/libs/js/sk/iframe.js"></script>
</head>
<body>
  <div id="scrollContent">
   <table width="100%">
    <!-- 左侧树形 -->
     <td class="ver01">
        <div class="box3" overflow="hidden" showStatus="false" panelTitle="树列表">
			 	<div class="cusBoxContent" id="cusBoxContent" style="width:280px;">
			  		<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmleft name=rfrmleft src="/tkBookContentPowerAction.do?method=tree&userid=${userid}&type=${type}" allowTransparency="true"></IFRAME>
			  	</div>
		</div>
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			<div id="rfrmrightContent" style="margin: 0;padding: 0 5px 0 0;">
				<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmright name=rfrmright onload="setCwinHeight('rfrmleft', 'rfrmright', 300)" src="/sysLayoutAction.do?method=welcome" allowTransparency="true"></IFRAME>
			</div>
		</td>
     </td>
   </table>
  </div> 
</body>
</html>
