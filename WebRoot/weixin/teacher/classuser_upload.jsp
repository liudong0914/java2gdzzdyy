<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkClassUpload"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>${student.username }-拍照作业</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=classUserList&bookcontentid=${bookcontentid }&classid=${classid}&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">作业拍照</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<form method="post" name="pageForm" >
<%
List list = (List)request.getAttribute("list");
int count = 0;
if(list != null && list.size() > 0){
	count = list.size();
}
%>
<div class="container_tips">
	<div class="conrainer_tips_font">
    	<p>[${student.username }]上传了<%=count %>张图片</p>
    </div>
</div>
<div id="container">
	<div class="conrainer_photo">
		<%
		TkClassUpload tcu = null;
		for(int i=0; i<count; i++){
			tcu = (TkClassUpload)list.get(i);
		%>
    	<img src="/upload/<%=tcu.getImgpath() %>" class="conrainer_photo_img" onclick="preview('<%=MpUtil.HOMEPAGE %>/upload/<%=tcu.getImgpath() %>')"/>
        <%}%>
    </div>
</div>
<input type="hidden" name="bookcontentid" value="${bookcontentid }"/>
<input type="hidden" name="classid" value="${classid }"/>
<input type="hidden" name="userid" value="${userid }"/>
</form>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script type="text/javascript">
function preview(imgurl){
	wx.previewImage({
	    current: imgurl, // 当前显示图片的http链接
	    urls: [${imgUrls }] // 需要预览的图片http链接列表
	});
}
</script>
</body>
</html>