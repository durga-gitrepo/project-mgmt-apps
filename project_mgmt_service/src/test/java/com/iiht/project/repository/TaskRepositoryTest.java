package com.iiht.project.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.iiht.project.main.ProjectMgmtApplication;
import com.iiht.project.model.Task;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes=ProjectMgmtApplication.class)
public class TaskRepositoryTest  {

	@MockBean
	TaskRepository taskRepository;
	
	@Autowired
	TestEntityManager entityMgr;
	
	List<Task> taskList = new ArrayList<Task>();
	
	@Before
	public void setUp()
	{				
		Task t1= new Task();
		t1.setTaskId(new Long(1));
		t1.setTaskName("Parent Task1");
		t1.setIsParentTask("Y");
				
		taskList.add(t1);
		
		Task t2= new Task();
		t1.setTaskId(new Long(11));
		t2.setParentTaskId(new Long(1));
		t2.setTaskName("Child Task1");
		t1.setIsParentTask("N");
		t1.setParentTaskId(new Long(1));
				
		taskList.add(t2);
	}
	
	@Test
	public void testCreateTask()
	{
		Task t1= new Task();
		t1.setTaskId(new Long(11));
		t1.setParentTaskId(new Long(1));
		t1.setTaskName("Task1");
						
		Task createdTask = t1;
		when(taskRepository.save(t1)).thenReturn(createdTask);
		Task result = taskRepository.save(t1);
	     
		verify(taskRepository, times(1)).save(t1);
	    verifyNoMoreInteractions(taskRepository);
		
		assertThat(result).isEqualTo(t1);	
	}
	
	@Test
	public void testgetAllParentTask()
	{
		List<Task> parentTaskList = new ArrayList<Task>();
		Task t1= new Task();
		t1.setTaskId(new Long(2));
		t1.setTaskName("Parent Task1");
		t1.setIsParentTask("Y");
		
		Task t2= new Task();
		t2.setTaskId(new Long(1));
		t2.setTaskName("Parent Task2");
		t2.setIsParentTask("Y");
		
		parentTaskList.add(t1);
		parentTaskList.add(t2);
		
		when(taskRepository.findAllParentTask()).thenReturn(parentTaskList);
		List<Task> result = taskRepository.findAllParentTask();
	     
		verify(taskRepository, times(1)).findAllParentTask();
	    verifyNoMoreInteractions(taskRepository);
		
		assertThat(result).isEqualTo(parentTaskList);	
	}
}
