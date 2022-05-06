package com.doan.sigma.service;

import java.util.ArrayList;
import java.util.List;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doan.sigma.dto.CommentsDTO;
import com.doan.sigma.entity.Comments;
import com.doan.sigma.entity.Posts;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.repository.CommentsRepository;
import com.doan.sigma.repository.PostsRepository;

@Transactional
@Service
public class CommentsServiceImpl implements CommentsService{

	@Autowired
	private CommentsRepository commentsRepo;
	
	@Autowired
	private PostsRepository postsRepo;
	@Override
	public Integer addCommentToPost(CommentsDTO commentsDTO) throws SubException {

		Posts post = postsRepo.findById(commentsDTO.getPostsId()).orElseThrow(()->new SubException("cant find post with id to add comment"));
		List<Comments> currentComments = post.getComments();
		Comments commentToAdd = new Comments();
		commentToAdd.setId(commentsDTO.getId());
		commentToAdd.setPostsId(commentsDTO.getPostsId());
		commentToAdd.setUsername(commentsDTO.getUsername());

		commentToAdd.setMessage(commentsDTO.getMessage());
		
		commentsRepo.save(commentToAdd);
		currentComments.add(commentToAdd);
		post.setComments(currentComments);
		
		return commentToAdd.getId();
	}

	@Override
	public Integer deleteComment(Integer id) throws SubException {
		Comments comment = commentsRepo.findById(id).orElseThrow(()->new SubException("cant find comment id to delete"));
		Integer commentId = comment.getId();
		commentsRepo.delete(comment);
		return commentId;
		
	}

	@Override
	public Integer updateComment(CommentsDTO commentDTO) throws SubException {
		Comments comment = commentsRepo.findById(commentDTO.getId()).orElseThrow(()->new SubException("cant find comment with that key"));
		comment.setMessage(commentDTO.getMessage());
		
		return comment.getId();
	}

	@Override
	public List<CommentsDTO> getCommentsByOwner(String email) throws SubException {
		List<Comments> currentComments  = commentsRepo.findByUsername(email);
		List<CommentsDTO> commentsDTO = new ArrayList<CommentsDTO>();
		
		for(Comments c : currentComments) {
			CommentsDTO cDTO = new CommentsDTO();
			cDTO.setId(c.getId());
			cDTO.setPostsId(c.getPostsId());
			cDTO.setUsername(c.getUsername());

			cDTO.setCreatedAt(c.getCreatedAt());
			cDTO.setMessage(c.getMessage());

			commentsDTO.add(cDTO);
		}
		return commentsDTO;
	}

	@Override
	public List<CommentsDTO> getCommentsByPostId(Integer postsId) throws SubException {
		List<Comments> comments = commentsRepo.findByPostsId(postsId);
		List<CommentsDTO> commentsDTOs = new ArrayList<>();
		for(Comments comment: comments) {
			CommentsDTO commentsDTO = new CommentsDTO();
			commentsDTO.setId(comment.getId());
			commentsDTO.setPostsId(comment.getPostsId());
			commentsDTO.setUsername(comment.getUsername());

			commentsDTO.setCreatedAt(comment.getCreatedAt());
			commentsDTO.setMessage(comment.getMessage());

			commentsDTOs.add(commentsDTO);
		}
		
		return commentsDTOs;
	}

}