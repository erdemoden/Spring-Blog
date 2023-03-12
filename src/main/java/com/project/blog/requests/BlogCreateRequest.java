package com.project.blog.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BlogCreateRequest {

    @NotNull(message = "Title Field Is Required")
    @Size(max = 15,message = "Title Can Not Be More Than 15 Characters")
    private String title;

    @NotNull(message = "Subject Field Is Required")
    @Size(max = 350,message = "Subject Field Can Not Be More Than 350 Characters")
    private String subject;
    @NotNull(message = "Owner Of Blog Can Not Be Null")
    private long owner;
    @NotNull(message = "Follower Of The Blog Can Not Be Null")
    private long follower;
}
