package com.project.blog.DTOS;

import com.project.blog.entities.Comments;
import lombok.Data;

import java.util.List;

@Data
public class PostLikeId {
    private long id;
    private String post;
    private long likes;
    private String userName;
    private String userPhoto;
    private List<Comments> comments;
}
