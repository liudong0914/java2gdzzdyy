<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>在线答疑</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-1.10.2.js" language="javascript" type="text/javascript"></script>
<script type="text/javascript" src="/libs/js/sk/tip.js"></script>
<script type="text/javascript">
var imageServerIds = "";
var audioServerIds = "";
var audioTimes = "";

var subjectid="${helpquestion.subjectid}";
var subjectname="${helpquestion.name}";
var gradeid="${helpquestion.gradeid}";
var gradename="${helpquestion.gradename}";
var cxueduanid="${helpquestion.cxueduanid}";
function showSubject(){
    $("#bg").css({height: $(document).height()});
    $("#bg,.payment_time_mask01").show();
}
function selectSubject(sid,sname){
	subjectid=sid;
	subjectname=sname;
	$("#sub_span").text(sname);
	$(".payment_time_mask01").find("a").removeClass("hover");
	$("#sub"+sid).addClass("hover");
	$("#bg,.payment_time_mask01").css("display", "none");
	$.ajax({
		type:"post",
        url:"weixinHelp.app?method=getGradeByAjax",
        data:"userid=${userid}&subjectid="+subjectid,
        success:function(data){
        	if(data!=""){
        		$("#grade_div").html(data);	
        		$("#grade_span").html("");
        		gradeid="";
        		gradename="";
        	}
        }
	});
}

function showGrade(){
	if(subjectid!=""){
		$("#bg").css({height: $(document).height()});
	    $("#bg,.payment_time_mask02").show();	
	}else{
		$("#grade_span").html("<div style='color:red;'>请先选择学科</div>");
	}
}

function selectGrade(gid,gname,cxd){
	gradeid=gid;
	gradename=gname;
	cxueduanid=cxd;
	$("#grade_span").html(gname);
	$("#grade_div").find("a").removeClass("hover");
	$("#g"+gid).addClass("hover");
	$("#bg,.payment_time_mask02").css("display", "none");
}

function showTime(){
	$("#bg").css({height: $(document).height()});
    $("#bg,.payment_time_mask03").show();	
}

function selectTime(tid){
	$(".payment_time_mask03").find("a").removeClass("hover");
	$("#t"+tid).addClass("hover");
	$("#in_time").val(tid);
	$("#bg,.payment_time_mask03").hide();
	$("#t_div").text(" ( 小时 ) ");
}

function showMoney(){
	$("#bg").css({height: $(document).height()});
    $("#bg,.payment_time_mask04").show();	
}

function selectMoney(mid){
	$(".payment_time_mask04").find("a").removeClass("hover");
	$("#m"+mid).addClass("hover");
	$("#in_money").val(mid);
	$("#bg,.payment_time_mask04").hide();
	$("#m_div").text(" ( 学币 ) ");
}
function chooseImage(){
	var ic=${imgcount}-$("#img_div>div").size();
	if(ic>0){
		wx.chooseImage({
		    count: ic, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		        var images=$("#img_div").html();
		        for ( var i = 0; i < localIds.length; i++) {
		        	images+="<div class='Published_div_yy' id='img"+i+"'>"+
					            "<a href='javascript:deleteImg("+i+")'><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>"+
					            "<img src='"+localIds[i]+"' class='Published_div_yy_img01' />"+
					       	"</div>";
				}
				$("#img_div").html(images);
				var ic=${imgcount}-$("#img_div>div").size();
				if(ic==0){
					$("#uploadimg").hide();
				}
		    }
		});
	}
}


function deleteImg(imgid){
	$("#img"+imgid).remove();
	var ic=${imgcount}-$("#img_div>div").size();
	if(ic<${imgcount}){
		$("#uploadimg").show();
	}
}

function uploadAudio(){
	$("#record_box_1").show();
}

function closeBox(){
	$("#record_box_1").hide();
}

function closeBox1(){
	clearTimeout(timeoutid);
	document.getElementById("record_box_2").style.display = "none";
	wx.stopRecord({
	    success: function (res) {
	    }
	});
}

