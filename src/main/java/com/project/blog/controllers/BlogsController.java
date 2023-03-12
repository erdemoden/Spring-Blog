package com.project.blog.controllers;

import com.project.blog.entities.Blogs;
import com.project.blog.requests.BlogCreateRequest;
import com.project.blog.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/blogs")
public class BlogsController {
    private final BlogService blogService;
    @PostMapping("/save")
    public void saveBlogs(@RequestBody @Valid BlogCreateRequest blogCreateRequest){
        blogService.saveBlog(blogCreateRequest);
    }
    @GetMapping("/delete")
    public void deleteBlogs(@RequestParam long id){
        blogService.deleteBlog(id);
    }
}
