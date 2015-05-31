package com.checkpoint.aimer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.checkpoint.aimer.entity.AbstractRelation;
import com.checkpoint.aimer.entity.ConfirmedFriendships;
import com.checkpoint.aimer.entity.UnconfirmedFriendships;
import com.checkpoint.aimer.entity.User;

public interface UnconfirmedFriendshipsRepository extends CrudRepository<UnconfirmedFriendships,Long> {
	@Query("SELECT unconfirmed FROM UnconfirmedFriendships unconfirmed WHERE unconfirmed.friend_1=:u1 AND unconfirmed.friend_2=:u2")
	public UnconfirmedFriendships getFriendship(@Param("u1") User u1, @Param("u2") User u2);
}
