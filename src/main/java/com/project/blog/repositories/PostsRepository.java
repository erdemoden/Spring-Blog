package com.project.blog.repositories;

import com.project.blog.entities.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.blog.entities.Posts;

import java.util.List;
import java.util.Optional;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query(value = "SELECT * FROM posts where posts.userid = :user",nativeQuery = true)
    List<Posts> postsFromUser(@Param("user") String user);

    @Query(value = "select * from posts  where posts.blogs = :blog",nativeQuery = true)
    List<Posts> postsWithBlog(@Param("blog") String blog);

} 
