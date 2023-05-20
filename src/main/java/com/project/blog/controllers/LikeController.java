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
	
	
	@PostMapping("/create")
	public int saveLike(@RequestBody LikeCreateRequest likeReq,@RequestHeader String Authorization) {
		return likesService.cretaOneLike(likeReq,Authorization);
	}

	@GetMapping("/getlikes")
	public int getLikes(@RequestParam long postid){
		return likesService.getLikes(postid);
	}
	@GetMapping("/delete/{postId}")
	public void deleteLike(@PathVariable("postId") Long postId,@RequestHeader String Authorization) {
		likesService.deleteLikeById(postId,Authorization);
	}
	@GetMapping("/isuserliked")
	public boolean isUserLiked(@RequestParam long postId,@RequestHeader String Authorization){
		return likesService.isLikedByUser(postId,Authorization);
	}
	
	
}
