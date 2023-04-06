package com.project.blog.configs;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        Map<String,String> config = new HashMap<>();
        config.put("cloud_name", "dbxchbci8");
        config.put("api_key", "853482939757412");
        config.put("api_secret", "beQiT4NT2yONoIca27DXOrm6EKs");
        Cloudinary cloudinary = new Cloudinary(config);
        return cloudinary;
    }
}
