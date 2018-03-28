<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>我的班级</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<style type="text/css">
.container_bj{ width:100%; height:100%; overflow:hidden; padding-bottom:74px; font-size:16px; }
.conrainer_4_bj{ float:left; width:65%; margin-left:18px; margin-top: 0px;}
.conrainer_4_bj_1{ font-size:16px; color:#000; line-height: 24px;}
.conrainer_4_bj_2{ font-size:14px; color:#c2c2c2; line-height:18px; }
.conrainer_4_bj_3{ font-size:16px; color:#000; line-height:24px; }
.conrainer_4_bj_a{ float:right; line-height:20px; margin-right:18px; display:block; width:62px; height:20px; font-size:14px; color:#fff; background-color:#b8d8cd; text-align:center; border-radius:3px; margin-top:9px; }
.conrainer_4_bj_a1{ line-height:20px; float:right; margin-right:18px; display:block; width:62px; height:20px; font-size:14px; color:#fff; margin-top:2px; background-color:#b8d8cd; text-align:center; border-radius:3px; }
.container_44{ width:100%; padding:10px 0px; overflow:hidden; border-bottom:1px solid #eee; border-top:1px solid #eee; background-color:#fff; margin-top:10px;height: auto; }
.container_3_size_bj{margin-top:50px;}
</style>
</head>

<body style="background-color:#f3f3f3;">
<div id="container_bj" style="padding-bottom:120px;">
    <%
	List list = (List)request.getAttribute("list");
	if(list != null && list.size() > 0){
		Object[] object = null;
		for(int i=0, size=list.size(); i<size; i++){
			object = (Object[])list.get(i);
	%>
    <div class="container_44">
    	<a href="/weixinTeacher.app?method=bookContent&userid=${userid }&bookid=<%=object[2] %>&classid=<%=object[0] %>">
        	<div class="conrainer_4_bj">
            	<p class="conrainer_4_bj_1"><%=object[3] %></p>
                <p class="conrainer_4_bj_2"><%=object[1] %></p>
            </div>
            <a class="conrainer_4_bj_a"><%=Constants.getCodeTypevalue("version", object[4].toString()) %></a>
        </a>
    </div>
    <%}}else{ %>
    <div class="container_44">
    	<div class="conrainer_4_bj" style="width:90%;">
        	<p class="conrainer_4_bj_3" style="color:#c2c2c2;font-size:14px;">还没有班级，赶快去创建班级吧。</p>
        </div>
    </div>
    <%} %>
</div>
<div <%if(list != null && list.size() > 3){ %>class="conrainer_fixed1"<%}else{ %> style="margin-top:-125px;"<%} %>>
	<div class="container_3">
		<a href="/weixinTeacher.app?method=createClass&userid=${userid }">
	    	<div class="container_3_size_bj" style="margin-top:-15px">
				<span>创建班级</span>
	    	</div>
	    </a>
	</div>
</div>
<div id="foot">
	<a href="/weixinAccountIndex.app?method=index&userid=${userid }" class="foot_1">
    	<img src="/weixin/images/footer01.png"/>
    	<p>首页</p>
    </a>
    <a href="/weixinAccountIndex.app?method=daohang&userid=${userid}" class="foot_1">
    	<img src="/weixin/images/footer02.png" />
    	<p>导航</p>
    </a>
    <a href="/weixinTeacher.app?method=classList&userid=${userid }" class="foot_1">
    	<img src="/weixin/images/ctb.png" />
    	<p>班级详情</p>
    </a>
    <a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}" class="foot_1">
    	<img src="/weixin/images/footer04.png" />
    	<p>个人中心</p>
    </a>
</div>
</body>
</html>