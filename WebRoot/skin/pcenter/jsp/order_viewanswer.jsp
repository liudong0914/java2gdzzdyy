<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
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
			<p>订单详情</p>
        </div>
        <div class="question">
            <div class="answer_student">
                <div class="answer_student_top">
                    <a href="#"><img src="/skin/pcenter/images/img.jpg"/></a>
                    <a href="#"><p>${model.flago }</p></a>
                    <p class="answer_student_top_p">${model.flagl }前</p>
                </div>
                <div class="answer_student_font">
                	<c:if test="${model.status == '1'}">
                    		<p class="answer_student_font_p">
								未回复
							</p>
						</c:if>
						<c:if test="${model.status == '2'}">
							<p class="answer_student_font_p answer_student_font_p01">
								已回复
							</p>
						</c:if>
						<c:if test="${model.status == '3'}">
							<p class="answer_student_font_p answer_student_font_p01">
								已超时
							</p>
						</c:if>
                    <a href="#" onclick="viewQuestion('<bean:write name="model" property="questionid"/>')"><p class="answer_student_font_pright">${zxHelpQuestion.title }</p></a>
                </div>
                <a href="#" ><p class="answer_student_p">${zxHelpQuestion.descript }</p></a>
               <div class="answer_student_font">
                    <p class="answer_student_font_p answer_student_font_p01">
                    	回复内容
                    </p>
                    <a href="#"><p class="answer_student_font_pright"></p></a>
                </div>
                <a href="#"><p class="answer_student_p">${zxHelpAnswer.content }</p></a>
                <div  class="wk_money">
                    <p class="wk_money_p01">¥</p>
                    <p class="wk_money_p">${model.money }</p>
                    <p class="answer_student_p_teacher answer_student_p_teacher01">回答时限：${model.flags }</p>
                </div>
           </div>
           <div class="detail">
           		<p class="detail_title">回复视频</p>
	           		<div class="answer_student_ygm_yy">
	           			<button type="button" onclick="addFilmVideo('<bean:write name="zxHelpAnswer" property="answerid"/>')">播放视频</button>
	           		</div>
                <p class="detail_title">回复语音与图片</p>
                <div class="answer_student_ygm_yy">
	                   <%
						List list2 = (List)request.getAttribute("list2");
						if(list2 !=null && list2.size()>0){
						    for(int i=0;i<list2.size();i++){
						        ZxHelpFile file =(ZxHelpFile)list2.get(i);
						%>
						<audio id="myaudio" src="/upload/<%=file.getFlago() %>" controls="controls" style="margin:auto">
						</audio></br>
						<%}}else{ %>
						暂无录音
						<%} %>
                </div>
                <div class="detail_img">
                	<%
					List list1 = (List)request.getAttribute("list1");
					if(list1 !=null && list1.size()>0){
					    for(int i=0;i<list1.size();i++){
					        ZxHelpFile file =(ZxHelpFile)list1.get(i);
					%>
						<img  src="/upload/<%=file.getThumbpath() %>" alt="<%=file.getFlagl() %>" width="200px" height="150px" onclick="javascript:window.open('/upload/<%=file.getFlago() %>');"/>
					<%}}else{ %>
					暂无图片
					<%} %>
                </div>
           </div>
            
        </div><!----information_right_main-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
</form>

<%@ include file="footer.jsp"%>
<script>
var status=<bean:write name="model" property="status"/>
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
function addFilmVideo(answerid){
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
