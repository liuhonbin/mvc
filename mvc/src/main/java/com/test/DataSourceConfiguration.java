package com.test;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.lhb.core.annotation.Bean;
import com.lhb.core.annotation.Configuration;
import com.lhb.orm.DataSourceSetting;
import com.lhb.orm.SqlType;

@Configuration
public final class DataSourceConfiguration {

	/**
	 * 默认数据源配置
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/xs");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}

	/**
	 * 同步数据源配置
	 * @return
	 */
	@Bean(name = "back")
	public DataSource dataSourceBack() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/xs_back");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		return dataSource;
	}

	/**
	 * 配置读写数据源
	 * @return
	 */
	@Bean
	public DataSourceSetting dataSourceSetting() {
		DataSourceSetting dataSourceSetting = new DataSourceSetting();
		dataSourceSetting.setSlave("datasource", SqlType.SELECT);
		dataSourceSetting.setMaster("back", SqlType.INSERT,SqlType.DELETE,SqlType.UPDATE);
		return dataSourceSetting;
	}
}
