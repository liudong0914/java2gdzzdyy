<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkBookContent"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>我的微课</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script type="text/javascript">
$(document).scroll(function(){
   	if($("#loading").is(":hidden")){
   		$("#loading").show();
   	}
   	var scrolltop = $(document).scrollTop();
    var dheight = $(document).height();
    var wheight = $(window).height();
    if(scrolltop >= dheight-wheight){
    var keywords = document.getElementById("keywords").value;
    var startcount = document.getElementById("startcount").value;
	$.ajax( { 
		 url:'/weixinVod.app?method=search',// 跳转到 action  
		 data:'userid=${userid}&keywords='+keywords+'&startcount='+startcount,  
		 type:'post',  
		 dataType:'json',
		 cache:false,  
		 success:function(data) {
			 if(data != null){
				 if(data.hasNextPage == "1"){
					document.getElementById("sp_1").style.display = "none";
					document.getElementById("sp_2").style.display = "";
				 }else{
					document.getElementById("sp_1").style.display = "";
					document.getElementById("sp_2").style.display = "none";
				 }
				 document.getElementById("startcount").value = data.startcount;
				 if(data.datalist == "暂无数据！"){
				 	document.getElementById("sp_1").style.display = "none";
					document.getElementById("sp_2").style.display = "";
				 }else{
				 	$("#mainId").append(data.datalist);
				 }
			 }
		 },
         error: function(xhr, type){
        	 
         }
		});
	      	}
		});
function postData(){
	var keywords = document.getElementById("keywords").value;
	if(keywords == ""){
		alert("请输入搜索关键字!");
		return;
	}
 	document.getElementById("startcount").value="0";
 	var startcount = document.getElementById("startcount").value;
	document.pageForm.action = "/weixinVod.app?method=toSearch&userid=${userid }&startcount="+startcount;
	document.pageForm.submit();
}
function changeType(typeid){
	document.getElementById("searchtype").value = typeid;
	postData();
}
function search_onkeydown(evt){
	evt = (evt) ? evt : ((window.event) ? window.event : "");
  	keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which :evt.charCode);
  	if (keyCode == 13){    
  		postData();
  		return false;
  	}  
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/dh.jsp"%>
<form name="pageForm" method="post">
<div class="search">
	<a href="/weixinVod.app?method=inSearchIndex&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_input01">
        <div  class="search_input_01_main">
            <input type="search" placeholder="搜索关键字" id="keywords"  name="keywords" value="${keywords }" onKeyPress="return search_onkeydown(event)" style="color:#000;"/>  
            <a href="javascript:postData()"><img src="/weixin/images/icon04.png" /></a> 
        </div>
     </div>
    <a href="javascript:showDH()" ><div class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<!-----class_main-------->
<!-----下拉菜单-------->
   <div class="menu" >
	  <ul>
	     <li <logic:equal value="0" name="searchtype">class="menu_li"</logic:equal>><a class="menu_a" href="javascript:changeType('0')">综合</a>
         </li>
         <li <logic:equal value="1" name="searchtype">class="menu_li0"</logic:equal>><a class="menu_a" href="javascript:changeType('1')">已购买</a>
         </li>
         <li <logic:equal value="2" name="searchtype">class="menu_li0"</logic:equal>><a class="menu_a" href="javascript:changeType('2')">未购买</a>
         </li>
     </ul>
   </div>
<!--menu ed-->
<!-----class_main-------->
<%
String searchtype = (String)request.getAttribute("searchtype");
List bookcontentList = (List)request.getAttribute("bookcontentList");
List buycontentidList1 = (List)request.getAttribute("buycontentidList1");
List buycontentidList2 = (List)request.getAttribute("buycontentidList2");
TkBookContent tbc = null;
%>
<div class="class_buy">
   <div class="class_buy_moudle" id="mainId">
   	   <%
   	    String temp = "0";
   		if(bookcontentList != null && bookcontentList.size() > 0){
   			for(int i=0, size=bookcontentList.size(); i<size; i++){
   				tbc = (TkBookContent)bookcontentList.get(i);
   	   %>
   	   <%if("0".equals(searchtype)){ %>
	   	   <%if("1".equals(tbc.getFlago())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	       </div>
	       <%}else if("2".equals(tbc.getFlago())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a01">举一反三</a>
	            <%} %>
	       </div>
	       <%}else if("3".equals(tbc.getFlago())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
	            <%} %>
	       </div>
	       <%} %>
       <%}else if("1".equals(searchtype)){ %>
           <%if("1".equals(tbc.getFlago()) && buycontentidList1.contains(tbc.getBookcontentid())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	            </div>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	       </div>
	       <%}else if("2".equals(tbc.getFlago()) && buycontentidList2.contains(tbc.getBookcontentid())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	            </div>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a01">举一反三</a>
	       </div>
	       <%}else if("3".equals(tbc.getFlago()) && (buycontentidList1.contains(tbc.getBookcontentid()) || buycontentidList2.contains(tbc.getBookcontentid()))){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
	            <%} %>
	       </div>
	       <%} %>
       
       <%}else if("2".equals(searchtype)){ %>
           <%if("1".equals(tbc.getFlago()) && !buycontentidList1.contains(tbc.getBookcontentid())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	       </div>
	       <%}else if("2".equals(tbc.getFlago()) && !buycontentidList2.contains(tbc.getBookcontentid())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <a class="class_buy_moudle_main_a03">举一反三</a>
	       </div>
	       <%}else if("3".equals(tbc.getFlago()) && (!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid()))){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=<%=tbc.getBookid() %>&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
	            <%} %>
	       </div>
	       <%} %>
       
       <%} %>
       <%}%>
   		<%if("0".equals(temp)){ %>暂无数据！<%} %>
   	   <%}else{ %>
       暂无数据！
       <%} %>
   </div>
</div>
   </div>
</div>
<div class="loading" id="loading">
	<span id="sp_1"><img src="/weixin/images/loading.gif" class="loadingimg"/>加载中</span>
	<span id="sp_2" style="display:none;">数据已全部加载完!</span>
</div>
<input type="hidden" name="searchtype" id="searchtype" value="${searchtype }"/>
<input type="hidden" name="startcount" id="startcount" value="<bean:write name="startcount" />" />
</form>
</body>
</html>