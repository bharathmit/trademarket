package com.trademarket.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

@EnableWebMvc
@ComponentScan(basePackages = { "com.trademarket.controller","com.trademarket.service","com.trademarket.security" })
@Configuration
@Configurable
@Import({ Log4jConfig.class ,MultiHttpSecurityConfig.class , DataBaseConfig.class })
public class AppConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	
	@Bean
	public VelocityConfigurer getVelocityConfig() {
		VelocityConfigurer velConfig = new VelocityConfigurer();
		velConfig.setResourceLoaderPath("/WEB-INF/html/");
		return velConfig;
	}
	
	@Bean
	public VelocityViewResolver getVelocityViewResolver() {
		VelocityViewResolver resolver = new VelocityViewResolver();
		resolver.setCache(true);
		resolver.setExposeSpringMacroHelpers(true);
		resolver.setPrefix("");
		resolver.setSuffix(".html");
		return resolver;
	}
	
	@Bean
	public VelocityEngine velocityEngine() throws VelocityException, IOException{
		VelocityEngineFactoryBean factory = new VelocityEngineFactoryBean();
		Properties props = new Properties();
		props.put("resource.loader", "class");
		props.put("class.resource.loader.class", 
				  "org.apache.velocity.runtime.resource.loader." + 
				  "ClasspathResourceLoader");
		factory.setVelocityProperties(props);
		
		return factory.createVelocityEngine();
	}
	
	
	
	
}
