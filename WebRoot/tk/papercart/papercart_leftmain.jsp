<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
</head>
<body style="overflow: hidden;">
  <div id="scrollContent">
   <table width="100%">
    <!-- 左侧树形 -->
     <td class="ver01">
        <div class="box3" overflow="hidden" showStatus="false" panelTitle="树列表">
			 	<div class="cusBoxContent" id="cusBoxContent" style="width:200px;height: 1000px;">
			  		<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmleft name=rfrmleft src="/tkPaperCartAction.do?method=tree&gradetype=${gradetype }&subjectid=${subjectid}" allowTransparency="true"></IFRAME>
			  	</div>
		</div>
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			<div id="rfrmrightContent" style="margin: 0;padding: 0 5px 0 0;height: 1000px;">
				<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=onright name=onright  src="/tkPaperCartAction.do?method=questionmain&gradetype=${gradetype }&subjectid=${subjectid}" allowTransparency="true"></IFRAME>
			</div>
		</td>
     </td>
   </table>
  </div> 
</body>
</html>
