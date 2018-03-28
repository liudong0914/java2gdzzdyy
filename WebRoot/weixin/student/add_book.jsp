<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>绑定作业本</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function addSaveBook(){
	var hascommit = document.getElementById("hascommit").value;
	if(hascommit == "0"){
		document.getElementById("hascommit").value = "1";
		document.pageForm.action="/weixinStudent.app?method=addSaveBook";
    	document.pageForm.submit();
	}
}
function selectBook(){
	document.pageForm.action="/weixinStudent.app?method=selectBook";
    document.pageForm.submit();
}
</script>
<style>
:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
    color: #cdcdcd;  
}
::-moz-placeholder { /* Mozilla Firefox 19+ */
    color: #cdcdcd;
}
input:-ms-input-placeholder,
textarea:-ms-input-placeholder {
    color: #cdcdcd;
}
.botton6 input::-webkit-input-placeholder,
textarea::-webkit-input-placeholder {
    color: #cdcdcd;
}
</style>
</head>

<body>
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=student&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">绑定作业本</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form name="pageForm" method="post">
<div id="container">
    <div class="container_4">
    	<a href="javascript:selectBook();"><div class="container_2_size" id="box_div3">
    		<span class="container_2_size_span" id="showbookname">${bookname }</span>
    		<input type="hidden" name="bookid" id="bookid" value="${bookid }"/>
    		<input type="hidden" name="bookname" id="bookname" value="${bookname }"/>
            <span class="container_2_more"><img src="/weixin/images/school.png"></span>
        </div></a>
    </div>
	<div class="container_3">
    	<a href="javascript:addSaveBook();">
        	<div class="container_3_size">
    			<span>立即绑定</span>
        	</div>
        </a>
    </div>
    <logic:present name="errmsg"><div class="tips"><bean:write name="errmsg" /></div></logic:present>
</div>
<input type="hidden" name="userid" value="${userid }"/>
<input type="hidden" name="hascommit" id="hascommit" value="0"/><!-- 控制重复提交 -->
</form>
</body>
</html>