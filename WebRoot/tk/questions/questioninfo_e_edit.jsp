<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<%@ page import="com.wkmk.tk.bo.TkQuestionsInfo"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="../../" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<!--框架必需end-->

<!-- 表单验证start -->
<script src="../../libs/js/form/validationRule.js"
	type="text/javascript"></script>
<script src="../../libs/js/form/validation.js" type="text/javascript"></script>
<script src="../../libs/js/sk/validateForm.js" type="text/javascript"></script>
<!-- 表单验证end -->

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
</head>
<script type="text/javascript" src="/libs/DatePicker/WdatePicker.js"></script>
<%
	TkQuestionsInfo model = (TkQuestionsInfo) request
			.getAttribute("model");
%>
<script type="text/javascript">
	var keditor;
	var keditor2;
	$(function() {
		var isrightsn = "${model.isrightans}";
		var radios = document.getElementsByName("tkQuestionsInfo.isrightans");
		for ( var i = 0; i < radios.length; i++) {
			if (radios[i].value == isrightsn) {
				radios[i].checked = true;
			}
		}
		addRow();
	     if(isrightsn=='1'){
		 var num=document.getElementById("num").value;
		  var textoption="${model.rightans}";
		  var shuzu =textoption.split("【】");
		  for(var i=1;i<=num;i++){
		    var texttime=shuzu[i-1];
		    var sname=texttime.split("【或】");
		    for(var j=1;j<=sname.length;j++){
		        var name=String.fromCharCode((64+i))+j;
		        document.getElementById(name).style.display="inline";
                document.getElementById(name).value=sname[j-1];
                if(j>1){
                 var xname=String.fromCharCode((64+i))+String.fromCharCode((64+i))+j;
                 document.getElementById(xname).style.display="inline";
                }
		    }
		   
		  }
		}
	});

	function initComplete() {
		KindEditor.ready(function(K) {
			keditor = K.create('textarea[name="tkQuestionsInfo.titlecontent"]',
					{
						uploadJson : '/kindeditor/config/upload.jsp',
						allowImageRemote : false,
						afterBlur : function() {
							this.sync();
						}
					});
			keditor2 = K.create('textarea[name="tkQuestionsInfo.descript"]', {
				uploadJson : '/kindeditor/config/upload.jsp',
				allowImageRemote : false,
			});
			prettyPrint();
		});
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

	function g(objid) {
		return document.getElementById(objid);
	}
	function changeGrade() {
		g('knopointname').value = "";
		g('knopointid').value = "";
		g('knopointidupdate').value = "1";
	}

	function saveRecord() {
		if (validateForm('form[name=tkQuestionsInfoActionForm]')) {
			var content = document.getElementById("content");
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
			if (content.value == "") {
				top.Dialog.alert("试题内容不能为空！");
				return false;
			}
			var radios = document.getElementsByName("tkQuestionsInfo.isrightans");
            if(radios[1].checked ==true){
			var num=document.getElementById("num").value;
			for(var i=1;i<=num;i++){
			  if(document.getElementById(i).value==""){
			     top.Dialog.alert("正确答案不能为空!");
			     return false;
			   }
		   	 }
			}
			var objectForm = document.tkQuestionsInfoActionForm;
			document.getElementById("btnsave").disabled = true;
			objectForm.action = "tkQuestionsInfoAction.do?method=${act}";
			objectForm.submit();
		}
	}
	
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
  var num=document.getElementById("num").value;
  var tb=document.getElementById("testTab");
  var begin=document.getElementById("begin").rowIndex+1;
  var over=document.getElementById("over").rowIndex;
  for(var i=begin;i<over;i++){
      tb.deleteRow(3);
   }
   var radios = document.getElementsByName("tkQuestionsInfo.isrightans");
   if(radios[0].checked ==true){
    document.getElementById("begin").style.visibility="visible";
   addInput();
   }else{
    document.getElementById("begin").style.visibility="hidden";
   var nrow=3;
   var newTr=tb.insertRow(nrow);
   newTr.id="tr";
   newTr.name="row";
   var newTd0=newTr.insertCell(0);
   var newTd1=newTr.insertCell(1);
   newTd0.className="ali03";
   newTd0.innerHTML="正确答案：";
   newTd1.className="ali01";
   newTd1.innerHTML="<textarea id=\"1\" name=\"tkQuestionsInfo.option1\" style=\"width:670px;height:230px;visibility:hidden;\"><%=htmlspecialchars(model.getOption1())%></textarea>";
   nkeditor1=KindEditor.create('textarea[name="tkQuestionsInfo.option1"]',{uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
     });
   }  
   
  var begin2=document.getElementById("begin2").rowIndex+1;
  var over2=document.getElementById("over2").rowIndex;
  var index=begin2;
  for(var i=begin2;i<over2;i++){
      tb.deleteRow(index);
   }
   for(var i=1;i<=num;i++){
    var nrow=begin2-1+i;
    var newTr=tb.insertRow(nrow);
     newTr.id="tr2";
     newTr.name="row2";
    var newTd0=newTr.insertCell(0);
    var newTd1=newTr.insertCell(1);
    newTd0.className="ali03";
    newTd0.innerHTML="参考分值"+String.fromCharCode((64+i))+"：";
    newTd1.className="ali01";
    newTd1.innerHTML="<input name=\"cankao"+i+"\" id=\"cankao"+i+"\" type=\"text\" class=\"validate[required,custom[onlyNumberWide]]\" />";
   }
    var score="${model.score}";
    var scores=score.split("【】");
    for(var k=1;k<=scores.length;k++){
       document.getElementById("cankao"+k).value=scores[k-1];
    }
 }
 function addInput(){
 if(document.getElementsByName("tkQuestionsInfo.isrightans")[0].checked ==true){
   document.getElementById("begin").style.visibility="visible";
 }
  var num=document.getElementById("num").value;
  var tb=document.getElementById("testTab");
  var begin=document.getElementById("begin").rowIndex+1;
  var over=document.getElementById("over").rowIndex;
  for(var i=begin;i<over;i++){
    tb.deleteRow(3);
  }
   for(var i=1;i<=num;i++){
     var nrow=2+i;
     var newTr=tb.insertRow(nrow);
     newTr.id="tr";
     newTr.name="row";
     var newTd0=newTr.insertCell(0);
     var newTd1=newTr.insertCell(1);
     newTd0.className="ali03";
     newTd0.innerHTML="正确答案"+String.fromCharCode((64+i))+"：";
     newTd1.className="ali01";
     if(1==i){
     newTd1.innerHTML="<input id=\"A1\" type=\"text\" class=\"validate[required]\" name=\"A1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('A')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"A\"><input id=\"A2\" type=\"text\" name=\"A2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"AA2\" type=\"button\"  onclick=\"delthis('A','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"A3\" type=\"text\" name=\"A3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"AA3\" type=\"button\"  onclick=\"delthis('A','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(2==i){
      newTd1.innerHTML="<input id=\"B1\" type=\"text\" class=\"validate[required]\" name=\"B1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('B')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"B\"><input id=\"B2\" type=\"text\" name=\"B2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"BB2\" type=\"button\"  onclick=\"delthis('B','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"B3\" type=\"text\" name=\"B3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"BB3\" type=\"button\"  onclick=\"delthis('B','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(3==i){
      newTd1.innerHTML="<input id=\"C1\" type=\"text\" class=\"validate[required]\" name=\"C1\" style=\"width: 120px;\"  />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('C')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"C\"><input id=\"C2\" type=\"text\" name=\"C2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"CC2\" type=\"button\"  onclick=\"delthis('C','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"C3\" type=\"text\" name=\"C3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"CC3\" type=\"button\"  onclick=\"delthis('C','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(4==i){
       newTd1.innerHTML="<input id=\"D1\" type=\"text\" class=\"validate[required]\" name=\"D1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('D')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"D\"><input id=\"D2\" type=\"text\" name=\"D2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"DD2\" type=\"button\"  onclick=\"delthis('D','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"D3\" type=\"text\" name=\"D3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"DD3\" type=\"button\"  onclick=\"delthis('D','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(5==i){
       newTd1.innerHTML="<input id=\"E1\" type=\"text\" class=\"validate[required]\" name=\"E1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('E')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"E\"><input id=\"E2\" type=\"text\" name=\"E2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"EE2\" type=\"button\"  onclick=\"delthis('E','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"E3\" type=\"text\" name=\"E3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"EE3\" type=\"button\"  onclick=\"delthis('E','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(6==i){
       newTd1.innerHTML="<input id=\"F1\" type=\"text\" class=\"validate[required]\" name=\"F1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('F')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"F\"><input id=\"F2\" type=\"text\" name=\"F2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"FF2\" type=\"button\"  onclick=\"delthis('F','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"F3\" type=\"text\" name=\"F3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"FF3\" type=\"button\"  onclick=\"delthis('F','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(7==i){
        newTd1.innerHTML="<input id=\"G1\" type=\"text\" class=\"validate[required]\"  name=\"G1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('G')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"G\"><input id=\"G2\" type=\"text\" name=\"G2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"GG2\" type=\"button\"  onclick=\"delthis('G','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"G3\" type=\"text\" name=\"G3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"GG3\" type=\"button\"  onclick=\"delthis('G','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(8==i){
        newTd1.innerHTML="<input id=\"H1\" type=\"text\" class=\"validate[required]\" name=\"H1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('H')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"H\"><input id=\"H2\" type=\"text\" name=\"H2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"HH2\" type=\"button\"  onclick=\"delthis('H','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"H3\" type=\"text\" name=\"H3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"HH3\" type=\"button\"  onclick=\"delthis('H','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(9==i){
        newTd1.innerHTML="<input id=\"I1\" type=\"text\" class=\"validate[required]\" name=\"I1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('I')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"I\"><input id=\"I2\" type=\"text\" name=\"I2\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"II2\" type=\"button\"  onclick=\"delthis('I','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"I3\" type=\"text\" name=\"I3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"II3\" type=\"button\"  onclick=\"delthis('I','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }else if(10==i){
         newTd1.innerHTML="<input id=\"J1\" type=\"text\" class=\"validate[required]\" name=\"J1\" style=\"width: 120px;\" />&nbsp;&nbsp;<input type=\"button\"  onclick=\"addrights('J')\" value=\"添加\" />&nbsp;&nbsp;&nbsp;&nbsp;<span id=\"J\"><input id=\"J2\" type=\"text\" name=\"J2\" style=\"display: none;\" />&nbsp;&nbsp;<input id=\"JJ2\" type=\"button\"  onclick=\"delthis('J','2')\" value=\"删除\" style=\"display: none;\" />&nbsp;&nbsp;&nbsp;&nbsp;<input id=\"J3\" type=\"text\" name=\"J3\" style=\"display: none;width: 120px;\" />&nbsp;&nbsp;<input id=\"JJ3\" type=\"button\"  onclick=\"delthis('J','3')\" value=\"删除\" style=\"display: none;\" /></span>";
     }
   }
 }
 
 function addrights(temp){
  var name=temp+"2";
  var snme=temp+temp+"2";
  if(document.getElementById(name).style.display=="none"){
    document.getElementById(name).className="validate[required]";
    document.getElementById(name).style.display="inline";
    document.getElementById(snme).style.display="inline";
  }else{
     document.getElementById(temp+"3").className="validate[required]";
     document.getElementById(temp+"3").style.display="inline";
     document.getElementById(temp+temp+"3").style.display="inline";
  }
 }
 function delthis(temp1,temp2){
   var name=temp1+temp2;
   var sname=temp1+temp1+temp2;
   document.getElementById(name).className="";
   document.getElementById(name).style.display="none";
   document.getElementById(sname).style.display="none";
 }
 
