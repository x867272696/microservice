package com.htl.microservice;

import java.nio.charset.StandardCharsets;
import javax.servlet.MultipartConfigElement;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hetianlong
 * @since 2019.10.30
 *
 */
@ServletComponentScan
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.htl.microservice.dao")
public class App {
	
	private static final String SWAGGER_SCAN_BASE_PACKAGE = "com.htl.microservice.controller";
	
    /**
     * 远程调用bean
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
    	SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();  
    	requestFactory.setConnectTimeout(15000);
    	requestFactory.setReadTimeout(15000);
    	RestTemplate template = new RestTemplate(requestFactory);
    	template.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    	return template;
    }
    
    /**
     * 文件上传限制大小
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("2048000KB"); // KB,MB
        return factory.createMultipartConfig();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		System.out.println("start success.");
	}
	
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("题库")
                .description("题库")
                .version("1.0")
                .build();
    }
	
}
