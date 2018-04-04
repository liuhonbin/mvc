package com.lhb.orm.util.sql;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.sql.DataSource;

import com.lhb.core.exception.BeansException;
import com.lhb.core.factory.Bean4Obtain;
import com.lhb.core.factory.BeanFactory;
import com.lhb.orm.annotation.Delete;
import com.lhb.orm.annotation.Sql;
import com.lhb.orm.annotation.Update;
import com.lhb.orm.base.BaseDao;
import com.lhb.orm.base.BaseEntity;
import com.lhb.orm.base.impl.BaseImpl;

import net.sf.cglib.proxy.InvocationHandler;

public class ProxyCgbliSql implements InvocationHandler {

	private BaseDao dao;
	private BeanFactory factory = new Bean4Obtain();

	public ProxyCgbliSql() {
		// TODO Auto-generated constructor stub
		try {
			dao = new BaseImpl(factory.getBean("datasource", DataSource.class));
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return in(proxy, method, args);
	}

	private Object in(Object proxy, Method method, Object[] args)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		Class<?> returnType = method.getReturnType();
		Annotation annotation[] = method.getAnnotations();
		if (returnType.getName().contains("java.util.List")) {
			if (annotation.length > 0) {
				for (Annotation an : annotation) {
					if (an instanceof Sql) {
						Sql sql_object = (Sql) an;
						return dao.getList(sql_object.sql(), sql_object.return_type(), (BaseEntity) args[0]);
					}

				}
			}
		} else {
			if (annotation.length > 0) {
				for (Annotation an : annotation) {
					if (an instanceof Update) {
						Update update_object = (Update) an;
						return dao.update(update_object.sql(), (BaseEntity) args[0]);
					} else if (an instanceof Delete) {
						Delete delete_object = (Delete) an;
						return dao.delete(delete_object.sql(), (BaseEntity) args[0]);
					} else if (an instanceof Sql) {
						Sql sql_object = (Sql) an;
						return dao.queryForObject(sql_object.sql(), sql_object.return_type(), (BaseEntity) args[0]);
					}
				}
			}
		}
		return null;
	}

}
