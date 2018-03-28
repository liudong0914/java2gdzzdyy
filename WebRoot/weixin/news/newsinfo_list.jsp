<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>资讯列表</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	var num=0;
	$(document).ready(function(){
		if(num==0){
			$.ajax({
				type:"post",
				url:"/weixinAccountIndex.app?method=getNewsByAjax",
				data:"pagenum="+num+"&columnid=${columnid}&userid=${userid}",
				success:function(data){
					if(data!=""){
						$("#newsinfo").html(data);
					}else{
						$("#newsinfo").html("<div style='width:100%;background-color:#fff;text-align:center;height:85px;line-height:85px;'>暂无资讯</div>");
						$(document).unbind('scroll');
					}
					num++;
				}
			});
		}
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
					url:"/weixinAccountIndex.app?method=getNewsByAjax",
					data:"pagenum="+num+"&columnid=${columnid}&userid=${userid}",
					success:function(data){
						var obj=$("#newsinfo");
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
	});
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search">
	<a href="/weixinAccountIndex.app?method=index&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">资讯</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<!-----------------内容------------------>
<div class="class_main library_content" id="newsinfo"></div>
<div class="loading" id="zjz">
	加载中<img class="loadingimg" src="/weixin/images/Loading6.gif"/>
</div>
</body>
</html>