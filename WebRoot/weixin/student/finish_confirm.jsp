<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>确认信息</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript">
function finish(){
	document.finishForm.action = "/weixinStudent.app?method=finish";
	document.finishForm.submit();
}
function goBack(){
	document.pageForm.action = "/weixinStudent.app?method=lianxi1";
	document.pageForm.submit();
}
</script>
</head>

<body style="background-color:#f3f3f3;">
<form method="post" name="pageForm" >
<div id="container">
	<div class="container_nr">
    	<div class="conrainer_nr_font">
        	<!-- <p>请确定当前作业已全部做完，提交成功后可重做作业，您真的要提交作业吗？</p> -->
        	<p>请确定当前作业已全部做完，您真的要提交作业吗？</p>
        </div>
    </div>
    
	<div class="container_nr_moble">
    	<a href="javascript:finish();">
        	<div class="container_nr_moble1">
    			<span>确定</span>
        	</div>
        </a>
        <a href="javascript:goBack();">
        	<div class="container_nr_moble3">
    			<span>取消</span>
        	</div>
        </a>
    </div>
</div>
<input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
<input type="hidden" name="tasktype" value="<bean:write name="tasktype"/>"/>
<input type="hidden" name="taskid" value="<bean:write name="taskid"/>"/>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
<input type="hidden" name="index" value="<bean:write name="index"/>"/>
<input type="hidden" name="next" value="<bean:write name="next"/>"/>
</form>
<form method="post" name="finishForm" target="_top">
<input type="hidden" name="bookcontentid" value="<bean:write name="bookcontentid"/>"/>
<input type="hidden" name="classid" value="<bean:write name="classid"/>"/>
<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
<input type="hidden" name="tasktype" value="<bean:write name="tasktype"/>"/>
<input type="hidden" name="taskid" value="<bean:write name="taskid"/>"/>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
<input type="hidden" name="index" value="<bean:write name="index"/>"/>
<input type="hidden" name="next" value="<bean:write name="next"/>"/>
</form>
</body>
</html>