package com.doan.sigma.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.doan.sigma.entity.Comments;

@Repository
public interface CommentsRepository extends CrudRepository<Comments,Integer> {
	
	List<Comments> findByUsername(@Param("username") String username);
	
	//@Query("SELECT c FROM Comments as C WHERE c.commentsId.postsId = :postsId")
	List<Comments> findByPostsId(@Param("postsId") Integer postsId);
	//List<Comments> findByPost(Integer postId);
}