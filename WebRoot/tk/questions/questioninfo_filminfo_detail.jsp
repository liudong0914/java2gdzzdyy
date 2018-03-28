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

<!-- 编辑器start -->
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>
  </head>
  <body>
  <div>
    <div class="box1" position=center>
    <table id="testTab" class="tableStyle" formMode="line" align="center">
     <tr>
      <th colspan="3">微课详情</th>
      </tr>
      <tr>
      <td class="ali03" width="160">标题 ：</td>
      <td class="ali01">${model.title }</td>
      </tr>
      <tr>
      <td class="ali03" >关键字：</td>
      <td class="ali01">${model.keywords }</td>
      </tr>
      <tr>
      <td class="ali03">描述：</td>
      <td class="ali01">${model.descript }</td>
      </tr>
      <tr>
       <td class="ali03">作者：</td>
       <td class="ali01">${model.actor }</td>
      </tr>
      <tr>
      <td class="ali03">缩略图：</td>
      <td class="ali01">
      <img src="/upload/<bean:write property="sketch" name="model"/>"  width="90" height="120" border="1" />
      </td>
      </tr>
      <tr>
      <td class="ali03">点击量：</td>
      <td class="ali01">${model.hits }</td>
      </tr>
      <tr>
       <td class="ali03">支持量：</td>
       <td class="ali01">${model.support }</td>
      </tr>
      <tr>
       <td class="ali03">踩：</td>
       <td class="ali01">${model.tread }</td>
      </tr>
      <tr>
       <td class="ali03">收藏量：</td>
       <td class="ali01">${model.collects }</td>
      </tr>
      <tr>
      <td class="ali03">视频评分：</td>
      <td class="ali01">${model.score }</td>
      </tr>
      <tr>
       <td class="ali03">视屏评分人数：</td>
       <td class="ali01">${model.scoreusers }</td>
      </tr>
      <tr>
       <td class="ali03">状态：</td>
       <td class="ali01">${statusName }</td>
      </tr>
      <tr>
       <td class="ali03">是否推荐：</td>
       <td class="ali01">
        <c:if test="${model.recommand=='1' }">是</c:if>
        <c:if test="${model.recommand=='0' }">否</c:if>
       </td>
      </tr>
      <tr>
       <td class="ali03">推荐编号：</td>
       <td class="ali01">${model.recommandno }</td>
      </tr>
      <tr>
       <td class="ali03">创建时间：</td>
       <td class="ali01">${model.createdate }</td>
      </tr>
      <tr>
       <td class="ali03">修改时间：</td>
       <td class="ali01">${model.updatetime }</td>
      </tr>
      <tr>
       <td class="ali03">创建人：</td>
       <td class="ali01">${userName }</td>
      </tr>
      <tr>
      <td colspan="2"><button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            </td>
     </tr>
    </table>
    </div>
  </div>
  </body>
</html>
