<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.search.PageList"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysMessageUser"%>
<%@page import="com.wkmk.sys.bo.SysMessageInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Constants.PRODUCT_NAME %>-消息通知</title>
<link rel="stylesheet" type="text/css" href="/libs/skins/blue/style.css"/>
<script type="text/javascript" src="/libs/js/jquery.js"></script>
<script type="text/javascript" src="/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="/libs/js/popup/dialog.js"></script>
	
<link href="/skin/course/css/style.css" rel="stylesheet"/>
<script type="text/javascript" src="/libs/js/sk/comm.js"></script>
</head>
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount" />

function postData(){
    document.pageForm.action = "/courseCenter.do?method=myMessage&mark=${mark }";
  	document.pageForm.submit();
}
function view(messageuserid){
	var diag = new Dialog();
	diag.Title = "查看消息通知";
	diag.URL = "/courseCenter.do?method=viewMessage&messageuserid=" + messageuserid;
	diag.Width = 600;
	diag.Height = 320;
	diag.CancelEvent = function(){
		postData();
		diag.close();
	};
	diag.show();
}
</script>
<body>
<%@ include file="/skin/course01/jsp/top.jsp"%>

<div class="container clearfix">
<%@ include file="left.jsp" %>
<form name="pageForm" method="post">
<div class="rightCon">
	<div class="main">
		<div class="mainBox">
			<div class="mainTit">
				<h3 class="title">消息通知</h3>
			</div>
			<div class="mainCon mainCon01">
				<div class="tab">
					<ul id="quickNav" style="">
						<li class="borlnone <logic:equal value="" name="isread">current</logic:equal>">
							<a class="jobInfo" href="/courseCenter.do?method=myMessage&mark=8">
							全部（${allmsg }）
							</a>
						</li>
						<li class="<logic:equal value="1" name="isread">current</logic:equal>">
							<a class="jobInfo" href="/courseCenter.do?method=myMessage&mark=8&isread=1">
							已阅（${readmsg }）
							</a>
						</li>
						<li class="<logic:equal value="0" name="isread">current</logic:equal>">
							<a class="course" href="/courseCenter.do?method=myMessage&mark=8&isread=0">
							未阅（${unreadmsg }）
							</a>
						</li>
					</ul>
				</div>
			
				<table width="100%" class="table">
					<thead>
						<tr>
							<th width="45">序号</th>
							<th>消息主题</th>
							<th width="130">发送时间</th>
							<th width="60">操作</th>
						</tr>
					</thead>
					<tbody>
						<%
						Integer startcount = (Integer)request.getAttribute("startcount");
						PageList pagelist = (PageList)request.getAttribute("pagelist");
						List list = pagelist.getDatalist();
						if(list != null && list.size() > 0){
							SysMessageUser smu = null;
							SysMessageInfo smi = null;
							for(int i=0, size=list.size(); i<size; i++){
								smu = (SysMessageUser)list.get(i);
								smi = smu.getSysMessageInfo();
						%>
						<tr>
							<td colspan="4">
							<div class="tableBox box02">
								<table width="100%" style="border-collapse:separate;">
									<thead>
									<tr>
										<th width="45"><%=startcount + i + 1 %></th>
										<th style="text-align:left;"><%if("1".equals(smu.getIsread())){ %><b class="email-r"></b><%}else{ %><b class="email-u"></b><%} %><%=smi.getTitle() %></th>
										<th width="130"><%=smi.getCreatedate() %></th>
										<th width="60">
											<a class="alink" href="javascript:view('<%=smu.getMessageuserid() %>')">查看</a>
										</th>
									</tr>
									</thead>
								</table>
							</div>
							</td>
						</tr>
						<%}}else{ %>
						<tr>
							<td colspan="4" style="text-align:left;">暂无任何消息通知！</td>
						</tr>
						<%} %>
					</tbody>
				</table>
				<%@ include file="/skin/course/jsp/page.jsp"%>
			</div>
		</div>
	</div>
</div>
<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
</form>
</div>

<%@ include file="/skin/course01/jsp/footer.jsp"%>
</body>
</html>
