<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.util.date.DateTime"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestion"%>
<%@page import="com.util.date.TimeUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.zx.bo.ZxHelpOrder"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>问题详情</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script type="text/javascript">
function search(id, value){
	document.getElementById(id).value = value;
	document.pageForm.action = "/weixinHelp.app?method=index&userid=${userid }";
	document.pageForm.submit();
}
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		var con=document.getElementById("div_"+name+"_"+i);
		menu.className=i==cursel?"search_wk_a":"";
		con.style.display=i==cursel?"block":"none";
	}
	
	//如果已经加载过内容，无需重新加载，只需显示即可
	if(cursel == 2 && ($("#con_two_1").html() == "" || $("#con_two_1").html() == "暂无评论！")){
		getComment(1);
	}
}
function goBack(){
	if(${tag eq '1'}){
		location.href="/weixinHelp.app?method=myQuestion&userid=${userid}&menutype=${menutype}&pagenum=${pagenum}&questionid=${question.questionid}";
	}else if(${tag eq '2'}){
		location.href="/weixinHelp.app?method=index&userid=${userid}&pagenum=${pagenum}&questionid=${question.questionid}";	
	}else if(${sys_userid eq question.sysUserInfo.userid||(!empty(teacher)&&teacher eq sys_userid)}){
		location.href="/weixinHelp.app?method=myQuestion&userid=${userid}&menutype=${menutype}&pagenum=${pagenum}&questionid=${question.questionid}";
	}else{
		location.href="/weixinHelp.app?method=index&userid=${userid}&pagenum=${pagenum}&questionid=${question.questionid}";
	}
}
</script>
</head>

<body>
<%
List imageQuestionFileList = (List)request.getAttribute("imageQuestionFileList");
List audioQuestionFileList = (List)request.getAttribute("audioQuestionFileList");
%>
<div class="payment_time_mask01">
	<div class="payment_time_mask01_main">
        <a href="javascript:stopAudio();">
        	<img src="/weixin/images/d03.png" class="wk_buy_a01"/>
        </a> 
        <div style="height:20px;"></div>
        <%@ include file="/weixin/help/playaudio_js.jsp"%>
        <%
        ZxHelpFile file = null;
        for(int i=0, size=audioQuestionFileList.size(); i<size; i++){
        	file = (ZxHelpFile)audioQuestionFileList.get(i);
        %>
        <div class="answer_student_ygm_yy answer_student_ygm_yy_tc">
        	<a href="javascript:playAudio('<%=MpUtil.HOMEPAGE %>/upload/<%=file.getMp3path() %>', 'audio_<%=file.getFileid() %>')">
            <div class="answer_student_ygm_img" style="width:<%=file.getTimelength()*2 %>px;">
                <img src="/weixin/images/d01.png" id="audio_<%=file.getFileid() %>"/>
            </div>
            <p class="answer_student_ygm_yy_p"><%=file.getTimelength() %>"</p>
            </a>
        </div>
        <%} %>
        <div class="answer_student_ygm_div answer_student_ygm_div01">
        	<%
	        for(int i=0, size=imageQuestionFileList.size(); i<size; i++){
	        	if(i > 3) break;
	        	file = (ZxHelpFile)imageQuestionFileList.get(i);
	        %>
        	<img src="/upload/<%=file.getThumbpath() %>" <%if(i < 3){ %>class="answer_student_ygm_div_img"<%} %> onclick="wx.previewImage({current:'<%=MpUtil.HOMEPAGE %>/upload/<%=file.getFilepath() %>',urls:[${questionImgUrls }]});"/>
        	<%} %>
        </div>
        <%if(imageQuestionFileList.size() > 3){ %>
        <div class="answer_student_ygm_div answer_student_ygm_div01">
        	<%
	        for(int i=4, size=imageQuestionFileList.size(); i<size; i++){
	        	file = (ZxHelpFile)imageQuestionFileList.get(i);
	        %>
        	<img src="/upload/<%=file.getThumbpath() %>" <%if(i < imageQuestionFileList.size()-1){ %>class="answer_student_ygm_div_img"<%} %> onclick="wx.previewImage({current:'<%=MpUtil.HOMEPAGE %>/upload/<%=file.getFilepath() %>',urls:[${questionImgUrls }]});"/>
        	<%} %>
        </div>
        <%} %>
    </div>
