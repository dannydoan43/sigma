package com.doan.sigma.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Table(name="users")
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@JsonIgnoreProperties({ "followers","following","posts","comments" })	
public class Users implements Serializable {

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

	@Column(name="enabled")
	private boolean enabled;
	
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
							
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="users_username",referencedColumnName="username")
	@JsonManagedReference
	private List<Posts> posts;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="users_username",referencedColumnName="username")
	private List<Comments> comments;
	
	@JoinTable(name="users_has_followers",
			joinColumns= {@JoinColumn(name="users_email",insertable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name="followers_email",insertable = false, updatable = false)})
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Users> followers;
	
	@JoinTable(name="users_has_followers",
			joinColumns= {@JoinColumn(name="followers_email",insertable = false, updatable = false)},
			inverseJoinColumns = {@JoinColumn(name="users_email",insertable = false, updatable = false)})
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Users> following;

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

	public List<Users> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Users> followers) {
		this.followers = followers;
	}

	public List<Users> getFollowing() {
		return following;
	}

	public void setFollowing(List<Users> following) {
		this.following = following;
	}

	
}