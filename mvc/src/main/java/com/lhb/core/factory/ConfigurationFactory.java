package com.lhb.core.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lhb.core.BeanContainer;
import com.lhb.core.annotation.Bean;

/**
 * 读取配置文件创建bean工程
 *
 * @author 26391
 */
public class ConfigurationFactory {

    private BeanContainer beanContainer = BeanContainer.getInstance();

    private ConfigurationFactory() {

    }

    public void parsingConfiguration(Class<?> clazz) {
        Object t;
        try {
            t = clazz.newInstance();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                for (Annotation annotation : method.getAnnotations()) {
                    if (annotation instanceof Bean) {
                        Bean bean = (Bean) annotation;
                        if ("".equals(bean.name())) {
                            beanContainer.setBean(method.getReturnType().getSimpleName().toLowerCase(),
                                    method.invoke(t, null));
                        } else {
                            beanContainer.setBean(bean.name(), method.invoke(t, null));
                        }
                    }
                }
            }
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    // 单列模式加载
    private static class Singteton {
        private static ConfigurationFactory configurationFactory;

        static {
            configurationFactory = new ConfigurationFactory();
        }

        public static ConfigurationFactory getInstance() {
            return configurationFactory;
        }
    }

    public static ConfigurationFactory getInstance() {
        return Singteton.getInstance();
    }


}
