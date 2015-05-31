package com.checkpoint.aimer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.checkpoint.aimer.entity.ConfirmedFriendships;
import com.checkpoint.aimer.entity.User;

public interface ConfirmedFriendshipsRepository extends CrudRepository<ConfirmedFriendships,Long>{

	@Query("SELECT confirmed FROM ConfirmedFriendships confirmed WHERE confirmed.friend_1=:u1 AND confirmed.friend_2=:u2")
	public ConfirmedFriendships getFriendship(@Param("u1") User u1, @Param("u2") User u2);
}
