package com.project.blog.controllers;

import java.util.List;
import java.util.Map;

import com.project.blog.DTOS.FollowedBlogs;
import com.project.blog.entities.Blogs;
import com.project.blog.responses.OwnerFollower;
import com.project.blog.responses.PictureResponse;
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
	@GetMapping("isOwner")
	public OwnerFollower isOwner(@RequestParam String username,@RequestParam String title){
		return userService.isOwner(username,title);
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
	public PictureResponse saveUserPic(@RequestParam MultipartFile userpic, @RequestHeader String Authorization){
		return userService.saveUserPic(userpic,Authorization);
		//System.out.println(userpic.getContentType().toString().substring(userpic.getContentType().toString().lastIndexOf("/")+1));
	}
	@GetMapping(value ="/getphoto",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public String getFile(@RequestParam String location){
		return userService.getFile(location);
	}
	@GetMapping("/getusersphoto")
	public PictureResponse getUsersPhoto(@RequestParam String username){
		return userService.getUsersPhoto(username);
	}
	@GetMapping("/checkpicture")
	public PictureResponse checkPicture(@RequestHeader String Authorization){
		String check = userService.checkPicture(Authorization);
		PictureResponse pictureResponse = new PictureResponse();
		if(check.equals("error")) {
			pictureResponse.setError(check);
			return pictureResponse;
		}
		pictureResponse.setPicPath(check);
		return pictureResponse;
	}
	@GetMapping("/getfollowedblog")
	public List<FollowedBlogs>getFollowedBlogs(@RequestHeader String Authorization){
		return userService.getFollowedBlogs(Authorization);
	}
}
