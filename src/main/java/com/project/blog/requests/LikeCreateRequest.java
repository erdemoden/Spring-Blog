package com.project.blog.requests;

import lombok.Data;

@Data
public class LikeCreateRequest {

	Long id;
	Long userid;
	Long postid;
}
