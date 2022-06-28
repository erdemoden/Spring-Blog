package com.project.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.entities.Posts;
import com.project.blog.requests.PostCreateRequest;
import com.project.blog.services.PostsService;

@RestController
@RequestMapping("/post")
public class PostsController {

	private PostsService postsService;
	 
	
	@Autowired
	public PostsController(PostsService postsService) {
		
		this.postsService = postsService;
	}

	@PostMapping
	public Posts savePost(@RequestBody PostCreateRequest postReq) {
		
		return postsService.createOnePost(postReq);
		
	}
	
	@GetMapping
	public List<Posts> getAllPosts(){
		return postsService.getAllPosts();
	}
	
	@GetMapping("/delete/{id}")
	public void deletepost(@PathVariable Long id) {
		postsService.deletePostById(id);
	}
}
