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

@Table(name="posts")
@Entity
public class Posts {
//removing owner from entity and dto ---- changing users_email to users_username 
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
	@Column(name="users_email")
//	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Users.class)		//just added this line
	private String usersEmail;			//might have to change this to users user? danny??? @manytoone @joincolumn(name="username", referencedColumnName="users_username")
	//giving a reference to users would expose the email!!!!!!!!!!!!!!!!!!!!
	
	
	
	@Column(name="title")
	private String title;
							
	@Column(name="owner")
	private String owner;
//	
	
//	@ManyToOne
//	@JoinColumn(name="users_email",nullable=false)
//	@JsonBackReference
//	private Users user;			//connected to Users line 59?	changed String users_email to Users user

	@OneToMany(mappedBy="postsId",cascade = CascadeType.ALL, orphanRemoval = true)
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name="posts_id")
	private List<Comments> comments;		//its this line
	
	//constructor?
	public Posts() {
		super();
	}
	
//	public void addComment(Comments comment) {
//		comments.add(comment);
//		comment.setPostsId(this.id);
//	}
//	public void deleteComment(Comments comment) {
//		comments.remove(comment);
//		comment.setPostsId(null);
//	}
	
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

//	public Users getUser() {
//		return user;
//	}
//
//	public void setUser(Users user) {
//		this.user = user;
//	}

//	public Users getUser() {
//		return user;
//	}
//
//	public void setUser(Users user) {
//		this.user = user;
//	}
	
	
}