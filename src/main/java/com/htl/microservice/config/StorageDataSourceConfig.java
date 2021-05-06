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
@MapperScan(basePackages = { "com.htl.microservice.dao.storage" }, sqlSessionFactoryRef = "storageSqlSessionFactory")
public class StorageDataSourceConfig {
 
	public static final String MAPPER_XML_LOCATION = "classpath:mapper/storage/*.xml";
 
	@Autowired
	@Qualifier("storageDataSource")
	private DataSource storageDataSource;
 
	@Bean
	public SqlSessionTemplate storageSqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(storageSqlSessionFactory());
	}
 
	@Bean
	public SqlSessionFactory storageSqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(storageDataSource);
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_XML_LOCATION));
		return factoryBean.getObject();
	}
}
