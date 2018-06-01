package com.lhb.core.factory;

import com.lhb.core.BeanContainer;
import com.lhb.core.exception.BeansException;

public class Bean4Obtain implements BeanFactory {
	
	private BeanContainer beanContainer = BeanContainer.getInstance();

	@Override
	public Object getBean(String name) throws BeansException {
		if (beanContainer.getBean(name) == null) {
			throw new BeansException("Does this '" + name + "' exist?");
		}
		return beanContainer.getBean(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		if (beanContainer.getBean(name) == null) {
			throw new BeansException("Does this '" + name + "' exist?");
		}
		return (T) beanContainer.getBean(name);
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
