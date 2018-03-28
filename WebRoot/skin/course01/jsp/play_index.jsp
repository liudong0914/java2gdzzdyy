<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.edu.bo.EduCourseColumn"%>
<%@page import="com.wkmk.edu.bo.EduCourseFilm"%>
<%@page import="com.wkmk.edu.bo.EduCourseFile"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${model.title }_课程学习_<%=Constants.PRODUCT_NAME %></title>
<script type="text/javascript" src="/skin/course/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/skin/course01/js/cryptojs/rollups/tripledes.js"></script>
<script type="text/javascript" src="/skin/course01/js/cryptojs/components/mode-ecb-min.js"></script>
<link href="/skin/course01/css/sytle.css" rel="stylesheet" type="text/css">
<script>
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		var con=document.getElementById("con_"+name+"_"+i);
		menu.className=i==cursel?"hover":"";
		con.style.display=i==cursel?"block":"none";
	}
}
/* function click(e) {
 if (document.all) {
 if (event.button==2||event.button==3) { 
oncontextmenu='return false';
 }
 }
 if (document.layers) {
 if (e.which == 3) {
 oncontextmenu='return false';
 }
 }
 }
 if (document.layers) {
 document.captureEvents(Event.MOUSEDOWN);
 }
 document.onmousedown=click;
 document.oncontextmenu = new Function("return false;") */

/* document.onkeydown =document.onkeyup = document.onkeypress=function(){ 
 if(window.event.keyCode == 123) { 
 window.event.returnValue=false;
 return(false); 
 } 
 } */

</script>
</head>
<%
String isOkDown = (String)request.getAttribute("isOkDown");
%>
<body style="background-color:#262628;">
	<div class="Course_play">
    	<a href="/course/${model.courseid }.htm" class="Course_play_a">&lt;&lt;返回课程</a>
        <div class="Course_play_bf">
        	<p class="Course_play_bf_p">${eduCourseFilm.title }</p>
            <div class="Course_play_bf_main">
            	<%
            	Boolean canStudy = (Boolean)request.getAttribute("canStudy");
            	if(canStudy){
            	%>
            	<div style="width:100%; height:100%; background-color:#fff;">
					<script type="text/javascript" src="/ckplayerX/ckplayer/ckplayer.js" charset="utf-8"></script>
            		<div id="a1" style="width:100%; height:500px;"></div>
            		
            		<script type="text/javascript">
							var videoObject = {
								container: '#a1',
								variable: 'player',
								debug:true,
								flashplayer:true,
								video:'CE:${playurl}'
							};
							var player=new ckplayer(videoObject);
						</script>
            	</div>
            	<%}else{ %>
            	<div class="Code">
                	<p>打开手机微信客户端右上角+，扫描二维码，在线支付购买学习课程。</p>
                    <img src="/upload/twocode/course/film/${eduCourseFilm.qrcodeno }.png" />
                </div>
            	<%} %>
                <p class="Course_play_bf_main_p"></p>
                <div class="Course_play_bf_bottom">
                    <div class="Course_play_bf_bottom_star">
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
            </div><!-------Course_play_bf_main--e---------->  
        </div><!-------Course_play_bf--e---------->
        <div class="Course_play_list">	
            <div id="Tab1">
                <div class="Course_play_list_ul">
                    <ul>
                    <li id="one1" onclick="setTab('one',1,2)"  class="hover">课程章节</li>
                    <!-- <li id="one2" onclick="setTab('one',2,2)" >资料下载</li> -->
                    </ul>
                </div>
                <div class="Course_play_list_font">
                    <div id="con_one_1" class="hover">
                    	<%
                    	EduCourseFilm eduCourseFilm = (EduCourseFilm)request.getAttribute("eduCourseFilm");
						List columnList = (List)request.getAttribute("columnList");
						Map courseFilmMap = (Map)request.getAttribute("courseFilmMap");
						List courseFilmList = null;
						EduCourseColumn column = null;
						EduCourseFilm film = null;
						for(int i=0, size=columnList.size(); i<size; i++){
							column = (EduCourseColumn)columnList.get(i);
						%>
                    	<div class="Course_play_list_font_title">
                    		<%if(column.getFlags() !=""){ %>
                    			<p class="Course_play_list_font_title_p01"><%=column.getFlags() %></p>
	                            <p class="Course_play_list_font_title_p02">
	                            <%if(column.getLinkurl() != null && !"".equals(column.getLinkurl())){ %>
						        <a href="<%=column.getLinkurl() %>" target="_blank" style="color:#fff;"><%=column.getTitle() %></a>
						        <%}else{ %>
						        <%=column.getTitle() %>
						        <%} %>
	                            </p>
                            <%}else{ %>
                            	<p class="Course_play_list_font_title_p01"><%=column.getTitle() %></p>
                            <%} %>
                        </div>
                        <%
						courseFilmList = (List)courseFilmMap.get(column.getColumnid());
						if(courseFilmList != null && courseFilmList.size() > 0){
							for(int m=0, n=courseFilmList.size(); m<n; m++){
								film = (EduCourseFilm)courseFilmList.get(m);
						%>
                    	<a href="/course-play-${model.courseid }-<%=film.getCoursefilmid() %>.htm"><div class="Course_play_list_font_div <%if(eduCourseFilm.getCoursefilmid().intValue() == film.getCoursefilmid().intValue()){ %>hover<%} %>">
                        	<p class="Course_play_list_font_div_p"><%=m+1 %>. <%=film.getTitle() %></p>
                        </div></a>
                        <%}} %>
    					<%} %>
                    </div>
                    <div id="con_one_2" style="display:none">
                    	<%
						Map courseFileMap = (Map)request.getAttribute("courseFileMap");
						List courseFileList = null;
						EduCourseFile file = null;
						for(int i=0, size=columnList.size(); i<size; i++){
							column = (EduCourseColumn)columnList.get(i);
						%>
                    	<div class="Course_play_list_font_title">
                    		<p class="Course_play_list_font_title_p01"><%=column.getFlags() %></p>
                            <p class="Course_play_list_font_title_p02"><%=column.getTitle() %></p>
                        </div>
                        <%
                        courseFileList = (List)courseFileMap.get(column.getColumnid());
						if(courseFileList != null && courseFileList.size() > 0){
							for(int m=0, n=courseFileList.size(); m<n; m++){
								file = (EduCourseFile)courseFileList.get(m);
								if("1".equals(isOkDown)){
						%>
	                    	<a href="/course-download-<%=file.getCourseid() %>-<%=file.getFileid() %>.htm" target="_blank" title="点击下载"><div class="Course_play_list_font_div">
	                        	<p class="Course_play_list_font_div_p">[<%=Constants.getCodeTypevalue("coursefiletype", file.getCoursefiletype()) %>] <%=file.getFilename() %></p>
	                        </div></a>
                        <%}else{ %>
	                        <a href="/eduCourseFileAction.do?method=preview&fileid=<%=file.getFileid() %>"  target="_blank" title="在线预览"><div class="Course_play_list_font_div">
	                        	<p class="Course_play_list_font_div_p">[<%=Constants.getCodeTypevalue("coursefiletype", file.getCoursefiletype()) %>] <%=file.getFilename() %></p>
	                        </div></a>
                        <%} %>
                        <%}} %>
    					<%} %>
                    </div>
                </div>
            </div>
        </div>
    </div><!-------Course_play--e---------->
</body>
</html>
