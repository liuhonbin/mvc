package com.lhb.core;

import java.util.HashMap;
import java.util.Map;

public class BeanContainer {

    public Map<String, Object> ioc = new HashMap<String, Object>();

    private BeanContainer() {

    }

    // 单列模式加载
    private static class Singteton {
        private static BeanContainer beanContainer;

        static {
            beanContainer = new BeanContainer();
        }

        public static BeanContainer getInstance() {
            return beanContainer;
        }
    }

    public static BeanContainer getInstance() {
        return Singteton.getInstance();
    }

    public Object getBean(String name) {
        return ioc.get(name);
    }

    public Object setBean(String name, Object value) {
        return ioc.put(name, value);
    }
}
