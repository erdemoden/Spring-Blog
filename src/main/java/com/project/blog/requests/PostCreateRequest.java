package com.project.blog.requests;

import lombok.Data;

@Data
public class PostCreateRequest {

	Long id;
	String title;
	String post;
	Long userid;
	Long blogid;
}
