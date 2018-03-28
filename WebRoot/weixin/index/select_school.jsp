<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.sys.bo.SysAreaInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>选择学校</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function searchRecord(){
    document.pageForm.action="/weixinAccountIndex.app?method=selectSchool";
    document.pageForm.submit();
}
function changeArea(province, city, county){
	document.getElementById("province").value = province;
	document.getElementById("city").value = city;
	document.getElementById("county").value = county;
    searchRecord();
}
function selectDeal(unitid){
	document.pageForm.action="/weixinAccountIndex.app?method=selectDeal&schoolid=" + unitid;
    document.pageForm.submit();
}
function gotoPageSize(size){
	document.getElementById('startcount').value = size;
	searchRecord();
}
function getType(value){
	document.getElementById('hiddendiv').value = value;
	if(value == '1'){
		document.getElementById('type_div').style.display = 'block';
	}
	if(value == '0'){
		document.getElementById('type_div').style.display = 'none';
	}
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<form name="pageForm" method="post">
<!-- 浮动层 start-->
<div class="category" id="type_div" <logic:equal value="0" name="hiddendiv">style="display:none;"</logic:equal>>
	<%
	String province = (String)request.getAttribute("province");
	List provinceList = (List)request.getAttribute("provinceList");
	List cityList = (List)request.getAttribute("cityList");
	List countyList = (List)request.getAttribute("countyList");
	SysAreaInfo sai = null;
	%>
	<dl>
		<dt>省：</dt>
		<dd>
			<span <%if("".equals(province)){ %>style="background:#45d4a2;"<%} %>><a href="javascript:changeArea('','','')">全部</a></span>
			<%
			for(int i=0, size=provinceList.size(); i<size; i++){
				sai = (SysAreaInfo)provinceList.get(i);
			%>
			<span <%if(sai.getCitycode().equals(province)){ %>style="background:#45d4a2;"<%} %>><a href="javascript:changeArea('<%=sai.getCitycode() %>','','')"><%=sai.getAreaname() %></a></span>
			<%} %>
		</dd>
	</dl>
	<%
	if(cityList != null && cityList.size() > 0){ 
		String city = (String)request.getAttribute("city");
	%>
	<dl>
		<dt>市：</dt>
		<dd>
			<span <%if("".equals(city)){ %>style="background:#45d4a2;"<%} %>><a href="javascript:changeArea('${province }','','')">全部</a></span>
			<%
			for(int i=0, size=cityList.size(); i<size; i++){
				sai = (SysAreaInfo)cityList.get(i);
			%>
			<span <%if(sai.getCitycode().equals(city)){ %>style="background:#45d4a2;"<%} %>><a href="javascript:changeArea('${province }','<%=sai.getCitycode() %>','')"><%=sai.getAreaname() %></a></span>
			<%} %>
		</dd>
	</dl>
	<%} %>
	<%
	if(countyList != null && countyList.size() > 0){ 
		String county = (String)request.getAttribute("county");
	%>
	<dl>
		<dt>区：</dt>
		<dd>
			<span <%if("".equals(county)){ %>style="background:#45d4a2;"<%} %>><a href="javascript:changeArea('${province }','${city }','')">全部</a></span>
			<%
			for(int i=0, size=countyList.size(); i<size; i++){
				sai = (SysAreaInfo)countyList.get(i);
			%>
			<span <%if(sai.getCitycode().equals(county)){ %>style="background:#45d4a2;"<%} %>><a href="javascript:changeArea('${province }','${city }','<%=sai.getCitycode() %>')"><%=sai.getAreaname() %></a></span>
			<%} %>
		</dd>
	</dl>
	<%} %>
	<div style="width:94%;text-align:center;"><span><a onclick="getType('0')" style="background:#45d4a2;padding:3px 20px;font-size:16px;cursor:pointer;">确定</a></span></div>
</div>
<input type="hidden" name="province" id="province" value="${province }"/>
<input type="hidden" name="city" id="city" value="${city }"/>
<input type="hidden" name="county" id="county" value="${county }"/>
<input type="hidden" name="hiddendiv" id="hiddendiv" value="${hiddendiv }"/>
<!-- 浮动层 end-->

<div id="box">
	<div class="box_1">
    	<div class="box_1_size">
        	<a href="javascript:getType('1')">
            	<span style="float:left;">地区</span>
                <span><img src="/weixin/images/box_1_size_bg.png" class="box_1_size_1_img" /></span>
            </a>
            <input type="text" class="box_1_size_1_input" name="searchname" placeholder="搜索学校" value="${searchname }"/>
            <a href="javascript:searchRecord();">
            <div class="box_1_size_2">
           		<span>搜索</span>
           	</div>
            </a>
    	</div>
    </div>
    <logic:iterate id="model" name="pagelist" property="datalist" indexId="index">
    <div <%if(index == 0){ %>class="container_4"<%}else{ %>class="container_5"<%} %> onclick="selectDeal('<bean:write name="model" property="unitid"/>')">
    	<div class="container_1_size">
        	<label class="container_1_size_111"><bean:write name="model" property="unitname"/></label>
        	<div class="container_4_form_1">
        		<input type="radio" name="schoolid" value="<bean:write name="model" property="unitid"/>" class="container_4_radio_list1" />
        	</div>
        </div>
    </div>
    </logic:iterate>
	<logic:empty name="pagelist" property="datalist">
		<div class="container_5" style="margin-top:15px;border-top:1px solid #ddd;">
    	<div class="container_1_size">
        	<label class="container_1_size_11">暂无数据！</label>
        	<div class="container_4_form_1">
        	</div></div>
    	</div>
	</logic:empty>
    <logic:greaterThan value="1" name="pagelist" property="totalPages">
    <%@ include file="page.jsp"%>
    </logic:greaterThan>
</div>
<input type="hidden" name="openid" value="${openid }"/>
<input type="hidden" name="headimgurl" value="${headimgurl }"/>
<input type="hidden" name="nickname" value="${nickname }"/>
<input type="hidden" name="sex" value="${sex }"/>
<input type="hidden" name="scanResult" value="${scanResult }"/>

<input type="hidden" name="usertype" value="${usertype }"/>
<input type="hidden" name="xueduan" value="${xueduan }"/>
<input type="hidden" name="username" value="${username }"/>
<input type="hidden" name="loginname" value="${loginname }"/>
<input type="hidden" name="password" value="${password }"/>
<input type="hidden" name="repassword" value="${repassword }"/>
<input type="hidden" name="mobile" value="${mobile }"/>
</form>
</body>
</html>