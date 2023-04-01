package com.lhb.core.factory;

import com.lhb.core.exception.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    public <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    public Object getBean(String name, Object... args) throws BeansException;

    public <T> T getBean(String name, Class<T> requiredType, Object... args) throws BeansException;
}
