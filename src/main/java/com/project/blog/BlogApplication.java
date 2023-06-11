package com.project.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class BlogApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(BlogApplication.class);
		Properties properties = new Properties();
		if(System.getenv("BLOG_APP_FRONT")!=null && !System.getenv("BLOG_APP_FRONT").isEmpty()){
			properties.put("blog.app.front",System.getenv("BLOG_APP_FRONT"));
			application.setDefaultProperties(properties);
			application.run(args);
		}
		else{
			properties.put("blog.app.front","http://localhost:3000");
			application.setDefaultProperties(properties);
			application.run(args);
		}
		//SpringApplication.run(BlogApplication.class, args);
	}

}
