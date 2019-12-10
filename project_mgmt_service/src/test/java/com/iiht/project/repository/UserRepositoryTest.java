package com.iiht.project.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
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
import com.iiht.project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ContextConfiguration(classes=ProjectMgmtApplication.class)
public class UserRepositoryTest  {

	@MockBean
	UserRepository userRepository;
	
	@Autowired
	TestEntityManager entityMgr;
	
	List<User> userList = new ArrayList<User>();
	
	@Before
	public void  setUp()
	{				
		User u1= new User();
		u1.setUserId(new Long(1));
		u1.setFirstName("AAA");
		u1.setLastName("111");
		u1.setEmployeeId(new Long(123213));				
		userList.add(u1);
		
		User u2= new User();
		u2.setUserId(new Long(2));
		u2.setFirstName("BBB");
		u2.setLastName("123");
		u2.setEmployeeId(new Long(123213));	
		userList.add(u2);
	}
	
	/*@After
	public void tearDown()
	{		
		for (User user : userList) {
			entityMgr.remove(user);
		}
	}*/
	
	@Test
	public void testCreateUser()
	{
		User u1= new User();
		u1.setUserId(new Long(1));
		u1.setFirstName("AAA");
		u1.setLastName("111");
		u1.setEmployeeId(new Long(123213));
		
		User createdUser = u1;
		when(userRepository.save(u1)).thenReturn(createdUser);
		User result = userRepository.save(u1);
	     
		verify(userRepository, times(1)).save(u1);
	    verifyNoMoreInteractions(userRepository);
		
		assertThat(result).isEqualTo(u1);	
	}
	
	
	@Test
	public void testSearchUser()
	{
		List<User> userList = new ArrayList<User>();
		User u1= new User();
		u1.setUserId(new Long(1));
		u1.setFirstName("AAA");
		u1.setLastName("111");
		u1.setEmployeeId(new Long(123213));
		userList.add(u1);
		
		String name = "AAA";
		when(userRepository.findAllUsersByName(name)).thenReturn(userList);
		List<User> result = userRepository.findAllUsersByName(name);
	     
		verify(userRepository, times(1)).findAllUsersByName(name);
	    verifyNoMoreInteractions(userRepository);
		
		assertThat(result).isEqualTo(userList);	
		
		// negative
		String nameOther = "XXX";
		when(userRepository.findAllUsersByName(nameOther)).thenReturn(null);
		List<User> resultOther = userRepository.findAllUsersByName(nameOther);
	     
		verify(userRepository, times(1)).findAllUsersByName(nameOther);
	    verifyNoMoreInteractions(userRepository);
		
		assertThat(resultOther).isNotEqualTo(userList);
	}
	
	@Test
	public void testGetAllUser()
	{
		List<User> allUserList = new ArrayList<User>();
		when(userRepository.findAll()).thenReturn(userList);
		List<User> result = userRepository.findAll();
	     
		verify(userRepository, times(1)).findAll();
	    verifyNoMoreInteractions(userRepository);
		
		assertThat(result).isEqualTo(userList);	
		
		// negative
		User u1= new User();
		u1.setUserId(new Long(3));
		u1.setFirstName("ZZZ");
		u1.setLastName("333");
		u1.setEmployeeId(new Long(756400));
		allUserList.add(u1);
		
		assertThat(result).isNotEqualTo(allUserList);
			
	}
}
