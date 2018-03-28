package com.wkmk.weixin.bo;

public class MpMessageInfoEventLocation extends MpMessageInfo {   
	private String ToUserName;//接收消息用户
	private String FromUserName;//发送消息用户
	private String CreateTime;//发生时间，如：1420795776594（当前时间毫秒数）
	private String MsgType;//类型,text,image,voice,video,location,link,event
	
   
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String ToUserName) {
		this.ToUserName = ToUserName;
	}
	
	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String FromUserName) {
		this.FromUserName = FromUserName;
	}
	
	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String CreateTime) {
		this.CreateTime = CreateTime;
	}
	
	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String MsgType) {
		this.MsgType = MsgType;
	}
	
	
}  
