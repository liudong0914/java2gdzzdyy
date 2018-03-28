<%@page import="com.util.search.PageList"%>
<%@ page contentType="text/html;charset=utf-8"%>
<div class="page">
    <ul>
      <%
      PageList page_list = (PageList)request.getAttribute("pagelist");
      %>
      <%
      if(page_list.getTotalPages() > 7){ 
      	  if(page_list.getCurPage() > 4){
      		  int _start = Long.valueOf(page_list.getCurPage()).intValue() - 4;
      		  int _end = _start + 6;
      		  if(page_list.getTotalPages() < _end){
      			_end = Long.valueOf(page_list.getTotalPages()).intValue();
      		  }
      		for(int i=_start; i<=_end; i++){
      %>
      <li><a href="javascript:gotoPageSize(<%=(i-1)*page_list.getPageSize() %>)" <%if(page_list.getCurPage() == i){ %>class="curss"<%} %>><%=i %></a></li>
      <%
      		}
      }else{ 
    	  for(int i=1; i<=7; i++){
      %>
      <li><a href="javascript:gotoPageSize(<%=(i-1)*page_list.getPageSize() %>)" <%if(page_list.getCurPage() == i){ %>class="curss"<%} %>><%=i %></a></li>
      <%
    	  }
      }}else{ 
          for(int i=1; i<=page_list.getTotalPages(); i++){
      %>
      <li><a href="javascript:gotoPageSize(<%=(i-1)*page_list.getPageSize() %>)" <%if(page_list.getCurPage() == i){ %>class="curss"<%} %>><%=i %></a></li>
      <%}} %>
    </ul>
</div>
<input type="hidden" name="startcount" id="startcount" value="0"/>