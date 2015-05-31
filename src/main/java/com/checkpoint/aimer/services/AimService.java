package com.checkpoint.aimer.services;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.checkpoint.aimer.entity.Aim;
import com.checkpoint.aimer.entity.Step;
import com.checkpoint.aimer.entity.User;
import com.checkpoint.aimer.repositories.AimRepository;
import com.checkpoint.aimer.utils.EntityMerge;

@Service
public class AimService {
	public static Logger logger = Logger.getLogger("AimService");
	@Autowired
	private AimRepository repository;
	
	@Autowired
	private UserService userService;
	
	/**
	 * Creates aim of user that is principal
	 * @param aim
	 * @param principal
	 */
	public void addAimToUser(Aim aim, Principal principal) {
		User u = this.userService.getUserByNickname(principal.getName());
		aim.setUser(u);
		this.repository.save(aim);
	}
	
	/**
	 * @param aimId
	 * @return single aim specified with aimId
	 */
	public Aim getAim(Long aimId) {
		return this.repository.findOne(aimId);
	}
	
	/**
	 * Removes aim of authorized user
	 * @param principal - user's principals
	 * @param id - id of aim
	 */
	public void remove(Principal principal, Long id) {
		User u = this.userService.getUserByNickname(principal.getName());
		Aim oldAim = this.repository.findOne(id);
		if(oldAim.getUser().equals(u)) 
			this.repository.delete(oldAim);
		else throw new UnsecuredAimException();
	}
	
	/**
	 * Modifies aim of authorized user
	 * @param principal - User's principal
	 * @param id - id of aim
	 * @param aim
	 */
	public void modify(Principal principal, Long id, Aim aim) {
		User u = this.userService.getUserByNickname(principal.getName());
		Aim oldAim = this.repository.findOne(id);
		if(oldAim.getUser().equals(u)) {
			oldAim = new EntityMerge<Aim>().merge(oldAim, aim);
			this.repository.save(oldAim);
		} throw new UnsecuredAimException();
	}
	
	/**
	 * @param aimId
	 * @return the completion status of aim. Computes as a division of completed steps and
	 * summary amount of steps of current aim
	 */
	public Float getAimStatus(Long aimId) {
		Aim aim = this.repository.findOne(aimId);
		float amountOfSteps = aim.getSteps().size();
		float amountOfCompleted = 0; 
		for(Step step: aim.getSteps())
			if(step.isCompleted()) amountOfCompleted++;
		
		return amountOfCompleted/amountOfSteps;
	}
	
	/**
	 * @param userId 
	 * @param orderBy - column to order by
	 * @param desc - is descending
	 * @return list of aims 
	 */
	public List<Aim> getAims(Long userId, Boolean desc) {
		User user = this.userService.getUser(userId);

		if(desc)
			return this.repository.findByUserDesc(user);
		else
			return this.repository.findByUser(user);

	}
	
	/**
	 * Throws when user tries to perform some operations with aim
	 * that it doesn't created
	 * @author roman
	 *
	 */
	public class UnsecuredAimException extends RuntimeException{}
}
