package rw.wys.tms.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import rw.wys.tms.domain.Project;
import rw.wys.tms.domain.Task;

public interface TaskDao extends CrudRepository<Task, Long> {
	List<Task> findByProject(Project project);
	List<Task> findByStatus(String status);
	List<Task> findByProjectAndStatus(Project project, String status);
	
}
