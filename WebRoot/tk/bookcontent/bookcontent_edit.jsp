<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkBookContent"%>
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
<script type="text/javascript">
var keditor;
var keditor2;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="tkBookContent.beforeclass"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		keditor2= K.create('textarea[name="tkBookContent.teachingcase"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
} 
function saveRecord(){
    if(validateForm('form[name=tkBookContentActionForm]')){
       var objectForm=document.tkBookContentActionForm;
       var flag=false;
       var contentno="${model.parentno}"+"${model.contentno}";
       var xcontentno="${model.parentno}"+document.getElementById("contentno").value;
       if(contentno!=xcontentno){
         $.ajax({
         url:"tkBookContentAction.do?method=ishavecontentno&bookid=${model.bookid }&contentno="+xcontentno+"&parentno=${model.parentno}&bookid=${model.bookid}",
         data:{},
         cache:false,
         async:false,
         dateType:'json',
         success:function(data){
            if(data!="ok"){
               top.Dialog.alert(data);  
               flag=true;
             }
           }
         });
       }
       if(flag)return false;
		//同步数据后可以直接取得textarea的value
	  <c:if test="${model.parentno!='0000'}">	
		keditor.sync();
		keditor2.sync();
	  </c:if>
	var upload=true;
	<c:if test="${model.parentno!='0000'}">	
	  upload= testingUpload();
	 </c:if>
	if(upload==false)return false;
	<c:if test="${model.parentno!='0000'}">	
	    if(document.getElementById("thefile").value!=""){    
	    document.getElementById('fade').style.display ='block';
	    }
	   </c:if>  
	    document.getElementById("btnsave").disabled = true;
	  	objectForm.action="tkBookContentAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
    }
}
    
 function historyback(){
  var objectForm=document.tkBookContentActionForm;
  objectForm.action="tkBookContentAction.do?method=list"; 
  objectForm.submit();
 }
    
 function testingUpload(){
    var mp3=document.getElementById("thefile");
     if(mp3.value.lastIndexOf(".")!=-1){
        var ext=mp3.value.substr((mp3.value.lastIndexOf(".")+1)).toUpperCase();
        if(ext!='MP3'){
          top.Dialog.alert("只能上传MP3格式的文件");      
          return false;
        }else{
         //检测上传文件的大小        
    var isIE = /msie/i.test(navigator.userAgent) && !window.opera;  
    var fileSize = 0;           
    if (isIE && !mp3.files){       
        var filePath = mp3.value;       
        var fileSystem = new ActiveXObject("Scripting.FileSystemObject");          
        var file = fileSystem.GetFile (filePath);       
        fileSize = file.Size;      
    } else {      
        fileSize = mp3.files[0].size;       
    }     


    var size = fileSize / 1024*1024;   


    if(size>(1024*1024*100)){    
        top.Dialog.alert("文件大小不能超过100Mb");   
        return false;
    }    
        
        
        }
    } 
    
 }
  
</script>
  </head>
  <%@ include file="/libs/jsp/tip.jsp"%>
 <%TkBookContent model = (TkBookContent)request.getAttribute("model"); %> 
  <body>
    <div>
     <div class="box1" position=center>
      <html:form action="/tkBookContentAction.do" method="post" enctype="multipart/form-data" >
       <table class="tableStyle" formMode="line" align="center">
         <tr>
            <th colspan="3">${title }</th>
         </tr>
         <tr>
          <td class="ali03" width="80">章节名称：</td>
          <td class="ali01"><input type="text" class="validate[required]" style="width: 200px;" name="tkBookContent.title" value="${model.title }" /><span class="star">*</span></td>
         </tr>
         <tr>
           <td class="ali03" >章节编号：</td>
           <td class="ali01">${model.parentno }<input id="contentno" type="text" style="width: 100px;" name="tkBookContent.contentno" class="validate[required,custom[onlyNumber],length[4,4]]" value="${model.contentno }" /><span class="star">*</span></td>
         </tr>
        <c:if test="${model.parentno!='0000'}">
         <tr>
          <td class="ali03" width="80">对应PDF书本页码：</td>
          <td class="ali01"><input type="text" class="validate[required]" style="width: 100px;" name="tkBookContent.pagenum" value="${model.pagenum }" /><span class="star">*</span>（格式：作业1是第1页到第3页，则需要填入1-2-3）</td>
         </tr>
         <tr>
         <td class="ali03">课前预习：</td>
         <td><textarea name="tkBookContent.beforeclass" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getBeforeclass())%></textarea></td>
         </tr>
         <tr>
          <td class="ali03">教学案：</td>
          <td><textarea name="tkBookContent.teachingcase" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getTeachingcase())%></textarea></td>
         </tr>
         <tr>
         <tr>
           <td class="ali03">上传MP3</td>
           <td><input id="thefile" type="file" name="thefile" keepDefaultStyle="true" /><c:if test="${model.mp3path!=''&&model.mp3path!=null }"><span style="color: red;">该章节内容存在MP3文件</span></c:if> </td>
         </tr>
         </c:if>
          <tr>
          <td colspan="2" class="ali02">
          <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
          <button type="button" onclick="historyback();"><span class="icon_back">返回</span></button>            
          </td>
         </tr>
       </table>
            <!-- 分页与排序 -->
 <input type="hidden" name="mp3path" value="${model.mp3path }"/>
 <input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
 <input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
 <input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
 <input type="hidden" name="tkBookContent.bookcontentid" value="${model.bookcontentid}"/>
 <input type="hidden" name="tkBookContent.parentno" value="${model.parentno }"/>
 <input type="hidden" name="tkBookContent.bookid"  value="${model.bookid }"/> 
 <input type="hidden" name="tkBookContent.beforeclasstwocode" value="${model.beforeclasstwocode}" />
 <input type="hidden" name="tkBookContent.teachingcasetwocode" value="${model.teachingcasetwocode }"/>
 <input type="hidden" name="tkBookContent.mp3path" value="${model.mp3path}" />
 <input type="hidden" name="tkBookContent.mp3pathtwocode" value="${model.mp3pathtwocode }"/>
 <input type="hidden" name="tkBookContent.paperid" value="${model.paperid }" />  
 <input type="hidden" name="parentno" value="${parentno }"/>      
 <input type="hidden" name="bookid" value="${bookid}"/>
 <input type="hidden" name="title" value="${title }"/>
 <input type="hidden" name="contentno" value="${contentno }"/>
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
