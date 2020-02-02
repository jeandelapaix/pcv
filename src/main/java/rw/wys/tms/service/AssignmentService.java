package rw.wys.tms.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import rw.wys.tms.domain.Assignment;
import rw.wys.tms.domain.Task;
import rw.wys.tms.domain.User;

public interface AssignmentService {
	List<Assignment> findByUser(User user);
	List<Assignment> findByTask(Task task);
	Assignment findById(Long id);
	List<Assignment> findActiveByTask(Task task);
	@Secured("ROLE_ADMIN")
	boolean resactivateAssignment(Assignment assignment);
	@Secured("ROLE_ADMIN")
	boolean desactivateAssignment(Assignment assignment);
	List<Assignment> findActiveByUser(User user);
	@Secured("ROLE_ADMIN")
	boolean save(Task task, User user);
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	void changeTaskStatus(Task task);
}
