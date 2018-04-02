<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestion"%>
<%@page import="com.util.date.TimeUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
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
	if(cursel == 2 && ($("#con_two_1").html() == "" || $("#con_two_1").html() == "暂无评价！")){
		getComment(1);
	}
}
function setTab1(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		menu.className=i==cursel?"hover":"";
	}
	getComment(cursel);
}
function goBack(){
	if(${tag eq '1'}){
		location.href="/weixinHelp.app?method=myQuestion&userid=${userid}&menutype=${menutype}&pagenum=${pagenum}&questionid=${question.questionid}";	
	}else if(${tag eq '2'}){
		location.href="/weixinHelp.app?method=index&userid=${userid}&pagenum=${pagenum}&questionid=${question.questionid}";	
	}else if(${teacher.userid eq sys_userid||alreadyBuy eq '1'}){
		location.href="/weixinHelp.app?method=myQuestion&userid=${userid}&menutype=${menutype}&pagenum=${pagenum}&questionid=${question.questionid}";	
	}else{
		location.href="/weixinHelp.app?method=index&userid=${userid}&pagenum=${pagenum}&questionid=${question.questionid}";
	}
}
</script>
</head>

<body>
<div id="bg" style="z-index:9999;"></div>
<div id="bg1"></div>
<%
ZxHelpQuestion question = (ZxHelpQuestion)request.getAttribute("question");
float sellprice = question.getMoney()*Constants.BUY_QUESTION_DISCOUNT/10;
%>
<div class="payment_time_mask02">
    <div class="wk_buy" >
        <a class="wk_buy_a02"></a> 
        <p class="wk_buy_p">请输入支付密码</p>    
        <p class="wk_buy_p2">广东省中职德育云平台</p> 
        <p class="wk_buy_p3"><%=sellprice %><span style="font-size:12px;">(学币)</span></p> 	
        <p class="wk_buy_p4">余额：${sysUserInfo.money }(学币)<span style="font-size:14px;text-align:right;float:right;padding-right:10px;color:#0000ff;text-decoration:underline;" onclick="wxpay()">微信在线支付</span></p>
        <%
        String iswxpay = "0";
        String paypassword = (String)request.getAttribute("paypassword");
        SysUserInfo sysUserInfo = (SysUserInfo)request.getAttribute("sysUserInfo");
        if(sysUserInfo.getMoney() < sellprice || "0".equals(sysUserInfo.getPaypassword())){
        	if(sysUserInfo.getMoney() < sellprice){
        		iswxpay = "1";//微信直接在线购买
        %>
        <p class="wk_buy_p" style="color:#00aa00;text-decoration:underline;cursor:pointer;" onclick="recharge()">余额不足，去充值</p>
        <%}else{ %>
        <p class="wk_buy_p" style="color:#00aa00;text-decoration:underline;cursor:pointer;" onclick="getPwd()">首次使用，去设置支付密码</p>
        <%}}else{ %>
        <%if("1".equals(paypassword)){ %>
        <div class="input">
        	<input type="password" name="pwd1" id="pwd1" value="" maxlength="1" readonly="readonly"/>   
            <input type="password" name="pwd2" id="pwd2" value="" maxlength="1" readonly="readonly"/>
            <input type="password" name="pwd3" id="pwd3" value="" maxlength="1" readonly="readonly"/>
            <input type="password" name="pwd4" id="pwd4" value="" maxlength="1" readonly="readonly"/>
            <input type="password" name="pwd5" id="pwd5" value="" maxlength="1" readonly="readonly"/>
            <input type="password" name="pwd6" id="pwd6" value="" maxlength="1" readonly="readonly"/>  
        </div>
        <p style="padding-top:5px;">
        <span style="font-size:12px;color:#ff0000;padding-left:10px;text-align:left;float:left;" id="pwdtips"></span>
        <span style="font-size:12px;color:#777;text-align:right;float:right;padding-right:10px;" onclick="getPwd()">忘记密码？</span>
        </p>
        <p style="font-size:12px;color:#00aa00;padding-left:10px;padding-top:8px;text-align:left;float:left;">支付密码当天输错3次，将在第2天解锁。</p>
        <%}else{ %>
        <p style="font-size:14px;color:#ff0000;padding-left:10px;padding-top:8px;text-align:left;float:left;">为了您的支付安全，当天支付密码已输错3次，支付功能已被锁定，将在明天凌晨解锁。</p>
        <%}} %>
        <input type="hidden" name="hiddenpwdid" id="hiddenpwdid" value="1"/>
    </div>
    <div class="pay" <%if(sysUserInfo.getMoney() < sellprice || "0".equals(sysUserInfo.getPaypassword()) || "0".equals(paypassword)){ %>style="display:none;"<%} %>>
    	<div class="pay_module">
            <a href="javascript:setPwd('1')" class="pay_module_a">
                <p class="pay_module01">1</p>
            </a>
            <a href="javascript:setPwd('2')"  class="pay_module_a">
                <p class="pay_module01">2</p>
                <p class="pay_module02">ABC</p>
            </a>
            <a href="javascript:setPwd('3')" >
                <p class="pay_module01">3</p>
                <p class="pay_module02">DEF</p>
            </a>
        </div>
        <div class="pay_module">
            <a href="javascript:setPwd('4')"  class="pay_module_a">
                <p class="pay_module01">4</p>
                <p class="pay_module02">GHI</p>
                
            </a>
            <a href="javascript:setPwd('5')"  class="pay_module_a">
                <p class="pay_module01">5</p>
                <p class="pay_module02">JKL</p>
            </a>
            <a href="javascript:setPwd('6')" >
                <p class="pay_module01">6</p>
                <p class="pay_module02">MNO</p>
            </a>
        </div>
        <div class="pay_module">
            <a href="javascript:setPwd('7')"  class="pay_module_a">
                <p class="pay_module01">7</p>
                <p class="pay_module02">PQRS</p>
                
            </a>
            <a href="javascript:setPwd('8')"  class="pay_module_a">
                <p class="pay_module01">8</p>
                <p class="pay_module02">TUV</p>
            </a>
            <a href="javascript:setPwd('9')">
                <p class="pay_module01">9</p>
                <p class="pay_module02">WXYZ</p>
            </a>
        </div>
        <div class="pay_module pay_module_mian">
            <a class="pay_module_a pay_module_a_left">                
            </a>
            <a href="javascript:setPwd('0')" class="pay_module_a pay_module_mian_a">
                <p class="pay_module01 pay_module03">0</p>
            </a>
            <a href="javascript:delPwd()" class="pay_module_a_left">
            	<img src="/weixin/images/pay.png" class="pay_module_a_img" />
            </a>
        </div>
    </div>
