package com.checkpoint.aimer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.checkpoint.aimer.entity.User;

public interface UserRepository extends CrudRepository<User, Long>{
	@Query("select u from User u where u.credits.login=:login")
	public User getUserByNickname(@Param("login") String login);
}
