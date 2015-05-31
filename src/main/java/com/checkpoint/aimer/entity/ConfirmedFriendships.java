package com.checkpoint.aimer.entity;

import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name="FriendShip")
public class ConfirmedFriendships extends AbstractRelation{
	public ConfirmedFriendships() {}
	
	public ConfirmedFriendships(AbstractRelation relation) {
		super(relation.friend_1,relation.friend_2);
	}
	
	public ConfirmedFriendships(User u1, User u2) {
		super(u1,u2);
	}
}
