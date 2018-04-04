package com.lhb.orm.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

/**
 * 数据库连接
 * 
 * @author liuhonbin
 *
 */
public class DBUtil {

	protected DataSource dataSource;

	public DBUtil(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 查询调用
	 * 
	 * @return
	 */
	public ResultSet query(String sql) {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public int other(String sql) {
		Connection conn = null;
		Statement st = null;
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			st.addBatch(sql);
			conn.commit();
			return 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
				return 0;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return 0;
	}

}
