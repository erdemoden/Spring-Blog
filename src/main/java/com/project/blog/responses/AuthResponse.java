package com.project.blog.responses;

import lombok.Data;

@Data
public class AuthResponse {
	boolean isCreated;
	String accessToken;
	String error;
	String route;
	String username;
	String location;
}
