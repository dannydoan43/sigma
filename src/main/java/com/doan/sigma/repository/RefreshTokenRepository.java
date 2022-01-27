package com.doan.sigma.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.doan.sigma.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,Long>{
	Optional<RefreshToken> findByToken(String token);
	
	void deleteByToken(String token);

}
