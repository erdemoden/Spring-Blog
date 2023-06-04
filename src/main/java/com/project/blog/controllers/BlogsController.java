package com.project.blog.controllers;

import com.project.blog.entities.Blogs;
import com.project.blog.requests.BlogCreateRequest;
import com.project.blog.responses.ErrorSuccessResponse;
import com.project.blog.responses.FindBlogsByTitle;
import com.project.blog.responses.UserBlogLike;
import com.project.blog.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
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
    @GetMapping("/findbytitle")
    public FindBlogsByTitle findByTitle(@RequestParam String title){
        return blogService.findByTitle(title);
    }
    @GetMapping("/createadmin")
    public ErrorSuccessResponse createAdmin(@RequestParam long blogid,@RequestHeader String Authorization,@RequestParam String adminname){
        return blogService.createAdmin(blogid,Authorization,adminname);
    }
    @GetMapping("/removeadmin")
    public ErrorSuccessResponse removeAdmin(@RequestParam long blogid,@RequestHeader String Authorization,@RequestParam String adminname){
       return blogService.deleteAdmin(blogid,Authorization,adminname);
    }
    @GetMapping("/followblog")
    public ErrorSuccessResponse followBlog(@RequestParam long blogid,@RequestHeader String Authorization){
        return blogService.followblog(blogid,Authorization);
    }
    @GetMapping("/unfollowblog")
    public ErrorSuccessResponse unFollowBlog(@RequestParam long blogid,@RequestHeader String Authorization){
        return blogService.unfollowblog(blogid,Authorization);
    }
}
