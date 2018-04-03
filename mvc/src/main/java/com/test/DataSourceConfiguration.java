package com.test;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.core.annotation.Bean;
import com.core.annotation.Configuration;

@Configuration
public final class DataSourceConfiguration {

	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/xs");
		dataSource.setUsername("root");
		dataSource.setPassword("123456");
		System.out.println("我被装入容器了哦····························");
		return dataSource;
	}
}
