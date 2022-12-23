package com.project.blog.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.configs.RedisCacheStore;
import com.project.blog.requests.AuthRequest;
import com.project.blog.requests.MailKey;
import com.project.blog.responses.AuthResponse;
import com.project.blog.services.AuthService;
import com.project.blog.services.EmailService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
private final EmailService emailService;
private final AuthService authService;
private final RedisCacheStore redisCacheStore;

@PostMapping("/beforelogin")
public AuthResponse beforeLogin(@RequestBody AuthRequest auth) {
	return authService.beforeLogin(auth);
	
}
@PostMapping("loginwithmail")
public AuthResponse loginWithMail(@RequestBody MailKey key) {
	return authService.loginWithMail(key);
}
@PostMapping("/beforeregister")
public AuthResponse beforeRegister(@RequestBody AuthRequest auth) {
	return authService.beforeRegisteration(auth);
}

@PostMapping("/registerwithmail")
public AuthResponse registerWithMail(@RequestBody MailKey key) {
	return authService.registerWithMail(key);
}

@GetMapping("/route")
public AuthResponse route() {
	AuthResponse authResponse = new AuthResponse();
	authResponse.setRoute("/");
	return authResponse;
	
}

@GetMapping("/deneme")
public void deneme() {
	redisCacheStore.put("merhaba", "deneme", 60);
	System.out.println(redisCacheStore.get("merhaba"));
	emailService.sendEmail("erdemoden5@gmail.com","Vertification Code","bsdcncbjsfrp");
	
}

}
