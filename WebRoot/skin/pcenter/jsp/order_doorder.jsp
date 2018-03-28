<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
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

<!--数字分页start-->
<script type="text/javascript" src="../../../libs/js/nav/pageNumber.js"></script>
<script type="text/javascript" src="../../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../../libs/js/sk/comm.js"></script>
<!--数字分页end-->

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
<%@ include file="js.jsp"%>
<script>
$(document).ready(function(){
	getVideo();
	getImg();
	getMp3();
});

function getVideo(){
	var orderid = document.getElementById("orderid").value;
	var num0 = document.getElementById("num0").value;
	$.ajax({
        type:"post",
        url:"/pcenter.do?method=ajaxGetContent",
        data:"orderid=" + orderid,
        success:function(msg){
        	$("#video_div").html(msg)
        	var ic=num0-$("#video_div>div").size();
        	if(ic<=0){
        		$("#uploadimg0").hide();
        	}
			}
	});
}

function getImg(){
	var orderid = document.getElementById("orderid").value;
	var num = document.getElementById("num").value;
	$.ajax({
        type:"post",
        url:"/pcenter.do?method=ajaxGetContent2",
        data:"orderid=" + orderid,
        success:function(msg){
        	$("#img_div").html(msg)
        	var ic=num0-$("#img_div>div").size();
        	if(ic<=0){
        		$("#uploadimg").hide();
        	}
			}
	});
}

function getMp3(){
	var orderid = document.getElementById("orderid").value;
	$.ajax({
        type:"post",
        url:"/pcenter.do?method=ajaxGetContent3",
        data:"orderid=" + orderid,
        success:function(msg){
        	$("#mp3_div").html(msg)
			}
	});
}


  function GetRTime(){
	var time = document.getElementById("zxHelpOrder.enddate").value;
	var datetimeArray = time.split(' ');
    var dateArray = datetimeArray[0].split('-');
    var timeArray = datetimeArray[1].split(':');
    var EndTime = new Date(dateArray[0],(dateArray[1]-1),dateArray[2],timeArray[0],timeArray[1],timeArray[2]).getTime();//月份是实际月份-1
    var NowTime = new Date().getTime();
    //yyyy/MM/dd HH:mm:ss
    var t =EndTime - NowTime;
    var d=0;
    var h=0;
    var m=0;
    var s=0;
    if(t>=0){
      d=Math.floor(t/1000/60/60/24);
      h=Math.floor(t/1000/60/60%24);
      m=Math.floor(t/1000/60%60);
      s=Math.floor(t/1000%60);
    }
    
    if(t<0){
    	window.location.href="/pcenter.do?method=orderList&status=1&mark=4&point=1";
    }
    document.getElementById("endtime").innerHTML = "回答时限："+d + "天" +h + "时"+m + "分"+s + "秒";
  }
  setInterval(GetRTime,0);
</script>
</head>
<body style="background-color:#f9f9f9;">
<!------头部-------->
<%@ include file="top.jsp"%>
<!------内容-------->
<html:form action="/pcenter.do" method="post">
<div class="personal">
	<%@ include file="left.jsp" %>
    <div class="information_right">
    	<div class="information_right_top">
			<p>回复答疑</p>
        </div>
        <div class="question">
           <p class="answer_fb">${zxHelpQuestion.descript }</p>   
           <p class="answer_student_p_teacher" id='endtime'>回答时限：
           </p></br>    
           <textarea type="text" name="content" id="content" class="answer_textarea" placeholder="请输入回复内容" style="width:700px;background:none;"  >${content }</textarea>    
           <a href="#" onclick="saveRecord()" class="answer_fb_a">提交回复</a>
           <a href="/pcenter.do?method=orderList&status=${status }&mark=4&point=${point}&startcount=${startcount}"  class="answer_fb_a">返回</a>
           <div class="detail">
                <p class="detail_title detail_title01">视频（选填）</p>
                	<div id="video_div">
    				</div>
					<div class="Published_div_yy" onclick="diagAddRecord('/zxHelpOrderAction.do?method=videoAdd')" id="uploadimg0" style="width: 120px;height: 45px;">
            			<img src="/weixin/images/icon10.png" class="Published_div_yy_img02" />
       				</div>
           	</div>
            <div class="detail">
                <p class="detail_title detail_title01">图片（选填）</p>
                	<div id="img_div">
    				</div>
					<div class="Published_div_yy" onclick="diagAddRecord2('/zxHelpOrderAction.do?method=pictureAdd')" id="uploadimg" style="width: 200px;height: 150px;">
            			<img src="/skin/pcenter/images/bphoto.png" class="Published_div_yy_img02" style="width: 125px;height: 125px;margin-top:-62px;margin-left:-60px;"/>
       				</div>
           </div>
           <div class="detail">
           		<div id="mp3_div">
    			</div>
           </div>
        </div><!----information_right_main-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
	<input type="hidden" name="zxHelpOrder.enddate" id="zxHelpOrder.enddate" value='<bean:write property="enddate" name="model"/>' />
	<input type="hidden" name="zxHelpFileId"  id="zxHelpFileId"/>
	<input type="hidden" name="zxHelpFileIdpicture"  id="zxHelpFileIdpicture"/>
	
	<input type="hidden" name="zxHelpOrder.orderid" id="orderid" value='<bean:write property="orderid"  name="model"/>'/>
	<input type="hidden" name="zxHelpOrder.questionid" value='<bean:write property="questionid"  name="model"/>'/>
	
	<input type="hidden" name="num" id="num" value="<%=Constants.UPLOAD_IMAGE_COUNT %>"/>
	<input type="hidden" name="num0" id="num0" value="<%=Constants.UPLOAD_VIDEO_COUNT %>"/>
	<input type="hidden" name="startcount" id="startcount" value="${startcount}"/>
