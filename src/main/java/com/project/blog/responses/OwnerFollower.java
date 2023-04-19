package com.project.blog.responses;

import lombok.Data;

@Data
public class OwnerFollower {

    boolean owner = false;
    boolean follower = false;
    boolean admin = false;
}
