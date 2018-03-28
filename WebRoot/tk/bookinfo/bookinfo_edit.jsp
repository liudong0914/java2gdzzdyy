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

function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=tk/book&pathtype=ID&sketch=true&swidth=221&sheight=316';
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
   if(validateForm('form[name=tkBookInfoActionForm]')){
		var objectForm = document.tkBookInfoActionForm;
		var flag=false;
		var bookno="<bean:write name='model' property='bookno'/>";
		var xbookno=document.getElementById("bookno").value;
		if(bookno!=xbookno){
		    $.ajax({
	    url:"tkBookInfoAction.do?method=ishavebookno&bookno="+xbookno+"&subjectid=${model.subjectid}",
	    data:{},
	    cache:false,
	    async:false,  
	    dateType:'json',
	     success:function(data) {  
        if(data!="ok" ){  
             top.Dialog.alert(data);  
             flag=true;
         }
         }
         });
		}
		if(flag)return false;
		//同步数据后可以直接取得textarea的value
		keditor.sync();
	  	objectForm.action="tkBookInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
  }
  
  
</script>
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
          <td class="ali01"><input type="text" class="validate[required]" style="width: 200px;" name="tkBookInfo.title" value="${model.title }" /><span class="star">*</span></td>
         </tr>
         <tr>
           <td class="ali03" >课本编号：</td>
           <td class="ali01"><input id="bookno" type="text" style="width: 100px;" name="tkBookInfo.bookno" class="validate[required,custom[onlyNumber],length[1,6]]" value="${model.bookno }" /><span class="star">*</span></td>
         </tr>
         <tr>
         <td class="ali03">缩略图：</td>
          <td class="ali01" >
           <img src="/upload/${model.sketch }" title="点击修改照片" width="90" height="120" border="1" id="uploadImgShow" onclick="uploadPhoto()"/>
           <input type="hidden" id="uploadImg" name="tkBookInfo.sketch" value="${model.sketch }"/>
           </td>
         </tr>
         <tr>
            <td class="ali03" width="80">所属年级：</td>
            <td class="ali01">
            <select name="tkBookInfo.gradeid" boxHeight="130">
            <c:forEach items="${gradeinfoList }" var="g" >
             <option value="${g.gradeid}" ${model.gradeid eq g.gradeid ?'selected':''}>${g.gradename }</option>
            </c:forEach>
            </select>
            </td>
         </tr>
         <tr>
          <td class="ali03" width="80">所属版本：</td>
          <td class="ali01">
          <java2code:option name="tkBookInfo.part" ckname="1" codetype="usingtype" valuename="model" property="part"></java2code:option>
          </td>
         </tr>
         <tr>
          <td class="ali03" width="80">所属年份：</td>
          <td class="ali01"><input type="text" name="tkBookInfo.theyear" readonly="readonly" style="width:60px;" value="<bean:write property="theyear"  name="model"/>" class="date validate[required]" dateFmt="yyyy"/></td>
         </tr>
         <tr>
           <td class="ali03" >主编： </td>
           <td class="ali01"><input type="text" class="validate[required]" style="width: 200px;" name="tkBookInfo.editor" value="${model.editor }"/><span class="star">*</span></td>
         </tr>
         <tr>
           <td class="ali03">副主编：</td>
           <td class="ali01"><input type="text" class="validate[required]" style="width: 200px;" name="tkBookInfo.deputyeditor" value="${model.deputyeditor }"/><span class="star">*</span></td>
         </tr>
         <tr>
         <td class="ali03">教材版本：</td>
         <td class="ali01">
         <java2code:option name="tkBookInfo.version" codetype="version" valuename="model" property="version"></java2code:option>
         </td>
         </tr>
         <tr>
           <td class="ali03">状态：</td>
           <td class="ali01">
           <java2code:option name="tkBookInfo.status" codetype="status4" valuename="model" property="status"></java2code:option>
           </td>
         </tr>
         <tr>
           <td class="ali03">配套微课：</td>
           <td class="ali01">
           <java2code:option name="tkBookInfo.vodstate" codetype="vodstate" valuename="model" property="vodstate"></java2code:option>
           </td>
         </tr>
         <tr>
         <td class="ali03">课本简介：</td>
         <td class="ali01" colspan="2"><textarea name="tkBookInfo.descript" style="width:400px;">${model.descript }</textarea></td>
         </tr>
         <tr>
          <td class="ali03">课本详细介绍：</td>
          <td><textarea name="tkBookInfo.htmlcontent" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getHtmlcontent())%></textarea></td>
         </tr>
         <tr>
          <td colspan="2">
          <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
          <button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            
          </td>
         </tr>
       </table>
            <!-- 分页与排序 -->
 <input type="hidden" name="subjectid" value="${subjectid }"/>
 <input type="hidden" name="title" value="${title }"/>
 <input type="hidden" name="bookno" value="${bookno }"/>
 <input type="hidden" name="editor" value="${editor }"/>
 <input type="hidden" name="deputyeditor" value="${deputyeditor }"/>
 <input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
 <input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
 <input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
 <input type="hidden" name="tkBookInfo.bookid" value="${model.bookid }"/>
 <input type="hidden" name="tkBookInfo.subjectid" value="${model.subjectid }"/>
 <input type="hidden" name="tkBookInfo.unitid" value="${model.unitid }"/>
 <input type="hidden" name="tkBookInfo.userid" value="${model.userid }"/> 
 <input type="hidden" name="tkBookInfo.updatetime" value="${model.updatetime }"/>
 <input type="hidden" name="tkBookInfo.createdate" value="${model.createdate }"/>
 <input type="hidden" name="tkBookInfo.userid" value="${model.userid }"/>
 <input type="hidden" name="tkBookInfo.unitid" value="${model.unitid}"/>
 <input type="hidden" name="tkBookInfo.twocodepath" value="${model.twocodepath }"/>
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
