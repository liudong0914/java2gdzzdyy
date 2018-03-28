<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.util.date.DateTime"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>医护行业培训项目首页_<%=Constants.PRODUCT_NAME %></title>
<link href="/skin/course01/css/css.css" rel="stylesheet" type="text/css">
</head>

<body style="background-color:#c7eeff;">
<div class="header">
	<div class="header_main">
    	<a href="http://cms.hxnlmooc.com/index.shtml" class="header_main_a01">返回首页</a>
        <%
        SysUserInfo s_sysuserinfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
        if(s_sysuserinfo != null && s_sysuserinfo.getUserid() != null){
        %>
        	<a href="/courseCenter.do?method=index" class="header_main_a02">个人中心</a>
        <%}else{ %>
        	<a href="/courseCenter.do?method=index" class="header_main_a02">登录</a>
        <%} %>
    </div>
</div>

<div class="content">
	<a href="#"><div class="content_div01">
    	<p class="content_div01_title">职业社会能力训练</p>
        <p class="content_div01_p"><span>▪</span>&nbsp;与人交流与职场发展力</p>
        <p class="content_div01_p"><span>▪</span>&nbsp;与人合作与团队协调力</p>
        <p class="content_div01_p"><span>▪</span>&nbsp;解决问题与工作执行力</p>
        <p class="content_div01_p02"><span>▪</span>&nbsp;基础级&nbsp;<span>▪</span></p>
    </div></a>
    <a href="#"><div class="content_div01 content_div02">
    	<p class="content_div01_title">职业方法能力训练</p>
        <p class="content_div01_p"><span>▪</span>&nbsp;自我学习与自我管理</p>
        <p class="content_div01_p"><span>▪</span>&nbsp;信息处理与新技术应用</p>
        <p class="content_div01_p"><span>▪</span>&nbsp;数字应用与绩效运营</p>
        <p class="content_div01_p"><span>▪</span>&nbsp;创新方法与应用</p>
        <p class="content_div01_p02"><span>▪</span>&nbsp;基础级&nbsp;<span>▪</span></p>
    </div></a>
    <div class="content_div03">
    	<p>链接网址：<a href="#">蓝墨云教材</a></p>
    </div>
    <div class="content_div03 content_div04">
    	<p>链接网址：<a href="#">蓝墨云教材</a></p>
    </div>
</div>

<div id="foot">
<div class="foot_nr">
	<p>Copyright © 2016-<%=DateTime.getDateYear() %> 全国职业核心能力测评培训网 版权所有 | 京ICP备09025234号</p>
	<p>技术支持：<a href="http://www.edutech.com.cn" target="_blank" style="color: #fff;">北京师科阳光信息技术有限公司</a><script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1262220875' style='display: inline !important;margin-left: 10px;' %3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s19.cnzz.com/z_stat.php%3Fid%3D1262220875%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script></p>
<!-- menu_footer E -->
</div><!--版权内容 结束-->

</div><!--版权 结束-->
</body>
</html>