</div>

<%
List imageQuestionFileList = (List)request.getAttribute("imageQuestionFileList");
List audioQuestionFileList = (List)request.getAttribute("audioQuestionFileList");
%>
<div class="payment_time_mask01" style="z-index:99999;">
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

<div class="payment_time_mask" style="z-index:99999;">
	<form name="commentForm" method="post">
    <div class="wk_pj" >
    	<a href="#" class="wk_pj_a wk_buy_a" >
        </a> 
        <div class="write_main write_main_pj">
            <p>评分</p>
            <p class="write_main_p">1分差评，2-3分中评，4分及以上好评</p>
            <div class="write_main_img write_main_img01" style="text-align:left;">
            	<input type="radio" name="opinionCategory" value="1" />1分
    	 		<input type="radio" name="opinionCategory" value="2" />2分
     			<input type="radio" name="opinionCategory" value="3" />3分
     			<input type="radio" name="opinionCategory" value="4" />4分
    	 		<input type="radio" name="opinionCategory" value="5" checked/>5分
            </div>
        </div>
        <div class="write_input write_input_pj">
                <textarea type="text" id="content" name="content" class="write_input01 write_input02"  placeholder="发表文字评价～&nbsp;您的评价对其他小伙伴很重要哦～" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}"></textarea>
        </div>
        <div class="write_input_nm write_input_nm_pj">
            <input type="checkbox" name="anonymous" id="anonymous" value="1"/>
            <p>匿名评价</p><p id="msg_tip" style="color:#ff0000 !important;display:none;">请输入评价内容！</p>
            <a href="javascript:addComment()" class="write_input_nm_pj_a">提交</a>
        </div>
    </div>
    </form>
