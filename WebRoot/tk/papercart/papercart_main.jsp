<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线组卷</title>

<!--弹窗组件start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
</head>
<body>
  <div id="scrollContent">
   <table width="100%">
    <!-- 左侧树形 -->
     <td class="ver01">
        <div class="box3" overflow="hidden" showStatus="false" panelTitle="学科列表">
			 	<div id="cusBox" class="cusBoxContent" id="cusBoxContent" style="width:200px;height: 750px;">
			  		<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmleft name=rfrmleft src="/tkPaperCartAction.do?method=subject&type=${type }&gradetype=${gradetype }&subjectid=${subjectid}" allowTransparency="true"></IFRAME>
			  	</div>
		</div>
		
		<!--右侧区域start-->
		<td width="100%" class="ver01" >
			<div id="rfrmrightContent" style="height: 750px;">
				<IFRAME scrolling="auto" width="100%" height="100%" frameBorder=0 id=rfrmright name=rfrmright  src="/tkPaperCartAction.do?method=leftmain&gradetype=${gradetype }&subjectid=${subjectid}" allowTransparency="true" style="overflow: hidden;"></IFRAME>
			</div>
		</td>
     </td>
   </table>
  </div> 
</body>
</html>
