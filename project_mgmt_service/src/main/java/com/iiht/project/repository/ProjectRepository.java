package com.iiht.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iiht.project.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	public List<Project> findByProjectNameOrderByProjectName(String projectName);
}
