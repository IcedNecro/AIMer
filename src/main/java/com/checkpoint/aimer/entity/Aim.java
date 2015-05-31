package com.checkpoint.aimer.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class Aim {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date timeStarted;
	private Date timeFinishing;
	private int priority;
	private String description;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy="aim", cascade= CascadeType.ALL)
	private List<AimComment> comments;

	@OneToMany(mappedBy="aim", cascade=CascadeType.ALL)
	private List<Step> steps;
	
	public Date getTimeStarted() {
		return timeStarted;
	}

	public void setTimeStarted(Date timeStarted) {
		this.timeStarted = timeStarted;
	}

	public Date getTimeFinishing() {
		return timeFinishing;
	}

	public void setTimeFinishing(Date timeFinishing) {
		this.timeFinishing = timeFinishing;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public List<AimComment> getComments() {
		return comments;
	}

	@JsonIgnore
	public void setComments(List<AimComment> comments) {
		this.comments = comments;
	}
	
	public void setUser(User u) {
		this.user = u;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Step> getSteps() {
		return steps;
	}

	@JsonIgnore
	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public User getUser() {
		return user;
	}	
	
	@JsonGetter(value="user_id")
	public Long getUserId() {
		return this.user.getId();
	}
	
	@JsonIgnore
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	@Override
	public String toString() {

		return priority+" priority";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Aim)
			return this.getId().equals(((Aim)obj).getId());
		else return false;
	}
}
