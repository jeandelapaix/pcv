package rw.wys.tms.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.wys.tms.dao.ProjectDao;
import rw.wys.tms.domain.Project;
import rw.wys.tms.domain.Task;
import rw.wys.tms.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao projectDao;
	@Autowired
	TaskServiceImpl taskService;
	@Override
	public Project createProject(Project project) {
		projectDao.save(project);
		return project;
	}

	@Override
	public List<Project> viewAllProject() {
		return (List<Project>) projectDao.findAll();
	}

	@Override
	public void removeProject(Long id) throws Exception {
		
		Project localProject = projectDao.findOne(id);
		if (localProject != null) {
			List<Task> localTaks=localProject.getTasks();
			if(localTaks !=null) {
				for(Task task:localTaks) {
					taskService.deleteTask(task.getId());
				}
			}
			projectDao.delete(localProject);
		} else {
			throw new Exception("Project not exists");
		}
	}

	@Override
	public void updateProject(Project project) throws Exception {
		if (projectDao.findOne(project.getId()) != null) {
			projectDao.save(project);
		} else {
			throw new Exception("Mentioned project doesnot exist");
		}
	}

	@Override
	public Project findById(Long id) {
		return projectDao.findOne(id);

	}

	@Override
	public Project findByName(String name) {
		return projectDao.findByName(name);
	}

}