var second = 0;
var timeoutid;
function startRecord(){
	document.getElementById("record_box_1").style.display = "none";
	document.getElementById("recordSeconds").innerHTML = "00";
	document.getElementById("record_box_2").style.display = "";
	wx.startRecord();
	//开始计时
	second = 0;//重新初始化
	startRecordTime();
	wx.onVoiceRecordEnd({
	    // 录音时间超过一分钟没有停止的时候会执行 complete 回调
	    complete: function (res) {
	        var localId = res.localId;
	        stopRecord(localId);
	    }
	});
}

function startRecordTime(){
	if(second < 10){
		document.getElementById("recordSeconds").innerHTML = "0" + second;
	}else{
		if(second > 60){
			document.getElementById("recordSeconds").innerHTML = 60;
		}else{
			document.getElementById("recordSeconds").innerHTML = second;
		}
	}
	second ++;
	timeoutid = window.setTimeout("startRecordTime()", 1000);
}


function stopRecord(localid){
	clearTimeout(timeoutid);
	if(localid && localid == "0"){
		wx.stopRecord({
		    success: function (res) {
		    	var localId = res.localId;	
		    	if(second > 60) second = 60;
		    	addAudioImg(localId);
		    }
		});
	}else{
		if(second > 60) second = 60;
		addAudioImg(localid);
	}
	document.getElementById("record_box_2").style.display = "none";
}
var audionum=0;
function addAudioImg(audiowxid){
	audionum++;
	var audio=$("#audio_div").html();
	audio+="<div class='Published_div_yy01' id='audio"+audionum+"'>"+
		    "<a href='javascript:deleteAudio("+audionum+")'><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>"+
		    "<div class=\"answer_student_ygm_yy\">"+
				"<div class=\"answer_student_ygm_img answer_student_ygm_yy_teacherimg\" >"+
					"<a href=\"javascript:playAudio('"+audiowxid+"',"+audionum+")\"><img src='/weixin/images/d01.png' aid=\""+audiowxid+"\" atimes=\""+second+"\" id=\"audionimg"+audionum+"\" /></a>"+
				"</div>"+
				"<p class=\"answer_student_ygm_yy_p01\">"+second+"</p>"+
			"</div>";
	$("#audio_div").html(audio);
	var ic=${audiocount}-$("#audio_div>div").size();
	if(ic==0){
		$("#ly").hide();
	}
}

function playAudio(aId,divid){
	$("#audio_div>div").each(function(){
		$(this).find("img").eq(1).attr("src","/weixin/images/d01.png");
	});
	$("#audionimg"+divid).attr("src","/weixin/images/d01.gif");
	wx.playVoice({
	    localId: aId // 需要播放的音频的本地ID，由stopRecord接口获得
	});
	wx.onVoicePlayEnd({
	    success: function (res) {
	        var localId = res.localId; // 返回音频的本地ID
	        if(aId==localId){
	        	$("#audionimg"+divid).attr("src","/weixin/images/d01.png");
	        	wx.stopVoice({
	        	    localId: localId
	        	});
	        }
	    }
	});
}

function deleteAudio(obj){
	$("#audio"+obj).remove();
	<%--判断语音数量是否低于限制数量，如果低于限制显示录音按钮--%>
	var ic=${audiocount}-$("#audio_div>div").size();
	if(ic<${audiocount}){
		$("#ly").show();
	}
}

function showFB(){
	$("#bg").css({height: $(document).height()});
    $("#bg,#fb").show();
}

