<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@page import="com.wkmk.edu.bo.EduCourseFilm"%>
<%@page import="com.wkmk.edu.bo.EduCourseUser"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>解题微课</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script type="text/javascript">
$(document).scroll(function(){
   	if($("#loading").is(":hidden")){
   		$("#loading").show();
   	}
   	var scrolltop = $(document).scrollTop();
    var dheight = $(document).height();
    var wheight = $(window).height();
    if(scrolltop >= dheight-wheight){
    var keywords = document.getElementById("keywords").value;
    var startcount = document.getElementById("startcount").value;
	$.ajax( { 
		 url:'/weixinVod.app?method=search',// 跳转到 action  
		 data:'userid=${userid}&keywords='+keywords+'&startcount='+startcount,  
		 type:'post',  
		 dataType:'json',
		 cache:false,  
		 success:function(data) {
			 if(data != null){
				 if(data.hasNextPage == "1"){
					document.getElementById("sp_1").style.display = "none";
					document.getElementById("sp_2").style.display = "";
				 }else{
					document.getElementById("sp_1").style.display = "";
					document.getElementById("sp_2").style.display = "none";
				 }
				 document.getElementById("startcount").value = data.startcount;
				 if(data.datalist == "暂无数据！"){
				 	document.getElementById("sp_1").style.display = "none";
					document.getElementById("sp_2").style.display = "";
				 }else{
				 	$("#mainId").append(data.datalist);
				 }
			 }
		 },
         error: function(xhr, type){
        	 
         }
		});
	      	}
		});
function postData(){
	var keywords = document.getElementById("keywords").value;
	if(keywords == ""){
		alert("请输入搜索关键字!");
		return;
	}
 	document.getElementById("startcount").value="0";
 	var startcount = document.getElementById("startcount").value;
	document.pageForm.action = "/weixinCourse.app?method=toSearch&userid=${userid }&startcount="+startcount;
	document.pageForm.submit();
}
function changeType(typeid){
	document.getElementById("searchtype").value = typeid;
	postData();
}
function search_onkeydown(evt){
	evt = (evt) ? evt : ((window.event) ? window.event : "");
  	keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
  	if (keyCode == 13){    
  		postData();
  		return false;
  	}  
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/dh.jsp"%>
<form name="pageForm" method="post">
<div class="search">
	<a href="/weixinVod.app?method=inSearchIndex&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_input01">
        <div  class="search_input_01_main">
            <input type="search" placeholder="搜索关键字" id="keywords"  name="keywords" value="${keywords }" onKeyPress="return search_onkeydown(event)" style="color:#000;"/>  
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
String searchtype = (String)request.getAttribute("searchtype");
List filmList = (List)request.getAttribute("filmList");
List buycontentidList = (List)request.getAttribute("buycontentidList");
VwhFilmInfo vfi = null;
EduCourseFilm ecf = null;
%>
<div class="class_buy">
   <div class="class_buy_moudle" id="mainId">
   	  <%
   		String temp = "0";
   		if(filmList != null && filmList.size() > 0){
   			for(int i=0, size=filmList.size(); i<size; i++){
   			    Object[] object = (Object[])filmList.get(i);
   			    ecf = (EduCourseFilm)object[0];
   				vfi = (VwhFilmInfo)object[1];
   				
   	   %>
   	   <%if("0".equals(searchtype)){ temp= "1";%>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=vfi.getTitle() %></p>
	                <%if(!buycontentidList.contains(vfi.getFilmid())){ %>
	                	<a href="/weixinCourse.app?method=buy&userid=${userid }&coursefilmid=<%=ecf.getCoursefilmid() %>&searchtype=${searchtype }">购买</a>
	                <%} %>
	            </div>
	            <%if(!buycontentidList.contains(vfi.getFilmid())){ %>
	            <a class="class_buy_moudle_main_a03">学习</a>
	            <%}else{ %>
	            <a href="/weixinCourse.app?method=play&userid=${userid }&coursefilmid=<%=ecf.getCoursefilmid() %>&searchtype=${searchtype }" class="class_buy_moudle_main_a01">学习</a>
	            <%} %>
	       </div>
       <%}else if("1".equals(searchtype)){ %>
	        <%if(buycontentidList.contains(vfi.getFilmid())){temp= "1"; %>
	        	 <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
		       		<div class="class_buy_moudle_main_p">
		                <p class="class_buy_moudle_main_p01"><%=vfi.getTitle() %></p>
		            </div>
		            <a href="/weixinCourse.app?method=play&userid=${userid }&coursefilmid=<%=ecf.getCoursefilmid() %>&searchtype=${searchtype }" class="class_buy_moudle_main_a01">学习</a>
		       </div>
	        <%}%>
       <%}else if("2".equals(searchtype)){ %>
	       	<%if(!buycontentidList.contains(vfi.getFilmid())){temp= "1"; %>
	        	<div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=vfi.getTitle() %></p>
	                <a href="/weixinCourse.app?method=buy&userid=${userid }&coursefilmid=<%=ecf.getCoursefilmid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	       		</div>
	        <%}%>
       <%} %>
       <%}%>
       <%if("0".equals(temp)){ %>暂无数据！<%} %>
   	   <%}else{ %>
       暂无数据！
       <%} %>
   </div>
</div>
   </div>
</div>
<div class="loading" id="loading">
	<span id="sp_1"><img src="/weixin/images/loading.gif" class="loadingimg"/>加载中</span>
	<span id="sp_2" style="display:none;">数据已全部加载完!</span>
</div>
<input type="hidden" name="searchtype" id="searchtype" value="${searchtype }"/>
<input type="hidden" name="startcount" id="startcount" value="<bean:write name="startcount" />" />
</form>
</body>
</html>