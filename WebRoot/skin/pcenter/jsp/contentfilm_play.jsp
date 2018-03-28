<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.vwh.bo.VwhFilmPix"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>个人中心</title>
<link type="text/css" rel="stylesheet" href="/skin/pcenter/css/style.css">
<script type="text/javascript" src="/skin/pcenter/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/ckplayer/js/offlights.js"></script>
<!--框架必需start-->

<script type="text/javascript" src="../../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<script>
$(document).ready(function(){
	var filmid = document.getElementById("filmid").value;
	var a = document.getElementById(filmid);
	a.style.color="#47d6aa";
	
});
function changePlay(imgpath,mp4path,obj){
	var newAddress = "{i->/upload/" + imgpath + "}{html5->http://${computerinfo.ip}:${computerinfo.port}/upload/" + mp4path + "->video/mp4}";
	CKobject.getObjectById('a1').newAddress(newAddress);
	document.getElementById("filmid").value =obj;
	
	//var title = document.getElementById("title"+obj).value;
	//document.getElementById("titleId").innerHTML= title;
	
	var filmid = document.getElementById("filmid").value;
	var a_filmid =null;
	var a_filmids = document.getElementsByName('a_filmid');
	for(var i=0;i<a_filmids.length;i++){
		a_filmid = a_filmids[i].value;
		if(a_filmid == filmid){
			var a = document.getElementById(a_filmid);
			a.style.color="#47d6aa";
		}else{
			var a = document.getElementById(a_filmid);
			a.style.color="#ccc";
		}
	 }
	 //切换视频时候，刷新播放次数	 
	 $.ajax({
        type:"post",
        url:"/weixinVod.app?method=getAjaxCommentHits",
        data:"filmid="+filmid+"&userid=${userid}&bookcontentid=${bookcontentid}&bookid=${bookid }&searchtype=${searchtype }&type=${type }",
        success:function(msg){
        	$("#hitsid").html(msg);
			}
	});
}
</script>
</head>
<body>
<!------头部-------->
<%@ include file="top.jsp"%>
<!------内容-------->
<div class="video">
	<div class="video_main">
    	<div class="video_main_left">
        	<!--  <p class="video_main_left_P" id="titleId">${title}</p>-->
				<div id="a1"></div>
					<%
					VwhFilmPix model = (VwhFilmPix)request.getAttribute("model"); 
					%>
					<script type="text/javascript" src="/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
					<script type="text/javascript">
						var flashvars={
							p:1,
							e:1,
							i:'/upload/${filmpix.imgpath}'
							};
						var video=['http://${computerinfo.ip}:${computerinfo.port}/upload/${filmpix.flvpath}->video/mp4'];
						var support=['all'];
						CKobject.embedHTML5('a1','ckplayer_a1','100%','100%',video,flashvars,support);
					</script>
        </div>
        <div class="video_main_menu">
        	<div class="video_main_menu_title">
            	<p class="video_main_menu_title_p">目录</p>
           
            </div>
            <div class="video_main_menu_font">
            	<c:forEach var="tbcf" items="${tkBookContentFilms}" varStatus="a"> 
            		<a href="#" id="${tbcf.filmid }" onclick="changePlay('${tbcf.flago}','${tbcf.flags}','${tbcf.filmid }')">0${a.index+1 }&nbsp;${tbcf.flagl }</a>
               		<input type="hidden"  name="a_filmid" id="${a.index }" value="${tbcf.filmid }"/>
               		<input type="hidden"  id="title${tbcf.filmid }" value="${tbcf.flagl }"/>
               	</c:forEach>
            </div>
        </div>
    </div>
</div>
<input type="hidden" name="filmid" id="filmid" value="${firstFilmid }"/>
<%@ include file="footer.jsp"%>
</body>
</html>