function submitQuestion(stype){
	$("#bg,#fb").hide();
	var timereg=/^[1-9][0-9]{0,1}$/;
	var moneyreg=/^(\d{1,4})(?:\.\d{1,2})?$/;
	var state=0;
	if(subjectid==""){
		$("#sub_span").html("<div style='color:red;'>请选择学科</div>");
		state=1;
	}
	if(gradeid==""){
		$("#grade_span").html("<div style='color:red;'>请选择年级</div>");
		state=1;
	}
	if($("#title").val()==""){
		$("#title_span").text("(标题不能为空)");
		state=1;
	}
	var titlelen = strlen($("#title").val());
	if(titlelen >= 200){
		$("#title_span").text("(标题不超过100字，或200英文)");
		state=1;
	}
	if($("#descript").val()==""){
		$("#des_span").text("(详情描述不能为空)");
		state=1;
	}
	var descriptlen = strlen($("#descript").val());
	if(descriptlen >= 1000){
		$("#des_span").text("(详情不超过500字，或1000英文)");
		state=1;
	}
	if($("#in_time").val()==""){
		$("#t_div").html("<div style='color:red;'>&nbsp;&nbsp;请输入回答最佳时限</div>");
		state=1;
	}else if(!timereg.test($("#in_time").val())){
		$("#t_div").html("<div style='color:red;'>&nbsp;&nbsp;格式无效（1~99）</div>");
		state=1;
	}
    if($("#in_money").val()==""){
		$("#m_div").html("<div style='color:red;'>&nbsp;&nbsp;请输入奖励金额</div>");
		state=1;
	}else if(!moneyreg.test($("#in_money").val())){
		$("#m_div").html("<div style='color:red;'>&nbsp;&nbsp;格式无效（0.01~9999.99）</div>");
		state=1;
	}
    if(state==0){
    	if($("#in_money").val()==0){//未设置奖励金额,直接保存答疑
        	saveQuestion(stype);
        }else{//设置奖励金额，需要验证支付密码并支付设置奖励
        	questionPayMent();
        }
    }
}

var iswxpay=0;
//支付答疑设置奖励
function questionPayMent(){
	var price=$("#in_money").val();
	var usermoney=${sysUserInfo.money};
	$("#price").text(price);
	if(usermoney<price||usermoney==0||${paypassword eq '0'}){
		$(".pay").hide();
	}
	if(usermoney<price||${sysUserInfo.paypassword eq '0'}){
		if(usermoney<price){
			iswxpay=1;
			wxpay();
		}else{
			$("#scsy").show();
			$("#bg,#qzf").show();
		}
	}else{
		//当前页面输入错误少于3次
		var cxdisplay = document.getElementById("cx").style.display;
		if(${paypassword eq '1'} && cxdisplay == "none"){
			$("#zf").show();		
		}else{
			$("#cx").show();
		}
		$("#bg,#qzf").show();
	}
}

function saveQuestion(status){
	document.getElementById("div_hidden").style.display = "block";
    uploadFile('saveQuestionByAjax');
}

function restoreTime(){
	$("#t_div").text(" ( 小时 ) ");
}

function restoreMoney(){
	$("#m_div").text(" ( 学币 ) ");
}

function colseSubmit(){
	$("#bg,#fb").hide();
}
//获取中、英文字符长度
function strlen(str) {
    var len = 0;
    for (var i = 0; i < str.length; i++) {
        var c = str.charCodeAt(i);
        //单字节加1 
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
            len++;
        }
        else {
            len += 2;
        }
    }
    return len;
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
</head>
<body>	
<div class="payment_time_mask01">
	<div  class="menu_div_question" >
		<c:forEach items="${subjectlist}" var="s">
     	<a class="menu_div_a${helpquestion.subjectid eq s.subjectid?' hover':'' }" id="sub${s.subjectid}" href="javascript: selectSubject(${s.subjectid},'${s.subjectname}')">${s.subjectname}</a>
     	</c:forEach>
    </div>
</div>
<div class="payment_time_mask02">
	<div  class="menu_div_question">
		<div id="grade_div">
			<c:forEach items="${gradelist}" var="grade">
	     	<a class="menu_div_a${helpquestion.gradeid eq grade.gradeid?' hover':'' }" id="g${grade.gradeid}" href="javascript:selectGrade(${grade.gradeid},'${grade.gradename}',${grade.cxueduanid})">${grade.gradename}</a>
	     	</c:forEach>
		</div>
    </div>
