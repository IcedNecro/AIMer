package com.checkpoint.aimer.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
	@RequestMapping(value="/sec", method=RequestMethod.GET)
	public String secured() {
		return "secured";
	}
	
	@RequestMapping(value="/unsec", method=RequestMethod.GET)
	public String unsecured() {
		return "unsecured";
	}
	@RequestMapping(value="/unsec/2", method=RequestMethod.GET)
	public String unsecured2() {
		return "unsecured2";
	}
}
