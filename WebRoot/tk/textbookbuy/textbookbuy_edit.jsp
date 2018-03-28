<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.sun.media.sound.ModelAbstractChannelMixer"%>
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
  
  function saveRecord(){
   if(validateForm('form[name=tkTextBookBuyActionForm]')){
   		 	var isdelivery=document.getElementsByName("isdelivery");
		   var rdVal;
		   for(var i=0;i<isdelivery.length;i++){
		     if(isdelivery.item(i).checked){
		         rdVal=isdelivery.item(i).getAttribute("value"); 
		         break;
		      }else{
		      	continue;
		      }
		   }
		   if(rdVal !=""){
				var objectForm = document.tkTextBookBuyActionForm;
			  	objectForm.action="tkTextBookBuyAction.do?method=<bean:write name="act"/>&isdelivery="+rdVal;
			  	objectForm.submit();
	  		}else{
	  			alert("请选择发货状态！");
	  			return false;
	  		}
	}
  }
  
</script>
  </head>
  <body>
    <div>
     <div class="box1" position=center>
      <html:form action="/tkTextBookBuyAction.do" method="post">
       <table class="tableStyle" formMode="line" align="center">
         <tr>
            <th colspan="3">${title }</th>
         </tr>
         <tr>
          <td class="ali03" width="80">教材名称：</td>
          <td class="ali01">${textBookInfo.textbookname }</td>
         </tr>
         <tr>
         <td class="ali03">缩略图：</td>
          <td class="ali01" >
           <img src="/upload/${textBookInfo.sketch }"  width="90" height="120" border="1" id="uploadImgShow" />
           </td>
         </tr>
        <%--  <tr>
           <td class="ali03" >作者： </td>
           <td class="ali01">${textBookInfo.author }</td>
         </tr> --%>
         <tr>
           <td class="ali03">出版社：</td>
           <td class="ali01">${textBookInfo.press }</td>
         </tr>
         <tr>
			<td class="ali03">范围和种类：</td>
			<td class="ali01">${textBookInfo.type }</td>
		</tr>
        <tr>
         	<td class="ali03">单价：</td>
         	<td class="ali01">${textBookInfo.price }(学币)</td>
        </tr>
        <tr>
         	<td class="ali03">折扣：</td>
         	<td class="ali01"><c:if test="${textBookInfo.discount == '10'}">无</c:if><c:if test="${textBookInfo.discount != '10'}">${textBookInfo.discount}折</c:if></td>
        </tr>
		<tr>
			<td class="ali03">售价：</td>
			<td class="ali01">${textBookInfo.sellprice }(学币)</td>
		</tr>
         <tr>
           <td class="ali03">状态：</td>
           <td class="ali01">${bookStatus }</td>
         </tr>
         <tr>
			<td class="ali03">联系电话：</td>
			<td class="ali01">${textBookInfo.phone }</td>
		</tr>
		<tr>
			<td class="ali03">订购数量：</td>
			<td class="ali01">${model.totalnum }</td>
		</tr>
		<tr>
			<td class="ali03">订购总价：</td>
			<td class="ali01">${model.totalprice }(学币)</td>
		</tr>
		<tr>
			<td class="ali03">购买人姓名：</td>
			<td class="ali01">${sysUserInfo.username }</td>
		</tr>
		<tr>
			<td class="ali03">收件人姓名：</td>
			<td class="ali01">${model.recipientname }</td>
		</tr>
		<tr>
			<td class="ali03">收件人电话：</td>
			<td class="ali01">${model.recipientphone }</td>
		</tr>
		<tr>
			<td class="ali03">收件人地址：</td>
			<td class="ali01">${model.recipientaddress }</td>
		</tr>
		<tr>
			<td class="ali03">发货状态：</td>
			<td class="ali01">
				<%String isdelivery = (String)request.getAttribute("isdelivery"); %>
				<input type="radio" name="isdelivery" <%if("0".equals(isdelivery)){ %>checked="checked"<%} %> value="0"/>未发货
				<input type="radio" name="isdelivery" <%if("1".equals(isdelivery)){ %>checked="checked"<%} %> value="1"/>已发货
			</td>
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
 <input type="hidden" name="textbookbuyid" value="${model.textbookbuyid }"/>
      </html:form>
     </div>
    </div>
  </body>
</html>
