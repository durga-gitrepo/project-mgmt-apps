package com.iiht.project.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ParentTask {

	@Id
	public Long parentTaskId;
	
	public String taskName;
	
	public Long getParentTaskId() {
		return parentTaskId;
	}

	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
