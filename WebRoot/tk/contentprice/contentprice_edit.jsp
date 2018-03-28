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
	  	objectForm.action="tkBookContentPriceAction.do?method=<bean:write name="act"/>";
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
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/tkBookContentPriceAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">	
		<tr>
			<th colspan="2">修改价格</th>
		</tr>
		<tr>
			<td class="ali03">定价：</td>
			<td class="ali01"><input type="text" name="tkBookContentPrice.price"  onblur="counta()" value="<bean:write property="price" name="model"/>"/>
		</tr>	
		<tr>
			<td class="ali03">折扣：</td>
			<td class="ali01"><input type="text" name="tkBookContentPrice.discount" onblur="countb()" value="<bean:write property="discount" name="model"/>"/></td>
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
				<button type="button" onclick="javascript:history.go(-1)" ><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
	<input type="hidden" name="tkBookContentPrice.priceid" value="<bean:write property="priceid" name="model"/>"/> 
	<input type="hidden" name="tkBookContentPrice.type" value="<bean:write property="type" name="model"/>"/> 
	<input type="hidden" name="tkBookContentPrice.sellcount" value="<bean:write property="sellcount" name="model"/>"/>
	<input type="hidden" name="bookcontenid" value="<bean:write name="bookcontentid"/>"/>
</html:form>
</div>
</div>

</body>
</html>