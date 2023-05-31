package com.project.blog.controllers;

import java.util.List;

import com.project.blog.responses.ErrorSuccessResponse;
import com.project.blog.responses.PostCreatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@PostMapping("/save")
	public PostCreatedResponse savePost(@RequestBody PostCreateRequest postReq, @RequestHeader String Authorization) {
		return postsService.createOnePost(postReq,Authorization);
	}
	@GetMapping("/postsOfUser")
	public List<Posts> postsOfUsers(@RequestHeader String Authorization ){
	return postsService.listPostsOfUser(Authorization);
	}
	@GetMapping
	public List<Posts> getAllPosts(){
		return postsService.getAllPosts();
	}
	
	@GetMapping("/delete/{id}")
	public ErrorSuccessResponse deletepost(@PathVariable Long id) {
		return postsService.deletePostById(id);
	}
}
