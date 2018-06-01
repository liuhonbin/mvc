package com.lhb.core;

import java.lang.annotation.Annotation;
import java.util.List;

import com.lhb.core.annotation.Component;
import com.lhb.core.annotation.Configuration;
import com.lhb.core.annotation.Service;
import com.lhb.core.factory.ConfigurationFactory;

public class ApplicationContext {

	private BeanContainer beanContainer = BeanContainer.getInstance();
	private ConfigurationFactory configurationFactory = ConfigurationFactory.getInstance();

	private ApplicationContext() {
		
	}
	
	public void init() {
		getAllAnnotated();
	}

	/**
	 * Gets all annotated classes.
	 */
	private void getAllAnnotated() {
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

	private void isAmmotated(Class<?> clazz) throws InstantiationException, IllegalAccessException {
		Annotation ClazzAnnotation[] = clazz.getAnnotations();
		for (Annotation annotation : ClazzAnnotation) {
			if (annotation instanceof Configuration) {
				configurationFactory.parsingConfiguration(clazz);
			} else if (annotation instanceof Service) {
				Service service = (Service) annotation;
				if ("".equals(service.name())) {
					beanContainer.setBean(clazz.getName().toLowerCase(), clazz.newInstance());
				} else {
					beanContainer.setBean(service.name(), clazz.newInstance());
				}
			} else if (annotation instanceof Component) {
				Component component = (Component) annotation;
				if ("".equals(component.name())) {
					beanContainer.setBean(clazz.getName().toLowerCase(), clazz.newInstance());
				} else {
					beanContainer.setBean(component.name(), clazz.newInstance());
				}
			}
		}
	}

	// 单列模式加载
	private static class Singteton {
		private static ApplicationContext applicationContext;
		static {
			applicationContext = new ApplicationContext();
		}

		public static ApplicationContext getInstance() {
			return applicationContext;
		}
	}

	public static ApplicationContext getInstance() {
		return Singteton.getInstance();
	}

}
