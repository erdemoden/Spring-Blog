package com.project.blog.requests;

import lombok.Data;

@Data
public class AuthRequestLogin {

	String mailOrEmail;
	String password;
}
