package com.project.blog.Filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.blog.entities.User;
import com.project.blog.responses.AuthResponse;
import com.project.blog.security.JwtTokenProvider;
import com.project.blog.services.UserService;


public class RouteFilter implements Filter {
	
	

	public JwtTokenProvider jwtTokenProvider;
	

	public UserService userService;
	
	
	public RouteFilter(JwtTokenProvider tokenProvider, UserService userService) {
	this.jwtTokenProvider = tokenProvider;
	this.userService = userService;
	}
	
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	
	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		if(bearer!=null&&StringUtils.hasText(bearer)&& bearer.startsWith("Bearer ")) {
			return bearer.substring("Bearer".length()+1);
		}
		return null;
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		AuthResponse authResponse = new AuthResponse();
		String jwt = extractJwtFromRequest(httpRequest);
		 
		if(jwt!=null&&StringUtils.hasText(jwt)&&jwtTokenProvider.validateToken(jwt) == true) {
			authResponse.setRoute("/homepage");
			long id = jwtTokenProvider.getUserIdFromJwt(jwt);
			User user = userService.findById(id);
			authResponse.setUsername(user.getUsername());
			mapper.writeValue(response.getOutputStream(), authResponse);
		}
		chain.doFilter(httpRequest, response);
	}


}
