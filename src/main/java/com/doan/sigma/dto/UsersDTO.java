package com.doan.sigma.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import com.doan.sigma.entity.Comments;
import com.doan.sigma.entity.Posts;
//going to have to add setComments(getComments()) in the get requests

//added @notempty
public class UsersDTO {
	@Email
	@NotNull
	@NotEmpty
	private String email;
	@PastOrPresent
	private Date createdAt;
	@Size(min=0,max=44)
	private String description;
	@NotNull
	private int followersCount;
	private boolean enabled;
//	@NotNull
//	private int friendsCount;
	//@Pattern(regexp="/^[a-z ,.'-]+$/i")
	//@Size(min=0,max=44)
//	private String firstLastName;
	private String firstName;
	private String lastName;
	@PastOrPresent
	private Date loginAt;
	@NotNull
	@NotEmpty
	private String password;		//can add a pattern here for password requirements
	@NotNull
	@NotEmpty
	private String username;
	
	private List<Posts> posts;
	private List<Comments> comments;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getFollowersCount() {
		return followersCount;
	}
	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}
//	public int getFriendsCount() {
//		return friendsCount;
//	}
//	public void setFriendsCount(int friendsCount) {
//		this.friendsCount = friendsCount;
//	}
//	public String getFirstLastName() {
//		return firstLastName;
//	}
//	public void setFirstLastName(String firstLastName) {
//		this.firstLastName = firstLastName;
//	}
	public Date getLoginAt() {
		return loginAt;
	}
	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<Posts> getPosts() {
		return posts;
	}
	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}