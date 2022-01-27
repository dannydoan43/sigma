package com.doan.sigma.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.doan.sigma.entity.Comments;

public class PostsDTO {

	@NotNull
	private int id;
//	private PostsId postsId;
	
	@PastOrPresent
	private Date createdAt;
	
	@NotNull
	private int likeCount;
	
	@NotNull
	private boolean liked;
	
	@Size(min=0,max=99)
	private String text;
	
	private String usersEmail;
	//private Users user;
	private String title;
	private String owner;

	private List<Comments> comments;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

//	public String getUsers_email() {
//		return users_email;
//	}
//
//	public void setUsers_email(String users_email) {
//		this.users_email = users_email;
//	}

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
//
//	public Users getUser() {
//		return user;
//	}
//
//	public void setUser(Users user) {
//		this.user = user;
//	}
//
//	public PostsId getPostsId() {
//		return postsId;
//	}
//
//	public void setPostsId(PostsId postsId) {
//		this.postsId = postsId;
//	}

	public String getUsersEmail() {
		return usersEmail;
	}

	public void setUsersEmail(String usersEmail) {
		this.usersEmail = usersEmail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
