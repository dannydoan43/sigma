package com.doan.sigma.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.doan.sigma.utility.FollowersId;

@Entity
@Table(name="users_has_followers")
public class Followers {
	@EmbeddedId
	private FollowersId followersId;
	//now can do findByIdFollowerEmail or findByIdUsersEmail
	//do i need these Users?
	@ManyToOne
	@JoinColumn(name="users_email", insertable=false, updatable=false)
	private Users userOne;
	
	@ManyToOne
	@JoinColumn(name="followers_email",insertable=false, updatable=false)
	private Users userTwo;
	
	public Followers() {
    }
	
	public Followers(FollowersId followersId,Users userOne, Users userTwo) {
		this.followersId = followersId;
		this.userOne = userTwo;
		this.userTwo = userOne;
		
		//do i need this?
//		userOne.getFollowers().add(this);
//		userTwo.getFollowed().add(this);
	}

	public FollowersId getFollowersId() {
		return followersId;
	}

	public void setFollowersId(FollowersId followers) {
		this.followersId = followers;
	}

	public Users getUserOne() {
		return userOne;
	}

	public void setUserOne(Users userOne) {
		this.userOne = userOne;
	}

	public Users getUserTwo() {
		return userTwo;
	}

	public void setUserTwo(Users userTwo) {
		this.userTwo = userTwo;
	}
}