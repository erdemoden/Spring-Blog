package com.project.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.requests.AuthRequest;
import com.project.blog.responses.AuthResponse;
import com.project.blog.security.JwtTokenProvider;
import com.project.blog.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
private AuthenticationManager authenticationManager;
private JwtTokenProvider jwtTokenProvider;
private PasswordEncoder passwordEncoder;
private UserService userServcie;


@Autowired
public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder,
		UserService userServcie) {
	this.authenticationManager = authenticationManager;
	this.jwtTokenProvider = jwtTokenProvider;
	this.passwordEncoder = passwordEncoder;
	this.userServcie = userServcie;
}

@PostMapping("/login")
public AuthResponse login(@RequestBody AuthRequest auth) {
	try {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(),auth.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtTokenProvider.generateJwtToken(authentication);
		
	}
	catch(Exception e) {
		AuthResponse response = new AuthResponse();
		response.setCreated(false);
		return response;
	}
	return null;
	
}


}
