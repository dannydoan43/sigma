package com.doan.sigma.service;

import java.util.ArrayList;
import java.util.List;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doan.sigma.dto.PostsDTO;
import com.doan.sigma.entity.Posts;
import com.doan.sigma.entity.Users;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.repository.PostsRepository;
import com.doan.sigma.repository.UsersRepository;

@Transactional
@Service
public class PostsServiceImpl implements PostsService {

	@Autowired
	private PostsRepository postsRepo;
	
	@Autowired
	private UsersRepository userRepo;
	//added in 		postToAdd.setTitle(postDTO.getTitle());
				//	postToAdd.setOwner(postDTO.getOwner()); to posts methods
	@Override
	public List<PostsDTO> getAllPosts() throws SubException {
		Iterable<Posts> posts = postsRepo.findAll();
		List<PostsDTO> postDTOs = new ArrayList<PostsDTO>();
		for(Posts post: posts) {
			PostsDTO dto = new PostsDTO();
			dto.setComments(post.getComments());
			dto.setCreatedAt(post.getCreatedAt());
			dto.setId(post.getId());
			dto.setLikeCount(post.getLikeCount());
			dto.setLiked(post.isLiked());
			dto.setText(post.getText());
			dto.setUsersEmail(post.getUsersEmail());
			dto.setTitle(post.getTitle());
			dto.setOwner(post.getOwner());
			postDTOs.add(dto);
		}
		return postDTOs;
	}
	
	//gotta go in and redo methods
	@Override
	public PostsDTO updatePost(PostsDTO postDTO) throws SubException {		//changed when i changed Integer id to CommentsId id
		Posts post = postsRepo.findById(postDTO.getId()).orElseThrow(()->new SubException("cant find post with id to update"));
		post.setLikeCount(postDTO.getLikeCount());
		post.setLiked(postDTO.isLiked());
		post.setText(postDTO.getText());
		post.setUsersEmail(postDTO.getUsersEmail());
		post.setTitle(postDTO.getTitle());
		post.setOwner(postDTO.getOwner());
//		List<Comments> currentComments = post.getComments();
		
		PostsDTO returnPost = new PostsDTO();
		returnPost.setCreatedAt(post.getCreatedAt());
		returnPost.setId(post.getId());
		returnPost.setLikeCount(post.getLikeCount());
		returnPost.setLiked(post.isLiked());
		returnPost.setText(post.getText());
		returnPost.setComments(post.getComments());
		returnPost.setUsersEmail(post.getUsersEmail());
		returnPost.setTitle(post.getTitle());
		returnPost.setOwner(post.getOwner());
		return returnPost; 
		
//		Posts post = postsRepo.findById(postDTO.getId()).orElseThrow(()->new SubException("could not find post with id to update"));
//		post.setLikeCount(postDTO.getLikeCount());
//		post.setLiked(postDTO.isLiked());
//		post.setText(postDTO.getText());
//		//post.setUsers_email(postDTO.getUsers_email());		//should they be able to change the email?
//		
////		List<Comments> currentComments = new ArrayList<Comments>();
////		for(Comments comment :post.getComments()) {			//do i even need this entire for each if i have line 66
////			//is this line below even needed if i do post.getComments()?
////			//Comments c = commentsRepo.findById(comment.getCommentsId().getId()).orElseThrow(()->new SubException("could not find commment id"));
////			Comments 
////			c.setCreatedAt(comment.getCreatedAt());
////			c.setCommentsId(comment.getCommentsId());
////			//c.setId(comment.getId());
////			c.setMessage(comment.getMessage());
////			//c.setPost(post);  			//???
//////			c.setPostId(postDTO.getId());
////			currentComments.add(c);
////		}
//		//post.setComments(currentComments);			//this line is currently breaking the update...do i technically need to do this if I am changing it inside the for loop?
//		//post.setId(postsId);		technically not setting an id because it is autogenerated
//		//postsRepo.save(post);
//		
//		PostsDTO returnPost = new PostsDTO();
//		returnPost.setCreatedAt(post.getCreatedAt());
//		returnPost.setId(post.getId());
//		returnPost.setLikeCount(post.getLikeCount());
//		returnPost.setLiked(post.isLiked());
//		returnPost.setText(post.getText());
////		returnPost.setUsers_email(post.getUsers_email());
//		returnPost.setUser(post.getUser());
//		returnPost.setComments(post.getComments());
//		return returnPost; 
	}

	@Override
	public PostsDTO deletePost(Integer postId) throws SubException {
		Posts post = postsRepo.findById(postId).orElseThrow(()->new SubException("could not find post with id to delete"));
		PostsDTO postDTO = new PostsDTO();
		postDTO.setCreatedAt(post.getCreatedAt());
		postDTO.setId(post.getId());
		postDTO.setLikeCount(post.getLikeCount());
		postDTO.setLiked(post.isLiked());
		postDTO.setText(post.getText());
		postDTO.setComments(post.getComments());
		postDTO.setUsersEmail(post.getUsersEmail());
		postDTO.setTitle(post.getTitle());
		postDTO.setOwner(post.getOwner());
		postsRepo.delete(post);
		return postDTO;
//		Posts post = postsRepo.findById(postId).orElseThrow(()->new SubException("could not find post with id to delete"));
//		PostsDTO postDTO = new PostsDTO();
//		postDTO.setCreatedAt(post.getCreatedAt());
//		postDTO.setId(post.getId());
//		postDTO.setLikeCount(post.getLikeCount());
//		postDTO.setLiked(post.isLiked());
//		postDTO.setText(post.getText());
////		postDTO.setUsers_email(post.getUsers_email());
//		postDTO.setComments(post.getComments());
//		
//		postsRepo.delete(post);
//		return postDTO;
	}
	
