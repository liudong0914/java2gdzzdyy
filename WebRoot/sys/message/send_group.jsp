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

<script type="text/javascript">
	function saveRecord(){
		if($("#title").val()==""){
			alert("请输入消息标题~");
			$("#title").focus();
		}else if($("#mc").val()==""){
			alert("请输入消息内容~");
			$("#mc").focus();
		}else if($("#issms").is(":checked")&&$("#sc").val()==""){
			alert("请输入短信内容");
			$("#sc").focus();
		}else{
			var obj=document.sysMessageInfoActionForm;
			obj.action="/sysMessageInfoAction.do?method=saveSendGroup";
			obj.submit();	
		}
	}
	
	function showContent(){
		$("#smscontent").show();
	}
	
	function hideContent(){
		$("#smscontent").hide();
		$("#sc").val("");
	}
</script>
  </head>
  <body>
    <div>
     <div class="box1" position=center>
      <html:form action="/sysMessageInfoAction.do" method="post">
       <table class="tableStyle" formMode="line" align="center">
         <tr>
            <th colspan="2">向群组发送消息</th>
         </tr>
        
         <tr>
          <td class="ali03" width="120">用户类型：</td>
          <td class="ali01"><java2code:option name="usertype" codetype="usertype" valuename="usertype" selectall="3"/><span class="star"></span></td>
         </tr>
         <tr>
          <td class="ali03" width="120">学段：</td>
          <td class="ali01"><java2code:option name="xueduan" codetype="xueduan" valuename="xueduan" selectall="3"/><span class="star"></span></td>
         </tr>
         <tr>
          <td class="ali03">消息标题：</td>
          <td class="ali01"><input type="text" id="title" style="width: 400px;" name="title"/><span class="star">*</span></td>
         </tr>
         <tr>
          <td class="ali03">消息内容：</td>
          <td class="ali01"><textarea name="content" id="mc" style="width:400px;height:100px;"></textarea><span class="star">*</span></td>
         </tr>
         <tr>
          <td class="ali03">是否短信通知：</td>
          <td class="ali01"><input type="radio" name="issms" value="1" onclick="showContent()" id="issms"/>是<input type="radio" name="issms" onclick="hideContent()" value="0" checked/>否<span class="star">*</span></td>
         </tr >
         <tr id="smscontent" style="display:none;">
	          <td class="ali03">短信内容：</td>
	          <td class="ali01"><textarea name="smscontent" id="sc" style="width:400px;height:100px;"></textarea><span class="star">*</span></td>
         </tr>
         <tr>
	          <td colspan="2">
		          <button type="button" onclick="saveRecord()" class="margin_right5" style="margin-left:-80px;"><span class="icon_save">发送</span></button>           
	          </td>
         </tr>
       </table>
      </html:form>
     </div>
    </div>
  </body>
</html>
