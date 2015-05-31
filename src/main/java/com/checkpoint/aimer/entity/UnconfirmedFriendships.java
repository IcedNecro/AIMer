package com.checkpoint.aimer.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="FriendRequest")
public class UnconfirmedFriendships extends AbstractRelation {
	public UnconfirmedFriendships() {}
	
	public UnconfirmedFriendships(User u1, User u2) {
		super(u1, u2);
	}
}
