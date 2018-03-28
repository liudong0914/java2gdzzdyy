<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>我的班级列表</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>

<body>
<%@ include file="/weixin/account/teacher_dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=teacher&userid=${userid }"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div></a>
    <div  class="search_wk">
    	<p class="search_wk_p">班级列表</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div id="container_bj">
	<logic:iterate id="model" name="list">
    <div class="container_44">
    	<a href="/weixinTeacher.app?method=classInfo&userid=${userid }&classid=<bean:write name="model" property="classid"/>">
        	<div class="conrainer_4_bj">
            	<p class="conrainer_4_bj_1"><bean:write name="model" property="classname"/></p>
                <p class="conrainer_4_bj_2">编号: <bean:write name="model" property="classno"/></p>
            </div>
            <font class="conrainer_4_bj_a"><bean:write name="model" property="students"/>学生</font>
        </a>
    </div>
    </logic:iterate>
    <logic:empty name="list">
    <div class="container_44">
    	<div class="conrainer_4_bj">
        	<p class="conrainer_4_bj_3">暂无班级</p>
        </div>
    </div>
    </logic:empty>
</div>
</body>
</html>