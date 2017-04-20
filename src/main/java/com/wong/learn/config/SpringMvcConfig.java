package com.wong.learn.config;

import java.util.Arrays;
import java.util.List;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.wong.learn.interceptor.PermissionInterceptor;

/**
 * 相当于原spring-mvc.xml
 * 继承WebMvcConfigurerAdapter 可以简化很大一部分设置，可以省略绝大部分bean方法
 *
 * @author wangjuntao
 * @date 2017-4-19
 * @since
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.wong.learn.controller")
public class SpringMvcConfig extends WebMvcConfigurerAdapter {


	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML));
		converters.add(converter);
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/jsp/", ".jsp");
		registry.enableContentNegotiation(new MappingJackson2JsonView());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new PermissionInterceptor()).addPathPatterns(
				"/**");
	}

}
