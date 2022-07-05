package com.project.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.blog.entities.User;
import com.project.blog.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	private UserService userService;
 
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	@GetMapping("{id}")
	public User userById(@PathVariable long id) {
		
		return userService.findById(id);
	}
	@GetMapping("/hello")
	public String merhaba() {
		return "Hello";
	}
	@GetMapping
	public List<User>getAllUsers(){
		return userService.getAllUsers();
	}
	
	@PostMapping("/save")
	public void saveUser(@RequestBody User user) {
		userService.save(user);
	}
	@GetMapping("/delete/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.deleteUserById(id);
	}

}
