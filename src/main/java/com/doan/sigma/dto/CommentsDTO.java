package com.doan.sigma.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

public class CommentsDTO {
	
	private Integer id;			//what to do here,.....how to get this to autogenerate a value?
	
	private Integer postsId;
	//this user is the user who commented
	private String usersEmail;
	
	@NotNull
	@Size(min=0,max=99)
	private String message;
	
	@PastOrPresent
	private Date createdAt;
	
	private String owner;
	
//	@NotNull
//	private Integer postsId;

	//private Posts post;
	
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

//	public Integer getPostsId() {
//		return postsId;
//	}
//
//	public void setPostsId(Integer postId) {
//		this.postsId = postId;
//	}

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
}
