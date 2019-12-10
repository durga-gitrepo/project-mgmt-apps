package com.iiht.project.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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
import com.iiht.project.model.Project;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes=ProjectMgmtApplication.class)
public class ProjectRepositoryTest  {

	@MockBean
	ProjectRepository projectRepository;
	
	@Autowired
	TestEntityManager entityMgr;
	
	List<Project> projectList = new ArrayList<Project>();
	
	@Before
	public void setUp()
	{				
		Project p1= new Project();
		p1.setProjectId(new Long(1));
		p1.setStartDate(new Date());
		p1.setEndDate(new Date());
		p1.setManagerName("AAA");
		p1.setPriority(100);
		p1.setProjectName("test project1");
		
		projectList.add(p1);		
		
		Project p2= new Project();
		p2.setProjectId(new Long(1));
		p2.setStartDate(new Date());
		p2.setEndDate(new Date());
		p2.setManagerName("BBB");
		p2.setPriority(50);
		p2.setProjectName("test project2");
		
		projectList.add(p2);
	}
	
	@Test
	public void testCreateProject()
	{
		Project p1= new Project();
		p1.setProjectId(new Long(1));
		p1.setStartDate(new Date());
		p1.setEndDate(new Date());
		p1.setManagerName("AAA");
		p1.setPriority(100);
		p1.setProjectName("test project1");
						
		Project createdProject = p1;
		when(projectRepository.save(p1)).thenReturn(createdProject);
		Project result = projectRepository.save(p1);
	     
		verify(projectRepository, times(1)).save(p1);
	    verifyNoMoreInteractions(projectRepository);
		
		assertThat(result).isEqualTo(p1);	
	}
	
	@Test
	public void testFindProjectByName()
	{
		List<Project> projectList = new ArrayList<Project>();
		Project p1= new Project();
		p1.setProjectId(new Long(1));
		p1.setStartDate(new Date());
		p1.setEndDate(new Date());
		p1.setManagerName("AAA");
		p1.setPriority(100);
		p1.setProjectName("test project1");
		
		projectList.add(p1);
		
		String name = "test";
		when(projectRepository.findByProjectNameOrderByProjectName(name)).thenReturn(projectList);
		List<Project> result = projectRepository.findByProjectNameOrderByProjectName(name);
	     
		verify(projectRepository, times(1)).findByProjectNameOrderByProjectName(name);
	    verifyNoMoreInteractions(projectRepository);
		
		assertThat(result).isEqualTo(projectList);	
	}
	
	@Test
	public void testFindProjectByNameNonExisting()
	{
		List<Project> projectList = new ArrayList<Project>();
		Project p1= new Project();
		p1.setProjectId(new Long(1));
		p1.setStartDate(new Date());
		p1.setEndDate(new Date());
		p1.setManagerName("AAA");
		p1.setPriority(100);
		p1.setProjectName("test project1");
		
		projectList.add(p1);
		
		String name = "new";
		when(projectRepository.findByProjectNameOrderByProjectName(name)).thenReturn(null);
		List<Project> result = projectRepository.findByProjectNameOrderByProjectName(name);
	     
		verify(projectRepository, times(1)).findByProjectNameOrderByProjectName(name);
	    verifyNoMoreInteractions(projectRepository);
		
		assertThat(result).isNotEqualTo(projectList);
	}
	
	@Test
	public void testGetAllProject()
	{
		List<Project> allProjectList = new ArrayList<Project>();
		
		when(projectRepository.findAll()).thenReturn(projectList);
		List<Project> result = projectRepository.findAll();
	     
		verify(projectRepository, times(1)).findAll();
	    verifyNoMoreInteractions(projectRepository);
		
		assertThat(result).isEqualTo(projectList);	
		
		// negative
		Project p1= new Project();
		p1.setProjectId(new Long(3));
		p1.setStartDate(new Date());
		p1.setEndDate(new Date());
		p1.setManagerName("BBB");
		p1.setPriority(50);
		p1.setProjectName("test project3");
		allProjectList.add(p1);
		
		assertThat(result).isNotEqualTo(allProjectList);
			
	}
}
