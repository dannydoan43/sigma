package com.doan.sigma.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.doan.sigma.dto.UsersDTO;
import com.doan.sigma.entity.Users;
import com.doan.sigma.exception.SubException;

public interface UsersService {
	public List<Users> getAll() throws SubException;
	public UsersDTO findByUsersEmail(String email) throws SubException;
	public String findByUsername(String username) throws SubException;
	public String addUser(UsersDTO userDTO) throws SubException;
	public String deleteUser(String email) throws SubException;
	public String updateUser(UsersDTO userDTO) throws SubException;
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
