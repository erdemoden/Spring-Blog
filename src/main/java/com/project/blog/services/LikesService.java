package com.project.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Likes;
import com.project.blog.entities.Posts;
import com.project.blog.entities.User;
import com.project.blog.repositories.LikesRepository;
import com.project.blog.repositories.PostsRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.requests.LikeCreateRequest;

@Service
public class LikesService {

	private PostsRepository postsRepository;
	private UserRepository userRepository;
	private LikesRepository likesRepository;
	
	
	@Autowired
	public LikesService(PostsRepository postsRepository, UserRepository userRepository, LikesRepository likesRepository) {
		this.postsRepository = postsRepository;
		this.userRepository = userRepository;
		this.likesRepository = likesRepository;
	}


	public Likes cretaOneLike(LikeCreateRequest likeReq) {
		User user = userRepository.findById(likeReq.getUserid()).orElse(null);
		Posts post = postsRepository.findById(likeReq.getPostid()).orElse(null);
		if(post == null || user == null) {
			return null;
		}
		Likes like = new Likes();
		like.setPosts(post);
		like.setUser(user);
		return likesRepository.save(like);
	}
	
	public void deleteLikeById(Long id) {
		likesRepository.deleteById(id);
	}
	
}
