function SetCwinHeight(frameid, minheight){
	var iframeid=document.getElementById(frameid);
	if (document.getElementById){
	   if (iframeid && !window.opera){
		   if (iframeid.contentDocument && iframeid.contentWindow.document.documentElement.scrollHeight){
		     var height1 = iframeid.contentWindow.document.documentElement.scrollHeight;
		   	 iframeid.height = height1>minheight?height1:minheight;
		   }else if(iframeid.Document && iframeid.Document.body.scrollHeight){
		     var height2 = iframeid.Document.body.scrollHeight;
		  	 iframeid.height = height2>minheight?height2:minheight;
		   }
	   }
	}
}

function setCwinHeight(leftframeid, rightframeid, minheight){
	//对比框架左侧和内容右侧的高度，哪个高度大就以哪个高度为准
	var leftid = window.top.document.getElementById('frmleft');
	var rightFrameid = document.getElementById(rightframeid);
	var leftFrameid = document.getElementById(leftframeid);
	if (document.getElementById){
	   if (leftid && !window.opera){
		   if (leftid.contentDocument && leftid.contentWindow.document.documentElement.scrollHeight){
		       var leftHeight = leftid.contentWindow.document.documentElement.scrollHeight;
		       var rightFrameHeight = rightFrameid.contentWindow.document.documentElement.scrollHeight;
		   	   if(leftHeight > rightFrameHeight){
		   	       leftFrameid.height = leftHeight>minheight?(leftHeight-35):minheight;
		   	       rightFrameid.height = leftHeight>minheight?(leftHeight+10):minheight;
		   	   }else{
		   	       leftFrameid.height = rightFrameHeight>minheight?(rightFrameHeight-60):minheight;
		   	       rightFrameid.height = rightFrameHeight>minheight?rightFrameHeight:minheight;
		   	   }
		   }else if(leftid.Document && leftid.Document.body.scrollHeight){
		       var leftHeight = leftid.Document.body.scrollHeight;
		       var rightFrameHeight = rightFrameid.Document.body.scrollHeight;
		   	   if(leftHeight > rightFrameHeight){
		   	       leftFrameid.height = leftHeight>minheight?(leftHeight-35):minheight;
		   	       rightFrameid.height = leftHeight>minheight?(leftHeight+10):minheight;
		   	   }else{
		   	       leftFrameid.height = rightFrameHeight>minheight?(rightFrameHeight-60):minheight;
		   	       rightFrameid.height = rightFrameHeight>minheight?rightFrameHeight:minheight;
		   	   }
		   }
	   }
	}
}