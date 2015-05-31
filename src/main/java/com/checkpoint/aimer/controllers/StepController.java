package com.checkpoint.aimer.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.checkpoint.aimer.entity.Step;
import com.checkpoint.aimer.services.StepService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/step")
@Api(basePath="/step", value="api for modifiyng steps")
public class StepController {

	@Autowired
	private StepService stepService;
	
	@RequestMapping(value="/add/{aimId}", consumes="application/json", method=RequestMethod.POST) 
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="adds step to aim")
	public void addStep(@RequestBody Step step, @PathVariable Long aimId, Principal principal) {
		this.stepService.add(aimId, step, principal);
	}
	
	@RequestMapping(value="/delete/{stepId}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="deletes step")
	public void deleteStep(@PathVariable Long stepId, Principal principal) {
		this.stepService.deleteStep(stepId, principal);
	}
	
	@RequestMapping(value="/complete/{stepId}", consumes="application/json", method=RequestMethod.PUT) 
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="completes step")
	public void completeStep(@PathVariable Long stepId, Principal principal) {
		this.stepService.setCompleted(principal, stepId);
	}
	
	@RequestMapping(value="/{aimId}/getSteps/", consumes="application/json", method=RequestMethod.PUT) 
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="gets step list")
	public List<Step> getSteps(@PathVariable Long aimId) {
		return this.stepService.getSteps(aimId);
	}

}
