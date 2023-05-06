package com.project.blog.controllers;

import com.project.blog.entities.Blogs;
import com.project.blog.requests.BlogCreateRequest;
import com.project.blog.responses.UserBlogLike;
import com.project.blog.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blogs")
public class BlogsController {
    private final BlogService blogService;
    @PostMapping("/save")
    public Blogs saveBlogs(@RequestBody @Valid BlogCreateRequest blogCreateRequest,@RequestHeader String Authorization){
        return blogService.saveBlog(blogCreateRequest,Authorization);
    }
    @GetMapping("/delete")
    public void deleteBlogs(@RequestParam long id){
        blogService.deleteBlog(id);
    }

    @GetMapping("/getblogs")
    public List<Blogs> blogsWithUserId(@RequestParam long userid){
        return blogService.findByUser(userid);
    }
    @GetMapping("/userbloglike")
    public List<UserBlogLike> findByUserAndBlogLike(@RequestParam String name){
        return blogService.findByUserAndBlogLike(name);
    }
}
