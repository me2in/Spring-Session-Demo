package com.wong.learn.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * 相当于原web.xml的功能。
 * 实际上应该实现的是{@link ServletContainerInitializer}类
 * 但spring本身已实现这个类{@link SpringServletContainerInitializer}，而这个类又会自动找实现了{@link WebApplicationInitializer}接口的类，
 * 最终实现tomcat启动自动加载
 *
 * @author wangjuntao
 * @date 2017-4-19
 * @since
 */
public class DefaultWebApplicationInitializer implements
		WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		
		//设置contextLoader,必须第一个设置
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfig.class);
		servletContext.addListener(new ContextLoaderListener(rootContext));
		
		/*
		 * Spring-Session过滤器
		 * spring-session的jar包会对外暴露一个叫“springSessionRepositoryFilter”的bean(配置在{@link SpringHttpSessionConfiguration})
		 * 这个bean在{@link SessionRepositoryFilter}中
		 * 我们用DelegatingFilterProxy(String beanname)方法，这个代理类会自动注入这个springSessionRepositoryFilter，
		 * 并代理这个filter
		 */
		FilterRegistration.Dynamic sessionFilterConfig = servletContext.addFilter("springSessionRepositoryFilter", 
				new DelegatingFilterProxy("springSessionRepositoryFilter"));
		sessionFilterConfig.addMappingForUrlPatterns(getSessionDispatcherTypes(), false, "/*");
		sessionFilterConfig.setAsyncSupported(true);
		
		 // 编码过滤器
		FilterRegistration.Dynamic encodeFilterConfig = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		encodeFilterConfig.setAsyncSupported(true);
		encodeFilterConfig.addMappingForUrlPatterns(getSessionDispatcherTypes(), false, "/*");
		encodeFilterConfig.setInitParameter("encoding", "UTF-8");
		
		//DispatchServlet
		AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.register(SpringMvcConfig.class);
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatchServlet",new DispatcherServlet(mvcContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.setAsyncSupported(true);
		dispatcher.addMapping("/");
	}
	
	protected EnumSet<DispatcherType> getSessionDispatcherTypes() {
		return EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR,
				DispatcherType.ASYNC);
	}

}
