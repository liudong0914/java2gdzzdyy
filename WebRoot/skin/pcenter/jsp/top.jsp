<%@ page contentType="text/html; charset=utf-8" %>
<div class="header">
    <div class="top">
        <a href="/index.htm"><img src="/skin/pcenter/images/logo.png"  class="logo"/></a>
        <div class="top_right">
            <logic:notEmpty name="s_sysuserinfo" property="username">
            		<p class="top_right_p" style="margin-right: 22px;margin-left: 0px;">${s_sysuserinfo.username}</p>
           		 <a href="/sysUserLoginAction.do?method=logout"  class="top_right_a">退出</a>
           	</logic:notEmpty>
           	<p class="top_right_p">客服电话：010-64034373 (工作日:08:00 ~ 17:00)</p>
        </div>
    </div><!------头部结束-------->
</div>