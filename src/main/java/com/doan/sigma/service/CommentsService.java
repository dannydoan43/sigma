package com.doan.sigma.service;

import java.util.List;

import com.doan.sigma.dto.CommentsDTO;
import com.doan.sigma.exception.SubException;


public interface CommentsService {
	public Integer addCommentToPost(CommentsDTO commentDTO) throws SubException;
	public Integer deleteComment(Integer id) throws SubException;
	public Integer updateComment(CommentsDTO commentDTO) throws SubException;
	public List<CommentsDTO> getCommentsByOwner(String email) throws SubException;
	public List<CommentsDTO> getCommentsByPostId(Integer postsId) throws SubException;
}
