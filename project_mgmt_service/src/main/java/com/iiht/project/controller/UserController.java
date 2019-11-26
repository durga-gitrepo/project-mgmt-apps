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

import com.iiht.project.model.User;
import com.iiht.project.repository.UserRepository;

@RestController
@RequestMapping("project/")
public class UserController {
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(value="user/{id}", method=RequestMethod.GET)
	public <T> ResponseEntity<?> getUser(@PathVariable Long id)
	{
		Optional<User> dataResponse = userRepository.findById(id);
		if(dataResponse != null)
		{
			User existingUser = dataResponse.get();
			return ResponseEntity.ok().body(userRepository.saveAndFlush(existingUser));
		}
		else
		{
			log.error("User doesn't exists");
			return ResponseEntity.badRequest().body("User doesn't exists");
		}
	}
		
	@RequestMapping(value="user/create", method=RequestMethod.POST)
	public User createUser(@RequestBody User user)
	{
		User response = userRepository.saveAndFlush(user);
		return response;
	}
	
	@RequestMapping(value="user/update/{id}", method=RequestMethod.PUT)
	public <T> ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id)
	{
		Optional<User> dataResponse =userRepository.findById(id);
		
		if(dataResponse != null)
		{
			User existingUser = dataResponse.get();
			BeanUtils.copyProperties(user, existingUser);
			return ResponseEntity.ok().body(userRepository.saveAndFlush(existingUser));
		}
		else
		{
			log.error("User doesn't exists for update");
			return ResponseEntity.badRequest().body("User doesn't exists for update");
		}
	}
	
	@RequestMapping(value="user/delete/{id}", method=RequestMethod.DELETE)
	public <T> ResponseEntity<?> deleteUser(@PathVariable Long id)
	{
		Optional<User> dataResponse =userRepository.findById(id);
		
		if(dataResponse != null)
		{
			User existingUser = dataResponse.get();
			userRepository.delete(existingUser);
			return ResponseEntity.ok().body("Deleted successfully");
		}
		else
		{
			log.error("User doesn't exists for delete");
			return ResponseEntity.badRequest().body("User doesn't exists for delete");
		}
	}
	
	
	@RequestMapping(value="user/dashboard", method=RequestMethod.GET)
	public <T> ResponseEntity<?> getAllUsers(@RequestParam(required=false, name="sortBy", value="sortBy") String sortBy)
	{
		if (sortBy != null && ! "".equals(sortBy.trim()))
		{
			List<User> allUsers = userRepository.findAll(Sort.by(sortBy).ascending());
			return ResponseEntity.ok().body(allUsers);
		}
		else
		{
			List<User> allUsers = userRepository.findAll();
			return ResponseEntity.ok().body(allUsers);
		}
	}
	
	@RequestMapping(value="user/search/{name}", method=RequestMethod.GET)
	public <T> ResponseEntity<?> searchUser(@PathVariable String name)
	{
		List<User> allUsers = userRepository.findAllUsersByName(name);
		return ResponseEntity.ok().body(allUsers);
	}
}
