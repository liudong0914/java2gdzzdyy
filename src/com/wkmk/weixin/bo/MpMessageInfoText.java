package com.wkmk.weixin.bo;

public class MpMessageInfoText extends MpMessageInfo {  
	private String MsgId;// 
	private String Content;//文本内容
	
	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String MsgId) {
		this.MsgId = MsgId;
	}
   
	public String getContent() {
		return Content;
	}

	public void setContent(String Content) {
		this.Content = Content;
	}
}  
