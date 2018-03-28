package com.wkmk.util.timer;

import java.util.TimerTask;

import javax.servlet.ServletContext;

public class SystemTimer extends TimerTask {

	private ServletContext context = null;
	//1=与转码服务器重新交互，
	private String type = null;

	public SystemTimer(ServletContext context, String type) {
		this.context = context;
		this.type = type;
	}
	
	public void run() {
		if("1".equals(type)){
			ConvertNotifyUtil convertNotifyUtil = new ConvertNotifyUtil(context);
			convertNotifyUtil.reConvertFile();
		}
	}
}
