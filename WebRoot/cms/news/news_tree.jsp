<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../../libs/jsp/taglibs.jsp"%>
<html>
  <head>
    <title></title>
    <script language="JavaScript" src="/libs/js/tree/mztree/mztree.js"></script>
	<link href="/libs/js/tree/mztree/mztree.css" rel="stylesheet" type="text/css">
  </head>
  <body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <script LANGUAGE="JavaScript">
    var tree = new MzTreeView("tree");
    tree.icons["property"] = "property.gif";
    tree.icons["css"] = "collection.gif";
    tree.icons["book"]  = "book.gif";
    tree.iconsExpand["book"] = "bookopen.gif"; //展开时对应的图片
    tree.setIconPath("/libs/js/tree/mztree/img/"); //可用相对路径
    tree.nodes['-1_0000'] = 'text:资讯管理';
 	<bean:write name="treenode" filter="false"/>//树节点
    tree.setURL("<bean:write name="rooturl"/>");
    tree.setTarget("rfrmright");
    document.write(tree.toString());    
    </script>
  </body>
</html>