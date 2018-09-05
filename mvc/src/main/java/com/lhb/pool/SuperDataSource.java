package com.lhb.pool;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

import com.lhb.pool.pool.SuperPool;
import com.lhb.pool.pool.SuperPoolConnection;
import com.lhb.pool.uitl.StringUtils;


/**
 * 
 * <p>
 * Copyright： Copyright (c) 2017
 * </p>
 * <p>
 * Company： 熠道大数据
 * </p>
 * 
 * @ClassName: SuperConnectionPool
 * @Description: TODO(数据库连接池)
 * @author liuhonbin
 * @date 2018年4月24日
 */

public class SuperDataSource extends ResourceManagement implements DataSource, ConnectionPoolDataSource {

	protected volatile String username;
	protected volatile String password;
	protected volatile String jdbcUrl;
	protected volatile String driverClass;
	protected volatile int initialSize = 0;
	protected volatile int minIdle = 0;
	protected volatile int maxIdle = 0;
	protected volatile int maxActive = 0;
	protected volatile long maxWait = 0;
	protected volatile long waitTime = 1000L;

	private SuperPool superPool = SuperPool.getInstance();

	public SuperDataSource() {
	}

	public SuperDataSource(String username, String password, String jdbcUrl) {
		super();
		this.username = username;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
	}

	public SuperPoolConnection getSuperPoolConnection() {
		init();
		try {
			return superPool.getSuperPoolConnection(getWaitTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Connection getConnection() throws SQLException {
		SuperPoolConnection superPoolConnection = getSuperPoolConnection();
		if (superPoolConnection != null) {
			return superPoolConnection;
		} else {
			throw new UnsupportedOperationException("Not supported by SuperDataSource");
		}
	}

	public Connection getConnection(String username, String password) throws SQLException {
		if (this.username == null && this.password == null && username != null && password != null) {
			this.username = username;
			this.password = password;
			return getConnection();
		}

		if (!StringUtils.equals(username, this.username)) {
			throw new UnsupportedOperationException("Not supported by SuperDataSource");
		}

		if (!StringUtils.equals(password, this.password)) {
			throw new UnsupportedOperationException("Not supported by SuperDataSource");
		}
		return getConnection();
	}

	private void init() {
		if (superPool.getConfiguration() == null) {
			DatabaseConfiguration configuration = new DatabaseConfiguration();
			configuration.setDriverClass(getDriverClass());
			configuration.setJdbcUrl(getJdbcUrl());
			configuration.setUsername(getUsername());
			configuration.setPassword(getPassword());
			configuration.setInitialSize(getInitialSize());
			configuration.setMaxActive(getMaxActive());
			configuration.setMaxIdle(getMaxIdle());
			configuration.setMaxWait(getMaxWait());
			configuration.setMinIdle(getMinIdle());
			superPool.setConfiguration(configuration);
		}
	}

	public PooledConnection getPooledConnection() throws SQLException {
		SuperPoolConnection superPoolConnection = getSuperPoolConnection();
		if (superPoolConnection != null) {
			return superPoolConnection;
		} else {
			throw new UnsupportedOperationException("Not supported by SuperDataSource");
		}
	}

	public PooledConnection getPooledConnection(String user, String password) throws SQLException {
		if (this.username == null && this.password == null && username != null && password != null) {
			this.username = username;
			this.password = password;
			return getPooledConnection();
		}

		if (!StringUtils.equals(username, this.username)) {
			throw new UnsupportedOperationException("Not supported by SuperDataSource");
		}

		if (!StringUtils.equals(password, this.password)) {
			throw new UnsupportedOperationException("Not supported by SuperDataSource");
		}
		return getPooledConnection();
	}

	// 关闭连接池的时候这里要去进行连接集合的清理，目前未做实现
	public void close() throws SQLException {
		// 开始回收连接
		for (Connection connection : superPool.getUse()) {
			connection.close();
		}
		superPool.setUse(null);
		for (Connection connection : superPool.getDuse()) {
			connection.close();
		}
		superPool.setDuse(null);
	}

	// ****************参数get,set方法分界线******************************************
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public long getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}

	public long getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}

}
