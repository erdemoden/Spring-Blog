package com.project.blog.responses;

import com.project.blog.DTOS.FollowedBlogs;
import lombok.Data;

import java.util.List;

@Data
public class OwnerFollower {

    boolean owner = false;
    boolean follower = false;
    boolean admin = false;
    String error;
    List<FollowedBlogs> adminBlogs;
    List<FollowedBlogs> ownerBlogs;
}
