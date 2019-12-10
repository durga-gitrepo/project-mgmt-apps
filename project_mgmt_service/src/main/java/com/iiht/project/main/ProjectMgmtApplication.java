package com.iiht.project.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(scanBasePackages = {"com.iiht.project"})
@EnableJpaRepositories(basePackages= {"com.iiht.project.repository"})
@ComponentScan({"com.iiht.project.service", "com.iiht.project.controller"})
@EntityScan("com.iiht.project.model")
@EnableAutoConfiguration
@Component
public class ProjectMgmtApplication extends SpringBootServletInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectMgmtApplication.class);
			
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{
		return builder.sources(ProjectMgmtApplication.class);
	}
	
	public static void main(String args[])
	{
		SpringApplication.run(ProjectMgmtApplication.class, args);
		logger.info("Application started successfuully");
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{		
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/project/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
			} 
		};
	}
}
