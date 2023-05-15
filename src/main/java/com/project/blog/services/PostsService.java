package com.project.blog.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.project.blog.entities.Blogs;
import com.project.blog.repositories.BlogsRepository;
import com.project.blog.requests.BlogCreateRequest;
import com.project.blog.responses.PostCreatedResponse;
import com.project.blog.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.entities.Posts;
import com.project.blog.entities.User;
import com.project.blog.repositories.PostsRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.requests.PostCreateRequest;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PostsService {

	private final PostsRepository postsRepository;
	private final UserRepository userRepository;
	private final BlogsRepository blogsRepository;
	private  final JwtTokenProvider jwtTokenProvider;

	public List<Posts> getAllPosts(){
		
		return postsRepository.findAll();
	}
	
	public PostCreatedResponse createOnePost(PostCreateRequest postReq, String Authorization) {
		PostCreatedResponse postCreatedResponse = new PostCreatedResponse();
		Optional<User> user = getUserFromAuth(Authorization);
		Blogs blogs = blogsRepository.findByTitle(postReq.getBlogTitle());
		if(user == null || blogs == null) {
			postCreatedResponse.setError("There is No Such An User Or Blog");
			return postCreatedResponse;
		}
		Posts post = new Posts();
		post.setPost(postReq.getPost());
		post.setUser(user.orElse(null));
		post.setBlogs(blogs);
		postsRepository.save(post);
		postCreatedResponse.setMessage("You Can See Your Post On Your Profile Screen");
		return postCreatedResponse;
	}
	public List<Posts> listPostsOfUser(String Authorization){
		Optional<User> user = getUserFromAuth(Authorization);
		List<Posts> posts = postsRepository.postsFromUser(user.get().getUsername());
		return posts;
	}
	public List<Posts> listPostsOfBlogs(long blogid){
		Optional<Blogs> blogs = blogsRepository.findById(blogid);
		List<Posts> posts = postsRepository.postsWithBlog(blogs.get().getTitle());
		return posts;
	}
	public Optional<User> getUserFromAuth(String Authorization){
		String bearer = extractJwtFromString(Authorization);
		long id = jwtTokenProvider.getUserIdFromJwt(bearer);
		Optional<User> user = userRepository.findById(id);
		return user;
	}
	public void deletePostById(Long id) {
		postsRepository.deleteById(id);
	}

	private String extractJwtFromString(String bearer) {
		if(StringUtils.hasText(bearer)&& bearer.startsWith("Bearer ")) {
			return bearer.substring("Bearer".length()+1);
		}
		return null;
	}
	
	
	
}
