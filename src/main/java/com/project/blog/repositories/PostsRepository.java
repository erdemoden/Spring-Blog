package com.project.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.entities.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long> {

} 
