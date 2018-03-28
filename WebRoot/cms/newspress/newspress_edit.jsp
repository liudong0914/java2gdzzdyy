<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.wkmk.cms.bo.*" %>
<%@ page import="java.util.*" %>
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

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->
<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/cmsNewsInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增资讯</logic:equal><logic:equal value="updateSave" name="act">修改资讯</logic:equal></th>
		</tr>
		<tr>
			<td class="ali03">标题：</td>
			<td class="ali01"><input type="text" style="width:350px;" class="validate[required]" name="cmsNewsInfo.title" value="<bean:write property="title" name="model"/>"/><span class="star">*</span></td>
		</tr>	
		
		<tr>
			<td class="ali03">作者：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="cmsNewsInfo.author" value="进步学堂"/></td>
		</tr>
		<tr>
		    <td class="ali03">所在栏目</td>
		    <td>
		        <select name="columnid" id="columnid">
		            <%List lst = (List)request.getAttribute("columnlist");
		              CmsNewsInfo model = (CmsNewsInfo)request.getAttribute("model");
		              if(lst != null && lst.size() > 0){
		                  CmsNewsColumn cnc = null;
			              for(int i = 0;i < lst.size();i++){
			                  cnc = (CmsNewsColumn)lst.get(i);
	                         %>
						     <option value="<%=cnc.getId() %>"><%for(int j=0;j<(cnc.getColumnno().length()-8)/4;j++){%> ├<%}%><%=cnc.getColumnname()%></option>				
						 <%} }%>
			</select>
		    </td>
		</tr>
		<tr>
			<td class="ali03">发布时间：</td>
			<td><input type="text" name="cmsNewsInfo.happendate" style="width:150px;" readonly="readonly" value="<bean:write property="happendate"  name="model"/>" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<%-- 
		<tr>
			<td class="ali03">资讯链接：</td>
			<td class="ali01"><input type="text" style="width:250px;" name="cmsNewsInfo.linkurl" value="<bean:write property="linkurl" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">关键字：</td>
			<td class="ali01"><input type="text" style="width:250px;" name="cmsNewsInfo.keywords" value="<bean:write property="keywords" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">缩略图：</td>
            <td class="ali01">
              <img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改照片" width="120" height="90" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
			  <input type="hidden" id="uploadImg" name="cmsNewsInfo.sketch" value='<bean:write property="sketch" name="model"/>'/>
			</td>
		</tr>S
		--%>
        <tr>
			<td class="ali03">资讯内容：</td>
			<td class="ali01" colspan="2">
				<textarea name="cmsNewsInfo.htmlcontent" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getHtmlcontent())%></textarea>
			</td>
		</tr>
			</tr>
			<td colspan="2">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
			</td>
		</tr>
	</table>

<input type="hidden" name="cmsNewsInfo.newsid" value="<bean:write property="newsid"  name="model"/>"/>
<input type="hidden" name="columnid" value="<bean:write name="columnid"/>" />
<input type="hidden" name="cmsNewsInfo.linkurl" value="<bean:write property="linkurl"  name="model"/>"/>
<input type="hidden" name="cmsNewsInfo.keywords" value="<bean:write property="keywords"  name="model"/>"/>
<input type="hidden" name="cmsNewsInfo.sketch" value="<bean:write property="sketch"  name="model"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=cmsNewsInfoActionForm]')){
		var objectForm = document.cmsNewsInfoActionForm;
        //同步数据后可以直接取得textarea的value
		keditor.sync();
	  	objectForm.action="cmsNewsPressAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="cmsNewsInfo.htmlcontent"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}

function backRecord(){
  	document.cmsNewsInfoActionForm.action="/cmsMyNewsInfoAction.do?method=list";
  	document.cmsNewsInfoActionForm.submit();
}
function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=news&pathtype=ID&sketch=true';
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
</script>
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
</body>
</html>