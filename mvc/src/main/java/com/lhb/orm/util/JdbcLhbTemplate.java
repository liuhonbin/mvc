package com.lhb.orm.util;


import com.lhb.orm.util.sql.ProxyCgbliSql;
import com.lhb.orm.util.sql.ProxySql;

import net.sf.cglib.proxy.Enhancer;

public class JdbcLhbTemplate {

	
	public JdbcLhbTemplate() {
	}

	@SuppressWarnings("unchecked")
	public static <T> T executeProxy(Class<T> clazz) {
		return (T) ProxySql.newInstance(clazz);
	}

	@SuppressWarnings("unchecked")
	public static <T> T executeProxyCgbli(Class<T> clazz) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new ProxyCgbliSql());
		return (T)enhancer.create();
	}
}
