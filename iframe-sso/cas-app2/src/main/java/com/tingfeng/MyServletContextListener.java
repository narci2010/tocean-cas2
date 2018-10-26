/*
 * 版权所有.(c)2010-2018. 拓胜科技
 */
package com.tingfeng;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {
	// jetty 采用了xml配置，所以必须在xml中用传统配置设置
	public void contextInitialized(ServletContextEvent sce) {
		HTTPSTrustManager.allowAllSSL();
		System.out.println("servletContext被创建了！");// web应用启动的时候，执行
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("servletcontext被销毁了！！");// web应用关闭的时候，执行
	}
}