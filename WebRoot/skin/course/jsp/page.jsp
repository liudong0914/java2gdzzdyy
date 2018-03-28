<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.search.PageList"%>
<div class="pager turn-page">
	<div>
		<%
      	PageList page_list = (PageList)request.getAttribute("pagelist");
      	%>
      	<%if(page_list.isHasPreviousPage()){ %>
		<a class="prev disabled" href="javascript:gotoPageSize(1)" title="首页"><<</a>
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
		<a class="show <%if(page_list.getCurPage() == i){ %>hover<%} %>" href="javascript:gotoPageSize(<%=i %>)"><%=i %></a>
		<%if(i == (_start+5)){ %>
	    <a class="show " >...</a>
	    <%} %>
		<%
      		}
      	}else{ 
    	  for(int i=1; i<=10; i++){
      	%>
		<a class="show <%if(page_list.getCurPage() == i){ %>hover<%} %>" href="javascript:gotoPageSize(<%=i %>)"><%=i %></a>
		<%
    	  }
      	}}else{ 
          for(int i=1; i<=page_list.getTotalPages(); i++){
      	%>
		<a class="show <%if(page_list.getCurPage() == i){ %>hover<%} %>" href="javascript:gotoPageSize(<%=i %>)"><%=i %></a>
		<%}} %>
		<%if(page_list.isHasNextPage()){ %>
		<a class="next " href="javascript:gotoPageSize(<%=page_list.getTotalPages() %>)" title="末页">>></a>
		<%} %>
	</div>
</div>
<script type="text/javascript">
function gotoPageSize(size){
	document.getElementById('pageNo').value = size;
	postData();
}
</script>