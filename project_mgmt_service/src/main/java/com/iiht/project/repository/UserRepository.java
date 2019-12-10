package com.iiht.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iiht.project.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query(name="findAllUsers", value="select u from User u where firstName like :name% or lastName like :name% order by firstName asc", nativeQuery=false)
	public List<User> findAllUsersByName(@Param(value = "name") String name);
}
