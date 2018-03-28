<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
<%@page import="com.wkmk.tk.bo.TkBookContent"%>
<%@page import="java.util.Map"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>${tkBookInfo.title }(${tkBookInfo.flags })</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function postData(){
	document.pageForm.action = "/weixinVod.app?method=bookContentGoodFilmList&userid=${userid }&bookid=${bookid }";
	document.pageForm.submit();
}
function changeType(typeid){
	document.getElementById("searchtype").value = typeid;
	postData();
}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/dh.jsp"%>
<form name="pageForm" method="post">
<div class="search">
	<a href="/weixinVod.app?method=goodFilmBookList&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div class="search_input01">
        <div  class="search_input_01_main">
            <input type="search" placeholder="搜索关键字" name="keywords" value="${keywords }" style="color:#000;"/>  
            <a href="javascript:postData()"><img src="/weixin/images/icon04.png" /></a> 
        </div>
     </div>
    <a href="javascript:showDH()" ><div class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
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
TkBookInfo tkBookInfo = (TkBookInfo)request.getAttribute("tkBookInfo");
List bookcontentList = (List)request.getAttribute("bookcontentList");
List buycontentidList1 = (List)request.getAttribute("buycontentidList1");
List buycontentidList2 = (List)request.getAttribute("buycontentidList2");
List parentList = (List)request.getAttribute("parentList");
List parentnoList = (List)request.getAttribute("parentnoList");
Map childMap = (Map)request.getAttribute("childMap");
TkBookContent tbc = null;
%>
<%if(parentList != null && parentList.size() > 0){ %>
<div class="class_buy">
	<%
	List childList = null;
	TkBookContent parent = null;
	String temp = "0";
	for(int m=0, n=parentList.size(); m<n; m++){
		parent = (TkBookContent)parentList.get(m);
		if(parentnoList.contains(parent.getContentno())){
			temp = "1";
	%>
   <div class="class_buy_moudle">
   		<div class="class_buy_moudle_title">
            <p class="class_buy_moudle_title_p"><%=parent.getTitle() %></p>
       </div>
       <%
        childList = (List)childMap.get(parent.getContentno());
        if(childList != null && childList.size() > 0){
		for(int i=0, size=childList.size(); i<size; i++){
			tbc = (TkBookContent)childList.get(i);
   	   %>
   	   <%if("0".equals(searchtype)){ %>
	   	   <%if("1".equals(tkBookInfo.getVodstate())){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	       </div>
	       <%}else if("2".equals(tkBookInfo.getVodstate())){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a01">举一反三</a>
	            <%} %>
	       </div>
	       <%}else if("3".equals(tkBookInfo.getVodstate())){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
	            <%} %>
	       </div>
	       <%} %>
       <%}else if("1".equals(searchtype)){ %>
           <%if("1".equals(tkBookInfo.getVodstate()) && buycontentidList1.contains(tbc.getBookcontentid())){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	            </div>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	       </div>
	       <%}else if("2".equals(tkBookInfo.getVodstate()) && buycontentidList2.contains(tbc.getBookcontentid())){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	            </div>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a01">举一反三</a>
	       </div>
	       <%}else if("3".equals(tkBookInfo.getVodstate()) && (buycontentidList1.contains(tbc.getBookcontentid()) || buycontentidList2.contains(tbc.getBookcontentid()))){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
	            <%} %>
	       </div>
	       <%} %>
       
       <%}else if("2".equals(searchtype)){ %>
           <%if("1".equals(tkBookInfo.getVodstate()) && !buycontentidList1.contains(tbc.getBookcontentid())){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	       </div>
	       <%}else if("2".equals(tkBookInfo.getVodstate()) && !buycontentidList2.contains(tbc.getBookcontentid())){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <a class="class_buy_moudle_main_a03">举一反三</a>
	       </div>
	       <%}else if("3".equals(tkBookInfo.getVodstate()) && (!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid()))){ %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
	            <%} %>
	       </div>
	       <%} %>
       
       <%} %>
       <%}} %>
   <%}} %>
   <%if("0".equals(temp)){ %>暂无数据！<%} %>
</div>
<%}else{ %>
<div class="class_buy">
   <div class="class_buy_moudle">
   	   <%
   	    String temp = "0";
   		if(bookcontentList != null && bookcontentList.size() > 0){
   			for(int i=0, size=bookcontentList.size(); i<size; i++){
   				tbc = (TkBookContent)bookcontentList.get(i);
   	   %>
   	   <%if("0".equals(searchtype)){ %>
	   	   <%if("1".equals(tkBookInfo.getVodstate())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	       </div>
	       <%}else if("2".equals(tkBookInfo.getVodstate())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a01">举一反三</a>
	            <%} %>
	       </div>
	       <%}else if("3".equals(tkBookInfo.getVodstate())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
	            <%} %>
	       </div>
	       <%} %>
       <%}else if("1".equals(searchtype)){ %>
           <%if("1".equals(tkBookInfo.getVodstate()) && buycontentidList1.contains(tbc.getBookcontentid())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	            </div>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	       </div>
	       <%}else if("2".equals(tkBookInfo.getVodstate()) && buycontentidList2.contains(tbc.getBookcontentid())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	            </div>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a01">举一反三</a>
	       </div>
	       <%}else if("3".equals(tkBookInfo.getVodstate()) && (buycontentidList1.contains(tbc.getBookcontentid()) || buycontentidList2.contains(tbc.getBookcontentid()))){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <%if(!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid())){ %><a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a><%} %>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
	            <%} %>
	       </div>
	       <%} %>
       
       <%}else if("2".equals(searchtype)){ %>
           <%if("1".equals(tkBookInfo.getVodstate()) && !buycontentidList1.contains(tbc.getBookcontentid())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	       </div>
	       <%}else if("2".equals(tkBookInfo.getVodstate()) && !buycontentidList2.contains(tbc.getBookcontentid())){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <a class="class_buy_moudle_main_a03">举一反三</a>
	       </div>
	       <%}else if("3".equals(tkBookInfo.getVodstate()) && (!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid()))){temp = "1"; %>
	       <div  class="class_buy_moudle_main <%if(i==size-1){ %>class_buy_moudle_main02<%} %>">
	       		<div class="class_buy_moudle_main_p">
	                <p class="class_buy_moudle_main_p01"><%=tbc.getTitle() %></p>
	                <a href="/weixinVod.app?method=buy&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&searchtype=${searchtype }">购买</a>
	            </div>
	            <%if(!buycontentidList1.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a03">解题微课</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=1&searchtype=${searchtype }" class="class_buy_moudle_main_a01">解题微课</a>
	            <%} %>
	            <%if(!buycontentidList2.contains(tbc.getBookcontentid())){ %>
	            <a class="class_buy_moudle_main_a02">举一反三</a>
	            <%}else{ %>
	            <a href="/weixinVod.app?method=play&userid=${userid }&bookid=${bookid }&bookcontentid=<%=tbc.getBookcontentid() %>&type=2&searchtype=${searchtype }" class="class_buy_moudle_main_a04">举一反三</a>
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
<%} %>
<input type="hidden" name="searchtype" id="searchtype" value="${searchtype }"/>
</form>
</body>
</html>