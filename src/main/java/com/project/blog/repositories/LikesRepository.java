package com.project.blog.repositories;

import com.project.blog.entities.Posts;
import com.project.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.blog.entities.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query(value = "SELECT COUNT(postid) FROM likes where postid =:posts",nativeQuery = true)
    int countLikesWithUSer(@Param("posts") Posts posts);
}
 