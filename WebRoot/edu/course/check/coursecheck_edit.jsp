<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.edu.bo.EduCourseInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
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

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
<!-- 编辑器end -->

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->

<!-- 籍贯start -->
<script type="text/javascript" src="../../libs/js/sk/PCASClass.js" charset="uft-8"></script>
<!-- 籍贯end -->
<%
EduCourseInfo model = (EduCourseInfo)request.getAttribute("model");
%>
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/eduCourseInfoAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="5">课程信息</th>
		</tr>
		<tr>
			<td class="ali03">课程编号：</td>
			<td class="ali01" ><input type="text" style="width:150px;"  class="validate[required]" name="eduCourseInfo.courseno" id="eduCourseInfo.courseno" value="<bean:write property="courseno" name="model"/>"/></td>
			
			<td class="ali03">课程名称：</td>
			<td class="ali01"><input type="text" style="width: 300px;" class="validate[required]" name="eduCourseInfo.title" value="<bean:write property="title" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">缩略图：</td>
			<td class="ali01">
				 <img src="/upload/<bean:write property="sketch" name="model"/>" title="点击修改照片" width="120" height="90" border="1" id=uploadImgShow onclick="uploadPhoto()"/>
			  <input type="hidden" id="uploadImg" name="eduCourseInfo.sketch" value='<bean:write property="sketch" name="model"/>'/>
			</td>
			
			<td class="ali03">学习次数：</td>
			<td class="ali01"><bean:write property="studys" name="model"/></td>
		</tr>
		<tr>	
		<%
		EduCourseInfo eci = (EduCourseInfo)request.getAttribute("model");
          String[] coursetypeids = Constants.getCodeTypeid("coursetypeid");
          String[] coursetypenames = Constants.getCodeTypename("coursetypeid");
          %>
			<td class="ali03">所属分类：</td>
			<td class="ali01">
				<select name="eduCourseInfo.coursetypeid">
              	<%
              	for(int i=0; i<coursetypeids.length; i++){
              	%>
              	<option value="<%=coursetypeids[i] %>" <%if(coursetypeids[i].equals(eci.getCoursetypeid())){ %>selected="selected"<%} %>><%=coursetypenames[i] %></option>
              	<%} %>
              </select>
			</td>
			
			<td class="ali03">收藏次数：</td>
			<td class="ali01"><bean:write property="collects" name="model"/></td>
			
		</tr>
		<tr>
			<td class="ali03">总课时数：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="eduCourseInfo.classhour" value="<bean:write property="classhour" name="model"/>"/></td>
		
			<td class="ali03">查看次数：</td>
			<td class="ali01"><bean:write property="hits" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">总价：</td>
			<td class="ali01"><input type="text" class="validate[required]" name="eduCourseInfo.price" value="<bean:write property="price" name="model"/>"/></td>
		
			<td class="ali03">注明文字：</td>
			<td class="ali01"><input type="text"  name="eduCourseInfo.note" value="<bean:write property="note" name="model"/>"/></td>
		</tr>
		<tr>
			<td class="ali03">详细描述：</td>
			<td class="ali01" colspan="3">
				<textarea name="eduCourseInfo.htmlcontent" style="width:670px;height:230px;visibility:hidden;"><%=htmlspecialchars(model.getHtmlcontent())%></textarea>
			</td>
		</tr>
		<tr>
			<td class="ali03">创建时间：</td>
			<td class="ali01"><bean:write property="createdate" name="model"/></td>
		
			<td class="ali03">创建教师：</td>
			<td class="ali01"><bean:write property="sysUserInfo.username" name="model"/></td>
		</tr>
		<tr>
			<td class="ali03">开始时间：</td>
			<td class="ali01"><input type="text" style="width: 150px;" name="eduCourseInfo.startdate" value="<bean:write property="startdate" name="model"/>" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/></td>
			
			<td class="ali03">结束时间：</td>
			<td class="ali01"><input type="text" style="width: 150px;" name="eduCourseInfo.enddate"  value="<bean:write property="enddate" name="model"/>" class="date validate[required,custom[date]]" dateFmt="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td class="ali03">审核处理：</td>
			<td class="ali01" colspan="3">
				<input type="radio" name="eduCourseInfo.status" <%if("1".equals(eci.getStatus()))out.print(" checked ");%> value="1"/>审核通过
				<input type="radio" name="eduCourseInfo.status" <%if("2".equals(eci.getStatus()))out.print(" checked ");%> value="2"/>发布待审核
                <input type="radio" name="eduCourseInfo.status" <%if("3".equals(eci.getStatus()))out.print(" checked ");%> value="3"/>审核不通过
                <input type="radio" name="eduCourseInfo.status" <%if("4".equals(eci.getStatus()))out.print(" checked ");%> value="4"/>禁用
			</td>
		</tr>
		<tr>
			<td colspan="5">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="eduCourseInfo.courseid" value='<bean:write property="courseid"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.collects" value='<bean:write property="collects"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.hits" value='<bean:write property="hits"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.studys" value='<bean:write property="studys"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.createdate" value='<bean:write property="createdate"  name="model"/>'/>
<input type="hidden" name="userid" value='<bean:write name="userid"/>'/>
<input type="hidden" name="eduCourseInfo.unitid" value='<bean:write property="unitid"  name="model"/>'/>

<input type="hidden" name="eduCourseInfo.score" value='<bean:write property="score"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.scoreusers" value='<bean:write property="scoreusers"  name="model"/>'/>
<input type="hidden" name="eduCourseInfo.objectives" value='<bean:write property="objectives"  name="model"/>'/>

<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
var keditor;
function initComplete(){
	KindEditor.ready(function(K) {
		keditor = K.create('textarea[name="eduCourseInfo.htmlcontent"]', {
			uploadJson : '/kindeditor/config/upload.jsp',
			allowImageRemote : false,
		});
		prettyPrint();
	});
}
function saveRecord(){
	if(validateForm('form[name=eduCourseInfoActionForm]')){
		var chkObjs=null;
		var obj=document.getElementsByName("eduCourseInfo.status")
		for (var i=0;i<obj.length;i++){ //遍历Radio
			if(obj[i].checked){
				chkObjs=obj[i].value;
			}
		} 
		if(chkObjs == null){
			alert("审核处理不能为空，请选择！");
			return false;
		}
		keditor.sync();
		var objectForm = document.eduCourseInfoActionForm;
	  	objectForm.action="/eduCourseInfoAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.eduCourseInfoActionForm.action="/eduCourseInfoAction.do?method=checkCourseList";
  	document.eduCourseInfoActionForm.submit();
}
function uploadPhoto(){
	var diag = new top.Dialog();
	diag.Title = "上传图片";
	diag.URL = '/sysImageUploadAction.do?method=uploadimage&savepath=column&pathtype=ID&sketch=true';
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