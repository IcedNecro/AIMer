package com.checkpoint.aimer.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.checkpoint.aimer.entity.Aim;
import com.checkpoint.aimer.services.AimService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/aim")
@Api(basePath="/aim", value="controller provides CRUD for aims")
public class AimController {
	
	@Autowired
	private AimService service;

	@RequestMapping(value="/{id}", method=RequestMethod.POST,consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="posts aim on wall")
	public void addAim(@PathVariable Long id) {
		this.service.getAim(id);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST,consumes="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="posts aim on wall")
	public void addAim(@RequestBody Aim aim, Principal principal) {
		this.service.addAimToUser(aim, principal);
	}
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.PATCH,consumes="application/json")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="modifies aim")
	public void modifyAim(@PathVariable Long id, @RequestBody Aim aim, Principal principal) {	
		this.service.modify(principal, id, aim);
	} 
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.POST,consumes="application/json")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="removes aim from aim list of user")
	public void removeAim(@PathVariable Long id, Principal principal) {	
		this.service.remove(principal, id);
	}
	
	@RequestMapping(value="{/{uid}/all", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="gets the list of aims")
	public List<Aim> getAims(@PathVariable Long uid, @RequestParam Boolean desc) {
		List<Aim> aims = this.service.getAims(uid, desc);
		return aims;
	}
	
	@RequestMapping(value="/{aimId}/status", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="gets the list of aims")
	public Float aimStatus(@PathVariable Long aimId) {

		Float status = this.service.getAimStatus(aimId);
		return status;
	}
}


