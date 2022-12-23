package com.project.blog.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
	@OneToMany(mappedBy="user",cascade = {CascadeType.REMOVE,CascadeType.ALL})
	List<Likes> likes;
	
	@OneToMany(mappedBy = "user",cascade = {CascadeType.ALL,CascadeType.REMOVE})
	List<Posts> posts;
}
 