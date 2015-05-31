package com.checkpoint.aimer.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.checkpoint.aimer.entity.AimComment;

public interface AimCommentRepository extends CrudRepository<AimComment, Long>{}
