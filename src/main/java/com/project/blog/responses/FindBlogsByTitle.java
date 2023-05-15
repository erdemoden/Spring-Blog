package com.project.blog.responses;

import com.project.blog.DTOS.PostLikeId;
import lombok.Data;

import java.util.List;

@Data
public class FindBlogsByTitle {
    private long id;
    private String title;
    private String subject;
    private List<PostLikeId> postLikeIdList;
}
