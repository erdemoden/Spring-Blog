package com.project.blog.repositories;

import com.project.blog.entities.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsRepository extends JpaRepository<Blogs,Long> {

    Blogs findByTitle(String title);
}
