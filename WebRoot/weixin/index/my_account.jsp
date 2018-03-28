<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>我的账号</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript" src="/weixin/js/jquery-2.1.1.min.js"></script>
</head>

<body style="background-color:#f3f3f3;">
<%
SysUserInfo userInfo = (SysUserInfo)request.getAttribute("userInfo");
%>
<div id="content">
	<div class="content_user">
		<%if(userInfo.getPhoto().startsWith("http://")){ %>
    	<img src="<%=userInfo.getPhoto() %>" id="userPhoto" width="120" height="120"/>
    	<%}else{ %>
    	<img src="/upload/<%=userInfo.getPhoto() %>" id="userPhoto" width="120" height="120"/>
    	<%} %>
        <p><%=userInfo.getUsername() %></p>     
    </div>
    <div class="content_font">
    	<p><%=Constants.getCodeTypevalue("sex", userInfo.getSex()) %></p>
        <p><bean:write name="unitInfo" property="unitname"/></p>
    </div>
    
    <div class="content_botton">
    	<a href="/weixinAccountIndex.app?method=beforeUpdatePwd&userid=${userid }">
    		<div class="botton4">
    			<p>修改密码</p>
    		</div>
    	</a>
    	<a href="javascript:updatePhoto()">
   			<div class="botton3">
    			<p>修改头像</p>
    		</div>
    	</a>
    	<%-- 
    	<a href="/weixinAccountIndex.app?method=beforeUpdateInfo&userid=${userid }" class="botton5">更换手机号？点此修改</a>
        <a href="/weixinAccountIndex.app?method=logoutConfirm&userid=${userid }" class="botton5">更换账号？退出绑定</a>
        --%>
    </div>
</div>
<div id="footer">
	<a href="/weixinAccountIndex.app?method=userindex&userid=${userid }" class="footer_0">
    	<img src="/weixin/images/wd.png" />
        <p>首页</p>
    </a>
    <%@ include file="/weixin/index/scan_footer.jsp"%>
	<a href="/weixinAccountIndex.app?method=myAccount&userid=${userid }" class="footer_00">
    	<img src="/weixin/images/user.png" />
    	<p>我的账号</p>
    </a>
</div>
<script type="text/javascript">
function updatePhoto(){
	wx.chooseImage({
	    count: 1,
	    //sizeType: ['original', 'compressed'],//原图，压缩图
	    sizeType: ['compressed'],
	    sourceType: ['album', 'camera'],
	    success: function (res) {
	        var localIds = res.localIds;
	        syncUpload(localIds);
	    }
	});
	var syncUpload = function(localIds){
		//移除数组中的最后一个元素并返回该元素,如果该数组为空，那么将返回 undefined
		var localId = localIds.pop();
		document.getElementById("userPhoto").src = localId;
	 	wx.uploadImage({
	  		localId: localId,
	  		isShowProgressTips: 1,
	  		success: function (res) {
	   			var serverId = res.serverId; // 返回图片的服务器端ID
	   			//其他对serverId做处理的代码
	   			//if(localIds && localIds.length > 0){
	    		//	syncUpload(localIds);
	   			//}
	   			//开始调用微信公众号api获取图片上传至自己的服务器
	   			uploadPhoto(serverId);
	  		}
	 	});
	}
	var uploadPhoto = function(serverId){
		window.location.href = "/weixinStudent.app?method=uploadPhoto&userid=${userid }&serverId=" + serverId;
	}
}
</script>
</body>
</html>