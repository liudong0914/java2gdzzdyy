<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${model.title }_<%=Constants.PRODUCT_NAME %></title>
<link href="/skin/course01/css/sytle.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/libs/js/jquery.js"></script>
<script type="text/javascript" src="/libs/js/sk/iframe.js"></script>
<style>
	body{ min-width:1200px;}
</style>
<script type="text/javascript">
function showTeacherDescript(){
	document.getElementById("teacher_descript").style.height = "auto";
	document.getElementById("show_button").style.display = "none";
}
function toUrl(url,cursel,size){
	for(var i=1; i<=size; i++){
		var menu = document.getElementById("ctab_" + i);
		menu.className=i==cursel?"hover":"";
	}
	document.getElementById("leftFrame").src = url;
}
function collect(id){
	var temp = '';
	$.ajax({
		url: "/plogin.do?method=checkLogin",
	    data: "ram=" + Math.random(),
	    async: false,
	    success: function(msg){
	    	temp = msg;
	   	}
	 });
	if(temp == '1'){
		$.ajax({
		   url: "/course.bo?method=collects",
		   data: "courseid=${model.courseid }&ram=" + Math.random(),
		   success: function(msg){
		   	 if('-1' == msg){
		   	 	alert("您已收藏过当前课程！");
		   	 }else{
		   	    $("#"+id).text(msg);
		   	    alert("课程收藏成功！");
		   	 }
		   }
		}); 
	}else{
		alert("请您先登录再收藏当前课程!");
	}
	return;
}
function startStudy(){
	var temp = '';
	$.ajax({
		url: "/plogin.do?method=checkLogin",
	    data: "ram=" + Math.random(),
	    async: false,
	    success: function(msg){
	    	temp = msg;
	   	}
	 });
	if(temp == '1'){
		window.top.location = '/course-play-${model.courseid }-0.htm';
	}else{
		login();
	}
	return;
}
</script>
</head>

<body>
<%@ include file="top.jsp"%>

<div class="content">
	<div class="content_class">
    	<img src="/upload/${model.sketch }" class="content_class_img" />
        <div class="content_class_font">
        	<p class="content_class_font_p">${model.title }</p>
            <div class="content_class_font_fx" style="padding-top:15px;padding-bottom:0px;">
            	<p>共${model.classhour }课时</p>
                <p class="content_class_font_fx_p">${students }人学习</p>
                <div class="content_class_font_fx_a" style="width:120px;margin-left:30px;">
                	<a href="javascript:collect('collect_div')" class="content_class_font_fx_a02" style="width:120px;">收藏（<font id="collect_div">${model.collects }</font>）</a>
                </div>
            </div>
            <div class="content_class_font_fx content_class_font_fx01">
            	<div class="content_class_font_fx_star">
            		<%
            		EduCourseInfo model = (EduCourseInfo)request.getAttribute("model");
            		for(int i=1; i<=5; i++){
	            		if(model.getScore() <= (i-1)){
	            	%>
                	<img src="/skin/course01/images/a10.png" />
                	<%}else if(model.getScore() >= i){ %>
                	<img src="/skin/course01/images/a05.png" />
                	<%}else{ %>
                	<img src="/skin/course01/images/a09.png" />
                	<%}} %>
                </div>
            	<p>${model.scoreusers }人评</p>
            </div>
            <%if(model.getPrice() > 0){ %>
            <p style="background:#fafafa;height:40px;line-height:40px;padding-left:10px;font-size:16px;margin-top:5px;">价格：<font style="font-size:18px;font-weight:bold;color:#ff0000;">¥ ${model.price }<%if(!"".equals(model.getNote()) && model.getNote() != null){ %>(${model.note })<%} %></font></p>
            <%}else{ %>
            <p style="background:#fafafa;height:40px;line-height:40px;padding-left:10px;font-size:16px;margin-top:5px;">价格：<font style="font-size:18px;font-weight:bold;color:#96e286;">免费</font></p>
            <%} %>
            <a href="javascript:startStudy()" class="content_class_font_a">开始学习</a>
        </div><!------content_class_font-e------>
    </div><!------content_class-e------>
    <div class="content_main">
    	<div class="content_main_left">
        	<div id="Tab5">
                <div class="content_main_left_ul">
                    <ul>
                    <li id="ctab_1" onclick="toUrl('/course.bo?method=coursedescript&courseid=${model.courseid }', 1, 3)">课程介绍</li>
                    <li id="ctab_2" onclick="toUrl('/course.bo?method=coursecolumn&courseid=${model.courseid }', 2, 3)" class="hover">课程目录</li>
                    <li id="ctab_3" onclick="toUrl('/course.bo?method=coursecomment&courseid=${model.courseid }', 3, 3)">课程评价</li>
                    </ul>
                </div>
                <div class="content_main_left_font">
                    <iframe id="leftFrame" name="leftFrame" width="100%" height="100" onload="SetCwinHeight('leftFrame', 100)" frameborder="0" marginheight="0" marginwidth="0" scrolling=no src="/course.bo?method=coursecolumn&courseid=${model.courseid }" allowTransparency="true"></iframe>
                </div><!------content_main_left_font-e------>
            </div><!------Tab5-e------>
        </div><!------content_main_left-e------>
        <div class="content_main_right">
        	<%
        	SysUserInfo teacher = model.getSysUserInfo();
        	String photo = teacher.getPhoto();
        	if(!photo.startsWith("http://")){
        		photo = "/upload/" + photo;
        	}
        	%>
        	<p class="content_main_right_title">讲师介绍</p>
            <div class="content_main_right_main">
                <img src="<%=photo %>" class="content_main_right_main_img" />
                <a class="content_main_right_main_name"><%=teacher.getUsername() %></a>
                <p class="content_main_right_main_p" style="text-align:center;">${detail.thetitle }</p>
                <%-- <p class="content_main_right_main_p" style="text-align:center;">${detail.major }</p> --%>
                <p class="content_main_right_main_p" style="height:200px;overflow:hidden;" id="teacher_descript">${detail.descript }</p>
                <a href="javascript:showTeacherDescript()" id="show_button" class="content_main_right_main_more" title="点击查看更多"><img src="/skin/course01/images/a14.png" /></a>
            </div>
        </div><!------content_main_right-e------>
    </div><!------content_main-e------>
</div><!------content-e------>
<!------中间内容结束------->

<%@ include file="footer.jsp"%>
</body>
</html>