</div>

<div id="bg"></div>
<div class="payment_time_mask">
	<form name="commentForm" method="post">
    <div class="wk_pj" >
    	<a href="#" class="wk_pj_a wk_buy_a" >
        </a> 
        <div class="write_main write_main_pj">
            <p>评论内容</p>
        </div>
        <div class="write_input write_input_pj">
                <textarea type="text" id="content" name="content" class="write_input01 write_input02" style="height:118px;" placeholder="～请发表文字评论～" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}"></textarea>
        </div>
        <div class="write_input_nm write_input_nm_pj">
            <input type="checkbox" name="anonymous" id="anonymous" value="1"/>
            <p>匿名评论</p><p id="msg_tip" style="color:#ff0000 !important;display:none;">请输入评论内容！</p>
            <a href="javascript:addComment()" class="write_input_nm_pj_a">提交</a>
        </div>
    </div>
    </form>
</div>
<%
ZxHelpQuestion question = (ZxHelpQuestion)request.getAttribute("question");
%>
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search">
	<a href="javascript:goBack()"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_wk search_wk_answer">
        <a id="one1" href="javascript:setTab('one',1,2)" class="search_wk_a">详情</a>
        <a id="one2" href="javascript:setTab('one',2,2)">评论</a>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<div class="class_main_student" id="div_one_1">
	<form name="pageForm" method="post">
    <div class="answer_student_xq">
        <div class="answer_student_top">
            <img src="${student.photo }" />
            <p>${student.username } • ${question.subjectname } - ${question.gradename }</p>
            <p class="answer_student_top_p"><%=TimeUtil.getTimeName(question.getCreatedate()) %></p>
        </div>	
        <div class="answer_student_font">
        	<%if("2".equals(question.getReplystatus()) || "3".equals(question.getReplystatus())){ %>
        	<p class="answer_student_font_p answer_student_font_p01">已答</p>
        	<%}else{ %>
        	<p class="answer_student_font_p">未答</p>
        	<%} %>
            <p class="answer_student_font_pright"><%=question.getTitle() %></p>
        </div>
        <p class="answer_student_p02"><%=question.getDescript() %></p>
        <div  class="wk_money">
            <p class="wk_money_p" style="color:#1fcc8a;">免费</p>
            <div class="answer_student_xq_main_a answer_student_xq_main_a_teacher">
                <a href="javascript:search('cxueduanid','${question.cxueduanid }')">${question.gradename }</a>
                <a href="javascript:search('subjectid','${question.subjectid }')">${question.subjectname }</a>
            </div>
        </div>
        <%if("0".equals(question.getReplystatus()) || "1".equals(question.getReplystatus())){ %>
        <p class="answer_student_p_teacher">回答截止时间：<%if(DateTime.getCompareToDate(question.getEnddate(), DateTime.getDate()) > 0){ %><%=question.getEnddate() %><%}else{ %>已结束<%} %></p>
        <%} %>
    </div>
    <input type="hidden" name="cxueduanid" id="cxueduanid" value=""/>
    <input type="hidden" name="subjectid" id="subjectid" value=""/>
    </form>
    <%
    List questionFileList = (List)request.getAttribute("questionFileList");
    if(questionFileList != null && questionFileList.size() > 0){
    %>
    <a href="#"><div class="answer_student_look payment_time_title01">
    	<p>查看提问问题语音与图片</p>
        <img src="/weixin/images/c02.png" />
    </div></a>
    <%} %>
    <%
    SysUserInfo teacher = (SysUserInfo)request.getAttribute("teacher");
    if(teacher != null){
    %>
    <div class="answer_student_ygm">
    	<div class="answer_student_ygm_top">
            <p class="answer_student_ygm_p">答案</p>
            <div class="answer_student_top answer_student_ygm_name">
                <img src="${teacher.photo }" />
                <p>${teacher.username }</p>
            </div>
        </div>
        <p class="answer_student_p02">${answer.content }</p>
        <%
        List imageAnswerFileList = (List)request.getAttribute("imageAnswerFileList");
        List audioAnswerFileList = (List)request.getAttribute("audioAnswerFileList");
        for(int i=0, size=audioAnswerFileList.size(); i<size; i++){
        	file = (ZxHelpFile)audioAnswerFileList.get(i);
        %>
        <div class="answer_student_ygm_yy">
        	<a href="javascript:playAudio('<%=MpUtil.HOMEPAGE %>/upload/<%=file.getMp3path() %>', 'audio_<%=file.getFileid() %>')">
            <div class="answer_student_ygm_img" style="width:<%=file.getTimelength()*2 %>px;">
                <img src="/weixin/images/d01.png" id="audio_<%=file.getFileid() %>"/>
            </div>
            <p class="answer_student_ygm_yy_p"><%=file.getTimelength() %>"</p>
            </a>
        </div>
        <%} %>
        <div class="answer_student_ygm_div">
        	<%
	        for(int i=0, size=imageAnswerFileList.size(); i<size; i++){
	        	if(i > 3) break;
	        	file = (ZxHelpFile)imageAnswerFileList.get(i);
	        %>
        	<img src="/upload/<%=file.getThumbpath() %>" <%if(i < 3){ %>class="answer_student_ygm_div_img"<%} %> onclick="wx.previewImage({current:'<%=MpUtil.HOMEPAGE %>/upload/<%=file.getFilepath() %>',urls:[${answerImgUrls }]});"/>
        	<%} %>
        </div>
        <%if(imageAnswerFileList.size() > 3){ %>
        <div class="answer_student_ygm_div">
        	<%
	        for(int i=4, size=imageAnswerFileList.size(); i<size; i++){
	        	file = (ZxHelpFile)imageAnswerFileList.get(i);
	        %>
        	<img src="/upload/<%=file.getThumbpath() %>" <%if(i < imageAnswerFileList.size()-1){ %>class="answer_student_ygm_div_img"<%} %> onclick="wx.previewImage({current:'<%=MpUtil.HOMEPAGE %>/upload/<%=file.getFilepath() %>',urls:[${answerImgUrls }]});"/>
        	<%} %>
        </div>
        <%} %>
        <%
        List videoAnswerFileList = (List)request.getAttribute("videoAnswerFileList");
        if(videoAnswerFileList != null && videoAnswerFileList.size() > 0){
        %>
        <div class="answer_student_p02" style="margin-top:10px;">
        <script type="text/javascript" src="/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
        <%
        for(int i=0, size=videoAnswerFileList.size(); i<size; i++){
        	file = (ZxHelpFile)videoAnswerFileList.get(i);
        %>
        <div id="a<%=i+1 %>"></div>
		<script type="text/javascript">
			var flashvars={
				p:1,
				e:1,
				i:'/upload/<%=file.getThumbpath() %>'
				};
			var video=['<%=MpUtil.HOMEPAGE %>/upload/<%=file.getMp4path() %>->video/mp4'];
			var support=['all'];
			CKobject.embedHTML5('a<%=i+1 %>','ckplayer_a<%=i+1 %>','100%','100%',video,flashvars,support);
		</script>
		<%} %>
        </div>
        <%} %>
    </div>
	<%} %>
