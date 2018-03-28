var zTree_Menu = null;
	var setting = {
		view: {
			fontCss: getFontCss,
			showLine: false,
			selectedMulti: false,
			dblClickExpand: false
		},
		callback: {
			beforeClick:beforeClick,
			onClick: onClick
		}
	};
	
	
	
	function initComplete(){
		setTimeout(function(){
			if(top.listData!=null){
				var treedata=top.listData.treeNodes;
				if(treedata){
					$.fn.zTree.init($("#treeDemo"), setting, treedata);
				}
			}
			
			zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
			
			showContent();
			
			 $("#searchKey").keydown(function(event){
			 	if(event.keyCode==13){
					findNode();
				}
			 })
			 
			 showAll();
		},500);
	}
	
	function initTreeMenu(data){
		if(data){
			var treedata=data.treeNodes;
			if(treedata){
				$.fn.zTree.init($("#treeDemo"), setting, treedata);
			}
			
			showAll();
		}
	}
	
	//每次刷新时保持上次打开的
	function showContent(){
		var treeNodeId=jQuery.jCookie('leftTreeNodeId');
		if(treeNodeId==false||treeNodeId=="false"){}
		else{
			var node = zTree_Menu.getNodeByParam("id", treeNodeId);
			zTree_Menu.selectNode(node);
			//展开上次选中的
			//zTree_Menu.expandNode(node);
			
			//如果上次选中的为第一级节点，则添加选中样式
			if (node.level === 0) {
				//得到当前点击节点，添加选中样式
				var a = $("#" + node.tId + "_a");
				a.click()
			}
			if(node.url){
				//每次刷新时设置当前位置内容
				top.positionType="simple";
				if(node.getParentNode()){
					//top.positionContent="当前位置："+node.getParentNode().name+">>"+node.name;
				}
				//打开上次的链接
				top.frmright.location=node.url;
			}
		}
	}
	//每次点击切换状态和样式
	function beforeClick(treeId, treeNode) {
			//第一级点击项添加选中样式
			if (treeNode.level === 0) {
				//第一级移除选中样式
				$("#"+treeId).find("a").each(function(){
					if($(this).hasClass("cur")){
						$(this).removeClass("cur");
					}
				})
				
				var a = $("#" + treeNode.tId + "_a");
				a.addClass("cur");
			
				//单击展开或收缩
				if(treeNode.open){
					zTree_Menu.expandNode(treeNode,false);
					var a = $("#" + treeNode.tId + "_a");
					a.removeClass("cur");
				}
				else{
					zTree_Menu.expandAll(false);
					zTree_Menu.expandNode(treeNode);
				}
			}
			else{
				zTree_Menu.expandNode(treeNode);
			}
			
			
		}
		
	function onClick(e,treeId, treeNode){
		if (treeNode.level === 0) {
			if(!treeNode.open){
				var a = $("#" + treeNode.tId + "_a");
				a.removeClass("curSelectedNode");
			}
		}
		
		//出现进度条
		if(treeNode.url!=null){
			showProgressBar();
			
			//每次点击时设置当前位置内容
			if(treeNode.getParentNode()){
				//top.positionContent="当前位置："+treeNode.getParentNode().name+">>"+treeNode.name;
			}
			else{
				//top.positionContent="当前位置："+treeNode.name;
			}
			top.positionType="simple";
		}
		
		//存储点击节点id
		jQuery.jCookie('leftTreeNodeId',treeNode.id.toString());
		
	}
	

	function  showAll(){
		zTree_Menu.expandAll(true);
	}
	function  hideAll(){
		zTree_Menu.expandAll(false);
		jQuery.jCookie('leftTreeNodeId',"false");
	}
	function findNode(){
		var value = $.trim($("#searchKey").val());
		if(value!=""){
			getNodesByParamFuzzy('name', value)
		}
	}
	 //根据某一条件查找节点 模糊查询
    function getNodesByParamFuzzy(key, value, parentNode){
        var nodes = zTree_Menu.getNodesByParamFuzzy(key, value, parentNode);  
        //取消之前的高亮显示
        highlightNodes(zTree_Menu, zTree_Menu.highlightNodeList, false);
        //高亮显示
        highlightNodes(zTree_Menu, nodes, true);
        zTree_Menu.highlightNodeList = nodes;
        //选中第一个
        if(null != nodes && nodes.length > 0){
            zTree_Menu.expandAll(false);
			var pNode=nodes[0].getParentNode()
			zTree_Menu.expandNode(pNode,true);
			zTree_Menu.expandNode(nodes[0],true);
        }
    }
    
    //高亮显示
    function highlightNodes(zTree, nodes, highlight) {
        if(null == nodes)  return;
		for( var i = 0, l = nodes.length; i < l; i++) {
			nodes[i].highlight = highlight;
			zTree.updateNode(nodes[i]);
		}
	}
	function getFontCss(treeId, node){
	    return (!!node.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	//返回首页的处理
	function homeHandler(){
		zTree_Menu.expandAll(false);
		top.positionType="none";
		jQuery.jCookie('leftTreeNodeId',"false");
	}
