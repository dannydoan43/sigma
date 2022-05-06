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
			dto.setUsername(post.getUsername());
			dto.setTitle(post.getTitle());
			postDTOs.add(dto);
		}
		return postDTOs;
	}
	
	@Override
	public PostsDTO updatePost(PostsDTO postDTO) throws SubException {		//changed when i changed Integer id to CommentsId id
		Posts post = postsRepo.findById(postDTO.getId()).orElseThrow(()->new SubException("cant find post with id to update"));
		post.setLikeCount(postDTO.getLikeCount());
		post.setLiked(postDTO.isLiked());
		post.setText(postDTO.getText());
		post.setUsername(postDTO.getUsername());
		post.setTitle(postDTO.getTitle());
		
		PostsDTO returnPost = new PostsDTO();
		returnPost.setCreatedAt(post.getCreatedAt());
		returnPost.setId(post.getId());
		returnPost.setLikeCount(post.getLikeCount());
		returnPost.setLiked(post.isLiked());
		returnPost.setText(post.getText());
		returnPost.setComments(post.getComments());
		returnPost.setUsername(post.getUsername());

		returnPost.setTitle(post.getTitle());
		return returnPost; 
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
		postDTO.setUsername(post.getUsername());

		postDTO.setTitle(post.getTitle());
		postsRepo.delete(post);
		return postDTO;
	}
	
	@Override
	public Integer addPost(PostsDTO postDTO) throws SubException {
		Users user = userRepo.findByUsername(postDTO.getUsername()).orElseThrow(()->new SubException("user not found to add post"));
		List<Posts> currentPosts = user.getPosts();
		Posts postToAdd = new Posts();
		
		postToAdd.setLikeCount(postDTO.getLikeCount());
		postToAdd.setLiked(postDTO.isLiked());
		postToAdd.setText(postDTO.getText());
		postToAdd.setUsername(postDTO.getUsername());

		postToAdd.setTitle(postDTO.getTitle());
		postsRepo.save(postToAdd);
		currentPosts.add(postToAdd);
		user.setPosts(currentPosts);

		return postToAdd.getId();
	}

	@Override
	public String likePost(Integer id) throws SubException {
		Posts post = postsRepo.findById(id).orElseThrow(()->new SubException("could not find post by id"));
		post.setLikeCount(post.getLikeCount()+1);
		return "liked count of post : " + post.getId() + " is now : " + post.getLikeCount();
	}
	public List<PostsDTO> getPostsByUsername(String username) throws SubException {
		List<Posts> posts = postsRepo.findByUsername(username);
		List<PostsDTO> returnList = new ArrayList<PostsDTO>();
		for(Posts p : posts) {
			PostsDTO postDTO = new PostsDTO();
			postDTO.setCreatedAt(p.getCreatedAt());
			postDTO.setId(p.getId());
			postDTO.setLikeCount(p.getLikeCount());
			postDTO.setLiked(p.isLiked());
			postDTO.setText(p.getText());
			postDTO.setUsername(p.getUsername());
			postDTO.setTitle(p.getTitle());
			returnList.add(postDTO);
		}
		return returnList;
	}
	
	public List<PostsDTO> getPostsByUser_email(String email) throws SubException {
		Users user = userRepo.findById(email.toLowerCase()).orElseThrow(()->new SubException("could not find post by email"));
		List<PostsDTO> postList = new ArrayList<>();

		for(Posts p : user.getPosts()) {
			PostsDTO postDTO = new PostsDTO();
			postDTO.setCreatedAt(p.getCreatedAt());
			postDTO.setId(p.getId());
			postDTO.setLikeCount(p.getLikeCount());
			postDTO.setLiked(p.isLiked());
			postDTO.setText(p.getText());
			postDTO.setUsername(p.getUsername());

			postDTO.setTitle(p.getTitle());
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
		postDTO.setUsername(post.getUsername());

		postDTO.setTitle(post.getTitle());
		return postDTO;
	}
}