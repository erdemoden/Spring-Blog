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

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogsRepository blogsRepository;
    private final UserRepository userRepository;
    public void saveBlog(BlogCreateRequest blogCreateRequest){
        User user = userRepository.findById(blogCreateRequest.getOwner()).orElse(null);
        List<Blogs> followerblogs = null;
        List<User> followers = new LinkedList<>();
        followerblogs = user.getFollowerBlogs();
        Blogs blogs = new Blogs();
        blogs.setSubject(blogCreateRequest.getSubject());
        blogs.setTitle(blogCreateRequest.getTitle());
        blogs.setOwner(user);
        followerblogs.add(blogs);
        user.setFollowerBlogs(followerblogs);
        followers.add(user);
        blogs.setFollowers(followers);
        userRepository.save(user);
    }
    public  void deleteBlog(long blogid){
        Blogs blogs = blogsRepository.findById(blogid).orElse(null);
        blogsRepository.delete(blogs);
    }
}
