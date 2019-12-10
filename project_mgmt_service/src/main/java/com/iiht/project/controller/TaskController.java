package com.iiht.project.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.project.model.ParentTask;
import com.iiht.project.model.Task;
import com.iiht.project.model.TaskResponse;
import com.iiht.project.repository.ParentTaskRepository;
import com.iiht.project.repository.TaskRepository;

@RestController
@RequestMapping("project/")
public class TaskController {
	
	private static Logger log = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	ParentTaskRepository parentTaskRepository;
	
	@RequestMapping(value="task/{id}", method=RequestMethod.GET)
	public <T> ResponseEntity<?> getTask(@PathVariable Long id)
	{
		Optional<Task> dataResponse = taskRepository.findById(id);
		if(dataResponse != null)
		{
			Task existingTask = dataResponse.get();
			return ResponseEntity.ok().body(taskRepository.saveAndFlush(existingTask));
		}
		else
		{
			log.error("Task doesn't exists");
			return ResponseEntity.badRequest().body("Task doesn't exists");
		}
	}
		
	@RequestMapping(value="task/create", method=RequestMethod.POST)
	public Task createTask(@RequestBody Task task)
	{
		Task response = taskRepository.saveAndFlush(task);
		
		if(response != null && "Y".equalsIgnoreCase(response.getIsParentTask()))
		{
			ParentTask parentTask = new ParentTask();
			parentTask.setParentTaskId(response.getTaskId());
			parentTask.setTaskName(response.getTaskName());
			parentTaskRepository.saveAndFlush(parentTask);			
		}
		return response;
	}
	
	@RequestMapping(value="task/update/{id}", method=RequestMethod.PUT)
	public <T> ResponseEntity<?> updateTask(@RequestBody Task task, @PathVariable Long id)
	{
		Optional<Task> dataResponse = taskRepository.findById(id);
		
		if(dataResponse != null)
		{
			Task existingTask = dataResponse.get();
			BeanUtils.copyProperties(task, existingTask);
			return ResponseEntity.ok().body(taskRepository.saveAndFlush(existingTask));
		}
		else
		{
			log.error("Task doesn't exists for update");
			return ResponseEntity.badRequest().body("Task doesn't exists for update");
		}
	}
	
	@RequestMapping(value="task/endTask/{id}", method=RequestMethod.DELETE)
	public <T> ResponseEntity<?> deleteTask(@PathVariable Long id)
	{
		Optional<Task> dataResponse = taskRepository.findById(id);
		
		if(dataResponse != null)
		{
			Task existingTask = dataResponse.get();
			existingTask.setStatus("Completed");
			return ResponseEntity.ok().body(taskRepository.saveAndFlush(existingTask));
		}
		else
		{
			log.error("Task doesn't exists for ending");
			return ResponseEntity.badRequest().body("Task doesn't exists for ending");
		}
	}

	
	@RequestMapping(value="task/dashboard", method=RequestMethod.GET)
	public <T> ResponseEntity<?> getAllTasks(@RequestParam(required=false, name="sortBy", value="sortBy") String sortBy)
	{
		List<TaskResponse> taskList = new ArrayList<TaskResponse>();
		String sortColumn  = "";
		if (sortBy != null && ! "".equals(sortBy.trim()))
		{
			if("startDate".equals(sortBy))
			{
				sortColumn = "t.start_date";
			}
			else if("endDate".equals(sortBy))
			{
				sortColumn = "t.end_date";
			}
			else if("priority".equals(sortBy))
			{
				sortColumn = "t.priority";
			}
			else if("status".equals(sortBy))
			{
				sortColumn = "t.status";
			}
		}
		else
		{
			sortColumn = "t.taskName";
		}
		Object[] allTasks = taskRepository.findAllTaskDetails(sortColumn);
		if (allTasks != null &&  allTasks.length > 0)
		{
			for (Object dataObj : allTasks)
			{
				Object[] data = (Object[]) dataObj;
				TaskResponse taskDetails = new TaskResponse();
				taskDetails.setTaskId(((Integer) data[0]).longValue());
				taskDetails.setTaskName((String) data[1]); 
				taskDetails.setParentTaskName((String) data[2]); 
				taskDetails.setProjectName((String) data[3]); 
				taskDetails.setPriority((Integer) data[4]); 
				taskDetails.setStartDate((data[5]  != null ? (Date) data[5] : null)); 
				taskDetails.setEndDate((data[6]  != null ? (Date) data[6] : null)); 
				taskDetails.setStatus((String) data[7]); 
				taskDetails.setUserName((String) data[8]); 
				taskDetails.setIsParentTask((String) data[9]);
				taskList.add(taskDetails);
			}
		}
		return ResponseEntity.ok().body(taskList);
	}
	
