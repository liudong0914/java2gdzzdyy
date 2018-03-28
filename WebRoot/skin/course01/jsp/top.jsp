<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<link href="/skin/course01/css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function login(){
	var topurl = window.top.location;
	window.top.location = '/plogin.do?method=slogin&redirecturl=' + topurl;
}
function logout(){
	var topurl = window.top.location;
	window.top.location = '/plogin.do?method=slogout&redirecturl=' + topurl;
}
function hidetext(){  
	var mychar = document.getElementById("con");
    mychar.style.display="none"
}  
function showtext(){  
	var mychar = document.getElementById("con");
    mychar.style.display="block"
}
</script>
<div class="fade" id="con">
     <div class="tanchu" >
        	<a href="javascript:hidetext()"></a>       	
			<img src="/skin/course01/images/code_fade.jpg" class="tanchu_img"  />    
            <p class="tanchu_p">打开手机微信客户端右上角+，扫描二维码关注公众号，在线注册绑定账号。</p>
     </div>
</div>
<div class="header">
	<div class="header_main">
    	<a href="/default.html" target="_blank"><img src="/skin/course01/images/logo.png" class="logo" /></a>
        <div class="nav">
        	<a href="/default.html" target="_blank">首页</a>
            <a href="/index/1.htm">院校培训</a>
            <a href="/index/2.htm">退役军人培训</a>
            <a href="/index/3.htm">医护行业培训</a>
        </div>
        <%
        SysUserInfo s_sysuserinfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
        if(s_sysuserinfo != null && s_sysuserinfo.getUserid() != null){
        %>
        <div class="login">
        	<div class="login01">
            	<img src="/skin/course01/images/a03.png" />
                <a href="/courseCenter.do?method=index"><%=s_sysuserinfo.getUsername() %></a>
                <a href="/courseCenter.do?method=index">[个人中心]</a>&nbsp;&nbsp;|&nbsp;<a href="javascript:logout()">[退出]</a>
            </div>
        </div>
        <%}else{ %>
        <div class="login">
        	<div class="login01">
            	<img src="/skin/course01/images/a03.png" />
                <a href="javascript:login()">登录</a>
            </div>
            <div class="login01 login02">
            	<img src="/skin/course01/images/a04.gif" />
                <a href="javascript:showtext()">注册</a>
            </div>
        </div>
        <%} %>
    </div><!------header_main-e------>
</div><!------header-e------>
<!------头部结束------->