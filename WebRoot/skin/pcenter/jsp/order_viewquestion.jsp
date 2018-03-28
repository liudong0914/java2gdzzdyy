<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.weixin.mp.MpUtil"%>
<%@page import="java.util.List"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.wkmk.zx.bo.ZxHelpOrder"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestionComplaint"%>
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>个人中心</title>
<link type="text/css" rel="stylesheet" href="/skin/pcenter/css/style.css">
<script type="text/javascript" src="/skin/pcenter/js/jquery-1.8.2.min.js"></script>

<!--框架必需start-->
<script type="text/javascript" src="../../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<script type="text/javascript" src="/skin/course01/js/cryptojs/rollups/tripledes.js"></script>
<script type="text/javascript" src="/skin/course01/js/cryptojs/components/mode-ecb-min.js"></script>
<!-- 表单验证start -->
<script src="../../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="../../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%@ include file="js.jsp"%>
</head>
<body style="background-color:#f9f9f9;">
<!------头部-------->
<%@ include file="top.jsp"%>
<!------内容-------->
<form name="pageForm" method="post">
<div class="personal">
	<%@ include file="left.jsp" %>
    <div class="information_right">
    	<div class="information_right_top">
			<p>答疑详情</p>
        </div>
        <div class="question">
            <div class="answer_student" style="padding-bottom:15px;">
                <div class="answer_student_top">
                    <img src="/skin/pcenter/images/img.jpg"/>
                    <p>${model.flago }</p>
                    	<p class="answer_student_top_p">${model.flagl }前</p>
                </div>
                <div class="answer_student_font">
                	<c:if test="${zxHelpOrder.status == '1'}">
                    		<p class="answer_student_font_p">
								未回复
							</p>
						</c:if>
						<c:if test="${zxHelpOrder.status == '2'}">
							<c:if test="${zxHelpOrder.status == '2'}">
							<p class="answer_student_font_p answer_student_font_p01">
								已回复
							</p>
							</c:if>
						</c:if>
						<c:if test="${zxHelpOrder.status == '3'}">
							<p class="answer_student_font_p answer_student_font_p01" style="background-color: #ccc !important;">
								已超时
							</p>
						</c:if>
                    <p class="answer_student_font_pright1" style="margin-top:3px;">${model.title }</p>
                </div>
                <p class="answer_student_p">${model.descript }</p>
                <div  class="wk_money">
                    <p class="wk_money_p01" style="margin-top:4px;">¥</p>
                    <p class="wk_money_p" style="margin-top:2px;">${model.money }</p>
                    <p class="answer_student_p_teacher answer_student_p_teacher01">回答时限：${model.flags }</p>
                </div>
           </div>
           <div class="detail">
           <%
           	List files = (List)request.getAttribute("files");
           List videoFiles = (List)request.getAttribute("videoFiles");
      		List list2 = (List)request.getAttribute("list2");//录音
      		List list1 = (List)request.getAttribute("list1");//图片
      		
      		List files_order = (List)request.getAttribute("files_order");
      		List videoFilesOrder = (List)request.getAttribute("videoFilesOrder");
       		List list4 = (List)request.getAttribute("list4");//录音
       		List list3 = (List)request.getAttribute("list3");//图片
       		
       		ZxHelpQuestionComplaint zxHelpQuestionComplaint = (ZxHelpQuestionComplaint)request.getAttribute("zxHelpQuestionComplaint");
           %>
           
           <%
           	if((files !=null && files.size()>0)||(videoFiles !=null && videoFiles.size()>0)){
           %>
           <div class="answer_student" style="padding-bottom:18px;padding-top: 0px;">
           	 	<%
           		if(files !=null && files.size()>0){
           		%>
                <p class="detail_title">提问语音与图片</p>
                <%if(list2 !=null && list2.size()>0){ %>
                <div class="answer_student_ygm_yy">
	                   <%
						
						if(list2 !=null && list2.size()>0){
						    for(int i=0;i<list2.size();i++){
						        ZxHelpFile file =(ZxHelpFile)list2.get(i);
					%>
						<audio id="myaudio" src="/upload/<%=file.getFlago() %>" controls="controls" style="margin:auto;padding-top: 10px;">
						</audio><br/>
					<%}}else{ %>
						暂无录音
					<%} %>
                </div>
                <%} %>
                <%if(list1 !=null && list1.size()>0){ %>
                <div class="detail_img" style="padding-top: 5px;">
                	<%
						if(list1 !=null && list1.size()>0){
				    		for(int i=0;i<list1.size();i++){
				        		ZxHelpFile file =(ZxHelpFile)list1.get(i);
					%>
						<img  src="/upload/<%=file.getThumbpath() %>" alt="<%=file.getFlagl() %>" width="200px" height="150px" onclick="javascript:window.open('/upload/<%=file.getFlago() %>');"/>
					<%}}else{ %>
						暂无图片
					<%} %>
                </div>
                <%} %>
                <%} %>
                
                <% 
           		if(videoFiles !=null && videoFiles.size()>0){
           		%>
           		<p class="detail_title" style="padding-top: 16px;">提问视频</p>
           		<div class="answer_student_ygm_yy">
				        <script type="text/javascript" src="/ckplayerX/ckplayer/ckplayer.js" charset="utf-8"></script>
				        <%
				        for(int i=0, size=videoFiles.size(); i<size; i++){
				            ZxHelpFile file = (ZxHelpFile)videoFiles.get(i);
				        %>
				        <div id="a<%=i+1 %>"  style="width:100%; height:100%;"></div></br>
						<script type="text/javascript">
						var video1= '<%=MpUtil.HOMEPAGE %>/upload/<%=file.getMp4path() %>';
							var video2 = textDes(video1);
							var videoObject = {
									container: '#a<%=i+1 %>',
									variable: 'player',
									flashplayer:true,
									video:video2
								};
							var player=new ckplayer(videoObject);
						</script>
						<%} %>
           		</div>
           		<%} %>
           	</div>
             <%} %>   
           		
	           
	            <logic:notEmpty name="content">
                 <div class="answer_student_font" style="padding-bottom:0px;">
                    <p class="detail_title">
                    	回复内容
                    </p>
                    <p class="answer_student_font_pright1"></p>
                </div>
                <p class="answer_student_p">${content}</p>
                </logic:notEmpty>
	           
	            <%
           			if((files_order !=null && files_order.size()>0)||(videoFilesOrder !=null && videoFilesOrder.size()>0)){
           		%>
           		<div class="answer_student" style="padding-bottom:18px;padding-top: 0px;">
		           <%
	           		if(files_order !=null && files_order.size()>0){
	           		%>	
	                <p class="detail_title" style="padding-top: 20px;">回复语音与图片</p>
	                <%if(list4 !=null && list4.size()>0){ %>
	                <div class="answer_student_ygm_yy">
		                   <%
							if(list4 !=null && list4.size()>0){
							    for(int i=0;i<list4.size();i++){
							        ZxHelpFile file =(ZxHelpFile)list4.get(i);
							%>
							<audio id="myaudio" src="/upload/<%=file.getFlago() %>" controls="controls" style="margin:auto;padding-top: 10px;">
							</audio><br/>
							<%}}else{ %>
							暂无录音
							<%} %>
	                </div>
	                 <%} %>
	                <%if(list3 !=null && list3.size()>0){ %>
	                <div class="detail_img" style="padding-top: 10px;">
	                	<%
						if(list3 !=null && list3.size()>0){
						    for(int i=0;i<list3.size();i++){
						        ZxHelpFile file =(ZxHelpFile)list3.get(i);
						%>
							<img  src="/upload/<%=file.getThumbpath() %>" alt="<%=file.getFlagl() %>" width="200px" height="150px" onclick="javascript:window.open('/upload/<%=file.getFlago() %>');"/>
						<%}}else{ %>
						暂无图片
						<%} %>
	                </div>
	                 <%} %>
	                <%} %>
		           
	           		<% 
	           		if(videoFilesOrder !=null && videoFilesOrder.size()>0){
	           		%>
	                <p class="detail_title" style="padding-top: 16px;">回复视频</p>
	           		<div class="answer_student_ygm_yy">
	           				<script type="text/javascript" src="/ckplayerX/ckplayer/ckplayer.js" charset="utf-8"></script>
					        <%
					        for(int i=0, size=videoFilesOrder.size(); i<size; i++){
					            ZxHelpFile file = (ZxHelpFile)videoFilesOrder.get(i);
					        %>
					        <div id="a_<%=i+1 %>"></div></br>
							<script type="text/javascript">
							var video1= '<%=MpUtil.HOMEPAGE %>/upload/<%=file.getMp4path() %>';
							
							var video2 = textDes(video1);
							var videoObject = {
								container: '#a<%=i+1 %>',
								variable: 'player',
								debug:true,
								flashplayer:true,
								video:video2
							};
							var player=new ckplayer(videoObject);
							</script>
							<%} %>
	           		</div>
		           	<%} %>
           		</div>
           		<%} %>
           		
           		 <%
		           if(zxHelpQuestionComplaint !=null){
		           %>
		           <div class="answer_student" style="padding-bottom:18px;padding-top: 0px;">
		          		<div class="answer_student_font">
			                   	<p class="detail_title" style="float: left;  margin-right:10px">投诉详情 </p>
			                   	 <%if(zxHelpQuestionComplaint.getStatus().equals("0")){ %>
			                   		<p class="answer_student_font_p">
										待处理
									</p>
								 <%}else if(zxHelpQuestionComplaint.getStatus().equals("1")){ %>
									<p class="answer_student_font_p">
										接收投诉
									</p>
								<%}else if(zxHelpQuestionComplaint.getStatus().equals("2")){ %>
									<p class="answer_student_font_p answer_student_font_p01" style="background-color: #ccc !important;">
										驳回投诉
									</p>
								 <%} %>
		                </div>	
		                <div class="answer_student_font">
		                   <p class="detail_title">投诉内容：<%=zxHelpQuestionComplaint.getDescript() %></p>
		                </div>
		                <%if(!zxHelpQuestionComplaint.getStatus().equals("0")){ %>
		                	<p class="answer_student_p">处理内容：<%=zxHelpQuestionComplaint.getReplycontent() %></p>
		                <%} %>
		           </div>
		           <%} %>
           </div>
           <div style="width:260px; overflow: hidden; margin: 0 auto;">
           		<c:if test="${point == '3'}">
           			<a href="/pcenter.do?method=orderListByComplation&status=${status }&mark=4&point=${point}&startcount=${startcount}"  class="answer_fb_a" style=" margin-left: 20px;">返回</a>
           		</c:if>
           		<c:if test="${point != '3'}">
	           		<a href="/pcenter.do?method=orderList&status=${status }&mark=4&point=${point}&startcount=${startcount}"  class="answer_fb_a" style=" margin-left: 20px;">返回</a>
	            </c:if>
	            <c:if test="${zxHelpOrder.status == '1'}">
	           		<a href="/pcenter.do?method=doOrder&mark=4&objid=${zxHelpOrder.orderid }&startcount=${startcount}"  class="answer_fb_a" style=" margin-left: 20px;">回复答疑</a>
	           	</c:if>
           	</div>
        </div><!----information_right_main-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
