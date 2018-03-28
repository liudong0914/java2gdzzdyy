<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>检查作业</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<script>
function addError(questionid,papercontentid){
   document.getElementById(questionid+"che").style.display="inline";
   document.getElementById(questionid+"adderror").style.display="none";
   var bookcontentid="${bookcontentid}";
   var classid="${classid}";
   $.ajax({
   url:"weixinTeacher.app?method=addErrorQuestion&papercontentid="+papercontentid+"&bookcontentid="+bookcontentid+"&classid="+classid+"&type=1&userid=${userid}",
   data:{},
   cache:false,
   async:false,
   dataType:'json'
   });
}
function chexiao(questionid,papercontentid){
   document.getElementById(questionid+"che").style.display="none";
   document.getElementById(questionid+"adderror").style.display="inline";
   var bookcontentid="${bookcontentid}";
   var classid="${classid}";
    $.ajax({
   url:"weixinTeacher.app?method=delerrorQuestion&papercontentid="+papercontentid+"&bookcontentid="+bookcontentid+"&classid="+classid+"&type=1&userid=${userid}",
   data:{},
   cache:false,
   async:false,
   dataType:'json'
   });
}

function beforeaddError(questionid){
   document.getElementById(questionid+"beforeche").style.display="inline";
   document.getElementById(questionid+"beforeadderror").style.display="none";
   var bookcontentid="${bookcontentid}";
   var classid="${classid}";
   $.ajax({
   url:"weixinTeacher.app?method=addErrorQuestion&questionid="+questionid+"&bookcontentid="+bookcontentid+"&classid="+classid+"&type=2&userid=${userid}",
   data:{},
   cache:false,
   async:false,
   dataType:'json'
   });
}

function beforechexiao(questionid){
   document.getElementById(questionid+"beforeche").style.display="none";
   document.getElementById(questionid+"beforeadderror").style.display="inline";
   var bookcontentid="${bookcontentid}";
   var classid="${classid}";
   $.ajax({
   url:"weixinTeacher.app?method=delerrorQuestion&questionid="+questionid+"&bookcontentid="+bookcontentid+"&classid="+classid+"&type=2&userid=${userid}",
   data:{},
   cache:false,
   async:false,
   dataType:'json'
   });
}
</script>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinTeacher.app?method=bookContent&userid=${userid }&bookid=${bookid }&classid=${classid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">检查作业</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<div id="container_list" style="padding-bottom:0px;">
   <%int i=0; %>
     <c:forEach items="${beforeList }" var="q">
	<div class="container_9">
    	<div class="container_9_size">
            <a href="/weixinTeacher.app?method=detaiQuestion&questionid=${q.questionid }&userid=${userid}&bookcontentid=${bookcontentid}&classid=${classid}&type=1"  class="container_9_size_moble">
           		<div class="conrainer_9_moble_span">
                	<span><%i++ ;%><%=i %>.${q.titlecontent }[课前预习题]</span>
                </div>
                <span><img src="/weixin/images/school_11.png" class="conrainer_9_size_moble_img" /></span>
            </a>
            <div class="container_9_size_font">
                <c:if test="${q.flags=='1' }">
                <a id="${q.questionid }beforeche" onclick="beforechexiao(${q.questionid });" class="container_9_size_a1" style="background-color:  #D1D1D1 ;border-color:  #D1D1D1 ;cursor: pointer;">撤销</a>
            	<a id="${q.questionid }beforeadderror" onclick="beforeaddError(${q.questionid });" class="container_9_size_a1" style="display:none;cursor: pointer;">加入错题本</a>
            	</c:if>
            	<c:if test="${q.flags!='1' }">
            	 <a id="${q.questionid }beforeche" onclick="beforechexiao(${q.questionid });" class="container_9_size_a1" style="display:none;background-color:  #D1D1D1 ;border-color:  #D1D1D1 ;cursor: pointer;">撤销</a>
            	<a id="${q.questionid }beforeadderror" onclick="beforeaddError(${q.questionid });" class="container_9_size_a1" style="cursor: pointer;">加入错题本</a>
            	</c:if>
            	<a class="container_9_size_a">对${q.rightNum }</a>
            	<a class="container_9_size_a">错${q.errorNum }</a>
            </div>
    	</div>
    </div>
    </c:forEach>
    <c:forEach items="${list }" var="q">
	<div class="container_9">
    	<div class="container_9_size">
            <a href="/weixinTeacher.app?method=detaiQuestion&questionid=${q.questionid }&userid=${userid}&bookcontentid=${bookcontentid}&classid=${classid}&type=1"  class="container_9_size_moble">
           		<div class="conrainer_9_moble_span">
                	<span><%i++ ;%><%=i %>.${q.titlecontent }</span>
                </div>
                <span><img src="/weixin/images/school_11.png" class="conrainer_9_size_moble_img" /></span>
            </a>
            <div class="container_9_size_font">
               <c:if test="${q.flags=='1' }">
               <a id="${q.questionid }che" onclick="chexiao(${q.questionid },${q.papecontentid});" class="container_9_size_a1" style="background-color:  #D1D1D1 ;border-color:  #D1D1D1 ;cursor: pointer;">撤销</a>
            	<a id="${q.questionid }adderror" onclick="addError(${q.questionid },${q.papecontentid});" class="container_9_size_a1" style="display:none;cursor: pointer;">加入错题本</a>
               </c:if>
               <c:if test="${q.flags!='1'}">
                <a id="${q.questionid }che" onclick="chexiao(${q.questionid },${q.papecontentid});" class="container_9_size_a1" style="display:none;background-color:  #D1D1D1 ;border-color:  #D1D1D1 ;cursor: pointer;">撤销</a>
            	<a id="${q.questionid }adderror" onclick="addError(${q.questionid },${q.papecontentid});" class="container_9_size_a1" style="cursor: pointer;">加入错题本</a>
                </c:if>
                <a class="container_9_size_a">对${q.rightNum }</a>
            	<a class="container_9_size_a">错${q.errorNum }</a>
            </div>
    	</div>
    </div>
    </c:forEach>
    <%if(i > 0){ %>
     <div class="container_12">
    	<a href="/weixinTeacher.app?method=detailErrorQuestion&bookcontentid=${bookcontentid}&classid=${classid}&userid=${userid}">
        	<div class="container_11_size">
    			<span>错题本</span>
        	</div>
        </a>
    </div>
    <div class="container_12" style="margin-top:10px;">
    	<a href="/weixinTeacher.app?method=getUncommitStudent&bookcontentid=${bookcontentid}&classid=${classid}&userid=${userid}">
        	<div class="container_11_size">
    			<span>查看未交作业学生</span>
        	</div>
        </a>
    </div>
    <%}else{ %>
    <div class="container_welcome">
    	<div class="conrainer_welcome_font">
        	<p>当前作业没有试题！</p>
        </div>
    </div>
    <%} %>
    <logic:equal value="1" name="uploadImg">
    <div class="container_12" style="margin-top:10px;">
    	<a href="/weixinTeacher.app?method=classUserList&bookcontentid=${bookcontentid}&classid=${classid}&userid=${userid}">
        	<div class="container_11_size">
    			<span>检查学生拍照上传作业</span>
        	</div>
        </a>
    </div>
    </logic:equal>
</div>

  </body>
</html>
