package com.lhb.core;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhb.core.annotation.Component;
import com.lhb.core.annotation.Configuration;
import com.lhb.core.annotation.Service;
import com.lhb.core.factory.ConfigurationFactory;

public class ApplicationContext {
	public static Map<String, Object> ioc = new HashMap<String, Object>();

	public static void init() {
		getAllAnnotated();
	}

	/**
	 * Gets all annotated classes.
	 */
	private static void getAllAnnotated() {
		List<String> clazzName = ContextLoader.classNames;
		for (String name : clazzName) {
			try {
				Class<?> clazz = Class.forName(name);
				isAmmotated(clazz);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static void isAmmotated(Class<?> clazz) throws InstantiationException, IllegalAccessException {
		Annotation ClazzAnnotation[] = clazz.getAnnotations();
		for (Annotation annotation : ClazzAnnotation) {
			if (annotation instanceof Configuration) {
				ConfigurationFactory.parsingConfiguration(clazz);
			} else if (annotation instanceof Service) {
				Service service = (Service) annotation;
				if ("".equals(service.name())) {
					ioc.put(clazz.getName().toLowerCase(), clazz.newInstance());
				} else {
					ioc.put(service.name(), clazz.newInstance());
				}
			} else if (annotation instanceof Component) {
				Component component = (Component) annotation;
				if ("".equals(component.name())) {
					ioc.put(clazz.getName().toLowerCase(), clazz.newInstance());
				} else {
					ioc.put(component.name(), clazz.newInstance());
				}
			}
		}
	}

}