</div>
<div class="payment_time_mask03">
	<div  class="menu_div_question" >
		<a  href="javascript:selectTime(1)" id="t1" class="menu_div_a" >1小时</a>
		<a  href="javascript:selectTime(2)" id="t2" class="menu_div_a" >2小时</a>
		<a  href="javascript:selectTime(3)" id="t3" class="menu_div_a" >3小时</a>
		<a  href="javascript:selectTime(4)" id="t4" class="menu_div_a" >4小时</a>
		<a  href="javascript:selectTime(5)" id="t5" class="menu_div_a" >5小时</a>
		<a  href="javascript:selectTime(6)" id="t6" class="menu_div_a" >6小时</a>
		<a  href="javascript:selectTime(7)" id="t7" class="menu_div_a" >7小时</a>
		<a  href="javascript:selectTime(8)" id="t8" class="menu_div_a" >8小时</a>
		<a  href="javascript:selectTime(9)" id="t9" class="menu_div_a" >9小时</a>
		<a  href="javascript:selectTime(10)" id="t10" class="menu_div_a" >10小时</a>
		<a  href="javascript:selectTime(11)" id="t11" class="menu_div_a" >11小时</a>
		<a  href="javascript:selectTime(12)" id="t12" class="menu_div_a" >12小时</a>
		<a  href="javascript:selectTime(24)" id="t24" class="menu_div_a" >1天</a>
		<a  href="javascript:selectTime(48)" id="t48" class="menu_div_a" >2天</a>
		<a  href="javascript:selectTime(72)" id="t72" class="menu_div_a" >3天</a>
    </div>
</div>
<div class="payment_time_mask04">
	<div  class="menu_div_question" >
	     	<a  href="javascript:selectMoney(0)" id="m0" class="menu_div_a" >免&nbsp;&nbsp;费</a>
	     	<a  href="javascript:selectMoney(1)" id="m1" class="menu_div_a" >1学币</a>
	     	<a  href="javascript:selectMoney(2)" id="m2" class="menu_div_a" >2学币</a>
	     	<a  href="javascript:selectMoney(3)" id="m3" class="menu_div_a" >3学币</a>
	     	<a  href="javascript:selectMoney(4)" id="m4" class="menu_div_a" >4学币</a>
	     	<a  href="javascript:selectMoney(5)" id="m5" class="menu_div_a" >5学币</a>
	     	<a  href="javascript:selectMoney(6)" id="m6" class="menu_div_a" >6学币</a>
	     	<a  href="javascript:selectMoney(7)" id="m7" class="menu_div_a" >7学币</a>
	     	<a  href="javascript:selectMoney(8)" id="m8" class="menu_div_a" >8学币</a>
	     	<a  href="javascript:selectMoney(9)" id="m9" class="menu_div_a" >9学币</a>
	     	<a  href="javascript:selectMoney(10)" id="m10" class="menu_div_a" >10学币</a>
	     	<a  href="javascript:selectMoney(15)" id="m15" class="menu_div_a" >15学币</a>
    </div>
</div>
<div id="bg"></div>
<div class="payment_time_mask" id="qzf" style="margin-top:40%;">
 <div class="wk_buy">
        <a class="wk_buy_a" onclick="closeQuestionPay()"></a> 
        <p class="wk_buy_p">请输入支付密码</p>    
        <p class="wk_buy_p2">进步学堂</p> 
        <p class="wk_buy_p3">¥<span id="price"></span><span style="font-size:12px;">(学币)</span></p> 	
        <p class="wk_buy_p4">余额：${sysUserInfo.money }(学币)<span style="font-size:14px;text-align:right;float:right;padding-right:10px;color:#0000ff;text-decoration:underline;" onclick="wxpay()">微信在线支付</span></p>
        <p class="wk_buy_p" id="yebz" style="display:none;color:#00aa00;text-decoration:underline;cursor:pointer;" onclick="recharge()">余额不足，去充值</p>
        <p class="wk_buy_p" id="scsy" style="display:none;color:#00aa00;text-decoration:underline;cursor:pointer;" onclick="getPwd()">首次使用，去设置支付密码</p>
        <div id="zf" style="display:none;">
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
        </div>
        <p id="cx" style="display:none;font-size:14px;color:#ff0000;padding-left:10px;padding-top:8px;text-align:left;float:left;">为了您的支付安全，当天支付密码已输错3次，支付功能已被锁定，将在明天凌晨解锁。</p>  
        <input type="hidden" name="hiddenpwdid" id="hiddenpwdid" value="1"/>
    </div>
    <div class="pay" id="pay_keyboard">
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

