package com.checkpoint.aimer.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.checkpoint.aimer.entity.User;
import com.checkpoint.aimer.services.UserService;
import com.checkpoint.aimer.utils.RegistrationRequest;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;


@Api(value="Controller for registration",basePath="/auth", produces = "application/json")
@RequestMapping("/auth")
@RestController
public class LoginAndRegisterController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/register", method=RequestMethod.POST, consumes="application/json",
			produces="text/plain" )
	@ApiOperation("registers new user in system")
	public void register(@RequestBody RegistrationRequest request) {;
		User user = new User(request);
		service.addUser(user);
	}
	
}
