package com.lhb.orm.base.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.lhb.mvc.core.DispatcherServlet;
import com.lhb.orm.base.BaseDao;
import com.lhb.orm.base.BaseEntity;
import com.lhb.orm.util.DBUtil;
import com.lhb.orm.util.DTOUtils;
import com.lhb.orm.util.SqlUtil;

public class BaseImpl implements BaseDao {
	
	private Logger logger = Logger.getLogger(BaseImpl.class);

	protected DBUtil dbUtil;

	public BaseImpl(DataSource dataSource) {
		dbUtil = new DBUtil(dataSource);
	}

	public void setDataSource(DataSource dataSource) throws RuntimeException {
		dbUtil = new DBUtil(dataSource);
	}

	public <T> List<T> getList(String sql, Class<T> clazz, Object baseEntity) {
		List<T> list = new ArrayList<T>();
		String newSql = null;
		ResultSet rs = null;
		try {
			newSql = SqlUtil.sqlPrament(sql, baseEntity);
			debug(newSql);
			rs = dbUtil.query(newSql);
			while (rs.next()) {
				list.add(DTOUtils.ReqBuildEntity(rs, clazz));
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public <T> T queryForObject(String sql, Class<T> clazz, Object baseEntity) {
		String newSql = null;
		ResultSet rs = null;
		try {
			newSql = SqlUtil.sqlPrament(sql, baseEntity);
			debug(newSql);
			rs = dbUtil.query(newSql);
			while (rs.next()) {
				return DTOUtils.ReqBuildEntity(rs, clazz);
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int update(String sql, Object baseEntity) {
		// TODO Auto-generated method stub
		int a = 0;
		try {
			String new_sql = SqlUtil.sqlPrament(sql, baseEntity);
			debug(new_sql);
			a = dbUtil.other(new_sql);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
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
		return a;
	}

	public int delete(String sql, Object baseEntity) {
		// TODO Auto-generated method stub
		int a = 0;
		try {
			String new_sql = SqlUtil.sqlPrament(sql, baseEntity);
			debug(new_sql);
			a = dbUtil.other(new_sql);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
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
		return a;
	}

	@Override
	public <T> int add(String sql, Object baseEntity) {
		// TODO Auto-generated method stub
		int a = 0;
		try {
			String new_sql = SqlUtil.sqlPrament(sql, baseEntity);
			debug(new_sql);
			a = dbUtil.other(new_sql);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
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
		return a;
	}

	@Override
	public <T> int add(String sql, List<Object> list) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	private void debug(Object object) {
		if(logger.isDebugEnabled()) {
			logger.debug(object);
		}
	}

}
