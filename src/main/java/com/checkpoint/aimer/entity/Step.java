package com.checkpoint.aimer.entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Step {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private boolean isCompleted;
	private String description;
	private Date dateStarted;
	private Date finished;

	@ManyToOne
	@JoinColumn
	private Aim aim;
	
	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}

	public Date getFinished() {
		return finished;
	}

	public void setFinished(Date finished) {
		this.finished = finished;
	}

	@JsonIgnore
	public Aim getAim() {
		return aim;
	}

	public void setAim(Aim aim) {
		this.aim = aim;
	}
}
