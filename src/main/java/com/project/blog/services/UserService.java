package com.project.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.project.blog.entities.User;
import com.project.blog.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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
	
	public void deleteUserById(long id) {
		try {
		userRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			System.out.println("merhaba");
		}
	}
}
