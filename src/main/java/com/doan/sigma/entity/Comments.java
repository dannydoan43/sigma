package com.doan.sigma.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Table
@Entity
public class Comments {
	//changing users_email to users_username and removing owner from comments entity and dto
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;			//what to do here,.....how to get this to autogenerate a value?
	
	@Column(name="posts_id")	//changed column to joincolumn 
	//@ManyToOne(fetch = FetchType.LAZY, targetEntity = Posts.class)	//needs target entity because i do not have type posts post but just the id
	private Integer postsId;
	//this user is the user who commented
	@Column(name="users_username")
	//@ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)		//do not need this?
	private String username;
	
	@Column(name="message")
	private String message;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;

	public Comments() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPostsId() {
		return postsId;
	}

	public void setPostsId(Integer postsId) {
		this.postsId = postsId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}