package com.project.blog.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Table(name = "posts")
@Data
public class Posts {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	String title;
	
	@Lob
	@Column(columnDefinition = "text")
	String post;
	
	@ManyToOne(cascade= {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="userid")
	User user;
	
	@OneToMany(mappedBy = "posts",cascade = {CascadeType.ALL})
	
	List<Likes> likes;
	
}
 