</script>
<input type="text" style="display: none;"/>
<body>
	<div>
		<div class="box1" position=center>
			<html:form action="/tkQuestionsInfoAction.do" method="post">
				<table id="testTab" class="tableStyle" formMode="line" align="center">
					<tr>
						<td class="ali03">标题：</td>
						<td class="ali01"><input type="text"
							class="validate[required]" style="width: 670px;"
							name="tkQuestionsInfo.title" value="${model.title }" /></td>
					</tr>
					<tr>
						<td class="ali03">试题内容：</td>
						<td class="ali01"><textarea id="content"
								name="tkQuestionsInfo.titlecontent"
								style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getTitlecontent())%></textarea>
						</td>
					</tr>
					<tr id="begin" >
					  <td class="ali03">填空个数：</td>
					  <td class="ali01">
					   <select id="num" name="tkQuestionsInfo.optionnum" onchange="addRow();">
					    <c:forEach begin="1" end="10" step="1" var="x">
					    <option value="${x }"${model.optionnum eq x?"selected":"" }>${x }</option>
					    </c:forEach>
					   </select>
					  </td>
					</tr>
					<tr id="over">
						<td class="ali03">是否有固定标准答案：</td>
						<td><input type="radio" value="1"
							name="tkQuestionsInfo.isrightans" onclick="addInput()"  />是 <input type="radio"
							value="0" name="tkQuestionsInfo.isrightans" onclick="addRow()" />否</td>
					</tr>
					<tr id="begin2">
						<td class="ali03">难易程度</td>
						<td class="ali01"><java2code:option
								name="tkQuestionsInfo.difficult" codetype="difficult"
								valuename="model" property="difficult"></java2code:option>
						</td>
					</tr>
					<tr id="over2">
						<td class="ali03">状态：</td>
						<td class="ali01"><java2code:option
								name="tkQuestionsInfo.status" codetype="status4"
								valuename="model" property="status"></java2code:option>
						</td>
					</tr>
					<tr>
						<td class="ali03">年级：</td>
						<td class="ali01"><select id="grade"
							name="tkQuestionsInfo.gradeid" boxHeight="130"
							onchange="changeGrade();">
								<c:forEach items="${gradeLists}" var="g">
									<option value="${g.gradeid }" ${model.gradeid eq g.gradeid?"selected":"" }>${g.gradename
										}</option>
								</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
						<td class="ali04">知识点：</td>
						<td><input type="text"
							style="width:180px;background-color:#f7f8f9;color:#888;"
							readonly="readonly" name="knopointname" id="knopointname"
							value="${knopointnames }" onclick="selectKnopoint()" /> <input
							type="hidden" name="knopointid" id="knopointid"
							value="${knopointids }" /> <input type="hidden"
							name="knopointidupdate" id="knopointidupdate" value="0" />
						</td>
					</tr>
					<tr>
						<td class="ali03">地区：</td>
						<td class="ali01"><input type="text"
							name="tkQuestionsInfo.area" value="${model.area}" />
						</td>
					</tr>
					<tr>
						<td class="ali03">标签：</td>
						<td><input type="text" name="tkQuestionsInfo.tag"
							value="${model.tag }" style="width: 670px;" />
						</td>
					</tr>
				     <c:if test="${model.parentid=='0' }">	
					<tr>
						<td class="ali03">答案解析：</td>
						<td><textarea name="tkQuestionsInfo.descript"
								style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getDescript())%></textarea>
						</td>
					</tr>
					</c:if>
					<tr>
						<td class="ali03">年份：</td>
						<td><input type="text" name="tkQuestionsInfo.theyear"
							value="${model.theyear }" readonly="readonly"
							onclick="WdatePicker({dateFmt:'yyyy', alwaysUseStartDate:true})" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button type="button" class="margin_right5"
								onclick="saveRecord()" id="btnsave">
								<span class="icon_save">保存</span>
							</button>
							<button type="button" onclick="javascrip:history.go(-1)">
								<span class="icon_back">返回</span>
							</button>
						</td>
					</tr>
				</table>
				 <input type="hidden" name="tkQuestionsInfo.similartwocodepath" value="${model.similartwocodepath }"/>
                 <input type="hidden" name="tkQuestionsInfo.filmtwocodepath" value="${model.filmtwocodepath }"/>
				<input type="hidden" name="questiontypeid"
					value="${questiontypeid }" />
				<input type="hidden" name="subjectid" value="${subjectid }" />
				 <input type="hidden" name="id" value="${model.parentid }"/>
				<input type="hidden" name="tkQuestionsInfo.parentid" value="${model.parentid }" />
				<input type="hidden" name="tkQuestionsInfo.subjectid"
					value="${model.subjectid }" />
				<input type="hidden" name="questiontypeid"
					value="${questiontypeid }" />
				<input type="hidden" name="tkQuestionsInfo.unitid"
					value="${model.unitid }" />
				<input type="hidden" name="tkQuestionsInfo.questionid"
					value="${model.questionid }" />
				<input type="hidden" name="tkQuestionsInfo.questionno"
					value="${model.questionno }" />
				<input type="hidden" name="tkQuestionsInfo.questiontype"
					value="${model.questiontype }" />
				<input type="hidden" name="tkQuestionsInfo.doctype"
					value="${model.doctype }" />
				<input type="hidden" name="tkQuestionsInfo.cretatdate"
					value="${model.cretatdate}" />
				<input type="hidden" name="tkQuestionsInfo.descriptpath"
					value="${model.descriptpath }" />
				<input type="hidden" name="tkQuestionsInfo.authorname"
					value="${model.authorname }" />
				<input type="hidden" name="tkQuestionsInfo.authorid"
					value="${model.authorid }" />
				       <input type="hidden" name="questionno" value="${questionno }"/>
       <input type="hidden" name="title" value="${title }"/>
       <input type="hidden" name="difficult" value="${difficult }"/>
      <input type="hidden" name="tkQuestionsInfo.filepath" value="${model.filepath }" />    
				<!-- 分页与排序 -->
				<input type="hidden" name="pager.pageNo"
					value="<bean:write name="pageno"/>" />
				<input type="hidden" name="direction"
					value="<bean:write name="direction"/>" />
				<input type="hidden" name="sort" value="<bean:write name="sort"/>" />
			</html:form>
		</div>
	</div>
</body>
</html>
<%!private String htmlspecialchars(String str) {
		if (str == null || "".equals(str))
			return "";
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("\"", "&quot;");
		return str;
	}%>