<div class="payment_time_mask" id="fb">
	<div class="question_fb" >
    	<a href="javascript:colseSubmit()"  class="wk_buy_a" >
        	<img src="/weixin/images/img10.png" />
        </a> 
    	<p  class="question_fb_p" >确认发布当前提问内容吗？</p>
        <div class="menu_div_question_div">
            <a href="javascript:submitQuestion(0)"  class="menu_div_question_div_a">保存草稿</a>
            <a href="javascript:submitQuestion(1)">确认发布</a>
        </div>
     </div>
</div>

<div class="search search_write" style="z-index:999999;">
	<a href="javascript:history.back()">
		<div  class="search_left">
    		<img src="/weixin/images/a01.png" class="search_class" />
    	</div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">发布问题</p>
    </div>
    <div class="payment_time_title" style="display:inline-block; position:absolute; float:right; right:10px; color:#e60012; top:0px; font-size:16px; width:100px;">   
    <a href="javascript:submitQuestion(1)" class="write">发布</a>
    </div>
</div>
<div class="search_padding"></div>

<a href="javascript:showSubject()">
	<div class="answer_student_look answer_student_look_Published payment_time_title01">
	    <p>选择学科</p>
	    <img src="/weixin/images/c02.png" />
	    <span id="sub_span">${subject.subjectname}</span>
	</div>
</a>
<a href="javascript:showGrade()">
	<div class="answer_student_look answer_student_look_Published payment_time_title02">
	    <p>选择年级</p>
	    <img src="/weixin/images/c02.png" />
	    <span id="grade_span"></span>
	</div>
</a>

<div class="Published">
	<p>问题标题<span id="title_span"></span></p>
    <div class="Published_div">
    	<textarea type="text" id="title" class="Published_textarea"  placeholder="请输入问题标题" onFocus="if(value==defaultValue){value='';this.style.color='#000'}$('#title_span').text('');" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}"></textarea>
    </div>
</div>
<div class="Published">
	<p>详情描述<span id="des_span"></span></p>
    <div class="Published_div">
    	<textarea type="text" id="descript" class="Published_textarea" style="height:60px;" placeholder="请输入详情描述" onFocus="if(value==defaultValue){value='';this.style.color='#000'}$('#des_span').text('');" onBlur="if(!value){value=defaultValue; this.style.color='#c9caca'}"></textarea>
    </div>
</div>
<div class="Published">
	<p>语音与图片（选填 ）</p>
    <div class="Published_div01">
    	<div id="img_div">
    	</div>
		<div class="Published_div_yy" onclick="chooseImage()" id="uploadimg">
            <img src="/weixin/images/d06.png" class="Published_div_yy_img02" />
       	</div>
       	<div style="clear:both;"></div>
       	<div id="audio_div"></div>
        <div class="Published_div_yy" onclick="uploadAudio()" id="ly">
            <img src="/weixin/images/d07.png" class="Published_div_yy_img02" />
        </div>
    </div>
</div>
<div class="Published">
	<p>回答最佳时限</p>
    <DIV class="CONTENT_tame">
        <div class="header_tame">
            <div class="sub_tame" onclick="showTime()">
                <p>选择时限</p>
                <span class="right"></span>
            </div>
        </div>
    </DIV>
    <input type="text" class="firstpane_input" id="in_time" onclick="showTime()" onFocus="restoreTime()" readonly="readonly" maxlength="3" style="width:40px;" value="2"/><div style="margin-top:16px;" id="t_div">（ 小时 ）</div>
</div>
<div class="Published">
	<p>问题金额</p>
    <DIV class="CONTENT_tame01">
        <div class="header_tame01">
            <div class="sub_tame01" onclick="showMoney()">
                <p>选择金额</p>
                <span class="right"></span>
            </div>
        </div>
    </DIV>
    <input type="text" class="firstpane_input" id="in_money" onclick="showMoney()" onFocus="restoreMoney()" maxlength="4" readonly="readonly" style="width:40px;" value="1"/><div style="margin-top:16px;margin-left:20px;" id="m_div">（ 学币 ）</div>
