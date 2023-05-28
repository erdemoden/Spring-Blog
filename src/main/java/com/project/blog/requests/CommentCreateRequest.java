package com.project.blog.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentCreateRequest {
    long postId;
    @NotNull(message = "Title Field Is Required")
    @Size(min=1,max = 150,message = "Comment Should Be MAX 150 Characters and MIN 1 Characters ")
    String comment;
}
