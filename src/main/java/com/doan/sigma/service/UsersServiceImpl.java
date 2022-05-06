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
//import com.doan.sigma.utility.HashingUtility;

@Service
@Transactional
public class UsersServiceImpl implements UsersService,UserDetailsService{

	@Autowired
	private UsersRepository userRepo;
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> userOptional = userRepo.findByUsername(username);	//users to user
		Users user = userOptional.orElseThrow(()->new UsernameNotFoundException("could not find user with username"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.isEnabled(),true,true,true,getAuthorities("USER"));
	}//39:00
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
//		userDTO.setFriendsCount(user.getFriendsCount());
		userDTO.setEnabled(false);
//		userDTO.setFirstLastName(user.getFirstLastName());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setLoginAt(user.getLoginAt());
		userDTO.setPassword(user.getPassword());
		userDTO.setUsername(user.getUsername());
		userDTO.setComments(user.getComments());
		userDTO.setFollowers(user.getFollowers());
		userDTO.setFollowing(user.getFollowing());
//		System.out.println("THIS IS OUTSIDE POSTS: ");
//		for(Posts post : user.getPosts()) {
//			System.out.println("THIS IS INSIDE POSTS: " + post.getUsersEmail() + " " + post.getText());
//		}
		//need to do posts
		userDTO.setPosts(user.getPosts());
		return userDTO;
	}

	@Override
	public String addUser(UsersDTO userDTO) throws SubException {	//might wanna change string to void
		boolean emailAvail = userRepo.findById(userDTO.getEmail().toLowerCase()).isEmpty();		//maybe wanna add a phone number?
		String emailAdded = null;
		if(emailAvail) {
			Users user = new Users();
			user.setEmail(userDTO.getEmail().toLowerCase());
			user.setCreatedAt(userDTO.getCreatedAt());
			user.setDescription(userDTO.getDescription());
			user.setFollowersCount(userDTO.getFollowersCount());
			//user.setFriendsCount(userDTO.getFriendsCount());
			//no need to set enabled from DTO because i am not giving it? instead give it default value of false?
			user.setEnabled(false);
//			user.setFirstLastName(userDTO.getFirstLastName());
			user.setFirstName(userDTO.getFirstName());
			user.setLastName(userDTO.getLastName());
			user.setLoginAt(userDTO.getLoginAt());
			String hashedpwd = null;
//			try {
//				hashedpwd = HashingUtility.getHashValue(userDTO.getPassword());			//any point in having this if signup uses passwordEncoder()
//			} catch (NoSuchAlgorithmException e) {}
			user.setPassword(hashedpwd);
			user.setUsername(userDTO.getUsername());
			userRepo.save(user);
			emailAdded = user.getEmail().toLowerCase();
		}
		return emailAdded;
	}

	@Override
	public String deleteUser(String email) throws SubException {	//when deleting a user make sure to remove him from followers/followed
		Users user = new Users();
		user = userRepo.findById(email.toLowerCase()).orElseThrow(()->new SubException("unable to delete by email"));	//change this message
		userRepo.delete(user);
		String foundEmail = "deleted account associated with: " + email;
		return foundEmail;
	}

	@Override	//if you want to change string, you ahve to remove responsetype:text from userservice api on client
	public String updateUser(UsersDTO userDTO) throws SubException {		//dont need @pathvar id if DTO has right validations
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("this is userDTO emial : " + userDTO.getEmail());
//		Users user = userRepo.findById(userDTO.getEmail().toLowerCase()).orElseThrow(()->new SubException("unable to update by email"));
		Users user = userRepo.findByUsername(userDTO.getUsername()).orElseThrow(()->new SubException("unable to find by username to update"));
		user.setDescription(userDTO.getDescription());
		//user.setEmail(userDTO.getEmail().toLowerCase());
//		user.setFirstLastName(userDTO.getFirstLastName());
//		user.setEnabled(user.isEnabled());
//		user.setFollowersCount(user.getFollowersCount());
//		user.setLoginAt(user.getLoginAt());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
//		user.setFollowersCount(userDTO.getFollowersCount());
//		user.setFriendsCount(userDTO.getFriendsCount());
//		user.setEnabled(false);
		if(encoder.matches(userDTO.getPassword(), user.getPassword()) || userDTO.getPassword().equals(user.getPassword())) {
			user.setPassword(user.getPassword());
		} else {
			user.setPassword(encoder.encode(userDTO.getPassword()));	//just added in encoder
		}
		user.setUsername(userDTO.getUsername());
		//user.setPosts(userDTO.getPosts());   		//added this line..no need to set posts to null cause we are updating user info
		//include posts
//		List<Posts> postsList = new ArrayList<Posts>();		//i dont think i need to do all of this...
//		for(Posts post : user.getPosts()) {
//			Posts p = postsRepo.findById(post.getId()).orElseThrow(()-> new SubException("could not find posts in updateuser"));
//			p.setComments(post.getComments());
//			p.setCreatedAt(post.getCreatedAt());
//			p.setId(post.getId());
//			p.setLikeCount(post.getLikeCount());
//			p.setLiked(post.isLiked());
//			p.setText(post.getText());
//			//p.setUsers_email(userDTO.getEmail());
//			p.setUser(user);		//think its this
//			postsList.add(p);
//		}
		
		//userRepo.save(user);		//do i need this? does updating user change all emails?
		//probably incldue hashing in update
//		return userDTO;
		return "updated user " + user.getUsername();
	}

}
