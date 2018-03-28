<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>个人中心</title>
<link type="text/css" rel="stylesheet" href="/skin/pcenter/css/style.css">
<script type="text/javascript" src="/skin/pcenter/js/jquery-1.8.2.min.js"></script>

<!--框架必需start-->
<script type="text/javascript" src="../../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<!--框架必需end-->

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%@ include file="js.jsp"%>
<script type="text/javascript">
function downfile(){
	var objectForm = document.sysUserInfoActionForm;
  	objectForm.action="/pcenter.do?method=downFile";
  	objectForm.submit();
}
</script>
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
			<p>教师认证  </p>
        </div>
        <div class="information_right_main teacher_main">
            <div class="information_right_main_02">
            	<p style="letter-spacing: 3px;"><a class="teacher">*</a>真实姓名：</p>
                <p>
                	<bean:write property="username" name="model"/>
                </p>
            </div>
            <div class="information_right_main_03">
            	<p style="letter-spacing: 1.4px;"><a class="teacher">*</a>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别：</p>
                <p><bean:write property="flagl" name="model" /></p>
            </div>
             <div class="information_right_main_02">
            	<p style="letter-spacing: 3px;"><a class="teacher">*</a>手机号码：</p>
                <p>
                	<bean:write property="mobile" name="model"/>
                </p>
            </div>
            <div class="information_right_main_02">
            	<p style="letter-spacing: 3px;"><a class="teacher">*</a>身份证号：</p>
                <p>
                	<bean:write property="identitycardno" name="model"/>
                </p>
            </div>
            <div class="information_right_main_02">
            	<p style="letter-spacing: 0.2px;"><a class="teacher">*</a>手持证件照：</p>
                <p>
                	<img src="/upload/<bean:write property="idphto" name="model"/>"  width="220" height="120" border="1" />
                </p>
            </div>
            <div class="information_right_main_02">
            	<p style="letter-spacing: 0.2px;"><a class="teacher">*</a>教师资格证：</p>
                <p>
                	<img src="/upload/<bean:write property="imgpath" name="model"/>"  width="220" height="120" border="1" />
                </p>
            </div>
            <logic:notEmpty name="model" property="filepath">
             <div class="information_right_main_02">
            	<p style="letter-spacing: 3px; padding-left: 15px;">附件下载：</p>
                <p>
                	<a href="#" onclick="downfile()"><img src="/libs/images/bfileext/bak/rar.gif" alt="点击下载附件" width="45px" height="45px"/></a>
                </p>
            </div>
            </logic:notEmpty>
            <div class="information_right_main_02">
            	<p style="letter-spacing: 3px; padding-left: 15px;">申请状态：</p>
                <p>
                	<c:if test="${model.status == '0'}">
					审核中
				</c:if>
				<c:if test="${model.status == '1'}">
					通过
				</c:if>
				<c:if test="${model.status == '2'}">
					驳回
				</c:if>
                </p>
            </div>
            <logic:equal value="2" name="model" property="status">
	            <div class="information_right_main_02">
	            	<p>驳回原因：</p>
	                <p>
	                	<bean:write property="returndescript" name="model"/>
	                </p>
	            </div>
            </logic:equal>
            <logic:notEqual value="1" name="model" property="status">
            	<a href="/pcenter.do?method=beforeUpdateTwo&teacherid=${model.teacherid }"  class="information_right_main_a">编辑</a>
            </logic:notEqual>
        </div><!----information_right_main-e---->
        <div class="possword_bottom">
        	<img src="/skin/pcenter/images/icon10.png" class="teacher_img" />
            <div class="teacher_font">
                <span>通过身份验证后即可在平台上参与答疑回复挣学币，并可提现。</span><br />
                <span>进步学堂承诺，认证过程中填写的身份证号与上传的证明材料只做认证，不会用作其他任何用途。</span>
            </div>
        </div><!----possword_bottom-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
<input type="hidden" name="filepath" value="<bean:write property="filepath" name="model"/>" />
</html:form>
<%@ include file="footer.jsp"%>
</body>
</html>