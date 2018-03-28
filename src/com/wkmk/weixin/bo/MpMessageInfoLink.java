package com.wkmk.weixin.bo;


public class MpMessageInfoLink extends MpMessageInfo { 
	private String MsgId;// 
	private String Title;//消息标题
	private String Description;//消息描述
	private String Url;//消息链接
	
	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String MsgId) {
		this.MsgId = MsgId;
	}
   
	public String getTitle() {
		return this.Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}
	
	public String getDescription() {
		return Description;
	}

	public void setDescription(String Description) {
		this.Description = Description;
	}
	
	public String getUrl() {
		return Url;
	}

	public void setUrl(String Url) {
		this.Url = Url;
	}
	
}  
