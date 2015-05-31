package com.checkpoint.aimer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkpoint.aimer.entity.Aim;
import com.checkpoint.aimer.entity.ConfirmedFriendships;
import com.checkpoint.aimer.entity.UnconfirmedFriendships;
import com.checkpoint.aimer.entity.User;
import com.checkpoint.aimer.repositories.ConfirmedFriendshipsRepository;
import com.checkpoint.aimer.repositories.UnconfirmedFriendshipsRepository;
import com.checkpoint.aimer.repositories.UserRepository;
import com.checkpoint.aimer.utils.EntityMerge;

/**
 * Service that provides operations over user account and not only
 * 
 * @author roman
 */
@Service
public class UserService {
	Logger log = Logger.getAnonymousLogger();
	
	@Autowired 
	private UserRepository repository;
	
	@Autowired
	private UnconfirmedFriendshipsRepository unconfirmedRepo;
	
	@Autowired
	private ConfirmedFriendshipsRepository confirmedRepo;
	
	public void addUser(User user) {
		this.repository.save(user);
	}
	
	public User getUserByNickname(String str) {
		return this.repository.getUserByNickname(str);
	}
	
	public User getUser(Long id) {
		return this.repository.findOne(id);
	}
	
	public List<Aim> getListOfAims(Long id) {
		User u = this.repository.findOne(id);
		return u.getAims();
	}
	
	/**
	 * Registers the request for friendship
	 * @param id_1 - id of user who want to be friend
	 * @param id_2 - id of user who is wanted to be a friend
	 */
	public void sendRequestForFriendship(Long id_1, Long id_2) {
		User u1 = this.repository.findOne(id_1);
		User u2 = this.repository.findOne(id_2);
		
		//Checks if this request currently exists or the relationships were defined earlier
		if(	this.unconfirmedRepo.getFriendship(u1, u2)==null &&
			this.confirmedRepo.getFriendship(u1, u2)==null) {
			UnconfirmedFriendships unconfirmed = new UnconfirmedFriendships(u1, u2);
			this.unconfirmedRepo.save(unconfirmed);
		}
	}
	
	/**
	 * Confirms the friend request. 
	 * @param yourId - yourId provided for security needs
	 * @param friendshipId - id of request user want to confirm
	 */
	public void confirmFriendship(Long yourId, Long friendshipId) {

		UnconfirmedFriendships friendship = this.unconfirmedRepo.findOne(friendshipId);

		if(yourId == friendship.getFriend_2().getId()) {
			this.unconfirmedRepo.delete(friendship);
			ConfirmedFriendships friendships = new ConfirmedFriendships(friendship);
			this.confirmedRepo.save(friendships);
			ConfirmedFriendships friendships2 = new ConfirmedFriendships(friendships.getFriend_2(), friendships.getFriend_1());
			this.confirmedRepo.save(friendships2);
		}
	}
	
	/**
	 * Returns the friendlist of user specified by 'id'
	 * @param id - id of user
	 * @return
	 */
	public List<User> getFriendList(Long id) {
		User u = this.repository.findOne(id);
		
		return u.getFriendList();
	}
	
	/**
	 * Returns whole user information (such as comments, aims, friends)
	 * @param userName
	 * @return
	 */
	public User getUserProfile(String userName) {
		User user = this.getUserByNickname(userName);
		return user;
	}
	
	public void modify(Long id, User u) {
		User user = this.repository.findOne(id);
		user = new EntityMerge<User>().merge(user, u);
		this.repository.save(user);		
	}
}
