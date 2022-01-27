package com.doan.sigma.repository;

import org.springframework.data.repository.CrudRepository;

import com.doan.sigma.entity.Posts;

public interface PostsRepository extends CrudRepository<Posts,Integer>{

//	Posts findByPostsIdId(@Param("id") Integer id);
}