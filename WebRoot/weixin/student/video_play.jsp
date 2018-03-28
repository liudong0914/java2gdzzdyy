<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmPix"%>
<%@page import="com.wkmk.vwh.bo.VwhFilmInfo"%>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>
<title>查看微课</title>
<link href="/weixin/css/header.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/style.css" rel="stylesheet" type="text/css" />
<link href="/weixin/css/css.css" rel="stylesheet" type="text/css" />
<%@ include file="/weixin/index/weixin_js.jsp"%>
<script type="text/javascript" src="/weixin/js/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
function play(filmid, pixid){
	var url = "/weixinStudent.app?method=videoPlay&filmid=" + filmid;
	if(pixid > 0){
		url = url + "&pixid=" + pixid;
	}
	document.pageForm.action = url;
	document.pageForm.submit();
}
</script>
</head>

<body>
<form method="post" name="pageForm" >
<div class="search search_write">
	<%@ include file="/weixin/student/view_goback1.jsp"%>
    <div  class="search_wk">
    	<p class="search_wk_p">查看微课</p>
    </div>
</div>
<div class="search_padding"></div>
<div id="container" style="padding-bottom:45px;">
	<div class="container_class">
    	<div id="a1" style="width:100%;margin:0 auto;text-align:center"></div>
		<script type="text/javascript" src="/ckplayer/ckplayer/ckplayer.js" charset="utf-8"></script>
		<script type="text/javascript">
			var flashvars={
		    	f:'http://${computerInfo.ip }:${computerInfo.port }/upload/${pix.flvpath }',
		   		s:'0',
		        i:'/upload/${pix.imgpath }',
		        c:0,
		        p:1,
		        e:1,
		        h:4
		    };
		    var video=['http://${computerInfo.ip }:${computerInfo.port }/upload/${pix.flvpath }->video/mp4'];
		    CKobject.embed('/ckplayer/ckplayer/ckplayer.swf','a1','ckplayer_a1','100%','100%',true,flashvars,video);
		</script>
    </div>
    <div class="container_class_title">
    	<div class="container_class_title_size">
    		<p>${model.title }</p>
        </div>
    </div>
    <%
    VwhFilmPix pix = (VwhFilmPix)request.getAttribute("pix");
    List pixList = (List)request.getAttribute("pixList");
    if(pixList != null && pixList.size() > 1){
    	VwhFilmPix vfp = null;
    	for(int i=0, size=pixList.size(); i<size; i++){
    		vfp = (VwhFilmPix)pixList.get(i);
    		String bgcolor = "";
    		if(vfp.getPixid().intValue() == pix.getPixid().intValue()){
    			bgcolor = "background-color:#ececec;";
    		}
    %>
    <div class="container_class_other1" <%if(i == 0){ %>style="margin-top:10px;<%=bgcolor %>"<%}else{ %>style="<%=bgcolor %>"<%} %>>
    	<div class="container_class_other_size">
        	<a href="javascript:play(<%=vfp.getFilmid() %>, <%=vfp.getPixid() %>)">
            	<span class="container_class_other_size_left"><%=i+1 %>.<%=vfp.getName() %></span>
                <div class="container_class_other_size_right">
                	<span><img src="/weixin/images/school_11.png" class="container_class_other_size_right_img" /></span>
                </div>
            </a>
        </div>
    </div>
    <%}} %>
    <%
    VwhFilmInfo model = (VwhFilmInfo)request.getAttribute("model");
    List filmList = (List)request.getAttribute("filmList");
    if(filmList != null && filmList.size() > 1){
    	int temp = 0;
    	VwhFilmInfo vfi = null;
    	for(int i=0, size=filmList.size(); i<size; i++){
    		vfi = (VwhFilmInfo)filmList.get(i);
    		if(vfi.getFilmid().intValue() == model.getFilmid().intValue()) continue;
    		temp++;
    %>
    <div class="container_class_other1" <%if(temp == 1){ %>style="margin-top:10px;"<%} %>>
    	<div class="container_class_other_size">
        	<a href="javascript:play(<%=vfi.getFilmid() %>, 0)">
            	<span class="container_class_other_size_left"><%=vfi.getTitle() %></span>
                <div class="container_class_other_size_right">
                	<span><img src="/weixin/images/school_11.png" class="container_class_other_size_right_img" /></span>
                </div>
            </a>
        </div>
    </div>
    <%}} %>
</div>
<input type="hidden" name="qid" value="<bean:write name="qid"/>"/>
<input type="hidden" name="vtype" value="<bean:write name="vtype"/>"/>
<input type="hidden" name="userid" value="<bean:write name="userid"/>"/>
</form>
</body>
</html>