<%@ page contentType="text/html;charset=utf-8"%>
<audio id="audioPlay" style="display:none;"></audio>
<input type="hidden" name="hiddenfileid" id="hiddenfileid" value=""/>
<script>
function playAudio(filepath, fileid){
	//当前播放一个音频，直接点击播放另一个音频
	var hiddenfileid = document.getElementById("hiddenfileid").value;
	if(hiddenfileid != "" && hiddenfileid != fileid){
		stopAudio(hiddenfileid);
	}
	document.getElementById("hiddenfileid").value = fileid;
	
	var audio = document.getElementById('audioPlay');
	if(audio){
		if(audio.src && audio.src != "" && audio.src != "http://127.0.0.1/default.mp3"){
			stopAudio(fileid);
		}else{
			document.getElementById(fileid).src = "/weixin/images/d01.gif";
			audio.src = filepath;
			audio.volume = 1;//设置最大音量
			//监听播放结束
			audio.addEventListener('ended', function (){
			    stopAudio(fileid)
			}, false);
	        audio.play();
		}
	}
}
function stopAudio(fileid){
	if(fileid && fileid != ""){
	}else{
		fileid = document.getElementById("hiddenfileid").value;
	}
	var audio = document.getElementById('audioPlay');
	if(audio){
		audio.src = "http://127.0.0.1/default.mp3";
		audio.pause();
		document.getElementById(fileid).src = "/weixin/images/d01.png";
	}
}

window.onunload = function(){
	var fileid = document.getElementById("hiddenfileid").value;
	if(fileid != ""){
		stopAudio(fileid);
	}
} 
</script>