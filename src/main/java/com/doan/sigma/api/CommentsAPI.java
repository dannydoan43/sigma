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

import com.doan.sigma.dto.CommentsDTO;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.service.CommentsServiceImpl;

@RestController
@RequestMapping("comments")
@Validated
@CrossOrigin
public class CommentsAPI {
	@Autowired
	Environment env;
	
	@Autowired
	private CommentsServiceImpl commentsService;
	
	@GetMapping("/getC/{postsId}")
	ResponseEntity<List<CommentsDTO>> getCommentsByPostId(@PathVariable Integer postsId) throws SubException {
		return new ResponseEntity<List<CommentsDTO>>(commentsService.getCommentsByPostId(postsId),HttpStatus.OK);
	}
	
	@GetMapping("/getUser/{username}")
	ResponseEntity<List<CommentsDTO>> getCommentsByUsername(@PathVariable String username) throws SubException {	
		return new ResponseEntity<List<CommentsDTO>>(commentsService.getCommentsByOwner(username),HttpStatus.OK);
	}
	
	@PostMapping("/addC")	//removed postsid from add
	ResponseEntity<Integer> addCommentToPost(@Valid @RequestBody CommentsDTO commentsDTO) throws SubException {
		return new ResponseEntity<Integer>(commentsService.addCommentToPost(commentsDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	ResponseEntity<Integer> updateComment(@Valid @RequestBody CommentsDTO commentsDTO) throws SubException {
		return new ResponseEntity<Integer>(commentsService.updateComment(commentsDTO),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")	//might have to change back to pathvariable if you get a null value from putting in json
	ResponseEntity<Integer> deleteComment(@PathVariable Integer id) throws SubException {
		return new ResponseEntity<Integer>(commentsService.deleteComment(id),HttpStatus.OK);
	}
}