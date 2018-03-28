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
<script src="../../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->
<%@ include file="js.jsp"%>
</head>
<body style="background-color:#f9f9f9;">
<!------头部-------->
<%@ include file="top.jsp"%>
<!------内容-------->
<html:form action="/pcenter.do" method="post">
<div class="personal">
	<%@ include file="left.jsp" %>
    <div class="information_right">
    	<div class="information_right_top">
			<p>密码修改</p>
        </div>
        <div class="information_right_main possword">
        	<logic:present name="promptinfo" scope="request">
					<div class="information_right_main_02">
						<p>&nbsp;</p>
						<P><bean:write name="promptinfo" scope="request"/></P>
					</div>
			</logic:present>
            <div class="information_right_main_02">
            	<p style="letter-spacing: 0.5px;"><a class="teacher">*</a>原始密码：</p>
                <p>
                	<input type="password" class="validate[required]" name="oldpassword" id="oldpassword" value="" style="width:200px;"/>
                </p>
            </div>
            <div class="information_right_main_02">
            	<p class="possword_p"><a class="teacher">*</a>新&nbsp;密&nbsp;码：</p>
                <P>
                	<input type="password" class="validate[required]" name="sysUserInfo.password" id="password" value="" style="width:200px;"/>
                </P>
            </div>
            <div class="information_right_main_02">
            	<p style="letter-spacing: 0.5px;"><a class="teacher">*</a>再次输入：</p>
                <p>
                	<input type="password" class="validate[required]" name="passwordagain" id="passwordagain" value="" style="width:200px;"/>
                </p>
            </div>
            <a href="#" onclick="saveRecord()" class="information_right_main_a" style="margin-left: 94px;">确定</a>
        </div><!----information_right_main-e---->
        <div class="possword_bottom">
            <p>修改密码说明：</p>
            <span>1.一旦修改了密码，请用新密码来登录。</span>
            <span>2.温馨提示：为了您的账号安全最好不要设置成您的生日，电话号码，车牌和门牌号码。</span>
        </div><!----possword_bottom-e---->
    </div><!----information_right-e---->
</div><!----personal-e---->
<input type="hidden" name="sysUserInfo.userid" value='<bean:write property="userid"  name="model"/>'/>
</html:form>
<script>
function saveRecord(){
	if(validateForm('form[name=sysUserInfoActionForm]')){
		if(document.getElementById('password').value!=document.getElementById('passwordagain').value){
		    top.Dialog.alert("两次输入的密码不一致，请重试!");
		    return false;
		}
		var objectForm = document.sysUserInfoActionForm;
	  	objectForm.action="/pcenter.do?method=modifySave";
	  	objectForm.submit();
	}
}
</script>
<%@ include file="footer.jsp"%>

</body>
</html>