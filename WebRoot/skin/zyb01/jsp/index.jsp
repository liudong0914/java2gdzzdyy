<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>进步学堂-官方网站</title>
<meta name="keywords" content="进步学堂 龙门书局 龙门 启东作业本 启东中学作业本 黄冈小状元 黄冈小状元作业本 黄冈小状元达标卷 黄冈小状元数学小秘招 龙门专题 龙门名师 蓝卡英语 龙门工具书 三点一测 秒杀小题 课时作业 北京龙腾八方文化有限责任公司" />
<meta name="description" content="进步学堂 龙门书局 龙门 启东作业本 启东中学作业本 黄冈小状元 黄冈小状元作业本 黄冈小状元达标卷 黄冈小状元数学小秘招 龙门专题 龙门名师 蓝卡英语 龙门工具书 三点一测 秒杀小题 课时作业 北京龙腾八方文化有限责任公司 龙门书局的主要产品有：中小学同步类学习辅导书，产品有讲解类、练习类、中高考类。其中《黄冈小状元》、《三点一测》、《状元笔记》、《启东中学作业本》、《课时作业》等系列图书年年增补、年年修订，年年出新，成为千千万万学生有口皆碑的品牌，也成为了教辅图书的经典之作。《黄冈小状元》系列图书上市十余年，好评如潮，累计发行数千万册，是小学练习类最具市场影响力的图书" />
<link type="text/css" rel="stylesheet" href="/skin/zyb01/css/style.css"/>
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
</head>

<body>
<%@ include file="top.jsp"%>

<div class="focus" id="focus">
	<div id="focus_m" class="focus_m">
		<ul>
			<li style="display:block;background:url('/skin/zyb01/images/1.jpg') center center no-repeat;"></li>
			<li style="display:none;background:url('/skin/zyb01/images/2.jpg') center center no-repeat;"></li>
			<li style="display:none;background:url('/skin/zyb01/images/3.jpg') center center no-repeat;"></li>
		</ul>
	</div>
	<a href="javascript:;" class="focus_l" id="focus_l" title="上一张"><b></b><span></span></a>
	<a href="javascript:;" class="focus_r" id="focus_r" title="下一张"><b></b><span></span></a>
</div>
<script type="text/javascript"  src="/skin/zyb01/js/index.js"></script>

<div class="listen">
	<div class="listen_title">    
        <div class="listen_line"></div>
        <div class="listen_p"><p>试听专享</p></div>
        <div class="listen_line02"></div>
    </div>
    <div class="listen_main">
    	<%
    	List filmlist = (List)request.getAttribute("filmlist");
    	Object[] obj = null;
    	VwhFilmInfo vfi = null;
    	for(int i=0, size=filmlist.size(); i<size; i++){
    		obj = (Object[])filmlist.get(i);
    		vfi = (VwhFilmInfo)obj[0];
    	%>
    	<a href="/zyb-play-1-<%=obj[2] %>.htm" target="_blank" title="<%=vfi.getTitle() %>"><div class="listen_main_module <%if(i%4 != 0){ %>listen_main_module01<%} %>">
        	<img src="/upload/<%=vfi.getSketch() %>" />
            <p><%=vfi.getTitle() %></p>
        </div></a>
        <%} %>
    </div>
    <div class="loading" id="loading" style="text-align:center;margin-bottom:15px;color:#c5c5c5;">
		<img src="/weixin/images/loading.gif" class="loadingimg"/>加载中
	</div>
</div>

<%@ include file="footer.jsp"%>
<script type="text/javascript">
var num = 1;
$(document).ready(function(){
	$(document).scroll(function(){
    	if($("#loading").is(":hidden")){
      		$("#loading").show();
      	}
    	var scrolltop = $(document).scrollTop();
        var dheight = $(document).height();
        var wheight = $(window).height();
        if(scrolltop >= dheight-wheight-100){
    		  $.ajax({
		        type:"post",
		        url:"/zyb.bo?method=getFilmAuditionByAjax",
		        data:"pagenum=" + num,
		        success:function(msg){
		        	if(msg != ""){
		        		$("#listen_main").append(msg);
		        	}else{
		        		$("#loading").html("数据已全部加载完!");
		        	}
     			}
 			});
    	  	num++;
      	}
	});
});
</script>
</body>
</html>