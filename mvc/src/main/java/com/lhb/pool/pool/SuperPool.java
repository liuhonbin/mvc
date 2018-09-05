package com.lhb.pool.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

import com.lhb.pool.DatabaseConfiguration;


public class SuperPool {

	// 在SuperDataSource.getSuperPoolConnection() 方法中进初始化设置
	private DatabaseConfiguration configuration;
	// 已使用连接
	private ArrayBlockingQueue<SuperPoolConnection> use = new ArrayBlockingQueue<SuperPoolConnection>(1024);

	// 未使用连接
	private ArrayBlockingQueue<SuperPoolConnection> duse = new ArrayBlockingQueue<SuperPoolConnection>(1024);

	// 驱动是否初始化标志位
	private boolean initDriver = false;

	private SuperPool() {

	}

	// 初始化驱动方法，只会加载一次
	private void initDriver() {
		try {
			Class.forName(configuration.getDriverClass());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 初始化成功设置为true
		initDriver = true;
	}

	private SuperPoolConnection getConnection() {
		// 判断驱动是否初始化加载
		if (!initDriver) {
			initDriver();
		}
		Connection conn = null;
		// 自定义的一个Connection类，实现了javax.sql.PooledConnection, java.sql.Connection 接口
		SuperPoolConnection superPoolConnection = null;
		try {
			conn = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getUsername(),
					configuration.getPassword());
			// 将获取到的连接设置到自定义的Connection实现类中
			superPoolConnection = new SuperPoolConnection(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return superPoolConnection;
	}

	// 单列模式加载
	private static class Singteton {
		private static SuperPool superPool;
		static {
			superPool = new SuperPool();
		}

		public static SuperPool getInstance() { 
			return superPool;
		}
	}

	public static SuperPool getInstance() {
		return Singteton.getInstance();
	}

	public DatabaseConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(DatabaseConfiguration configuration) {
		this.configuration = configuration;
	}

	// 获取连接
	public SuperPoolConnection getSuperPoolConnection(long waitTime) throws InterruptedException {
		SuperPoolConnection superPoolConnection = null;
		// 已使用连接 use  未使用连接  duse
		while (true) {
			// 如果可用连接和已用连接加起来的数量小于最大连接，才允许去获取连接，否则设置时间挂起
			if (use.size() + duse.size() == getConfiguration().getMaxIdle()) {
				// 如果可用连接小于等于0那就新获取一个连接，返回之前把这个连接放到已用集合中去
				wait(waitTime);
			} else {
				//未使用连接是否小于等于0  同时已使用连接要小于总连接数
				if (duse.size() <= 0 && use.size() < getConfiguration().getMaxIdle()) {
					superPoolConnection = getConnection();
					use.put(superPoolConnection);
					return superPoolConnection;
				} else {
					
					return duse.take();
				}
				
			}
		}
	}

	// 增加一个连接到已用连接集合中去
	public void addUse(SuperPoolConnection use) {
		this.use.add(use);
	}

	// 移除一个已用连接
	public void removeUse(SuperPoolConnection use) {
		this.use.remove(use);
	}

	// 增加一个可用连接
	public void addDuse(SuperPoolConnection duse) {
		this.duse.add(duse);
	}

	// 移除一个可用连接
	public void removeDuse(SuperPoolConnection duse) {
		this.duse.remove(duse);
	}

	public ArrayBlockingQueue<SuperPoolConnection> getUse() {
		return use;
	}

	public ArrayBlockingQueue<SuperPoolConnection> getDuse() {
		return duse;
	}

	public void setUse(ArrayBlockingQueue<SuperPoolConnection> use) {
		this.use = use;
	}

	public void setDuse(ArrayBlockingQueue<SuperPoolConnection> duse) {
		this.duse = duse;
	}
}
