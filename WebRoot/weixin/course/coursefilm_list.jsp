<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.edu.bo.EduCourseColumn"%>
<%@page import="com.wkmk.edu.bo.EduCourseFilm"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>${eduCourseInfo.title }</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function postData(){
	document.pageForm.action = "/weixinCourse.app?method=courseFilmList&userid=${userid }&courseid=${courseid }";
	document.pageForm.submit();
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/dh.jsp"%>
<form name="pageForm" method="post">
<div class="search">
	<a href="/weixinCourse.app?method=getCourseList&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_input01">
        <div  class="search_input_01_main">
            <input type="search" placeholder="搜索关键字" name="keywords" value="${keywords }" style="color:#000;"/>  
            <a href="javascript:postData()"><img src="/weixin/images/icon04.png" /></a> 
        </div>
     </div>
    <a href="javascript:showDH()" ><div class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<!-----class_main-------->
<%
Boolean canStudy = (Boolean)request.getAttribute("canStudy");
List buyCoursefilmidList = (List)request.getAttribute("buyCoursefilmidList");
List columnList = (List)request.getAttribute("columnList");
%>
<%if(columnList != null && columnList.size() > 0){ %>
<div class="class_buy">
	<%
	Map courseFilmMap = (Map)request.getAttribute("courseFilmMap");
	
	List courseFilmList = null;
	EduCourseColumn column = null;
	EduCourseFilm film = null;
	String linkcourseid = null;
	String linkurl = null;
	for(int m=0, n=columnList.size(); m<n; m++){
		column = (EduCourseColumn)columnList.get(m);
		linkurl = column.getLinkurl();
		if(linkurl != null && !"".equals(linkurl) && linkurl.startsWith("/course/") && linkurl.endsWith(".htm")){
			linkcourseid = linkurl.substring(8, linkurl.lastIndexOf(".htm"));
		}
	%>
   <div class="class_buy_moudle">
   		<div class="class_buy_moudle_title">
            <p class="class_buy_moudle_title_p"><%=column.getFlags() %> - 
            <%if(linkurl != null && !"".equals(linkurl) && linkurl.startsWith("/course/") && linkurl.endsWith(".htm")){ %>
            <a href="/weixinCourse.app?method=courseFilmList&userid=${userid }&courseid=<%=linkcourseid %>" style="font-size:12px;color:#000;"><%=column.getTitle() %></a>
            <%}else{ %>
            <font style="font-size:12px;"><%=column.getTitle() %></font>
            <%} %>
            </p>
       </div>
       <%
       	courseFilmList = (List)courseFilmMap.get(column.getColumnid());
   		if(courseFilmList != null && courseFilmList.size() > 0){
   			for(int i=0, size=courseFilmList.size(); i<size; i++){
   				film = (EduCourseFilm)courseFilmList.get(i);
   	   %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p" style="padding:14px 0px 0px 0px;">
	                <p class="class_buy_moudle_main_p01"><%=film.getTitle() %></p>
	                <%if(!canStudy && !buyCoursefilmidList.contains(film.getCoursefilmid()) && film.getSellprice() > 0){ %>
	                <a href="/weixinCourse.app?method=buy&userid=${userid }&coursefilmid=<%=film.getCoursefilmid() %>">购买</a>
	                <%}else{ %>
	                <a href="/weixinCourse.app?method=play&userid=${userid }&coursefilmid=<%=film.getCoursefilmid() %>" style="background-color:#3cd5a2;">学习</a>
	                <%} %>
	            </div>
	       </div>
       <%}} %>
   <%} %>
   </div>
</div>
<%}else{ %>
<div class="class_buy">
   <div class="class_buy_moudle">
   	   <%
   		List courseFilmList = (List)request.getAttribute("coursefilmList");
   		EduCourseFilm film = null;
   		if(courseFilmList != null && courseFilmList.size() > 0){
   			for(int i=0, size=courseFilmList.size(); i<size; i++){
   				film = (EduCourseFilm)courseFilmList.get(i);
   	   %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p" style="padding:14px 0px 0px 0px;">
	                <p class="class_buy_moudle_main_p01"><%=film.getTitle() %></p>
	                <%if(!canStudy && !buyCoursefilmidList.contains(film.getCoursefilmid()) && film.getSellprice() > 0){ %>
	                <a href="/weixinCourse.app?method=buy&userid=${userid }&coursefilmid=<%=film.getCoursefilmid() %>">购买</a>
	                <%}else{ %>
	                <a href="/weixinCourse.app?method=play&userid=${userid }&coursefilmid=<%=film.getCoursefilmid() %>" style="background-color:#3cd5a2;">学习</a>
	                <%} %>
	            </div>
	       </div>
       <%}}else{ %>
       暂无数据！
       <%} %>
   </div>
</div>
<%} %>
</form>
</body>
</html>