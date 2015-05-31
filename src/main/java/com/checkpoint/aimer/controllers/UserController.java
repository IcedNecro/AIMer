package com.checkpoint.aimer.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.checkpoint.aimer.entity.Aim;
import com.checkpoint.aimer.entity.User;
import com.checkpoint.aimer.services.AimService;
import com.checkpoint.aimer.services.UserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Api(value="user crud operations", basePath="/user")
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired 
	private UserService service;
	
	@Autowired
	private AimService aimService;
	
	@RequestMapping(value="/{uid}", method = RequestMethod.GET, consumes="application/json")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="modifies user")
	public User getUserById(@PathVariable Long uid) {
		return this.service.getUser(uid);
	}
	
	@RequestMapping(value="/{id}/modify", method = RequestMethod.PUT, consumes="application/json")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="modifies user")
	public void modify(@PathVariable Long id, @RequestBody User user) {
		this.service.modify(id,user);
	}
	
	@RequestMapping(value="/{yourId}/friend_request/{userId}", method = RequestMethod.PATCH, consumes="application/json")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="sends friend request")
	public void addFriend(@PathVariable Long yourId, @PathVariable Long userId) {
		this.service.sendRequestForFriendship(yourId, userId);
	}
	
	@RequestMapping(value="/{yourId}/confirm/{requestId}", method = RequestMethod.PATCH, consumes="application/json")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="sends friend request")
	public void confirmFriend(@PathVariable Long yourId, @PathVariable Long requestId) {
		this.service.confirmFriendship(yourId, requestId);
	}

	@RequestMapping(value="/{uid}/friends", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="Returns list of friends")
	public List<User> getFriends(@PathVariable Long uid) {
		return this.service.getFriendList(uid);
	}
	
	@RequestMapping(value="/{userName}", method=RequestMethod.GET)
	@ApiOperation(value="posts aim on wall")
	public User addAim(@PathVariable String userName) {
		return this.service.getUserProfile(userName);
	}
}
