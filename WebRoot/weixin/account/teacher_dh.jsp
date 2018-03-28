<%@ page contentType="text/html;charset=utf-8"%>
<script src="/weixin/js/jquery-1.10.2.js" type="text/javascript"></script>
<script type="text/javascript">
function showDH(){
	if($(".payment_time_mask00").css("display") == "block"){
		$(".payment_time_mask00").hide();
	}else{
    	$(".payment_time_mask00").css({height: $(window).height()});
    	$(".payment_time_mask00").show();
	}
}
</script>
	<div id="bg01">
	</div>
	<div class="payment_time_mask00">     	
        <a href="/weixinAccountIndex.app?method=index&userid=${userid }" class="fade_module01" target="_top">
			<img src="/weixin/images/icon09.png"  class="fade_module01_img"/>
		    <p>首页</p>
		    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
		</a>
		 <a href="/weixinCourse.app?method=getCourseList&userid=${userid}" class="fade_module01" target="_top">
			<img src="/weixin/images/icon10.png"  class="fade_module01_img"/>
		    <p>课程</p>
		    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
		</a>
		<a href="/weixinAccountIndex.app?method=getNewsByColumn&userid=${userid}" class="fade_module01" target="_top">
			<img src="/weixin/images/icon08.png"  class="fade_module01_img"/>
		    <p>资讯</p>
		    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
		</a>
		 <a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}" class="fade_module01" target="_top">
			<img src="/weixin/images/icon07.png"  class="fade_module01_img"/>
		    <p>个人中心</p>
		    <img src="/weixin/images/icon15.png"  class="fade_module01_img01"/>
		</a>
    </div>
