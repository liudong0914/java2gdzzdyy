<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="/libs/jsp/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>角色模块</title>
<!--框架必需start-->
<script type="text/javascript" src="../../libs/js/jquery.js"></script>
<script type="text/javascript" src="../../libs/js/framework.js"></script>
<link href="../../libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="../../"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->

<!-- ztree start -->
<script type="text/javascript" src="../../libs/js/tree/ztree/ztree.js"></script>
<link href="../../libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css"/>
<!-- ztree end -->

</head>
<body>
  <div style="margin-top:8px;text-align:center;">
  <input type="button" value="全选" onclick="CheckAllNodes()"/>&nbsp;&nbsp;
  <input type="button" value="全不选" onclick="CancelAllNodes()"/>&nbsp;&nbsp;
  <input type="button" value="确定" onclick="getSelectValue();"/>&nbsp;&nbsp;
  <input type="button" value="取消" onclick="top.Dialog.close()"/>
  </div>
  <div>
  <ul id="treeDemo" class="ztree"></ul>
  </div>
 <form name="pageForm" method="post">
 <input type="hidden" name="checkid" id="checkid" value=""/>
 <input type="hidden" name="roleid" value="<bean:write name="roleid"/>"/>
 </form>
 <script type="text/javascript">
	var setting = {
		check: {
			enable: true
		},
		view: {
			showIcon: false
		}
	};
	
	var zNodes = [
		<%=request.getAttribute("data") %>
	];
	
	function initComplete(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	}
	
	//选择结果
	function getSelectValue(){
		//获取zTree对象
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		//得到选中的数据集
		var checkedNodes = zTree.getCheckedNodes(true);
		
		var msg = "";
		for(var i = 0; i < checkedNodes.length; i++){
			msg += "," + checkedNodes[i].id;
		}
		if(msg != ""){
			msg = msg.substring(1);
		}
		document.getElementById('checkid').value = msg;
		document.pageForm.action="sysRoleModuleAction.do?method=updateRoleModule";
  		document.pageForm.submit();
	}
	
	//全选
    function CheckAllNodes() {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.checkAllNodes(true);
    }

    //全取消
    function CancelAllNodes() {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.checkAllNodes(false);
    }
 </script>
</body>
</html>