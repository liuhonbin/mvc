package com.lhb.pool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.CommonDataSource;

/**
 * 
* <p>Copyright： Copyright (c) 2017</p>
* <p>Company： 熠道大数据</p>
* @ClassName: ResourceManagement  
* @Description: TODO(管理器)  
* @author liuhonbin  
* @date 2018年4月24日
 */
public class ResourceManagement extends LogResourceManagement {
	
	public ResourceManagement() {
		// TODO Auto-generated constructor stub
	}
	
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public int getLoginTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
