package com.project.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Likes saveLike(@RequestBody LikeCreateRequest likeReq) {
		
		return likesService.cretaOneLike(likeReq);
		
	}
	@GetMapping("/delete/{id}")
	public void deleteLike(@PathVariable Long id) {
		likesService.deleteLikeById(id);
	}
	
	
}
