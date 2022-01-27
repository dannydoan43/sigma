package com.doan.sigma.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.doan.sigma.entity.Users;
import com.doan.sigma.repository.UsersRepository;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired//can probably move this into usersserviceimpl
	private UsersRepository usersRepo;	//the video has this as private final?
	//38:46 freecodecamp.org
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> userOptional = usersRepo.findByUsername(username);
		Users user = userOptional.orElseThrow(()->new UsernameNotFoundException("could not find user with username"));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),user.isEnabled(),true,true,true,getAuthorities("USER"));
	}//39:00
	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role));
	}


}