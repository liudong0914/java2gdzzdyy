<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>班级详情</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<style type="text/css">
img{border:0 none; display:inline;}
</style>
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function delClass(){
	document.pageForm.action = "/weixinTeacher.app?method=delClass&userid=${userid }&classid=${classInfo.classid }";
	document.pageForm.submit();
}
function hidetext(){
	var mychar = document.getElementById("con");
	mychar.style.display="none"
}  
function showtext(){
	var mychar = document.getElementById("con");
	mychar.style.display="block"
}
</script>
</head>

<body>
<!-------------弹出确认窗口----------->
<div class="fade1" id="con">
	<div class="tanchu_wx">   	
		<div class="tanchu_fade">
         	<div class="tanchu_fade_font">
         		<p>删除后数据不可恢复，确定删除当前班级吗？</p>
         		<div class="tanchu_fade_font_form">
             		<input type="button" value="确定" onClick="delClass()"/>
                 	<input type="button" value="取消" onClick="hidetext()"/>
                </div>
             </div>
         </div>     
     </div>
</div>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=classList&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">班级详情</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form method="post" name="pageForm" >
<div id="container" style="padding-bottom:75px;">
	<div class="container_bjxq">
    	<p><img src="/upload/<bean:write name="classInfo" property="twocodepath"/>" width="200" height="220"/></p>
    </div>
	<div class="container_1">
    	<div class="container_bjxq_font">
        	<p>班级编号：<bean:write name="classInfo" property="classno"/></p>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_bjxq_font">
        	<p class="container_bjxq_font_p">班级名称：<bean:write name="classInfo" property="classname"/></p>
        	<a href="/weixinTeacher.app?method=classEdit&userid=${userid }&classid=${classInfo.classid }" >
            	<div class="container_bjxq_font_a"><p>修改</p></div>
            </a>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_bjxq_font">
        	<p class="container_bjxq_font_p">班级总人数：<bean:write name="classInfo" property="students"/></p>
        	<logic:equal value="1" name="classInfo" property="pwd">
            <a href="/weixinTeacher.app?method=classPassword&userid=${userid }&classid=${classInfo.classid }" >
            	<div class="container_bjxq_font_a"><p>学生密码</p></div>
            </a>
            </logic:equal>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_bjxq_font">
        	<p>已加入学生：<bean:write name="studentCounts"/></p>
        </div>
    </div>
    <div class="container_2">
    	<div class="container_bjxq_font">
    		<%
    		TkBookInfo bookInfo = (TkBookInfo)request.getAttribute("bookInfo");
    		%>
        	<p>作业本：<%=bookInfo.getTitle() %><!-- （<%=Constants.getCodeTypevalue("version", bookInfo.getVersion()) %>） --></p>
        </div>
    </div>
    <logic:equal value="0" name="canDel">
    <div class="content_botton">
    	<a href="javascript:showtext()">
    		<div class="botton4">
    			<p>删除班级</p>
    		</div>
    	</a>
    </div>
    </logic:equal>
</div>
</form>
</body>
</html>