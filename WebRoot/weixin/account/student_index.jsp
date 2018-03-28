<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.cms.bo.CmsImageInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title><%=Constants.PRODUCT_NAME %></title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<link href="/weixin/css/banner.css" rel="stylesheet" type="text/css">
<script src="/libs/js/jquery.js" language="javascript" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="/weixin/js/public.js"></script>
<script type="text/javascript" src="/weixin/js/jquery.SuperSlide.js"></script>
<script language="javascript" type="text/javascript">
	var num=0;
	$(document).ready(function(){
		if(num==0){
			$.ajax({
				type:"post",
				url:"/weixinCourse.app?method=getCourseInfoByAjax",
				data:"pagenum="+num+"&userid=${userid}",
				success:function(data){
					if(data!=""){
						$("#filmcontent").html(data);
					}else{
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
					url:"/weixinCourse.app?method=getCourseInfoByAjax",
					data:"pagenum="+num+"&userid=${userid}",
					success:function(data){
						var obj=$("#filmcontent");
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
		
		setInterval('AutoScroll("#scrollDiv")',3000);
	});
	
	function AutoScroll(obj){
        $(obj).find("ul:first").animate({
                marginTop:"-28px"
        },1500,function(){
                $(this).css({marginTop:"0px"}).find("a:first").appendTo(this);
        });
	}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>
<div class="search">
	<a href="javascript:scanCode()"><div  class="search_left">
    	<img src="/weixin/images/icon03.png" />
        <p>扫一扫</p>
    </div></a>
    <div  class="search_input">
    	<a href="/weixinVod.app?method=inSearchIndex&userid=${userid}">搜索课程微课</a>   
    </div>
    <a href="/weixinAccountIndex.app?method=messageList&userid=${userid}">
	    <div  class="search_left search_right">
		    <div class="search_right_p">
		    	<img src="/weixin/images/icon05.png" />    	
		        <p>${unread}</p>
		    </div>
	        <p>消息</p>
	    </div>
    </a>
</div>
<div class="search_padding"></div>

<!-- 图片滚动开始 -->
<div class="com-content" style="padding-bottom:5px;">
	<div class="com-header-area" id="js-com-header-area"></div>
    <div class="com-content-area" id="js-com-content-area">
    <script type="text/javascript">
    $(function(){
        $('.pxui-show-more').first().find('a').click();  
        $('.pxui-show-more').last().find('a').click();  
    });
	</script>
	<div class="page-role home-page">
	<script src="/weixin/js/jquery.touchslider.min.js"></script>
	<script src="/weixin/js/index.js"></script>
	<script>
	jQuery(function($) {
		$(window).resize(function(){
			var width=$('#js-com-header-area').width();
			$('.touchslider-item a').css('width',width);
			$('.touchslider-viewport').css('height',300*(width/640));
		}).resize();	
		$(".touchslider").touchSlider({mouseTouch: true, autoplay: true});
	});
	</script>
		<div class="touchslider">
    		<div class="touchslider-viewport">
    			<%
    			List adlist = (List)request.getAttribute("images");
    			int adsize = 0;
    			if(adlist != null) adsize = adlist.size();
    			CmsImageInfo vii = null;
    			String linkurl = null;
    			for(int i=0; i<adsize; i++){
    				vii = (CmsImageInfo)adlist.get(i);
    				if(vii.getUrl() != null && !"".equals(vii.getUrl())){
    					linkurl = vii.getUrl();
    				}else{
    					linkurl = "javascript:;";
    				}
    			%>
				<div class="touchslider-item"><a href="<%=linkurl%>"><img src="/upload/<%=vii.getSketch() %>" style="vertical-align:top;"/></a></div>
				<%} %>
		    </div>
    		<div class="touchslider-navtag">
    			<%for(int i=0; i<adsize; i++){ %>
				<span class="touchslider-nav-item <%if(i==0){ %>touchslider-nav-item-current<%}%>"></span>
				<%} %>
		    </div>
		</div>
	</div>
	</div>
</div>
<!-- 图片滚动结束 -->

<!--资讯开始-->
<div class="news">
	<p  class="news_p">资讯</p>
    <div  class="news_font">
        <div id="scrollDiv">
		  <ul>
		  <logic:iterate id="model" name="newslist">
		    <a href="/weixinAccountIndex.app?method=getNews&newsid=${model.newsid}&userid=${userid}" target="_parent"><li>${model.title}</li></a>
		  </logic:iterate>
		  </ul>
		</div>
    </div>
</div>
<!--资讯结束-->

<!--试听专享开始-->
<img src="/weixin/images/img06.png" class="listen" />
<div class="listen_main" id="filmcontent">
</div>
<div class="zjz" id="zjz">
	加载中<img src="/weixin/images/Loading6.gif"/>
</div>
<!--试听专享结束-->
<%@ include file="/weixin/account/footer.jsp"%>
<%@ include file="/weixin/account/scan0.jsp"%>
</body>
</html>