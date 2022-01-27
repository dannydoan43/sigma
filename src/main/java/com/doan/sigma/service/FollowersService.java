package com.doan.sigma.service;

import java.util.List;

import com.doan.sigma.entity.Followers;
import com.doan.sigma.exception.SubException;

public interface FollowersService {
	public String updateFollowersEmail(String newEmail, String oldEmail) throws SubException;
	public String deleteFollower(Followers id) throws SubException;
	public String addFollowerTo(Followers id) throws SubException;
	public List<String> getFollowing(String email) throws SubException;
	public List<String> getFollowersOf(String email) throws SubException;
	public Followers getAll(Followers follower) throws SubException;
}