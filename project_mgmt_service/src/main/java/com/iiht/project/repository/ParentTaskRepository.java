package com.iiht.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iiht.project.model.ParentTask;

@Repository
public interface ParentTaskRepository extends JpaRepository<ParentTask, Long>{
	
}
