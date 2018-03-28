<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.tk.bo.TkBookInfo"%>
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
<script type="text/javascript">
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="tkBookInfo.htmlcontent"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}

</script>
<!-- 编辑器end -->
  </head>
 <%TkBookInfo model = (TkBookInfo)request.getAttribute("model"); %> 
  <body>
    <div>
     <div class="box1" position=center>
      <html:form action="/tkBookInfoAction.do" method="post">
       <table class="tableStyle" formMode="line" align="center">
         <tr>
            <th colspan="3">${title }</th>
         </tr>
       <tr>
          <td class="ali03" width="80">课本名称：</td>
          <td class="ali01">${model.title }</td>
         </tr>
         <tr>
           <td class="ali03" >课本编号：</td>
           <td class="ali01">${model.bookno }</td>
         </tr>
         <tr>
         <td class="ali03">缩略图：</td>
          <td class="ali01" >
           <img src="/upload/${model.sketch }"  width="90" height="120" border="1" id="uploadImgShow" />
           </td>
         </tr>
         <tr>
            <td class="ali03" width="80">所属年级：</td>
            <td class="ali01">
            ${gradeName }
            </td>
         </tr>
         <tr>
          <td class="ali03" width="80">所属版本：</td>
          <td class="ali01">
          ${partName }
          </td>
         </tr>
         <tr>
           <td class="ali03" >所属年份： </td>
           <td class="ali01">${model.theyear }</td>
         </tr>
         <tr>
           <td class="ali03" >主编： </td>
           <td class="ali01">${model.editor }</td>
         </tr>
         <tr>
           <td class="ali03">副主编：</td>
           <td class="ali01">${model.deputyeditor }</td>
         </tr>
         <tr>
           <td class="ali03">教材版本：</td>
           <td class="ali01">${versionName }</td>
         </tr>
         <tr>
           <td class="ali03">状态：</td>
           <td class="ali01">
             ${status }
           </td>
         </tr>
         <tr>
           <td class="ali03">配套微课：</td>
           <td class="ali01">
             <java2code:value codetype="vodstate" property="vodstate"></java2code:value>
           </td>
         </tr>
         <tr>
         <td class="ali03">课本简介：</td>
         <td class="ali01" colspan="2">${model.descript }</td>
         </tr>
         <tr>
          <td class="ali03">课本详细介绍：</td>
          <td class="ali01">${model.htmlcontent}</td>
         </tr>
         <tr>
         <td class="ali03">开始时间：</td>
         <td class="ali01">${model.createdate }</td>
         </tr>
         <tr>
         <td class="ali03">修改时间：</td>
         <td class="ali01">${model.updatetime }</td>
         </tr>
         <tr>
          <td class="ali03">创建者：</td>
          <td>${userName }</td>
         </tr>
           <tr>
          <td colspan="2">
          <button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            
          </td>
         </tr>
       </table>
            <!-- 分页与排序 -->

      </html:form>
     </div>
    </div>
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
