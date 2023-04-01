package com.lhb.orm.util.sql;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.sql.DataSource;

import com.lhb.core.exception.BeansException;
import com.lhb.core.factory.Bean4Obtain;
import com.lhb.core.factory.BeanFactory;
import com.lhb.orm.DataSourceSetting;
import com.lhb.orm.annotation.delete;
import com.lhb.orm.annotation.insert;
import com.lhb.orm.annotation.select;
import com.lhb.orm.annotation.update;
import com.lhb.orm.base.BaseDao;
import com.lhb.orm.base.BaseEntity;
import com.lhb.orm.base.impl.BaseImpl;

public class ProxySql implements InvocationHandler {
    private BaseDao dao;
    private BeanFactory factory = new Bean4Obtain();

    public static Object newInstance(Class<?> clazz) {
        @SuppressWarnings("rawtypes")
        Class[] interfaces = new Class[1];
        interfaces[0] = clazz;
        return Proxy.newProxyInstance(ProxySql.class.getClassLoader(), interfaces, new ProxySql());
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return in(proxy, method, args);
    }

    private Object in(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
        try {
            dao = new BaseImpl(factory.getBean("datasource", DataSource.class));
            DataSourceSetting dataSourceSetting = null;
            if (factory.getBean("datasourcesetting") != null) {
                dataSourceSetting = factory.getBean("datasourcesetting", DataSourceSetting.class);
            }
            Class<?> returnType = method.getReturnType();
            Annotation annotation[] = method.getAnnotations();
            if (returnType.getName().contains("java.util.List")) {
                if (annotation.length > 0) {
                    for (Annotation an : annotation) {
                        if (an instanceof select) {
                            select sql_object = (select) an;
                            if (dataSourceSetting != null) {
                                dao.setDataSource(dataSourceSetting.getDataSource(sql_object.type()));
                            }
                            return dao.getList(sql_object.sql(), sql_object.return_type(), (Object) args[0]);
                        }

                    }
                }
            } else {
                if (annotation.length > 0) {
                    for (Annotation an : annotation) {
                        if (an instanceof update) {
                            update update_object = (update) an;
                            if (dataSourceSetting != null) {
                                dao.setDataSource(dataSourceSetting.getDataSource(update_object.type()));
                            }
                            return dao.update(update_object.sql(), (Object) args[0]);
                        } else if (an instanceof delete) {
                            delete delete_object = (delete) an;
                            if (dataSourceSetting != null) {
                                dao.setDataSource(dataSourceSetting.getDataSource(delete_object.type()));
                            }
                            return dao.delete(delete_object.sql(), (Object) args[0]);
                        } else if (an instanceof select) {
                            select sql_object = (select) an;
                            if (dataSourceSetting != null) {
                                dao.setDataSource(dataSourceSetting.getDataSource(sql_object.type()));
                            }
                            return dao.queryForObject(sql_object.sql(), sql_object.return_type(), (Object) args[0]);
                        } else if (an instanceof insert) {
                            insert insert_object = (insert) an;
                            if (dataSourceSetting != null) {
                                dao.setDataSource(dataSourceSetting.getDataSource(insert_object.type()));
                            }
                            return dao.add(insert_object.sql(), (Object) args[0]);
                        }
                    }
                }
            }
        } catch (BeansException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

}
