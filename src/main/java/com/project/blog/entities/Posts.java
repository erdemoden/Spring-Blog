package com.project.blog.entities;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Data
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;

	@Lob
	@Column(columnDefinition = "LONGTEXT")
	String post;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "userid")
	@JsonIgnore
	User user;

	@OneToMany(mappedBy = "posts", cascade = {CascadeType.ALL})
	@JsonIgnore
	List<Likes> likes;

	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "blogid")
	@JsonIgnore
	Blogs blogs;

	@OneToMany(mappedBy ="posts",cascade = {CascadeType.ALL})
	@JsonIgnore
	List<Comments> comments;

}
 