</div>
<p class="password_p password_p01">
1、发布提问时请尽量描述清楚问题内容。<br />
2、回答最佳时限，即提交发布问题后开始计时，老师必须在此设置时间前回答问题，否则此问题将失效。<br />
3、问题金额，即给予老师回答此问题的奖赏，如因时间过期提问失效，学币将自动退回当前账户余额。<br />
4、问题交易成功后，当前答疑提问可进行售卖给其他同学，自己可从中获取部分收益，详情请进入“个人中心-<a href="/weixinHelp.app?method=questionDescript&userid=${userid}">答疑规则</a>”进行查看。
</p>
<div class="record_box" id="record_box_1" style="display:none;">
	<a href="javascript:closeBox()"><img src="/weixin/images/ly_close.png" class="record_close" /></a>
    <div class="record_box_p">
		<p>点击开始录音</p>
    </div>
	<a href="javascript:startRecord()"><img src="/weixin/images/ly_img3.png" class="record_img" /></a>    
</div>
<div class="record_box" id="record_box_2" style="display:none;">
	<a href="javascript:closeBox1()"><img src="/weixin/images/ly_close.png" class="record_close" /></a>
    <div class="record_box_p">
		<p>录音中</p>
   	 	<p id="recordSeconds">00</p>
    </div>
	<a href="javascript:stopRecord('0')"><img src="/weixin/images/ly_img2.png" class="record_img" /></a>    
