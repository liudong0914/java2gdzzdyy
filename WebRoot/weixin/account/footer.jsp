<%@ page contentType="text/html;charset=utf-8"%>
<div id="foot">
	<a href="/weixinAccountIndex.app?method=index&userid=${userid}" class="footer_1">
    	<img src="/weixin/images/footer01.png"/>
    	<p>首页</p>
    </a>
    <a href="/weixinCourse.app?method=getCourseList&userid=${userid}" class="footer_1">
    	<img src="/weixin/images/footer02.png" />
    	<p>课程</p>
    </a>
      <a href="/weixinTextBook.app?method=index&userid=${userid}"  class="footer_1">
    	<img src="/weixin/images/footer05.png" />
    	<p>教材订购</p>
    </a>
    <a href="/weixinAccountIndex.app?method=getNewsByColumn&userid=${userid}" class="footer_1">
    	<img src="/weixin/images/footer03.png" />
    	<p>资讯</p>
    </a>
    <a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}" class="footer_1">
    	<img src="/weixin/images/footer04.png" />
    	<p>个人中心</p>
    </a>
</div>