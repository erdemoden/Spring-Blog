package com.project.blog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Posts;
import com.project.blog.entities.User;
import com.project.blog.repositories.PostsRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.requests.PostCreateRequest;

@Service
public class PostsService {

	private PostsRepository postsRepository;
	private UserRepository userRepository;

	@Autowired
	public PostsService(PostsRepository postsRepository,UserRepository userRepository) {
		this.postsRepository = postsRepository;
		this.userRepository = userRepository;
	} 
	
	public List<Posts> getAllPosts(){
		
		return postsRepository.findAll();
	}
	
	public Posts createOnePost(PostCreateRequest postReq) {
		
		User user = userRepository.findById(postReq.getUserid()).orElse(null);
		
		if(user == null) {
			return null;
		}
		Posts post = new Posts();
		post.setPost(postReq.getPost());
		post.setTitle(postReq.getTitle());
		post.setUser(user);
		return postsRepository.save(post);
	
	}
	
	public void deletePostById(Long id) {
		postsRepository.deleteById(id);
	}
	
	
	
}
