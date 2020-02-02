package rw.wys.tms.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import rw.wys.tms.domain.Project;
import rw.wys.tms.domain.Task;

public interface TaskService {

	Task findById(long id) throws Exception;

	List<Task> findAllTask();

	List<Task> findByProject(long projectId) throws Exception;
	@Secured("ROLE_ADMIN")
	void updateTask(Task task) throws Exception;
	@Secured("ROLE_ADMIN")
	Task createTask(Task task) throws Exception;
	@Secured("ROLE_ADMIN")
	void deleteTask(long taskId) throws Exception;
	List<Task> findByStatus(String status);
	List<Task> findByProjectAndStatus(Project project, String status);
	
}
