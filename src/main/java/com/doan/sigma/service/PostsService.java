package com.doan.sigma.service;

import java.util.List;

import com.doan.sigma.dto.PostsDTO;
import com.doan.sigma.exception.SubException;

public interface PostsService {
	public List<PostsDTO> getAllPosts() throws SubException;	//probably change return ty[e
	public PostsDTO updatePost(PostsDTO postDTO) throws SubException;
	public PostsDTO deletePost(Integer postId)	throws SubException;
	public Integer addPost(PostsDTO postDTO) throws SubException;
	public String likePost(Integer id) throws SubException;
	public PostsDTO getPost(Integer id) throws SubException;
	public List<PostsDTO> getPostsByUser_email(String email) throws SubException;
}