<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@ page import="com.wkmk.tk.bo.TkQuestionsInfo" %>
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

<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>

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
<script type="text/javascript" src="/libs/DatePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%TkQuestionsInfo model = (TkQuestionsInfo)request.getAttribute("model"); %>
<script type="text/javascript">
$(function(){
 addRow();
});
 var nkeditor1;
 var nkeditor2;
 var nkeditor3;
 var nkeditor4;
 var nkeditor5;
 var nkeditor6;
 var nkeditor7;
 var nkeditor8;
 var nkeditor9;
 var nkeditor10;
 
 function addRow(){
 document.getElementById("tkQuestionsInfo.option1").style.display="";
 document.getElementById("tkQuestionsInfo.option2").style.display="";
 document.getElementById("tkQuestionsInfo.option3").style.display="";
 document.getElementById("tkQuestionsInfo.option4").style.display="";
 document.getElementById("tkQuestionsInfo.option5").style.display="";
 document.getElementById("tkQuestionsInfo.option6").style.display="";
 document.getElementById("tkQuestionsInfo.option7").style.display="";
 document.getElementById("tkQuestionsInfo.option8").style.display="";
 document.getElementById("tkQuestionsInfo.option9").style.display="";
 document.getElementById("tkQuestionsInfo.option10").style.display="";
  var num=document.getElementById("num").value;
  for(var i=10;i>num;i--){
   document.getElementById("tkQuestionsInfo.option"+i).style.display="none";
   document.getElementById("tkQuestionsInfo.option"+i).value="";
  }
  var radios=document.getElementsByName("tkQuestionsInfo.rightans");
  var rights="${model.rightans}";
  for(var i=0;i<radios.length;i++){
    if(radios[i].value==rights){
      radios[i].checked=true;
      changeValue(radios[i]);
    }
   }     
 }
var keditor;
var keditor2;

