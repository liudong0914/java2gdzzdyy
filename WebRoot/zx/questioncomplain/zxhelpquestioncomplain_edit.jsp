<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.sys.bo.SysUserInfo"%>
<%@page import="com.wkmk.util.common.Constants"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestion"%>
<%@page import="com.wkmk.zx.bo.ZxHelpFile"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.zx.bo.ZxHelpQuestionComplaint"%>
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

<!--弹窗start-->
<script type="text/javascript" src="../../libs/js/popup/drag.js"></script>
<script type="text/javascript" src="../../libs/js/popup/dialog.js"></script>
<!--弹窗end-->

<!-- 日期控件start -->
<script type="text/javascript" src="../../libs/js/form/datePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<%
ZxHelpQuestionComplaint model=(ZxHelpQuestionComplaint)request.getAttribute("model");
%>
</head>
<body>
<div id="scrollContent">
<div class="box1" position="center">
<html:form action="/zxHelpQuestionComplaintAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="5">投诉处理</th>
		</tr>
		<tr>
			<td class="ali01" rowspan="10" width="65px;">问题详情</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">问题标题：</td>
			<td class="ali01" colspan="3">
				<bean:write property="title" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">问题内容：</td>
			<td class="ali01" colspan="3">
				<textarea rows="" cols="" readonly="readonly" style="width: 99%;background:transparent;border-style:none;font-size:12px; overflow:auto; background-attachment:fixed;background-repeat:no-repeat;border-color:#FFFFFF;outline: none"><bean:write property="descript" name="zxHelpQuestion"/></textarea>	
			</td>
		</tr>	
		<tr>
			<td class="ali03" width="95px;">提问时间：</td>
			<td class="ali01">
				<bean:write property="createdate" name="zxHelpQuestion"/>
			</td>
			<td class="ali03" width="95px;">奖金：</td>
			<td class="ali01">
				<bean:write property="money" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">提问人IP：</td>
			<td class="ali01">
				<bean:write property="userip" name="zxHelpQuestion"/>
			</td>
			<td class="ali03" width="95px;">提问人姓名：</td>
			<td class="ali01">
				<bean:write property="sysUserInfo.username" name="zxHelpQuestion"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">状态：</td>
			<td class="ali01">
				<c:if test="${zxHelpQuestion.status == '0'}">
					待发布
				</c:if>
				<c:if test="${zxHelpQuestion.status == '1'}">
					已发布
				</c:if>
				<c:if test="${zxHelpQuestion.status == '2'}">
					投诉中
				</c:if>
				<c:if test="${zxHelpQuestion.status == '3'}">
					投诉已处理
				</c:if>
				<c:if test="${zxHelpQuestion.status == '9'}">
					已关闭
				</c:if>
			</td>
			<td class="ali03" width="95px;">回复状态：</td>
			<td class="ali01">
				<c:if test="${zxHelpQuestion.replystatus == '0'}">
					未回复
				</c:if>
				<c:if test="${zxHelpQuestion.replystatus == '1'}">
					已被抢单
				</c:if>
				<c:if test="${zxHelpQuestion.replystatus == '2'}">
					已回复
				</c:if>
				<c:if test="${zxHelpQuestion.replystatus == '3'}">
					确认付款
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">浏览次数：</td>
			<td class="ali01">
				<bean:write property="hits" name="zxHelpQuestion"/>
			</td>
			<td class="ali03" width="95px;">销量：</td>
			<td class="ali01">
				<bean:write property="sellcount" name="zxHelpQuestion"/>
			</td>
		</tr>
		
		<%
           	List files = (List)request.getAttribute("files");
		List videoFiles = (List)request.getAttribute("videoFiles");
      		List list2 = (List)request.getAttribute("list2");//录音
      		List list1 = (List)request.getAttribute("list1");//图片
           %>
        <tr>
        	<td class="ali03" width="95px;">提问录音：</td>
			<td class="ali01" colspan="3">
					 <%
						
						if(list2 !=null && list2.size()>0){
						    for(int i=0;i<list2.size();i++){
						        ZxHelpFile file =(ZxHelpFile)list2.get(i);
					%>
						<audio id="myaudio" src="/upload/<%=file.getFlago() %>" controls="controls" style="margin:auto">
						</audio></br>
					<%}}else{ %>
						暂无录音
					<%} %>
			</td>
        </tr>   
		<tr>
			<td class="ali03" width="95px;">提问图片：</td>
			<td class="ali01" colspan="3">
				<%
						if(list1 !=null && list1.size()>0){
				    		for(int i=0;i<list1.size();i++){
				        		ZxHelpFile file =(ZxHelpFile)list1.get(i);
					%>
						<img  src="/upload/<%=file.getThumbpath() %>" alt="<%=file.getFlagl() %>" width="200px" height="150px" onclick="javascript:window.open('/upload/<%=file.getFlago() %>');"/>
					<%}}else{ %>
						暂无图片
					<%} %>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">提问视频：</td>
			<td class="ali01" colspan="3">
			<%
				if(videoFiles !=null && videoFiles.size()>0){
				%>
				<button type="button" onclick="addFilmVideo('<bean:write name="model" property="questionid"/>')">播放视频</button>
			<%}else{ %>
					暂无视频
				<%} %>
			</td>
		</tr>	
		
		
		<tr>
			<td class="ali01" rowspan="7" width="65px;">回复详情</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">回复内容：</td>
			<td class="ali01" colspan="3">
				<textarea rows="" cols="" readonly="readonly" style="width: 99%;background:transparent;border-style:none;font-size:12px; overflow:auto; background-attachment:fixed;background-repeat:no-repeat;border-color:#FFFFFF;outline: none"><bean:write property="content" name="zxHelpAnswer"/></textarea>
			</td>
		</tr>	
		
		<tr>
			<td class="ali03" width="95px;">回复人姓名：</td>
			<td class="ali01">
				<bean:write property="flagl" name="zxHelpAnswer"/>
			</td>
			
			<td class="ali03" width="95px;">回复人IP：</td>
			<td class="ali01">
				<bean:write property="userip" name="zxHelpAnswer"/>
			</td>
		</tr>
		
		<tr>
			<td class="ali03" width="95px;">回复时间：</td>
			<td class="ali01" colspan="3">
				<bean:write property="createdate" name="zxHelpAnswer"/>
			</td>
		</tr>
		<%
           	List files_order = (List)request.getAttribute("files_order");
			List videoFilesOrder = (List)request.getAttribute("videoFilesOrder");
      		List list4 = (List)request.getAttribute("list4");//录音
      		List list3 = (List)request.getAttribute("list3");//图片
           %>
        <tr>
        	<td class="ali03" width="95px;">回复录音：</td>
			<td class="ali01" colspan="3">
					 <%
						
						if(list4 !=null && list4.size()>0){
						    for(int i=0;i<list4.size();i++){
						        ZxHelpFile file =(ZxHelpFile)list4.get(i);
					%>
						<audio id="myaudio" src="/upload/<%=file.getFlago() %>" controls="controls" style="margin:auto">
						</audio></br>
					<%}}else{ %>
						暂无录音
					<%} %>
			</td>
        </tr>   
		<tr>
			<td class="ali03" width="95px;">回复图片：</td>
			<td class="ali01" colspan="3">
				<%
						if(list3 !=null && list3.size()>0){
				    		for(int i=0;i<list3.size();i++){
				        		ZxHelpFile file =(ZxHelpFile)list3.get(i);
					%>
						<img  src="/upload/<%=file.getThumbpath() %>" alt="<%=file.getFlagl() %>" width="200px" height="150px" onclick="javascript:window.open('/upload/<%=file.getFlago() %>');"/>
					<%}}else{ %>
						暂无图片
					<%} %>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">回复视频：</td>
			<td class="ali01" colspan="3">
			<%
			if(videoFilesOrder !=null && videoFilesOrder.size()>0){
			%>
				<button type="button" onclick="addFilmVideo2('<bean:write name="zxHelpAnswer" property="answerid"/>')">播放视频</button>
			<%}else{ %>
				暂无视频
			<%} %>
			</td>
		</tr>
		
		
		<tr>
			<td class="ali01" rowspan="6" width="65px;">投诉详情</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">投诉内容：</td>
			<td class="ali01" colspan="3">
				<bean:write property="descript" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">投诉时间：</td>
			<td class="ali01">
				<bean:write property="createdate" name="model"/>
			</td>
		
			<td class="ali03" width="95px;">投诉人：</td>
			<td class="ali01">
				<bean:write property="flagl" name="model"/>
			</td>
		</tr>
		<tr>
			<td class="ali03" width="95px;">投诉处理：</td>
			<td class="ali01">
				<input type="radio" name="zxHelpQuestionComplaint.status" value="1" <%if(model.getStatus().equals("1")){ %>checked="checked"<%} %> />接受投诉
                <input type="radio" name="zxHelpQuestionComplaint.status" value="2" <%if(model.getStatus().equals("2")){ %>checked="checked"<%} %>/>驳回投诉
			</td>
			
			<td class="ali03" width="95px;">投诉人IP：</td>
			<td class="ali01">
				<bean:write property="userip" name="model"/>
			</td>
		</tr>	
		<tr>
			<td class="ali03" width="95px;">处理内容：</td>
			<td class="ali01" colspan="3">
				<textarea style="width:99%;" name="zxHelpQuestionComplaint.replycontent" id="replycontent"></textarea>
			</td>
		</tr>
		
		<tr>
			<td colspan="4">
				<button type="button" class="margin_right5" onclick="saveRecord()"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="zxHelpQuestionComplaint.complaintid" value='<bean:write property="complaintid"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComplaint.questionid" value='<bean:write property="questionid"  name="model"/>'/>
