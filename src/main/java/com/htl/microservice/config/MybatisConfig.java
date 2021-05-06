package com.htl.microservice.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MybatisConfig {
		@Value("${spring.datasource.account.url}")
		private String accountDbUrl;
	 
		@Value("${spring.datasource.account.username}")
		private String accountUsername;
	 
		@Value("${spring.datasource.account.password}")
		private String accountPassword;
	 
		@Value("${spring.datasource.storage.url}")
		private String storageDbUrl;
	 
		@Value("${spring.datasource.storage.username}")
		private String storageUsername;
	 
		@Value("${spring.datasource.storage.password}")
		private String storagePassword;
	 
		final static Logger logger = LoggerFactory.getLogger(MybatisConfig.class);
	 
		@Bean(name = "accountDataSource")
		@Primary
		public DataSource accountDataSource() {
			AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
			atomikosDataSourceBean.setUniqueResourceName("accountDataSource");
			atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
			Properties properties = new Properties();
			properties.put("URL", accountDbUrl);
			properties.put("user", accountUsername);
			properties.put("password", accountPassword);
			atomikosDataSourceBean.setXaProperties(properties);
			return atomikosDataSourceBean;
		}
	 
		@Bean(name = "storageDataSource")
		public DataSource storageDataSource() {
			AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
			atomikosDataSourceBean.setUniqueResourceName("storageDataSource");
			atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.cj.jdbc.MysqlXADataSource");
			Properties properties = new Properties();
			properties.put("URL", storageDbUrl);
			properties.put("user", storageUsername);
			properties.put("password", storagePassword);
			atomikosDataSourceBean.setXaProperties(properties);
			return atomikosDataSourceBean;
		}
}
