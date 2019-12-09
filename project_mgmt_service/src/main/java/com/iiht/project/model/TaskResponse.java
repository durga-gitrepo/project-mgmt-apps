package com.iiht.project.model;

import java.sql.Date;


import com.fasterxml.jackson.annotation.JsonFormat;

public class TaskResponse {

	public Long taskId;
	
	public String taskName;
	
	public String parentTaskName;
	
	public String projectName;
	
	public Integer priority;	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date endDate;
		
	public String status;
	
	public String userName;
	
	public String isParentTask;
	
	public Long parentTaskId;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}	

	public String getParentTaskName() {
		return parentTaskName;
	}

	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsParentTask() {
		return isParentTask;
	}

	public void setIsParentTask(String isParentTask) {
		this.isParentTask = isParentTask;
	}

	public Long getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}	
}
