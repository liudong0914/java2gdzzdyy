<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.util.date.DateTime"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>退役军人培训项目首页_<%=Constants.PRODUCT_NAME %></title>
<link href="/skin/course01/css/css.css" rel="stylesheet" type="text/css">
</head>

<body style="background-color:#f7d8cb;">
<div class="header header02">
	<div class="header_main">
    	<a href="http://cms.hxnlmooc.com/index.shtml" class="header_main_j01">返回首页</a>
         <%
        SysUserInfo s_sysuserinfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
        if(s_sysuserinfo != null && s_sysuserinfo.getUserid() != null){
        %>
        	<a href="/courseCenter.do?method=index" class="header_main_j02">个人中心</a>
        <%}else{ %>
        	<a href="/courseCenter.do?method=index" class="header_main_j02">登录</a>
        <%} %>
    </div>
</div>

<div class="content">
	<a href="#"><div class="content_div_jr01">
    	<p class="content_div_jr01_title">职业社会能力训练</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;职业精神与职业成功力</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;与人交流与职场发展力</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;与人合作与团队协调力</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;解决问题与工作执行力</p>
        <p class="content_div_jr01_p02"><span>▪</span>&nbsp;基础级&nbsp;<span>▪</span></p>
    </div></a>
    <a href="#"><div class="content_div_jr01 content_div_jr02">
    	<p class="content_div_jr01_title">职业方法能力训练</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;自我学习与管理</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;信息处理与数字应用</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;创新方法与应用</p>
        <p class="content_div_jr01_p02"><span>▪</span>&nbsp;基础级&nbsp;<span>▪</span></p>
    </div></a>
    <a href="#"><div class="content_div_jr01 content_div_jr03">
    	<p class="content_div_jr01_title">退役军人就业与创业指导</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;规划你的职业生涯</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;自主择业与灵活就业</p>
        <p class="content_div_jr01_p"><span>▪</span>&nbsp;创办你的公司</p>
    </div></a>
    <div class="content_div_jr04">
    	<p class="content_div_jr04_title">岗位技能培训平台</p>
    	<p class="content_div_jr04_font"><span>▪</span>&nbsp;网站：<a href="#">教育网</a></p>
    </div>
     <div class="content_div_jr04 content_div_jr05">
    	<p class="content_div_jr04_title">通识教育学习平台</p>
    	<p class="content_div_jr04_font"><span>▪</span>&nbsp;网站：<a href="#">教育网</a></p>
    </div>
    <div class="content_div_jr04 content_div_jr06">
    	<p class="content_div_jr04_title">通识教育学习平台</p>
    	<p class="content_div_jr04_font"><span>▪</span>&nbsp;网站：<a href="#">教育网</a></p>
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