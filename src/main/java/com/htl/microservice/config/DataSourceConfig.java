//package com.htl.microservice.config;
//
//import javax.sql.DataSource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import com.alibaba.druid.pool.DruidDataSource;
//
//@Configuration
//public class DataSourceConfig {
//
//	@Value("${spring.datasource.url}")
//	private String url;
//	@Value("${spring.datasource.username}")
//	private String username;
//	@Value("${spring.datasource.password}")
//	private String password;
//	@Value("${spring.datasource.minIdle}")
//	private int minIdle;
//	@Value("${spring.datasource.initialSize}")
//	private int initialSize;
//	@Value("${spring.datasource.maxActive}")
//	private int maxActive;
//	@Value("${spring.datasource.testOnBorrow}")
//	private Boolean testOnBorrow;
//	@Value("${spring.datasource.driverClassName}")
//	private String driverClassName;
//
//	@Bean
//	public DataSource getDataSource() {
//		DruidDataSource dataSource = new DruidDataSource();
//		dataSource.setDriverClassName(driverClassName);
//		dataSource.setUrl(url);
//		dataSource.setUsername(username);
//		dataSource.setPassword(password);
//		dataSource.setMaxActive(maxActive);
//		dataSource.setMinIdle(minIdle);
//		dataSource.setTestOnBorrow(testOnBorrow);
//		dataSource.setInitialSize(initialSize);
//		return dataSource;
//	}
//}
