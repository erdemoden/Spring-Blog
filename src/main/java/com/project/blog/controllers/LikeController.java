package com.project.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.project.blog.entities.Likes;
import com.project.blog.entities.Posts;
import com.project.blog.requests.LikeCreateRequest;
import com.project.blog.requests.PostCreateRequest;
import com.project.blog.services.LikesService;

@RestController
@RequestMapping("/like")
public class LikeController {

	LikesService likesService;

	@Autowired
	public LikeController(LikesService likesService) {
		this.likesService = likesService;
	}
	
	
	@PostMapping
	public int saveLike(@RequestBody LikeCreateRequest likeReq) {
		
		return likesService.cretaOneLike(likeReq);
		
	}

	@GetMapping("/getlikes")
	public int getLikes(@RequestParam long postid){
		return likesService.getLikes(postid);
	}
	@GetMapping("/delete/{id}")
	public void deleteLike(@PathVariable Long id) {
		likesService.deleteLikeById(id);
	}
	
	
}
