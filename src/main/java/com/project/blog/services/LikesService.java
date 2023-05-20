package com.project.blog.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Likes;
import com.project.blog.entities.Posts;
import com.project.blog.entities.User;
import com.project.blog.repositories.LikesRepository;
import com.project.blog.repositories.PostsRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.requests.LikeCreateRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {

	private final PostsRepository postsRepository;
	private final UserRepository userRepository;
	private final LikesRepository likesRepository;
	private final UserService userService;
	



	public int cretaOneLike(LikeCreateRequest likeReq,String authorization) {
		User user = userService.getUserFromAuth(authorization).orElse(null);
		Posts post = postsRepository.findById(likeReq.getPostid()).orElse(null);
		if(post == null || user == null) {
			return 0;
		}
		Likes like = new Likes();
		like.setPosts(post);
		like.setUser(user);
		likesRepository.save(like);
		return getLikes(likeReq.getPostid());
	}
	public boolean isLikedByUser(long postId,String authorization){
		User user = userService.getUserFromAuth(authorization).orElse(null);
		Posts posts = postsRepository.findById(postId).orElse(null);
		return likesRepository.isUserLiked(posts,user)!=0;
	}
	public int getLikes(long postId){
		Posts post = postsRepository.findById(postId).orElse(null);
		if(post == null) {
			return 0;
		}
		return likesRepository.countLikesWithPosts(post);
	}
	public void deleteLikeById(long postId,String authorization) {
		User user = userService.getUserFromAuth(authorization).orElse(null);
		Posts posts = postsRepository.findById(postId).orElse(null);
		Likes likes = likesRepository.getLikeByUserAndPost(posts,user);
	}
	
}
