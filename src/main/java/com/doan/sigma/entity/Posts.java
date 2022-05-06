package com.doan.sigma.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Table(name="posts")
@Entity
public class Posts {
//removing owner from entity and dto ---- changing users_email to users_username 
	//changing usersEmail to username
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date createdAt;
	
	@Column(name="like_count")
	private int likeCount;
	
	@Column(name="liked")
	private boolean liked;
	
	@Column(name="text")
	private String text;
		//the only thing i did was go from users_email to users_username and change column to joincolumn with manytoone
	@Column(name="users_username")
//	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)		//just added this line
	private String username;			//might have to change this to users user? danny??? @manytoone @joincolumn(name="username", referencedColumnName="users_username")
	//giving a reference to users would expose the email!!!!!!!!!!!!!!!!!!!!
	
	
	
	@Column(name="title")
	private String title;

	@OneToMany(mappedBy="postsId",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private List<Comments> comments;		//its this line
	
	//constructor?
	public Posts() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public List<Comments> getComments() {
		return comments;
	}

	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}