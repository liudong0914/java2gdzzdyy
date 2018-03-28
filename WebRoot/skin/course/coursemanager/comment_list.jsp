<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.search.PageList"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script src="/skin/course/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/libs/js/sk/comm.js"></script>
<link href="/skin/course/css/style.css" rel="stylesheet"/>
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount" />

function postData(){
    document.pageForm.action = "/eduCourseCommentAction.do?method=list";
  	document.pageForm.submit();
}
function view(commentid){
    document.pageForm.action = "/eduCourseCommentAction.do?method=view&commentid="+commentid;
  	document.pageForm.submit();
}

function edit(commentid){
    document.pageForm.action = "/eduCourseCommentAction.do?method=beforeUpdate&commentid="+commentid;
  	document.pageForm.submit();
}

</script>
</head>

<body>
<form name="pageForm" method="post">
<div class="main">
	<div class="mainBox">
		<div class="mainTit">
			<h3 class="title">课程评价</h3>
		</div>
		<div class="mainCon mainCon01">
			<div class="tab">
				<div class="acts" style="left:0px;">
					评价内容：<input type="text" class="ipt-text" name="content" value="${content }" style="width:200px;">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-pop ml20" value="查询" onclick="postData()">
				</div>
			</div>
		
			<table width="100%" class="table">
				<thead>
					<tr>
						<th width="45">
						<input type="checkbox" onclick="setState(this.checked)">
						</th>
						<th>评价内容</th>
						<th width="60">评分</th>
						<th width="80">评价用户</th>
						<th width="130">评价时间</th>
						<th width="100">操作</th>
					</tr>
				</thead>
				<tbody>
				<logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
					<tr>
						<td colspan="6">
						<div class="tableBox box02">
							<table width="100%" style="border-collapse:separate;">
								<thead>
								<tr>
									<th width="45">
										<input class="checkShow msgCheck" type="checkbox" name="checkid" value="<bean:write property="commentid" name="model"/>" />
									</th>
									<th style="text-align:left;"><bean:write property="content" name="model"/></th>
									<th width="60">${model.score }</th>
									<th width="80"><bean:write property="sysUserInfo.username" name="model"/></th>
									<th width="130"><bean:write property="createdate" name="model"/></th>
									<th width="100">
										<a class="alink" href="#"  onclick="view('<bean:write property="commentid" name="model"/>')">查看</a>
										<c:if test="${empty model.replycontent}">
											<a class="alink" href="#"  onclick="edit('<bean:write property="commentid" name="model"/>')">回复</a>
										</c:if>
									</th>
								</tr>
								</thead>
							</table>
						</div>
						</td>
					</tr>
				</logic:iterate>	
				</tbody>
			</table>
<%@ include file="/skin/course/jsp/page.jsp"%>

<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
		</div>
	</div>
</div>
</form>
</body>
</html>
