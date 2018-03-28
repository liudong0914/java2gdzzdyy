<%@ page contentType="text/html;charset=utf-8"%>
<script type="text/javascript">
var localImageIds = "";
var localAudioIds = "";
function uploadFile(method){
	//上传图片和音频到微信服务器，再上传到龙门作业宝服务器
    //开始上传图片
    if($("#img_div>div").size() > 0){
		 $("#img_div>div").each(function(){
		    	var imgpath = $(this).find("img").eq(1).attr("src");
		    	localImageIds = localImageIds + "," + imgpath;
		  });
    } 
    //开始上传音频
    if($("#audio_div>div").size() > 0){
	   $("#audio_div>div").each(function(){
	       var audioid=$(this).find("img").eq(1).attr("aid");
	       var audiotime=$(this).find("img").eq(1).attr("atimes");
	       localAudioIds = localAudioIds + "," + audioid;
	       audioTimes = audioTimes + "," + audiotime;
	   });
    }
    
    if(localImageIds != "" || localAudioIds != ""){
    	showTipDiv("正在上传！", 120, 50, 15);//15秒
    	if(localAudioIds != ""){
    		if(localImageIds != ""){
    			uploadAudioFile('1', method);//上传完音频后继续上传图片
    		}else{
    			uploadAudioFile('0', method);
    		}
    	}else{
    		uploadImageFile(method);
    	}
    }else{
    	postData(method);
    }
}

function uploadAudioFile(uploadImageTag, method){
	var syncUpload = function(localIds){
		//移除数组中的最后一个元素并返回该元素,如果该数组为空，那么将返回 undefined
		var localId = localIds.pop();
		if(localId && localId != ""){
			wx.uploadVoice({
		  		localId: localId,
		  		isShowProgressTips: 1,
		  		success: function (res) {
		   			var serverId = res.serverId; // 返回音频的服务器端ID
		   			audioServerIds = audioServerIds + "," + serverId;
		   			//其他对serverId做处理的代码
		   			if(localIds && localIds.length > 0){
		    			syncUpload(localIds);
		   			}else{
		   				if(uploadImageTag == '1'){
		   					uploadImageFile(method);
		   				}else{
		   					showTipDiv("正在提交！", 120, 50, 5);
							postData(method);
		   				}
		   			}
		  		}
		 	});
		}else{
			if(localIds && localIds.length > 0){
    			syncUpload(localIds);
   			}else{
   				if(uploadImageTag == '1'){
   					uploadImageFile(method);
   				}else{
   					showTipDiv("正在提交！", 120, 50, 5);
					postData(method);
   				}
   			}
		}
	}
	//需要将字符串转换成数组传递
	var localAudioIdArray = localAudioIds.split(",");
	syncUpload(localAudioIdArray);
}

function uploadImageFile(method){
	var syncUpload = function(localIds){
		//移除数组中的最后一个元素并返回该元素,如果该数组为空，那么将返回 undefined
		var localId = localIds.pop();
		if(localId && localId != ""){
			wx.uploadImage({
		  		localId: localId,
		  		isShowProgressTips: 1,
		  		success: function (res) {
		   			var serverId = res.serverId; // 返回图片的服务器端ID
		   			imageServerIds = imageServerIds + "," + serverId;
		   			//其他对serverId做处理的代码
		   			if(localIds && localIds.length > 0){
		    			syncUpload(localIds);
		   			}else{
		   				showTipDiv("正在提交！", 120, 50, 5);
						postData(method);
		   			}
		  		}
		 	});
		}else{
			if(localIds && localIds.length > 0){
    			syncUpload(localIds);
   			}else{
   				showTipDiv("正在提交！", 120, 50, 5);
				postData(method);
   			}
		}
	}
	//需要将字符串转换成数组传递
	var localImageIdArray = localImageIds.split(",");
	syncUpload(localImageIdArray);
}
</script>