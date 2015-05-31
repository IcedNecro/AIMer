package com.checkpoint.aimer.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkpoint.aimer.entity.Aim;
import com.checkpoint.aimer.entity.AimComment;
import com.checkpoint.aimer.entity.User;
import com.checkpoint.aimer.repositories.AimCommentRepository;
import com.checkpoint.aimer.repositories.AimRepository;
import com.checkpoint.aimer.repositories.UserRepository;
import com.checkpoint.aimer.utils.EntityMerge;

/**
 * Service that provides CRUD operations for Aim comments
 * @author roman
 *
 */
@Service
public class AimCommentService {

	@Autowired
	private AimCommentRepository repository;
	
	@Autowired
	private AimRepository aimRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	/**
	 * removes aim with id
	 * @param id
	 */
	public void modifyComment(Principal principal, Long id, AimComment toMerge){
		User user = this.userRepository.getUserByNickname(principal.getName());
		AimComment comment = this.repository.findOne(id);
		if(comment.getUser().equals(user)) {
			comment = new EntityMerge<AimComment>().merge(comment, toMerge);
			this.repository.save(comment);
		} else throw new UnaccessibleAimComment();
	}
	
	
	/**
	 * Adds users comment to aim with aimId
	 * 
	 * @param principal - users principal
	 * @param aimId
	 * @param comment 
	 */
	public void addComment(Principal principal, Long aimId, AimComment comment) {
		Aim aim = this.aimRepository.findOne(aimId);
		User user = this.userRepository.getUserByNickname(principal.getName());
		comment.setAim(aim);
		comment.setUser(user);
		this.repository.save(comment);
	}
	
	/**
	 * removes aim with id
	 * @param id
	 */
	public void deleteComment(Principal principal, Long id){
		User user = this.userRepository.getUserByNickname(principal.getName());
		AimComment comment = this.repository.findOne(id);
		if(comment.getUser().equals(user))
			this.repository.delete(id);
		else
			throw new UnaccessibleAimComment();
	}
	
	/**
	 * @param aimId 
	 * @return list of comments of aim with id
	 */
	public List<AimComment> getComments(Long aimId) {
		Aim aim = this.aimRepository.findOne(aimId);
		return aim.getComments();
	}
	
	public class UnaccessibleAimComment extends RuntimeException {}
}