</div>

<%@ include file="/weixin/account/dh.jsp"%>
<div class="search" style="z-index:999999;">
	<a href="javascript:goBack()"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_wk search_wk_answer">
    	<a id="one1" href="javascript:setTab('one',1,2)" class="search_wk_a">详情</a>
        <a id="one2" href="javascript:setTab('one',2,2)">评价</a>
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
        	<%if("3".equals(question.getReplystatus())){ %>
        	<p class="answer_student_font_p answer_student_font_p01">已答</p>
        	<%}else{ %>
        	<p class="answer_student_font_p">未答</p>
        	<%} %>
            <p class="answer_student_font_pright"><%=question.getTitle() %></p>
        </div>
        <p class="answer_student_p02"><%=question.getDescript() %></p>
        <div  class="wk_money">
            <c:if test="${question.money > 0 }">
            <p class="wk_money_p01">¥</p>
            <p class="wk_money_p"><%=question.getMoney()*Constants.BUY_QUESTION_DISCOUNT/10 %></p>
            </c:if>
            <c:if test="${question.money <= 0 }">
            <p class="wk_money_p" style="color:#1fcc8a;">免费</p>
            </c:if>
            <div class="answer_student_xq_main_a answer_student_xq_main_a_teacher">
                <a href="javascript:search('cxueduanid','${question.cxueduanid }')">${question.gradename }</a>
                <a href="javascript:search('subjectid','${question.subjectid }')">${question.subjectname }</a>
            </div>
        </div>
        <c:if test="${question.money > 0 }">
        <div class="wk_font">
            <span><%=Constants.BUY_QUESTION_DISCOUNT %>折</span>
            <span class="wk_font_span" >定价：<strike>${question.money }</strike></span>
            <span class="wk_font_span">购买已答问题，为定价<%=Constants.BUY_QUESTION_DISCOUNT %>折</span>
        </div>
        </c:if>
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
    String viewAnswer = (String)request.getAttribute("viewAnswer");
    String canConment = (String)request.getAttribute("canConment");
    List imageAnswerFileList = (List)request.getAttribute("imageAnswerFileList");
    List audioAnswerFileList = (List)request.getAttribute("audioAnswerFileList");
    List videoAnswerFileList = (List)request.getAttribute("videoAnswerFileList");
    SysUserInfo teacher = (SysUserInfo)request.getAttribute("teacher");
    if("1".equals(viewAnswer)){
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
	<%}else{ %>
	<div class="answer_student_ygm">
    	<div class="answer_student_ygm_top">
            <p class="answer_student_ygm_p">答案</p>
            <div class="answer_student_top answer_student_ygm_name">
                <img src="${teacher.photo }" />
                <p>${teacher.username }</p>
            </div>
        </div>
        <p class="answer_student_p02">
        	含<%if(videoAnswerFileList.size() > 0){ %><%=videoAnswerFileList.size() %>个视频解答／<%} %><%if(audioAnswerFileList.size() > 0){ %><%=audioAnswerFileList.size() %>个语音解答／<%} %><%if(imageAnswerFileList.size() > 0){ %><%=imageAnswerFileList.size() %>个图片解答／<%} %>
        </p>
    </div>
	<%} %>
