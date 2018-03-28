<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.util.common.Constants"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>回答提问</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/libs/js/sk/tip.js"></script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
<%@ include file="/weixin/index/wxconfig_js.jsp"%>
<script type="text/javascript">
var time=setInterval("residualTimeCalculation()", 1000);
var recoverytime=${recoverytime};
function residualTimeCalculation(){
	recoverytime=recoverytime-1000;
	var rh=Math.floor(recoverytime/1000/3600);
	var rm=Math.floor(recoverytime/1000/60%60);
	var rs=Math.floor(recoverytime/1000%60);
	var rt=(rh<10?("0"+rh):rh)+":"+(rm<10?("0"+rm):rm)+":"+(rs<10?("0"+rs):rs);
	$("#recoverytime").text(rt);
	if(rh<=0&&rm<=0&&rs<=0){
		history.go(0);
	}
} 

var imageServerIds = "";
var audioServerIds = "";
var audioTimes = "";
var imgcount=<%=Constants.UPLOAD_IMAGE_COUNT%>;
var audiocount=<%=Constants.UPLOAD_AUDIO_COUNT%>;

function chooseImage(){
	var ic=imgcount-$("#img_div>div").size()-$("#ans_img>div").size();
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
				var ic=imgcount-$("#img_div>div").size()-$("#ans_img>div").size();
				if(ic==0){
					$("#uploadimg").hide();
				}
		    }
		});
	}
}

