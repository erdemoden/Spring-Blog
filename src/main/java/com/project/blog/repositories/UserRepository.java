package com.project.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.blog.entities.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findByEmail(String email);

	@Query(value = "Select * from user where username like %:username%",nativeQuery = true)
	List<User> findByUsernameLike(@Param("username") String username);

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.userphoto = :userpic where u.id = :id ")
	void updatePhoto(@Param("userpic") String userpic,@Param("id") long id);

	@Query(value = "Select * from user e where TIMESTAMPDIFF(MINUTE, e.blocked, :compare) >= 1",nativeQuery = true)
	List<User> findBlockedUsers(@Param("compare")LocalDateTime compare);

} 
