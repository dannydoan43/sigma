package com.doan.sigma.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Table(name="users")
@Entity
public class Users {
	//add in setEnabled?
	//changed id to email
	@Id
	@Column(name="email")
	private String email;
	
    @CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at", updatable = false)
	private Date createdAt;		//will convert camelCase to snake_case
	@Column(name="description")
	private String description;
	@Column(name="followers_count")
	private int followersCount;
//	
//	@Column(name="friends_count")
//	private int friendsCount;
//	
	@Column(name="enabled")
	private boolean enabled;
	
//	@Column(name="first_last_name")	//commenting out getters and setters 
//	private String firstLastName;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_at")
	private Date loginAt;
	@Column(name="password")
	private String password;
	@Column(name="username")
	private String username;
	
	@OneToMany(mappedBy="usersEmail",cascade = CascadeType.ALL, orphanRemoval = true)		//joincolumn is slower performance, but mappedBy is a bidirectional relationship
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name="users_username")
	private List<Posts> posts;	//@JsonManagedReference
	
	@OneToMany(mappedBy="usersEmail",cascade = CascadeType.ALL, orphanRemoval = true)	//swapped to joiincolumn test
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name="users_username",referencedColumnName="username")			//i thionk it might be this
	private List<Comments> comments;
//	@OneToMany(cascade=CascadeType.ALL,orphanRemoval = true)		//changed from line 58 to 55
//	@JoinColumn(name="users_email")
//	private List<Posts> posts;
	

	public void addPost(Posts post) {
		posts.add(post);
		post.setUsersEmail(this.email);
	}
	public void deletePost(Posts post) {
		posts.remove(post);
		post.setUsersEmail(null);
	}
	
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
//
//	public void setFriendsCount(int friendsCount) {
//		this.friendsCount = friendsCount;
//	}

//	public String getFirstLastName() {
//		return firstLastName;
//	}
//
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

//	public List<Followers> getFollowed() {			//changed from followers to users
//		return followed;
//	}
//
//	public void setFollowed(List<Followers> followed) {
//		this.followed = followed;
//	}

//	public List<Users> getFollowers() {
//		return followers;
//	}
//
//	public void setFollowers(List<Users> followers) {
//		this.followers = followers;
//	}

	
	
}