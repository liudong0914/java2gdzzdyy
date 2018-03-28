package com.wkmk.weixin.bo;


public class MpMessageInfoVideo extends MpMessageInfo {   
	private String MsgId;// 
	private String ThumbMediaId;//视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	private String MediaId;//视频消息媒体id，可以调用多媒体文件下载接口拉取数据
	
	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String MsgId) {
		this.MsgId = MsgId;
	}
   
	public String getFormat() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String ThumbMediaId) {
		this.ThumbMediaId = ThumbMediaId;
	}
	
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String MediaId) {
		this.MediaId = MediaId;
	}
}  
