package com.checkpoint.aimer.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.checkpoint.aimer.entity.Aim;
import com.checkpoint.aimer.entity.Step;
import com.checkpoint.aimer.entity.User;
import com.checkpoint.aimer.repositories.AimRepository;
import com.checkpoint.aimer.repositories.StepRepository;
import com.checkpoint.aimer.repositories.UserRepository;
import com.checkpoint.aimer.utils.EntityMerge;

@Service
public class StepService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private AimRepository aimRepository;
	
	@Autowired
	private StepRepository repository;
	
	/**
	 * Adds step to aim with aimId
	 * @param aimId
	 * @param step
	 */
	public void add(Long aimId, Step step, Principal principal) {
		User user = this.userRepository.getUserByNickname(principal.getName());
		Aim aim = this.aimRepository.findOne(aimId);
		if(aim.getUser().equals(user)) {
			step.setAim(aim);
			this.repository.save(step);
		} else throw new UnaccessibleStepException();
	}
	
	/**
	 * Merges existing step with modificator
	 * @param idToModify
	 * @param modificator
	 */
	public void modifyStep(Long idToModify, Step modificator, Principal principal) {
		User user = this.userRepository.getUserByNickname(principal.getName());
		Step step = this.repository.findOne(idToModify);
		if(user.getAims().contains(step.getAim())) {
			step = new EntityMerge<Step>().merge(step, modificator);
			this.repository.save(step);
		} else throw new UnaccessibleStepException();

	}
	
	/**
	 * Completes step specified with id
	 * @param id
	 */
	public void setCompleted(Principal principal, Long id) {
		User user = this.userRepository.getUserByNickname(principal.getName());
		Step step = this.repository.findOne(id);
		if(user.getAims().contains(step.getAim())) {
			step.setCompleted(true);
			this.repository.save(step);
		} else throw new UnaccessibleStepException();
	}
	
	/**
	 * @param aimId
	 * @return list of steps relates with aim
	 */
	public List<Step> getSteps(Long aimId) {
		Aim aim = this.aimRepository.findOne(aimId);
		return aim.getSteps();
	}
	
	/**
	 * Deletes step specified with id
	 * @param id
	 */
	public void deleteStep(Long id, Principal principal) {
		User user = this.userRepository.getUserByNickname(principal.getName());
		Step step = this.repository.findOne(id);
		if(user.getAims().contains(step.getAim()))
			this.repository.delete(id);
		else throw new UnaccessibleStepException();
	}
	
	/**
	 * @param id
	 * @return single step specified with id
	 */
	public Step getStep(Long id) {
		return this.repository.findOne(id);
	}
	
	public class UnaccessibleStepException extends RuntimeException {}
}
