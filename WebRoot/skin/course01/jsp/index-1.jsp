<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.util.date.DateTime"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>院校企业培训项目首页_<%=Constants.PRODUCT_NAME %></title>
<link href="/skin/course01/css/css.css" rel="stylesheet" type="text/css">
</head>

<body style="background-color:#bfddcf;">
<div class="header header03">
	<div class="header_main">
    	<a href="http://cms.hxnlmooc.com/index.shtml" class="header_main_j01 header_main_y01">返回首页</a>
    	 <%
        SysUserInfo s_sysuserinfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
        if(s_sysuserinfo != null && s_sysuserinfo.getUserid() != null){
        %>
        	<a href="/courseCenter.do?method=index" class="header_main_j02 header_main_y02">个人中心</a>
        <%}else{ %>
        	<a href="/courseCenter.do?method=index" class="header_main_j02 header_main_y02">登录</a>
        <%} %>
    </div>
</div>

<div class="content">
	 <div class="content_training_main">
	 		<%
        	List list = (List)request.getAttribute("list");
        	EduCourseInfo model = null;
        	for(int i=0, size=list.size(); i<size; i++){
        		model = (EduCourseInfo)list.get(i);
        	%>
        	<a href="/course/<%=model.getCourseid() %>.htm"><div class="content_training_main_div">
            	<img src="/upload/<%=model.getSketch() %>" />
                <p class="content_training_main_div_p01 content_training_main_div_p001"><%=model.getTitle() %></p>
                <p class="content_training_main_div_p02">主讲：<%=model.getSysUserInfo().getUsername() %></p>
            </div></a>
            <%} %>
        </div><!------content_training_main-e------>
</div>

<div id="foot">
<div class="foot_nr">
	<p>Copyright © 2016-<%=DateTime.getDateYear() %> 全国职业核心能力测评培训网 版权所有 | 京ICP备09025234号</p>
	<p>技术支持：<a href="http://www.edutech.com.cn" target="_blank" style="color: #fff;">北京师科阳光信息技术有限公司</a><script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1262220875' style='display: inline !important;margin-left: 10px;' %3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s19.cnzz.com/z_stat.php%3Fid%3D1262220875%26show%3Dpic' type='text/javascript'%3E%3C/script%3E"));</script>
	</p>
<!-- menu_footer E -->
</div><!--版权内容 结束-->

</div><!--版权 结束-->
</body>
</html>