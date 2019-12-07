package com.iiht.project.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.project.model.Project;
import com.iiht.project.repository.ProjectRepository;

@RestController
@RequestMapping("project/")
public class ProjectController {
	
	private static Logger log = LoggerFactory.getLogger(ProjectController.class);
	
	@Autowired
	ProjectRepository projectRepository;
	
	@RequestMapping(value="project/{id}", method=RequestMethod.GET)
	public <T> ResponseEntity<?> getProject(@PathVariable Long id)
	{
		Optional<Project> dataResponse = projectRepository.findById(id);
		if(dataResponse != null)
		{
			Project existingProject = dataResponse.get();
			return ResponseEntity.ok().body(projectRepository.saveAndFlush(existingProject));
		}
		else
		{
			log.error("Project doesn't exists");
			return ResponseEntity.badRequest().body("Project doesn't exists");
		}
	}
		
	@RequestMapping(value="project/create", method=RequestMethod.POST)
	public Project createUser(@RequestBody Project project)
	{
		Project response = projectRepository.saveAndFlush(project);
		return response;
	}
	
	@RequestMapping(value="project/update/{id}", method=RequestMethod.PUT)
	public <T> ResponseEntity<?> updateUser(@RequestBody Project project, @PathVariable Long id)
	{
		Optional<Project> dataResponse =projectRepository.findById(id);
		
		if(dataResponse != null)
		{
			Project existingProject = dataResponse.get();
			BeanUtils.copyProperties(project, existingProject);
			return ResponseEntity.ok().body(projectRepository.saveAndFlush(existingProject));
		}
		else
		{
			log.error("Project doesn't exists for update");
			return ResponseEntity.badRequest().body("Project doesn't exists for update");
		}
	}
	
	@RequestMapping(value="project/delete/{id}", method=RequestMethod.DELETE)
	public <T> ResponseEntity<?> deleteUser(@PathVariable Long id)
	{
		Optional<Project> dataResponse =projectRepository.findById(id);
		
		if(dataResponse != null)
		{
			Project existingProject = dataResponse.get();
			projectRepository.delete(existingProject);
			return ResponseEntity.ok().body("Deleted successfully");
		}
		else
		{
			log.error("Project doesn't exists for delete");
			return ResponseEntity.badRequest().body("Project doesn't exists for delete");
		}
	}
	
	
	@RequestMapping(value="project/dashboard", method=RequestMethod.GET)
	public <T> ResponseEntity<?> getAllUsers(@RequestParam(required=false, name="sortBy", value="sortBy") String sortBy)
	{
		if (sortBy != null && ! "".equals(sortBy.trim()))
		{
			List<Project> allUsers = projectRepository.findAll(Sort.by(sortBy).ascending());
			return ResponseEntity.ok().body(allUsers);
		}
		else
		{
			List<Project> allProjects = projectRepository.findAll();
			return ResponseEntity.ok().body(allProjects);
		}
	}
	
	@RequestMapping(value="project/search/{projectName}", method=RequestMethod.GET)
	public <T> ResponseEntity<?> searchUser(@PathVariable String projectName)
	{
		List<Project> allProjects = projectRepository.findByProjectNameOrderByProjectName(projectName);
		return ResponseEntity.ok().body(allProjects);
	}
}
