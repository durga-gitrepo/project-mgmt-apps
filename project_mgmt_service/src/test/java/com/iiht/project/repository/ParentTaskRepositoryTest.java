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
import com.iiht.project.model.ParentTask;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes=ProjectMgmtApplication.class)
public class ParentTaskRepositoryTest {
	
	@MockBean
	ParentTaskRepository parentTaskRepository;
	
	@Autowired
	TestEntityManager entityMgr;
	
	List<ParentTask> taskList = new ArrayList<ParentTask>();
	
	@Before
	public void setUp()
	{				
		ParentTask pt1= new ParentTask();
		pt1.setParentTaskId(new Long(1));
		pt1.setTaskName("Parent Task1");
				
		taskList.add(pt1);
		
		ParentTask pt2= new ParentTask();
		pt2.setParentTaskId(new Long(2));
		pt2.setTaskName("Parent Task1");
				
		taskList.add(pt2);
	}
	
	@Test
	public void testCreateParentTask()
	{
		ParentTask pt1= new ParentTask();
		pt1.setParentTaskId(new Long(1));
		pt1.setTaskName("Parent Task1");
						
		ParentTask createdParentTask = pt1;
		when(parentTaskRepository.save(pt1)).thenReturn(createdParentTask);
		ParentTask result = parentTaskRepository.save(pt1);
	     
		verify(parentTaskRepository, times(1)).save(pt1);
	    verifyNoMoreInteractions(parentTaskRepository);
		
		assertThat(result).isEqualTo(pt1);	
	}
	
}
