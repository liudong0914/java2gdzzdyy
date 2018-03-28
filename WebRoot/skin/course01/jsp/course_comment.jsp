<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.edu.bo.EduCourseComment"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/skin/course01/css/sytle.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function postData(){
    document.pageForm.action = "/course.bo?method=coursecomment";
  	document.pageForm.submit();
}
function search(){
	document.getElementById('pageNo').value = 1;
	postData();
}
</script>
</head>

<body style="background:#fff;">
<form name="pageForm" method="post">
<div>
	<div class="content_main_left_evaluate">
    	<p class="content_main_left_evaluate_p">${haoping }</p>
        <span class="content_main_left_evaluate_form">
             <label><input name="pingjia" type="radio" <logic:equal value="0" name="pingjia">checked="checked"</logic:equal> value="0" onclick="search()"/><p>全部评价（${scorecount }）</p></label> 
             <label><input name="pingjia" type="radio" <logic:equal value="1" name="pingjia">checked="checked"</logic:equal> value="1" onclick="search()"/><p>好评（${score1 }）</p></label> 
             <label><input name="pingjia" type="radio" <logic:equal value="2" name="pingjia">checked="checked"</logic:equal> value="2" onclick="search()"/><p>中评（${score2 }）</p></label> 
             <label><input name="pingjia" type="radio" <logic:equal value="3" name="pingjia">checked="checked"</logic:equal> value="3" onclick="search()"/><p>差评（${score3 }）</p></label> 
        </span> 
    </div>
    <div class="content_main_left_evaluate_main">
    	
        <%
        PageList pageList = (PageList)request.getAttribute("pagelist");
        List list = pageList.getDatalist();
        EduCourseComment ecc = null;
        SysUserInfo sui = null;
        String photo = null;
        for(int i=0, size=list.size(); i<size; i++){
        	ecc = (EduCourseComment)list.get(i);
        	sui = ecc.getSysUserInfo();
        	photo = sui.getPhoto();
        	if(!photo.startsWith("http://")){
        		photo = "/upload/" + photo;
        	}
        %>
        <div class="content_main_left_evaluate_main_module">
        	<img src="<%=photo %>" class="content_main_left_evaluate_main_module_img" />
            <div class="content_main_left_evaluate_main_module_main">
                <div class="content_main_left_evaluate_main_module_font">
                    <div class="content_main_left_evaluate_main_module_font_p">
                        <p class="content_main_left_evaluate_main_module_font_p01">
                        <%if("1".equals(ecc.getAnonymous())){ %>
                        <%=sui.getUsername().substring(0,1) %>**
                        <%}else{ %>
                        <%=sui.getUsername() %>
                        <%} %>
                        </p>
                        <div class="content_class_font_fx_star">
                        	<%
                        	for(int x=1; x<=5; x++){
			            		if(ecc.getScore() <= (x-1)){
			            	%>
		                	<img src="/skin/course01/images/a10.png" />
		                	<%}else if(ecc.getScore() >= x){ %>
		                	<img src="/skin/course01/images/a05.png" />
		                	<%}else{ %>
		                	<img src="/skin/course01/images/a09.png" />
		                	<%}} %>
                        </div>
                        <p class="content_main_left_evaluate_main_module_font_p02"><%=ecc.getCreatedate() %></p>
                    </div>
                    <p class="content_main_left_evaluate_main_module_font_p03"><%=ecc.getContent() %></p>
                </div>
                <%if(ecc.getReplycontent() != null && !"".equals(ecc.getReplycontent())){ %>
                <div class="content_main_left_evaluate_main_module_font content_main_left_evaluate_main_module_font01">
                    <div class="content_main_left_evaluate_main_module_font_p">
                        <p class="content_main_left_evaluate_main_module_font_p01">教师回复：</p>
                        <p class="content_main_left_evaluate_main_module_font_p02"><%=ecc.getReplycreatedate() %></p>
                    </div>
                    <p class="content_main_left_evaluate_main_module_font_p03"><%=ecc.getReplycontent() %></p>
                </div>
                <%} %>
            </div>
        </div><!------content_main_left_evaluate_main_module-e------>
        <%} %>
    </div><!------content_main_left_evaluate_main-e------>
    <%@ include file="page.jsp"%> 
</div>

<input type="hidden" value="<bean:write name="pagelist" property="curPage"/>" name="pager.pageNo" id="pageNo"/>
<input type="hidden" value="10" name="pager.pageSize" id="pageSize"/>
<input type="hidden" value="1" name="pager.type"/>
<input type="hidden" value="${courseid }" name="courseid"/>
</form>
</body>
</html>