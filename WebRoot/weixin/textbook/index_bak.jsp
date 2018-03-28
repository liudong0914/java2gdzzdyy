<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkTextBookInfo"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>教材订购</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function postData(){
	document.pageForm.action = "/weixinTextBook.app?method=index&userid=${userid}";
	document.pageForm.submit();
}
function showDiv(divid, num){
	for(var i=1; i<=num; i++){
		document.getElementById("hiddendiv_" + i).style.display = "none";
	}
	document.getElementById("hiddendiv_" + divid).style.display = "block";
}
function hidDiv(divid){
	document.getElementById("hiddendiv_" + divid).style.display = "none";
}
function changeType(typeid, value){
	document.getElementById(typeid).value = value;
	postData();
}
</script>
</head>

<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<form name="pageForm" method="post">
<div class="search">
	<a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_input01">
        <div  class="search_input_01_main">
            <input type="search" placeholder="搜索教材名称" name="keywords" value="${keywords }" style="color:#000;"/>  
            <a href="javascript:postData()"><img src="/weixin/images/icon04.png" /></a> 
        </div>
     </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>

<div class="testpaper" >
	<%
	List textbooklist = (List)request.getAttribute("textbooklist");
	if(textbooklist != null && textbooklist.size() > 0){
	 TkTextBookInfo bookInfo = null;
	 for(int i=0;i<textbooklist.size();i++){
	  bookInfo =(TkTextBookInfo)textbooklist.get(i);
	%>
		<a href="/weixinTextBook.app?method=getTextBookDetail&userid=${userid }&textbookid=<%=bookInfo.getTextbookid() %>">
			<div class="testpaper_main">
				<p class="testpaper_main_p"><%=bookInfo.getTextbookname() %></p>
				<div class="testpaper_main_right">
					 <p><%=bookInfo.getAuthor() %></p>
				 </div>
			 </div>
	 	</a>
	 <%}}else{ %>
	 暂无数据！
	 <%} %>
</div>
<div class="loading" id="loading">
		<img src="/weixin/images/loading.gif" class="loadingimg"/>加载中
	</div>
</form>

<script type="text/javascript">
var num = 1;
$(document).ready(function(){
	$(document).scroll(function(){
    	if($("#loading").is(":hidden")){
      		$("#loading").show();
      	}
    	var scrolltop = $(document).scrollTop();
        var dheight = $(document).height();
        var wheight = $(window).height();
        if(scrolltop >= dheight-wheight){
    		  $.ajax({
		        type:"post",
		        url:"/weixinTextBook.app?method=getTextBookByAjax",
		        data:"pagenum=" + num + "&userid=${userid}&keywords=${keywords }",
		        success:function(msg){
		        	if(msg != ""){
		        		$("#testpaper").append(msg);
		        	}else{
		        		$("#loading").html("数据已全部加载完!");
		        		$(document).unbind('scroll');
		        	}
     			}
 			});
    	  	num++;
      	}
	});
});
</script>
</body>
</html>