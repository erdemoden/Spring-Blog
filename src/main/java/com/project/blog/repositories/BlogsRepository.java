package com.project.blog.repositories;

import com.project.blog.entities.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogsRepository extends JpaRepository<Blogs,Long> {

    Blogs findByTitle(String title);

    @Query(value = "Select * from blogs where title like %:title%",nativeQuery = true)
    List<Blogs> findByTitleLike(@Param("title") String title);
}
