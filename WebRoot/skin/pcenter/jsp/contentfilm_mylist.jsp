<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>个人中心</title>
<link type="text/css" rel="stylesheet" href="/skin/pcenter/css/style.css">
<script type="text/javascript" src="/skin/pcenter/js/jquery-1.8.2.min.js"></script>

<!--框架必需start-->
<script type="text/javascript" src="../../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<script type="text/javascript" src="../../../libs/js/page_comm.js"></script>

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%@ include file="js.jsp"%>
<%
String type = (String)request.getAttribute("type");
%>
</head>
<body style="background-color:#f9f9f9;">
<!------头部-------->
<%@ include file="top.jsp"%>
<!------内容-------->
<form name="pageForm" method="post">
<div class="personal">
	<%@ include file="left.jsp" %>
    <div class="information_right">
    	<div class="information_right_top">
            <ul>
            	<li id="one1" class="<%if("1".equals(type)){ %>hover<%} %>"><a href="/pcenter.do?method=mycontentfilmlist&mark=5&type=1">解题微课</a></li>
                <li id="one2" class="<%if("2".equals(type)){ %>hover<%} %>"><a href="/pcenter.do?method=mycontentfilmlist&mark=5&type=2">举一反三</a></li>
            </ul>
        </div>
        <div class="my_class">
        <logic:notEmpty name="pagelist" property="datalist">
	         <c:forEach items="${pagelist.datalist}" var="model" varStatus="status">
	            <a href="#" onclick="window.open('/pcenter.do?method=play&bookid=${model.bookid }&bookcontentid=${model.contentid }&type=${model.type}&userid=${model.userid }')"><div class="listen_main_module listen_main_module_my">
	                <img src="/upload/${model.flagl }" />
	                <p>${model.flago }</p>
	            </div></a>
	          </c:forEach>   
	          <div class="page_01 page_02">
	             ${string}
	          </div><!--page_01 E -->
	         <input type="hidden" name="startcount" id="startcount" value="<bean:write name="startcount"/>" />
         </logic:notEmpty>
      	 <logic:empty name="pagelist" property="datalist">
      			<h3>暂无数据</h3>
      	 </logic:empty>
        </div>
    </div><!----information_right-e---->
</div><!----personal-e---->
</form>
<%@ include file="footer.jsp"%>
<script>
var num=<bean:write name="pagelist" property="pageCount" />

function addFilmPix(filmid){
	var mark="1";
	var diag = new top.Dialog();
	diag.Title = "微课视频";
	diag.URL = '/vwhFilmPixAction.do?method=list&filmid='+filmid+"&mark="+mark;
	diag.Width = 800;
	diag.Height = 600;
	diag.ShowMaxButton=true;
	diag.CancelEvent = function(){
			diag.close();
	};
	diag.show();
}
</script>
</body>
</html>