</div>
<div class="class_main_student" id="div_one_2" style="border-top:1px solid #e7e8e8; margin-top:5px;display:none">
	<div id="Tab2">
       <div class="Contentbox01">
           <div id="con_two_1" class="hover"></div>
           <div class="loading" id="loading">
				<img src="/weixin/images/loading.gif" class="loadingimg"/>加载中
		   </div>
        </div>
    </div>
</div>
        
<%
ZxHelpOrder order = (ZxHelpOrder)request.getAttribute("order");
String isAnswer = (String)request.getAttribute("isAnswer");
if("1".equals(isAnswer)){
%>    
	<div class="" style="margin-top:15px;">   
		<a href="/weixinHelp.app?method=answer&userid=${userid }&questionid=${question.questionid }" class="Menubox01_button">我来回答</a>
	</div>
<%}else if("2".equals(isAnswer)){ %>
	<div class="" style="margin-top:15px;">   
		<a href="/weixinHelp.app?method=answerContinue&userid=${userid }&questionid=${question.questionid }&orderid=<%=order.getOrderid() %>" class="Menubox01_button">继续回答</a>
	</div>
<%}else{ %>
	<div class="payment_time_title" style="margin-top:15px;">   
		<a href="#"  class="Menubox01_button">发表我的评论</a>
	</div>
<%} %>
	
