package com.project.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.entities.Likes;

public interface LikesRepository extends JpaRepository<Likes, Long> {

}
 