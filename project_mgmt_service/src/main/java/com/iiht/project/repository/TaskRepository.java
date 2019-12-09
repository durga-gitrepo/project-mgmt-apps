package com.iiht.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iiht.project.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	
	@Query(name="findAllParentTask", value="select t from Task t where t.isParentTask = 'Y' and t.status = 'Active'", nativeQuery=false)
	public List<Task> findAllParentTask();
	
	@Query(name="findAllTaskDetails", 
			value="select t.task_id, t.task_name as taskName, pt.task_name as parentTaskName, "
					+ " p.project_name as projectName, t.priority, "
					+ " t.start_date, t.end_date, t.status, t.user_name, t.is_parent_task, t.parent_task_id "
					+ " from Task t left join Parent_Task pt on t.parent_task_id = pt.parent_task_id "
					+ " left join Project p on t.project_id = p.project_id where 1=1 order by :sortKey asc", nativeQuery=true)
	public Object[] findAllTaskDetails(@Param(value = "sortKey") String sortKey);
	
	@Query(name="findAllProjectTaskDetails", 
			value="select t.task_id, t.task_name as taskName, pt.task_name as parentTaskName, "
					+ " p.project_name as projectName, t.priority, "
					+ " t.start_date, t.end_date, t.status, t.user_name, t.is_parent_task, t.parent_task_id "
					+ " from Task t left join Parent_Task pt on t.parent_task_id = pt.parent_task_id "
					+ " left join Project p on t.project_id = p.project_id where t.project_id = :projectId ", nativeQuery=true)
	public Object[] findAllProjectTaskDetails(@Param(value = "projectId") Long projectId);
	
	@Query(name="findParentProjectTaskDetails", 
			value="select t.task_id, t.task_name as taskName, pt.task_name as parentTaskName, "
					+ " p.project_name as projectName, t.priority, "
					+ " t.start_date, t.end_date, t.status, t.user_name, t.is_parent_task, t.parent_task_id "
					+ " from Task t left join Parent_Task pt on t.parent_task_id = pt.parent_task_id "
					+ " left join Project p on t.project_id = p.project_id "
					+ " where t.task_id IN (:taskIds) ", nativeQuery=true)
	public Object[] findParentProjectTaskDetails(@Param(value = "taskIds") List<Long> taskIds);
	
}
