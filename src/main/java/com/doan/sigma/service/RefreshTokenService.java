package com.doan.sigma.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.sigma.entity.RefreshToken;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.repository.RefreshTokenRepository;

@Service
@Transactional
public class RefreshTokenService {
	
	@Autowired
	private RefreshTokenRepository refreshTokenRepo;
	//give refreshtoken a expiration date
	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setCreatedDate(Instant.now());	//can probably just use @CreationTimeStamp
		refreshToken.setToken(UUID.randomUUID().toString());
		RefreshToken returnVal = refreshTokenRepo.save(refreshToken);	//id should be autogen
		return returnVal;
	}
	//refresh token should delete when getting a new one
	public void validateRefreshToken(String token) throws SubException {	//make this private or public?
		refreshTokenRepo.findByToken(token).orElseThrow(()-> new SubException("invalid refresh token"));
	}

	public void deleteRefreshToken(String token) throws SubException {	//does not actually log a person out...
		refreshTokenRepo.findByToken(token).orElseThrow(()-> new SubException("invalid refresh token"));
		refreshTokenRepo.deleteByToken(token);
	}
}
