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

<!--数字分页start-->
<script type="text/javascript" src="../../libs/js/nav/pageNumber.js"></script>
<script type="text/javascript" src="../../libs/js/sk/page.js"></script>
<script type="text/javascript" src="../../libs/js/sk/comm.js"></script>
<!--数字分页end-->
<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<<div class="box1" position=center>
       <table class="tableStyle" formMode="line" align="center">
         <tr>
            <th colspan="2">短信详情</th>
         </tr>
         <tr>
          <td class="ali03">短信内容：</td>
          <td class="ali01"><div style="width:400px;height:100px;">${model.content}</div></td>
         </tr>
	     <tr>
	       <td class="ali03">接收短信手机号码：</td>
	       <td class="ali01">${model.mobile}</td>
	     </tr>
	     <tr>
	       <td class="ali03">接收短信用户：</td>
	       <td class="ali01">${username}</td>
	     </tr>
	     <tr>
	       <td class="ali03">发送状态：</td>
	       <td class="ali01">${model.state eq '0'?"未发送":model.state eq '1'?"发送成功":model.state eq '2'?"发送失败":""}</td>
	     </tr>
	     <tr>
	       <td class="ali03">发送时间：</td>
	       <td class="ali01">${model.createdate}</td>
	     </tr>
	     <tr>
	       <td class="ali03">短信类型：</td>
	       <td class="ali01">${model.type eq '1'?"普通短信":model.type eq '2'?"变更手机号码验证码":model.type eq '3'?"修改支付密码验证码":""}</td>
	     </tr>
	     <c:if test="${model.type ne '1'}">
	     <tr>
	       <td class="ali03">验证码：</td>
	       <td class="ali01">${model.code}</td>
	     </tr>
	     <tr>
	       <td class="ali03">验证有效截止时间：</td>
	       <td class="ali01">${model.codevalidtime}</td>
	     </tr>
	     </c:if>
	     <tr>
	       <td colspan="2">
		     <button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            
	       </td>
         </tr>
       </table>
     </div>
</body>
</html>