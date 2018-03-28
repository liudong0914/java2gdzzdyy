	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
<title>错题本</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">错题本</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
	
		<div id="container_list">	
		<%int i=0; %>
		<c:forEach items="${list }" var="q">
			<div class="container_10">
				<div class="container_9_size">
					<a href="/weixinTeacher.app?method=detaiQuestion&questionid=${q.questionid }&userid=${userid}&bookcontentid=${bookcontentid}&classid=${classid}&type=2" class="container_9_size_moble">
						<div class="conrainer_9_moble_span">
							<span><%i++ ;%><%=i %>.${q.titlecontent }</span>
						</div> <span><img src="/weixin/images/school_11.png"
							class="conrainer_9_size_moble_img" /> </span> </a>
				</div>
			</div>
	    </c:forEach>
	    <%if(i == 0){ %>
	    <div class="container_welcome">
	    	<div class="conrainer_welcome_font">
	        	<p>当前作业没有错题！</p>
	        </div>
	    </div>
	    <%} %>
		</div>
</body>
</html>
