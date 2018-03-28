<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.util.date.DateTime"%>
<%@page import="com.wkmk.sys.bo.SysUserInfoDetail"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
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

<!-- 表单验证start -->
<script src="../../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="../../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%@ include file="js.jsp"%>

</head>

<body style="background-color:#f9f9f9;" onload="javascript:init()">
<!------头部-------->
<%@ include file="top.jsp"%>
<!------内容-------->
<html:form action="/pcenter.do" method="post">
<div class="personal">
	<%@ include file="left.jsp" %>
    <div class="information_right">
    	<div class="information_right_top">
			<p>个人信息</p>
        </div>
        <div class="information_right_main">
        	<div class="information_right_main_01">
            	<p>个人头像：</p>
                <p>
                	<img src="<bean:write property="photo" name="model"/>" width="90" height="120" border="1" id=uploadImgShow/>
            	</p>
            </div>
            <%-- 
            <div class="information_right_main_02">
            	<p style="letter-spacing: 1px">登&nbsp;录&nbsp;名：</p>
            	<p><bean:write property="loginname" name="model"/></p>
            </div>
            --%>
            <div class="information_right_main_02">
            	<p>真实姓名：</p>
                <p><bean:write property="username" name="model"/></p>
            </div>
            <div class="information_right_main_03">
            	<p>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</p>
                <p>
                <c:if test="${model.sex == '0' }">
               		 男
                </c:if>
                <c:if test="${model.sex == '1' }">
               		 女
                </c:if>
                </p>
            </div>
             <div class="information_right_main_02">
            	<p>手机号码：</p>
                <p><bean:write property="mobile" name="model"/></p>
            </div>
            <div class="information_right_main_02">
            	<p>用户类型：</p>
                <p><java2code:value codetype="usertype" property="usertype"></java2code:value></p>
			</div>
			<div class="information_right_main_02">
            	<p>最高学历：</p>
                <p>
                <c:if test="${detail.education == '1' }">
               		博士及以上
                </c:if>
                <c:if test="${detail.education == '2' }">
               		硕士
                </c:if>
                <c:if test="${detail.education == '3' }">
               		 本科
                </c:if>
                <c:if test="${detail.education == '4' }">
               		 大专
                </c:if>
                <c:if test="${detail.education == '9' }">
               		 其他
                </c:if>
                </p>
            </div>
            <div class="information_right_main_02">
            	<p>出生日期：</p>
				<p><bean:write property="birthday" name="detail"/></p>                
            </div>
            <div class="information_right_main_02">
            	<p>所在地区：</p>
				<p>	<%
					SysUserInfoDetail detail = (SysUserInfoDetail)request.getAttribute("detail");
					List provinceList = (List)request.getAttribute("provinceList");
					SysAreaInfo sai = null;
					for(int i=0, size=provinceList.size(); i<size; i++){
						sai = (SysAreaInfo)provinceList.get(i);
					%>
					 <%if(sai.getCitycode().equals(detail.getProvince())){ %><%=sai.getAreaname() %><%} %>
					<%} %>
					<%
					List cityList = (List)request.getAttribute("cityList");
					if(cityList != null && cityList.size() > 0){
					for(int i=0, size=cityList.size(); i<size; i++){
						sai = (SysAreaInfo)cityList.get(i);
					%>
					<%if(sai.getCitycode().equals(detail.getCity())){ %><%=sai.getAreaname() %><%} %>
					<%}} %>
					<%
					List countyList = (List)request.getAttribute("countyList");
					if(countyList != null && countyList.size() > 0){
					for(int i=0, size=countyList.size(); i<size; i++){
						sai = (SysAreaInfo)countyList.get(i);
					%>
					<%if(sai.getCitycode().equals(detail.getCounty())){ %><%=sai.getAreaname() %><%} %>
					<%}} %>
				</p>
            </div>
            <div class="information_right_main_02">
            	<p>个人简介：</p>
            	<p><bean:write property="descript" name="detail"/></p>
            </div>
            <a href="#" onclick="beforeUpdate()" class="information_right_main_a">编辑</a>
        </div><!----information_right_main-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
<input type="hidden" name="sysUserInfo.userid" value='<bean:write property="userid"  name="model"/>'/>
<input type="hidden" name="sysUserInfo.stno" value='<bean:write property="stno"  name="model"/>'/>
<input type="hidden" name="sysUserInfo.password" value='<bean:write property="password"  name="model"/>' />
<input type="hidden" name="sysUserInfo.status" value='<bean:write property="status" name="model"/>' />
<input type="hidden" name="sysUserInfo.unitid" id="sysUserInfo.unitid" value='<bean:write property="unitid"  name="model"/>' />
<input type="hidden" name="sysUserInfo.temppass" value='<bean:write property="temppass"  name="model"/>' />
<input type="hidden" name="sysUserInfo.uuid" value='<bean:write property="uuid"  name="model"/>' />
<input type="hidden" name="sysUserInfo.money" value='<bean:write property="money"  name="model"/>' />
<input type="hidden" name="sysUserInfo.authentication" value='<bean:write property="authentication"  name="model"/>' />
<input type="hidden" name="sysUserInfo.mobile" value='<bean:write property="mobile"  name="model"/>' />


<input type="hidden" name="sysUserInfo.nickname" value='<bean:write property="nickname"  name="model"/>' />
<input type="hidden" name="sysUserInfo.studentno" value='<bean:write property="studentno"  name="model"/>' />
<input type="hidden" name="sysUserInfoDetail.jobtitle" value='<bean:write property="jobtitle"  name="detail"/>' />
<input type="hidden" name="sysUserInfo.xueduan" value='<bean:write property="xueduan"  name="model"/>' />
<input type="hidden" name="sysUserInfoDetail.cardtype" value='<bean:write property="cardtype"  name="detail"/>' />
<input type="hidden" name="sysUserInfo.email" value='<bean:write property="email"  name="model"/>' />
<input type="hidden" name="sysUserInfoDetail.cardno" value='<bean:write property="cardno"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.qq" value='<bean:write property="qq"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.msn" value='<bean:write property="msn"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.nation" value='<bean:write property="nation"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.postcode" value='<bean:write property="postcode"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.address" value='<bean:write property="address"  name="detail"/>' />

<input type="hidden" name="sysUserInfoDetail.createdate" value='<bean:write property="createdate" name="detail"/>'/>
<input type="hidden" name="sysUserInfoDetail.pwdquestion" value='<bean:write property="pwdquestion"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.pwdanswer" value='<bean:write property="pwdanswer"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.flag" value='<bean:write property="flag"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.lastlogin" value='<bean:write property="lastlogin"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.logintimes" value='<bean:write property="logintimes"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.updattime" value='<bean:write property="updatetime"  name="detail"/>' />
<input type="hidden" name="sysUserInfoDetail.mood" value='<bean:write property="mood"  name="detail"/>' />
</html:form>
<script>
function beforeUpdate(){
		document.sysUserInfoActionForm.action="/pcenter.do?method=<bean:write name="act"/>";
		document.sysUserInfoActionForm.submit();
}

</script>

<%@ include file="footer.jsp"%>

</body>
</html>