</html:form>
<%@ include file="footer.jsp"%>

<script>
function saveRecord(){
	var startcount = document.getElementById("startcount").value;
		var objectForm = document.sysUserInfoActionForm;
		var content = document.getElementById("content").value;
		if("" == content){
			alert("回复内容不能为空，请重新输入！");
			return flase;
		}
	  	objectForm.action="/pcenter.do?method=<bean:write name="act"/>&point=<bean:write name="point"/>&status=<bean:write name="status"/>&startcount="+startcount;
	  	objectForm.submit();
}

function diagAddRecord(url){
	var num = document.getElementById("num0").value;
	var ic=num-$("#video_div>div").size();
	if(ic>0){
		var diag = new top.Dialog();
		diag.Title = "添加视频";
		var urls=url+"&num="+ic; 
		diag.URL = urls;
		diag.Width = 850;
		diag.Height = 500;
		diag.ShowMaxButton=true;
		diag.CancelEvent = function(){
			if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
				var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
				var videoId=document.getElementById('zxHelpFileId').value;
				if("" == videoId){
					document.getElementById('zxHelpFileId').value = uploadimageurl;
				}else{
					document.getElementById('zxHelpFileId').value = videoId +","+ uploadimageurl;
				}
				
				var uploadimageurl1 = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl1').value;
				var abcArray = new Array;
				abcArray = uploadimageurl1.split(',');
				var images=$("#video_div").html();
				for(i=0;i<abcArray.length;i++){
			        	images+="<div class='Published_div_yy' style='width:120px;height:45px;' id='video"+abcArray[i].split(':')[0]+"'>"+
						            "<a href='javascript:deleteVideo("+abcArray[i].split(':')[0]+")'><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>"+
						            "<img src='/weixin/images/icon10.png' class='Published_div_yy_img02'>"+abcArray[i].split(':')[1]+"</img>"+
						       	"</div>";
				}
				$("#video_div").html(images);
				var ic=num-$("#video_div>div").size();
				if(ic==0){
					$("#uploadimg0").hide();
				}
			}
			diag.close();
		};
		diag.show();
	}
}
function diagAddRecord2(url){
	var num = document.getElementById("num").value;
	var ic=num-$("#img_div>div").size();
	if(ic>0){
		var diag = new top.Dialog();
		diag.Title = "添加图片";
		var urls=url+"&num="+ic; 
		diag.URL = urls;
		diag.Width = 850;
		diag.Height = 500;
		diag.ShowMaxButton=true;
		diag.CancelEvent = function(){
			if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl2')){
				var uploadimageurl2 = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl2').value;
				var pictureId= document.getElementById('zxHelpFileIdpicture').value;
				if("" == pictureId){
					document.getElementById('zxHelpFileIdpicture').value =uploadimageurl2;
				}else{
					document.getElementById('zxHelpFileIdpicture').value = pictureId +","+ uploadimageurl2;
				}	
				
				var uploadimageurl3 = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl3').value;
				var abcArray = new Array;
				abcArray = uploadimageurl3.split(',');
				var images=$("#img_div").html();
				for(i=0;i<abcArray.length;i++){
			        	images+="<div class='Published_div_yy' style='width:200px;height:150px;' id='img"+abcArray[i].split(':')[0]+"'>"+
						            "<a href='javascript:deleteImg("+abcArray[i].split(':')[0]+")'><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>"+
						            "<img src='/upload/"+abcArray[i].split(':')[1]+"' class='Published_div_yy_img01' />"+
						       	"</div>";
				}
				$("#img_div").html(images);
				var ic=num-$("#img_div>div").size();
				if(ic==0){
					$("#uploadimg").hide();
				}
		    }
			diag.close();
		};
		diag.show();
	}
}

function deleteImg(imgid){
	$("#img"+imgid).remove();
	var num = document.getElementById("num").value;
	var ic=num-$("#img_div>div").size();
	if(ic<=num){
		$("#uploadimg").show();
	}
	$.ajax({
        type:"post",
        url:"/pcenter.do?method=ajaxDelImg",
        data:"imgid=" + imgid,
        success:function(msg){
        	
			}
	});
}
function deleteVideo(imgid){
	$("#video"+imgid).remove();
	var num = document.getElementById("num0").value;
	var ic=num-$("#video_div>div").size();
	if(ic<=num){
		$("#uploadimg0").show();
	}
	$.ajax({
        type:"post",
        url:"/pcenter.do?method=ajaxDelImg",
        data:"imgid=" + imgid,
        success:function(msg){
        	
			}
	});
}
</script>
</body>
</html>
