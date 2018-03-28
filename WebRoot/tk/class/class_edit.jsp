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
  </head>
  <body>
    <div>
     <div class="box1" position=center>
      <html:form action="/tkClassInfoAction.do" method="post">
       <table class="tableStyle" formMode="line" align="center">
         <tr>
            <th colspan="3">${title}</th>
         </tr>
         <tr>
          <td class="ali03" width="80">班级名称：</td>
          <td class="ali01"><input type="text" class="validate[required]" style="width: 200px;" name="tkClassInfo.classname" value="${model.classname}" /><span class="star">*</span></td>
         </tr>
         <c:if test="${!empty(tag)}">
          <tr>
              <td class="ali03" width="80">是否允许拍照上传：</td>
	          <td align="left">
	              <input type="radio" class="validate[required]" name="tkClassInfo.uploadimg" value="0" ${empty(model.uploadimg)?"checked":model.uploadimg eq '0'?"checked":""}/>不允许
	              <input type="radio" class="validate[required]" name="tkClassInfo.uploadimg" value="1" ${empty(model.uploadimg)?"":model.uploadimg eq '1'?"checked":""}/>允许<span class="star">*</span>
	          </td>
          </tr>
          </c:if>
         </tr>
          <td colspan="2">
          <button type="submit" class="margin_right5"><span class="icon_save">保存</span></button>
          <button type="button" onclick="javascrip:history.go(-1)"><span class="icon_back">返回</span></button>            
          </td>
         </tr>
       </table>
            <!-- 分页与排序 -->
             <input type="hidden" name="method" value="<bean:write name="act"/>"/>
             <input type="hidden" name="tag" value="${tag}"/>
 <input type="hidden" name="pager.pageNo" value="<bean:write name="pageno"/>"/>
 <input type="hidden" name="direction" value="<bean:write name="direction"/>"/>
 <input type="hidden" name="sort" value="<bean:write name="sort"/>"/>
 <input type="hidden" name="tkClassInfo.classid" value="${model.classid }"/>
 <input type="hidden" name="tkClassInfo.classno" value="${model.classno }"/>
 <input type="hidden" name="tkClassInfo.students" value="${model.students}"/> 
 <input type="hidden" name="tkClassInfo.pwd" value="${model.pwd}"/>
 <input type="hidden" name="tkClassInfo.createdate" value="${model.createdate}"/>
 <input type="hidden" name="tkClassInfo.twocodepath" value="${model.twocodepath}"/>
 <input type="hidden" name="tkClassInfo.bookid" value="${model.bookid}"/>
 <input type="hidden" name="tkClassInfo.userid" value="${model.userid}"/>
 <input type="hidden" name="tkClassInfo.unitid" value="${model.unitid}"/>
 <input type="hidden" name="tkClassInfo.uploadimg" value="${model.uploadimg }"/>
      </html:form>
     </div>
    </div>
  </body>
</html>
