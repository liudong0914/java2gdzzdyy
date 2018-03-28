<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="java.util.List"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
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
function init(){
	var sid = document.getElementById("sid").value;
	setTab("one", sid, "4");
}
<!--
/*第一种形式 第二种形式 更换显示样式*/
function setTab(name,cursel,n){
	document.getElementById("sid").value=cursel;
	for(i=1;i<=n;i++){
		var menu=document.getElementById(name+i);
		var con=document.getElementById("con_"+name+"_"+i);
		menu.className=i==cursel?"hover":"";
		con.style.display=i==cursel?"block":"none";
	}
}
//-->
</script>
</head>
<body style="background-color:#f9f9f9;" onload="init()">
<!------头部-------->
<%@ include file="top.jsp"%>
<!------内容-------->
<form name="pageForm" method="post">
<div class="personal">
	<%@ include file="left.jsp" %>
    <div class="information_right">
    	<div class="information_right_top">
            <ul>
            	<li id="one1" onmouseover="setTab('one',1,4)"  class="hover">未完成订单</li>
                <li id="one2" onmouseover="setTab('one',2,4)" >已完成订单</li>
                <li id="one3" onmouseover="setTab('one',3,4)">已超时订单</li>	
            </ul>
        </div>
        <div class="question">
	        <div id="con_one_1" class="hover">
	        <logic:notEmpty name="pagelist" property="datalist">
	        <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
	            <div class="answer_student">
	                <div class="answer_student_top">
	                    <a href="#"><img src="/skin/pcenter/images/img.jpg"/></a>
	                    <a href="#"><p>${model.flago }</p></a>
	                    <p class="answer_student_top_p">${model.flagl }前</p>
	                </div>
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
								<p class="answer_student_font_p answer_student_font_p01">
									已超时
								</p>
							</c:if>
	                    <a href="#" onclick="viewQuestion('<bean:write name="model" property="questionid"/>')"><p class="answer_student_font_pright" style="margin-top:3px;">${model.flags }</p></a>
	                </div>
	                <a href=#" ><p class="answer_student_p">${model.createdate }</p></a>
	                <p class="answer_student_p_teacher">回答时限：${model.enddate }</p>
	                <a class="answer_fb_a" style="margin-top:-20px;margin-bottom:8px;" onclick="doMain('<bean:write name="model" property="status"/>','<bean:write name="model" property="orderid"/>')">回复答疑</a>
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
	       	</div>
       	<div id="con_one_2" style="display:none">
       		<logic:notEmpty name="pagelist2" property="datalist">
	       		<logic:iterate id="model" name="pagelist2" property="datalist" indexId="index">
		            <div class="answer_student">
		                <div class="answer_student_top">
		                    <a href="#"><img src="/skin/pcenter/images/img.jpg"/></a>
		                    <a href="#"><p>${model.flago }</p></a>
		                    <p class="answer_student_top_p">${model.flagl }前</p>
		                </div>
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
									<p class="answer_student_font_p answer_student_font_p01">
										已超时
									</p>
								</c:if>
		                    <a href="#" onclick="viewQuestion('<bean:write name="model" property="questionid"/>')"><p class="answer_student_font_pright" style="margin-top:3px;">${model.flags }</p></a>
		                </div>
		                <a href=#" ><p class="answer_student_p">${model.createdate }</p></a>
		                <p class="answer_student_p_teacher">回答时限：${model.enddate }</p>
		                <!--  <a class="answer_fb_a" style="margin-top:-20px;margin-bottom:8px;" onclick="doMain('<bean:write name="model" property="status"/>','<bean:write name="model" property="orderid"/>')">查看回复</a>
		            -->
		            </div>
		      	</logic:iterate>
		      		<div class="page_01">
		             ${string2}
		          	</div><!--page_01 E -->
		          	<input type="hidden" name="startcount2" id="startcount2" value="${startcount2}"/>
	        </logic:notEmpty>
	      	<logic:empty name="pagelist2" property="datalist">
	      			<h3>暂无数据</h3>
	      	</logic:empty> 
       	</div>
        <div id="con_one_3" style="display:none">
        	<logic:notEmpty name="pagelist3" property="datalist">
	        	<logic:iterate id="model" name="pagelist3" property="datalist" indexId="index">
		            <div class="answer_student">
		                <div class="answer_student_top">
		                    <a href="#"><img src="/skin/pcenter/images/img.jpg"/></a>
		                    <a href="#"><p>${model.flago }</p></a>
		                    <p class="answer_student_top_p">${model.flagl }前</p>
		                </div>
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
									<p class="answer_student_font_p answer_student_font_p01">
										已超时
									</p>
								</c:if>
		                    <a href="#" onclick="viewQuestion('<bean:write name="model" property="questionid"/>')"><p class="answer_student_font_pright" style="margin-top:3px;">${model.flags }</p></a>
		                </div>
		                <a href=#" ><p class="answer_student_p">${model.createdate }</p></a>
		                <p class="answer_student_p_teacher">回答时限：${model.enddate }</p>
		            </div>
		      	</logic:iterate>
		      		<div class="page_01">
		             ${string3}
		          	</div><!--page_01 E -->
		          	<input type="hidden" name="startcount3" id="startcount3" value="${startcount3}"/>
	       </logic:notEmpty>
	      	<logic:empty name="pagelist3" property="datalist">
	      			<h3>暂无数据</h3>
	      	</logic:empty> 
        </div>
        </div><!----information_right_main-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
<input type="hidden" id="sid" name="sid" value="${sid }" />
</form>
<%@ include file="footer.jsp"%>
<script type="text/javascript">
//查看问题
function viewQuestion(objid){
    document.pageForm.action = '/pcenter.do?method=viewQuestion&mark=4&objid='+objid;
	document.pageForm.submit();

}
function doMain(status,objid){
	if(status == '3'){
		alert('订单已超时，无法继续回复处理！');
		return flase;
	}else if(status == '1'){
		 //未回复,处理编辑
		document.pageForm.action = '/pcenter.do?method=doOrder&mark=4&objid='+objid;
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
