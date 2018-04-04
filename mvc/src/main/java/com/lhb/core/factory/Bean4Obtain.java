package com.lhb.core.factory;

import java.util.Map;

import com.lhb.core.ApplicationContext;
import com.lhb.core.exception.BeansException;

public class Bean4Obtain implements BeanFactory {

	private Map<String, Object> ioc = ApplicationContext.ioc;

	@Override
	public Object getBean(String name) throws BeansException {
		if (ioc.get(name) == null) {
			throw new BeansException("Does this '" + name + "' exist?");
		}
		return ioc.get(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		if (ioc.get(name) == null) {
			throw new BeansException("Does this '" + name + "' exist?");
		}
		return (T) ioc.get(name);
	}

	@Override
	public Object getBean(String name, Object... args) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType, Object... args) throws BeansException {
		// TODO Auto-generated method stub
		return null;
	}

}
