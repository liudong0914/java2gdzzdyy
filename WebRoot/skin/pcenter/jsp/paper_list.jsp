<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>个人中心</title>
<link type="text/css" rel="stylesheet" href="/skin/pcenter/css/style.css">
<script type="text/javascript" src="/skin/pcenter/js/jquery-1.8.2.min.js"></script>

<!--框架必需start-->
<script type="text/javascript" src="../../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../../libs/js/framework.js"></script>
<link href="../../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<script type="text/javascript" src="../../../libs/js/page_comm.js"></script>
</head>

<body style="background-color:#f9f9f9;">
<%@ include file="top.jsp"%>
<!------内容-------->
<div class="personal">
	<div class="information">
     	<ul>
     	<logic:iterate id="type" name="typelist">
        	<a href="/pcenter.do?method=paperList&typeid=${type.typeid}">
        		<li <logic:equal value="${typeid}" name="type" property="typeid">class="information_line"</logic:equal>>
                	<span>${type.typename}</span>
            	</li>
            </a>
        </logic:iterate>
        </ul>
    </div><!----information-e---->
    <form name="pageForm" method="post">
    <div class="information_right">
    	<div class="testpaper_top">
        	<div class="area testpaper_input">
                    <ul class="clearfix">
                        <li class="return_reques">
                           <select name="subjectid">
                           		<option value="">学科</option>
                           		<logic:iterate id="s" name="subjectList">
                           			<option value="${s.subjectid}" ${subjectid eq s.subjectid?"selected":""}>${s.subjectname}</option>
                           		</logic:iterate>
                           </select>
                        </li>   
                    </ul>
                </div><!----area-e---->
                <div class="area testpaper_input">
                    <ul class="clearfix">
                        <li class="return_reques">
                            <input type="text" placeholder="试卷名称"  name="filename" value="${filename}">
                        </li>   
                    </ul>
                </div><!----area-e---->
                <div class="area testpaper_input">
                    <ul class="clearfix">
                        <li class="return_reques">
                            <select name="theyear">
                           		<option value="">年份</option>
                           		<logic:iterate id="y" name="yearlist">
                           			<option value="${y}" ${y eq theyear?"selected":""}>${y}</option>
                           		</logic:iterate>
                           </select>
                        </li>   
                    </ul>
                </div><!----area-e---->
                <div class="area testpaper_input">
                    <ul class="clearfix">
                        <li class="return_reques">
                            <input type="text" placeholder="所属地区" name="area" value="${area}">
                        </li>   
                    </ul>
                </div><!----area-e---->
             <a href="javascript:search();" class="testpaper_top_a">搜索</a>
             <a href="javascript:location.href='/pcenter.do?method=showUserInfo&mark=1';" style="margin-left:10px;width:100px;" class="testpaper_top_a">返回个人中心</a>
        </div><!----testpaper_top-e---->
        <div class="testpaper_main">
        	<table width="100%" border="0" cellpadding="0" cellspacing="1" style="background:#f0f1f1;">
              <tbody>
                <tr>
                  <td width="35%" height="38" align="center" bgcolor="#f8f8f8">试卷名称</td>
                  <td width="10%" align="center" bgcolor="#f8f8f8">学科</td>
                  <td width="8%" align="center" bgcolor="#f8f8f8">年份</td>
                  <td width="18%" align="center" bgcolor="#f8f8f8">所属地区</td>
                  <td width="9%" align="center" bgcolor="#f8f8f8">下载次数</td>
                  <td width="9%" align="center" bgcolor="#f8f8f8">下载</td>
                </tr>
                <logic:iterate id="pf" name="pagelist" property="datalist">
                <tr class="tr">
                  <td align="left" bgcolor="#FFFFFF" class="tab01">${pf.filename}</td>
                  <td align="center" bgcolor="#FFFFFF">${pf.flago}</td>
                  <td align="center" bgcolor="#FFFFFF">${pf.theyear }</td>
                  <td align="center" bgcolor="#FFFFFF">${pf.area }</td>
                  <td align="center" bgcolor="#FFFFFF">${pf.downloads}</td>
                  <td align="center" bgcolor="#FFFFFF"><a href="/pcenter.do?method=downloadPaperFile&objid=${pf.fileid}" id="testpaper_main_a">点击下载</a></td>
                </tr>
                </logic:iterate>               
              </tbody>
            </table>
            <div class="page_01">
                  ${pageString }
              </div>
        </div><!----testpaper_main-e---->
    </div><!----information_right-e---->
    <input type="hidden" name="typeid" value="${typeid}"/>
    </form>
</div><!----personal-e---->
<%@ include file="footer.jsp"%>
<script type="text/javascript">	
function search(){
	document.pageForm.action="/pcenter.do?method=paperList";
	document.pageForm.submit();
}
</script>
</body>
</html>
