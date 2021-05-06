package com.htl.microservice.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = { "com.htl.microservice.dao.account" }, sqlSessionFactoryRef = "accountSqlSessionFactory")
public class AccountDataSourceConfig {
 
	public static final String MAPPER_XML_LOCATION = "classpath:mapper/account/*.xml";
 
	@Autowired
	@Qualifier("accountDataSource")
	private DataSource accountDataSource;
 
	@Bean
	public SqlSessionTemplate accountSqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(accountSqlSessionFactory());
	}
 
	@Bean
	public SqlSessionFactory accountSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(accountDataSource);
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_LOCATION));
		return factoryBean.getObject();
	}
}