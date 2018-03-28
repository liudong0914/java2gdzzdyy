<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkClassUpload"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>拍照上传</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/student_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinStudent.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">拍照上传</p>
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
    	<p>已经上传<%=count %>张图片，还可以上传<%=6-count %>张图片</p>
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
        <%if(count < 6){ %><a href="javascript:uploadImage()"><img src="/weixin/images/conrainer_photo_a.png" class="conrainer_photo_img"/></a><%} %>
    </div>
</div>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<input type="hidden" name="bookcontentid" value="${bookcontentid }"/>
<input type="hidden" name="bookid" value="${bookid }"/>
<input type="hidden" name="classid" value="${classid }"/>
<input type="hidden" name="userid" value="${userid }"/>
</form>
<script type="text/javascript" src="/libs/js/sk/tip.js"></script>
<script type="text/javascript">
function uploadImage(){
	wx.chooseImage({
	    count: <%=6-count %>,
	    sizeType: ['compressed'],//只要压缩图片
	    sourceType: ['album', 'camera'],
	    success: function (res) {
	    	showTipDiv("开始上传！", 120, 50, 5);
	        var localIds = res.localIds;
	        syncUpload(localIds);
	    }
	});
	var serverIds = "";
	var syncUpload = function(localIds){
		//移除数组中的最后一个元素并返回该元素,如果该数组为空，那么将返回 undefined
		var localId = localIds.pop();
	 	wx.uploadImage({
	  		localId: localId,
	  		isShowProgressTips: 1,
	  		success: function (res) {
	   			var serverId = res.serverId; // 返回图片的服务器端ID
	   			serverIds = serverIds + "," + serverId;
	   			//其他对serverId做处理的代码
	   			if(localIds && localIds.length > 0){
	    			syncUpload(localIds);
	   			}else{
	   				showTipDiv("上传完成！", 120, 50, 1);
	   				uploadPhoto(serverIds);
	   			}
	  		}
	 	});
	}
	var uploadPhoto = function(serverIds){
		document.pageForm.action = "/weixinStudent.app?method=uploadImage&serverIds=" + serverIds;
		document.pageForm.submit();
	}
}
function preview(imgurl){
	wx.previewImage({
	    current: imgurl, // 当前显示图片的http链接
	    urls: [${imgUrls }] // 需要预览的图片http链接列表
	});
}
</script>
</body>
</html>