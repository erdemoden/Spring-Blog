package com.project.blog.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BlogCreateRequest {

    @NotNull(message = "Title Field Is Required")
    @Size(min=1,max = 15,message = "Title Should Be MAX 15 Characters and MIN 1 Characters ")
    private String title;

    @NotNull(message = "Subject Field Is Required")
    @Size(min=1,max = 350,message = "Subject Field Can Not Be More Than 350 Characters And Can Not Be 0 Character")
    private String subject;
}
