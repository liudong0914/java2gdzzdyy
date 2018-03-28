<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>账号设置</title>
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css">
<script src="/weixin/js/jquery-2.1.1.min.js" language="javascript" type="text/javascript"></script>
<script>
	function chooseImage(){
		wx.chooseImage({
		    count: 1, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        var localIds = eval(res.localIds); // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		        uploadPhoto(localIds.pop());
		    }
		});
	}
	
	function uploadPhoto(localid){
		wx.uploadImage({
            localId: localid, // 需要上传的图片的本地ID，由chooseImage接口获得
            isShowProgressTips: 1, // 默认为1，显示进度提示
            success: function (res) {
                var serverId = res.serverId; // 返回图片的服务器端ID
                $.ajax({
    		        type:"post",
    		        url:"weixinAccountIndex.app?method=saveUserPhoto",
    		        data:"userid=${userid}&serverPhotoIds="+serverId,
    		        success:function(msg){
    		        	if(msg!=""){
    			        	$("#userphoto").attr("src","/upload/"+msg);
    		        	}
    				}
    			});
            }
        });
	}
</script>
<%@ include file="/weixin/index/weixin_js.jsp"%>
</head>
<body>	
<%@ include file="/weixin/account/dh.jsp"%>
<div class="search search_write">
	<a href="/weixinAccountIndex.app?method=getPersonalCenter&userid=${userid}"><div  class="search_left">
    	<img src="/weixin/images/a01.png" class="search_class" />
    </div>
    </a>
    <div  class="search_wk">
    	<p class="search_wk_p">账号设置</p>
    </div>
    <a href="javascript:showDH()"><div  class="search_left search_right_dh">
    	<img src="/weixin/images/a02.png"  class="search_class01"  />
        <p>导航</p></p>
    </div></a>
</div>
<div class="search_padding"></div>
<div id="bottom">
    <a href="javascript:chooseImage()">
	    <div class="number">
	        <img src="${userinfo.photo}" id="userphoto"  class="number_img"/>
	        <p>头像更换</p>
	        <img src="/weixin/images/c02.png" class="number_img01" />
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=beforeUpdateNickName&userid=${userid}">
	    <div class="number01">
	        <img src="/weixin/images/c03.png"  class="number01_img"/>
	        <p>修改昵称</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=beforeUpdatePwd&userid=${userid}">
    	<div class="number01 number02">
	        <img src="/weixin/images/c04.png"  class="number01_img"/>
	        <p>设置支付密码</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=beforeUpdateMobile&userid=${userid}">
	    <div class="number01 number02">
	        <img src="/weixin/images/c05.png"  class="number01_img"/>
	        <p>变更手机号</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
    <a href="/weixinAccountIndex.app?method=beforeClearAttention&userid=${userid}">
	    <div class="number01 number02">
	        <img src="/weixin/images/c11.png"  class="number01_img"/>
	        <p>取消微信绑定</p>
	        <img src="/weixin/images/c02.png" class="number01_img01" />
	    </div>
    </a>
</div>
<%@ include file="/weixin/account/scan.jsp"%>
</body>
</html>