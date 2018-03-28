<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<div class="leftCon">
	<div class="sideHead clearfix">
		<a href="/courseCenter.do?method=beforeUpdateSelf">
		<div class="head-ico">
			<b class="head-circle"><!-- 这是头像那个灰色的圈，不要放东西。头像是下面的img↓ --></b>
			<%
			SysUserInfo sui = (SysUserInfo)session.getAttribute("s_sysuserinfo");
			if(sui.getPhoto().startsWith("http://")){
			%>
			<img src="${s_sysuserinfo.photo }" alt="我的头像" />
			<%}else{ %>
			<img src="/upload/${s_sysuserinfo.photo }" alt="我的头像" />
			<%} %>
		</div>
		</a>
		<div class="head-links">
			<h3 class="head-name ellipsis" style="display:inline-block;width:80px;word-wrap: break-word;" title="${s_sysuserinfo.username }">${s_sysuserinfo.username }</h3>
			<p class="links">
				<a href="/courseCenter.do?method=beforeUpdateSelf">设置</a><b>|</b><a href="/courseCenter.do?method=myMoney&mark=7">账户</a>
			</p>
		</div>
		<%-- 
		<a class="level-ico" title="等级">LV1</a>
		<div class="level-bar">
			<p style="width:0%;" title="进度0%">
				<!-- 进度是多少，style里面的百分比就改为多少 -->
			</p>
		</div>
		--%>
	</div>
	<div class="sideMenu">
		<logic:equal value="1" name="s_sysuserinfo" property="usertype">
		<c:if test="${s_hascoursemanager == 1}">
		<dl>
			<dt>
				<span>教授课程</span>
			</dt>
			<logic:equal value="1" name="s_sysuserinfodetail" property="canaddcourse">
			<dd <c:if test="${mark == 1}">class="hover"</c:if>>
				<a href="/courseCenter.do?method=createCourse&mark=1"><b class="ico ico-01"></b>创建课程</a>
			</dd>
			</logic:equal>
			<dd <c:if test="${mark == 2}">class="hover"</c:if>>
				<a href="/courseCenter.do?method=courseList&mark=2"><b class="ico ico-02"></b>课程管理</a>
			</dd>
		</dl>
		</c:if> 
		</logic:equal>
		<dl>
			<dt>
				<span>我的课程</span>
			</dt>
			<dd <c:if test="${mark == 3}">class="hover"</c:if>>
				<a href="/courseCenter.do?method=courseStudy&mark=3"><b class="ico ico-03"></b>课程学习</a>
			</dd>
			<dd <c:if test="${mark == 4}">class="hover"</c:if>>
				<a href="/courseCenter.do?method=courseFile&mark=4"><b class="ico ico-04"></b>课程资源</a>
			</dd>
			<!-- 
			<dd <c:if test="${mark == 4}">class="hover"</c:if>>
				<a href="#"><b class="ico ico-05"></b>我的提问</a>
			</dd>
			 -->
			<dd <c:if test="${mark == 5}">class="hover"</c:if>>
				<a href="/courseCenter.do?method=courseCollect&mark=5"><b class="ico ico-07"></b>课程收藏</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>订单中心</span>
			</dt>
			<dd <c:if test="${mark == 6}">class="hover"</c:if>>
				<a href="/courseCenter.do?method=myTrade&mark=6"><b class="ico ico-10"></b>我的订单</a>
			</dd>
			<dd <c:if test="${mark == 7}">class="hover"</c:if>>
				<a href="/courseCenter.do?method=myMoney&mark=7"><b class="ico ico-12"></b>我的账户</a>
			</dd>
		</dl>
		<dl>
			<dt></dt>
			<dd <c:if test="${mark == 8}">class="hover"</c:if>>
				<a href="/courseCenter.do?method=myMessage&mark=8"><b class="ico ico-14"></b>消息通知</a>
			</dd>
		</dl>
	</div>
</div>