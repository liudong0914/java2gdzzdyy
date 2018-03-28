package com.wkmk.weixin.bo;


public class MpMessageInfoVoice extends MpMessageInfo {   
	private String MsgId;// 
	private String Format;//语音格式，如amr，speex等
	private String MediaId;//语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String Recognition;//识别文字
	
	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String MsgId) {
		this.MsgId = MsgId;
	}
   
	public String getFormat() {
		return Format;
	}

	public void setFormat(String Format) {
		this.Format = Format;
	}
	
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String MediaId) {
		this.MediaId = MediaId;
	}
	
	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String Recognition) {
		this.Recognition = Recognition;
	}
}  
