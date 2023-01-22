package com.project.blog.services;

import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.blog.configs.RedisCacheStore;
import com.project.blog.entities.User;
import com.project.blog.requests.AuthRequest;
import com.project.blog.requests.AuthRequestLogin;
import com.project.blog.requests.MailKey;
import com.project.blog.responses.AuthResponse;
import com.project.blog.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {
	
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;
	private final UserService userService;
	private final RedisCacheStore redisCacheStore;
	private final EmailService emailService;
	
	public String createRandomString() {
		int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    
	    return generatedString;
	}
	public AuthResponse checkMail(MailKey key) {
		User user  = new User();
		AuthResponse response = new AuthResponse();
		if(redisCacheStore.get(key.getKey())!=null) {
			user  = (User) redisCacheStore.get(key.getKey());
			if(user == null) {
				response.setCreated(false);
				return response;
			}
			response.setCreated(true);
			return response;
		}
		response.setError("Your Code Is Wrong");
		response.setCreated(false);
		return response;
		
	}
	
	public AuthResponse loginSendMail(AuthRequestLogin auth) {
		User user = new User();
		boolean mailCheck = false;
		AuthResponse response = new AuthResponse();
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getMailOrEmail(),auth.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken = jwtTokenProvider.generateJwtToken(authentication);
			response.setCreated(true);
			response.setAccessToken("Bearer "+jwtToken);
			if(userService.findByEmail(auth.getMailOrEmail())!=null) {
				user = userService.findByEmail(auth.getMailOrEmail());
				String key = createMailKey(user);
				emailService.sendEmail(user.getEmail(),"Vertification Code","Your Vertification Code : "+key);
				createMailKey(user);
			}
			else {
				user = userService.findByUserName(auth.getMailOrEmail());
				String key = createMailKey(user);
				emailService.sendEmail(user.getEmail(),"Vertification Code","Your Vertification Code : "+key);
			}
			return response;
		}
		catch(Exception e) {
			response.setCreated(false);
			response.setError("Your Name Or Password Is Wrong Please Check Them");
			return response;
		}
	}
	
	public AuthResponse beforeRegisteration(AuthRequest auth) {
		AuthResponse response = new AuthResponse();
		if(userService.findByUserName(auth.getUsername())!=null) {
			response.setCreated(false);
			response.setError("This Name Is Already Taken Please Change Your Name");
			return response;
		}
		if(userService.findByEmail(auth.getEmail())!=null) {
			response.setCreated(false);
			response.setError("This Email Is Already Taken Please Change Your E-Mail");
			return response;
		}
		User user = new User();
		user.setEmail(auth.getEmail());
		user.setUsername(auth.getUsername());
		user.setPassword(auth.getPassword());
		String key = createMailKey(user);
		emailService.sendEmail(user.getEmail(),"Vertification Code","Your Vertification Code : "+key);
		response.setCreated(true);
		return response;
	}
	
	public AuthResponse registerWithMail(MailKey key) {
		User user = new User();
		User myUser = new User();
		AuthResponse response = new AuthResponse();
		if(redisCacheStore.get(key.getKey())!=null) {
			try {
			user  = (User) redisCacheStore.get(key.getKey());
			myUser.setUsername(user.getUsername());
			myUser.setEmail(user.getEmail());
			myUser.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.save(myUser);
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwtToken = jwtTokenProvider.generateJwtToken(authentication);
			response.setCreated(true);
			response.setAccessToken("Bearer "+jwtToken);
			return response;
			}
			catch (Exception e) {
				response.setCreated(false);
				response.setError("Your Name Or Password Is Wrong Please Check Them");
				return response;
			}
		}
		response.setCreated(false);
		response.setError("Your Mail Vertification Key Is Wrong");
		return response;
	}
	
	public String createMailKey(User user) {
		String randomString = createRandomString();
		redisCacheStore.put(randomString,user,90);
		return randomString;
	}
}
