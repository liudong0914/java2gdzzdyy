<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.cms.bo.CmsNewsInfo"%>
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

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->

<script>
function saveRecord(){
	if(validateForm('form[name=tkBookContentPriceActionForm]')){
		var objectForm = document.tkBookContentPriceActionForm;
		var price = document.getElementsByName("tkBookContentPrice.price")[0].value;
		var discount = document.getElementsByName("tkBookContentPrice.discount")[0].value;
		var sellprice = document.getElementsByName("tkBookContentPrice.sellprice")[0].value;
		var re = /^[0-9]+.?[0-9]*$/;
		var patrn = /^[1-9]$/ig; 
		if(!re.test(price)){
		  alert("定价必须为数字");
		  return false;
		}
		if(!re.test(discount)){
		  alert("折扣必须为数字");
		  return false;
		}
		if(!re.test(sellprice)){
		  alert("售卖价格必须为数字");
		  return false;
		}
		document.getElementById('popWindow').style.display = 'block'; 
        document.getElementById('maskLayer').style.display = 'block';
	  	objectForm.action="tkBookContentPriceAction.do?method=initEditSave";
	  	objectForm.submit();
	}
}
var re = /^[0-9]+.?[0-9]*$/;
function counta(){
    var price = document.getElementsByName('tkBookContentPrice.price')[0].value;
    if(!re.test(price)){
		  alert("请输入数字");
		  document.getElementsByName('tkBookContentPrice.price')[0].value="";
		  return false;
   }else{
      var discount = document.getElementsByName("tkBookContentPrice.discount")[0].value;
      if(discount != null && discount != ""){
      count();
      }
    }	
}
function countb(){
    var discount = document.getElementsByName("tkBookContentPrice.discount")[0].value;

    if(!re.test(discount)||(discount>10||discount<0)){
		  alert("请输入0-10的数字");
		  document.getElementsByName("tkBookContentPrice.discount")[0].value="";
		  return false;
	}else{
	  var price = document.getElementsByName("tkBookContentPrice.price")[0].value;
	  if(price != null){
        count();
      }
	}
}
function count(){
 var discount = parseFloat(document.getElementsByName('tkBookContentPrice.discount')[0].value);
 var price = parseFloat(document.getElementsByName('tkBookContentPrice.price')[0].value);
 document.getElementsByName('tkBookContentPrice.sellprice')[0].value = price*discount/10;  
}
</script>
<style type="text/css"> 
.popWindow { 
background-color:#9D9D9D; 
width: 100%; 
height: 100%; 
left: 0; 
top: 0; 
filter: alpha(opacity=50); 
opacity: 0.5; 
z-index: 1; 
position: absolute; 

} 
.maskLayer { 
background-color:#000; 
width: 200px; 
height: 30px; 
margin:0 0 0 -100px;
line-height: 30px; 
left: 50%; 
top: 50%; 
color:#fff; 
z-index: 2; 
position: absolute; 
text-align:center; 
} 
</style>
</head>
<body>
<div id="">
<div class="box1" position="center">
<html:form action="/tkBookContentPriceAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">	
		<tr>
			<th colspan="2">初始化价格</th>
		</tr>
		<tr>
			<td class="ali03">定价：</td>
			<td class="ali01"><input type="text" name="tkBookContentPrice.price" CK_TYPE="int" onblur="counta()" value="<bean:write property="price" name="model"/>" />
		</tr>	
		<tr>
			<td class="ali03">折扣：</td>
			<td class="ali01"><input type="text" name="tkBookContentPrice.discount" onblur="countb()"  value="<bean:write property="discount" name="model"/>"/></td>
		</tr>
        <tr>
			<td class="ali03">售卖价格：</td>
			<td class="ali01">
				<input type="text" name="tkBookContentPrice.sellprice" value="<bean:write property="sellprice"  name="model"/>" />
			</td>
		</tr>		
        <tr>
			</tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()" ><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table> 
	
	<input type="hidden" name="bookid" value="<bean:write name="bookid"/>"/>
</html:form>
</div>
</div>

</body>

</html>
<div id="popWindow" class="popWindow" style="display: none;"> 
<div id="maskLayer" class="maskLayer" style="display: none;">价格正在初始化中，请等待~~~ </div>
</div> 