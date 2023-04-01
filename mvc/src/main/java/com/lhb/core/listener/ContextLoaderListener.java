package com.lhb.core.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lhb.core.ApplicationContext;
import com.lhb.core.ContextLoader;

public class ContextLoaderListener extends ContextLoader implements ServletContextListener {
    private ApplicationContext applicationContext = ApplicationContext.getInstance();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.init();
        applicationContext.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }

}