function deleteImg(imgid){
	$("#img"+imgid).remove();
	var ic=imgcount-$("#img_div>div").size()-$("#ans_img>div").size();
	if(ic<imgcount){
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
					"<a href=\"javascript:playAudio1('"+audiowxid+"',"+audionum+")\"><img src='/weixin/images/d01.png' aid=\""+audiowxid+"\" atimes=\""+second+"\" id=\"audioimg"+audionum+"\" /></a>"+
				"</div>"+
				"<p class=\"answer_student_ygm_yy_p01\">"+second+"</p>"+
			"</div>";
	$("#audio_div").html(audio);
	var ic=audiocount-$("#audio_div>div").size()-$("#ans_audio>div").size();
	if(ic==0){
		$("#ly").hide();
	}
}

function playAudio1(aId,divid){
	$("#audio_div>div").each(function(){
		$(this).find("img").eq(1).attr("src","/weixin/images/d01.png");
	});
	$("#audioimg"+divid).attr("src","/weixin/images/d01.gif");
	wx.playVoice({
	    localId: aId // 需要播放的音频的本地ID，由stopRecord接口获得
	});
	wx.onVoicePlayEnd({
	    success: function (res) {
	        var localId = res.localId; // 返回音频的本地ID
	        if(aId==localId){
	        	$("#audioimg"+divid).attr("src","/weixin/images/d01.png");
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
	var ic=audiocount-$("#audio_div>div").size()-$("#ans_audio>div").size();
	if(ic<audiocount){
		$("#ly").show();
	}
}

function saveAnswer(stype){
	var content=$("#content").val();
	if(content==""){
		$("#answer_span").html("(请输入答案)");
		$("#bg,.payment_time_mask").hide();	
		return;
	}
	//点击支付弹出蒙板，避免重复点击
    document.getElementById("div_hidden").style.display = "block";
	uploadFile(stype);
}

function postData(status){
	if(imageServerIds != "" || audioServerIds != ""){
		showTipDiv("上传完成！", 120, 50, 5);
	}
	var content=$("#content").val();
	content = encodeURIComponent(encodeURIComponent(content));
	//调用ajax保存答疑回复
	$.ajax({
		type:"post",
	       url:"/weixinHelp.app?method=saveAnswerByAjax",
	       data:"userid=${userid}&orderid=${order.orderid}&questionid=${question.questionid}&answerid=${answer.answerid}&content="+content+"&status="+status+"&imageServerIds="+imageServerIds+"&audioServerIds="+audioServerIds+"&audioTimes="+audioTimes,
	       async: false,
	       success:function(msg){
	           //或者等待1秒再显示
	    	   //location.href="/weixinHelp.app?method=info&userid=${userid}&questionid=${question.questionid}";
	    	   window.location.replace("/weixinHelp.app?method=myQuestion&userid=${userid}");
	       }    
	});	
}

function showFaBu(){
	$("#bg").css({height: $(document).height()});
	$("#bg,.payment_time_mask").show();	
}

function closeFaBu(){
	$("#bg,.payment_time_mask").hide();	
}
function deleteFile(fid){
	$.ajax({
		type:"post",
       url:"/weixinHelp.app?method=deleteFile",
       data:"fileid="+fid,
       async: false,
       success:function(msg){
    	   $("#f"+fid).remove();
    	   	var ac=audiocount-$("#audio_div>div").size()-$("#ans_audio>div").size();
    		if(ac>0){
    			$("#ly").show();
    		}
    		var ic=imgcount-$("#img_div>div").size()-$("#ans_img>div").size();
    		if(ic>0){
    			$("#uploadimg").show();
    		}
       }
	});	
	
}
$(function(){
	if($("#ans_img>div").size()>=imgcount){
		$("#uploadimg").hide();
	}	
	if($("#ans_audio>div").size()>=audiocount){
		$("#ly").hide();
	}
});
</script>
</head>
<body>	
<div id="bg"></div>
<div class="payment_time_mask" style="margin-top:30%;">
	<div class="question_fb" >
    	<a href="javascript:closeFaBu()"  class="wk_buy_a" >
        	<img src="/weixin/images/img10.png" />
        </a> 
    	<p  class="question_fb_p" >确认发布当前提问内容吗？</p>
        <div class="menu_div_question_div">
            <a href="javascript:saveAnswer(0)"  class="menu_div_question_div_a">保存草稿</a>
            <a href="javascript:saveAnswer(1)">确认发布</a>
        </div>
     </div>
</div>

<div id="header">
</div>
<div class="search search_write" style="z-index:666666;">
	<a href="javascript:location.href='/weixinHelp.app?method=info&questionid=${question.questionid}&userid=${userid}';"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">发布答案</p>
    </div>
    <div class="payment_time_title" style=" display:inline-block;float:right; right:10px; color:#e60012; top:0px; font-size:16px;">   
    <a href="javascript:showFaBu()" class="write">发布</a>
    </div>
</div>

<div class="answer_student_xq answer_student_xq_teacher">
        <div class="answer_student_font">
            <p class="answer_student_font_pright answer_student_font_pright01">${question.title}</p>
        </div>
         <p class="answer_student_p_teacher">回答剩余时限：<span id="recoverytime" style="margin-left:15px;">00:00:00</span></p>
</div>
<div class="Published">
	<p>答案<span id="answer_span"></span></p>
    <div class="Published_div">
    	<textarea type="text" id="content" class="Published_textarea"  placeholder="请输入答案" style="color:#000;">${answer.content}</textarea>
    </div>
</div>

<div class="Published">
	<p>语音与图片（选填 ）</p>
    <div class="Published_div01">
    	<div id="img_div">
    		
    	</div>
		<div id="ans_img">
			<c:forEach items="${answerFileList}" var="file">
       			<c:if test="${file.filetype eq '1'}">
       				<div class="Published_div_yy" id="f${file.fileid }">
					    <a href="javascript:deleteFile(${file.fileid})"><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>
					    <img src="/upload/${file.thumbpath}" class='Published_div_yy_img01' />
					</div>
       			</c:if>
       		</c:forEach>
		</div>
		<div class="Published_div_yy" onclick="chooseImage()" id="uploadimg" >
            <img src="/weixin/images/d06.png" class="Published_div_yy_img02" />
       	</div>
       	<div style="clear:both;"></div>
       	<div id="audio_div">
       		
       	</div>
       	<div id="ans_audio">
       		<c:forEach items="${answerFileList}" var="file">
       			<c:if test="${file.filetype eq '2'}">
       				<div class='Published_div_yy01' id="f${file.fileid}">
						<a href='javascript:deleteFile(${file.fileid})'><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>
						<div class="answer_student_ygm_yy">
							<div class="answer_student_ygm_img answer_student_ygm_yy_teacherimg">
								<a href="javascript:playAudio('<%=MpUtil.HOMEPAGE %>/upload/${file.mp3path}', 'audio_${file.fileid}')"><img src='/weixin/images/d01.png' id="audio_${file.fileid}" /></a>
							</div>
							<p class="answer_student_ygm_yy_p01">${file.timelength}</p>
						</div>
					</div>
       			</c:if>
       		</c:forEach>
       	</div>
        <div class="Published_div_yy" onclick="uploadAudio()" id="ly">
            <img src="/weixin/images/d07.png" class="Published_div_yy_img02" />
        </div>
    </div>
</div>
<p class="password_p password_p01" style="padding-right:5px;">
1、不可恶意抢单不回答问题，也不可乱回答问题，否则被投诉后，账号可能会被禁用。<br />
2、回答提问时请尽量描述清楚答案内容，也可进行语音回复和拍照回复。<br />
3、请控制在回答剩余时限前进行提交回复，否则当前订单失效，无法获取其对应的学币。<br />
4、若当前答疑设置的问题金额大于0学币时，回答此问题可获得问题金额百分之八十的奖赏，奖赏的学币将自动充值到您当前账户余额。<br />
5、问题交易成功后，当前答疑提问可进行售卖给其他同学，自己可从中获取部分收益，详情请进入“个人中心-<a href="/weixinHelp.app?method=questionDescript&userid=${userid}">答疑规则</a>”进行查看。
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
<p class="password_p password_p01"></p> 
<div id="div_hidden" style="width:100%; height:100%; overflow:hidden; position:absolute; top:0px; z-index:999999999;background:rgba(0,0,0,0.5); display:none;"></div>
<%@ include file="/weixin/help/playaudio_js.jsp"%>
<%@ include file="/weixin/help/upload_js.jsp"%>

<a href="javascript:scanCode()"  class="Menubox01_button">扫一扫（登录电脑端上传答疑视频）</a>
</body>
</html>