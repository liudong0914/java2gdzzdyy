<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=uft-8" />
<title>未授权</title>
<style>
*{margin:0;  padding:0;}
body{font-family:"宋体"; font-size:12px; color:#666666; line-height:20px;}
img,li,dt,dd,span{display:block;}
#baocuo{width:880px; height:270px; border:#e2e2e2 1px solid; margin:110px auto 0 auto; position:relative;}
#baocuo h3{width:675px; padding-top:54px; font-size:18px; color:#068ae3; text-align:left; padding-left:112px;}
</style>
</head>

<body style="background-color:#fbfcfa;">
<div id="baocuo">
<h3><span style="float:left; position:absolute; top:40px; left:54px;"><img src="/libs/images/sk/logo.jpg" width="47" height="62" /></span>
<%
	String result = request.getParameter("result");
	if(result != null && !"".equals(result)){
		String promptinfo = null;
		if("1".equals(result)){
			promptinfo = "当前产品未获授权!<br>请与北京师科学苑网络有限公司联系!";
		}
		if("2".equals(result)){
			promptinfo = "产品试用日期已到期!<br>请与北京师科学苑网络有限公司联系!";
		}
		if("3".equals(result)){
			promptinfo = "当前产品未获正式授权!<br>请与北京师科学苑网络有限公司联系!";
		}
	
		out.print(promptinfo+"<br/>");
 	}else{%>
 	未找到相关文件或服务器内部错误！<br />
 	请联系管理员或稍后重试! 
<%} %>
</h3>
<span style="float:left; position:absolute; top:45px; right:64px;"><img src="/libs/images/sk/sorry.jpg"  /></span>
<span style="float:left; position:absolute; bottom:55px; left:110px;">
公司网站：<a href="http://www.wkmk.com" target="_blank">http://www.wkmk.com</a>
<br />
联系电话：15201212201 <br />
电子邮件：<a href="mailto:service@wkmk.com">service@wkmk.com</a>
</span>
</div>
</body>
</html>