package com.doan.sigma.service;

import java.util.ArrayList;
import java.util.List;
//import javax.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.doan.sigma.entity.Followers;
import com.doan.sigma.entity.Users;
import com.doan.sigma.exception.SubException;
import com.doan.sigma.repository.FollowersRepository;
import com.doan.sigma.repository.UsersRepository;
import com.doan.sigma.utility.FollowersId;

@Service
@Transactional
public class FollowersServiceImpl implements FollowersService{

	@Autowired
	private FollowersRepository followersRepo;
	
	@Autowired 
	private UsersRepository usersRepo;
	
	@Override
	public String updateFollowersEmail(String newEmail, String oldEmail) throws SubException {
		//maybe select all relationships(Followers) where = :oldEmail and save a copy? then write new relationships with the :newEmail
		//List<Followers> followers = select Followers as f where f.users_email OR f.followers_email = :oldEmail
		//List<Followers> newList = {};
		//
		//for(Follower mine : followers) {
		//	FollowersId id = new FollowersId();
		//	if(mine.users_email.equals(oldEmail)) {
		//		id.setUsers_email(newEmail);
		//		id.setFollowers_email(mine.getFollowers_email());
		//	} else if(mine.followers_email.equals(oldEmail) {
		//		id.setFollowers_email(newEmail);
		//		id.setUsers_email(mine.getUsers_email());
		//	}
		//	followersRepo.delete(followers);			//this one is sketchy
		//	followersRepo.save(id);
		//}
		//trying to insert into table2 a value that doesnt exist in table1
		//going to need to go back to users to do this
		
		List<Followers> oldEmailList = followersRepo.findAllByOldEmail(oldEmail.toLowerCase());
		List<Followers> newEmailList = new ArrayList<Followers>();
		boolean isAvail = followersRepo.findAllFollowersByEmail(newEmail.toLowerCase()).isEmpty();
		boolean isAvail2 = followersRepo.findAllFollowingByEmail(newEmail.toLowerCase()).isEmpty();
	
		FollowersId id = new FollowersId();
		Followers toSave = new Followers();
		if(isAvail && isAvail2) {
			for(Followers f : oldEmailList) {
				if(f.getFollowersId().getUsers_email().equalsIgnoreCase(oldEmail)) {
					id.setUsers_email(newEmail.toLowerCase());
					id.setFollowers_email(f.getFollowersId().getFollowers_email().toLowerCase());
					//do i need to set userone and stuff?
				} else if (f.getFollowersId().getFollowers_email().equalsIgnoreCase(oldEmail)) {
					id.setFollowers_email(newEmail.toLowerCase());
					id.setUsers_email(f.getFollowersId().getUsers_email().toLowerCase());
				}
				toSave.setFollowersId(id);
				newEmailList.add(toSave);
			}
			followersRepo.saveAll(newEmailList);
		}
		return "updated " + oldEmail + " to " + newEmail;
	}

	//maybe change the parameters to followersId 
	@Override
	public String deleteFollower(Followers id) throws SubException {
		Users userOne = usersRepo.findById(id.getFollowersId().getUsers_email().toLowerCase()).orElseThrow(()->new SubException("email to follow not found to remove follower relationship"));
		Users userTwo = usersRepo.findById(id.getFollowersId().getFollowers_email().toLowerCase()).orElseThrow(()->new SubException("follower email not found to remove follower relationship"));
		
		FollowersId newId = new FollowersId();
		newId.setFollowers_email(id.getFollowersId().getFollowers_email().toLowerCase());
		newId.setUsers_email(id.getFollowersId().getUsers_email().toLowerCase());
		
		Followers f = followersRepo.findById(newId).orElseThrow(()->new SubException("relationship not found to delete"));
		userOne.setFollowersCount(userOne.getFollowersCount()-1);
		//userTwo.setFollowingCount(blah);
		usersRepo.save(userOne);
		followersRepo.delete(f);
		return "removed " + id.getFollowersId().getFollowers_email() + " as a follower of " + id.getFollowersId().getUsers_email();
	}

	
	@Override	//might have to change this back to email,email
	public String addFollowerTo(Followers id) throws SubException {
		Users userTwo = usersRepo.findById(id.getFollowersId().getFollowers_email().toLowerCase()).orElseThrow(()->new SubException("follower email not found to add follower relationship"));
		Users userOne = usersRepo.findById(id.getFollowersId().getUsers_email().toLowerCase()).orElseThrow(()->new SubException("email to follow not found to add follower relationship"));
				
		Followers f = new Followers();
		FollowersId newId = new FollowersId();
		newId.setFollowers_email(userTwo.getEmail().toLowerCase());
		newId.setUsers_email(userOne.getEmail().toLowerCase());
		f.setFollowersId(newId);
		f.setUserOne(userOne);			//do i need this? i already have a way to get Users through the emailId
		f.setUserTwo(userTwo);
		
		boolean relationshipExists = followersRepo.findById(newId).isEmpty();
		if(!relationshipExists)
			throw new SubException("usertwo already follows userone");	
		
		userOne.setFollowersCount(userOne.getFollowersCount()+1);		//i was updating the wrong users follower count lol thats y save wasnt working
		usersRepo.save(userOne);
		followersRepo.save(f);
		
		return "added relationship";
	}
	
	@Override
	public List<String> getFollowing(String email) throws SubException {
		List<String> following = followersRepo.findAllFollowingByEmail(email);
		if(following.isEmpty())
			throw new SubException("user is not following anyone");
		return following;
	}
	
	@Override
	public List<String> getFollowersOf(String email) throws SubException {
		List<String> followersOf = followersRepo.findAllFollowersByEmail(email);
		if(followersOf.isEmpty()) {
			throw new SubException("no followers found");
		}
		
		return followersOf;
	}

	@Override
	public Followers getAll(Followers follower) throws SubException {
		Followers follow = followersRepo.findById(follower.getFollowersId()).orElseThrow(()->new SubException("i tried lol"));
		return follow;
	}

}