package com.wkmk.weixin.bo;



public class MpMessageInfoImage extends MpMessageInfo { 
	private String MsgId;// 
	private String PicUrl;//图片链接
	private String MediaId;//图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
   
	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String MsgId) {
		this.MsgId = MsgId;
	}
	
	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String PicUrl) {
		this.PicUrl = PicUrl;
	}
	
	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String MediaId) {
		this.MediaId = MediaId;
	}
}  
