package com.wkmk.util.listener;

import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wkmk.util.common.Constants;
import com.wkmk.util.timer.SystemTimer;

/**
 * <p>
 * Description: 系统监听器
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @Date Dec 12, 2012 3:08:38 PM
 */
public class SystemListener implements ServletContextListener {

	private Timer timer = null;

	public void contextInitialized(ServletContextEvent event) {
		System.out.println("当前产品版本号：" + Constants.PRODUCT_VERSION);
		// timer = new Timer(true);
		// ServletContext context = event.getServletContext();
	}

	public void contextDestroyed(ServletContextEvent event) {
		if (timer == null) {
			timer = new Timer(true);
		}
		timer.cancel();
	}
}