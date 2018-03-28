package com.wkmk.weixin.bo;


public class MpMessageInfoEvent extends MpMessageInfo {   
	private String Event;// 
	private String EventKey;// 
	private String Ticket;// 
	
	public String getEvent() {
		return Event;
	}

	public void setEvent(String Event) {
		this.Event = Event;
	}
	
	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String EventKey) {
		this.EventKey = EventKey;
	}
	
	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String Ticket) {
		this.Ticket = Ticket;
	}
}  
