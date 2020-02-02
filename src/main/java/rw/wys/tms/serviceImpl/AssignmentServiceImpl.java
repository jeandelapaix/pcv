package rw.wys.tms.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.wys.tms.dao.AssignmentDao;
import rw.wys.tms.dao.TaskDao;
import rw.wys.tms.dao.UserDao;
import rw.wys.tms.domain.Assignment;
import rw.wys.tms.domain.Task;
import rw.wys.tms.domain.User;
import rw.wys.tms.service.AssignmentService;

@Service
@Transactional
public class AssignmentServiceImpl implements AssignmentService {

	@Autowired
	private AssignmentDao assignmentDao;

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private KieContainer kieContainer;

	@Autowired
	private UserDao userDao;

	@Override
	public List<Assignment> findByUser(User user) {
		return assignmentDao.findByUser(user);
	}

	@Override
	public List<Assignment> findByTask(Task task) {
		return assignmentDao.findByTask(task);
	}

	@Override
	public Assignment findById(Long id) {
		return assignmentDao.findOne(id);
	}

	public boolean save(Task task, User user) {
		Task localTask = taskDao.findOne(task.getId());
		User u = userDao.findOne(user.getId());
		int myTask = assignmentDao.findByTask(localTask).size();

		if (localTask != null) {
			if (myTask <= 0) {
				taskStatusChecker(task, u);
			}
		}
		boolean b = assignmentDao.save(new Assignment(task, user, true)) == null;
		return b;
	}

	@Override
	public void changeTaskStatus(Task task) {
		Task localTask = taskDao.findOne(task.getId());
		if (localTask != null) {
			taskStatusChecker2(task);
		}
	}

	public boolean desactivateAssignment(Assignment assignment) {
		assignment.setDate(false);
		boolean b = assignmentDao.save(assignment) == null;
		return b;
	}

	public boolean resactivateAssignment(Assignment assignment) {
		assignment.setDate(true);
		boolean b = assignmentDao.save(assignment) == null;
		return b;
	}

	public List<Assignment> findActiveByTask(Task task) {
		return assignmentDao.findActiveByTaskAndActive(task, true);
	}

	@Override
	public List<Assignment> findActiveByUser(User user) {
		return assignmentDao.findActiveByUserAndActive(user, true);
	}

	public void taskStatusChecker2(Task task) {
		KieSession kieSession = kieContainer.newKieSession("rulesSession");
		kieSession.insert(task);
		kieSession.fireAllRules();
		kieSession.dispose();
		taskDao.save(task);
		kieSession.halt();
	}

	public void taskStatusChecker(Task task, User u) {
		KieSession kieSession = kieContainer.newKieSession("rulesSession");
		kieSession.insert(task);
		kieSession.insert(u);
		kieSession.fireAllRules();
		kieSession.dispose();
		kieSession.halt();
	}

}