	@Override
	public Integer addPost(PostsDTO postDTO) throws SubException {
		//System.out.println("THIS IS EMAIL INSIDE POSTDTO : " + postDTO.getPostsId().getUsersEmail());
		Users user = userRepo.findById(postDTO.getUsersEmail().toLowerCase()).orElseThrow(()->new SubException("user not found to add post"));
		//Users user = userRepo.findByUsername(postDTO.getUsersEmail()).orElseThrow(()->new SubException("user not found to add post"));
		List<Posts> currentPosts = user.getPosts();
		Posts postToAdd = new Posts();
		
		postToAdd.setLikeCount(postDTO.getLikeCount());
		postToAdd.setLiked(postDTO.isLiked());
		postToAdd.setText(postDTO.getText());
		postToAdd.setUsersEmail(postDTO.getUsersEmail());
		postToAdd.setTitle(postDTO.getTitle());
		postToAdd.setOwner(postDTO.getOwner());
//		postToAdd.setPostsId(postDTO.getPostsId());
		//maybe just add in a String username to posts and comments?
		postsRepo.save(postToAdd);
		currentPosts.add(postToAdd);
		user.setPosts(currentPosts);

		return postToAdd.getId();
		//postDTO.getuseremail is null
//		Users user = userRepo.findById(email.toLowerCase()).orElseThrow(()->new SubException("user not found to add post"));
//		List<Posts> postsList = user.getPosts();
//		Posts newPost = new Posts();
//		newPost.setLikeCount(postDTO.getLikeCount());
//		newPost.setLiked(postDTO.isLiked());
//		newPost.setText(postDTO.getText());
////		newPost.setUsers_email(email.toLowerCase());
//		newPost.setUser(user); 		//???
//		newPost.setComments(postDTO.getComments());    	//this might potentially break program because it could be null
//		postsList.add(newPost);
//		
//		postsRepo.save(newPost);		//?
//		user.setPosts(postsList);
//		Users userSaved = userRepo.save(user);
//		List<Posts> postsAfterSave = userSaved.getPosts();
//		Posts post = postsAfterSave.get(postsAfterSave.size()-1);
//
//		return post.getId();
	}

	@Override
	public String likePost(Integer id) throws SubException {
		Posts post = postsRepo.findById(id).orElseThrow(()->new SubException("could not find post by id"));
		post.setLikeCount(post.getLikeCount()+1);
		return "liked count of post : " + post.getId() + " is now : " + post.getLikeCount();
//		Posts post = postsRepo.findById(id).orElseThrow(()->new SubException("could not find post by id"));
//		post.setLikeCount(post.getLikeCount()+1);
//		postsRepo.save(post);
//		//might have to delete the liked boolean...? makes more sense to include another many-to-many table for liked_by
//		return "increased likes count by 1, it is now " + post.getLikeCount();
	}
	
	public List<PostsDTO> getPostsByUser_email(String email) throws SubException {
		Users user = userRepo.findById(email.toLowerCase()).orElseThrow(()->new SubException("could not find post by email"));
		List<PostsDTO> postList = new ArrayList<>();
		//PostsDTO postDTO = new PostsDTO();		//place this inside the for each?
		
		
		
		//for each post it is copying the latest entry 
		for(Posts p : user.getPosts()) {	//it is correctly getting the amount of posts
			PostsDTO postDTO = new PostsDTO();
			postDTO.setCreatedAt(p.getCreatedAt());
//			postDTO.setPostsId(p.getPostsId());
			postDTO.setId(p.getId());
			postDTO.setLikeCount(p.getLikeCount());
			postDTO.setLiked(p.isLiked());
			postDTO.setText(p.getText());
			postDTO.setUsersEmail(p.getUsersEmail());
			postDTO.setTitle(p.getTitle());
			postDTO.setOwner(p.getOwner());
			//postDTO.setUsers_email(p.getUsers_email());	//it is correctly setting postDTO
//			postDTO.setUser(p.getUser());
			postDTO.setComments(p.getComments());
			
			postList.add(postDTO);
		}
		return postList;
	}
	
	@Override
	public PostsDTO getPost(Integer id) throws SubException {
		Posts post = postsRepo.findById(id).orElseThrow(()->new SubException("unable to find post by id"));
		PostsDTO postDTO = new PostsDTO();
		postDTO.setCreatedAt(post.getCreatedAt());
		postDTO.setId(post.getId());
		postDTO.setLikeCount(post.getLikeCount());
		postDTO.setLiked(post.isLiked());
		postDTO.setText(post.getText());
		postDTO.setComments(post.getComments());
		postDTO.setUsersEmail(post.getUsersEmail());
		postDTO.setTitle(post.getTitle());
		postDTO.setOwner(post.getOwner());
		return postDTO;
//		Posts post = postsRepo.findById(id).orElseThrow(()->new SubException("unable to find post by id"));
//		
//		PostsDTO postDTO = new PostsDTO();
//		postDTO.setCreatedAt(post.getCreatedAt());
//		postDTO.setId(post.getId());
//		postDTO.setLikeCount(post.getLikeCount());
//		postDTO.setLiked(post.isLiked());
//		postDTO.setText(post.getText());
//		//postDTO.setUsers_email(post.getUsers_email());
//		postDTO.setUser(post.getUser());
//		//post.getUser().getPosts()		//what happens when i set this to null?
//		postDTO.getUser().setPosts(null);
//		postDTO.setComments(post.getComments());
//		//postDTO.setUser(post.getUser());
//		
//		return postDTO;
	}
}