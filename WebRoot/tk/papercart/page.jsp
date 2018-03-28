<%@page import="com.util.search.PageList"%>
<%@ page contentType="text/html; charset=utf-8"%>
<div class="page">
<style type="text/css"> 
ul,li{list-style:none}
.page{ position:relative;width:600px; letter-spacing:0px;height:80px;margin-top:40px; padding-left:10px;}
.page ul{width:700px;padding-left:0px;}
.page ul li{float:left;}
.page ul li a{padding:5px 15px;color:#666;margin:0 2px;border:1px solid #eee;}
.page ul li a:hover{background:#40a8ff;color:#fff !important;}
.page ul li a.curss{background:#40a8ff;color:#fff !important;}
</style>
    <ul>
      <%
      PageList page_list = (PageList)request.getAttribute("pagelist");
      %>
      <%if(page_list.isHasPreviousPage()){ %>
      <li><a href="javascript:gotoPageSize(<%=page_list.getStartOfPreviousPage() %>)">上一页</a></li>
      <%} %>
      <%
      if(page_list.getTotalPages() > 10){ 
      	  if(page_list.getCurPage() > 5){
      		  int _start = Long.valueOf(page_list.getCurPage()).intValue() - 5;
      		  int _end = _start + 9;
      		  if(page_list.getTotalPages() < _end){
      			_end = Long.valueOf(page_list.getTotalPages()).intValue();
      		  }
      		for(int i=_start; i<=_end; i++){
      %>
      <li><a href="javascript:gotoPageSize(<%=(i-1)*page_list.getPageSize() %>)" <%if(page_list.getCurPage() == i){ %>class="curss"<%} %>><%=i %></a></li>
      <%
      		}
      }else{ 
    	  for(int i=1; i<=10; i++){
      %>
      <li><a href="javascript:gotoPageSize(<%=(i-1)*page_list.getPageSize() %>)" <%if(page_list.getCurPage() == i){ %>class="curss"<%} %>><%=i %></a></li>
      <%
    	  }
      }}else{ 
          for(int i=1; i<=page_list.getTotalPages(); i++){
      %>
      <li><a href="javascript:gotoPageSize(<%=(i-1)*page_list.getPageSize() %>)" <%if(page_list.getCurPage() == i){ %>class="curss"<%} %>><%=i %></a></li>
      <%}} %>
      <%if(page_list.isHasNextPage()){ %>
      <li><a href="javascript:gotoPageSize(<%=page_list.getStartOfNextPage() %>)">下一页</a></li>
      <%} %>
    </ul>
</div>