<input type="hidden" name="startcount" id="startcount" value="${startcount}"/>
</form>

<%@ include file="footer.jsp"%>
<script>
var status=<bean:write name="zxHelpOrder" property="status"/>
//查看问题
function viewQuestion(objid){
  document.pageForm.action = '/pcenter.do?method=viewQuestion&objid='+objid;
	document.pageForm.submit();

}
function doMain(objid){
	if(status == '3'){
		alert('订单已超时，无法继续回复处理！');
		return flase;
	}else if(status == '1'){
		 //未回复,处理编辑
		document.pageForm.action = '/pcenter.do?method=doOrder&objid='+objid;
		document.pageForm.submit();
	}else if(status =='2'){
		 //已回复，预览
		document.pageForm.action = '/pcenter.do?method=viewAnswer&objid='+objid;
		document.pageForm.submit();
	}

}
function addFilmVideo(questionid){
	var diag = new top.Dialog();
	diag.Title = "答疑视频";
	diag.URL = '/zxHelpOrderAction.do?method=videoList&flag=1&questionid='+questionid;
	diag.Width = 800;
	diag.Height = 500;
	diag.ShowMaxButton=true;
	diag.CancelEvent = function(){
		diag.close();
	};
	diag.show();
}
function textDes(str) {
	var keyCode = 'cd4b635b494306cddf6a6e74a7b0b4d8';
	var newText = encodeURI(desEncrypt(str, keyCode));
	newText='CE:' + newText;
	return newText;
}
function desEncrypt(word, keyCode) {
	var key = CryptoJS.enc.Utf8.parse(keyCode);
	var iv = CryptoJS.enc.Utf8.parse(keyCode);
	var srcs = CryptoJS.enc.Utf8.parse(word);
	var encrypted = CryptoJS.DES.encrypt(srcs, key, {
		iv: iv,
		mode: CryptoJS.mode.ECB
	});
	return encrypted.toString();
}
function desDecrypt(word, keyCode) {
	var key = CryptoJS.enc.Utf8.parse(keyCode);
	var iv = CryptoJS.enc.Utf8.parse(keyCode);
	var decrypt = CryptoJS.DES.decrypt(word, key, {
		iv: iv,
		mode: CryptoJS.mode.ECB
	});
	return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}
function addFilmVideo2(answerid){
	var diag = new top.Dialog();
	diag.Title = "回复视频";
	diag.URL = '/zxHelpOrderAction.do?method=videoListAnswer&flag=1&answerid='+answerid;
	diag.Width = 800;
	diag.Height = 500;
	diag.ShowMaxButton=true;
	diag.CancelEvent = function(){
		diag.close();
	};
	diag.show();
}
</script>
</body>
</html>
