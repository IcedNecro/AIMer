package com.checkpoint.aimer.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.checkpoint.aimer.entity.Aim;
import com.checkpoint.aimer.entity.User;

public interface AimRepository extends CrudRepository<Aim,Long>{

	@Query("SELECT aim FROM Aim aim WHERE aim.user=:user ORDER BY aim.priority")
	public List<Aim> findByUser(@Param("user") User user);
	
	@Query("SELECT aim FROM Aim aim WHERE aim.user=:user ORDER BY aim.priority desc")
	public List<Aim> findByUserDesc(@Param("user") User user);
}