</div>
<div class="class_main_student" id="div_one_2" style="border-top:1px solid #e7e8e8; margin-top:5px;display:none">
	<div id="Tab2">
    	<div class="Menubox01">
            <ul>
                <a class="Contentbox02_moudle">
                    <li id="two1" onclick="setTab1('two',1,4)"  class="hover">
                        <p  class="Contentbox02_moudle_p">全部</p>
                        <p  class="Contentbox02_moudle_p02" id="praise">${praise }</p>
                    </li>
                </a>
                <a class="Contentbox02_moudle">
                    <li id="two2" onclick="setTab1('two',2,4)" >
                        <p  class="Contentbox02_moudle_p">好评</p>
                        <p  class="Contentbox02_moudle_p02" id="praise1">${praise1 }</p>
                    </li>
                </a>
                <a class="Contentbox02_moudle">
                    <li id="two3" onclick="setTab1('two',3,4)">
                        <p  class="Contentbox02_moudle_p">中评</p>
                        <p  class="Contentbox02_moudle_p02" id="praise2">${praise2 }</p>
                    </li>
                </a>
                <a class="Contentbox02_moudle">
                	<li id="two4" onclick="setTab1('two',4,4)">
	                    <p  class="Contentbox02_moudle_p">差评</p>
	                    <p  class="Contentbox02_moudle_p02" id="praise3">${praise3 }</p>
                   </li>
               </a>
           </ul>
       </div>
       <div class="Contentbox01">
           <div id="con_two_1" class="hover"></div>
           <div class="loading" id="loading">
				<img src="/weixin/images/loading.gif" class="loadingimg"/>加载中
		   </div>
        </div>
    </div>
</div>
<logic:equal value="0" name="iscomplaint">
<%if("1".equals(canConment)){ %>
<div class="payment_time_title" style="margin-top:15px;">   
<a href="#"  class="Menubox01_button">发表我的评价</a>
</div>
<%}else{ %>
<div class="payment_time_title02">   
	<a href="#" class="button" >立即购买</a>
</div>
<%} %>
</logic:equal>
<logic:equal value="1" name="iscomplaint">
<%String isAnswerTeacher = (String)request.getAttribute("isAnswerTeacher"); %>
<%if("1".equals(canConment)){ %>
<div class="payment_time_title" style="margin-top:15px;">   
<a href="#"  class="Menubox01_button" <%if("1".equals(isAnswerTeacher)){ %>style="width:50%;"<%} %>>发表我的评价</a>
</div>
<%}else{ %>
<div class="payment_time_title02">   
	<a href="#" class="button" <%if("1".equals(isAnswerTeacher)){ %>style="width:50%;"<%} %>>立即购买</a>
</div>
<%} %>
<%if("1".equals(isAnswerTeacher)){ %>
<div class="" style="margin-top:15px;">   
<a href="/weixinHelp.app?method=viewQuestionComplaint&userid=${userid }&questionid=${question.questionid }&orderid=${answer.orderid}" class="button" style="width:50%;right:0px;">投诉被驳回</a>
</div>
<%} %>
</logic:equal>


