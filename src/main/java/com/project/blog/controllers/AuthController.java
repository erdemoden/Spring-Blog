package com.project.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.entities.User;
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
private UserService userService;


@Autowired
public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder,
		UserService userService) {
	this.authenticationManager = authenticationManager;
	this.jwtTokenProvider = jwtTokenProvider;
	this.passwordEncoder = passwordEncoder;
	this.userService = userService;
}

@PostMapping("/login")
public AuthResponse login(@RequestBody AuthRequest auth) {
	try {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(),auth.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwtToken = jwtTokenProvider.generateJwtToken(authentication);
		AuthResponse response = new AuthResponse();
		response.setCreated(true);
		response.setAccessToken("Bearer "+jwtToken);
		return response;
	}
	catch(Exception e) {
		AuthResponse response = new AuthResponse();
		response.setCreated(false);
		response.setError("Your Name Or Password Is Wrong Please Check Them");
		return response;
	}
	//return null;
	
}

@PostMapping("/register")
public AuthResponse register(@RequestBody AuthRequest auth) {
	AuthResponse response = new AuthResponse();
	if(userService.findByUserName(auth.getUsername())!=null) {
		response.setCreated(false);
		response.setError("This Name Is Already Taken Please Change Your Name");
		return response;
	}
	User user = new User();
	user.setUsername(auth.getUsername());
	user.setPassword(passwordEncoder.encode(auth.getPassword()));
	userService.save(user);
	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(auth.getUsername(),auth.getPassword());
	Authentication authManager = authenticationManager.authenticate(authToken);
	SecurityContextHolder.getContext().setAuthentication(authManager);
	String jwtToken = jwtTokenProvider.generateJwtToken(authManager);
	response.setCreated(true);
	response.setAccessToken("Bearer "+jwtToken);
	return response;
}

@GetMapping("/route")
public AuthResponse route() {
	AuthResponse authResponse = new AuthResponse();
	authResponse.setRoute("/");
	return authResponse;
	
}

}
