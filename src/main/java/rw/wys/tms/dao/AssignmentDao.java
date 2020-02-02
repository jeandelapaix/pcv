package rw.wys.tms.dao;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import rw.wys.tms.domain.Assignment;
import rw.wys.tms.domain.Task;
import rw.wys.tms.domain.User;

public interface AssignmentDao extends CrudRepository<Assignment, Long> {
	List<Assignment> findByUser(User user);
	List<Assignment> findByTask(Task task);
	List<Assignment> findActiveByTaskAndActive(Task task, boolean active);
	List<Assignment> findActiveByUserAndActive(User user, boolean active);
}