<div id="div_hidden" style="width:100%; height:100%; overflow:hidden; position:absolute; top:0px; z-index:999999999;background:rgba(0,0,0,0.5); display:none;"><div style="z-index:9999999999;auto:30% 0px;top:45%;position:absolute;left:35%;color:#ff0000;background-color:#fff;border: 1px solid #eee; border-radius: 5px;padding: 4px 10px;">处理中...</div></div>
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
	        data:"pagenum=" + num + "&userid=${userid}&questionid=${question.questionid}&commenttype=" + commenttype,
	        success:function(msg){
	        	if(msg != ""){
	        		$("#con_two_1").html(msg);
	        	}else{
	        		$("#con_two_1").html("暂无评价！");
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
			        data:"pagenum=" + num + "&userid=${userid}&questionid=${question.questionid}&commenttype=" + commenttype,
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
	
	
	
	var opinionCategory = document.getElementsByName('opinionCategory');
    var opinionCategoryValue = 1;
    for(var i=0;i<opinionCategory.length;i++){
	    if(opinionCategory[i].checked){
	    	opinionCategoryValue = opinionCategory[i].value;
	    }
    }
    
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
        data:"score=" + opinionCategoryValue + "&content="+ content +"&anonymouss="+anonymouss+"&userid=${userid}&questionid=${question.questionid}",
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
				
				var praise = parseInt($("#praise").html());
				$("#praise").html(praise+1);
				if(opinionCategoryValue == 1){
					var praise3 = parseInt($("#praise3").html());
					$("#praise3").html(praise3+1);
				}else if(opinionCategoryValue > 1 && opinionCategoryValue <4){
					var praise2 = parseInt($("#praise2").html());
					$("#praise2").html(praise2+1);
				}else if(opinionCategoryValue >= 4){
					var praise1 = parseInt($("#praise1").html());
					$("#praise1").html(praise1+1);
				}
				
				document.getElementById("content").value = "";
				document.getElementById("msg_tip").style.display = "none";
        	}
		}
	});
}

function setPwd(pwd){
	var hiddenpwdid = document.getElementById("hiddenpwdid").value;
	document.getElementById("pwd" + hiddenpwdid).value = pwd;
	if(hiddenpwdid == "6"){
		pay();
	}else{
		var newhiddenpwdid = parseInt(hiddenpwdid) + 1;
		document.getElementById("hiddenpwdid").value = newhiddenpwdid;
	}
}
function delPwd(){
	var hiddenpwdid = document.getElementById("hiddenpwdid").value;
	if(hiddenpwdid != "1"){
		var newhiddenpwdid = parseInt(hiddenpwdid) - 1;
		document.getElementById("pwd" + newhiddenpwdid).value = "";
		document.getElementById("hiddenpwdid").value = newhiddenpwdid;
	}
}
function pay(){
	var check = "1";
	var paypwd = "";
	for(var i=1; i<=6; i++){
    	if(document.getElementById("pwd" + i).value == ""){
    		document.getElementById("pwdtips").innerHTML = "密码输入错误！";
    		check = "0";
    		break;
    	}else{
    		paypwd = paypwd + document.getElementById("pwd" + i).value;
    	}
    }
    if(check == "1"){
    	$.ajax({
	        type:"post",
	        url:"/weixinHelp.app?method=pay",
	        data:"userid=${userid}&questionid=${question.questionid}&paypwd=" + paypwd,
	        success:function(msg){
	        	if(msg == "ok"){
	        		window.location.replace("/weixinHelp.app?method=info&userid=${userid }&questionid=${question.questionid}");
	        	}else{
	        		document.getElementById("pwdtips").innerHTML = "密码输入错误" + msg + "次！";
	        		var errcount = parseInt(msg);
	        		if(errcount < 3){
		        		for(var i=1; i<=6; i++){
					    	document.getElementById("pwd" + i).value = "";
					    }
					    document.getElementById("hiddenpwdid").value = "1";
	        		}else{
	        			window.location.replace("/weixinHelp.app?method=info&userid=${userid }&questionid=${question.questionid}");
	        		}
	        	}
   			}
		});
    }
}

