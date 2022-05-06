package com.doan.sigma.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doan.sigma.dto.PostsDTO;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.service.PostsServiceImpl;

@RestController
@RequestMapping("posts")
@Validated
@CrossOrigin
public class PostsAPI {

	@Autowired
	Environment env;
	
	@Autowired
	private PostsServiceImpl postsService;
	
	@GetMapping("/all")
	ResponseEntity<List<PostsDTO>> getAllPosts() throws SubException {
		return new ResponseEntity<List<PostsDTO>>(postsService.getAllPosts(),HttpStatus.OK);
	}
	
	@GetMapping("/getUser/{username}")
	ResponseEntity<List<PostsDTO>> getPostsByUsername(@PathVariable String username) throws SubException {
		return new ResponseEntity<List<PostsDTO>>(postsService.getPostsByUsername(username),HttpStatus.OK);
	}
	
	@GetMapping("/{email}")
	ResponseEntity<List<PostsDTO>> getPostsByUser_email(@PathVariable String email) throws SubException {
		return new ResponseEntity<List<PostsDTO>>(postsService.getPostsByUser_email(email),HttpStatus.OK);
	}

	@PutMapping("/update")
	ResponseEntity<PostsDTO> updatePost(@Valid @RequestBody PostsDTO postDTO) throws SubException {
		return new ResponseEntity<PostsDTO>(postsService.updatePost(postDTO),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{postId}")
	ResponseEntity<PostsDTO> deletePost(@PathVariable Integer postId) throws SubException {
		return new ResponseEntity<PostsDTO>(postsService.deletePost(postId),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	ResponseEntity<Integer> addPosts(@Valid @RequestBody PostsDTO postDTO) throws SubException {
		return new ResponseEntity<Integer>(postsService.addPost( postDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/like/{postId}")
	ResponseEntity<String> likePost(@PathVariable Integer postId) throws SubException {
		return new ResponseEntity<String>(postsService.likePost(postId),HttpStatus.OK);
	}
	
	@GetMapping("/get/{id}")
	ResponseEntity<PostsDTO> getPosts(@PathVariable Integer id) throws SubException {
		return new ResponseEntity<PostsDTO>(postsService.getPost(id),HttpStatus.OK);
	}
}