package com.project.blog.responses;

import lombok.Data;

@Data
public class CommentCreateResponse {
    private String comment;

    private String username;

    private String userPhoto;

}
