package com.project.blog.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.project.blog.responses.PictureResponse;
import com.project.blog.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.blog.entities.User;
import com.project.blog.repositories.UserRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

	private UserRepository userRepository;
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	public UserService(UserRepository userRepository, JwtTokenProvider tokenProvider) {
		this.userRepository = userRepository;
		this.jwtTokenProvider = tokenProvider;
	}
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public void save(User user) {
			userRepository.save(user);
	} 
	
	public User findById(long id) {
		
		return userRepository.findById(id).orElse(null);
	}
	
	public User findByUserName(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public void deleteUserById(long id) {
		try {
		userRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			System.out.println("merhaba");
		}
	}
	public PictureResponse saveUserPic(MultipartFile userpic, String Authorization){
		String path = System.getProperty("user.dir") + "/";
		PictureResponse pictureResponse = new PictureResponse();
		String bearer = extractJwtFromString(Authorization);
		long id = jwtTokenProvider.getUserIdFromJwt(bearer);
		Optional<User> user = userRepository.findById(id);
		String fileName = user.get().getUsername();
		String extension = userpic.getContentType().toString().substring(userpic.getContentType().toString().lastIndexOf("/")+1);
		Path saveTo = Paths.get(path,fileName+"."+extension);
		try {
			Files.copy(userpic.getInputStream(),saveTo);
			userRepository.updatePhoto(String.valueOf(saveTo),id);
			pictureResponse.setPicPath(saveTo.toString());
			return pictureResponse;
		}
		catch (IOException e) {
			pictureResponse.setError("Something Went Wrong");
			return pictureResponse;
		}
	}
	public FileSystemResource getFile(String location){
		try {
			return new FileSystemResource(Paths.get(location));
		} catch (Exception e) {
			// Handle access or file not found problems.
			throw new RuntimeException();
		}
	}
	public void checkMail(String mail) {
		
	}

	private String extractJwtFromString(String bearer) {
		if(StringUtils.hasText(bearer)&& bearer.startsWith("Bearer ")) {
			return bearer.substring("Bearer".length()+1);
		}
		return null;
	}

}
