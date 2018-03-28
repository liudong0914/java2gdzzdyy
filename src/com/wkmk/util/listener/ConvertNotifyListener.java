package com.wkmk.util.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wkmk.util.timer.SystemTimer;
import com.util.date.DateTime;

/**
 * <p>Description: 转码通知《将与转码服务器交互失败的文档或视频重新与转码服务器交互》</p>
 * <p>E-mail: zhangxuedong28@gmail.com</p>
 * @Date Dec 12, 2012 3:08:38 PM
 */
public class ConvertNotifyListener implements ServletContextListener {
	private Timer timer = null;
	private static final Integer CONNECT_SERVER_TIME = 1 * 60;//默认1小时连接一次数据库查询

	public void contextInitialized(ServletContextEvent event) {
		timer = new Timer(true);
		ServletContext context = event.getServletContext();
		
		// 设置定时将与转码服务器交互失败的文档或视频重新与转码服务器交互
		//timer.schedule(new SystemTimer(context, "1"), getHtmlTime(), 1000 * 60 * CONNECT_SERVER_TIME);
		timer.schedule(new SystemTimer(context, "1"), 30*1000, 1000 * 60 * CONNECT_SERVER_TIME);//30秒后立即执行
	}

	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
	}
	
	/*
	 * 获取系统定时转码的时间点
	 * 系统启动时大于当前设置的时间时，就会自动先执行一次
	 */
	private static Date getHtmlTime(){
		Date date = null;
		String curdate = DateTime.getDate("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = format.parse(curdate + " 00:01:02");
		} catch (ParseException e) {
		}
		
		return date;
	}
}