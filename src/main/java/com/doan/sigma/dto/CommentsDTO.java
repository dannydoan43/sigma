package com.doan.sigma.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

public class CommentsDTO {
	
	private Integer id;
	
	private Integer postsId;
	@NotNull
	private String username;
	
	@NotNull
	@Size(min=0,max=99)
	private String message;
	
	@PastOrPresent
	private Date createdAt;
	

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
