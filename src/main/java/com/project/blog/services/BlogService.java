package com.project.blog.services;

import com.project.blog.entities.Blogs;
import com.project.blog.entities.User;
import com.project.blog.repositories.BlogsRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.requests.BlogCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogsRepository blogsRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    public void saveBlog(BlogCreateRequest blogCreateRequest,String authorization){
        Optional<User> user = userService.getUserFromAuth(authorization);
        List<User> followers = new LinkedList<>();
        Blogs blogs = new Blogs();
        blogs.setSubject(blogCreateRequest.getSubject());
        blogs.setTitle(blogCreateRequest.getTitle());
        blogs.setOwner(user.get());
        followers.add(user.get());
        blogs.setFollowers(followers);
        blogsRepository.save(blogs);
    }
    public  void deleteBlog(long blogid){
        Blogs blogs = blogsRepository.findById(blogid).orElse(null);
        blogsRepository.delete(blogs);
    }
    public List<Blogs> findByUser(long userid){
        User user = userRepository.findById(userid).orElse(null);
        return user.getFollowerBlogs();
    }
}
