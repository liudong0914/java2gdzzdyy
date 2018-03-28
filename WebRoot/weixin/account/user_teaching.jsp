<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>设置教学</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=userInfoManager&userid=${userid}">
		<div  class="search_left">
	    	<img src="/weixin/images/a01.png" class="search_class" />
	    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">设置教学</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
	<a href="/weixinAccountIndex.app?method=getSubjectInfo&userid=${userid}&teachingid=${empty(m0)?'':m0.teachingid}">
	    <div class="number01">
	        <p>设置1:</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	        <c:if test="${!empty(m0)}">
	        	<span>${m0.subjectname}&nbsp;&nbsp;&nbsp;&nbsp;${m0.gradename}</span>
	        </c:if>
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=getSubjectInfo&userid=${userid}&teachingid=${empty(m1)?'':m1.teachingid}">
	    <div class="number01">
	        <p>设置2:</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	        <c:if test="${!empty(m1)}">
	        	<span>${m1.subjectname}&nbsp;&nbsp;&nbsp;&nbsp;${m1.gradename}</span>
	        </c:if>
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=getSubjectInfo&userid=${userid}&teachingid=${empty(m2)?'':m2.teachingid}">
	    <div class="number01">
	        <p>设置3:</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	        <c:if test="${!empty(m2)}">
	        	<span>${m2.subjectname}&nbsp;&nbsp;&nbsp;&nbsp;${m2.gradename}</span>
	        </c:if>
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=getSubjectInfo&userid=${userid}&teachingid=${empty(m3)?'':m3.teachingid}">
	    <div class="number01">
	        <p>设置4:</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	        <c:if test="${!empty(m3)}">
	        	<span>${m3.subjectname}&nbsp;&nbsp;&nbsp;&nbsp;${m3.gradename}</span>
	        </c:if>
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=getSubjectInfo&userid=${userid}&teachingid=${empty(m4)?'':m4.teachingid}">
	    <div class="number01">
	        <p>设置5:</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	        <c:if test="${!empty(m4)}">
	        	<span>${m4.subjectname}&nbsp;&nbsp;&nbsp;&nbsp;${m4.gradename}</span>
	        </c:if>
	    </div>
    </a>
</body>
</html>