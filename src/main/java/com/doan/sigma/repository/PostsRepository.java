package com.doan.sigma.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.doan.sigma.entity.Posts;

public interface PostsRepository extends CrudRepository<Posts,Integer>{

	List<Posts> findByUsername(@Param("username") String username);
}