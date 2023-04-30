package com.project.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.blog.entities.Posts;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query(value = "SELECT * FROM Posts where Posts.user = :user",nativeQuery = true)
    List<Posts> postsFromUser(@Param("user") String user);
} 
