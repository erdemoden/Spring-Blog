package com.project.blog.repositories;

import com.project.blog.entities.Posts;
import com.project.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.blog.entities.Likes;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query(value = "SELECT COUNT(postid) FROM likes where postid =:posts",nativeQuery = true)
    int countLikesWithPosts(@Param("posts") Posts posts);

    @Query(value = "Select * from likes where postid=:posts and userid=:user",nativeQuery = true)
    Likes getLikeByUserAndPost(@Param("posts") Posts posts, @Param("user") User user);

    @Query(value = "select case when exists(select * from likes where userid=:user and postid=:posts) then true else false end ",nativeQuery = true)
    int isUserLiked(@Param("posts") Posts posts, @Param("user") User user);
}
 