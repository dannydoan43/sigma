package com.doan.sigma.dto;

import javax.validation.constraints.Email;
public class FollowersDTO {
	@Email
	private String users_email;
	
	@Email
	private String followers_email;

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
}