<script type="text/javascript">
$(function () {
    $(".payment_time_title a").click(function () {
        $("#bg").css({
            display: "block", height: $(document).height()
        });
        var $box = $('.payment_time_mask');
        $box.css({
            display: "block",
        });
    });
    //点击关闭按钮的时候，遮罩层关闭
    $(".payment_time_mask .wk_buy_a").on('click',function () {
        $("#bg,.payment_time_mask").css("display", "none");
       
    });
});
$(function () {
    $(".payment_time_title01").click(function () {
        $("#bg").css({
            display: "block", height: $(document).height()
        });
        var $box = $('.payment_time_mask01');
        $box.css({
            display: "block",
        });
    });
    //点击关闭按钮的时候，遮罩层关闭
    $(".payment_time_mask01 .wk_buy_a01").on('click',function () {
        $("#bg,.payment_time_mask01").css("display", "none");
       
    });
});

function getComment(commenttype){
	var num = 0;
	if(num == 0){
		$.ajax({
	        type:"post",
	        url:"/weixinHelp.app?method=getAjaxComment",
	        data:"pagenum=" + num + "&userid=${userid}&questionid=${question.questionid}&commenttype=" + commenttype + "&ftag=1",
	        success:function(msg){
	        	if(msg != ""){
	        		$("#con_two_1").html(msg);
	        	}else{
	        		$("#con_two_1").html("暂无评论！");
	        	}
   			}
		});
  	  	num++;
	}
	$(document).ready(function(){
		$(document).scroll(function(){
	    	if($("#loading").is(":hidden")){
	      		$("#loading").show();
	      	}
	    	var scrolltop = $(document).scrollTop();
	        var dheight = $(document).height();
	        var wheight = $(window).height();
	        if(scrolltop >= dheight-wheight){
	    		  $.ajax({
			        type:"post",
			        url:"/weixinHelp.app?method=getAjaxComment",
			        data:"pagenum=" + num + "&userid=${userid}&questionid=${question.questionid}&commenttype=" + commenttype + "&ftag=1",
			        success:function(msg){
			        	if(msg != ""){
			        		$("#con_two_1").append(msg);
			        	}else{
			        		$("#loading").html("数据已全部加载完!");
			        	}
	     			}
	 			});
	    	  	num++;
	      	}
		});
	});
}
function addComment(){
	var Tab1 = document.getElementById("Tab1");
	var b=document.getElementById("b");
	
    var content = document.getElementById("content").value;
    if(content == ""){
    	document.getElementById("msg_tip").style.display = "block";
    	return ;
    }
    
    var anonymouss = "0";
    var anonymous=document.getElementById("anonymous");
    if(anonymous.checked){
    	anonymouss ="1";
    }
	$.ajax({
        type:"post",
        url:"/weixinHelp.app?method=addAjaxComment",
        data:"score=0&content="+ content +"&anonymouss="+anonymouss+"&userid=${userid}&questionid=${question.questionid}",
        success:function(msg){
        	if(msg == "1"){
        		//关闭蒙板
        		$("#bg,.payment_time_mask").css("display", "none");
        		
        		var name = "one";
        		var cursel = 2;
        		for(i=1;i<=2;i++){
					var menu=document.getElementById(name+i);
					var con=document.getElementById("div_"+name+"_"+i);
					menu.className=i==cursel?"search_wk_a":"";
					con.style.display=i==cursel?"block":"none";
				}
				
				getComment(1);
				
				document.getElementById("content").value = "";
				document.getElementById("msg_tip").style.display = "none";
        	}
		}
	});
}
</script>
</body>
</html>