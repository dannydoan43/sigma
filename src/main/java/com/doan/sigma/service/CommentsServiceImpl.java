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
	//can delete Integer postsId because the DTO has it
	@Override
	public Integer addCommentToPost(CommentsDTO commentsDTO) throws SubException {
		//what is commentsDTO.getpostsid().getid()?
		//System.out.println("THIS IS POSTID INSIDE COMMENTSDTO : " + commentsDTO.getCommentsId().getPostsId());
		//Posts post = postsRepo.findById(postsId).orElseThrow(()->new SubException("cant find post to add comment"));
//		Posts post = null;
//		post = postsRepo.findByPostsIdId(postsId);
//		if(post==null) throw new SubException("could not find post by id");
		Posts post = postsRepo.findById(commentsDTO.getPostsId()).orElseThrow(()->new SubException("cant find post with id to add comment"));
		List<Comments> currentComments = post.getComments();
		Comments commentToAdd = new Comments();
//		commentToAdd.setCommentsId(commentsDTO.getCommentsId());
		commentToAdd.setId(commentsDTO.getId());
		commentToAdd.setPostsId(commentsDTO.getPostsId());
		commentToAdd.setUsersEmail(commentsDTO.getUsersEmail());
		commentToAdd.setMessage(commentsDTO.getMessage());
		commentToAdd.setOwner(commentsDTO.getOwner());
		
		commentsRepo.save(commentToAdd);
		currentComments.add(commentToAdd);
		post.setComments(currentComments);	//need to save before add to get an id
		
		return commentToAdd.getId();		//maybe return commentsId instead?
		//commentsDTO.getPostId() is null
//		Posts post= postsRepo.findById(postsId).orElseThrow(()->new SubException("post id not found to add comment"));
//		List<Comments> currentComments = post.getComments();
//		Comments comment = new Comments();
//		comment.setMessage(commentsDTO.getMessage());
////		comment.setPostId(commentsDTO.getPostsId());
//		//comment.setPost(post);
//		comment.setCommentsId(commentsDTO.getCommentsId());
//		
//		commentsRepo.save(comment);
//		currentComments.add(comment);
//		post.setComments(currentComments);
//		Posts postSaved = postsRepo.save(post);			//do i need this?
//		List<Comments> commentsAfterSave = postSaved.getComments();
//		Comments msg = null;
//		if(commentsAfterSave.size() > 1) {
//			msg = commentsAfterSave.get(commentsAfterSave.size()-1);
//		}
//		else {
//			msg = commentsAfterSave.get(commentsAfterSave.size());
//		}
//		return msg.getCommentsId().getId();
		//return msg.getId();
	}

	@Override	//should make it so they can't delete a comment unless their email matches
	public Integer deleteComment(Integer id) throws SubException {
		Comments comment = commentsRepo.findById(id).orElseThrow(()->new SubException("cant find comment id to delete"));
		Integer commentId = comment.getId();
		commentsRepo.delete(comment);
		return commentId;
		
//		Comments comment = commentsRepo.findById(commentId).orElseThrow(()->new SubException("comment not found to delete"));
//		commentsRepo.delete(comment);
//		return comment.getCommentsId().getId();
		//return comment.getId();
	}

	//took out Integer commentId from update
	@Override
	public Integer updateComment(CommentsDTO commentDTO) throws SubException {
		Comments comment = commentsRepo.findById(commentDTO.getId()).orElseThrow(()->new SubException("cant find comment with that key"));
		comment.setMessage(commentDTO.getMessage());
		
//		Comments comment = commentsRepo.findById(commentId).orElseThrow(()->new SubException("comment not found to update"));
//		comment.setMessage(commentDTO.getMessage());
//		//comment.setPost(commentDTO.getPost());		//post inside DTO is null
////		comment.setPostId(commentDTO.getPostsId());			//do i really want to allow them to change postid?
//		return comment.getCommentsId().getId();
		//return comment.getId();
		return comment.getId();
	}

	@Override
	public List<CommentsDTO> getCommentsByOwner(String email) throws SubException {	//Integer commentId to String email
//		Users user = usersRepo.findById(email.toLowerCase()).orElseThrow(()->new SubException("no user with that email"));	//do i even need this?
		List<Comments> currentComments  = commentsRepo.findByUsersEmail(email);
		List<CommentsDTO> commentsDTO = new ArrayList<CommentsDTO>();
		
		for(Comments c : currentComments) {
			CommentsDTO cDTO = new CommentsDTO();
//			cDTO.setCommentsId(c.getCommentsId());
			cDTO.setId(c.getId());
			cDTO.setPostsId(c.getPostsId());
			cDTO.setUsersEmail(c.getUsersEmail());
			cDTO.setCreatedAt(c.getCreatedAt());
			cDTO.setMessage(c.getMessage());
			cDTO.setOwner(c.getOwner());

			commentsDTO.add(cDTO);
		}
		
		return commentsDTO;
//		Comments comment = commentsRepo.findById(commentId).orElseThrow(()->new SubException("could not find comment"));
		
		
		
//		//Posts post = postsRepo.findById(comment.getPost().getId()).orElseThrow(()-> new SubException("could not find comments by postid"));
//		
//		UsersDTO userDTO = new UsersDTO();
//		userDTO.setCreatedAt(post.getUser().getCreatedAt());
//		userDTO.setDescription(post.getUser().getDescription());
//		userDTO.setEmail(post.getUser().getEmail());
//		userDTO.setFirstLastName(post.getUser().getFirstLastName());
//		userDTO.setFollowersCount(post.getUser().getFollowersCount());
//		userDTO.setFriendsCount(post.getUser().getFriendsCount());
//		userDTO.setLoginAt(post.getUser().getLoginAt());
//		userDTO.setPassword(post.getUser().getPassword());
//		userDTO.setUsername(post.getUser().getUsername());
//		
//		
//		
//		List<Comments> comments = post.getComments();
////		List<Comments> comments = commentsRepo.findByPost(postsId);
//		if(comments.isEmpty()) throw new SubException("no comments");
//		
//		List<CommentsDTO> commentsDTO = new ArrayList<>();
//		for(Comments msg : comments) {
//			CommentsDTO commentDTO = new CommentsDTO();
//			commentDTO.setCreatedAt(msg.getCreatedAt());
//			commentDTO.setId(msg.getId());
//			commentDTO.setMessage(msg.getMessage());
////			commentDTO.setPostsId(msg.getPostId());
//			commentDTO.setPost(msg.getPost());
//			
//			commentsDTO.add(commentDTO);
//		}
		
//		return commentsDTO;
//		return null;
	}

	@Override
	public List<CommentsDTO> getCommentsByPostId(Integer postsId) throws SubException {		//change IntegerpostsId to CommentsId commentsId
		List<Comments> comments = commentsRepo.findByPostsId(postsId);
//		List<Comments> comments = commentsRepo.findByCommentsIdPostsId(commentsId.getPostsId());
		List<CommentsDTO> commentsDTOs = new ArrayList<>();
		for(Comments comment: comments) {
			CommentsDTO commentsDTO = new CommentsDTO();
//			commentsDTO.setCommentsId(comment.getCommentsId());
			commentsDTO.setId(comment.getId());
			commentsDTO.setPostsId(comment.getPostsId());
			commentsDTO.setUsersEmail(comment.getUsersEmail());
			commentsDTO.setCreatedAt(comment.getCreatedAt());
			commentsDTO.setMessage(comment.getMessage());
			commentsDTO.setOwner(comment.getOwner());

			//add posts?
			commentsDTOs.add(commentsDTO);
		}
		
		return commentsDTOs;
//		Posts post = postsRepo.findById(postsId).orElseThrow(()-> new SubException("could not find comments by postid"));
//		List<Comments> comments = post.getComments();
//		if(comments.isEmpty()) throw new SubException("no comments");
//		
//		
//		List<CommentsDTO> commentsDTO = new ArrayList<>();
//		for(Comments msg : comments) {
//			CommentsDTO commentDTO = new CommentsDTO();
//			commentDTO.setCreatedAt(msg.getCreatedAt());
//			commentDTO.setCommentsId(msg.getCommentsId());
//			//commentDTO.setId(msg.getId());
//			commentDTO.setMessage(msg.getMessage());
//			//commentDTO.setPost(msg.getPost());		//if i do not set this-post will be null
//			
//			commentsDTO.add(commentDTO);
//		}
//		return commentsDTO;
		
	}

}