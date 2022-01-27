package com.doan.sigma.dto;

import javax.validation.constraints.NotBlank;

public class RefreshTokenRequest {	//DTO
	@NotBlank
	private String refreshToken;
	private String username;
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}