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

import com.doan.sigma.dto.UsersDTO;
import com.doan.sigma.entity.Users;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.service.UsersServiceImpl;

@RestController
@RequestMapping("users")
@Validated
@CrossOrigin
public class UsersAPI {

	@Autowired
	Environment env;
	
	@Autowired
	private UsersServiceImpl userService;
	
	@GetMapping("u/{username}")
	ResponseEntity<UsersDTO> findByUsername(@PathVariable String username) throws SubException {
		return new ResponseEntity<UsersDTO>(userService.findByUsername(username),HttpStatus.OK);
	}
	@GetMapping("e/{email}")
	ResponseEntity<UsersDTO> findByUsersEmail(@PathVariable String email) throws SubException{
		return new ResponseEntity<UsersDTO>(userService.findByUsersEmail(email), HttpStatus.OK);
	}
	@GetMapping("/all") 
	ResponseEntity<List<Users>> getAll() throws SubException{
		return new ResponseEntity<List<Users>>(userService.getAll(),HttpStatus.OK);
	}
	
	@PostMapping("/add")
	ResponseEntity<String> addUser(@Valid @RequestBody UsersDTO userDTO) throws SubException {
		return new ResponseEntity<String>(userService.addUser(userDTO),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{email}")
	ResponseEntity<String> deleteUser(@PathVariable String email) throws SubException {
		return new ResponseEntity<String>(userService.deleteUser(email),HttpStatus.OK);
	}
	
	@PutMapping("/update")
	ResponseEntity<String> updateUser(@Valid @RequestBody UsersDTO userDTO) throws SubException {
		return new ResponseEntity<String>(userService.updateUser(userDTO),HttpStatus.OK);
	}
	
}