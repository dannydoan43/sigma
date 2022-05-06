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
	
	public RefreshToken generateRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setCreatedDate(Instant.now());	//can also use @CreationTimeStamp
		refreshToken.setToken(UUID.randomUUID().toString());
		RefreshToken returnVal = refreshTokenRepo.save(refreshToken);
		return returnVal;
	}
	
	public void validateRefreshToken(String token) throws SubException {
		refreshTokenRepo.findByToken(token).orElseThrow(()-> new SubException("invalid refresh token"));
	}

	public void deleteRefreshToken(String token) throws SubException {
		refreshTokenRepo.findByToken(token).orElseThrow(()-> new SubException("invalid refresh token"));
		refreshTokenRepo.deleteByToken(token);
	}
}
