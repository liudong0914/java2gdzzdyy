<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.edu.bo.EduCourseColumn"%>
<%@page import="com.wkmk.edu.bo.EduCourseFilm"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/skin/course01/css/sytle.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/libs/js/jquery.js"></script>
<script type="text/javascript">
function startStudy(courseid, coursefilmid){
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
		window.top.location = '/course-play-' + courseid + '-' + coursefilmid + '.htm'; 
	}else{
		var topurl = window.top.location;
		window.top.location = '/plogin.do?method=slogin&redirecturl=' + topurl;
	}
	return;
}
</script>
</head>

<body style="background:#fff;">
<div>
	<%
	List parentList = (List)request.getAttribute("parentList");
	Map childMap = (Map)request.getAttribute("childMap");
	Map courseFilmMap = (Map)request.getAttribute("courseFilmMap");
	List childList = null;
	List courseFilmList = null;
	EduCourseColumn column = null;
	EduCourseFilm film = null;
	for(int i=0, size=parentList.size(); i<size; i++){
		column = (EduCourseColumn)parentList.get(i);
	%>
	<div class="content_main_left_font_title">
    	<p class="content_main_left_font_titlp01"><%=column.getTitle() %></p>
    </div>	
    	
    	 <%
	        childList = (List)childMap.get(column.getColumnno());
	        if(childList != null && childList.size() > 0){
			for(int j=0, k=childList.size(); j<k; j++){
				column = (EduCourseColumn)childList.get(j);
   	   	%>
	        <div class="content_main_left_font_title02">
	        	<p class="content_main_left_font_title02_p01">
	        		<%if(column.getLinkurl() != null && !"".equals(column.getLinkurl())){ %>
			        <a href="<%=column.getLinkurl() %>" target="_blank"><%=column.getTitle() %></a>
			        <%}else{ %>
			        <%=column.getTitle() %>
			        <%} %>
	        	</p>
	        </div>
			<%
			courseFilmList = (List)courseFilmMap.get(column.getColumnid());
			if(courseFilmList != null && courseFilmList.size() > 0){
				for(int m=0, n=courseFilmList.size(); m<n; m++){
					film = (EduCourseFilm)courseFilmList.get(m);
			%>
			<div class="content_main_left_font_list<%if(m%2 != 0){ %>01<%} %>">
				<div class="content_main_left_font_list_div<%if(m%2 != 0){ %>01<%} %>">
			    	<a><p class="content_main_left_font_list_p<%if(m%2 != 0){ %>00<%} %>"><%=m+1 %></p></a>
			        <a><p class="content_main_left_font_list_p01"><%=film.getTitle() %></p></a>
			        <a href="javascript:startStudy('<%=film.getCourseid() %>', '<%=film.getCoursefilmid() %>')" class="content_main_left_font_list_a">开始学习</a>
		    	</div>
		    </div>
		    <%}} %>
    	<%}}else{ %>
    		<%
			courseFilmList = (List)courseFilmMap.get(column.getColumnid());
			if(courseFilmList != null && courseFilmList.size() > 0){
				for(int m=0, n=courseFilmList.size(); m<n; m++){
					film = (EduCourseFilm)courseFilmList.get(m);
			%>
			<div class="content_main_left_font_list<%if(m%2 != 0){ %>01<%} %>">
				<div class="content_main_left_font_list_div<%if(m%2 != 0){ %>01<%} %>">
			    	<a><p class="content_main_left_font_list_p<%if(m%2 != 0){ %>00<%} %>"><%=m+1 %></p></a>
			        <a><p class="content_main_left_font_list_p01"><%=film.getTitle() %></p></a>
			        <a href="javascript:startStudy('<%=film.getCourseid() %>', '<%=film.getCoursefilmid() %>')" class="content_main_left_font_list_a">开始学习</a>
		    	</div>
		    </div>
		    <%}} %>
    	<%} %>
    <%} %>
</div>
</body>
</html>