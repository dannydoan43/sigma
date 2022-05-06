package com.doan.sigma.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.doan.sigma.utility.FollowersId;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="users_has_followers")
public class Followers {
	@EmbeddedId
	private FollowersId followersId;
	public Followers() {
    }
	
	public Followers(FollowersId followersId,Users userOne, Users userTwo) {
		this.followersId = followersId;
	}

	public FollowersId getFollowersId() {
		return followersId;
	}

	public void setFollowersId(FollowersId followers) {
		this.followersId = followers;
	}

}