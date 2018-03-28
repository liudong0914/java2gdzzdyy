<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkTextBookInfo"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
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

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->
<script type="text/javascript">

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=tk/book&pathtype=ID';
	diag.Width = 350;
	diag.Height = 180;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('uploadimageurl')){
			var uploadimageurl = diag.innerFrame.contentWindow.document.getElementById('uploadimageurl').value;
			document.getElementById('uploadImgShow').src = "/upload/" + uploadimageurl
			document.getElementById('uploadImg').value = uploadimageurl;
		}
		diag.close();
	};
	diag.show();
}
  
  function saveRecord(){
   if(validateForm('form[name=tkTextBookInfoActionForm]')){
		var objectForm = document.tkTextBookInfoActionForm;
	  	objectForm.action="tkTextBookInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
  }
  function showSellprice(){
	var price = document.getElementById("price").value;
	var discount = document.getElementById("discount").value;
	var sellprice = "";
	if(price !="" && discount != ""){
		if(discount <= 10){
			sellprice = ((price*discount)/10).toFixed(2);
			document.getElementById("sellprice").value =sellprice;
		}else{
			alert("折扣不能大于10！");
		}
	}
}
  
</script>
  </head>
 <%TkTextBookInfo model = (TkTextBookInfo)request.getAttribute("model"); %> 
  <body>
    <div>
     <div class="box1" position=center>
      <html:form action="/tkTextBookInfoAction.do" method="post">
       <table class="tableStyle" formMode="line" align="center">
         <tr>
            <th colspan="3">${title }</th>
         </tr>
         <tr>
          <td class="ali03" width="80">教材名称：</td>
          <td class="ali01"><input type="text" class="validate[required]" style="width: 200px;" name="tkTextBookInfo.textbookname" value="${model.textbookname }" /><span class="star">*</span></td>
         </tr>
         <tr>
          <td class="ali03" width="80">书号：</td>
          <td class="ali01"><input type="text"  style="width: 200px;" name="tkTextBookInfo.textbookno" value="${model.textbookno }" /></td>
         </tr>
         <tr>
           <td class="ali03" >排序：</td>
           <td class="ali01"><input  type="text" style="width: 100px;" name="tkTextBookInfo.orderindex" class="validate[required,custom[onlyNumber],length[1,6]]" value="${model.orderindex }" /><span class="star">*</span></td>
         </tr>
         <tr>
         <td class="ali03">缩略图：</td>
          <td class="ali01" >
           <img src="/upload/${model.sketch }" title="点击修改缩略图" width="90" height="120" border="1" id="uploadImgShow" onclick="uploadPhoto()"/>
           <input type="hidden" id="uploadImg" name="tkTextBookInfo.sketch" value="${model.sketch }"/>
           </td>
         </tr>
         <tr>
           <td class="ali03" >作者： </td>
           <td class="ali01"><input type="text" class="validate[required]" style="width: 200px;" name="tkTextBookInfo.author" value="${model.author }"/><span class="star">*</span></td>
         </tr>
         <tr>
           <td class="ali03">出版社：</td>
           <td class="ali01"><input type="text" class="validate[required]" style="width: 200px;" name="tkTextBookInfo.press" value="${model.press }"/><span class="star">*</span></td>
         </tr>
         <tr>
			<td class="ali03">范围和种类：</td>
			<td class="ali01"><input type="text" style="width:250px;" name="tkTextBookInfo.type" value="<bean:write property="type" name="model"/>"/></td>
		</tr>
         <tr>
         <td class="ali03">单价：</td>
         <td class="ali01">
         	<input  type="text" id="price" style="width: 100px;" name="tkTextBookInfo.price" class="validate[required,custom[onlyNumberWide]]" value="${model.price }" onchange="showSellprice()"/><span class="star">*</span>
         </td>
         </tr>
          <tr>
         <td class="ali03">折扣：</td>
         <td class="ali01">
         	<input  type="text" id="discount" style="width: 100px;" name="tkTextBookInfo.discount" class="validate[required,custom[onlyNumberWide]]" value="${model.discount }" onchange="showSellprice()"/><span class="star">*</span>(数值从0~10,0为免费,10为原价)
         </td>
         </tr>
		<tr>
			<td class="ali03">售价：</td>
			<td class="ali01"><input type="text"  id="sellprice" name="tkTextBookInfo.sellprice" class="validate[required]" style="width:200px;" value="${model.sellprice }" readonly="readonly" /><span class="star">*</span></td>
		</tr>
         <tr>
           <td class="ali03">状态：</td>
           <td class="ali01">
           <java2code:option name="tkTextBookInfo.status" codetype="status4" valuename="model" property="status"></java2code:option>
           </td>
         </tr>
         <tr>
			<td class="ali03">联系电话：</td>
			<td class="ali01"><input type="text" style="width:200px;" class="validate[telephone]" name="tkTextBookInfo.phone" value="${model.phone }"/></td>
		</tr>
          <td colspan="2">
          <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
          <button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            
          </td>
         </tr>
       </table>
            <!-- 分页与排序 -->
 <input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
 <input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
 <input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
 <input type="hidden" name="tkTextBookInfo.textbookid" value="${model.textbookid }"/>
 <input type="hidden" name="tkTextBookInfo.unitid" value="${model.unitid }"/>
 <input type="hidden" name="tkTextBookInfo.sellcount" value="${model.sellcount }"/>
 <input type="hidden" name="tkTextBookInfo.createdate" value="${model.createdate }"/>
      </html:form>
     </div>
    </div>
  </body>
</html>
