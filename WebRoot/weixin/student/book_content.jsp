<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.wkmk.tk.bo.TkBookContent"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>作业本内容</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=student&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">作业本内容</p>
    </div>
</div>
<div class="search_padding"></div>
<div id="container_list">
	<%
	String uploadImg = (String)request.getAttribute("uploadImg");
	List parentList = (List)request.getAttribute("parentList");
	Map childMap = (Map)request.getAttribute("childMap");
	if(parentList != null && parentList.size() > 0){
	List bookcontentidList = (List)request.getAttribute("bookcontentidList");
	List childList = null;
	TkBookContent tbc = null;
	for(int i=0, size=parentList.size(); i<size; i++){
		tbc = (TkBookContent)parentList.get(i);
	%>
	<div class="container_7">
    	<div class="container_7_size">
			<span><%=tbc.getTitle() %></span>
        </div>
    </div>
    <%
    childList = (List)childMap.get(tbc.getContentno());
    if(childList != null && childList.size() > 0){
    	for(int m=0, n=childList.size(); m<n; m++){
    		tbc = (TkBookContent)childList.get(m);
    %>
    <div class="container_8">
    	<div class="container_8_size">
            <a href="/weixinStudent.app?method=lianxi&userid=${userid }&bookcontentid=<%=tbc.getBookcontentid() %>&bookid=<%=tbc.getBookid() %>&classid=${classid }" class="container_7_size_moble">
           		<div class="conrainer_7_moble_span">
            		<span><%if(bookcontentidList.contains(tbc.getBookcontentid())){ %><span style="color:#ff3333;font-size:8px;">[完成]</span><%} %><%=tbc.getTitle() %></span>
                </div>
                <span><img src="/weixin/images/school_11.png" class="conrainer_7_size_moble_img school_img" /></span>
            </a>
            <div class="container_7_size_font" style="margin:0 auto;">
            	<a href="/weixinStudent.app?method=doBeforeClass&userid=${userid }&bookcontentid=<%=tbc.getBookcontentid() %>&classid=${classid }&index=1&next=1" class="container_7_size_a">预习作业</a>
            	<%if("1".equals(uploadImg)){ %>
            	<a href="/weixinStudent.app?method=classUploadImage&userid=${userid }&bookcontentid=<%=tbc.getBookcontentid() %>&bookid=<%=tbc.getBookid() %>&classid=${classid }" class="container_7_size_a">拍照上传</a>
            	<%}else{ %>
            	<a href="/weixinStudent.app?method=lianxi&userid=${userid }&bookcontentid=<%=tbc.getBookcontentid() %>&bookid=<%=tbc.getBookid() %>&classid=${classid }" class="container_7_size_a">课时作业</a>
            	<%} %>
            	<a href="/weixinStudent.app?method=lianxi&userid=${userid }&bookcontentid=<%=tbc.getBookcontentid() %>&bookid=<%=tbc.getBookid() %>&classid=${classid }&answercard=1" class="container_7_size_a">填答题卡</a>
            	<a href="/weixinStudent.app?method=errorQuestion&userid=${userid }&bookcontentid=<%=tbc.getBookcontentid() %>&classid=${classid }&bookid=<%=tbc.getBookid() %>" class="container_7_size_a">错题本</a>
            </div>
        </div>
    </div>
    <%}} %>
    <%}}else{ %>
    <div class="container_welcome">
		<div class="conrainer_welcome_font">
	    	<p>当前作业本没有内容！</p>
	    </div>
	</div>
    <%} %>
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
    <%
    String isAnswer = (String)request.getAttribute("isAnswer");
    if("1".equals(isAnswer)){
    %>
	<a href="/weixinStudent.app?method=getUserErrQuestionByClassBook&userid=${userid }&bookid=${bookid}&classid=${classid}" class="foot_1">
    	<img src="/weixin/images/ctb.png" />
    	<p>错题本</p>
    </a>
    <%}else{ %>
    <a href="/weixinStudent.app?method=delBook&userid=${userid }&bookid=${bookid}&classid=${classid}" class="foot_1">
    	<img src="/weixin/images/bz.png" />
    	<p>取消绑定</p>
    </a>
    <%} %>
    <a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}" class="foot_1">
    	<img src="/weixin/images/footer04.png" />
    	<p>个人中心</p>
    </a>
</div>
</body>
</html>