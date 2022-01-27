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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doan.sigma.entity.Followers;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.service.FollowersServiceImpl;

@RestController
@RequestMapping("follower")
@Validated
@CrossOrigin
public class FollowersAPI {

	@Autowired
	Environment env;
	
	@Autowired
	private FollowersServiceImpl followerService;

	@DeleteMapping("/delete")
	ResponseEntity<String> deleteFollower(@Valid @RequestBody Followers id) throws SubException {
		return new ResponseEntity<String>(followerService.deleteFollower(id), HttpStatus.OK);
	}
	
	@PostMapping("/add")
	ResponseEntity<String> addFollowerTo(@Valid @RequestBody Followers id) throws SubException {
		return new ResponseEntity<String>(followerService.addFollowerTo(id),HttpStatus.CREATED);
	}
	
	@GetMapping("following/{email}")
	ResponseEntity<List<String>> getFollowing(@PathVariable String email) throws SubException {
		return new ResponseEntity<List<String>>(followerService.getFollowing(email),HttpStatus.OK);
	}
	
	@GetMapping("followers/{email}")
	ResponseEntity<List<String>> getFollowersOf(@PathVariable String email) throws SubException {
		return new ResponseEntity<List<String>>(followerService.getFollowersOf(email),HttpStatus.OK);
	}
	
	@GetMapping("/all")
	ResponseEntity<Followers> getAll(@Valid @RequestBody Followers follower) throws SubException {
		return new ResponseEntity<Followers>(followerService.getAll(follower),HttpStatus.OK);
	}
}