function recharge(){
	window.location.href = "/wxpay/weixinPay.app?method=index&userid=${userid }";
}
function getPwd(){
	//支付密码设置成功后，自动跳转到此再次购买
	var redirecturl = "%2fweixinHelp.app%3fmethod%3dinfo%26userid%3d${userid }%26questionid%3d${question.questionid }";//转码处理
	window.location.href = "/weixinAccountIndex.app?method=beforeUpdatePwd&userid=${userid}&flag=1&redirecturl=" + redirecturl;
}
$(function () {
    $(".payment_time_title02 a").click(function () {
    	var iswxpay = '<%=iswxpay %>';
    	if(iswxpay == "1"){
    		wxpay();
    	}else{
	    	$("#bg1").css({
	            display: "block", height: $(document).height()
	        });
	        var $box = $('.payment_time_mask02');
	        $box.css({
	            display: "block",
	        });
	        for(var i=1; i<=6; i++){
		    	document.getElementById("pwd" + i).value = "";
		    }
		    document.getElementById("hiddenpwdid").value = "1";
    	}
    });
    $(".payment_time_mask02 .wk_buy_a02").on('click',function () {
        $("#bg1,.payment_time_mask02").css("display", "none");
       
    });
});
function wxpay(){
	//点击支付弹出蒙板，避免重复点击
    document.getElementById("div_hidden").style.display = "block";
    
    var buytitle = "购买广东省中职德育云平台在线答疑提问解答答案";
   	wx.checkJsApi({
   	    jsApiList: ['chooseWXPay'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
   	    success: function(res) {
   	    	if(res.checkResult.chooseWXPay == true){
   	    		//通过ajax获取统一支付接口返回的prepay_id、paySign
   	    	    $.ajax({
   	    	    	type:"post",
   	    	       	url:"/wxpay/weixinPay.app?method=getPrepayid",
   	    	       	data:"tradetype=3&money=<%=sellprice %>&questionid=${question.questionid }&openid=${openid}&userid=${userid}&body=" + buytitle + "&timestamp=<%=jsticket.get("timestamp") %>&nonce_str=<%=jsticket.get("nonceStr") %>",
   	    	       	async: false,
   	    	       	success:function(msg){
   	    	       		//ajax返回消息后，可以去掉蒙板
   	    	       		if(msg != "0"){
   	    	       			var result = msg.split(";");
   	    	       			wx.chooseWXPay({
   	    	           		    timestamp: <%=jsticket.get("timestamp") %>, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
   	    	           		    nonceStr: '<%=jsticket.get("nonceStr") %>', // 支付签名随机串，不长于 32 位【以上两个参数和wx.config保持一致】
   	    	           		    package: 'prepay_id=' + result[0], // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
   	    	           		    signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
   	    	           		    paySign: result[1], // 支付签名
   	    	           		    success: function (res) {
   	    	           		        // 支付成功后的回调函数
   	    	           		        //alert("res:"+res);
   	    	           		        //js返回支付成功，跳转到成功页面，具体支付成功与否，可通过回调函数真正写入数据库
   	    	           		        var redirecturl = "%2fweixinHelp.app%3fmethod%3dinfo%26userid%3d${userid }%26questionid%3d${question.questionid }";//转码处理
   	    	           		     	window.location.replace("/wxpay/weixinPay.app?method=paySuccess&userid=${userid}&out_trade_no=" + result[2] + "&redirecturl=" + redirecturl);
   	    	           		    },
   	    	                    cancel: function () {
   	    	                    	document.getElementById("div_hidden").style.display = "none";
   	    	                        //alert("用户取消支付！");
   	    	                        //window.location.replace("/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=${bookcontentid }&searchtype=${searchtype }");
   	    	                    },
   	    	                    error: function (e) {
   	    	                        alert("支付失败！");
   	    	                        window.location.replace("/2fweixinHelp.app?method=info&userid=${userid }&questionid=${question.questionid }");
   	    	                    }
   	    	           		});
   	    	       		}else{
   	    	       			alert("数据异常，请稍后再试！");
   	    	       			return;
   	    	       		}
   	    	  		}
   	    		});
   	    	}else{
   	    		alert("请在手机上支付！");
   	    		 //点击支付弹出蒙板，避免重复点击
   	    	    document.getElementById("div_hidden").style.display = "none";
   	    		return;
   	    	}
   	    }
   	});
}
</script>
</body>
</html>