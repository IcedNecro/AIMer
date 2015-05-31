package com.checkpoint.aimer.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AimComment {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String description;
	private Date datePublished;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn
	private Aim aim;
	
	@JsonGetter("aim_id")
	public Long getAimId() {
		return this.aim.getId();
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	public User getUser() {
		return user;
	}

	@JsonGetter("user_id") 
	public Long getUserId() {
		return this.user.getId();
	}
	
	@JsonIgnore
	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public Aim getAim() {
		return aim;
	}
	
	@JsonIgnore
	public void setAim(Aim aim) {
		this.aim = aim;
	}
}
