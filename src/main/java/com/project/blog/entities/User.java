package com.project.blog.entities;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Data
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	 Long id;
	
	String username;
	String password;
	String email;
	String userphoto;
	@OneToMany(mappedBy="user",cascade = {CascadeType.REMOVE,CascadeType.ALL})
	List<Likes> likes;
	@OneToMany(mappedBy = "user",cascade = {CascadeType.ALL,CascadeType.REMOVE})
	@JsonIgnore
	List<Posts> posts;

	@OneToMany(mappedBy = "owner",cascade={CascadeType.ALL})
	@JsonIgnore
	List<Blogs> ownerBlogs;

	@ManyToMany(mappedBy = "admins",fetch = FetchType.LAZY,cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	List<Blogs> adminBlogs;

	@ManyToMany(mappedBy = "followers",fetch = FetchType.LAZY,cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})

	List<Blogs> followerBlogs;
}
 