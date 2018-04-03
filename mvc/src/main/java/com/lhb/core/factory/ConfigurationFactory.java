package com.lhb.core.factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.lhb.core.ApplicationContext;
import com.lhb.core.annotation.Bean;

/**
 * 读取配置文件创建bean工程
 * 
 * @author 26391
 *
 */
public class ConfigurationFactory {

	public static void parsingConfiguration(Class<?> clazz) {
		Object t;
		try {
			t = clazz.newInstance();
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				for (Annotation annotation : method.getAnnotations()) {
					if (annotation instanceof Bean) {
						Bean bean = (Bean) annotation;
						if ("".equals(bean.name())) {
							ApplicationContext.ioc.put(method.getReturnType().getSimpleName().toLowerCase(),
									method.invoke(t, null));
						} else {
							ApplicationContext.ioc.put(bean.name(), method.invoke(t, null));
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

}
