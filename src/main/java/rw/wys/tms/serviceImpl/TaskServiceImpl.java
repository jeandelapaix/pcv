package rw.wys.tms.serviceImpl;

import java.util.List;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rw.wys.tms.dao.ProjectDao;
import rw.wys.tms.dao.TaskDao;
import rw.wys.tms.domain.Project;
import rw.wys.tms.domain.Task;
import rw.wys.tms.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;

	@Autowired
	private ProjectDao projectDao;

	@Autowired
	private KieContainer kieContainer;

	@Transactional
	@Override
	public Task createTask(Task task) throws Exception {
		Task localTask = taskDao.findOne(task.getId());
		if (localTask == null) {
			taskStatusChecker(task);
			localTask = taskDao.save(task);
		} else {
			throw new Exception("task already exist");
		}
		return localTask;

	}

	@Transactional
	@Override
	public void updateTask(Task task) throws Exception {
		Task localTask = taskDao.findOne(task.getId());
		if (localTask != null) {
			taskDao.save(task);
		} else {
			throw new Exception("Task not exists");
		}
	}

	@Transactional
	@Override
	public void deleteTask(long taskId) throws Exception {
		Task localTask = taskDao.findOne(taskId);
		if (localTask != null) {
			taskDao.delete(localTask);
		} else {
			throw new Exception("Task not exists");
		}
	}

	@Override
	public List<Task> findByProject(long projectId) throws Exception {
		Project project = projectDao.findOne(projectId);

		if (project != null) {
			return taskDao.findByProject(project);
		} else {
			throw new Exception("Project ID not exists");
		}

	}

	public void taskStatusChecker(Task task) {
		KieSession kieSession = kieContainer.newKieSession("rulesSession");
		kieSession.insert(task);
		kieSession.fireAllRules();
		kieSession.dispose();
		kieSession.halt();
	}

	@Override
	public List<Task> findAllTask() {
		return (List<Task>) taskDao.findAll();
	}

	@Override
	public Task findById(long id) throws Exception {
		Task task = taskDao.findOne(id);
		if (task != null) {
			return task;
		} else {
			throw new Exception("Task not found");
		}
	}

	public KieContainer getKieContainer() {
		return kieContainer;
	}

	public void setKieContainer(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}

	@Override
	public List<Task> findByStatus(String status) {
		
		return taskDao.findByStatus(status);
	}

	@Override
	public List<Task> findByProjectAndStatus(Project project, String status) {
		// TODO Auto-generated method stub
		return taskDao.findByProjectAndStatus(project, status);
	}

}
