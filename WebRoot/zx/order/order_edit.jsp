<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->
</head>
<body>
<div>
<div class="box1" position="center">
<html:form action="/zxHelpOrderAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="4">回复答疑</th>
		</tr>
		<tr>
			<td class="ali03" width="95px;">倒计时：</td>
			<td class="ali01" colspan="3">
				<div class="time">
				    <span id="t_d">00天</span>
				    <span id="t_h">00时</span>
				    <span id="t_m">00分</span>
				    <span id="t_s">00秒</span>
				</div>
				<script>
				  function GetRTime(){
					var time = document.getElementById("zxHelpOrder.enddate").value;
				    var EndTime= new Date(time);
				    var NowTime = new Date();
				    var t =EndTime.getTime() - NowTime.getTime();
				    var d=0;
				    var h=0;
				    var m=0;
				    var s=0;
				    if(t>=0){
				      d=Math.floor(t/1000/60/60/24);
				      h=Math.floor(t/1000/60/60%24);
				      m=Math.floor(t/1000/60%60);
				      s=Math.floor(t/1000%60);
				    }
				
				
				    document.getElementById("t_d").innerHTML = d + "天";
				    document.getElementById("t_h").innerHTML = h + "时";
				    document.getElementById("t_m").innerHTML = m + "分";
				    document.getElementById("t_s").innerHTML = s + "秒";
				  }
				  setInterval(GetRTime,0);
				</script>
				
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">回复内容：</td>
			<td class="ali01" colspan="3">
				<textarea name="content" id="content" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars("")%></textarea>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">
				<button type="button" onclick="diagAddRecord('/zxHelpOrderAction.do?method=videoAdd')"><span class="icon_add">新增视频:</span></button>
			</td>
			<td class="ali01" >
				<span name="videoName"  id="videoName"></span>
			</td>
			<td class="ali03" width="95px;">
			<button type="button" onclick="diagAddRecord2('/zxHelpOrderAction.do?method=pictureAdd')"><span class="icon_add">新增图片:</span></button>
			</td>
			<td class="ali01" >
				<span name="pictureName"  id="pictureName"></span>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<button type="button" class="margin_right5" onclick="saveRecord()" ><span class="icon_save">保存</span></button>
				<button type="button" onclick="javascript:history.go(-1);"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
	<input type="hidden" name="zxHelpFileId"  id="zxHelpFileId"/>
	<input type="hidden" name="zxHelpFileName"  id="zxHelpFileName"/>
	<input type="hidden" name="zxHelpFileIdpicture"  id="zxHelpFileIdpicture"/>
	<input type="hidden" name="zxHelpFilePictureName"  id="zxHelpFilePictureName"/>
	
	<input type="hidden" name="zxHelpOrder.orderid" value='<bean:write property="orderid"  name="model"/>'/>
	<input type="hidden" name="zxHelpOrder.questionid" value='<bean:write property="questionid"  name="model"/>'/>
	<input type="hidden" name="zxHelpOrder.createdate" value='<bean:write property="createdate"  name="model"/>' />
	<input type="hidden" name="zxHelpOrder.userip" value='<bean:write property="userip"  name="model"/>' />
	<input type="hidden" name="zxHelpOrder.userid" value='<bean:write property="userid"  name="model"/>' />
	<input type="hidden" name="zxHelpOrder.enddate" id="zxHelpOrder.enddate" value='<bean:write property="enddate" name="model"/>' />
	<input type="hidden" name="zxHelpOrder.money" value='<bean:write property="money"  name="model"/>' />
	<input type="hidden" name="zxHelpOrder.status" value='<bean:write property="status"  name="model"/>' />
	<input type="hidden" name="zxHelpOrder.paystatus" value='<bean:write property="paystatus"  name="model"/>' />
	
</html:form>
</div>
</div>
<script>
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="content"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}

function saveRecord(){
	if(validateForm('form[name=zxHelpOrderActionForm]')){
		var objectForm = document.zxHelpOrderActionForm;
		//同步数据后可以直接取得textarea的value
		keditor.sync();
	  	objectForm.action="zxHelpOrderAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function diagAddRecord(url){
	var diag = new top.Dialog();
	diag.Title = "添加视频";
	diag.URL = url;
	diag.Width = 850;
	diag.Height = 500;
	diag.ShowMaxButton=true;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
			var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
			document.getElementById('zxHelpFileId').value = uploadimageurl;
			var uploadimageurl1 = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl1').value;
			document.getElementById('zxHelpFileName').value = uploadimageurl1;
			document.getElementById("videoName").innerText=uploadimageurl1;
		}
		diag.close();
	};
	diag.show();
}
function diagAddRecord2(url){
	var diag = new top.Dialog();
	diag.Title = "添加图片";
	diag.URL = url;
	diag.Width = 850;
	diag.Height = 500;
	diag.ShowMaxButton=true;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl2')){
			var uploadimageurl2 = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl2').value;
			document.getElementById('zxHelpFileIdpicture').value = uploadimageurl2;
			var uploadimageurl3 = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl3').value;
			document.getElementById('zxHelpFilePictureName').value = uploadimageurl3;
			document.getElementById("pictureName").innerText=uploadimageurl3;
		}
		diag.close();
	};
	diag.show();
}
</script>
</body>
</html>
<%!
private String htmlspecialchars(String str) {
	if(str == null || "".equals(str)) return "";
	str = str.replaceAll("&", "&amp;");
	str = str.replaceAll("<", "&lt;");
	str = str.replaceAll(">", "&gt;");
	str = str.replaceAll("\"", "&quot;");
	return str;
}
%>