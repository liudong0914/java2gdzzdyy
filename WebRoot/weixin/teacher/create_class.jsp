<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>创建班级</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function saveClass(){
	var hascommit = document.getElementById("hascommit").value;
	if(hascommit == "0"){
		document.getElementById("hascommit").value = "1";
		document.pageForm.action="/weixinTeacher.app?method=saveClass";
    	document.pageForm.submit();
	}
}
function selectBook(){
	document.pageForm.action="/weixinTeacher.app?method=selectBook";
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
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=teacher&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">创建班级</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form name="pageForm" method="post">
<div id="container">
	<div class="container_1">
    	<div class="container_1_size">
        	<label class="container_1_size_cj">班级名称</label>
        	<input type="text" value="${classname }" placeholder="2008届七年级(2班)" name="classname" class="container_1_radio2" />
        </div>
    </div>
    <div class="container_2">
    	<div class="container_1_size">
        	<label class="container_1_size_cj">设定人数</label>
            <input type="text" value="${students }" placeholder="请输入班级人数" name="students" class="container_1_radio2" />
        </div>
    </div>
    <div class="container_4">
    	<a href="javascript:selectBook();"><div class="container_2_size" id="box_div3">
    		<span class="container_2_size_span" id="showbookname">${bookname }</span>
    		<input type="hidden" name="bookid" id="bookid" value="${bookid }"/>
    		<input type="hidden" name="bookname" id="bookname" value="${bookname }"/>
            <span class="container_2_more"><img src="/weixin/images/school.png"></span>
        </div></a>
    </div>
    <div class="container_4">
    	<div class="container_1_size">
        	<label class="container_1_size_11">学生是否需要密码进班</label>
        	<div class="container_4_form">
            	<input type="radio" name="pwd" value="1" class="container_4_radio" <logic:equal value="1" name="pwd">checked="checked"</logic:equal>/><label class="container_4_font">是</label>
       			<input type="radio" name="pwd" value="0" class="container_4_radio" <logic:equal value="0" name="pwd">checked="checked"</logic:equal>/><label class="container_4_font">否</label>
            </div>
        </div>
    </div>
	<div class="container_3">
    	<a href="javascript:saveClass();">
        	<div class="container_3_size">
    			<span>立即创建</span>
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