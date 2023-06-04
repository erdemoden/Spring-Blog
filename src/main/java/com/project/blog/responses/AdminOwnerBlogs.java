package com.project.blog.responses;

import com.project.blog.entities.Blogs;
import lombok.Data;

import java.util.List;

@Data
public class AdminOwnerBlogs {
    private List<Blogs> adminBlogs;
    private List<Blogs> ownerBlogs;
}
