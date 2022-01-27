package com.doan.sigma.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doan.sigma.entity.Followers;
import com.doan.sigma.utility.FollowersId;

@Repository
public interface FollowersRepository extends CrudRepository<Followers,FollowersId> {

	@Query("SELECT f FROM Followers f WHERE f.followersId.users_email = :oldEmail OR f.followersId.followers_email = :oldEmail")
	List<Followers> findAllByOldEmail(@Param("oldEmail") String oldEmail);
	
	@Query("SELECT f.followersId.followers_email FROM Followers f WHERE f.followersId.users_email = :email")
	List<String> findAllFollowersByEmail(@Param("email") String email);
	
	@Query("SELECT f.followersId.users_email FROM Followers f WHERE f.followersId.followers_email = :email")
	List<String> findAllFollowingByEmail(@Param("email") String email);
}