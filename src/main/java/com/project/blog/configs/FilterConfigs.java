package com.project.blog.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.project.blog.Filters.RouteFilter;
import com.project.blog.security.JwtTokenProvider;
import com.project.blog.services.UserService;

@Configuration
public class FilterConfigs {
	
@Autowired
UserService userService;

@Autowired
JwtTokenProvider jwtTokenProvider;
@Bean 
public FilterRegistrationBean<RouteFilter>routeBean(){
	FilterRegistrationBean<RouteFilter> routeFilterRegistrationBean = new FilterRegistrationBean<RouteFilter>();
	routeFilterRegistrationBean.addUrlPatterns("/auth/route");
	routeFilterRegistrationBean.setFilter(new RouteFilter(jwtTokenProvider,userService));
	return routeFilterRegistrationBean;
	
}

}
