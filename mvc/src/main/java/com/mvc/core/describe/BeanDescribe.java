package com.mvc.core.describe;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * This is an offense for viewing object information, mainly for developers to
 * know what information they are writing in the controller, which is convenient
 * for debugging.
 * 
 * @author lhb@xiu
 *
 */
public class BeanDescribe {

	private static Logger logger = Logger.getLogger(BeanDescribe.class);

	/**
	 * It's just a class description for getting an object, and an attribute
	 * description, a static method described by the method.
	 * 
	 * @param clazz
	 *            Described object
	 */
	public static void getBeanInfo(Class<? extends Object> clazz, String url) {
		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(clazz, Object.class);
			// BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
			MethodDescriptor[] MethodDescriptors = beanInfo.getMethodDescriptors();
			for (MethodDescriptor methodDescriptor : MethodDescriptors) {
				logger.debug("Mapped:{[" + url + "]} onto " + methodDescriptor.getMethod());
			}
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
