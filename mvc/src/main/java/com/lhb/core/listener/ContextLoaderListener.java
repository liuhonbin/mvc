package com.lhb.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lhb.core.ApplicationContext;
import com.lhb.core.ContextLoader;

public class ContextLoaderListener extends ContextLoader implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.init();
		ApplicationContext.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