<input type="hidden" name="zxHelpQuestionComplaint.descript" value='<bean:write property="descript"  name="model"/>' />
<input type="hidden" name="zxHelpQuestionComplaint.createdate" value='<bean:write property="createdate" name="model"/>' />
<input type="hidden" name="zxHelpQuestionComplaint.userid" value='<bean:write property="userid"  name="model"/>' />
<input type="hidden" name="zxHelpQuestionComplaint.userip" value='<bean:write property="userip"  name="model"/>' />
<input type="hidden" name="zxHelpQuestionComplaint.replyuserid" value='<bean:write property="replyuserid"  name="model"/>' />
<input type="hidden" name="zxHelpQuestionComplaint.replyuserip" value='<bean:write property="replyuserip"  name="model"/>' />
<input type="hidden" name="zxHelpQuestionComplaint.replycreatedate" value='<bean:write property="replycreatedate"  name="model"/>' />
<input type="hidden" name="zxHelpQuestionComplaint.orderid" value='<bean:write property="orderid"  name="model"/>' />
<input type="hidden" name="zxHelpQuestionComplaint.teacherid" value='<bean:write property="teacherid"  name="model"/>' />
</html:form>
</div>
</div>
<script>
function saveRecord(){
		var objectForm = document.zxHelpQuestionComplaintActionForm;
		var replycontent=document.getElementById("replycontent").value;
		var chkObjs=null;
		var obj=document.getElementsByName("zxHelpQuestionComplaint.status")
		for (var i=0;i<obj.length;i++){ //遍历Radio
			if(obj[i].checked){
				chkObjs=obj[i].value;
			}
		} 
		if(chkObjs == null){
			alert("投诉处理不能为空，请选择！");
			return false;
		}else{
			if("" == replycontent){
				alert("处理内容不能为空！");
				return false;
			}
		}	
		
	  	objectForm.action="zxHelpQuestionComplaintAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
}
function backRecord(){
  	document.zxHelpQuestionComplaintActionForm.action="/zxHelpQuestionComplaintAction.do?method=list";
  	document.zxHelpQuestionComplaintActionForm.submit();
}
function addFilmVideo(questionid){
	var diag = new top.Dialog();
	diag.Title = "答疑视频";
	diag.URL = '/zxHelpOrderAction.do?method=videoList&flag=1&questionid='+questionid;
	diag.Width = 800;
	diag.Height = 500;
	diag.ShowMaxButton=true;
	diag.CancelEvent = function(){
		diag.close();
	};
	diag.show();
}
function addFilmVideo2(answerid){
	var diag = new top.Dialog();
	diag.Title = "回复视频";
	diag.URL = '/zxHelpOrderAction.do?method=videoListAnswer&flag=1&answerid='+answerid;
	diag.Width = 800;
	diag.Height = 500;
	diag.ShowMaxButton=true;
	diag.CancelEvent = function(){
		diag.close();
	};
	diag.show();
}
</script>
</body>
</html>