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

import com.checkpoint.aimer.entity.AimComment;
import com.checkpoint.aimer.services.AimCommentService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/comments")
public class AimCommentController {
	@Autowired
	private AimCommentService commentService;
	
	@RequestMapping(value="/add/{aimId}", consumes="application/json", method=RequestMethod.POST) 
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="adds comment to aim")
	public void addStep(@RequestBody AimComment comment, @PathVariable Long aimId, Principal principal) {
		this.commentService.addComment(principal, aimId, comment);
	}
	
	@RequestMapping(value="/modify/{commentId}", consumes="application/json", method=RequestMethod.POST) 
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="modifies comment")
	public void modifyComment(@RequestBody AimComment toChange, @PathVariable Long commentId, Principal principal) {
		this.commentService.modifyComment(principal, commentId, toChange);
	}
	
	@RequestMapping(value="/delete/{commentId}", consumes="application/json", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="deletes comment")
	public void deleteStep(@PathVariable Long commentId, Principal principal) {
		this.commentService.deleteComment(principal, commentId);
	}
	
	@RequestMapping(value="/{aimId}/comments/", produces="application/json", method=RequestMethod.GET) 
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="gets comments list for aim")
	public List<AimComment> getSteps(@PathVariable Long aimId) {
		return this.commentService.getComments(aimId);
	}
}
