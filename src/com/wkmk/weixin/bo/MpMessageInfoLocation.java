package com.wkmk.weixin.bo;


public class MpMessageInfoLocation extends MpMessageInfo {   
	private String MsgId;// 
	private String Location_X;//地理位置维度
	private String Location_Y;//地理位置经度
	private String Scale;//地图缩放大小
	private String Label;//地理位置信息
	
	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String MsgId) {
		this.MsgId = MsgId;
	}
   
	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String Location_X) {
		this.Location_X = Location_X;
	}
	
	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String Location_Y) {
		this.Location_Y = Location_Y;
	}
	
	public String getScale() {
		return Scale;
	}

	public void setScale(String Scale) {
		this.Scale = Scale;
	}
	
	public String getLabel() {
		return Label;
	}

	public void setLabel(String Label) {
		this.Label = Label;
	}
}  
