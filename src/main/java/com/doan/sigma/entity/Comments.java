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
	@Column(name="users_email")
	//@ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)		//do not need this?
	private String usersEmail;
	
	@Column(name="message")
	private String message;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
//	
	@Column(name="owner")
	private String owner;
//	@Column(name="posts_id")
////	private Integer postsId;
//	@ManyToOne
//	@JoinColumn(name="users_email")
//	//@JoinColumn(name="i",referencedColumnName="")
//	private Posts post;
//	@ManyToOne
//	@JoinColumn(name="posts_id",nullable=false)
//	@JsonBackReference
//	private Posts post;
	//comments have a user?
	//constructor?
	public Comments() {
		super();
	}
//	
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

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

	public String getUsersEmail() {
		return usersEmail;
	}

	public void setUsersEmail(String usersEmail) {
		this.usersEmail = usersEmail;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

//	public Posts getPost() {
//		return post;
//	}
//
//	public void setPost(Posts post) {
//		this.post = post;
//	}

//	public CommentsId getCommentsId() {
//		return commentsId;
//	}
//
//	public void setCommentsId(CommentsId commentsId) {
//		this.commentsId = commentsId;
//	}

//	public Integer getPostId() {
//		return postsId;
//	}
//
//	public void setPostId(Integer postId) {
//		this.postsId = postId;
//	}
}