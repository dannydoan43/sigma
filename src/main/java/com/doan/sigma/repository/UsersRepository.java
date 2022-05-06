package com.doan.sigma.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doan.sigma.entity.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users,String> {
	Optional<Users> findByUsername(@Param("username") String username);
}
