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
<script type="text/javascript">
var num=<bean:write name="pagelist" property="pageCount" />
</script>
<%
String status = (String)request.getAttribute("status");
String point = (String)request.getAttribute("point");
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
            	<li id="one1" class="<%if("1".equals(point)){ %>hover<%} %>"><a href="/pcenter.do?method=orderList&status=1&mark=4&point=1">未回复答疑</a></li>
                <li id="one2" class="<%if("2".equals(point)){ %>hover<%} %>"><a href="/pcenter.do?method=orderList&status=2&mark=4&point=2">已回复答疑</a></li>
                <li id="one3" class="<%if("3".equals(point)){ %>hover<%} %>"><a href="/pcenter.do?method=orderListByComplation&status=2&mark=4&point=3">被投诉答疑</a></li>	
            </ul>
        </div>
        <div class="question">
	        <logic:notEmpty name="pagelist" property="datalist">
	        <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
	            <div class="answer_student">
	                <div class="answer_student_top">
	                    <img src="/skin/pcenter/images/img.jpg"/>
	                    <p>${model.flago }</p>
	                    	<p class="answer_student_top_p">${model.flagl }前</p>
	                </div>
	               <!--   <c:if test="${point == '3'}">
	                	<div class="answer_student_font" style="color: red;padding-top: 4px;padding-bottom: 0px;">
	                		<p class="answer_student_p_teacher">已被投诉</p>
	                	</div>
	                </c:if>-->
	                <div class="answer_student_font">
	                    	<c:if test="${model.status == '1'}">
	                    		<p class="answer_student_font_p">
									未回复
								</p>
							</c:if>
							<c:if test="${model.status == '2'}">
									<p class="answer_student_font_p answer_student_font_p01">
										已回复
									</p>
							</c:if>
							<c:if test="${model.status == '3'}">
								<p class="answer_student_font_p answer_student_font_p01" style="background-color: #ccc !important;">
									已超时
								</p>
							</c:if>
	                    <a href="#" onclick="viewQuestion('<bean:write name="model" property="questionid"/>','${point }','${status}')">
	                    	<p class="answer_student_font_pright" style="margin-top:3px;">${model.flags }</p>
	                    </a>
	                </div>
	                <p class="answer_student_p">${model.createdate }</p>
	                <p class="answer_student_p_teacher">回答时限：${model.enddate }</p>
	                <c:if test="${model.status == '1'}">
	                	<a class="answer_fb_a" style="margin-top:-20px;margin-bottom:8px;" onclick="doMain('<bean:write name="model" property="status"/>','<bean:write name="model" property="orderid"/>','${point }')">回复答疑</a>
	           		</c:if>
	            </div>
	      	</logic:iterate>
	      		<div class="page_01">
	             ${string}
	          	</div><!--page_01 E -->
	          	<input type="hidden" name="startcount" id="startcount" value="${startcount}"/>
	        </logic:notEmpty>
	      	<logic:empty name="pagelist" property="datalist">
	      			<h3>暂无数据</h3>
	      	</logic:empty>
        </div><!----information_right_main-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
</form>
<%@ include file="footer.jsp"%>
<script type="text/javascript">
//查看问题
function viewQuestion(objid,point,status){
	var startcount = document.getElementById("startcount").value;
    document.pageForm.action = '/pcenter.do?method=viewQuestion&mark=4&objid='+objid+'&point='+point+'&status='+status+'&startcount='+startcount;
	document.pageForm.submit();

}
function doMain(status,objid,point){
	var startcount = document.getElementById("startcount").value;
	if(status == '3'){
		alert('订单已超时，无法继续回复处理！');
		return flase;
	}else if(status == '1'){
		 //未回复,处理编辑
		document.pageForm.action = '/pcenter.do?method=doOrder&mark=4&objid='+objid+'&point='+point+'&status='+status+'&startcount='+startcount;
		document.pageForm.submit();
	}else if(status =='2'){
		 //已回复，预览
		document.pageForm.action = '/pcenter.do?method=viewAnswer&mark=4&objid='+objid;
		document.pageForm.submit();
	}

}

</script>
</body>
</html>