</div>
<div id="div_hidden" style="width:100%; height:100%; overflow:hidden; position: fixed; top:0px; z-index:999999999;background:rgba(0,0,0,0.5); display:none;"><!-- <div style="z-index:9999999999;auto:30% 0px;top:45%;position:absolute;left:35%;color:#ff0000;background-color:#fff;border: 1px solid #eee; border-radius: 5px;padding:4px 10px;">处理中...</div> --></div>
<script>
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
	        url:"/weixinHelp.app?method=payAddQuestion",
	        data:"userid=${userid}&paypwd=" + paypwd,
	        success:function(msg){
	        	if(msg == "ok"){
	        		document.getElementById("div_hidden").style.display = "block";
	        		uploadFile("saveQuestionByAjaxAndPay");
	        	}else{
	        		document.getElementById("pwdtips").innerHTML = "密码输入错误" + msg + "次！";
	        		var errcount = parseInt(msg);
	        		if(errcount < 3){
		        		for(var i=1; i<=6; i++){
					    	document.getElementById("pwd" + i).value = "";
					    }
					    document.getElementById("hiddenpwdid").value = "1";
	        		}else{
	        			//密码输入错误超过3次，隐藏输入框
	        			document.getElementById("pay_keyboard").style.display = "none";
	        			document.getElementById("zf").style.display = "none";
	        			document.getElementById("cx").style.display = "block";
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
	//支付密码设置成功后，自动跳转到此再次发布答疑
	var redirecturl = "%2fweixinHelp.app%3fmethod%3dbeforeAddQuestion%26userid%3d${userid }";//转码处理
	window.location.href = "/weixinAccountIndex.app?method=beforeUpdatePwd&userid=${userid}&flag=1&redirecturl=" + redirecturl;
}

function closeQuestionPay(){
	$("#bg,#qzf").hide();
}

function wxpay(){
	//点击支付弹出蒙板，避免重复点击
    document.getElementById("div_hidden").style.display = "block";
    var money = $("#in_money").val();
   	wx.checkJsApi({
   	    jsApiList: ['chooseWXPay'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
   	    success: function(res) {
   	    	if(res.checkResult.chooseWXPay == true){
   	    		//通过ajax获取统一支付接口返回的prepay_id、paySign
   	    	    $.ajax({
   	    	    	type:"post",
   	    	       	url:"/wxpay/weixinPay.app?method=getPrepayid",
   	    	       	data:"tradetype=4&money=" + money + "&questionid=${payid }&openid=${openid}&userid=${userid}&body=支付进步学堂在线答疑提问&timestamp=<%=jsticket.get("timestamp") %>&nonce_str=<%=jsticket.get("nonceStr") %>",
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
   	    	           		        uploadFile(result[2]);//传递订单号使用
   	    	           		    },
   	    	                    cancel: function () {
   	    	                    	document.getElementById("div_hidden").style.display = "none";
   	    	                    },
   	    	                    error: function (e) {
   	    	                        alert("支付失败！");
   	    	                        window.location.replace("/weixinHelp.app?method=info&userid=${userid }");
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
function postData(method){
	if(imageServerIds != "" || audioServerIds != ""){
    	showTipDiv("上传完成！", 120, 50, 5);
    }
    //由于+、&等字符提交时会被过滤掉，所以要经过转码提交处理
    var title=$("#title").val();
	var descript=$("#descript").val();
	title = encodeURIComponent(encodeURIComponent(title));
	descript = encodeURIComponent(encodeURIComponent(descript));
    
	if(method == "saveQuestionByAjax"){
	    //调用ajax保存答疑
		var times=$("#in_time").val();
		var money = $("#in_money").val();
		$.ajax({
			type:"post",
		       url:"/weixinHelp.app?method=saveQuestionByAjax",
		       data:"userid=${userid}&subjectid="+subjectid+"&subjectname="+subjectname+"&gradeid="+gradeid+"&gradename="+gradename+"&cxueduanid="+cxueduanid+"&title="+title+"&descript="+descript+"&times="+times+"&money="+money+"&status=1&imageServerIds="+imageServerIds+"&audioServerIds="+audioServerIds+"&audioTimes="+audioTimes+"&createdate=${createdate }",
		       async: false,
		       success:function(msg){}    
		});
		window.location.replace("/weixinHelp.app?method=myQuestion&userid=${userid }");
	}else if(method == "saveQuestionByAjaxAndPay"){
	    //调用ajax保存答疑
		var times=$("#in_time").val();
		var money = $("#in_money").val();
		$.ajax({
			type:"post",
	        url:"/weixinHelp.app?method=saveQuestionByAjaxAndPay",
	        data:"userid=${userid}&subjectid="+subjectid+"&subjectname="+subjectname+"&gradeid="+gradeid+"&gradename="+gradename+"&cxueduanid="+cxueduanid+"&title="+title+"&descript="+descript+"&times="+times+"&money="+money+"&status=1&imageServerIds="+imageServerIds+"&audioServerIds="+audioServerIds+"&audioTimes="+audioTimes+"&createdate=${createdate }",
	        async: false,
	        success:function(msg){}    
		});
	     	
	    window.location.replace("/weixinHelp.app?method=myQuestion&userid=${userid }");
	}else{//传递订单号
		//调用ajax保存答疑
		var times=$("#in_time").val();
		$.ajax({
			type:"post",
		       url:"/weixinHelp.app?method=saveQuestionByAjaxAndWxpay",
		       data:"userid=${userid}&subjectid="+subjectid+"&subjectname="+subjectname+"&gradeid="+gradeid+"&gradename="+gradename+"&cxueduanid="+cxueduanid+"&title="+title+"&descript="+descript+"&times="+times+"&money="+money+"&status=1&imageServerIds="+imageServerIds+"&audioServerIds="+audioServerIds+"&audioTimes="+audioTimes+"&createdate=${createdate }",
		       async: false,
		       success:function(msg){}    
		});
	           		        
	    //js返回支付成功，跳转到成功页面，具体支付成功与否，可通过回调函数真正写入数据库
	    var redirecturl = "%2fweixinHelp.app%3fmethod%3dmyQuestion%26userid%3d${userid }";//转码处理
	 	window.location.replace("/wxpay/weixinPay.app?method=paySuccess&userid=${userid}&out_trade_no=" + method + "&redirecturl=" + redirecturl);
	}
}
</script>
<%@ include file="/weixin/help/upload_js.jsp"%>
</body>
</html>