<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>课程学习</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script>
function setTab(c){
//	for(i=1;i<=2;i++){
//		var menu=document.getElementById("one"+i);
//		menu.className=i==c?"hover":"";
//	}
	$("#con").html(""); 
	var num=0;
	$.ajax({
		type:"post",
		url:"/weixinCourse.app?method=getMyCourseByAjax",
		data:"pagenum=0&userid=${userid}",
		success:function(data){
			if(data!=""){
				$("#con").html(data); 
			}else{
				$(document).unbind('scroll');
				$("#con").html("<div style='width:100%;background-color:#fff;text-align:center;height:85px;line-height:85px;'>尚未购买课程</div>"); 
			}
			num++;
		}
	});
	$("#zjz").html("加载中<img src='/weixin/images/Loading6.gif'/>");
	$("#zjz").hide();
	$(document).scroll(function(){
	    var scrolltop =$(window).scrollTop();
        var dheight=$(document).height();
        var wheight=$(window).height();
        if(scrolltop==dheight-wheight){
    	  if($("#zjz").is(":hidden")){
 	   		 $("#zjz").show();
 	      }
    	  $.ajax({
				type:"post",
				url:"/weixinCourse.app?method=getMyCourseByAjax",
				data:"pagenum="+num+"&userid=${userid}&type="+c,
				success:function(data){
					var obj=$("#con");
					var objhtml=obj.html();
					if(data!=""){
						obj.html(objhtml+data); 
					}else{
						$("#zjz").html("没有更多数据了!");
						$(document).unbind('scroll');
					}
					num++;
				}
			});
	      }
	});
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body onload="setTab(1)">	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">课程学习</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<!--  <div class="Menubox">
    <ul>
    	<li class="play_menu_ul_li"></li>
        <li id="one1" onclick="setTab(1)" style="width:30%;" class="hover">解题微课</li>
        <li id="one2" onclick="setTab(2)" style="width:30%;" >举一反三</li>
    </ul>
</div>-->
<div class="Contentbox">
     <div id="con">
     	
     </div>
     <div class="zjz" id="zjz">
	加载中<img src="/weixin/images/Loading6.gif"/>
	</div>
</div>
</body>
</html>