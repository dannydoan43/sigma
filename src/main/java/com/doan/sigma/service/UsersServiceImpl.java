package com.doan.sigma.service;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doan.sigma.dto.UsersDTO;
import com.doan.sigma.entity.Posts;
import com.doan.sigma.entity.Users;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.repository.UsersRepository;

@Service
@Transactional
public class UsersServiceImpl implements UsersService,UserDetailsService{

	@Autowired
	private UsersRepository userRepo;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> userOptional = userRepo.findByUsername(username);
		Users user = userOptional.orElseThrow(()->new UsernameNotFoundException("could not find user with username"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.isEnabled(),true,true,true,getAuthorities("USER"));
	}
	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}

	@Override
	public List<Users> getAll() throws SubException {
		return (List<Users>) userRepo.findAll();
	}
	
	@Override
	public UsersDTO findByUsername(String username) throws SubException{
		Optional<Users> userOptional = userRepo.findByUsername(username);
		Users user = userOptional.orElseThrow(()->new SubException("could not find user with username"));
		UsersDTO userDTO = new UsersDTO();
		
		userDTO.setEmail(user.getEmail());
		userDTO.setDescription(user.getDescription());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setPassword((user.getPassword()));
		userDTO.setUsername(user.getUsername());

		return userDTO;
	}
	
	@Override
	public UsersDTO findByUsersEmail(String email) throws SubException {
		UsersDTO userDTO = null;
		Users user = userRepo.findById(email.toLowerCase()).orElseThrow(()->new SubException("unable to find user by email"));
		userDTO = new UsersDTO();
		
		userDTO.setEmail(user.getEmail());
		userDTO.setCreatedAt(user.getCreatedAt());
		userDTO.setDescription(user.getDescription());
		userDTO.setFollowersCount(user.getFollowersCount());
		userDTO.setEnabled(false);
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setLoginAt(user.getLoginAt());
		userDTO.setPassword(user.getPassword());
		userDTO.setUsername(user.getUsername());
		userDTO.setComments(user.getComments());
		userDTO.setFollowers(user.getFollowers());
		userDTO.setFollowing(user.getFollowing());
		userDTO.setPosts(user.getPosts());
		return userDTO;
	}

	@Override
	public String addUser(UsersDTO userDTO) throws SubException {
		boolean emailAvail = userRepo.findById(userDTO.getEmail().toLowerCase()).isEmpty();
		String emailAdded = null;
		if(emailAvail) {
			Users user = new Users();
			user.setEmail(userDTO.getEmail().toLowerCase());
			user.setCreatedAt(userDTO.getCreatedAt());
			user.setDescription(userDTO.getDescription());
			user.setFollowersCount(userDTO.getFollowersCount());
			user.setEnabled(false);
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setLoginAt(userDTO.getLoginAt());
			String hashedpwd = null;
			user.setPassword(hashedpwd);
			user.setUsername(userDTO.getUsername());
			userRepo.save(user);
			emailAdded = user.getEmail().toLowerCase();
		}
		return emailAdded;
	}

	@Override
	public String deleteUser(String email) throws SubException {
		Users user = new Users();
		user = userRepo.findById(email.toLowerCase()).orElseThrow(()->new SubException("unable to delete by email"));
		userRepo.delete(user);
		String foundEmail = "deleted account associated with: " + email;
		return foundEmail;
	}

	@Override
	public String updateUser(UsersDTO userDTO) throws SubException {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("this is userDTO emial : " + userDTO.getEmail());
		Users user = userRepo.findByUsername(userDTO.getUsername()).orElseThrow(()->new SubException("unable to find by username to update"));
		user.setDescription(userDTO.getDescription());		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		if(encoder.matches(userDTO.getPassword(), user.getPassword()) || userDTO.getPassword().equals(user.getPassword())) {
			user.setPassword(user.getPassword());
		} else {
			user.setPassword(encoder.encode(userDTO.getPassword()));
		}
		user.setUsername(userDTO.getUsername());
		return "updated user " + user.getUsername();
	}

}