	@RequestMapping(value="task/projectDashboard/{projectId}", method=RequestMethod.GET)
	public <T> ResponseEntity<?> getAllTasksForSelectproject( @PathVariable Long projectId, @RequestParam(required=false, name="sortBy", value="sortBy") String sortBy)
	{
		List<TaskResponse> taskList = new ArrayList<TaskResponse>();
		List<TaskResponse> parentTaskList = new ArrayList<TaskResponse>();
		
		Set<Long> parentTaskIds = new HashSet<Long>();
		String sortColumn  = "";
		if (sortBy != null && ! "".equals(sortBy.trim()))
		{
			if("startDate".equals(sortBy))
			{
				sortColumn = "t.start_date";
			}
			else if("endDate".equals(sortBy))
			{
				sortColumn = "t.end_date";
			}
			else if("priority".equals(sortBy))
			{
				sortColumn = "t.priority";
			}
			else if("status".equals(sortBy))
			{
				sortColumn = "t.status";
			}
		}
		else
		{
			sortColumn = "t.taskName";
		}
		Object[] allTasks = taskRepository.findAllProjectTaskDetails(projectId);
		if (allTasks != null &&  allTasks.length > 0)
		{
			for (Object dataObj : allTasks)
			{
				Object[] data = (Object[]) dataObj;
				TaskResponse taskDetails = new TaskResponse();
				taskDetails.setTaskId(((Integer) data[0]).longValue());
				taskDetails.setTaskName((String) data[1]); 
				taskDetails.setParentTaskName((String) data[2]); 
				taskDetails.setProjectName((String) data[3]); 
				taskDetails.setPriority((Integer) data[4]); 
				taskDetails.setStartDate((data[5]  != null ? (Date) data[5] : null)); 
				taskDetails.setEndDate((data[6]  != null ? (Date) data[6] : null)); 
				taskDetails.setStatus((String) data[7]); 
				taskDetails.setUserName((String) data[8]); 
				taskDetails.setIsParentTask((String) data[9]);
				taskDetails.setParentTaskId((data[10]  != null) ? ((Integer) data[10]).longValue() : null);
				
				if(taskDetails.getParentTaskId() != null && taskDetails.getParentTaskId() > 0)
				{
					parentTaskIds.add(taskDetails.getParentTaskId());
				}
				taskList.add(taskDetails);
			}
		}
		
		if(parentTaskIds != null && parentTaskIds.size()> 0)
		{
			parentTaskList = getParentTaskDetails(parentTaskIds);
			if(parentTaskList != null && parentTaskList.size() > 0)
			{
				taskList.addAll(parentTaskList);
			}
		}
		return ResponseEntity.ok().body(taskList);
	}
	
	@RequestMapping(value="task/getAllParentTasks", method=RequestMethod.GET)
	public <T> ResponseEntity<?> getAllParentTasks()
	{
		List<Task> allparentTasks = taskRepository.findAllParentTask();
		return ResponseEntity.ok().body(allparentTasks);
	}
	
	public List<TaskResponse> getParentTaskDetails(Set<Long> parentTaskIds)
	{
		List<TaskResponse> parentTaskList = new ArrayList<TaskResponse>();
		
		List<Long> taskIds = new ArrayList<Long>();
		taskIds.addAll(parentTaskIds);
		
		if ( taskIds != null && taskIds.size() > 0)
		{
			Object[] allTasks = taskRepository.findParentProjectTaskDetails(taskIds);
			if (allTasks != null &&  allTasks.length > 0)
			{
				for (Object dataObj : allTasks)
				{
					Object[] data = (Object[]) dataObj;
					TaskResponse taskDetails = new TaskResponse();
					taskDetails.setTaskId(((Integer) data[0]).longValue());
					taskDetails.setTaskName((String) data[1]); 
					taskDetails.setParentTaskName((String) data[2]); 
					taskDetails.setProjectName((String) data[3]); 
					taskDetails.setPriority((Integer) data[4]); 
					taskDetails.setStartDate((data[5]  != null ? (Date) data[5] : null)); 
					taskDetails.setEndDate((data[6]  != null ? (Date) data[6] : null)); 
					taskDetails.setStatus((String) data[7]); 
					taskDetails.setUserName((String) data[8]); 
					taskDetails.setIsParentTask((String) data[9]);				
					parentTaskList.add(taskDetails);
				}
			}	
		}
		return parentTaskList;
	}
}
