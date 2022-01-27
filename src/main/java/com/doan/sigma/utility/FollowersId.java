package com.doan.sigma.utility;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class FollowersId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	//might wanna delete this
	@Column(name="users_email")			
	private String users_email;
	@Column(name="followers_email")
	private String followers_email;
	
	public FollowersId() {

	}
	
	public FollowersId(String users_email, String followers_email) {
		this.followers_email=followers_email;
		this.users_email=users_email;
	}
	
	public String getUsers_email() {
		return users_email;
	}
	public void setUsers_email(String users_email) {
		this.users_email = users_email;
	}
	public String getFollowers_email() {
		return followers_email;
	}
	public void setFollowers_email(String followers_email) {
		this.followers_email = followers_email;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
//just added these two
	@Override
	public int hashCode() {
		return Objects.hash(followers_email, users_email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FollowersId other = (FollowersId) obj;
		return Objects.equals(followers_email, other.followers_email) && Objects.equals(users_email, other.users_email);
	}
	
	
}