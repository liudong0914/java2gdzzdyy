<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>选择英语听力</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function searchRecord(){
    document.pageForm.action="/weixinStudent.app?method=selectEnglishBook";
    document.pageForm.submit();
}
function selectDeal(bookid){
	document.pageForm.action="/weixinStudent.app?method=selectEnglishBookNext&bookid=" + bookid;
    document.pageForm.submit();
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<form name="pageForm" method="post">
<div id="box" style="padding-bottom:72px;">
	<div class="box_1" style="position:fixed !important;width:100%; top:0px;z-index:99;">
    	<div class="box_1_size">
            <input type="text" class="box_1_size_1_input" style="width:70%" name="title" placeholder="搜索作业本" value="${title }"/>
            <a href="javascript:searchRecord();">
            <div class="box_1_size_2">
           		<span>搜索</span>
           	</div>
            </a>
    	</div>
    </div>
	<div style="padding-top:45px;"></div>
	<%
	List list = (List)request.getAttribute("list");
	if(list != null && list.size() > 0){
	TkBookInfo tbi = null;
	for(int i=0, size=list.size(); i<size; i++){
		tbi = (TkBookInfo)list.get(i);
	%>
    <div <%if(i == 0){ %>class="container_4"<%}else{ %>class="container_5"<%} %> onclick="selectDeal('<%=tbi.getBookid() %>')">
    	<div class="container_1_size">
        	<label class="container_1_size_111" style="font-size:14px;"><%=tbi.getTitle() %>（<%=Constants.getCodeTypevalue("version", tbi.getVersion()) %>）</label>
        	<div class="container_4_form_1">
        		<input type="radio" name="bookid" value="<%=tbi.getBookid() %>" class="container_4_radio_list1" />
        	</div>
        </div>
    </div>
    <%}}else{ %>
		<div class="container_5" style="margin-top:15px;border-top:1px solid #ddd;">
    	<div class="container_1_size">
        	<label class="container_1_size_11">暂无数据！</label>
        	<div class="container_4_form_1">
        	</div></div>
    	</div>
	<%} %>
</div>
<input type="hidden" name="userid" value="${userid }"/>
</form>

<div class="conrainer_fixed1" style="bottom:0;left:0;right:0;">
	<div class="container_3">
		<a href="/weixinVod.app?method=booklist&userid=${userid }">
	    	<div class="container_3_size_bj" style="margin-top:-15px">
				<span>查看下册作业本解题微课</span>
	    	</div>
	    </a>
	</div>
</div>
</body>
</html>