package com.project.blog.responses;

import com.project.blog.DTOS.FollowedBlogs;
import com.project.blog.entities.Blogs;
import lombok.Data;

import java.util.List;

@Data
public class AuthResponse {
	boolean isCreated;
	String accessToken;
	String error;
	String route;
	String username;
	String location;
	List<FollowedBlogs> followedblogs;
	boolean isBlocked;
}