function initComplete(){
   
    KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="tkQuestionsInfo.titlecontent"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		    afterBlur: function(){this.sync();}
		});
		keditor2= K.create('textarea[name="tkQuestionsInfo.descript"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		
      nkeditor1=KindEditor.create('textarea[name="tkQuestionsInfo.option1"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
        });
      nkeditor2=KindEditor.create('textarea[name="tkQuestionsInfo.option2"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });  
     nkeditor3=KindEditor.create('textarea[name="tkQuestionsInfo.option3"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });  
     nkeditor4=KindEditor.create('textarea[name="tkQuestionsInfo.option4"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });  
     nkeditor5=KindEditor.create('textarea[name="tkQuestionsInfo.option5"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });  
     nkeditor6=KindEditor.create('textarea[name="tkQuestionsInfo.option6"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });  
     nkeditor7=KindEditor.create('textarea[name="tkQuestionsInfo.option7"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });  
     nkeditor8=KindEditor.create('textarea[name="tkQuestionsInfo.option8"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });  
     nkeditor9=KindEditor.create('textarea[name="tkQuestionsInfo.option9"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });  
     nkeditor10=KindEditor.create('textarea[name="tkQuestionsInfo.option10"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });    
		prettyPrint();
	});
} 
function saveRecord(){
  if(validateForm('form[name=tkQuestionsInfoActionForm]')){
  var radios=document.getElementsByName("tkQuestionsInfo.rightans");
   var content=document.getElementById("content");
   var boolean=false;
   for(var i=0;i<radios.length;i++){
     if(radios[i].checked==true){
       boolean=true;
     }
   }
   if(boolean==false){
      top.Dialog.alert("请选择一个正确答案！");
      return false;
   }
   var objectForm=document.tkQuestionsInfoActionForm;
   //同步数据
   keditor.sync();
   <c:if test="${model.parentid=='0' }">
   keditor2.sync();
   </c:if>
   if(nkeditor1!=null){
   nkeditor1.sync();
   }
   if(nkeditor2!=null){
   nkeditor2.sync();
   }
   if(nkeditor3!=null){
   nkeditor3.sync();
   }
   if(nkeditor4!=null){
    nkeditor4.sync();
   }
   if(nkeditor5!=null){
    nkeditor5.sync();
   }
   if(nkeditor6!=null){
   nkeditor6.sync();
   }
   if(nkeditor7!=null){
   nkeditor7.sync();
   }
   if(nkeditor8!=null){
   nkeditor8.sync();
   }
   if(nkeditor9!=null){
   nkeditor9.sync();
   }
   if(nkeditor10!=null){
   nkeditor10.sync();
   }
  if(content.value==""){
    top.Dialog.alert("试题内容不能为空！");
     return false;
   }
   document.getElementById("btnsave").disabled = true;
   objectForm.action="tkQuestionsInfoAction.do?method=${act}";
   objectForm.submit();
  }
}
 
 function selectKnopoint(){
	var subjectid =${subjectid};
	var gradeid = document.getElementById("grade").value;
	var knopointid = document.getElementById("knopointid").value;
	var diag = new top.Dialog();
	diag.Title = "选择知识点";
	diag.URL = '/eduSelectAction.do?method=selectKnopoint&subjectid=' + subjectid + '&gradeid=' + gradeid + '&knopointid=' + knopointid;
	diag.Width = 400;
	diag.Height = 500;
	diag.CancelEvent = function(){
		if(diag.innerFrame.contentWindow.document.getElementById('selectobjid')){
			var id = diag.innerFrame.contentWindow.document.getElementById('selectobjid').value;
			var name = diag.innerFrame.contentWindow.document.getElementById('selectobjname').value;
			g('knopointid').value = id;
			g('knopointidupdate').value = "1";
			if(name == ""){
				g('knopointname').value = "点击选择知识点";
			}else{
				g('knopointname').value = name;
			}
		}
		diag.close();
	};
	diag.show();
}

 function changeValue(temp){
  document.getElementById("rightans").value=temp.value;	
 }
 
 function g(objid){
	return document.getElementById(objid);
}
function changeGrade(){
g('knopointname').value="";
g('knopointid').value="";
g('knopointidupdate').value = "1";
}
 
</script>

  </head>
  <body>
   <div>
      <div class="box1" position="center">
      <html:form action="/tkQuestionsInfoAction.do" method="post">
       <table  id="testTab" class="tableStyle" formMode="line" align="center">
       <tr>
          <td class="ali03">标题：</td>
          <td class="ali01"><input type="text" id="typeno" class="validate[required]" style="width: 670px;" name="tkQuestionsInfo.title" value="${model.title }"/></td>
       </tr>
       <tr>
         <td class="ali03">试题内容：</td>
         <td class="ali01"><textarea id="content" name="tkQuestionsInfo.titlecontent"style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getTitlecontent())%></textarea></td>
       </tr>
       <tr id="begin">
         <td class="ali03">客观选项题个数：</td>
         <td class="ali01">
          <select id="num" name="tkQuestionsInfo.optionnum" onchange="addRow();" >
          <c:forEach begin="2" end="10" step="1" var="x" >
          <option value="${x}"${model.optionnum eq x?"selected":"" }>${x}</option>
          </c:forEach>
          </select>个
         </td>
       </tr>
       <tr id="tkQuestionsInfo.option1" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="A" onclick="changeValue(this);" />选项A：</td>
          <td class="ali01"><textarea id="option1" name="tkQuestionsInfo.option1" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption1())%></textarea></td>
       </tr>
        <tr id="tkQuestionsInfo.option2" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="B" onclick="changeValue(this);" />选项B：</td>
          <td class="ali01"><textarea id="option2" name="tkQuestionsInfo.option2" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption2())%></textarea></td>
       </tr >
        <tr id="tkQuestionsInfo.option3" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="C" onclick="changeValue(this);" />选项C：</td>
          <td class="ali01"><textarea id="option3" name="tkQuestionsInfo.option3" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption3())%></textarea></td>
       </tr>
        <tr id="tkQuestionsInfo.option4" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="D" onclick="changeValue(this);" />选项D：</td>
          <td class="ali01"><textarea id="option4" name="tkQuestionsInfo.option4" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption4())%></textarea></td>
       </tr>
        <tr id="tkQuestionsInfo.option5" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="E" onclick="changeValue(this);" />选项E：</td>
          <td class="ali01"><textarea id="option5" name="tkQuestionsInfo.option5" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption5())%></textarea></td>
       </tr>
        <tr id="tkQuestionsInfo.option6" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="F" onclick="changeValue(this);" />选项F：</td>
          <td class="ali01"><textarea id="option6" name="tkQuestionsInfo.option6" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption6())%></textarea></td>
       </tr>
        <tr id="tkQuestionsInfo.option7" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="G" onclick="changeValue(this);" />选项G：</td>
          <td class="ali01"><textarea id="option7" name="tkQuestionsInfo.option7" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption7())%></textarea></td>
       </tr>
        <tr id="tkQuestionsInfo.option8" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="H" onclick="changeValue(this);" />选项H：</td>
          <td class="ali01"><textarea id="option8" name="tkQuestionsInfo.option8" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption8())%></textarea></td>
       </tr>
        <tr id="tkQuestionsInfo.option9" >
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="I" onclick="changeValue(this);" />选项I：</td>
          <td class="ali01"><textarea id="option9" name="tkQuestionsInfo.option9" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption9())%></textarea></td>
       </tr>
         <tr id="tkQuestionsInfo.option10" 	>
          <td class="ali03"><input type="radio" name="tkQuestionsInfo.rightans" value="j" onclick="changeValue(this);" />选项J：</td>
          <td class="ali01"><textarea id="option10" name="tkQuestionsInfo.option10" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getOption10())%></textarea></td>
       </tr>
       <tr id="over">
       <td class="ali03">正确答案：</td>
       <td class="ali01"><input type="text" id="rightans" readonly="readonly" value="${model.rightans }"/></td>
       </tr>
       <tr >
       <td class="ali03">难易程度：</td>
       <td class="ali01">
       <java2code:option name="tkQuestionsInfo.difficult" codetype="difficult" valuename="model" property="difficult"></java2code:option>
       </td>
       </tr>
       <tr>
        <td class="ali03">参考分值：</td>
        <td class="ali01"><input type="text" name="tkQuestionsInfo.score" value="${model.score }" class="validate[required,custom[onlyNumberWide]]" /> </td>
       </tr>
       <tr>
       <td class="ali03">状态：</td>
       <td class="ali01">
       <java2code:option name="tkQuestionsInfo.status" codetype="status4" valuename="model" property="status"></java2code:option>
       </td>
       </tr>
       <tr>
       <td class="ali03">年级：</td>
       <td class="ali01">
       <select id="grade" name="tkQuestionsInfo.gradeid"  boxHeight="130"  onchange="changeGrade();">
        <c:forEach items="${gradeLists }" var="g" >
        <option value="${g.gradeid }" ${model.gradeid eq g.gradeid?"selected":"" }>${g.gradename }</option>
        </c:forEach>
       </select>
       </td>
       </tr>
       <tr>
       <td class="ali04">知识点：</td>
       <td>
       <input type="text" style="width:180px;background-color:#f7f8f9;color:#888;" readonly="readonly" name="knopointname" id="knopointname" value="${knopointnames }" onclick="selectKnopoint()"/>
       <input type="hidden" name="knopointid" id="knopointid" value="${knopointids }"/>
       <input type="hidden" name="knopointidupdate" id="knopointidupdate" value="0"/>
       </td>
       </tr>
       <tr>
       <td class="ali03">地区：</td>
       <td class="ali01">
       <input type="text" name="tkQuestionsInfo.area" value="${model.area }" />
       </td>
       </tr>
       <tr>
       <td class="ali03">标签：</td>
       <td><input type="text" name="tkQuestionsInfo.tag" value="${model.tag }"  style="width: 670px;"/></td>
       </tr>
       <c:if test="${model.parentid=='0' }">
       <tr>
       <td class="ali03">答案解析：</td>
       <td><textarea name="tkQuestionsInfo.descript"style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getDescript())%></textarea></td>
       </tr>
       </c:if>
       <tr>
       <tr>
       <td class="ali03">年份：</td>
       <td><input type="text" name="tkQuestionsInfo.theyear" value="${model.theyear }" readonly="readonly"  onclick="WdatePicker({dateFmt:'yyyy', alwaysUseStartDate:true})" />
       </tr>
       <tr>
       <td colspan="2">
       <button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
       <button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            
       </td>
       </tr>
       </table>
       <input type="hidden" name="tkQuestionsInfo.similartwocodepath" value="${model.similartwocodepath }"/>
       <input type="hidden" name="tkQuestionsInfo.filmtwocodepath" value="${model.filmtwocodepath }"/>
       <input type="hidden" name="questiontypeid" value="${questiontypeid }"/>
       <input type="hidden" name="subjectid" value="${subjectid }"/>
       <input type="hidden" name="tkQuestionsInfo.isrightans" value="1"/>
       <input type="hidden" name="id" value="${model.parentid }"/>
       <input type="hidden" name="tkQuestionsInfo.parentid" value="${model.parentid }"/>
       <input type="hidden" name="tkQuestionsInfo.subjectid" value="${model.subjectid }"/>
       <input type="hidden" name="questiontypeid" value="${questiontypeid }"/>
       <input type="hidden" name="tkQuestionsInfo.unitid" value="${model.unitid }"/>
       <input type="hidden" name="tkQuestionsInfo.questionid" value="${model.questionid }"/>
       <input type="hidden" name="tkQuestionsInfo.questionno" value="${model.questionno }" />
       <input type="hidden" name="tkQuestionsInfo.questiontype" value="${model.questiontype }"/> 
       <input type="hidden" name="tkQuestionsInfo.doctype" value="${model.doctype }"/>
       <input type="hidden" name="tkQuestionsInfo.cretatdate" value="${model.cretatdate}"/>
       <input type="hidden" name="tkQuestionsInfo.descriptpath" value="${model.descriptpath }" />
       <input type="hidden" name="tkQuestionsInfo.authorname" value="${model.authorname }"/>
       <input type="hidden" name="tkQuestionsInfo.authorid"   value="${model.authorid }"/>
       <input type="hidden" name="tkQuestionsInfo.filepath" value="${model.filepath }" />
       <input type="hidden" name="questionno" value="${questionno }"/>
       <input type="hidden" name="title" value="${title }"/>
       <input type="hidden" name="difficult" value="${difficult }"/>
            <!-- 分页与排序 -->
   <input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
   <input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
   <input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
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
