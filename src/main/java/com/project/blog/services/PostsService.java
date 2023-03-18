package com.project.blog.services;

import java.util.List;

import com.project.blog.entities.Blogs;
import com.project.blog.repositories.BlogsRepository;
import com.project.blog.requests.BlogCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Posts;
import com.project.blog.entities.User;
import com.project.blog.repositories.PostsRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.requests.PostCreateRequest;

@Service
@RequiredArgsConstructor
public class PostsService {

	private final PostsRepository postsRepository;
	private final UserRepository userRepository;
	private final BlogsRepository blogsRepository;

	public List<Posts> getAllPosts(){
		
		return postsRepository.findAll();
	}
	
	public Posts createOnePost(PostCreateRequest postReq) {
		
		User user = userRepository.findById(postReq.getUserid()).orElse(null);
		Blogs blogs = blogsRepository.findById(postReq.getBlogid()).orElse(null);
		if(user == null) {
			return null;
		}
		Posts post = new Posts();
		post.setPost(postReq.getPost());
		post.setTitle(postReq.getTitle());
		post.setUser(user);
		post.setBlogs(blogs);
		return postsRepository.save(post);
	
	}
	
	public void deletePostById(Long id) {
		postsRepository.deleteById(id);
	}
	
	
	
}
