package com.project.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.project.blog.entities.User;
import com.project.blog.services.UserService;
import org.springframework.web.multipart.MultipartFile;

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
		userService.checkMail(user.getEmail());
		//userService.save(user);
	}
	
	@GetMapping("/delete/{id}")
	public void deleteUser(@PathVariable long id) {
		userService.deleteUserById(id);
	}

	@PostMapping("/userpic")
	public void saveUserPic(@RequestParam MultipartFile userpic,@RequestHeader String Authorization){
		userService.saveUserPic(userpic,Authorization);
		//System.out.println(userpic.getContentType().toString().substring(userpic.getContentType().toString().lastIndexOf("/")+1));
	}
	@GetMapping(value ="/getphoto",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public FileSystemResource getFile(@RequestParam String location){
		return userService.getFile(location);
	}

}
