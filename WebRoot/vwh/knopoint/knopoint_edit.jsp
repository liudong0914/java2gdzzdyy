<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@page import="com.wkmk.vwh.bo.VwhFilmPix"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmKnopoint"%>
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
</head>
<body>
<%
VwhFilmPix pix = (VwhFilmPix)request.getAttribute("pix");
VwhFilmKnopoint vfk = (VwhFilmKnopoint)request.getAttribute("model");
%>
<div>
<div class="box1" position="center">
<html:form action="/vwhFilmKnopointAction.do" method="post">
	<table class="tableStyle" formMode="line" align="center">
		<tr>
			<th colspan="2"><logic:equal value="addSave" name="act">新增微知识点</logic:equal><logic:equal value="updateSave" name="act">修改微知识点</logic:equal></th>
		</tr>
		<tr>
			<td class="ali01" colspan="2">
				<div id="video" style="position:relative;z-index: 100;width:400px;height:200px;float:left;margin-left:100px;"><div id="a1"></div></div>
				<script type="text/javascript" src="/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
				<script type="text/javascript">
					var flashvars={
						<%if("swf".equals(pix.getFileext().toLowerCase())){ %>
						f:'<%=request.getAttribute("vurl") %>',
						s:'3',
						<%}else{%>
						f:'/vurl.action?id=[$pat][$pat1][$pat2]',
						a:'<%=request.getAttribute("vurl") %>',
						s:'1',
						<%}%>
						c:'0',
						i:'/upload/<%=pix.getImgpath() %>',
						e:'2',
						v:'80',
						p:'1',
						h:'0',
						m:'0',
						<%if(vfk.getTiptime() != null && vfk.getTiptime() > 0){ %>
						g:'<%=vfk.getTiptime() %>',
						<%}%>
						ct:'2',
						my_url:encodeURIComponent(window.location.href)
					};
					var params={bgcolor:'#FFF',allowFullScreen:true,allowScriptAccess:'always'};
					var video=['/vurl.action?id=<%=request.getAttribute("vurl1") %>->ajax/get/utf-8'];
					CKobject.embed('/ckplayer/ckplayer/ckplayer.swf','a1','ckplayer_a1','400','200',false,flashvars,video,params);
					
					var ptime = 0;
					function ckplayer_status(str){
						if(str=='paused:true'){
							document.getElementById('tiptime').value = ptime;
						}
						if(str.indexOf('time:') != -1){
							ptime = parseInt(str.replace('time:',''));
						}
					}
				  </script>
				  <br/><div style="float:left;margin-left:100px;color:green;">说明：点击暂停视频将会自动获取视频提示时间点。</div>
			</td>
		</tr>
		<tr>
			<td class="ali03">标题：</td>
			<td class="ali01"><input type="text" class="validate[required]" style="width:400px;" name="vwhFilmKnopoint.title" value="<bean:write property="title" name="model"/>"/><span class="star">*</span></td>
		</tr>
		<tr>
			<td class="ali03">提示时间点：</td>
			<td class="ali01"><input type="text" class="validate[required,onlyNumber]" name="vwhFilmKnopoint.tiptime" id="tiptime" value="<bean:write property="tiptime" name="model"/>"/>(秒)<span class="star">*</span></td>
		</tr>
		<tr>
			<td colspan="3">
				<button type="button" class="margin_right5" onclick="saveRecord()" id="btnsave"><span class="icon_save">保存</span></button>
				<button type="button" onclick="backRecord()"><span class="icon_back">返回</span></button>
			</td>
		</tr>
	</table>
<input type="hidden" name="vwhFilmKnopoint.knopointid" value="<bean:write property="knopointid" name="model"/>"/>
<input type="hidden" name="vwhFilmKnopoint.pixid" value="<bean:write property="pixid" name="model"/>"/>
<input type="hidden" name="vwhFilmKnopoint.filmid" value="<bean:write property="filmid" name="model"/>"/>
<input type="hidden" name="vwhFilmKnopoint.unitid" value="<bean:write property="unitid" name="model"/>"/>
<input type="hidden" name="vwhFilmKnopoint.createdate" value="<bean:write property="createdate" name="model"/>"/>
<input type="hidden" name="vwhFilmKnopoint.showtime" value="<bean:write property="showtime" name="model"/>"/>

<input type="hidden" value="<bean:write name="pixid"/>" name="pixid"/>
<input type="hidden" value="<bean:write name="title"/>" name="title"/>
<!-- 分页与排序 -->
<input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
<input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
<input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
</html:form>
</div>
</div>
<script>
function saveRecord(){
	if(validateForm('form[name=vwhFilmKnopointActionForm]')){
		var objectForm = document.vwhFilmKnopointActionForm;
	  	objectForm.action="vwhFilmKnopointAction.do?method=<bean:write name="act"/>";
	  	objectForm.submit();
	}
}

function backRecord(){
  	document.vwhFilmKnopointActionForm.action="/vwhFilmKnopointAction.do?method=list";
  	document.vwhFilmKnopointActionForm.submit();
}
</script>
</body>
</html>