package com.checkpoint.aimer.entity;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public abstract class AbstractRelation {
	@Id
	@GenericGenerator(name = "generator", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
	@ManyToOne
	@JoinColumn(name="friend_1")
	protected User friend_1;
	
	@ManyToOne
	@JoinColumn(name="friend_2")
	protected User friend_2;

	public AbstractRelation() {}
	
	public AbstractRelation(User friend_1, User friend_2) {
		this.friend_1 = friend_1;
		this.friend_2 = friend_2;
	}
	
	public User getFriend_1() {
		return friend_1;
	}

	public void setFriend_1(User friend_1) {
		this.friend_1 = friend_1;
	}

	public User getFriend_2() {
		return friend_2;
	}

	public void setFriend_2(User friend_2) {
		this.friend_2 = friend_2;
	}

}
